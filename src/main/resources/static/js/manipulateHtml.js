function setUpCharacterEntryBox(isRandomInitiallyInfected) {
    if (isRandomInitiallyInfected) {
        var radio = document.getElementById('characterEntry');
        radio.style.display = 'none';
    }
    else {
        document.getElementById('characterEntry').style.display = 'block';
    }
}

function setUpCharacterSelectionRadioEventListeners() {
    var characterEntryBox = document.getElementById('characterEntry');

    var infectedRadio = document.getElementById('random');
    infectedRadio.addEventListener('change', function() {
        characterEntryBox.style.display = 'none';
        var selectedCharacter = document.getElementById('characterEntry');
        if (selectedCharacter.value.length == 0) {
            selectedCharacter.value = "e";
        }
    });

    var chooseRadio = document.getElementById('choose');
    chooseRadio.addEventListener('change', function() {
        characterEntryBox.style.display = 'block';
    });
}

function setUpLayerStyles(noOfLayers, fontSize) {
    for (var i = 0; i < noOfLayers; i++) {
        var layerTop = 35 + i*fontSize;
        var rule = "#layer" + i + " {top: " + layerTop + "px;}";
        var styleSheet = document.styleSheets[0];
        styleSheet.insertRule(rule, 0);
    }
    var divRule = "div {font-size: " + fontSize + "px; line-height: " + fontSize*noOfLayers + "px;}"
    styleSheet.insertRule(divRule, 0);
}

function populateLayers(content) {
    for (i = 0; i < content.length; i++) {
        var division = document.createElement('div');
        division.id = "layer" + i;
        if (content[i].length > 0) {
            division.innerText = content[i];
        }
        document.getElementById("layers").appendChild(division);
    }
}

function getMiniFontSize(fontSize, minFontSize) {
    if (fontSize < minFontSize) {
        fontSize = minFontSize;
    }
    var remainder = fontSize % minFontSize;
    fontSize = (fontSize - remainder) / minFontSize;
    return Math.round(fontSize);
}