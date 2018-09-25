/********************************************************
 * Méthodes d'affichage et de gestion des Popins        *
 ********************************************************/

	function closePopin() {
		hideModal('#popinGeneric');
	}

	function showPopin(popinaction, popinurlparamnames, popinurlparamvalues) {
		var url = buildPopinUrl(popinaction, popinurlparamnames, popinurlparamvalues);
		$("#popinIframe").attr('src', url);
		$('#popinIframe').on('load', function(){
			$('#popinIframe').css('height',$('#popinIframe').contents().find('.modalWrapper').height()+'px');
		});
		showModal('#popinGeneric');
		jQuery.cookie('popin',null);
		jQuery.cookie('popin',true,{ expires:7, path: '/'});
		
	};
	
	function buildPopinUrl(popinaction, popinurlparamnames, popinurlparamvalues) {
		var url = popinaction.replace(/&amp;/gi, "&");
		var i = 0;
		var paramNames = [];
		var paramValues = [];
		if (popinurlparamnames != undefined) {
			paramNames = popinurlparamnames.split(',');
		}
		if (popinurlparamvalues != undefined) {
			paramValues = popinurlparamvalues.split(',');
		}
		var urlParams = "";
		var firstUrlParam = true;
		for (i = 0; i < paramNames.length ; i++) {
			var paramName = paramNames[i];
			var paramValue = paramValues[i];
			if (paramValue != undefined && paramValue != null && paramValue != "") {
				if (firstUrlParam) {
					firstUrlParam = false;
					urlParams += '?'
				} else {
					urlParams += '&'
				}
				urlParams += paramName + '=' + paramValue;
			}
		}
		url += urlParams;
		return url;
	}
	
	function showModal(modalId) {
		$(modalId + " .fermer").click(function() {
			$("#popin-overlay-generic").hide();
			$(modalId).hide();
		});
		
		$("#popin-overlay-generic").show();
		$(modalId).show();
	}

	function hideModal(modalId) {
		$("#popin-overlay-generic").hide();
		$(modalId).hide();
	}

	function confirmPopin(message) {
		currentElement = window.event;
		currentElement = currentElement.target || currentElement.srcElement;
		return confirmAction(currentElement, message);
	}

	var isConfirmed = false;
	function confirmAction(action, message) {
		
		if (isConfirmed) {
			isConfirmed = false;
			return true;
		}
		
		$("#popin .valider").click(function() {
			isConfirmed = true;
			$("#popin").removeClass("confirm");
			$("#popin").modal('hide');
			$("#popin-overlay").hide();
			$(action).click();
		});
		$("#popin .fermer").click(function() {
			isConfirmed = false;
			$("#popin-overlay").hide();
			$("#popin").removeClass("confirm");
			$("#popin").modal('hide');
		});
		
		$("#popin").addClass("confirm");
		$("#popin .modal-body").text(message);
		$("#popin-overlay").show();
		$("#popin").modal('show');
		
		return false;
	}

	function confirmLink(link, message) {
		
		if (isConfirmed) {
			isConfirmed = false;
			return true;
		}	
		
		$("#popin .valider").click(function() {
			isConfirmed = true;
			$("#popin").removeClass("confirm");
			$("#popin").modal('hide');
			document.location.href= $(link).attr("href");
			$("#popin-overlay").hide();
		});
		
		$("#popin .fermer").click(function() {
			isConfirmed = false;
			$("#popin-overlay").hide();
			$("#popin").removeClass("confirm");
			$("#popin").modal('hide');
		});
		
		$("#popin").addClass("confirm");
		$("#popin .modal-body").text(message);
		$("#popin-overlay").show();
		$("#popin").modal('show');
		
		return false;
	}
	
 