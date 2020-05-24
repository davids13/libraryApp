package github.library.com.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AuthorTest {

    @Test
    @DisplayName("Test Author - Basic object creation > Methods with expect behavior + Success")
    void testObjectCreationExpectedMethodsBehaviorSuccess() {
        // Setup
        final Author author = generateAuthorFull();
        final Author authorCompare = generateAuthorFull();
        // Execute / Assert
        Assertions.assertEquals(authorCompare, author);
        Assertions.assertEquals(authorCompare.hashCode(), author.hashCode());
        Assertions.assertNotNull(author.toString());
        Assertions.assertEquals("John Doe", author.getAuthorName());
    }

    private Author generateAuthorFull() {
        final Author author = new Author();
        author.setAuthorName("John Doe");
        return author;
    }
}