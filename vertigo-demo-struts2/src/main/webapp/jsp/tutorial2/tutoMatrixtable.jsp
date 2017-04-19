<%@page import="kasper.ui.controller.*,io.vertigo.demo.ui.controller.*,io.vertigo.demo.ui.controller.tutorial2.*,  java.util.*"%>
<%@taglib uri="/WEB-INF/kasper_demo.tld" prefix="demo" %>
<%@taglib uri="/META-INF/kasper.tld" prefix="kasper" %>
<kasper:page style="tuto" controllerclass="io.vertigo.demo.ui.controller.tutorial.TutoMatrixtableController" title="Tutoriel - MatrixTable">
<table width="100%">
<tr><%@include file="menu.inc" %>
<td>
<div>
<h1>MatrixTable</h1>
<div class="description"><h2>Description</h2>
TODO</div>
<div class="api">
<h2>Paramètres :</h2>
<ul> 
	<li><b><u>dtcid</u> <span class="mandatory">*</span></b> : Nom de la DTC dans le context.</li>
	<li><b><u>name</u> <span class="mandatory">*</span></b> : Nom du champs contenant la valeur à renseigner.</li>
	<li><b><u>columnfieldname</u> <span class="mandatory">*</span></b> : Nom du champs référencant les données à afficher en colonne.</li>
	<li><b><u>rowfieldname</u> <span class="mandatory">*</span></b> : Nom du champs référencant les données à afficher en ligne.</li>
</ul>
</div>
<div class="exemple">
<h2>Exemple :</h2>
<kasper:form>
   <kasper:error/>
   Nombre d'éléments sélectionnés : <%=controller.getNbElements()%>
   <demo:matrixtable dtcid="DTC_TUTO_OBJECT" name="SI_STOCK" columnfieldname="ETA_ID" rowfieldname="TYP_ID" readonly="<%=!controller.isModeEdit()%>"/><br/>
  <%if(controller.isModeEdit()) {%>
  		<kasper:action name="READ" text="Passer en mode lecture"/>
  	<%} else {%>
  		<kasper:action name="EDIT" text="Passer en mode édition"/>  		
  	<%}%>
  </kasper:form>
 <br/>
 <h3>Code :</h3>
   <textarea readonly="true" rows="11">
			TODO
	 </textarea>
</div>
<div class="astuce"><a href="http://wiki/index.php/Kasper_4_:_Tutoriel_MatrixTable">Consulter les astuces</a></div>
</div>
</td></tr></table>

</kasper:page>
