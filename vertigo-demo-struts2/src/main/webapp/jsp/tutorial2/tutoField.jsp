<%@page import="kasper.ui.controller.*,io.vertigo.demo.ui.controller.*,io.vertigo.demo.ui.controller.tutorial2.*,  java.util.*"%>
<%@taglib uri="/WEB-INF/kasper_demo.tld" prefix="demo" %>
<%@taglib uri="/META-INF/kasper.tld" prefix="kasper" %>
<kasper:page style="tuto" controllerclass="io.vertigo.demo.ui.controller.tutorial.TutoController" title="Tutoriel - Field">
<table width="100%">
<tr><%@include file="menu.inc" %>
<td>
<div>
<h1>Field</h1>
<div class="description"><h2>Description</h2>
Ce composant représente un Field, quelque soit son type.<br/>
Il doit etre posé dans un tag panel fournissant un DTO courant : <code>&lt;kasper:objectpanel&gt;</code> ou <code>&lt;kasper:listpanel&gt;</code><br/>
<i>Le composant Bean et le Tag correspondant ont les même paramètres.</i>
</div>
<div class="api">
<h2>Paramètres :</h2>
<ul> 
	<li><b><u>name</u> <span class="mandatory">*</span></b> : Nom du champs dans le DTO courant.</li>
	<li><b>style</b> : Surcharge le style d'affichage à utiliser. Par défaut on prend celui associé au domain. La liste des styles possible est définie au niveau du projet.</li>
	<li><b>readonly</b> : Passe le champs en mode editable ou non éditable. Par défaut cette propriété est héritée des composants parents.</li>
	<li><b>css</b> : Style css du HTML du field. Le TagRenderer peut l'utiliser pour adapter le rendu.</li>
	<li><b>other</b> : Autres paramètres du composant HTML.</li>
	<li><b>isVisible</b> : Booleen, indiquant si le composant est affiché ou non.</li>
	<li><b>onChange</b> : Code javascript à lancer lors de la modification de la valeur de ce champs (uniquement si modifiable).</li>	
	<li><b>roles</b> : Liste des roles permettant l'utilisation de ce lien. Le lien est invisible pour les utilisateurs qui n'ont pas les droits suffisants.</li>
 </ul>
</div>
<div class="exemple">
<h2>Exemple :</h2>
<kasper:form>
   <kasper:error/>
   <kasper:objectpanel dtoid="DTO_TUTO_OBJECT" readonly="<%=!controller.isModeEdit()%>">
	 Label : <kasper:label name="OBJ_ID" /><br/>
	 Field OBJ_ID : <kasper:field name="OBJ_ID"/><br/>
	 Field CODE : <kasper:field name="CODE"/><br/>
	 Field LIBELLE : <kasper:field name="LIBELLE"/><br/>
	 Field LIBELLE masqué : <kasper:field name="LIBELLE" style="Password"/><br/>
	 Field DESCRIPTION : <kasper:field name="DESCRIPTION" style="TextArea"/><br/>
	 Field PRIX : <kasper:field name="PRIX"/><br/>
	 Field SI_STOCK : <kasper:field name="SI_STOCK"/><br/>
	 Field POIDS : <kasper:field name="POIDS"/><br/>
	 Field DATE_CREATION : <kasper:field name="DATE_CREATION"/><br/> 
  	</kasper:objectpanel>
  	<%if(controller.isModeEdit()) {%>
  		<kasper:action name="READ" text="Passer en mode lecture"/>
  	<%} else {%>
  		<kasper:action name="EDIT" text="Passer en mode édition"/>  		
  	<%}%>
  </kasper:form>
 <br/>
 <h3>Code :</h3>
   <textarea readonly="true" rows="11">
&lt;kasper:objectpanel dtoid="DTO_TUTO_OBJECT"&gt;
	 Label : &lt;kasper:field name="OBJ_ID" style="Label"/&gt;
	 Field OBJ_ID : &lt;kasper:field name="OBJ_ID"/&gt;
	 Field CODE : &lt;kasper:field name="CODE"/&gt;
	 Field LIBELLE : &lt;kasper:field name="LIBELLE"/&gt;
	 Field DESCRIPTION : &lt;kasper:field name="DESCRIPTION" style="TextArea"/&gt;
	 Field PRIX : &lt;kasper:field name="PRIX"/&gt;
	 Field SI_STOCK : &lt;kasper:field name="SI_STOCK"/&gt;
	 Field POIDS : &lt;kasper:field name="POIDS"/&gt;
	 Field DATE_CREATION : &lt;kasper:field name="DATE_CREATION"/&gt;
&lt;/kasper:objectpanel&gt;
</textarea>
</div>
<div class="astuce"><a href="http://wiki/index.php/Kasper_4_:_Tutoriel_Field">Consulter les astuces</a></div>
</div>
</td></tr></table>

</kasper:page>
