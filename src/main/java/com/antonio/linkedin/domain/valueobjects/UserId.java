package com.antonio.linkedin.domain.valueobjects;

import java.util.UUID;

public record UserId(UUID value) {
    public UserId() {
        this(UUID.randomUUID());
    }

    public UserId(String value) {
        this(UUID.fromString(value));
    }
}
