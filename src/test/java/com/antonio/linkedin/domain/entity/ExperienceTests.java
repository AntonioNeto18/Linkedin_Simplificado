package com.antonio.linkedin.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import com.antonio.linkedin.domain.DomainException;

public class ExperienceTests {

    private static final LocalDate START = LocalDate.of(2022, 3, 1);
    private static final LocalDate END = LocalDate.of(2024, 6, 30);

    @Test
    void shouldCreatePastExperience() {
        Experience experience = new Experience("Software Engineer", "Backend", false, START, END);

        assertThat(experience.getExperienceId()).isNotNull();
        assertThat(experience.getJobTitle()).isEqualTo("Software Engineer");
        assertThat(experience.getFunction()).isEqualTo("Backend");
        assertThat(experience.getCurrentlyWorking()).isFalse();
        assertThat(experience.getStartDate()).isEqualTo(START);
        assertThat(experience.getEndDate()).isEqualTo(END);
        assertThat(experience.getAuthor()).isNull();
    }

    @Test
    void shouldCreateCurrentExperienceWithoutEndDate() {
        Experience experience = new Experience("Software Engineer", "Backend", true, START, null);

        assertThat(experience.getCurrentlyWorking()).isTrue();
        assertThat(experience.getEndDate()).isNull();
    }

    @Test
    void shouldIgnoreEndDateWhenCurrentlyWorking() {
        Experience experience = new Experience("Software Engineer", "Backend", true, START, END);

        assertThat(experience.getEndDate()).isNull();
    }

    @Test
    void shouldRequireEndDateWhenNotCurrentlyWorking() {
        assertThatThrownBy(() -> new Experience("Software Engineer", "Backend", false, START, null))
                .isInstanceOf(DomainException.class)
                .hasMessage("End date is required when not currently working");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { "   " })
    void shouldRequireJobTitle(String jobTitle) {
        assertThatThrownBy(() -> new Experience(jobTitle, "Backend", true, START, null))
                .isInstanceOf(DomainException.class)
                .hasMessage("Job title is required");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { "   " })
    void shouldRequireFunction(String function) {
        assertThatThrownBy(() -> new Experience("Software Engineer", function, true, START, null))
                .isInstanceOf(DomainException.class)
                .hasMessage("Function is required");
    }

    @Test
    void shouldRequireCurrentlyWorking() {
        assertThatThrownBy(() -> new Experience("Software Engineer", "Backend", null, START, END))
                .isInstanceOf(DomainException.class)
                .hasMessage("Currently working is required");
    }

    @Test
    void shouldRequireStartDate() {
        assertThatThrownBy(() -> new Experience("Software Engineer", "Backend", true, null, null))
                .isInstanceOf(DomainException.class)
                .hasMessage("Start date is required");
    }
}
