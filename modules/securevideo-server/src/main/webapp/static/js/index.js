'use strict';

// Define a key: hardcoded in this example – this corresponds to the key used for encryption
//var KEY = new Uint8Array([
//    0xeb, 0xdd, 0x62, 0xf1, 0x68, 0x14, 0xd2, 0x7b,
//    0x68, 0xef, 0x12, 0x2a, 0xfc, 0xe4, 0xae, 0x3c
//]);

var KEY = new Uint8Array([
    148,64,221,83,225,205,222,70,1,122,69,184,170,7,145,86
]);

console.log('key='+toBase64(KEY));


// Setup event listeners to log video load algorithm events.
var dumpEvent = function (event) {
    log("EVENT " + event.type + " currentTime=" + event.target.currentTime +
    " paused=" + event.target.paused + " ended=" + event.target.ended +
    " readyState=" + event.target.readyState + "\n");
}
var events = ["loadedmetadata", "stalled"];


var video = document.getElementById('encryptedVideo');

for (var i = 0; i < events.length; ++i) {
    video.addEventListener(events[i], dumpEvent, false);
}

video.addEventListener('encrypted', handleEncrypted, false);

function createSession(initDataType, initData) {
    var keySession = video.mediaKeys.createSession();
    keySession.generateRequest(initDataType, initData).then(
        function () {
            log("Created keySession= " + keySession + " sessionId=" + keySession.sessionId);
            var onKeyMessage = function (event) {
                log("EVENT keymessage received: " + ArrayBufferToStr(event.message));
                var clearKeyInitDataStr = Uint8ArrayToStr(generateLicense(event.message));
                log("keymessage clear key data=" + clearKeyInitDataStr);
                keySession.update(stringToBytes(clearKeyInitDataStr));
            };
            keySession.addEventListener("message", onKeyMessage, false);
            keySession.addEventListener("error",
                function (err) {
                    log("EVENT error session=" + keySession.sessionId +
                    " systemCode=" + err.systemCode + " err=" + err);
                }, false);
        }, bail("generateRequest failed"));
}

//function createMediaKeys(video, mime) {
//    if (MediaKeys.isTypeSupported) {
//        var supported = MediaKeys.isTypeSupported("org.w3.clearkey", mime);
//        log('MediaKeys.isTypeSupported("org.w3.clearkey", ' + mime + ')= ' + supported);
//        if (!supported) {
//            // Abort the load...
//            video.src = '';
//            return false;
//        }
//    }
//    if (!video.mediaKeys) {
//        if (navigator.requestMediaKeySystemAccess) {
//            var options = [
//                {
//                    initDataType: "cenc",
//                    videoType: "video/mp4"
//                }
//            ];
//            navigator.requestMediaKeySystemAccess("org.w3.clearkey", options)
//                .then(function(keySystemAccess) {
//                    return keySystemAccess.createMediaKeys();
//                }, bail(name + " Failed to request key system access."))
//                .then(function(mediaKeys) {
//                    log(name + " created MediaKeys object ok");
//                    return video.setMediaKeys(mediaKeys);
//                }, bail(name + " failed to create MediaKeys object"))
//        } else {
//            var promise = MediaKeys.create("org.w3.clearkey");
//            promise.catch(function(ex) { log("ERROR: Unable to create MediaKeys ex=" + ex); });
//            promise.then(
//                function(createdMediaKeys) {
//                    log("MediaKeys created, createdMediaKeys=" + createdMediaKeys);
//                    return video.setMediaKeys(createdMediaKeys);
//                }
//            ).catch(function(ex) { log("ERROR: Unable to set MediaKeys ex=" + ex); });
//        }
//    }
//    return true;
//}


var mimes = {
    'webm': 'video/webm; codecs="vp8"',
    'mp4': 'video/mp4; codecs="avc1.42c00d,mp4a.40.2"'
};


// var config = [{
//     initDataTypes: ['webm'],
//     videoCapabilities: [{
//         contentType: 'video/webm; codecs="vp8"'
//     }]
// }];

