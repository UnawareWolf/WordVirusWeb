# WordVirusWeb

This project simulates the spread of a virus through a body of text entered by the user.

The virus spreads through characters in the text, where each character is formed of a number of nodes.

There are several stages of infection, which will cause the appearance of the node to change.

Once a node has reached the final stage of infection, it "dies" and is no longer visible.

The user chooses the first infected character and other parameters that determine the characteristics of the virus, such as how quickly it will spread and whether it will die out.

The virus has a chance of spreading between nearby nodes of a character, and between adjacent characters.

Each character inherits the infections of the previous occurrence of the character, where any infected nodes have a chance of progressing to the next stage of infection, healing and/or spreading to an adjacent node/character.

# The Code

The backend is written in Java using the Spring Boot framework. The infection logic and Spring Boot code are located here: [backend](https://github.com/UnawareWolf/WordVirusWeb/tree/master/src/main/java/com/unawarewolf/wordvirus).

The front end has a little Javascript to help the character nodes line up properly. This can be found here: [front end](https://github.com/UnawareWolf/WordVirusWeb/blob/master/src/main/resources/static/js/manipulateHtml.js).

The font assets containing the node graphics can be found here: [font](https://github.com/UnawareWolf/WordVirusWeb/tree/master/src/main/resources/static/fonts).
