<%@page contentType="text/html;charset=ISO-8859-1" import="java.util.List,io.vertigo.demo.ui.controller.*,fr.klee.knock.core.*,fr.klee.knock.commun.*"%>

<%@taglib uri="/META-INF/kasper.tld" prefix="kasper"%>
<%@taglib uri="/WEB-INF/kasper_demo.tld" prefix="kasper_demo"%>

<kasper:page controllerclass="io.vertigo.demo.ui.controller.referentiel.ReferentielListController" title="::KasperDemo - Referentiel Liste">
	<%@include file="/jsp/include/header.inc"%>
	
	<kasper:form>
		<kasper:objectpanel dtoid="<%=controller.getDtoCriterionName()%>" readonly="false">
			<kasper:grid cols="2" colwidth="20%,*">
				<kasper:label name="REFERENTIEL_NAME" /> <kasper:selection name="REFERENTIEL_NAME" collectionid="<%=controller.DTC_CHOICE_LIST%>" display="LIBELLE"/>				
			</kasper:grid>
			
			<div class="buttonbar"><div class="right">
					<kasper:link action="CLEAR" style="button">Effacer</kasper:link>
					<kasper:action name="SEARCH" text="Rechercher" isdefaultaction="true" />
			</div></div>
		
		<div class="separation"></div>
		<kasper:listpanel dtcid="<%=controller.getDtcListName()%>">
			<kasper:autolist/>			
		</kasper:listpanel>
			<div class="buttonbar"><div class="right">
					<kasper:link address="<%=controller.getDetailAddress()%>" style="button" params="MODE=NEW" fields="REFERENTIEL_NAME as DTD_URN">Creer</kasper:link>
			</div></div>
		</kasper:objectpanel>
		
	</kasper:form>
	<%@include file="/jsp/include/footer.inc"%>	
</kasper:page>