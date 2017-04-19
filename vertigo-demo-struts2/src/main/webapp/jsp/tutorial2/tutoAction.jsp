<%@page import="kasper.ui.controller.*,io.vertigo.demo.controller.*,io.vertigo.demo.ui.tutorial.*, demo.model.produit.*,  java.util.*"%>
<%@taglib uri="/WEB-INF/kasper_demo.tld" prefix="demo" %>
<%@taglib uri="/META-INF/kasper.tld" prefix="kasper" %>

<kasper:page style="tuto" controllerclass="io.vertigo.demo.ui.controller.tutorial.TutoController" title="Tutoriel - Action">
<table width="100%">
<tr><%@include file="menu.inc" %>
<td>
<div>
<h1>Action</h1>
<div class="description"><h2>Description</h2>
Ce composant représente une action vers un controller.<br/>
Il POST le formulaire html et envoi une action vers le controller destinataire.<br/>
Il doit etre posé dans un tag de formulaire <code>&lt;kasper:form&gt;</code>
<b>ET</b> un tag panel fournissant un DTO courant : <code>&lt;kasper:objectpanel&gt;</code> ou <code>&lt;kasper:listpanel&gt;</code><br/>
<i>Le composant Bean et le Tag correspondant ont les même paramètres.</i><br/>
<u>Styles possibles</u>
<ul><li>button <i>(par défaut)</i></li></ul>
<ul><li>link</li></ul>
</div>
<div class="api">
<h2>Paramètres :</h2>
<ul> 
	<li><b><u>name</u> <span class="mandatory">*</span></b> : Nom de l'action du controller. Ex: name="<b>NOM_ACTION</b>" appel la méthode <code>execute<b>NomAction</b></code> du controller.</li>
	<li><b><u>text</u> <span class="mandatory">*</span></b> : Libellé du bouton d'action.</li>
	<li><b>fields</b> : Transmission dans l'action des valeurs de certains champs du DTO courant. Ils seront récupérable dans le KMessage du <code>execute</code>.</li>
	<li><b>style</b> : Style du composant. Le TagRenderer peut l'utiliser pour adapter le rendu (ex:afficher sour forme d'un lien ou d'un bouton).</li>
	<li><b>css</b> : Style css du HTML de l'action. Le TagRenderer peut l'utiliser pour adapter le rendu (ex:afficher sour forme d'un lien ou d'un bouton).</li>
	<li><b>other</b> : Autres paramètres du composant HTML (balise &lt;A&gt;).</li>
	<li><b>isVisible</b> : Booleen, indiquant si le composant est affiché ou non.</li>
	<li><b>confirm</b> : Message à afficher pour confirmer l'utilisation de l'action.</li>
	<li><b>onClick</b> : Code javascript à lancer lors du click sur cette action.</li>	
	<li><b>isDefaultAction</b> : Booleen indiquant que cette action est celle par défaut, ie: celle lancée lors de l'appui sur <code>ENTER</code> dans le formulaire.</li>	
	<li><b>roles</b> : Liste des roles permettant l'utilisation de cette action. L'action est invisible pour les utilisateurs qui n'ont pas les droits suffisants.</li>
 </ul>
</div>
 
<div class="exemple">
<h2>Exemple :</h2>
<kasper:form>
   <kasper:objectpanel dtoid="DTO_TUTO_OBJECT">
   	<kasper:action name="MY_ACTION" text="Faire mon action"/>
   	<kasper:action name="MY_ACTION" text="Faire mon action, avec un field" fields="OBJ_ID"/>
   	<kasper:action name="MY_ACTION" text="Faire mon action, apres confirm" confirm="Vous êtes sûr ?"/>
   	<kasper:action name="MY_ACTION" text="Faire mon action, avec alerte" onclick="alert('Trop tard !!')"/>
    <kasper:action name="MY_ACTION" text="Mon action en lien" style="link"/><br/>
  </kasper:objectpanel>
</kasper:form>
 <br/>
 <h3>Code :</h3>
   <textarea readonly="true" rows="8">
&lt;kasper:form&gt;
   &lt;kasper:objectpanel dtoid="DTO_TUTO_OBJECT"&gt;
   	&lt;kasper:action name="MY_ACTION" text="Faire mon action"/&gt;
   	&lt;kasper:action name="MY_ACTION" text="Faire mon action, avec un field" fields="OBJ_ID"/&gt;
   	&lt;kasper:action name="MY_ACTION" text="Faire mon action, apres confirm" confirm="Vous êtes sûr ?"/&gt;
   	&lt;kasper:action name="MY_ACTION" text="Faire mon action, avec alerte" onclick="alert('Trop tard !!')"/&gt;
   	&lt;kasper:action name="MY_ACTION" text="Faire mon action" style="link"/&gt;
  &lt;/kasper:objectpanel&gt;
&lt;/kasper:form&gt;
</textarea>
</div>
<div class="astuce"><a href="http://wiki/index.php/Kasper_4_:_Tutoriel_Action">Consulter les astuces</a></div>
</div>
</td></tr></table>

</kasper:page>
