function saveConfiguration() {
    // Get a value saved in a form.
   
    var accountName = $('#accountName').val();
    var privateKey = $('#privateKey').val();

    
    // Save it using the Chrome extension storage API.
    chrome.storage.sync.set({'accountName': accountName,'privateKey':privateKey}, function() {
      // Notify that we saved.
      console.log('Configuration saved');
      window.close();
    });
}

$(function(){
  $('#btnSave').click(saveConfiguration);
  chrome.storage.sync.get(['accountName','privateKey'],function(configuration){
      console.log('Configuration loaded');
      $('#accountName').val(configuration.accountName);
      $('#privateKey').val(configuration.privateKey);
  });
})