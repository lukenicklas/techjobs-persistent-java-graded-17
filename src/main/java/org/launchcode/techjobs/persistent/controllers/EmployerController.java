package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("employers")
public class EmployerController {

    @Autowired //injects employer repository
    private EmployerRepository employerRepository; //created employer repository field

    @GetMapping("/")
    public String index(Model model) { //creates index method to display all employers
        model.addAttribute("title", "All Employers");
        model.addAttribute("employers", employerRepository.findAll());
        return "employers/index"; //returns index.html
    }

    @GetMapping("add")
    public String displayAddEmployerForm(Model model) {
        model.addAttribute(new Employer());
        return "employers/add";//returns add.html
    }

    @PostMapping("add") //maps post request to add
    public String processAddEmployerForm(@ModelAttribute @Valid Employer newEmployer,
                                    Errors errors, Model model) {

        if (errors.hasErrors()) { //checks if errors exist
            return "employers/add";
        }
        employerRepository.save(newEmployer); //saves new employer to database
        return "redirect:";//redirects to index
    }

    @GetMapping("view/{employerId}") //maps get request to view
    public String displayViewEmployer(Model model, @PathVariable int employerId) {
    //creates displayViewEmployer method passing data to view and int employerId to extract the ID from the URL
        Optional<Employer> optEmployer = employerRepository.findById(employerId);//retrieves employer & checks if it exists
        if (optEmployer.isPresent()) {
            Employer employer = (Employer) optEmployer.get();//if exists, gets the employer
            model.addAttribute("employer", employer); //adds the employer to the model
            return "employers/view";//returns view
        } else {
            return "redirect:../";//redirects to index
        }

    }
}
