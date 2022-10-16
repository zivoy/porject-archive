// thanks https://stackoverflow.com/a/19999868
// insert this into your page to self destruct the page in the case a user is using IE
// <script type="text/javascript" src="https://static.shalit.name/IEDestroy.js"></script>
window.addEventListener("DOMContentLoaded", function() {
    var ua = window.navigator.userAgent;
    var msie = ua.indexOf("MSIE ");

    if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./)){
        // if user is using ie
        document.write("Please do not use IE, as a result of your actions this page has self destructed.")
    }
    else {
        // If another browser
        console.log('Internet Explorer not detected. page extermenation aborted');
    }
}, false);
