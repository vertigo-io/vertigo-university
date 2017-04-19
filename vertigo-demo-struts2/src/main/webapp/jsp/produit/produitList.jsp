<%@page contentType="text/html;charset=ISO-8859-1" import="java.util.List,io.vertigo.demo.ui.controller.*,fr.klee.knock.core.*,fr.klee.knock.commun.*"%>

<%@taglib uri="/META-INF/kasper.tld" prefix="kasper"%>
<%@taglib uri="/WEB-INF/kasper_demo.tld" prefix="kasper_demo"%>

<kasper:page controllerclass="io.vertigo.demo.ui.controller.produit.ProduitListController" title="::KasperDemo - Liste des produits">
	<%@include file="/jsp/include/header.inc"%>
	
	<kasper:form>
		<kasper:objectpanel dtoid="<%=controller.getDtoCriterionName()%>" readonly="false">
			<kasper:grid cols="4" colwidth="20%,*,20%,*">
				<kasper:label name="CODE" />	<kasper:field name="CODE" />
				<kasper:label name="LIBELLE" />	<kasper:field name="LIBELLE" />			
				<kasper:label name="FAM_ID" />	<kasper:autocomplete name="FAM_ID" masterdatalist="DTC:MADA_FAMILLE"/>		
				<kasper:label name="PRIX_MIN" />	<kasper:field name="PRIX_MIN" />
				<kasper:label name="PRIX_MAX" />	<kasper:field name="PRIX_MAX" />
				<kasper:label name="POIDS_MIN" />	<kasper:field name="POIDS_MIN" />
				<kasper:label name="POIDS_MAX" />	<kasper:field name="POIDS_MAX" />
				<kasper:label name="DESCRIPTION" />	<kasper:item span="-1"><kasper:field name="DESCRIPTION" /></kasper:item>
				<kasper:label name="COMMENTAIRE" />	<kasper:item span="-1"><kasper:field name="COMMENTAIRE" /></kasper:item>
				<kasper:label name="SI_STOCK" />	<kasper:field name="SI_STOCK" />
			</kasper:grid>
			
			<div class="buttonbar"><div class="right">
					<kasper:link action="CLEAR" style="button">Effacer</kasper:link>
					<kasper:action name="SEARCH" text="Rechercher" isdefaultaction="true" />
			</div></div>
		</kasper:objectpanel>
		
		<div class="separation"></div>
		<kasper:listpanel dtcid="<%=controller.getDtcListName()%>">
			<kasper:table cols="8">
				<kasper:column name="CODE">
					<% if (controller.canUserViewDetail()) { %>
						<kasper:field name="CODE" />
						<kasper:link address="<%=controller.getDetailAddress() %>" fields="<%=controller.getIdFieldName() %>" css="lien">
							<kasper:field name="CODE" />
						</kasper:link>
					<% } else { %>
						<kasper:field name="CODE" />
					<%} %>
				</kasper:column>
				<kasper:field name="LIBELLE" />
			  <kasper:selection name="FAM_ID"/>
				<kasper:field name="PRIX" />
				<kasper:field name="POIDS" />
				<kasper:field name="DESCRIPTION" />
				<kasper:field name="SI_STOCK" />
				<kasper:action fields="<%=controller.getIdFieldName() %>" name="DELETE"
						text="Supprimer"
						confirm="Cette opération est irréversible.\nEtes-vous sûr de vouloir supprimer ce produit ?" />

			</kasper:table>
			<div class="buttonbar"><div class="right">
					<kasper:link address="<%=controller.getDetailAddress()%>" style="button" params="MODE=NEW">Creer</kasper:link>
			</div></div>
		
		</kasper:listpanel>
	</kasper:form>
	<%@include file="/jsp/include/footer.inc"%>	
</kasper:page>