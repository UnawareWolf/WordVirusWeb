function ensureInputIsValid(numberInput) {
    if (numberInput.value > 100) {
        numberInput.value = 100;
    }
    else if (numberInput.value < 0) {
        numberInput.value = 0;
    }
}

function setUpSubmitListener() {
    var submitButton = document.getElementById('inputForm');
    submitButton.addEventListener("submit", function() {
        var numberInputs = document.getElementsByClassName('numberInput');
        for (i in numberInputs) {
            ensureInputIsValid(numberInputs[i]);
        }
        return true;
    }, false);
}

function showNumberInputs(textInput) {
    numberInputId = textInput.id.replace('Text', 'Number');
    var numberInput = document.getElementById(numberInputId);
    textInput.blur();
    textInput.style.display = 'none';
    numberInput.style.display = 'block';
    numberInput.focus();
    numberInput.select();
}

function showTextInputs(numberInput) {
    numberInput.style.display = 'none';
    var textInputId = numberInput.id.replace('Number', 'Text');
    var textInput = document.getElementById(textInputId);
    var lastChar = numberInput.value.substr(numberInput.value.length - 1);
    if (lastChar != '%') {
        textInput.value = numberInput.value + '%';
    }
    textInput.style.display = 'block';
}

function setUpHiddenInputs() {
    var numberInputs = document.getElementsByClassName('numberInput');
    for (i in numberInputs) {
        var numberInput = numberInputs[i];
        if (numberInput.style === undefined) {
            continue;
        }
        showTextInputs(numberInput);
        numberInput.onblur = function() {
            ensureInputIsValid(this);
            showTextInputs(this);
        };
    }
    var percentageInputs = document.getElementsByClassName('percentageText');
    for (i in percentageInputs) {
        var percentageInput = percentageInputs[i];
        percentageInput.onfocus = function() {
            showNumberInputs(this);
        };
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
        var layerTop = i*fontSize + inputAreaTop - 55;
        var rule = "#layer" + i + " {top: " + layerTop + "px;}";
        styleSheet.insertRule(rule, 0);
    }
    var divRule = "virusIcons {font-size: " + fontSize + "px; line-height: " + fontSize*noOfLayers + "px;}"
    styleSheet.insertRule(divRule, 0);
}

function populateLayers(content) {
    for (i = 0; i < content.length; i++) {
        var division = document.createElement('virusIcons');
        division.id = "layer" + i;
//        division.className = "virusIcons";

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