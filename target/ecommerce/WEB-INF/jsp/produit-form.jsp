<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html><html><head><meta charset="UTF-8"><title>Produit</title></head><body>
<%@ include file="header.jspf" %>
<main>
  <h2>${empty produit ? 'Nouveau produit' : 'Modifier le produit'}</h2>
  <form class="box" method="post" action="${pageContext.request.contextPath}/ProduitServlet">
    <input type="hidden" name="action" value="enregistrer">
    <input type="hidden" name="id" value="${produit.id}">
    Désignation <input name="designation" value="${produit.designation}" required>
    Description <textarea name="description" rows="3">${produit.description}</textarea>
    Prix (MAD) <input type="number" step="0.01" min="0" name="prix" value="${empty produit ? 0 : produit.prix}" required>
    Stock <input type="number" min="0" name="stock" value="${empty produit ? 0 : produit.stock}" required>
    Image (URL) <input name="imageUrl" value="${produit.imageUrl}">
    Catégorie
    <select name="categorieId">
      <c:forEach var="c" items="${categories}">
        <option value="${c.id}" ${produit.categorie.id == c.id ? 'selected' : ''}>${c.libelle}</option>
      </c:forEach>
    </select>
    <button class="btn">Enregistrer</button>
    <a class="btn grey" href="${pageContext.request.contextPath}/ProduitServlet?action=lister">Annuler</a>
  </form>
</main></body></html>
