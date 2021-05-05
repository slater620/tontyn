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
        <title>Connexion</title>
    </head>
    <body>
       <div align='center'>
            <img src="<c:url value="/ressources/images/logo.png"/>" alt='logo'>
            <h1><b>Connexion</b></h1>
        </div>
            
        <div align="center">
            <c:if test="${error == 'true'}">
                <span style="color: red">Echec de connexion.<br>Adresse mail ou mot de passe incorect.<br><br></span>
            </c:if>
            
            <form action="<c:url value="/j_spring_security_check"></c:url>" method='post'>
                Adresse mail: <input id="email" name="email" value="${email}" required="true"/><br><br>
                Mot de passe: <input type='password' id='motDePasse' name="motDePasse" required="true"/><br>
                <br>
                <input type="checkbox" id="remember-me" name="remember-me"/>
                <label for="remember-me">Remember me</label><br><br>
                
                <input type="submit" value="Se connecter"/>
            </form>
            <br>
            <br>
            <a href="<c:url value="/mot_de_passe_oublie"/>">Mot de passe oublié</a><br><br>
            <a href="/tontyn/creerCompte">Créer un compte</a>
            <br>
            <br>
        </div>
    </body>
</html>
