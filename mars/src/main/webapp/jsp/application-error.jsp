<%@ page session="false" import="java.util.*, javax.servlet.*" %>
<%!
	
private String printException(Throwable t) throws Exception {
        int i;
        StringBuffer sw = new StringBuffer();
        StackTraceElement[] stack = t.getStackTrace();
        sw.append(t.getClass().getName());
        sw.append(": <h4>").append(t.getMessage()).append("</h4>");
        for (i = 0; i < stack.length; i++) {
            if (stack[i].getClassName().startsWith("javax.servlet.")) {
                sw.append("...");
                break;
            }
            sw.append("\n\t").append(stack[i]);
        }
        String res = sw.toString();
		for (i = 0; (i = res.indexOf('&', i)) >= 0; i++) res = res.substring(0, i) + "&amp;" + res.substring(i + 1);
	   	for (i = 0; (i = res.indexOf('\"', i)) >= 0;) res = res.substring(0, i) + "&quot;" + res.substring(i + 1);
    	for (i = 0; (i = res.indexOf('<', i)) >= 0;) res = res.substring(0, i) + "&lt;" + res.substring(i + 1);
    	for (i = 0; (i = res.indexOf('>', i)) >= 0;) res = res.substring(0, i) + "&gt;" + res.substring(i + 1);
    	for (i = 0; (i = res.indexOf('\t', i)) >= 0;) res = res.substring(0, i) + "&nbsp;&nbsp;" + res.substring(i + 1);
    	for (i = 0; (i = res.indexOf('\n', i)) >= 0; i++) res = res.substring(0, i + 1) + "<br/>" + res.substring(i + 1);
		for (i = 0; (i = res.indexOf("&lt;h4&gt;", i)) >= 0;) res = res.substring(0, i) + "<h4>" + res.substring(i + "&lt;h4&gt;".length());
    	for (i = 0; (i = res.indexOf("&lt;/h4&gt;", i)) >= 0; i++) res = res.substring(0, i) + "</h4>" + res.substring(i + "&lt;/h4&gt;".length());
		return res;
    }
%>

<%
		final String baseUrl = request.getRequestURL().substring(0, request.getRequestURL().indexOf(request.getServletPath()))+"/";

		Throwable e = (Throwable) request.getAttribute("javax.servlet.error.exception"), t;
		String errorMessage = (String) request.getAttribute("javax.servlet.error.message");
		Integer errorCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		//Throwable e = (Throwable)pageContext.getAttribute("ex"), t;
        List list = new ArrayList();
        String message = null;
        boolean sessionException = false;
        boolean contextException = false;
        boolean securityException = false;
        boolean noObjectFoundException = false;
       	
        if (errorCode!=null && errorCode.equals(401)) {
        	response.sendRedirect(baseUrl+"login/?code=401");
        }
        
        for (; e != null;) {
            list.add(e);
            if (e instanceof io.vertigo.vega.webservice.exception.SessionException) {
            	sessionException = true;
            	response.sendRedirect(baseUrl+"login/?code=400");
            	%>
            	<%-- jsp:forward page="noSession-error.jsp" / --%>
            <%}
            if (e instanceof io.vertigo.ui.exception.ExpiredViewContextException) contextException = true;
            if (e instanceof io.vertigo.account.authorization.VSecurityException || errorCode == 403) securityException = true;
            t = e.getCause();
            if (t == null && (e instanceof ServletException)) t = ((ServletException) e).getRootCause();
            if (t == null && (e instanceof java.sql.SQLException)) t = ((java.sql.SQLException) e).getNextException();
            if (t == null && (e instanceof io.vertigo.lang.WrappedException)) t = ((io.vertigo.lang.WrappedException) e).getCause();
            e = t;
        }
        Collections.reverse(list);
       
       StringBuilder sbHome = new StringBuilder("");
 	   sbHome.append("<a class=\"lien\" href=\"home/\">");
 	   sbHome.append("<button class=\"fix_link\">l'&eacute;cran d'accueil</button>");
 	   sbHome.append("</a>");
 	   
 	   StringBuilder sbPrevious = new StringBuilder("");
 	   sbPrevious.append("<a class=\"lien\" href=\"javascript:history.back();\">");
 	   sbPrevious.append("<button class=\"fix_link\">l'&eacute;cran pr&eacute;c&eacute;dent</button>");
 	   sbPrevious.append("</a>");
 	   
 	   StringBuilder sbReconnect = new StringBuilder("");
 	   sbReconnect.append("<a class=\"lien\" href=\"login/\">");
 	   sbReconnect.append("<button class=\"fix_link\">reconnecter</button>");
 	   sbReconnect.append("</a>");
 	   
 	   StringBuilder sbReconnectAccent = new StringBuilder("");
 	   sbReconnectAccent.append("<a class=\"lien\" href=\"login/\">");
 	   sbReconnectAccent.append("<button class=\"fix_link\">reconnect&eacute;</button>");
 	   sbReconnectAccent.append("</a>");
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<meta http-equiv="Content-Script-Type" content="text/javascript"/>
	
	<base href="<%=baseUrl%>"></base>	
    <script th:src="@{/vertigo-ui/static/3rdParty/cdn.jsdelivr.net/particles.js/2.0.0/particles.min.js}"></script>	
	<link href="static/css/error.css" type="text/css" rel="stylesheet"/>
	<title>Vertigo - <%=errorCode%></title>
