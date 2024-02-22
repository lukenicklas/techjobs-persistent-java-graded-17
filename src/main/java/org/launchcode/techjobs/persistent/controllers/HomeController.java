package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.Job;
import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.launchcode.techjobs.persistent.models.data.JobRepository;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

/**
 * Created by LaunchCode
 */
@Controller
public class HomeController {

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private SkillRepository skillRepository;

    @RequestMapping("/")
    public String index(Model model) {

        model.addAttribute("title", "MyJobs");

        List<Job> jobs = (List<Job>) jobRepository.findAll();
        model.addAttribute("jobs", jobs );

        return "index";
    }

    @GetMapping("add")//request mapping to add
    public String displayAddJobForm(Model model) {
	    model.addAttribute("title", "Add Job");
        model.addAttribute(new Job());//add new instance of job
        model.addAttribute("employers", employerRepository.findAll()); //adds all employers to the model


        List<Skill> skills = (List<Skill>) skillRepository.findAll();//retrieves skills list from database
        model.addAttribute("skills", skills);//allows user to select multiple skills

        return "add";
    }

    @PostMapping("add")
    public String processAddJobForm(@ModelAttribute @Valid Job newJob, //indicating newJob should be retrieved from the model
                                       Errors errors, Model model, @RequestParam int employerId, @RequestParam List<Integer> skills) {
//errors holds any validation errors, model allows us to pass data to the view, @RequestParam allows us to retrieve data, and list is list of skill ids
        if (errors.hasErrors()) {
	        model.addAttribute("title", "Add Job");
            model.addAttribute(new Job());
            model.addAttribute("employers", employerRepository.findAll());
            return "add";
        }
        Optional<Employer> result = employerRepository.findById(employerId); //retrieves the employer from the database
        if (result.isPresent()) {
            Employer employer = result.get(); //retrieves the employer object from the optional container
            newJob.setEmployer(employer); //sets employer for the new job

        }
        List<Skill> skillObjs = (List<Skill>) skillRepository.findAllById(skills);//retrieves the list of skills from the database
        newJob.setSkills(skillObjs);//sets skills for the new job

        jobRepository.save(newJob);//saves the new job to the database

        return "redirect:";
    }

    @GetMapping("view/{jobId}")
    public String displayViewJob(Model model, @PathVariable int jobId) {

            return "view";
    }

}
