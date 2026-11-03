<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html><html><head><meta charset="UTF-8"><title>Connexion</title></head><body>
<%@ include file="header.jspf" %>
<main>
  <h2>Connexion</h2>
  <c:if test="${not empty erreur}"><div class="err">${erreur}</div></c:if>
  <form class="box" method="post" action="${pageContext.request.contextPath}/InternauteServlet">
    <input type="hidden" name="action" value="connecter">
    Email <input type="email" name="email" required>
    Mot de passe <input type="password" name="motDePasse" required>
    <button class="btn">Se connecter</button>
  </form>
  <p>Pas de compte ? <a href="${pageContext.request.contextPath}/InternauteServlet?action=formInscription">S'inscrire</a></p>
</main></body></html>
