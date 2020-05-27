package github.library.com.control.dao;

import github.library.com.entity.Author;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.stream.Stream;

@Stateless
public class AuthorDaoImpl {

    private static final String PERSISTENCE_UNIT_NAME = "libraryApp_PU";

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    public Stream<Author> getAll() {
        final TypedQuery<Author> query = entityManager.createNamedQuery(Author.AUTHOR_FIND_ALL, Author.class);
        return query.getResultStream();
    }

    public void save(Author author) {
        //save or update the employee
        //Author authorDB = entityManager.merge(author);
        entityManager.persist(author);
        //update with id from db
        //authorDB.setId(author.getId());
    }

}
