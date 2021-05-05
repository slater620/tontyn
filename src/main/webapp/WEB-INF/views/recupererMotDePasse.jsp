<%-- 
    Document   : findUser
    Created on : 29 avr. 2021, 06:00:38
    Author     : tanankem
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reinitialiser mot de passe</title>
    </head>
    <body>
       <div align='center'>
            <img src="<c:url value="/ressources/images/logo.png"/>" alt='logo'>
            <h1><b>Réinitialiser mot de passe</b></h1>
        </div>
            
        <div align="center">
            <c:choose>
                <c:when test="${error=='true'}">
                    <span style="color: red">Echec de connexion.<br>Adresse mail ou mot de passe incorect.<br><br></span>
                </c:when>
                
                <c:otherwise>
                    Entrez votre adresse mail. Si un compte existe pour cette adresse, un lien vous sera envoyé pour initialiser votre mot de passe.
                    <br>
                    <br>
                </c:otherwise>
            </c:choose>
            
            <form action="<c:url value="/mot_de_passe_oublie/traitement"></c:url>" method='post'>
                Adresse mail: <input id="email" name="email" value="${email}" required="true"/><br><br>
                <br>
                <input type="submit" value="Envoyer le mail"/>
            </form>
            <br>
            <br>
            <a href="/tontyn/connexion">Se connecter</a><br><br>
            <a href="/tontyn/creerCompte">Créer un compte</a>
            <br>
            <br>
        </div>
    </body>
</html>
