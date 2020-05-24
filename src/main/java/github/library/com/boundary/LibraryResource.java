package github.library.com.boundary;

import github.library.com.control.dao.AuthorDaoImpl;
import github.library.com.entity.Author;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/library")
public class LibraryResource {

    private static final Logger LOG = Logger.getLogger(LibraryResource.class.getName());

    @Inject
    private AuthorDaoImpl authorDao;

    //@Path("/get")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAuthors() {
        LOG.log(Level.FINE, "REST request to get all Authors");
        final List<Author> authorList = authorDao.getAll();
        if (authorList.isEmpty())
            return Response.status(Response.Status.NO_CONTENT).build();

        return Response.ok(authorList).build();
    }
/*
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get() {
        LOG.log(Level.FINE, "REST request to get all Authors");
        List<Author> authorList = authorDao.get();
        if (authorList.isEmpty())
            return Response.status(Response.Status.NO_CONTENT).build();

        return Response.ok(authorList).build();
    }
 */
}
