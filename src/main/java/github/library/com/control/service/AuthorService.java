package github.library.com.control.service;

import github.library.com.control.dao.AuthorDao;
import github.library.com.entity.Author;
import github.library.com.entity.Genre;

import javax.ejb.Stateful;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Stateful
public class AuthorService {

    @Inject
    private AuthorDao authorDao;

    public void save(Author author) {
        authorDao.save(author);
    }

    public List<Author> getListOfAllAuthors() {
        final Stream<Author> authorStream = authorDao.getAll();
        if (authorStream == null) {
            return Collections.emptyList();
        }
        return authorStream.collect(Collectors.toList());
    }

    public List<Genre> getListOfAllGenres() {
        final Stream<Genre> genreStream = authorDao.getAllGenre();
        if (genreStream == null)
            return Collections.emptyList();

        return genreStream.collect(Collectors.toList());
    }

}
