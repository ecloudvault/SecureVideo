function log(msg) {
    console.log(msg);
}

function debug(msg) {
    if (true) {
        console.debug(msg);
    }
}
var serverAddress = "https://securevideo.lan"
// var serverAddress = "https://securevideo.lan:8443"

chrome.runtime.onMessage.addListener(function (msg, _, sendResponse) {
    if (msg.action === 'getVideoEncryptionKey') {
        log('getVideoEncryptionKey');
        log('secureVideoId :' + msg.secureVideoId);

        chrome.storage.sync.get(['accountName', 'privateKey'], function (configuration) {
            console.log('Load configuration');
            debug('accountName:\n' + configuration.accountName);
            debug('privateKey:\n' + configuration.privateKey);
            var rsaPrivateKey = new RSAKey();
             rsaPrivateKey = KEYUTIL.getKey(configuration.privateKey);
            var content = Math.random().toString(36).substr(1);
            var sigValueHex = rsaPrivateKey.signString(content, 'sha1');
            debug('sign result value: \n'+sigValueHex);
            $.ajax({
                type: 'POST',
                url: serverAddress + '/api/v1/videokey',
                data:{videoId:msg.secureVideoId,username:configuration.accountName,content:content,signValue:sigValueHex},
                success: function (resp) {
                    debug("server encrypted value: \n"+resp);
                    var decrypt = new JSEncrypt();
                    decrypt.setPrivateKey(configuration.privateKey);
                    var uncrypted = decrypt.decrypt(resp);

                    debug("uncrypted value :\n"+uncrypted);

                    sendResponse({key: uncrypted});
                }
            });

        });


        return true;
    }

    // If we don't return anything, the message channel will close, regardless
    // of whether we called sendResponse.
});