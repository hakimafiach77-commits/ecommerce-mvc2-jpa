<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html><html><head><meta charset="UTF-8"><title>Détail</title></head><body>
<%@ include file="header.jspf" %>
<main>
  <c:choose>
  <c:when test="${not empty produit}">
    <div class="card">
      <h2>${produit.designation}</h2>
      <p>Catégorie : ${produit.categorie.libelle}</p>
      <p>${produit.description}</p>
      <p class="prix"><fmt:formatNumber value="${produit.prix}" minFractionDigits="2"/> MAD — Stock : ${produit.stock}</p>
      <form method="get" action="${pageContext.request.contextPath}/PanierServlet">
        <input type="hidden" name="action" value="ajouter">
        <input type="hidden" name="produitId" value="${produit.id}">
        Quantité : <input type="number" name="quantite" value="1" min="1" max="${produit.stock}" style="width:70px">
        <button class="btn">Ajouter au panier</button>
      </form>
    </div>
  </c:when>
  <c:otherwise><p>Produit introuvable.</p></c:otherwise>
  </c:choose>
  <p><a href="${pageContext.request.contextPath}/ProduitServlet?action=lister">← Retour à la vitrine</a></p>
</main></body></html>