//var config = [{
//    initDataTypes: ['cenc'],
//    videoCapabilities: [{
//        contentType: 'video/mp4; codecs="avc1.42c00d"'
//    }]
//}];

var options = {
    'mp4': {
        initDataType: "cenc",
        videoType: "video/mp4"
    },
    'webm': [{
        initDataTypes: ['webm'],
        videoCapabilities: [{
            contentType: 'video/webm; codecs="vp8"'
        }]
    }]
};

function handleEncrypted(event) {
    log("EVENT encrypted initDataType=" + event.initDataType + " initData= " + ArrayBufferToStr(event.initData));

    if (MediaKeys.isTypeSupported) {
        var supported = MediaKeys.isTypeSupported("org.w3.clearkey", event.initDataType, mimes[event.initDataType]);
        log('MediaKeys.isTypeSupported("org.w3.clearkey", ' + event.initDataType + ', ' + mimes[event.initDataType] + ')= ' + supported);
        if (!supported) {
            // Abort the load...
            video.src = '';
            return;
        }
    }
    if (!video.mediaKeys) {
        var onMediaKeysSet = function () {
            log("MediaKeys set");
            createSession(event.initDataType, event.initData);
        };

        var onMediaKeysCreated = function (mediaKeys) {
            log("MediaKeys created");
            video.setMediaKeys(mediaKeys).then(onMediaKeysSet, bail("setMediaKeys() failed."));
        };

        if (MediaKeys.create) {
            MediaKeys.create("org.w3.clearkey").then(onMediaKeysCreated, bail("MediaKeys.create() failed."));
        } else if (navigator.requestMediaKeySystemAccess) {
            navigator.requestMediaKeySystemAccess("org.w3.clearkey", options[event.initDataType])
                .then(function (keySystemAccess) {
                    return keySystemAccess.createMediaKeys();
                }, bail(ArrayBufferToStr(event.initData) + " Failed to request key system access."))
                .then(onMediaKeysCreated, bail(ArrayBufferToStr(event.initData) + " failed to create MediaKeys object"))
        }
    } else {
        createSession(event.initDataType, event.initData);
    }
}

//function handleMessage(event) {
//    console.log('message event: ', event);
//    // If you had a license server, you would make an asynchronous XMLHttpRequest
//    // with event.message as the body.  The response from the server, as a
//    // Uint8Array, would then be passed to session.update().
//    // Instead, we will generate the license synchronously on the client, using
//    // the hard-coded KEY at the top.
//    var license = generateLicense(event.message);
//    console.log('license: ', license);
//
//    var session = event.target;
//    session.update(license).catch(
//        function (error) {
//            console.error('Failed to update the session', error);
//        }
//    );
//}

// Convert Uint8Array into base64 using base64url alphabet, without padding.
function toBase64(u8arr) {
    return btoa(String.fromCharCode.apply(null, u8arr)).
        replace(/\+/g, '-').replace(/\//g, '_').replace(/=*$/, '');
}

// This takes the place of a license server.
// kids is an array of base64-encoded key IDs
// keys is an array of base64-encoded keys
function generateLicense(message) {
    // Parse the clearkey license request.
    var request = JSON.parse(new TextDecoder().decode(message));
    // We only know one key, so there should only be one key ID.
    // A real license server could easily serve multiple keys.
    //console.assert(request.kids.length === 1);

    return new TextEncoder().encode(JSON.stringify({
        keys: [key]
    }));
}


function Uint8ArrayToStr(uintArr) {
    var str = "";
    for (var i = 0; i < uintArr.length; i++) {
        str += String.fromCharCode(uintArr[i])
    }
    return str;
}
function ArrayBufferToStr(buffer) {
    return Uint8ArrayToStr(new Uint8Array(buffer));
}

function stringToBytes(str) {
    var length = str.length;
    var bytes = new Uint8Array(length);
    for (var n = 0; n < length; ++n)
        bytes[n] = str.charCodeAt(n) & 0xFF;
    return bytes.buffer;
}

function bail(message) {
    return function (err) {
        log(message + (err ? err : ""));
    }
}

function log(msg) {
    console.log(msg);
}
