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

function removeSubmitButtonPadding() {
    var hoverSelectCells = document.getElementsByClassName('hoverSelect');
    for (i in hoverSelectCells) {
        var cell = hoverSelectCells[i];
        if (!(cell.style === undefined)) {
            cell.style.padding = '0px';
        }
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

function setLayerOffset(fontSize) {
    var layerElement = document.getElementById('layers');
    layerElement.style.top = - (45 * fontSize / 120) + 'px';
}

function roundElementValue(element, roundTo) {
    if (Number.isInteger(element.valueAsNumber)) {
        if (element.value >= roundTo) {
            element.value = Math.round(element.value / roundTo) * roundTo;
        }
        else {
            element.value = roundTo;
        }
    }
}

function setUpFontSizeRounding() {
    var fontSizeElement = document.getElementById('fontSizeElement');
    fontSizeElement.onblur = function() {
        roundElementValue(this, 12);
    };
    fontSizeElement.oninvalid = function() {
        roundElementValue(this, 12);
    };
}

function setUpLayerStyles(noOfLayers, fontSize) {
    setLayerOffset(fontSize);
    var inputAreaTop = document.getElementById("inputArea").getBoundingClientRect().top;
    var styleSheet = document.styleSheets[0];
    for (var i = 0; i < noOfLayers; i++) {
        var layerTop = i*fontSize;
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

function unifySpacing(miniFontSize) {
    var uniformSpaceStyle = miniFontSize + 'px';
    document.getElementById('body').style.padding = uniformSpaceStyle;
    document.getElementById('header').style.marginBottom = uniformSpaceStyle;
    document.getElementById('inputArea').style.padding = uniformSpaceStyle;

    var parameterTables = document.getElementsByClassName('parameters');
    var maxTableWidth = 0;
    var maxTableHeight = 0;
    for (i in parameterTables) {
        if (!(parameterTables[i].style === undefined)) {
            var table = parameterTables[i];
            table.style.marginBottom = uniformSpaceStyle;
            table.style.marginRight = uniformSpaceStyle;
            var tableWidth = getWidth(table);
            if (tableWidth > maxTableWidth) {
                maxTableWidth = tableWidth;
            }
            tableHeight = getHeight(table);
            if (tableHeight > maxTableHeight) {
                maxTableHeight = tableHeight;
            }
        }
    }
    maxTableWidth = Math.ceil(maxTableWidth / (2 * miniFontSize)) * 2 * miniFontSize;
    maxTableHeight = Math.ceil(maxTableHeight / miniFontSize) * miniFontSize;
    var smallTableWidth = maxTableWidth / 2;
    for (i in parameterTables) {
        if (!(parameterTables[i].style === undefined)) {
            var table = parameterTables[i];
            var tableWidth = getWidth(table);
            if (tableWidth < smallTableWidth) {
                table.style.width = smallTableWidth + 'px';
            }
            else {
                table.style.width = maxTableWidth + 'px';
            }
            table.style.height = maxTableHeight + 'px';
        }
    }
}

function getWidth(tableElement) {
    return parseInt(window.getComputedStyle(tableElement, null).getPropertyValue('width').replace('px', ''));
}

function getHeight(tableElement) {
    return parseInt(window.getComputedStyle(tableElement, null).getPropertyValue('height').replace('px', ''));
}

var observe;
if (window.attachEvent) {
    observe = function(element, event, handler) {
        element.attachEvent('on'+event, handler);
    };
}
else {
    observe = function(element, event, handler) {
        element.addEventListener(event, handler, false);
    };
}
function resizeInputAreaIfNecessary() {
    var text = document.getElementById('inputArea');
    function resize () {
        var clone = text.cloneNode();
        clone.className = 'clone';
        text.parentNode.insertBefore(clone, text);
        clone.style.height = 'auto';
        clone.value = text.value;
        text.style.height = (clone.scrollTop + clone.scrollHeight + 20) + 'px';
        text.parentNode.removeChild(clone);
    }
    /* 0-timeout to get the already changed text */
    function delayedResize () {
        window.setTimeout(resize, 0);
    }
    observe(text, 'change',  resize);
    observe(text, 'cut',     delayedResize);
    observe(text, 'paste',   delayedResize);
    observe(text, 'drop',    delayedResize);
    observe(text, 'keydown', delayedResize);

//    text.focus();
//    text.select();
    resize();
}