package com.antonio.linkedin.domain.entity;

import java.time.LocalDate;

import com.antonio.linkedin.domain.DomainException;
import com.antonio.linkedin.domain.valueobjects.Author;
import com.antonio.linkedin.domain.valueobjects.ExperienceId;

public class Experience {
    private ExperienceId experienceId;
    private String jobTitle;
    private String function;
    private Boolean currentlyWorking;
    private LocalDate startDate;
    private LocalDate endDate;
    private Author author;

    public Experience(ExperienceId experienceId, String jobTitle, String function, Boolean currentlyWorking, LocalDate startDate,
            LocalDate endDate, Author author) {

        this.validJobTitle(jobTitle);
        this.validFunction(function);
        this.validCurrentlyWorking(currentlyWorking);
        this.validStartDate(startDate);

        this.experienceId = experienceId;
        this.jobTitle = jobTitle;
        this.function = function;
        this.currentlyWorking = currentlyWorking;
        this.startDate = startDate;
        this.endDate = endDate;
        this.author = author;
    }

    public Experience(String jobTitle, String function, Boolean currentlyWorking, LocalDate startDate, LocalDate endDate) {
        this.validJobTitle(jobTitle);
        this.validFunction(function);
        this.validCurrentlyWorking(currentlyWorking);
        this.validStartDate(startDate);
        
        this.experienceId = new ExperienceId();
        this.jobTitle = jobTitle;
        this.function = function;
        this.currentlyWorking = currentlyWorking;
        this.startDate = startDate;
        this.endDate = endDate;

        if (currentlyWorking) {
            this.endDate = null;
        }

        if (!currentlyWorking && endDate == null) {
            throw new DomainException("End date is required when not currently working");
        }
    }
    
    /* Validations */
    private void validJobTitle(String jobTitle) {
        if(jobTitle == null || jobTitle.isBlank()) {
            throw new DomainException("Job title is required");
        }
    }

    private void validFunction(String function) {
        if(function == null || function.isBlank()) {
            throw new DomainException("Function is required");
        }
    }

    private void validCurrentlyWorking(Boolean currentlyWorking) {
        if(currentlyWorking == null) {
            throw new DomainException("Currently working is required");
        }
    }

    private void validStartDate(LocalDate startDate) {
        if(startDate == null) {
            throw new DomainException("Start date is required");
        }
    }

    /* Getters */
    public ExperienceId getExperienceId() {
        return experienceId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getFunction() {
        return function;
    }

    public Boolean getCurrentlyWorking() {
        return currentlyWorking;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Author getAuthor() {
        return author;
    }

    /* Setters */
    public void setAuthor(Author author) {
        this.author = author;
    }
}
