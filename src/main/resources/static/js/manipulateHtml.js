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
    return fontSize;
}