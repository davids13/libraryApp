package github.library.com.control.dao;

import github.library.com.entity.Author;
import github.library.com.entity.Genre;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.stream.Stream;

@Stateless
public class AuthorDao {

    private static final String PERSISTENCE_UNIT_NAME = "libraryApp_PU";
    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)

    private EntityManager entityManager;

    public void save(Author author) {
        entityManager.persist(author);
    }

    public Stream<Author> getAll() {
        final TypedQuery<Author> query = entityManager.createNamedQuery(Author.AUTHOR_FIND_ALL, Author.class);
        return query.getResultStream();
    }

    public Stream<Genre> getAllGenre() {
        final TypedQuery<Genre> query = entityManager.createNamedQuery(Genre.GENRE_FIND_ALL, Genre.class);
        return query.getResultStream();
    }

}
