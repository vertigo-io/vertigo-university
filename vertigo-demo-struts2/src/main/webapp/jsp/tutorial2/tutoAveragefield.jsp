<%@page import="kasper.ui.controller.*,io.vertigo.demo.controller.*,io.vertigo.demo.ui.tutorial.*, demo.model.produit.*,   java.util.*"%>
<%@taglib uri="/WEB-INF/kasper_demo.tld" prefix="demo" %>
<%@taglib uri="/META-INF/kasper.tld" prefix="kasper" %>
<kasper:page style="tuto" controllerclass="io.vertigo.demo.ui.controller.tutorial.TutoController" title="Tutoriel - AverageField">
<table width="100%">
<tr><%@include file="menu.inc" %>
<td>
<div>
<h1>AverageField</h1>
<div class="description"><h2>Description</h2>
TODO</div>
<div class="api">
<h2>Paramètres :</h2>
<ul> 
	<li><b><u>name</u> <span class="mandatory">*</span></b> : Nom du champs dans le DTO courant.</li>
	<li><b>style</b> : Surcharge le style d'affichage à utiliser. Par défaut on prend celui associé au domain. La liste des styles possible est définie au niveau du projet.</li>
	<li><b>label</b> : Surcharge le label du champs.</li>
</ul>
</div>
<div class="exemple">
<h2>Exemple :</h2>
<kasper:form>
   <kasper:error/>
    <demo:average dtcid="DTC_TUTO_OBJECT" name="PRIX"/><br/>
   	<demo:average dtcid="DTC_TUTO_OBJECT" name="POIDS"/><br/>
  <%if(controller.isModeEdit()) {%>
  		<kasper:action name="READ" text="Passer en mode lecture"/>
  	<%} else {%>
  		<kasper:action name="EDIT" text="Passer en mode édition"/>  		
  	<%}%>
  </kasper:form>
 <br/>
 <h3>Code :</h3>
   <textarea readonly=true rows=3>
	&lt;demo:average dtcid="DTC_TUTO_OBJECT" name="PRIX"/&gt;
   	&lt;demo:average dtcid="DTC_TUTO_OBJECT" name="POIDS"/&gt;
   </textarea>
</div>
<div class="astuce"><a href="http://wiki/index.php/Kasper_4_:_Tutoriel_AverageField">Consulter les astuces</a></div>
</td></tr></table>

</kasper:page>
