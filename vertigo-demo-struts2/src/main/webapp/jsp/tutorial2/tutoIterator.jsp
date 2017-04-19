<%@page import="kasper.ui.controller.*,io.vertigo.demo.ui.controller.*mio.vertigo.demo.uistruts.ui.controller.tutorial2.*.io.vertigo.demo.uistruts.services.produit.*"/WEB-INF/kasper_demo.tld" prefix="demo" %>
<%@taglib uri="/META-INF/kasper.tld" prefix="kasper" %>

<kasper:page style="tuto" controllerclass="io.vertigo.demo.ui.controller.tutorial.TutoMatrixtableController" title="Tutoriel - TableLayout">
<table width="100%">
<tr><%@include file="menu.inc" %>
<td>
<div>
<h1>ListPanel/Iterator</h1>
<div class="description"><h2>Description</h2>
Ces composants permettent de travailler sur des liste d'objet :<br/>
<ul><li><b>ListPanel :</b> représente la liste elle même</li>
<li><b>Iterator :</b> crée une boucle sur les éléments de la liste, à l'interieur il y a un objet métier actif</li>
</ul>
</div>
<div class="api">
<h2>ListPanel, Paramètres :</h2>
<ul> 
	TODO
</ul> 
</div>
<div class="api">
<h2>Iterator, Paramètres :</h2>
<ul> 
	<li><b><u>itemname</u></b> : Nom de la variable de la Jsp, représentant l'objet courant (<i>item</i> par défaut.</li>
</ul> 
</div>
<div class="exemple">
<h2>Exemple :</h2>
   <kasper:form>
   <kasper:error/>
   <kasper:listpanel dtcid="DTC_TUTO_OBJECT" readonly="<%=!controller.isModeEdit()%>" rowsperpage="10">	
    <%int i = 0; %>
    <kasper:iterator>
	 	<kasper:field name="CODE" readonly="true"/><br/>
	 	<kasper:field name="LIBELLE"/><br/>
		boucle <%=i++ %><br/>
	 </kasper:iterator>
   </kasper:listpanel>
 <%if(controller.isModeEdit()) {%>
  		<kasper:action name="READ" text="Passer en mode lecture"/>
  	<%} else {%>
  		<kasper:action name="EDIT" text="Passer en mode édition"/>  		
  	<%}%>
  </kasper:form>
 <br/>
 <h3>Code :</h3>
   <textarea readonly=true rows="11">
&lt;kasper:iterator&gt;
 	&lt;kasper:field name="CODE"/&gt; &lt;br/&gt;
 	&lt;kasper:field name="LIBELLE"/&gt; &lt;br/&gt;
 	boucle &lt;%=i++ %&gt;&lt;br/&gt;
&lt;/kasper:iterator&gt;</textarea>
</div>
<div class="astuce"><a href="http://wiki.klee.lan.net/index.php/Kasper_4_:_Tutoriel_ListPanel">Consulter les astuces</a></div>
</td></tr></table>

</kasper:page>
