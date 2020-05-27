package github.library.com.boundary;

import github.library.com.control.service.AuthorServiceImpl;
import github.library.com.entity.Author;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/library")
public class LibraryResource {

    @Inject
    private AuthorServiceImpl authorServiceImpl;

    //@Path("/get")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAuthors() {
        final List<Author> authorList = authorServiceImpl.getListOfAllAuthors();
        if (authorList.isEmpty())
            return Response.status(Response.Status.NO_CONTENT).build();

        return Response.ok(authorList).build();
    }

    @Path("/add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveAuthor(Author author) {
        if (author == null)
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity("request field").build());

        authorServiceImpl.saveAuthor(author);
        return Response.status(Response.Status.CREATED).entity(author).build();
    }

}
