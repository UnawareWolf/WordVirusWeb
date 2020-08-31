package com.unawarewolf.wordvirus;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class GreetingController {

    @GetMapping("/greeting")
    public String greetingForm(Model model) {
        Greeting greeting = new Greeting();
        greeting.setInfectionLow(0.05);
        greeting.setInfectionHigh(0.15);
        greeting.setProgressionLow(0.1);
        greeting.setProgressionHigh(0.1);
        greeting.setRecoveryLow(0.2);
        greeting.setRecoveryHigh(0.4);
        greeting.setInput(GridMain.getFileAsString("templates/defaultText.txt"));
        greeting.setFontSize(48);
        model.addAttribute("greeting", greeting);
        return "greeting";
    }

    @PostMapping("/greeting")
    public String greetingSubmit(@ModelAttribute Greeting greeting, Model model) {
        char[] inputCharArray = greeting.getInput().toCharArray();

        List<GridVirusCharacter> virusCharacters = GridMain.createVirusCharacterList(inputCharArray, greeting);

        String[] outputText = GridMain.formatOutputContentFont(virusCharacters);

        greeting.setOutput(outputText);

        model.addAttribute("greeting", greeting);
        return "result";
    }
}
