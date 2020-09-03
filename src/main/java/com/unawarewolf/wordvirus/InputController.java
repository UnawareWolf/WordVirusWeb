package com.unawarewolf.wordvirus;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class InputController {

    @GetMapping("/")
    public String greetingForm(Model model) {
        InputConfiguration inputConfiguration = new InputConfiguration();
        inputConfiguration.setInfectionLow(0.05);
        inputConfiguration.setInfectionHigh(0.15);
        inputConfiguration.setProgressionLow(0.1);
        inputConfiguration.setProgressionHigh(0.1);
        inputConfiguration.setRecoveryLow(0.2);
        inputConfiguration.setRecoveryHigh(0.4);
        inputConfiguration.setInput(GridMain.getFileAsString("templates/defaultText.txt"));
        inputConfiguration.setFontSize(48);
        model.addAttribute("greeting", inputConfiguration);
        return "greeting";
    }

    @PostMapping("/")
    public String greetingSubmit(@ModelAttribute InputConfiguration inputConfiguration, Model model) {
        char[] inputCharArray = inputConfiguration.getInput().toCharArray();

        List<GridVirusCharacter> virusCharacters = GridMain.createVirusCharacterList(inputCharArray, inputConfiguration);

        String[] outputText = GridMain.formatOutputContentFont(virusCharacters);

        inputConfiguration.setOutput(outputText);

        model.addAttribute("greeting", inputConfiguration);
        return "result";
    }
}
