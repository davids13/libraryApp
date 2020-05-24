package github.library.com.control.service;

import github.library.com.control.dao.AuthorDaoImpl;
import github.library.com.entity.Author;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AuthorServiceImpl {

    @Inject
    AuthorDaoImpl authorDao;

    public List<Author> getListOfAllAuthors() {
        final Stream<Author> authorStream = authorDao.getAll();
        if (authorStream == null) {
            return Collections.emptyList();
        }
        return authorStream.collect(Collectors.toList());
    }

    public void saveAuthor(Author author) {
        authorDao.save(author);
    }
}
