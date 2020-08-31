
function setUpLayerStyles(noOfLayers, fontSize) {

    for (var i = 0; i < noOfLayers; i++) {
        var layerStyle = document.createElement('style');
        var layerTop = 10 + i*fontSize;
        var rule = "#layer" + i + " {top: " + layerTop + "px;}";
//        layerStyle.innerHTML = "#layer" + i + " {top: " + layerTop + "px;}";


        var styleSheet = document.styleSheets[0];
        styleSheet.insertRule(rule, 0);
//        divStyle.line-height = fontSize * noOfLayers + "px";
//        document.styleSheets[0].insertRule(rule, 0);

//        document.body.appendChild(layerStyle);
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

function getMiniFontSize(fontSize) {
    if (fontSize < 12) {
        fontSize = 12;
    }
    var remainder = fontSize % 12;
    fontSize = (fontSize - remainder) / 12;
    return fontSize;
}