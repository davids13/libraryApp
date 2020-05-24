package github.library.com.commons.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class JaxRSApplicationTest {

    @InjectMocks
    private JaxRSApplication jaxRSApplication;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Test JaxRSApplication - Basic object creation > Methods with expect behavior + Success")
    void testObjectCreationExpectedMethodsBehaviorSuccess() {
        // Assert
        Assertions.assertNotNull(jaxRSApplication.toString());
    }
}