</head>

<body class="errorPage">
	<script>
		var isVisible = false;
		handleClick = function () {
			if (document.getElementById) {
				if(!isVisible)
					document.getElementById("errordetail").style.display = "block";
				else
			    	document.getElementById("errordetail").style.display = "none";
			    isVisible = !isVisible;
			  }
			  else {
			    document.write("Unrecognized Browser Detected");
			  }
			}
	</script>
  <div id="particles-js"></div>
  <div>
  	<div class="content">
    <h1><%=errorCode%></h1>
    <% if (sessionException) { %>
		<h2>Votre session a expir&eacute;</h2>
		<p>
		Vous n'avez pas utilis&eacute; l'application pendant un certain temps, pour des raisons de s&eacute;curit&eacute; l'application vous a donc d&eacute;connect&eacute;.<br/>
		Vous pourrez reprendre votre travail apr&eacute;s vous &eacute;tre <%=sbReconnectAccent.toString()%>.
		</p>
	<% } else if (contextException) { %>
		<h2>Le contexte de cet &eacute;cran n'est plus disponible</h2>
		<p>
		L'action que vous avez demand&eacute;e ne peut se poursuivre car le contexte de cet &eacute;cran n'est plus disponible.<br/>
		Vous pouvez continuer votre travail &eacute; partir de <%=sbPrevious.toString()%> ou <%=sbHome.toString()%>.<br/>
		Ce cas de figure peut se produire si vous avez cliqu&eacute; un trop grand nombre de fois de suite sur la fl&eacute;che "retour arri&eacute;re" de votre navigateur, ou si vous avez attendu trop longtemps avant de cliquer sur une action.
		</p>
	<% } else if (securityException) { %>
		<h2>Vous n'&eacute;tes pas habilit&eacute; &eacute; effectuer cette action</h2>
		<p>
		L'action que vous avez demand&eacute;e ne peut se poursuivre car vous n'avez pas les habilitations suffisantes.<br/>
		Vous pouvez continuer votre travail &eacute; partir de <%=sbPrevious.toString()%> ou <%=sbHome.toString()%>.<br/>
		Vous pouvez &eacute;galement vous <%=sbReconnect.toString()%>.
		</p>
	<% } else if (noObjectFoundException) { %>
		<h2>L'&eacute;l&eacute;ment demand&eacute; n'existe pas </h2>
		<p>
		L'&eacute;l&eacute;ment demand&eacute; n'existe pas, il vient probablement d'&eacute;tre supprim&eacute; par un autre utilisateur.<br/>
		Vous pouvez continuer votre travail &agrave; partir de <%=sbPrevious.toString()%> ou <%=sbHome.toString()%>.<br/>
		</p>
	<% } else { %>
		<h2>Erreur de l'application</h2>
<p>Cet &eacute;cran signifie qu'une erreur syst&egrave;me inattendue s'est produite pendant le traitement de l'action demand&eacute;e.<br/>
Un enregistrement a &eacute;t&eacute; cr&eacute;&eacute; dans le fichier de log de l'application.</p>
<p>De telles erreurs peuvent se produire en cas d'indisponibilit&eacute; temporaire d'un composant syst&egrave;me, dans ce cas,<br/>
vous pouvez revenir &agrave; <%=sbPrevious.toString()%> puis essayer &agrave; nouveau.</p>
<p>Il peut aussi s'agir d'une anomalie de l'application, dans ce cas, veuillez contacter le support technique et leur<br/>
communiquer l'heure &agrave; laquelle s'est produite l'erreur ainsi que les informations ci dessous.</p>
		
		<a href="#" onclick="handleClick();return false;" id="showerrorlink"><button class="denied__link">Voir le message d'erreur</button></a>	
		</div>
		<div id="errordetail" style="display:none;">
		<h2><%="HTTP (" + errorCode + ") : " + errorMessage %></h2>
		<% for (int i = 0; i < list.size(); i++) { %>
			<% t = (Throwable)list.get(i); %>
			<h4><%= i > 0 ? "Cons&eacute;quence (" + i + ")" : "Cause racine" %></h4>
			<div class="code">
				<%= printException(t)%>
			</div>
		<% } %>
		</div>
	<% } %>
       
    <svg>
    <g>
      <path class="stars" fill="#FFF" d="M112.456 363.093c-.056 7.866-6.478 14.197-14.344 14.142 7.866.056 14.198 6.48 14.142 14.345.056-7.866 6.48-14.198 14.345-14.142-7.868-.057-14.2-6.48-14.144-14.345zM432.436 274.908c-.056 7.866-6.478 14.198-14.344 14.142 7.866.057 14.197 6.48 14.142 14.345.056-7.866 6.48-14.197 14.345-14.142-7.868-.056-14.2-6.48-14.144-14.345zM159.75 58.352c-.12 16.537-13.62 29.848-30.157 29.73 16.537.118 29.848 13.62 29.73 30.156.118-16.537 13.62-29.848 30.156-29.73-16.54-.117-29.85-13.62-29.73-30.156z"/>
    </g>
  </svg>
  
  <img src="static/img/error/astronaut.svg" class="astronaut" />
  <div class="mars">
  	<img src="static/img/error/spaceship.svg" class="spaceship" />
  </div>
  </div>
      
