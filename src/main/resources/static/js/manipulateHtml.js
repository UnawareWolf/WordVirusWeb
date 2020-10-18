function setUpCharacterEntryBox(isRandomInitiallyInfected) {
    if (isRandomInitiallyInfected) {
        var characterEntryBox = document.getElementById('characterEntry');
        characterEntryBox.readOnly = true;
//        radio.style.display = 'none';
    }
    else {
//        document.getElementById('characterEntry').style.display = 'block';
        characterEntryBox.readOnly = false;
    }
}

function setUpCharacterSelectionRadioEventListeners() {
    var characterEntryBox = document.getElementById('characterEntry');

    var infectedRadio = document.getElementById('random');
    infectedRadio.addEventListener('change', function() {
//        characterEntryBox.style.display = 'none';
        characterEntryBox.readOnly = true;
        var selectedCharacter = document.getElementById('characterEntry');
        if (selectedCharacter.value.length == 0) {
            selectedCharacter.value = "e";
        }
    });

    var chooseRadio = document.getElementById('choose');
    chooseRadio.addEventListener('change', function() {
//        characterEntryBox.style.display = 'block';
        characterEntryBox.readOnly = false;
    });
}

function hideTextArea() {
//    var generateButton = document.getElementById('generateButton');
//
//    generateButton.addEventListener('click', function() {
//        var inputArea = document.getElementById('inputArea')
//        if (inputArea.style.display != 'none') {
//            inputArea.style.display = 'none';
//        }
//        else {
//            inputArea.style.display = 'block';
//        }
//    });

//    var inputArea = document.getElementById('inputArea')
//    if (inputArea.style.display != 'none') {
//        inputArea.style.display = 'none';
//    }
//    else {
//        inputArea.style.display = 'block';
//    }

//    var inputArea = document.getElementById('inputArea')
//    if (inputArea.style.opacity != 0) {
//        inputArea.style.opacity = 0;
//    }
//    else {
//        inputArea.style.opacity = 1;
//    }

    var inputArea = document.getElementById('inputArea')
    if (inputArea.style.color == 'black') {
        inputArea.style.color = 'rgba(0, 0, 0, 0)';
    }
    else {
        inputArea.style.color = 'black';
    }
}

function showGeneratedHideVector() {
    document.getElementById('layers').style.color = 'black';
    document.getElementById('inputArea').style.color = 'rgba(0, 0, 0, 0)';
}

function showVectorHideGenerated() {
    document.getElementById('inputArea').style.color = 'black';
    document.getElementById('layers').style.color = 'rgba(0, 0, 0, 0)';
}

function setUpLayerStyles(noOfLayers, fontSize) {
    var inputAreaTop = document.getElementById("inputArea").getBoundingClientRect().top
    var styleSheet = document.styleSheets[0];
    for (var i = 0; i < noOfLayers; i++) {
        var layerTop = i*fontSize + inputAreaTop - 56;
        var rule = "#layer" + i + " {top: " + layerTop + "px;}";
        styleSheet.insertRule(rule, 0);
    }
    var divRule = "div {font-size: " + fontSize + "px; line-height: " + fontSize*noOfLayers + "px;}"
    styleSheet.insertRule(divRule, 0);
}

function populateLayers(content) {
    for (i = 0; i < content.length; i++) {
        var division = document.createElement('div');
        division.id = "layer" + i;

//        layerContent = "<a>";

        if (content[i].length > 0) {
            division.innerHTML = content[i];
//            layerContent += content[i];
        }
//        layerContent += "</a>";
//        division.innerHTML = layerContent;
        document.getElementById("layers").appendChild(division);
    }
}

function getMiniFontSize(fontSize, minFontSize) {
    smallFontSize = fontSize;
    if (fontSize < minFontSize) {
        smallFontSize = minFontSize;
    }
    var remainder = smallFontSize % minFontSize;
    smallFontSize = (smallFontSize - remainder) / minFontSize;
    return Math.round(smallFontSize);
}


var observe;
if (window.attachEvent) {
    observe = function (element, event, handler) {
        element.attachEvent('on'+event, handler);
    };
}
else {
    observe = function (element, event, handler) {
        element.addEventListener(event, handler, false);
    };
}

function resizeInputAreaIfNecessary() {
    var inputArea = document.getElementById('inputArea');
    function resize () {
        inputArea.style.height = 'auto';
        inputArea.style.height = inputArea.scrollHeight+'px';
    }
    /* 0-timeout to get the already changed text */
    function delayedResize () {
        window.setTimeout(resize, 0);
    }
    observe(inputArea, 'change',  resize);
    observe(inputArea, 'cut',     delayedResize);
    observe(inputArea, 'paste',   delayedResize);
    observe(inputArea, 'drop',    delayedResize);
    observe(inputArea, 'keydown', delayedResize);

//    inputArea.focus();
//    inputArea.select();
    resize();
//    inputArea.blur();
}