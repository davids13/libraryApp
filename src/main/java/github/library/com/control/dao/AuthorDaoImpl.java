package github.library.com.control.dao;

import github.library.com.entity.Author;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Stateless
public class AuthorDaoImpl {

    private static final String PERSISTENCE_UNIT_NAME = "libraryApp_PU";

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    public List<Author> getAll() {
        Stream<Author> authorStream;
        List<Author> authorList = new ArrayList<>();

        final TypedQuery<Author> query = entityManager.createNamedQuery(Author.AUTHOR_FIND_ALL, Author.class);

        // The line bellow should be in the Service Layer because Dao layer shouldn´t do this verification
        // Using stream
        authorStream = query.getResultStream();
        if (authorStream != null)
            authorList = authorStream.collect(Collectors.toList());

        return authorList;

    }

    // this work
    public List<Author> get() {
        List<Author> authorList = new ArrayList<>();
        Author author = new Author();
        author.setAuthorName("bob");
        authorList.add(author);
        return authorList;
    }

}
