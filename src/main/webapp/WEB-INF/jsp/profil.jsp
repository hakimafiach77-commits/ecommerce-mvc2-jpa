<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html><html><head><meta charset="UTF-8"><title>Profil</title></head><body>
<%@ include file="header.jspf" %>
<main>
  <h2>Mon profil</h2>
  <form class="box" method="post" action="${pageContext.request.contextPath}/InternauteServlet">
    <input type="hidden" name="action" value="modifier">
    Email <input value="${sessionScope.internaute.email}" disabled>
    Nom <input name="nom" value="${sessionScope.internaute.nom}" required>
    Prénom <input name="prenom" value="${sessionScope.internaute.prenom}" required>
    Adresse <input name="adresse" value="${sessionScope.internaute.adresse}">
    <button class="btn">Mettre à jour</button>
    <a class="btn red confirm" href="${pageContext.request.contextPath}/InternauteServlet?action=supprimer">Supprimer mon compte</a>
  </form>
</main>
<script>$('.confirm').on('click', function(){ return confirm('Supprimer définitivement le compte ?'); });</script>
</body></html>
