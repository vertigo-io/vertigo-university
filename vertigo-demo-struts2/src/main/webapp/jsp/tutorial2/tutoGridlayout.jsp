<%@page import="kasper.ui.controller.*,io.vertigo.demo.controller.*,io.vertigo.demo.ui.tutorial.*, java.util.*"%>
<%@taglib uri="/WEB-INF/kasper_demo.tld" prefix="demo" %>
<%@taglib uri="/META-INF/kasper.tld" prefix="kasper" %>
<kasper:page style="tuto" controllerclass="io.vertigo.demo.ui.controller.tutorial.TutoController" title="Tutoriel - GridLayout">
<table width="100%">
<tr><%@include file="menu.inc" %>
<td>
<div>
<h1>GridLayout</h1>
<div class="description"><h2>Description</h2>
Ce composant représente une grille HTML.<br/>
Il est utilisé pour créer des formulaires ou assurer des alignements.<br/>
Ce composant est rendu sous forme de table HTML. Il assure les changements de lignes, et la structure de la table.<br/>
Ce composant est écrit avec sa balise de début et sa balise de fin, entre les deux il faut placer les éléments que l'on souhaite placer dans la grille.<br/>
La plupart des composants (<i>tag</i>) remplissent automatiquement une cellule de la grille.<br/>
Pour ajouter des composants spéciaux, du <b>html</b>, ou modifier les paramètres de la cellule (taille, ...) il faut utiliser le composant <a href="tutoItem.html">item</a>.<br/>
</div>
<div class="api">
<h2>Paramètres :</h2>
<ul> 
	<li><b><u>cols</u></b> : Nombre de colonne (2 par défaut).</li>
	<li><b>title</b> : Titre de la grille.<br/></li>
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
   <kasper:objectpanel dtoid="DTO_TUTO_OBJECT" readonly="<%=!controller.isModeEdit()%>">
	 <kasper:grid cols="4">
	 	<kasper:label name="CODE"/> <kasper:item rowspan="2"><kasper:field name="CODE"/></kasper:item>
	 	<kasper:item rowspan="2"><kasper:label name="LIBELLE"/></kasper:item> <kasper:item rowspan="2"><kasper:field name="LIBELLE"/></kasper:item> 
	 	 <kasper:label name="DESCRIPTION"/><kasper:item span="4"> <kasper:field name="DESCRIPTION" style="TextArea"/></kasper:item> 
	 	<kasper:label name="PRIX"/> <kasper:field name="PRIX"/> 
	 	<kasper:label name="SI_STOCK"/> <kasper:field name="SI_STOCK"/> 
	 	<kasper:label name="POIDS"/> <kasper:field name="POIDS"/> 
	 	<kasper:label name="DATE_CREATION"/> <kasper:field name="DATE_CREATION"/> 
	 	<kasper:item span="-1"/>
	 	<kasper:label name="TYP_ID"/> <kasper:autocomplete name="TYP_ID"/>
	 	<kasper:label name="TYP_ID"/> <kasper:selection name="TYP_ID"/>
	 </kasper:grid>
   </kasper:objectpanel>
 <%if(controller.isModeEdit()) {%>
  		<kasper:action name="READ" text="Passer en mode lecture"/>
  	<%} else {%>
  		<kasper:action name="EDIT" text="Passer en mode édition"/>  		
  	<%}%>
  </kasper:form>
 <br/>
 <h3>Code :</h3>
   <textarea readonly=true rows=11>
&lt;kasper:grid cols="4"&gt;
 	&lt;kasper:label name="CODE"/&gt; &lt;kasper:field name="CODE"/&gt; 
 	&lt;kasper:label name="LIBELLE"/&gt; &lt;kasper:field name="LIBELLE"/&gt; 
 	&lt;kasper:label name="DESCRIPTION"/&gt; &lt;kasper:field name="DESCRIPTION" style="TextArea"/&gt; 
 	&lt;kasper:label name="PRIX"/&gt; &lt;kasper:field name="PRIX"/&gt; 
 	&lt;kasper:label name="SI_STOCK"/&gt; &lt;kasper:field name="SI_STOCK"/&gt; 
 	&lt;kasper:label name="POIDS"/&gt; &lt;kasper:field name="POIDS"/&gt; 
 	&lt;kasper:label name="DATE_CREATION"/&gt; &lt;kasper:field name="DATE_CREATION"/&gt; 
 	&lt;kasper:item span="-1"/&gt; 
 	&lt;kasper:label name="TYP_ID"/&gt; &lt;kasper:autocomplete name="TYP_ID"/&gt;
 	&lt;kasper:label name="TYP_ID"/&gt; &lt;kasper:selection name="TYP_ID"/&gt; 
&lt;/kasper:grid&gt;</textarea>
</div>
<div class="astuce"><a href="http://wiki.klee.lan.net/index.php/Kasper_4_:_Tutoriel_grid">Consulter les astuces</a></div>
</td></tr></table>

</kasper:page>
