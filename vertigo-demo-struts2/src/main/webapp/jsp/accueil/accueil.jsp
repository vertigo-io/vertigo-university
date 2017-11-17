<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:include value="/jsp/include/header.jsp">
	<s:param name="subtitle">${pageName}</s:param>
</s:include>
	<h1>Bienvenue sur la Vertigo-Struts2</h1>	
 	<ul>
	<li><a href="./UtilisateurList.do">rechercher un utilisateur</a></li>
	<li><a href="./ProduitList.do">rechercher un produit</a></li>
	<li><a href="./Accueil.do">accueil</a></li>
	</ul>
<%@include file="/jsp/include/footer.jsp"%>