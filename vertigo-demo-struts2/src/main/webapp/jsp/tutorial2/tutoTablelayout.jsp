<%@page import="kasper.ui.controller.*,io.vertigo.demo.ui.controller.*,io.vertigo.demo.ui.controller.tutorial2.*, demo.model.produit.*,   java.util.*"%>
<%@taglib uri="/WEB-INF/kasper_demo.tld" prefix="demo" %>
<%@taglib uri="/META-INF/kasper.tld" prefix="kasper" %>

<kasper:page style="tuto" controllerclass="io.vertigo.demo.ui.controller.tutorial.TutoMatrixtableController" title="Tutoriel - TableLayout">
<table width="100%">
<tr><%@include file="menu.inc" %>
<td>
<div>
<h1>TableLayout</h1>
<div class="description"><h2>Description</h2>
Ce composant représente un tableau. Il assemble :<br/>
<ul><li>une entète, avec les libellés des colonnes clicables pour le tri</li>
<li>la liste des données, avec un pygama (alternance de couleur)</li>
<li>une pagination, elle apparait automatiquement si besoin</li></ul>
Il est utilisé pour créer des tableaux métiers.<br/>
Ce composant est rendu sous forme de table HTML. Il assure les changements de lignes, et la structure de la table.<br/>
Ce composant est écrit avec sa balise de début et sa balise de fin, entre les deux il faut placer les éléments qui représenteront les colonnes.<br/>
La plupart des composants (<i>tag</i>) sont automatiquement interprétés comme une colonne du tableau.<br/>
Pour ajouter des composants spéciaux, du <b>html</b>, ou modifier les paramètres de la colonne (couleur, alignement, tri, ...) il faut utiliser le composant <a href="tutoColumn.html">column</a>.<br/>
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
   <kasper:listpanel dtcid="DTC_TUTO_OBJECT" readonly="<%=!controller.isModeEdit()%>" rowsperpage="5">	
    <kasper:table cols="5">
	 	<kasper:column rowspan="1" name="CODE"><kasper:field name="CODE" readonly="true"/></kasper:column>
	 	<kasper:column rowspan="2" name="LIBELLE" hasheader="true"><kasper:field name="LIBELLE"/></kasper:column>
	 	<kasper:field name="DATE_CREATION"/> 
	 	<kasper:selection name="TYP_ID"/> 
		<kasper:column rowspan="1" name="SI_STOCK"><kasper:field name="SI_STOCK"/></kasper:column>
	  	<kasper:column span="1" hasheader="false">&nbsp;</kasper:column>
	  	<kasper:column span="3" hasheader="false" name="DESCRIPTION"><kasper:field name="DESCRIPTION" readonly="true"/></kasper:column>
	 </kasper:table>
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
&lt;kasper:table cols="4"&gt;
 	&lt;kasper:field name="CODE"/&gt; 
 	&lt;kasper:field name="LIBELLE"/&gt; 
 	&lt;kasper:field name="DESCRIPTION" style="TextArea"/&gt; 
 	&lt;kasper:field name="PRIX"/&gt; 
 	&lt;kasper:field name="SI_STOCK"/&gt; 
 	&lt;kasper:field name="POIDS"/&gt; 
 	&lt;kasper:field name="DATE_CREATION"/&gt; 
 	&lt;kasper:selection name="TYP_ID"/&gt; 
&lt;/kasper:table&gt;</textarea>
</div>
<div class="astuce"><a href="http://wiki.klee.lan.net/index.php/Kasper_4_:_Tutoriel_autoPanel">Consulter les astuces</a></div>
</td></tr></table>

</kasper:page>
