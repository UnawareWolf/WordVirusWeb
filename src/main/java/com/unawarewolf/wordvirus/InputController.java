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
    private VirusGenerator virusGenerator;

    @GetMapping("/")
    public String virusForm(Model model) {
        return displayForm(model, true);
    }

    @PostMapping(value="/", params="action=Generate")
    public String generateButtonPress(@Valid @ModelAttribute("inputConfiguration") InputConfiguration inputConfiguration,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "configure";
        }
        this.inputConfiguration = inputConfiguration;

        return generateAndDisplayOutput(model);
    }

    @PostMapping(value="/", params="action=Regenerate")
    public String regenerateButtonPress(Model model) {
        return inputConfiguration != null ? generateAndDisplayOutput(model) : displayForm(model, true);
    }

    @PostMapping(value="/", params="action=Reset")
    public String resetButtonPress(Model model) {
        return displayForm(model, true);
    }

    @PostMapping(value="/", params="action=Back")
    public String backButtonPress(Model model) {
        return displayForm(model, inputConfiguration == null);
    }

    private String generateAndDisplayOutput(Model model) {
        virusGenerator = virusGenerator == null ? new VirusGenerator(inputConfiguration) : virusGenerator;
        virusGenerator.setInputConfiguration(inputConfiguration);
        inputConfiguration.randomiseInitiallyInfectedIfRandomSelected();
        inputConfiguration.setOutput(virusGenerator.generateOutputText());
        model.addAttribute("inputConfiguration", inputConfiguration);
        return "result";
    }

    private String displayForm(Model model, boolean reset) {
        inputConfiguration = reset ? new InputConfiguration() : inputConfiguration;
        model.addAttribute("inputConfiguration", inputConfiguration);
        return "configure";
    }

}
