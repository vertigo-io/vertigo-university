
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<s:include value="/jsp/include/header.jsp">
	<s:param name="subtitle">${pageName}</s:param>
</s:include>

<s:form>
	<!-- mode création / modification ------------------------------------------------------------------------------------->
	<s:div layout="table" cols="4">
		<s:if test="%{!modeReadOnly}">
			<s:textfield name="utilisateurLogin.login" label="default"/>
			<s:if test="%{modeEdit}">
				<s:textfield name="utilisateurLogin.password" label="Ancien mot de passe"/>
			</s:if>
			<s:textfield name="utilisateurLogin.newPassword" label="default"/>
			<s:textfield name="utilisateurLogin.newPasswordCheck" label="default"/>
		</s:if>
		<tr><td colspan="4">&nbsp;</td></tr>
		<s:textfield name="utilisateur.nom" label="default"/>
		<s:textfield name="utilisateur.prenom" label="default"/>
		<s:textfield name="utilisateur.mail" label="default"/>
		<s:textfield name="utilisateur.telephone" label="default"/>
		<s:textfield name="utilisateur.fax" label="default"/>
		<s:checkbox name="utilisateur.siActif" label="default" labelposition="left"/>
	</s:div>		
	<div class="button-bar">
		<s:if test="%{modeReadOnly}">
			<s:hidden name="utiId" value="%{utilisateur.utiId}" />
			<s:submit action="deleteUtilisateurDetail" value="Supprimer" cssClass="supprimer" onclick='return confirmAction(this, "Etes vous sur de vouloir supprimer ce compte utilisateur ?")' />
		</s:if>
		<s:elseif test="%{modeEdit}" >
			<s:a action="UtilisateurDetail" cssClass="annuler">Annuler
			<s:param name="utiId" value="utilisateur.utiId"/></s:a>
		</s:elseif>
		<div class="right">
			<s:if test="%{modeReadOnly}">
				<s:submit action="editUtilisateurDetail" value="Modifier" cssClass="modifier" />
			</s:if>
			<s:else>
				<s:submit action="saveUtilisateurDetail" value="Enregistrer" cssClass="enregistrer" />
			</s:else>
		</div>
	</div>
</s:form>
<s:include value="/jsp/include/footer.jsp"/>