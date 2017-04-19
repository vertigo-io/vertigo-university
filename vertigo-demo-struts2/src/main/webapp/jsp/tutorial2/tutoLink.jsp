<%@page import="kasper.ui.controller.*,io.vertigo.demo.controller.*,io.vertigo.demo.ui.tutorial.*, java.util.*"%>
<%@taglib uri="/WEB-INF/kasper_demo.tld" prefix="demo" %>
<%@taglib uri="/META-INF/kasper.tld" prefix="kasper" %>
<kasper:page style="tuto" controllerclass="io.vertigo.demo.ui.controller.tutorial.TutoController" title="Tutoriel - Link">
<table width="100%">
<tr><%@include file="menu.inc" %>
<td>

<h1>Link</h1>
<div class="description"><h2>Description</h2>
Ce composant représente un lien vers un controller.<br/>
<i>Le composant Bean et le Tag correspondant ont les même paramètres.</i><br/>
<u>Styles possibles</u>
<ul><li>link <i>(par défaut)</i></li></ul>
<ul><li>button</li></ul>
</div>
<div class="api">
<h2>Contenu :</h2>
L'attribut <code>content</code> du Bean, ou le contenu du Tag, est utilisé pour le libellé du lien.<br/><br/>
<h2>Paramètres :</h2>
<ul> 
	<li><b><u>address</u></b> : Adresse du controller destination.<br/></li>
	<li><b><u>action</u></b> : Action à appeller sur le controlleur destination.</li>
	<li><b>fields</b> : Transmission dans le lien des valeurs de certains champs du DTO courant. Ils seront récupérable dans le KEvent lors du initContext.<br/>Les champs sont séparés par <b>,</b> et peuvent avoir des alias avec <b> as </b>.</li>
	<li><b>objects</b> : Transmission dans le lien des valeurs de certains objets du contexte. Ils seront récupérable dans le KEvent lors du initContext.</li>
	<li><b>params</b> : Autres paramètres statiques (sous la forme PARAM1=valeur,PARAM2=valeur2).</li>
	<li><b>style</b> : Style du composant. Le TagRenderer peut l'utiliser pour adapter le rendu (ex:afficher sour forme d'un lien ou d'un bouton).</li>
	<li><b>css</b> : Style css du HTML du lien.</li>
	<li><b>other</b> : Autres paramètres du composant HTML (balise &lt;A&gt;).</li>
	<li><b>confirm</b> : Message à afficher pour confirmer l'utilisation du lien.</li>
	<li><b>onClick</b> : Code javascript à lancer lors du click sur ce lien.</li>	
	<li><b>roles</b> : Liste des roles permettant l'utilisation de ce lien. Le lien est invisible pour les utilisateurs qui n'ont pas les droits suffisants.</li>
 </ul>
</div>

<div class="exemple">
<h2>Exemple :</h2>
   <kasper:link address="tutoLink.html">Lien vers cette page</kasper:link><br/>
   <kasper:link action="READ">Lien avec une action, sans adresse</kasper:link><br/>
   <kasper:link address="tutoLink.html" objects="DTO_TUTO_OBJECT">Lien vers cette page, avec un DTO</kasper:link><br/>
   <kasper:link address="tutoLink.html" params="CODE=codeTest">Lien vers cette page, avec 'params'</kasper:link><br/>
 	 <kasper:link address="tutoLink.html" style="button">Lien de style button</kasper:link><br/>
   <br/>
 <h3>Code :</h3>
   <textarea readonly="true" rows="6">
&lt;kasper:link address="tutoLink.html"&gt;Lien vers cette page &lt;/kasper:link&gt;
&lt;kasper:link action="READ"&gt;Lien avec une action, sans adresse &lt;/kasper:link&gt;
&lt;kasper:link address="tutoLink.html" objects="DTO_TUTO_OBJECT"&gt;Lien vers cette page, avec un DTO &lt;/kasper:link&gt;
&lt;kasper:link address="tutoLink.html" params="CODE=codeTest"&gt;Lien vers cette page, avec 'params' &lt;/kasper:link&gt;
&lt;kasper:link address="tutoLink.html" style="button"&gt;Lien de style button&lt;/kasper:link&gt;<br/>
  </textarea>
</div>
<div class="astuce"><a href="http://wiki/index.php/Kasper_4_:_Tutoriel_Link">Consulter les astuces</a></div>
</td></tr></table>
</kasper:page>
