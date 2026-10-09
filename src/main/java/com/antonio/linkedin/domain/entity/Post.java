package com.antonio.linkedin.domain.entity;

import com.antonio.linkedin.domain.valueobjects.Author;
import com.antonio.linkedin.domain.valueobjects.PostId;

public class Post {
    private PostId postId;
    private String content;
    private Author author;
    
    public Post(PostId postId, String content, Author author) {
        this.postId = postId;
        this.content = content;
        this.author = author;
    }

    public Post(String content) {
        this.postId = new PostId();
        this.content = content;
    }

    /* Getters */
    public PostId getPostId() {
        return postId;
    }
    public String getContent() {
        return content;
    }
    public Author getAuthor() {
        return author;
    }

    /* Setters */
    public void setAuthor(Author author) {
        this.author = author;
    }    
}
