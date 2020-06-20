package github.library.com.entity;

import github.library.com.commons.jpa.AbstractEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "authors")
@NamedQuery(name = Author.AUTHOR_FIND_ALL, query = Author.AUTHOR_FIND_ALL_QUERY)
public class Author extends AbstractEntity implements Serializable {

    public static final String AUTHOR_FIND_ALL = "Author.findAll";
    public static final String AUTHOR_FIND_ALL_QUERY = "SELECT a FROM Author a";

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    @ManyToMany(mappedBy = "bookAndAuthor")
    Set<Book> authorAndBook;

    // An no arg constructor and full constructor for the serialization
    public Author() {
    }

    public Author(int id, String firstName, String lastName, Set<Book> authorAndBook) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.authorAndBook = authorAndBook;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Book> getAuthorAndBook() {
        return authorAndBook;
    }

    public void setAuthorAndBook(Set<Book> authorAndBook) {
        this.authorAndBook = authorAndBook;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Author author = (Author) o;
        return Objects.equals(firstName, author.firstName) &&
                Objects.equals(lastName, author.lastName) &&
                Objects.equals(authorAndBook, author.authorAndBook);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName, authorAndBook);
    }

    @Override
    public String toString() {
        return String.format("Author{firstName='%s', lastName='%s', authorAndBook=%s}", firstName, lastName, authorAndBook);
    }
}