<script>
var particles = {
	      "particles": {
	        "number": {
	          "value": 160,
	          "density": {
	            "enable": true,
	            "value_area": 800
	          }
	        },
	        "color": {
	          "value": "#ffffff"
	        },
	        "shape": {
	          "type": "circle",
	          "stroke": {
	            "width": 0,
	            "color": "#000000"
	          },
	          "polygon": {
	            "nb_sides": 5
	          },
	          "image": {
	            "src": "img/github.svg",
	            "width": 100,
	            "height": 100
	          }
	        },
	        "opacity": {
	          "value": 1,
	          "random": true,
	          "anim": {
	            "enable": true,
	            "speed": 1,
	            "opacity_min": 0,
	            "sync": false
	          }
	        },
	        "size": {
	          "value": 3,
	          "random": true,
	          "anim": {
	            "enable": false,
	            "speed": 4,
	            "size_min": 0.3,
	            "sync": false
	          }
	        },
	        "line_linked": {
	          "enable": false,
	          "distance": 150,
	          "color": "#ffffff",
	          "opacity": 0.4,
	          "width": 1
	        },
	        "move": {
	          "enable": true,
	          "speed": 0.17,
	          "direction": "none",
	          "random": true,
	          "straight": false,
	          "out_mode": "out",
	          "bounce": false,
	          "attract": {
	            "enable": false,
	            "rotateX": 600,
	            "rotateY": 600
	          }
	        }
	      },
	      "interactivity": {
	        "detect_on": "canvas",
	        "events": {
	          "onhover": {
	            "enable": false,
	            "mode": "bubble"
	          },
	          "onclick": {
	            "enable": false,
	            "mode": "repulse"
	          },
	          "resize": false
	        },
	        "modes": {
	          "grab": {
	            "distance": 400,
	            "line_linked": {
	              "opacity": 1
	            }
	          },
	          "bubble": {
	            "distance": 250,
	            "size": 0,
	            "duration": 2,
	            "opacity": 0,
	            "speed": 3
	          },
	          "repulse": {
	            "distance": 400,
	            "duration": 0.4
	          },
	          "push": {
	            "particles_nb": 4
	          },
	          "remove": {
	            "particles_nb": 2
	          }
	        }
	      },
	      "retina_detect": true
	   };
	   particlesJS('particles-js', particles, function() {
	     console.log('callback - particles.js config loaded');
	   });

	</script>
</script>
  </body>
  </html>