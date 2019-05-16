function _chatbotWrapper() {
	var _botBaseUrl = "./";
	
	var _initParam = null;
	var _urlType;
	var _button = null;
	var _iframe = null;
	
	function _isUrlActive() {
		return _urlType !== "DISABLE";
	}
	
	function _resetIfNeeded() {
		if (_button) {
			document.body.removeChild(_button);
			_button = null;
			
			if (_iframe) {
				document.body.removeChild(_iframe);
				_iframe = null;
			}
		}
	}
	
	function _createFlottingButton() {		
		_button = document.createElement('div');
		
		_button.style.cssText = '\
			position: fixed;\
			display: flex;\
			align-items: center;\
			bottom: 50vh;\
			right: -100px;\
			width: 145px;\
			height: 45px;\
			transition: right 0.3s ease 0s;\
			background-color: #027be3;\
			color: white;\
			border: solid 1px black;\
			border-radius: 10px 0 0 10px;\
			cursor: pointer;\
			overflow: hidden;\
			-webkit-user-select: none;\
			-ms-user-select: none;\
			-moz-user-select: none;\
			user-select: none;\
			font-family: Verdana, Arial, Helvetica, sans-serif;\
		';
		
		_button.addEventListener("click", Chatbot.show);
		_button.addEventListener("mouseover", _buttonOver);
		_button.addEventListener("mouseout", _buttonOut);
		
		var img = document.createElement('img');
		img.style.cssText = '\
			width: 45px;\
			height: 45px;\
			background-color: white;\
			color: black;\
		';
		img.src = _botBaseUrl + "images/avatar/avatar_bar.png";
		img.alt = "Command Center Bot";
		_button.appendChild(img);
		
		var text = document.createElement('div');
		text.style.cssText = '\
			flex-grow: 1;\
			text-align: center;\
		';
		text.textContent = "Command Center Bot";
		_button.appendChild(text);
		
		document.body.appendChild(_button);
	}
	
	function _buttonOver() {
		_button.style.right = "0px";
	}
	
	function _buttonOut() {
		_button.style.right = "-100px";
	}
	
	function _createIframe() {
		_iframe = document.createElement('iframe');
		
		_iframe.style.cssText = '\
			position: fixed;\
			bottom: 0px;\
			right: 5px;\
			max-width: 450px;\
			min-width: 300px;\
			width: 80%;\
			max-height: 600px;\
			min-height: 400px;\
			height: 95%;\
			box-shadow: 0px 0px 15px 5px grey;\
			background-color: white;\
			border: solid 1px black;\
			border-radius: 10px 10px 0 0;\
		';
		
		_iframe.src = _botBaseUrl + "index.html?url_type=" + _urlType;
		if (_initParam.siret) _iframe.src += "&siret=" + _initParam.siret + "&siret_confirm=true";

		document.body.appendChild(_iframe);
	}
	
	function _getUrlType(url) {
		var mapping = {
			"ALL": ["."]
		}

		for (var prop in mapping) {
			var urlList = mapping[prop];
			for (var i = 0; i < urlList.length; i++) {
				var curUrl = urlList[i];
				if (url.indexOf(curUrl) !== -1) {
					return prop;
				}
			}
		}
		
		// return "GROUND"; // valeur par défaut
		return "DISABLE";
	}
	
	function _initIframeListener() {
		window.addEventListener("message", function(event) {
			if (event.data === "Chatbot.minimize") Chatbot.minimize();
			else if (event.data === "Chatbot.close") Chatbot.close();
		}, false);
	}

	this.Chatbot = {
		init : function(param) {
			_urlType = _getUrlType(param.url);
			if (param.botBaseUrl) {
				_botBaseUrl = param.botBaseUrl;
			}
			
			_resetIfNeeded();
			
			if (_isUrlActive()) {
				_initParam = param;
				_createFlottingButton();
				
				_initIframeListener();
			}
		},
		
		show : function() {
			if (_iframe === null) {
				_createIframe();
			} else {
				_iframe.style.visibility = "visible";
			}
		},
		
		minimize : function() {
			_iframe.style.visibility = "hidden";
		},
		
		close : function() {
			document.body.removeChild(_iframe);
			_iframe = null;
		}
	}
}

_chatbotWrapper();
