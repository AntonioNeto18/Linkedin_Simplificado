package com.antonio.linkedin.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.HashSet;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import com.antonio.linkedin.domain.DomainException;
import com.antonio.linkedin.domain.enums.RoleEnum;
import com.antonio.linkedin.domain.valueobjects.UserId;

public class UserTests {

    private User newUser() {
        return new User("Antonio Silva", "antonio@email.com", "secret", "Dev Java");
    }

    private User newAdmin() {
        return new User(new UserId(), "Admin", "admin@email.com", "secret", "Admin bio", RoleEnum.ADMIN,
                new HashSet<>(), new HashSet<>());
    }

    private Experience newExperience() {
        return new Experience("Software Engineer", "Backend", true, LocalDate.of(2024, 1, 1), null);
    }

    @Nested
    class Creation {

        @Test
        void shouldCreateUserWithDefaults() {
            User user = newUser();

            assertThat(user.getUserId()).isNotNull();
            assertThat(user.getFullName()).isEqualTo("Antonio Silva");
            assertThat(user.getEmail()).isEqualTo("antonio@email.com");
            assertThat(user.getPassword()).isEqualTo("secret");
            assertThat(user.getBiography()).isEqualTo("Dev Java");
            assertThat(user.getRole()).isEqualTo(RoleEnum.USER);
            assertThat(user.isAdmin()).isFalse();
            assertThat(user.getPosts()).isEmpty();
            assertThat(user.getExperiences()).isEmpty();
        }

        @Test
        void shouldGenerateDifferentIdsForEachUser() {
            assertThat(newUser().getUserId()).isNotEqualTo(newUser().getUserId());
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = { "   " })
        void shouldRequireFullName(String fullName) {
            assertThatThrownBy(() -> new User(fullName, "antonio@email.com", "secret", "bio"))
                    .isInstanceOf(DomainException.class)
                    .hasMessage("Full name is required");
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = { "   " })
        void shouldRequireEmail(String email) {
            assertThatThrownBy(() -> new User("Antonio", email, "secret", "bio"))
                    .isInstanceOf(DomainException.class)
                    .hasMessage("Email is required");
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = { "   " })
        void shouldRequirePassword(String password) {
            assertThatThrownBy(() -> new User("Antonio", "antonio@email.com", password, "bio"))
                    .isInstanceOf(DomainException.class)
                    .hasMessage("Password is required");
        }

        @Test
        void shouldAllowEmptyBiographyOnCreation() {
            User user = new User("Antonio", "antonio@email.com", "secret", null);

            assertThat(user.getBiography()).isNull();
        }
    }

    @Nested
    class Biography {

        @Test
        void shouldEditBiography() {
            User user = newUser();

            user.editBiography("Nova bio");

            assertThat(user.getBiography()).isEqualTo("Nova bio");
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = { "   " })
        void shouldNotEditBiographyWithBlankValue(String biography) {
            User user = newUser();

            assertThatThrownBy(() -> user.editBiography(biography))
                    .isInstanceOf(DomainException.class)
                    .hasMessage("Biography is required");
            assertThat(user.getBiography()).isEqualTo("Dev Java");
        }
    }

    @Nested
    class Posts {

        @Test
        void shouldAddPostAndSetUserAsAuthor() {
            User user = newUser();
            Post post = new Post("Hello LinkedIn");

            user.addPost(post);

            assertThat(user.getPosts()).containsExactly(post);
            assertThat(post.getAuthor().userId()).isEqualTo(user.getUserId());
            assertThat(post.getAuthor().email()).isEqualTo(user.getEmail());
        }

        @Test
        void shouldNotAddNullPost() {
            User user = newUser();

            assertThatThrownBy(() -> user.addPost(null))
                    .isInstanceOf(DomainException.class)
                    .hasMessage("Post is required");
        }

        @Test
        void authorShouldRemoveOwnPost() {
            User user = newUser();
            Post post = new Post("Hello LinkedIn");
            user.addPost(post);

            user.removePost(post);

            assertThat(user.getPosts()).isEmpty();
        }

        @Test
        void shouldNotRemoveNullPost() {
            User user = newUser();

            assertThatThrownBy(() -> user.removePost(null))
                    .isInstanceOf(DomainException.class)
                    .hasMessage("Post is required");
        }

        @Test
        void userShouldNotRemovePostFromAnotherAuthor() {
            User author = newUser();
            User other = newUser();
            Post post = new Post("Hello LinkedIn");
            author.addPost(post);

            assertThatThrownBy(() -> other.removePost(post))
                    .isInstanceOf(DomainException.class)
                    .hasMessage("Post author is not the same as the user");
            assertThat(author.getPosts()).containsExactly(post);
        }

        @Test
        void adminShouldRemoveOwnPost() {
            User admin = newAdmin();
            Post post = new Post("Comunicado");
            admin.addPost(post);

            admin.removePost(post);

            assertThat(admin.getPosts()).isEmpty();
        }

        @Test
        void adminShouldBeAllowedToRemovePostFromAnotherAuthor() {
            User author = newUser();
            User admin = newAdmin();
            Post post = new Post("Hello LinkedIn");
            author.addPost(post);

            admin.removePost(post);
        }
    }

    @Nested
    class Experiences {

        @Test
        void shouldAddExperienceAndSetUserAsAuthor() {
            User user = newUser();
            Experience experience = newExperience();

            user.addExperience(experience);

            assertThat(user.getExperiences()).containsExactly(experience);
            assertThat(experience.getAuthor().userId()).isEqualTo(user.getUserId());
            assertThat(experience.getAuthor().email()).isEqualTo(user.getEmail());
        }

        @Test
        void shouldNotAddNullExperience() {
            User user = newUser();

            assertThatThrownBy(() -> user.addExperience(null))
                    .isInstanceOf(DomainException.class)
                    .hasMessage("Experience is required");
        }

        @Test
        void authorShouldRemoveOwnExperience() {
            User user = newUser();
            Experience experience = newExperience();
            user.addExperience(experience);

            user.removeExperience(experience);

            assertThat(user.getExperiences()).isEmpty();
        }

        @Test
        void shouldNotRemoveNullExperience() {
            User user = newUser();

            assertThatThrownBy(() -> user.removeExperience(null))
                    .isInstanceOf(DomainException.class)
                    .hasMessage("Experience is required");
        }

        @Test
        void userShouldNotRemoveExperienceFromAnotherAuthor() {
            User author = newUser();
            User other = newUser();
            Experience experience = newExperience();
            author.addExperience(experience);

            assertThatThrownBy(() -> other.removeExperience(experience))
                    .isInstanceOf(DomainException.class)
                    .hasMessage("Experience author is not the same as the user");
            assertThat(author.getExperiences()).containsExactly(experience);
        }

        @Test
        void adminShouldRemoveOwnExperience() {
            User admin = newAdmin();
            Experience experience = newExperience();
            admin.addExperience(experience);

            admin.removeExperience(experience);

            assertThat(admin.getExperiences()).isEmpty();
        }
    }
}
