FROM ubuntu:18.04 as base

LABEL "Davids"="yourEmail@abc.com"

# Ensures your Dockerfile installs the latest package versions with no further coding or manual intervention
RUN apt-get update && apt-get install -y curl && apt-get install -y wget

ARG JAVA_VERSION=jdk8u242-b08
ARG JAVA_BINARY_URL=https://github.com/AdoptOpenJDK/openjdk8-binaries/releases/download/${JAVA_VERSION}/OpenJDK8U-jdk_x64_linux_hotspot_8u242b08.tar.gz
ARG JAVA_SHA=f39b523c724d0e0047d238eb2bb17a9565a60574cf651206c867ee5fc000ab43
RUN echo "Downloading Java JDK 8" && \
	\
    curl -LfsSo /tmp/openjdk.tar.gz ${JAVA_BINARY_URL} >> /dev/null && \
    \
    echo "Checking download with hash" && \
    echo "${JAVA_SHA} */tmp/openjdk.tar.gz" | sha256sum -c - >> /dev/null && \
    \
    echo "Creating Java directory and change to it" && \
    mkdir -p /opt/java/openjdk >> /dev/null && \
    cd /opt/java/openjdk >> /dev/null && \
    \
    echo "Unziping Java" && \
    tar -xf /tmp/openjdk.tar.gz --strip-components=1 >> /dev/null && \
    \
    echo "Cleaning downloaded files" && \
    rm -rf /tmp/openjdk.tar.gz
ENV JAVA_HOME=/opt/java/openjdk
ENV PATH="/opt/java/openjdk/bin:$PATH"

ARG MAVEN_VERSION=3.6.3
ARG MAVEN_BASE_URL=https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries
ARG MAVEN_SHA=c35a1803a6e70a126e80b2b3ae33eed961f83ed74d18fcd16909b2d44d7dada3203f1ffe726c17ef8dcca2dcaa9fca676987befeadc9b9f759967a8cb77181c0
RUN echo "Creating Maven folder" && \
    mkdir -p /usr/share/maven /usr/share/maven/ref >> /dev/null && \
    \
    echo "Downloading Maven" && \
    curl -fsSL -o /tmp/apache-maven.tar.gz ${MAVEN_BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz >> /dev/null && \
    \
    echo "Checking download hash" && \
    echo "${MAVEN_SHA}  /tmp/apache-maven.tar.gz" | sha512sum -c - >> /dev/null && \
    \
    echo "Unziping Maven" && \
    tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 >> /dev/null && \
    \
    echo "Cleaning and setting links" && \
    rm -f /tmp/apache-maven.tar.gz && \
    ln -s /usr/share/maven/bin/mvn /usr/bin/mvn
#Define environmental variables required by Maven, like Maven_Home directory and where the maven repo is located
ENV MAVEN_HOME=/usr/share/maven
ENV MAVEN_CONFIG="$USER_HOME_DIR/.m2"

FROM base as build

RUN mkdir -p /opt/app
COPY .. /opt/app
WORKDIR /opt/app
RUN mvn clean install

FROM base as deploy

# Payara version
ARG PAYARA_PKG=https://s3-eu-west-1.amazonaws.com/payara.fish/payara-5-micro-prerelease.jar
ARG PAYARA_VERSION=prerelease
ARG PKG_FILE_NAME=payara-micro.jar

# Initialize the configurable environment variables
ENV PAYARA_PATH /opt/payara
ENV PAYARA_DEPLOYMENT_PATH=${PAYARA_PATH}/deployments
ENV PAYARA_MICRO_JAR=${PAYARA_PATH}/PKG_FILE_NAME
ENV AUTODEPLOY_DIR=${PAYARA_PATH}/deployments
ENV DEPLOY_DIR=${PAYARA_PATH}/deployments

RUN echo "Installing payara" && \
    mkdir -p ${PAYARA_PATH}/deployments && \
    useradd -d ${PAYARA_PATH} payara && echo payara:payara | chpasswd && \
    chown -R payara:payara /opt/payara

RUN wget --quiet -O ${PAYARA_PATH}/${PKG_FILE_NAME} ${PAYARA_PKG} >> /dev/null

COPY --from=build /opt/app/libraryApp/target/*.war ${PAYARA_DEPLOYMENT_PATH}

USER payara
WORKDIR ${PAYARA_PATH}

# Inform Docker that the container is listening on the specified port at runtime.
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/opt/payara/payara-micro.jar", "--deploymentDir", "/opt/payara/deployments"]

# Run the specified command within the container.
#CMD [ "executable" ]
#CMD ["/usr/lib/postgresql/9.3/bin/postgres", "-D", "/var/lib/postgresql/9.3/main", "-c", "config_file=/etc/postgresql/9.3/main/postgresql.conf"]