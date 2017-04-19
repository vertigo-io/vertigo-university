<%@page import="kasper.ui.controller.*,io.vertigo.demo.controller.*,io.vertigo.demo.ui.tutorial.*, demo.model.produit.*,  java.util.*"%>
<%@taglib uri="/WEB-INF/kasper_demo.tld" prefix="demo" %>
<%@taglib uri="/META-INF/kasper.tld" prefix="kasper" %>
<kasper:page style="tuto" controllerclass="io.vertigo.demo.ui.controller.tutorial.TutoSelectionController" title="Tutoriel - AutoList">
<table width="100%">
<tr><%@include file="menu.inc" %>
<td>
<div>
<h1>AutoList</h1>
<div class="description"><h2>Description</h2>
TODO
</div>
<div class="api">
<h2>Paramètres :</h2>
<ul> 
 </ul>
</div>
<div class="exemple">
<h2>Exemple :</h2>
   <kasper:form>
   <kasper:error/>
   <kasper:listpanel dtcid="DTC_TUTO_TYPE_SPE" readonly="<%=!controller.isModeEdit()%>">
	 Automatique  : <kasper:autolist/><br/>
   </kasper:listpanel>
 <%if(controller.isModeEdit()) {%>
  		<kasper:action name="READ" text="Passer en mode lecture"/>
  	<%} else {%>
  		<kasper:action name="EDIT" text="Passer en mode édition"/>  		
  	<%}%>
  </kasper:form>
 <br/>
 <h3>Code :</h3>
   <textarea readonly=true rows=5>
 &lt;kasper:listpanel dtcid="DTC_TUTO_TYPE_SPE"&gt;
	 Automatique  : &lt;kasper:autolist/&gt;
 &lt;/kasper:listpanel&gt;
</textarea>
</div>
<div class="astuce"><a href="http://wiki.klee.lan.net/index.php/Kasper_4_:_Tutoriel_autoPanel">Consulter les astuces</a></div>
</td></tr></table>

</kasper:page>
