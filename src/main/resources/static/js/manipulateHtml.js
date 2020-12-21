function setUpCharacterEntryBox(isRandomInitiallyInfected) {
//    if (isRandomInitiallyInfected) {
//        var characterEntryBox = document.getElementById('characterEntry');
//        characterEntryBox.readOnly = true;
////        radio.style.display = 'none';
//    }
//    else {
////        document.getElementById('characterEntry').style.display = 'block';
//        characterEntryBox.readOnly = false;
//    }
}

function setUpCharacterSelectionRadioEventListeners() {
//    var characterEntryBox = document.getElementById('characterEntry');
//
//    var infectedRadio = document.getElementById('random');
//    infectedRadio.addEventListener('change', function() {
////        characterEntryBox.style.display = 'none';
//        characterEntryBox.readOnly = true;
//        var selectedCharacter = document.getElementById('characterEntry');
//        if (selectedCharacter.value.length == 0) {
//            selectedCharacter.value = "e";
//        }
//    });
//
//    var chooseRadio = document.getElementById('choose');
//    chooseRadio.addEventListener('change', function() {
////        characterEntryBox.style.display = 'block';
//        characterEntryBox.readOnly = false;
//    });
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

function percentageInputOnBlur() {
    percentageInputOnBlur2(this);
//    console.log(this.value);
//    var lastChar = this.value.substr(this.value.length - 1);
//    if (this.value > 100) {
//        this.value = 100;
//    }
//    if (this.value < 0) {
//        this.value = 0;
//    }
//    this.type = 'text';
//    if (lastChar != '%') {
//        this.value = this.value + '%';
//    }
//    this.style.width = 38 + 'px';
}

function percentageInputOnBlur2(input) {
    var lastChar = input.value.substr(input.value.length - 1);
    if (input.value > 100) {
        input.value = 100;
    }
    if (input.value < 0) {
        input.value = 0;
    }
    input.type = 'text';
    if (lastChar != '%') {
        input.value = input.value + '%';
    }
    input.style.width = 38 + 'px';
}

function percentageInputOnFocus() {
    changeInputTypeToNumber(this);
//    var lastChar = this.value.substr(this.value.length - 1);
//    if (lastChar == '%') {
//        this.value = this.value.substr(0, this.value.length - 1);
//    }
//    this.type = 'number';
}

function changeInputTypeToNumber(input) {
    var lastChar = input.value.substr(input.value.length - 1);
    if (lastChar == '%') {
        input.value = input.value.substr(0, input.value.length - 1);
    }
    input.type = 'number';
}

//function getTextWidth(text, font) {
//    // re-use canvas object for better performance
//    var canvas = getTextWidth.canvas || (getTextWidth.canvas = document.createElement("canvas"));
//    var context = canvas.getContext("2d");
//    context.font = font;
//    var metrics = context.measureText(text);
//    return metrics.width;
//}
//
//function resizePercentageInput() {
//    var newWidth = getTextWidth(this.value, this.style.font) + 5;
//    this.style.width = newWidth + 'px';
//    console.log(getTextWidth(this.value, 'Helvetica'));
//}
//
//function autoSizeInput(input) {
//    var newWidth = getTextWidth(input.value, input.style.font) + 5;
//    input.style.width = newWidth + 'px';
//}

function getPercentageInputs() {
    return document.getElementsByClassName("percentageInput");
}

function setUpPercentageInput() {
//    var percentageInputs = document.getElementsByClassName("percentageInput");
    var percentageInputs = getPercentageInputs();
    for (i in percentageInputs) {
        var percentageInput = percentageInputs[i];
        if (percentageInput != null && !(percentageInput === undefined)  && !(percentageInput.style === undefined)) {
//            autoSizeInput(percentageInput);
//            percentageInput.onkeyup = resizePercentageInput;
            percentageInputOnBlur2(percentageInput);
            percentageInput.onfocus = percentageInputOnFocus;
            percentageInput.onblur = percentageInputOnBlur;
        }
    }
}

function ensureInputIsValid(numberInput) {
    if (numberInput.value > 100) {
        numberInput.value = 100;
    }
    else if (numberInput.value < 0) {
        numberInput.value = 0;
    }
//    for (i in numberInputs) {
//        var numberInput = numberInputs[i];
//        if (numberInput.value > 100) {
//            numberInput.value = 100;
//        }
//        else if (numberInput.value < 0) {
//            numberInput.value = 0;
//        }
//    }
}

function setUpSubmitListener() {
    var submitButton = document.getElementById('inputForm');
    submitButton.addEventListener("submit", function() {
        var numberInputs = document.getElementsByClassName('numberInput');
        for (i in numberInputs) {
            ensureInputIsValid(numberInputs[i]);
        }
//        ensureInputIsValid();
//        var percentageInputs = getPercentageInputs();
//        for (i in getPercentageInputs()) {
//            changeInputTypeToNumber(percentageInputs[i]);
//        }
//        console.log('submitting now');
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

function setInputWidths() {

}

//function setUpTableCellSnapping() {
//    var percentageCells = document.getElementsByClassName("percentageCell");
//    for (i in percentageCells) {
//        var cell = percentageCells[i];
//        if (typeof cell.addEventListener === 'function') {
//            cell.addEventListener('click', function() {
//                console.log('clicked');
//                var input = document.getElementById("infectionLowInput");
//                input.focus();
//                var inputVal = input.value;
//                input.value = '';
//                input.value = inputVal;
//            });
//        }
//    }
//}

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