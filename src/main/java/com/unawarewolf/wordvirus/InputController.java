package com.unawarewolf.wordvirus;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class InputController {

    private InputConfiguration inputConfiguration;

    @GetMapping("/")
    public String virusForm(Model model) {
        return resetFormToDefault(model);
    }

    @PostMapping(value="/", params="action=Generate")
    public String generateOutput(@Valid @ModelAttribute("inputConfiguration") InputConfiguration inputConfiguration,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "configure";
        }

        inputConfiguration.generateOutputText();

        this.inputConfiguration = inputConfiguration;

        model.addAttribute("inputConfiguration", inputConfiguration);
        return "result";
    }

    @PostMapping(value="/", params="action=Regenerate")
    public String regenerateOutput(Model model) {
        try {
            inputConfiguration.generateOutputText();
        }
        catch (Exception e) {
            return resetFormToDefault(model);
        }

        model.addAttribute("inputConfiguration", inputConfiguration);
        return "result";
    }

    @PostMapping(value="/", params="action=Reset")
    public String resetForm(Model model) {
        return resetFormToDefault(model);
    }

    @PostMapping(value="/", params="action=Back")
    public String backToForm(Model model) {
        try{
            model.addAttribute("inputConfiguration", inputConfiguration);
        }
        catch (Exception e) {
            return resetFormToDefault(model);
        }
        return "configure";
    }

    private String resetFormToDefault(Model model) {
        inputConfiguration = new InputConfiguration();
        model.addAttribute("inputConfiguration", inputConfiguration);
        return "configure";
    }

}
