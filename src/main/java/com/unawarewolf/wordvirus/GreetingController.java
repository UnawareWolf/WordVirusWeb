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
        greeting.setInput("Lorem ipsum dolor sit amet consectetur adipiscing elit. Nunc eu arcu in nisi malesuada molestie ac sit amet nisl. Suspendisse potenti. Nunc a felis pharetra tincidunt est et efficitur dui. Cras quis condimentum odio. Maecenas tempus lobortis tellus et laoreet. Aenean in interdum nibh. Vestibulum nec turpis gravida purus vulputate condimentum. Integer malesuada faucibus enim placerat volutpat. Fusce convallis sapien in nulla sollicitudin mollis.");
        model.addAttribute("greeting", greeting);
        return "greeting";
    }

    @PostMapping("/greeting")
    public String greetingSubmit(@ModelAttribute Greeting greeting, Model model) {
        char[] inputCharArray = greeting.getInput().toCharArray();

        List<GridVirusCharacter> virusCharacters = GridMain.createVirusCharacterList(inputCharArray, greeting);

        String[] outputText = GridMain.formatOutputContentFont(virusCharacters);

//        String content = "";
//        for (String line : outputText) {
//            content += line;
//        }
        greeting.setOutput(outputText);

        model.addAttribute("greeting", greeting);
        return "result";
    }
}
