function log(msg){
	console.log(msg);
}

function debug(msg){
	log(msg);
}

log('SecureVideo chrome plugin loaded');

var port = chrome.runtime.connect();

window.addEventListener("message", function(event) {
  // We only accept messages from ourselves

}, false);

var keys = {

};

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

var videos = document.getElementsByTagName('video');

for(var i=0; i<videos.length;i++){
	var video = videos[i];
	var secureVideoId = video.attributes['secure-video'].value;
	log('secureVideoId='+secureVideoId);
	if(secureVideoId && secureVideoId.length>0){
		chrome.runtime.sendMessage({secureVideoId: secureVideoId,action:'getVideoEncryptionKey'}, function(response) {
	    	debug("get key is: " + response.key);
	    	keys[secureVideoId]=response.key;
	    	video.addEventListener('encrypted', handleEncrypted, false);
	    	video.load();
	    	video.play();
  		});
	}	
}

function createSession(video, initDataType, initData) {
    var keySession = video.mediaKeys.createSession();
    keySession.generateRequest(initDataType, initData).then(
        function () {
            debug("Created keySession= " + keySession + " sessionId=" + keySession.sessionId);
            var onKeyMessage = function (event) {
            	var messageStr = ArrayBufferToStr(event.message);
                debug("EVENT keymessage received: " + messageStr);
                var messageObj = JSON.parse(messageStr);

                var secureVideoId = video.attributes['secure-video'].value;
				var key= {
                    kty: 'oct',
                    alg: 'A128KW',
                    kid: messageObj.kids[0],
                    k: keys[secureVideoId]
                };

                var clearKeyInitDataStr = JSON.stringify({keys:[key]});				

                debug("keymessage clear key data=" + clearKeyInitDataStr);
                keySession.update(stringToBytes(clearKeyInitDataStr));
            };
            keySession.addEventListener("message", onKeyMessage, false);
            keySession.addEventListener("error",
                function (err) {
                    debug("EVENT error session=" + keySession.sessionId +
                    " systemCode=" + err.systemCode + " err=" + err);
                }, false);
        }, bail("generateRequest failed"));
}

function handleEncrypted(event) {
	var video = event.target;
    debug("EVENT encrypted initDataType=" + event.initDataType + " initData=" + ArrayBufferToStr(event.initData));

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
            debug("MediaKeys set");
            createSession(video, event.initDataType, event.initData);
        };

        var onMediaKeysCreated = function (mediaKeys) {
            debug("MediaKeys created");
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

function stringToBytes(str) {
    var length = str.length;
    var bytes = new Uint8Array(length);
    for (var n = 0; n < length; ++n)
        bytes[n] = str.charCodeAt(n) & 0xFF;
    return bytes.buffer;
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

function bail(message) {
    return function (err) {
        log(message + (err ? err : ""));
    }
}