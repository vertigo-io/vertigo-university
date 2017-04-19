<%@page contentType="text/html;charset=ISO-8859-1" import="java.util.List,io.vertigo.demo.controller.*,fr.klee.knock.core.*,fr.klee.knock.commun.*"%>
<%@page import="io.vertigo.demo.ReferentielController"%>

<%@taglib uri="/META-INF/kasper.tld" prefix="kasper"%>
<%@taglib uri="/WEB-INF/kasper_demo.tld" prefix="kasper_demo"%>

<kasper:page controllerclass="io.vertigo.demo.ui.controller.referentiel.ReferentielController" title="::KasperDemo - Référentiel Detail">
	<%@include file="/jsp/include/header.inc"%>
	<kasper:form>
		<kasper:objectpanel dtoid="<%=controller.getDtoName()%>" readonly="<%=controller.isModeView()%>">
			<kasper:auto />
			
			<div class="buttonbar">
		<% if (controller.isModeView()) {%>
			<kasper:link address="<%=controller.getListAddress() %>" style="button">Retourner à la liste</kasper:link>
		<%} else if (controller.isModeNew()) {%>
			<kasper:link address="<%=controller.getListAddress() %>" style="button">Annuler</kasper:link>
		<%} else if(controller.isModeEdit()) {%>
			<kasper:link fields="<%=controller.getPkFieldName()%>" objects="DTD_URN" style="button">Annuler</kasper:link>
		<%}%>
		<div class="right">		
		<% if (controller.isModeView())  {%>
			<kasper:action name="DELETE" text="Supprimer" confirm="Etes-vous sûr ? Cette opération n'est pas reverssible." roles="<%=controller.toString(controller.getDeleteRoles())%>"/>
			<kasper:action name="EDIT" text="Modifier" roles="<%=controller.toString(controller.getUpdateRoles())%>"/>
		<%} else if (controller.isModeNew()) {%>
			<kasper:action name="CREATE" text="Enregistrer" roles="<%=controller.toString(controller.getCreateRoles())%>"/>
		<%} else if(controller.isModeEdit()) {%>
			<kasper:action name="UPDATE" text="Enregistrer" roles="<%=controller.toString(controller.getUpdateRoles())%>"/>
		<%}%>
		</div>
	</div>
				
		</kasper:objectpanel>
	</kasper:form>
	<%@include file="/jsp/include/footer.inc"%>
</kasper:page>