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

    private VirusGenerator virusGenerator;

    @GetMapping("/")
    public String virusForm(Model model) {
        return resetFormToDefault(model);
    }

    @PostMapping(value="/", params="action=Generate")
    public String generateOutput(@Valid @ModelAttribute("virusGenerator") VirusGenerator virusGenerator,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "configure";
        }

        virusGenerator.generateOutputText();

        this.virusGenerator = virusGenerator;

        model.addAttribute("virusGenerator", virusGenerator);
        return "result";
    }

    @PostMapping(value="/", params="action=Regenerate")
    public String regenerateOutput(Model model) {
        try {
            virusGenerator.generateOutputText();
        }
        catch (Exception e) {
            return resetFormToDefault(model);
        }

        model.addAttribute("virusGenerator", virusGenerator);
        return "result";
    }

    @PostMapping(value="/", params="action=Reset")
    public String resetForm(Model model) {
        return resetFormToDefault(model);
    }

    @PostMapping(value="/", params="action=Back")
    public String backToForm(Model model) {
        try{
            model.addAttribute("virusGenerator", virusGenerator);
        }
        catch (Exception e) {
            return resetFormToDefault(model);
        }
        return "configure";
    }

    private String resetFormToDefault(Model model) {
        virusGenerator = new VirusGenerator();
        model.addAttribute("virusGenerator", virusGenerator);
        return "configure";
    }

}
