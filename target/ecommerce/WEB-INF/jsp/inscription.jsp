<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html><html><head><meta charset="UTF-8"><title>Inscription</title></head><body>
<%@ include file="header.jspf" %>
<main>
  <h2>Inscription</h2>
  <c:if test="${not empty erreur}"><div class="err">${erreur}</div></c:if>
  <form class="box" id="f" method="post" action="${pageContext.request.contextPath}/InternauteServlet">
    <input type="hidden" name="action" value="inscrire">
    Nom <input name="nom" required>
    Prénom <input name="prenom" required>
    Email <input type="email" name="email" required>
    Mot de passe <input type="password" id="mdp" name="motDePasse" minlength="4" required>
    Confirmation <input type="password" id="mdp2" required>
    Adresse <input name="adresse">
    <button class="btn">Créer mon compte</button>
  </form>
</main>
<script>
$('#f').on('submit', function(){
  if ($('#mdp').val() !== $('#mdp2').val()) { alert('Les mots de passe ne correspondent pas.'); return false; }
});
</script></body></html>
