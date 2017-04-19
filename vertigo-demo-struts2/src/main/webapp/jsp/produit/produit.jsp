<%@page contentType="text/html;charset=ISO-8859-1" import="java.util.List,io.vertigo.demo.controller.*,fr.klee.knock.core.*,fr.klee.knock.commun.*"%>
<%@page import="io.vertigo.demo.ProduitController"%>

<%@taglib uri="/META-INF/kasper.tld" prefix="kasper"%>
<%@taglib uri="/WEB-INF/kasper_demo.tld" prefix="kasper_demo"%>

<kasper:page controllerclass="io.vertigo.demo.ui.controller.produit.ProduitController" title="::KasperDemo - Detail d'un produits">
	<%@include file="/jsp/include/header.inc"%>
	<kasper:form>
		<kasper:objectpanel dtoid="<%=controller.getDtoName()%>" readonly="<%=controller.isModeView()%>">
			<kasper:grid cols="2" colwidth="20%,*">
				<kasper:label name="CODE" />	<kasper:field name="CODE" />
				<kasper:label name="LIBELLE" />	<kasper:field name="LIBELLE" />	
				<kasper:label name="FAM_ID" />	<kasper:autocomplete name="FAM_ID" masterdatalist="DTC:MADA_FAMILLE"/>				
				<kasper:label name="PRIX" />	<kasper:field name="PRIX" />
				<kasper:label name="POIDS" />	<kasper:field name="POIDS" />
				<kasper:label name="DESCRIPTION" />	<kasper:field name="DESCRIPTION" style="TextArea"/>
				<kasper:label name="COMMENTAIRE" />	<kasper:field name="COMMENTAIRE" style="TextArea"/>
				<kasper:label name="SI_STOCK" />	<kasper:field name="SI_STOCK" />								
			</kasper:grid>
			<%@include file="/jsp/include/templates/detailFooter.inc"%>			
		</kasper:objectpanel>
	</kasper:form>
	<%@include file="/jsp/include/footer.inc"%>
</kasper:page>