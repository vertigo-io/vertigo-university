
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<s:include value="/jsp/include/header.jsp">
	<s:param name="subtitle">${pageName}</s:param>
</s:include>

<s:form>
	<!-- mode création / modification ------------------------------------------------------------------------------------->
	<s:div layout="table">
		<s:textfield name="produit.code" label="default"/>
		<s:textfield name="produit.libelle" label="default"/>
		<s:textfield name="produit.prix" label="default"/>
		<s:textfield name="produit.description" label="default"/>
		<s:select name="produit.famId" label="default" list="familles"/>
	</s:div>		
	<div class="button-bar">
		<s:if test="%{modeReadOnly}">
			<s:hidden name="prdId" value="%{produit.prdId}" />
			<s:submit action="deleteProduitDetail" value="Supprimer" cssClass="supprimer" onclick='return confirmAction(this, "Etes vous sur de vouloir supprimer ce produit ?")' />
		</s:if>
		<s:elseif test="%{modeEdit}" >
			<s:a action="ProduitDetail" cssClass="annuler">Annuler
			<s:param name="prdId" value="produit.prdId"/></s:a>
		</s:elseif>
		<div class="right">
			<s:if test="%{modeReadOnly}">
				<s:submit action="editProduitDetail" value="Modifier" cssClass="modifier" />
			</s:if>
			<s:else>
				<s:submit action="saveProduitDetail" value="Enregistrer" cssClass="enregistrer" />
			</s:else>
		</div>
	</div>
</s:form>
<s:include value="/jsp/include/footer.jsp"/>