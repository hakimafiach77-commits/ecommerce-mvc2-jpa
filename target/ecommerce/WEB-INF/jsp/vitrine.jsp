<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html><html><head><meta charset="UTF-8"><title>Vitrine</title></head><body>
<%@ include file="header.jspf" %>
<main>
  <h2>Vitrine</h2>
  <form method="get" action="${pageContext.request.contextPath}/ProduitServlet" style="margin-bottom:16px">
    <input type="hidden" name="action" value="lister">
    <input name="q" placeholder="Rechercher..." value="${param.q}">
    <select name="cat">
      <option value="">Toutes les catégories</option>
      <c:forEach var="c" items="${categories}">
        <option value="${c.id}" ${param.cat == c.id ? 'selected' : ''}>${c.libelle}</option>
      </c:forEach>
    </select>
    <button class="btn">Filtrer</button>
  </form>
  <div class="grid">
    <c:forEach var="p" items="${produits}">
      <div class="card">
        <h3>${p.designation}</h3>
        <small>${p.categorie.libelle}</small>
        <p>${p.description}</p>
        <p class="prix"><fmt:formatNumber value="${p.prix}" minFractionDigits="2"/> MAD</p>
        <p>Stock : ${p.stock}</p>
        <a class="btn grey" href="${pageContext.request.contextPath}/ProduitServlet?action=detail&id=${p.id}">Détail</a>
        <a class="btn" href="${pageContext.request.contextPath}/PanierServlet?action=ajouter&produitId=${p.id}&quantite=1">Ajouter au panier</a>
        <a class="btn grey" href="${pageContext.request.contextPath}/ProduitServlet?action=editer&id=${p.id}">✎</a>
        <a class="btn red confirm" href="${pageContext.request.contextPath}/ProduitServlet?action=supprimer&id=${p.id}">🗑</a>
      </div>
    </c:forEach>
  </div>
  <c:if test="${empty produits}"><p>Aucun produit trouvé.</p></c:if>
</main>
<script>$('.confirm').on('click', function(){ return confirm('Confirmer la suppression ?'); });</script>
</body></html>
