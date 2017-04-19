<%@page import="kasper.ui.controller.*,io.vertigo.demo.controller.*,io.vertigo.demo.ui.tutorial.*, demo.model.produit.*,   java.util.*"%>
<%@taglib uri="/WEB-INF/kasper_demo.tld" prefix="demo" %>
<%@taglib uri="/META-INF/kasper.tld" prefix="kasper" %>
<!-- 
<%@taglib uri="/META-INF/itemlistbrowser.tld" prefix="ilb" %>
 -->
<kasper:page style="tuto" controllerclass="io.vertigo.demo.ui.controller.tutorial.TutoSelectionController" title="Tutoriel - ListBrowser">
<table width="100%">
<tr><%@include file="menu.inc" %>
<td>
<h1>ListBrowser</h1>
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
   <ilb:listbrowser dtcid="DTC_TUTO_TYPE_SPE"/><br/>   
  </kasper:form>
 <br/>
 <h3>Code :</h3>
   <textarea readonly=true rows=3>
	&lt;ilb:listbrowser dtcid="DTC_TUTO_TYPE_SPE"/&gt;
</textarea>
</div>
<div class="astuce"><a href="http://wiki.klee.lan.net/index.php/Kasper_4_:_Tutoriel_listbrowser">Consulter les astuces</a></div>
</td></tr></table>

</kasper:page>
