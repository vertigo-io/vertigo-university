<%@page import="kasper.ui.controller.*,io.vertigo.demo.ui.controller.*,io.vertigo.demo.ui.controller.tutorial2.*, java.util.*"%>
<%@taglib uri="/WEB-INF/kasper_demo.tld" prefix="demo" %>
<%@taglib uri="/META-INF/kasper.tld" prefix="kasper" %>
<kasper:page style="tuto" controllerclass="io.vertigo.demo.ui.controller.tutorial.TutoController" title="Tutoriel - AutoPanel">
<table width="100%">
<tr><%@include file="menu.inc" %>
<td>
<div>
<h1>AutoPanel</h1>
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
   <kasper:objectpanel dtoid="DTO_TUTO_OBJECT" readonly="<%=!controller.isModeEdit()%>">
	 Automatique  : <kasper:auto/><br/>
   </kasper:objectpanel>
 <%if(controller.isModeEdit()) {%>
  		<kasper:action name="READ" text="Passer en mode lecture"/>
  	<%} else {%>
  		<kasper:action name="EDIT" text="Passer en mode édition"/>  		
  	<%}%>
  </kasper:form>
 <br/>
 <h3>Code :</h3>
   <textarea readonly=true rows=5>
 &lt;kasper:objectpanel dtoid="DTO_TUTO_OBJECT"&gt;
	 Automatique  : &lt;kasper:auto/&gt;
 &lt;/kasper:objectpanel&gt;
</textarea>
</div>
<div class="astuce"><a href="http://wiki.klee.lan.net/index.php/Kasper_4_:_Tutoriel_autoPanel">Consulter les astuces</a></div>
</td></tr></table>

</kasper:page>
