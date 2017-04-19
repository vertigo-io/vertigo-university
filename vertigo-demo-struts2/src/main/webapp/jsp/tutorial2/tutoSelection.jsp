<%@page import="kasper.ui.controller.*,io.vertigo.demo.ui.controller.*,io.vertigo.demo.ui.controller.tutorial2.*, demo.model.produit.*,   java.util.*"%>
<%@taglib uri="/WEB-INF/kasper_demo.tld" prefix="demo" %>
<%@taglib uri="/META-INF/kasper.tld" prefix="kasper" %>
<kasper:page style="tuto" controllerclass="io.vertigo.demo.ui.controller.tutorial.TutoSelectionController" title="Tutoriel - Selection">
<table width="100%">
<tr><%@include file="menu.inc" %>
<td>
<div>
<h1>Selection</h1>
<div class="description"><h2>Description</h2>
Ce composant représente un champ de selection, quelque soit son style graphique.<br/>
La liste des valeurs proposées dans la sélection peut-être définie de <b>4</b> façons exclusives :<br/>
<ul>
<li><b>Automatiquement</b> : si le champ surquel est posé ce composant est une ForeignKey (par une association) vers un DTO déclaré dans le MasterDataBroker. Si plusieurs MasterDataList sont déclarées pour un même DT, celle par défaut est celle déclarée en <u>premier</u>.</li>
<li><b>Choix d'une liste de ref</b> : En indiquant l'URI de la liste de ref dans l'attribut <code>masterdatalist</code>.</li>
<li><b>Liste de valeur dans le context</b> : En indiquant le nom de la liste dans le context par l'attribut <code>collectionid</code>, le champ de la liste servant à la jointure dans <code>key</code> et le libéllé à afficher dans <code>display</code>.</li>
<li><b>Liste de valeur</b> : En fournissant la liste (l'objet java) par l'attribut <code>valuelist</code>, le champ de la liste servant à la jointure dans <code>key</code> et le libéllé à afficher dans <code>display</code>.</li>
</ul>
Il doit etre posé dans un tag panel fournissant un DTO courant : <code>&lt;kasper:objectpanel&gt;</code> ou <code>&lt;kasper:listpanel&gt;</code><br/>
<i>Le composant Bean et le Tag correspondant ont les même paramètres.</i>
</div>
<div class="api">
<h2>Paramètres :</h2>
<ul> 
	<li><b><u>name</u> <span class="mandatory">*</span></b> : Nom du champ dans le DTO courant.</li>
	<li><b><u>style</u></b> <span class="mandatory">*</span> : Précise le style d'affichage à utiliser. Par défaut les valeurs possibles sont : <code>Radio</code>, <code>ComboBox</code>, <code>ListBox</code>.</li>
	<li><b><u>masterdatalist</u></b>  : URI de la MasterDataList (Liste de référence) fournissant les valeurs sélectionnables.</li>
	<li><b><u>collectionid</u></b>  : Nom dans le context de la liste des valeurs sélectionnables.</li>
	<li><b>valuelist</b> : Liste java des valeurs sélectionnables (l'objet java).</li>
	<li><b><u>key</u></b> : Nom du champ de la liste servant pour la jointure avec le champ à selectionner.</li>
	<li><b><u>display</u></b> : Nom du champ de la liste à utiliser pour l'affichage (peut-être un champ calculé).</li>
	<li><b>nulllabel</b> : Libellé à afficher lorsqu'aucune valeur n'est sélectionée (équivalent à null). La valeur par défaut est définit dans le TagRenderer.</li>
	<li><b>readonly</b> : Passe le champ en mode editable ou non éditable. Par défaut cette propriété est héritée des composants parents.</li>
	<li><b>css</b> : Style css du HTML du field. Le TagRenderer peut l'utiliser pour adapter le rendu.</li>
	<li><b>other</b> : Autres paramètres du composant HTML.</li>
	<li><b>isVisible</b> : Booleen, indiquant si le composant est affiché ou non.</li>
	<li><b>onChange</b> : Code javascript à lancer lors de la modification de la valeur de ce champ (uniquement si modifiable).</li>	
	<li><b>roles</b> : Liste des roles permettant l'utilisation de ce lien. Le lien est invisible pour les utilisateurs qui n'ont pas les droits suffisants.</li>
 </ul>
</div>
         
<div class="exemple">
<h2>Exemple :</h2>
   <kasper:form>
   <kasper:error/>
   <kasper:objectpanel dtoid="DTO_TUTO_OBJECT" readonly="<%=!controller.isModeEdit()%>">
	 <u>Automatique (FK d'une MasterDataList) :</u><br/>
	 &nbsp;&nbsp; Selection RadioH : <kasper:selection name="TYP_ID" style="RadioH"/><br/>
	 &nbsp;&nbsp; Selection Radio : <kasper:selection name="TYP_ID" style="Radio"/><br/>
	 &nbsp;&nbsp; Selection ComboBox : <kasper:selection name="TYP_ID" style="ComboBox"/><br/>
	 &nbsp;&nbsp; Selection ListBox : <kasper:selection name="TYP_ID" style="ListBox"/><br/>
	 
	 Autre liste de ref : <kasper:selection name="TYP_ID" style="ComboBox" masterdatalist="DTC:MADA_TUTO_TYPE_2" /><br/>
	 Autre liste de ref (actif) : <kasper:selection name="TYP_ID" style="ComboBox" masterdatalist="DTC:MADA_TUTO_TYPE_2@ACTIF" /><br/>
	 Liste du context : <kasper:selection name="TYP_ID" style="ComboBox" collectionid="DTC_TUTO_TYPE_SPE" key="TYP_ID" display="LIBELLE"/><br/>
	 Liste java : <kasper:selection name="TYP_ID" style="ComboBox" valuelist="<%=controller.getTypeList()%>" key="TYP_ID" display="LIBELLE" /><br/>
  </kasper:objectpanel>
  <%if(controller.isModeEdit()) {%>
  		<kasper:action name="READ" text="Passer en mode lecture"/>
  	<%} else {%>
  		<kasper:action name="EDIT" text="Passer en mode édition"/>  		
  	<%}%>
  </kasper:form>
 <br/>
 <h3>Code :</h3>
   <textarea readonly=true rows=9>
&lt;kasper:objectpanel dtoid="DTO_TUTO_OBJECT"&gt;
	 &lt;u&gt;Automatique (FK d'une MasterDataList) :&lt;/u&gt;&lt;br/&gt;
	 &nbsp;&nbsp; Selection RadioH : &lt;kasper:selection name="TYP_ID" style="RadioH"/&gt;&lt;br/&gt;
	 &nbsp;&nbsp; Selection Radio : &lt;kasper:selection name="TYP_ID" style="Radio"/&gt;&lt;br/&gt;
	 &nbsp;&nbsp; Selection ComboBox : &lt;kasper:selection name="TYP_ID" style="ComboBox"/&gt;&lt;br/&gt;
	 &nbsp;&nbsp; Selection ListBox : &lt;kasper:selection name="TYP_ID" style="ListBox"/&gt;&lt;br/&gt;
	 Autre liste de ref : &lt;kasper:selection name="TYP_ID" style="ComboBox" masterdatalist="DTC:MADA_TUTO_TYPE_2" /&gt;&lt;br/&gt;
	 Autre liste de ref (actif) : &lt;kasper:selection name="TYP_ID" style="ComboBox" masterdatalist="DTC:MADA_TUTO_TYPE_2@ACTIF" /&gt;&lt;br/&gt;
	 Liste du context : &lt;kasper:selection name="TYP_ID" style="ComboBox" collectionid="DTC_TUTO_TYPE_SPE" key="TYP_ID" display="LIBELLE"/&gt;&lt;br/&gt;
	 Liste java : &lt;kasper:selection name="TYP_ID" style="ComboBox" valuelist="&lt;%=controller.getTypeList()%&gt;" key="TYP_ID" display="LIBELLE" /&gt;&lt;br/&gt;
&lt;/kasper:objectpanel&gt;
</textarea>
</div>
<div class="astuce"><a href="http://wiki.klee.lan.net/index.php/Kasper_4_:_Tutoriel_Selection">Consulter les astuces</a></div>
</td></tr></table>
</kasper:page>
