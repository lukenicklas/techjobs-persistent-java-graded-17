package org.launchcode.techjobs.persistent.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Employer extends AbstractEntity {

    @NotBlank(message = "Name is required") //validation for location
    @Size(min = 3, max = 70, message = "Name must be between 3 and 70 characters")
    private String location;

    @OneToMany
    @JoinColumn(name = "employer_id") //foreign key name in database
    private final List<Job> jobs = new ArrayList<>(); //initializes list of jobs

    public Employer() { //no arg constructor
    }

    public Employer(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

