package github.library.com.boundary;

import github.library.com.control.service.AuthorService;
import github.library.com.entity.Author;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Path("/library")
@Stateful
public class LibraryResource {

    @Inject
    private AuthorService authorService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAuthors() {
        /*final Stream<Author> authorList = authorService.getAll();
        final List<Author> authors = authorList.collect(Collectors.toList());
        return Response.ok(authors).build();
*/
        final List<Author> authorList = authorService.getListOfAllAuthors();
        if (authorList.isEmpty())
            return Response.status(Response.Status.NO_CONTENT).build();

        return Response.ok(authorList).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postAuthor(final Author author, @Context UriInfo uriInfo) {
        if (author.getAuthorName().isEmpty())
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).build());

        authorService.save(author);
        URI createdURI = uriInfo.getBaseUriBuilder().path(String.valueOf(author.getId())).build();
        //return Response.status(Response.Status.CREATED).entity(author).build();
        //or
        //return Response.ok(createdURI).build();
        return Response.created(createdURI).build();
    }

}
