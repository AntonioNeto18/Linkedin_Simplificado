package com.antonio.linkedin.domain.valueobjects;

import java.util.UUID;

public record PostId(UUID value) {
    public PostId() {
        this(UUID.randomUUID());
    }

    public PostId(String value) {
        this(UUID.fromString(value));
    }
}
