package com.antonio.linkedin.domain.valueobjects;

public record Author(UserId userId, String email) {
    public Author(String email) {
        this(new UserId(), email);
    }

    public Author(String userId, String email) {
        this(new UserId(userId), email);
    }
}
