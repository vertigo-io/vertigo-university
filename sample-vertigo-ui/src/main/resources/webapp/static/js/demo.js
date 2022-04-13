

function getDocHeight(doc) {
    doc = doc || document;
    // stackoverflow.com/questions/...
    var body = doc.body, html = doc.documentElement;
    var height = Math.max( body.scrollHeight, body.offsetHeight, 
    html.clientHeight, html.scrollHeight, html.offsetHeight );
    return height;
}

function setIframeHeight(ifrm) {
    var doc = ifrm.contentDocument? ifrm.contentDocument: 
    ifrm.contentWindow.document;
    ifrm.style.visibility = 'hidden';
    ifrm.style.height = "100px"; // reset to minimal height ...
    // IE opt. for bing/msn needs a bit added or scrollbar appears
    ifrm.style.height = getDocHeight( doc ) + 4 + "px";
    ifrm.style.visibility = 'visible';
}