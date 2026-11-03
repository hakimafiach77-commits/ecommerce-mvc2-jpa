<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html><html><head><meta charset="UTF-8"><title>Panier</title></head><body>
<%@ include file="header.jspf" %>
<main>
  <h2>Mon panier</h2>
  <c:choose>
  <c:when test="${empty panier or empty panier.lignes}">
    <p>Votre panier est vide.</p>
  </c:when>
  <c:otherwise>
    <table>
      <tr><th>Produit</th><th>Prix unitaire</th><th>Quantité</th><th>Sous-total</th><th></th></tr>
      <c:forEach var="l" items="${panier.lignes}">
        <tr>
          <td>${l.produit.designation}</td>
          <td><fmt:formatNumber value="${l.produit.prix}" minFractionDigits="2"/></td>
          <td>
            <form method="get" action="${pageContext.request.contextPath}/PanierServlet" style="display:inline">
              <input type="hidden" name="action" value="modifier">
              <input type="hidden" name="ligneId" value="${l.id}">
              <input type="number" name="quantite" value="${l.quantite}" min="1" style="width:60px">
              <button class="btn grey">OK</button>
            </form>
          </td>
          <td><fmt:formatNumber value="${l.sousTotal}" minFractionDigits="2"/> MAD</td>
          <td><a class="btn red" href="${pageContext.request.contextPath}/PanierServlet?action=retirer&ligneId=${l.id}">Retirer</a></td>
        </tr>
      </c:forEach>
      <tr><th colspan="3" style="text-align:right">Total</th>
          <th colspan="2"><fmt:formatNumber value="${panier.total}" minFractionDigits="2"/> MAD</th></tr>
    </table>
    <p><a class="btn red confirm" href="${pageContext.request.contextPath}/PanierServlet?action=vider">Vider le panier</a></p>
  </c:otherwise>
  </c:choose>
  <p><a href="${pageContext.request.contextPath}/ProduitServlet?action=lister">← Continuer mes achats</a></p>
</main>
<script>$('.confirm').on('click', function(){ return confirm('Vider le panier ?'); });</script>
</body></html>
