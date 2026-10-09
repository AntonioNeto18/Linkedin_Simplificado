package com.antonio.linkedin.domain.entity;

import java.util.HashSet;
import java.util.Set;

import com.antonio.linkedin.domain.DomainException;
import com.antonio.linkedin.domain.enums.RoleEnum;
import com.antonio.linkedin.domain.valueobjects.Author;
import com.antonio.linkedin.domain.valueobjects.UserId;

public class User {
    private UserId userId;
    private String fullName;
    private String email;
    private String password;
    private String biography;
    private RoleEnum role;

    private Set<Post> posts = new HashSet<>();
    private Set<Experience> experiences = new HashSet<>();
    
    public User(UserId userId, String fullName, String email, String password, String biography, RoleEnum role,
            Set<Post> posts, Set<Experience> experiences) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.biography = biography;
        this.role = role;
        this.posts = posts;
        this.experiences = experiences;
    }

    public User(String fullName, String email, String password, String biography) {
        this.validFullName(fullName);
        this.validEmail(email);
        this.validPassword(password);

        this.userId = new UserId();
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.biography = biography;
        this.role = RoleEnum.USER;
        this.posts = new HashSet<>();
        this.experiences = new HashSet<>();
    }

    public void addPost(Post post) {
        if (post == null) {
            throw new DomainException("Post is required");
        }
        post.setAuthor(new Author(this.userId, this.email));
        this.posts.add(post);
    }

    public void addExperience(Experience experience) {
        if (experience == null) {
            throw new DomainException("Experience is required");
        }

        experience.setAuthor(new Author(this.userId, this.email));
        this.experiences.add(experience);
    }

    public void removePost(Post post) {
        if (post == null) {
            throw new DomainException("Post is required");
        }

        if (!post.getAuthor().userId().equals(this.userId) || !this.isAdmin()) {
            throw new DomainException("Post author is not the same as the user");
        }

        this.posts.remove(post);
    }

    public void removeExperience(Experience experience) {
        if (experience == null) {
            throw new DomainException("Experience is required");
        }

        if (!experience.getAuthor().userId().equals(this.userId) || !this.isAdmin()) {
            throw new DomainException("Experience author is not the same as the user");
        }

        this.experiences.remove(experience);
    }

    public void editBiography(String biography) {
        this.validBiography(biography);
        this.biography = biography;
    }

    public boolean isAdmin() {
        return this.role.equals(RoleEnum.ADMIN);
    }

    /* Validations */
    private void validFullName(String fullName) {
        if(fullName == null || fullName.isBlank()) {
            throw new DomainException("Full name is required");
        }
    }

    private void validEmail(String email) {
        if(email == null || email.isBlank()) {
            throw new DomainException("Email is required");
        }
    }

    private void validPassword(String password) {
        if(password == null || password.isBlank()) {
            throw new DomainException("Password is required");
        }
    }

    private void validBiography(String biography) {
        if(biography == null || biography.isBlank()) {
            throw new DomainException("Biography is required");
        }
    }

    /* Getters */
    public UserId getUserId() {
        return userId;
    }
    public String getFullName() {
        return fullName;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getBiography() {
        return biography;
    }
    public RoleEnum getRole() {
        return role;
    }
    public Set<Post> getPosts() {
        return posts;
    }
    public Set<Experience> getExperiences() {
        return experiences;
    }
}
