package org.launchcode.techjobs.persistent.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Skill extends AbstractEntity {

    @NotBlank(message = "Description required")
    @Size(min = 3, max = 300, message = "Description must be between 3 and 300 characters")
    private String description;


    //adding a jobs field
    @ManyToMany(mappedBy = "skills") //foreign key name in database
    private final List<Job> jobs = new ArrayList<>(); //initializes list of jobs


    public Skill() { //no arg constructor
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Job> getJobs() {
        return jobs;
    }

}
