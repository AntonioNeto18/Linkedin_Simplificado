package com.antonio.linkedin.domain.valueobjects;

import java.util.UUID;

public record ExperienceId(UUID value) {
    public ExperienceId() {
        this(UUID.randomUUID());
    }

    public ExperienceId(String value) {
        this(UUID.fromString(value));
    }
}
