// thanks https://stackoverflow.com/a/19999868
var ua = window.navigator.userAgent;
var msie = ua.indexOf("MSIE ");

if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./)){
    var message = "<p>please do not use IE, you are using version"+ ua.substring(msie + 5, ua.indexOf(".", msie))+"</p>";
    // document.body.innerHTML = message
    document.write(message)
}
else  // If another browser
{
    console.log('Internet Explorer not detected. page extermenation aborted');
}
