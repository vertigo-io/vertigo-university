<%@page import="kasper.ui.controller.*,io.vertigo.demo.controller.*,io.vertigo.demo.ui.tutorial.*, java.util.*"%>
<%@taglib uri="/WEB-INF/kasper_demo.tld" prefix="demo" %>
<%@taglib uri="/META-INF/kasper.tld" prefix="kasper" %>

<kasper:page style="tuto" title="Tutoriel - Chart" controllerclass="io.vertigo.demo.ui.controller.tutorial.TutoChartController">
<table width="100%">
<tr><%@include file="menu.inc" %><td>
<div>
<h1>Chart</h1>
<div class="description"><h2>Description</h2>
Ce composant représente un graphique.<br/>
</div>
<div class="api">
<h2>Paramètres :</h2>
<ul> 
	<li><b><u>cols</u></b> : Nombre de colonne (2 par défaut).</li>
	<li><b>title</b> : Titre du tableau.<br/></li>
	<li><b>style</b> : Style du composant. Le TagRenderer peut l'utiliser pour adapter le rendu (<b>inutilisé pour l'instant</b>).</li>
	<li><b>css</b> : Style css du HTML du lien.</li>
	<li><b>other</b> : Autres paramètres du composant HTML (balise &lt;A&gt;).</li>
	<li><b>colwidth</b> : Liste des largeurs des colonnes séparées par des virgules. Les jokers <code>*</code> sont utilisable.</li>
	<li><b>colcss</b> : Liste des css des colonnes séparées par des virgules. (exemple <code>colcss="label,data"</code>, la premier colonne aura pour css <code>label</code> et la seconde <code>data</code></li>
</ul> 
</div>
<div class="exemple">
<h2>Exemple :</h2>
   <kasper:form>
   <kasper:error/>
   <kasper:listpanel dtcid="DTC_TUTO_OBJECT" readonly="<%=!controller.isModeEdit()%>">	
	    <kasper:chart title="Evolution du prix" type="COLUMN" name="LIBELLE" yaxislabel="Prix" height="400" width="800">
			<kasper:dataset value="PRIX"/>
			<kasper:dataset value="POIDS"/>
		</kasper:chart>
   </kasper:listpanel>
 <%if(controller.isModeEdit()) {%>
  		<kasper:action name="READ" text="Passer en mode lecture"/>
 	<%} else {%>
 		<kasper:action name="EDIT" text="Passer en mode édition"/>  		
 	<%}%>
  </kasper:form>
 <br/>
 <h3>Code :</h3>
   <textarea readonly=true rows="5">
&lt;kasperx:chart title="REVENUE_CHART" type="COLUMN" name="START_MONTH" yaxislabel="FORECAST_USD_LABEL" height="400" width="800"&gt;
	&lt;kasperx:dataset value="REVENUE"/&gt;
	&lt;kasperx:dataset value="PREVIOUS_FORECAST"/&gt;
	&lt;kasperx:dataset value="CURRENT_FORECAST"/&gt;
&lt;/kasperx:chart&gt;</textarea>
</div>
<div class="astuce"><a href="http://wiki.klee.lan.net/index.php/Kasper_4_:_Tutoriel_chart">Consulter les astuces</a></div>
</td></tr></table>

</kasper:page>
