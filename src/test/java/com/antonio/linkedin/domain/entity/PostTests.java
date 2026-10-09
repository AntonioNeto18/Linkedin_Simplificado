package com.antonio.linkedin.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.antonio.linkedin.domain.valueobjects.Author;

public class PostTests {

    @Test
    void shouldCreatePostWithoutAuthor() {
        Post post = new Post("Hello LinkedIn");

        assertThat(post.getPostId()).isNotNull();
        assertThat(post.getContent()).isEqualTo("Hello LinkedIn");
        assertThat(post.getAuthor()).isNull();
    }

    @Test
    void shouldGenerateDifferentIdsForEachPost() {
        assertThat(new Post("a").getPostId()).isNotEqualTo(new Post("b").getPostId());
    }

    @Test
    void shouldSetAuthor() {
        Post post = new Post("Hello LinkedIn");
        Author author = new Author("antonio@email.com");

        post.setAuthor(author);

        assertThat(post.getAuthor()).isEqualTo(author);
    }
}
