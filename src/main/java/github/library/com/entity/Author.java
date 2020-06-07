package github.library.com.entity;

import github.library.com.commons.jpa.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "author")
@NamedQuery(name = Author.AUTHOR_FIND_ALL, query = Author.AUTHOR_FIND_ALL_QUERY)
public class Author extends AbstractEntity implements Serializable {

    public static final String AUTHOR_FIND_ALL = "Author.findAll";
    public static final String AUTHOR_FIND_ALL_QUERY = "SELECT a FROM Author a";

    @Column(name = "author_name")
    private String authorName;

    // no arg constructor and full constructor for the serialization
    public Author() {
    }

    public Author(int id, String authorName) {
        super(id);
        this.authorName = authorName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(authorName, author.authorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorName);
    }

    @Override
    public String toString() {
        return String.format("Author{authorName='%s'}", authorName);
    }
}
