var wsUri = "ws://" + document.location.host + document.location.pathname + "websocket";
console.log("Connecting to " + wsUri);
var websocket = new WebSocket(wsUri);
websocket.onopen = function (evt) {
    onOpen(evt)
};
websocket.onmessage = function (evt) {
    onMessage(evt)
};
websocket.onerror = function (evt) {
    onError(evt)
};

var wsStartTime;
var wsEndTime;
var payload = "";

function echoText() {
    wsEchoText();
}

function wsEchoText() {
    payload = "";
    for (var i = 0; i < size.value; i++) {
        payload += "x";
    }
    wsStartTime = new Date().getTime();
    for (var i = 0; i < times.value; i++) {
        websocket.send(payload);
    }
}


function onOpen() {
    console.log("Connected to " + wsUri);
}

function onMessage(evt) {
    //if (evt.data == payload) {
    console.log("On message: " + evt.data + "; payload: " + payload);
    wsEndTime = new Date().getTime();
    console.log("Total execution time: " + (wsEndTime - wsStartTime) + " ms");
    //}
}

function onError(evt) {
    console.log("Error: " + evt.data);
}
