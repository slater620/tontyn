<%-- 
    Document   : formulaireReinitialisation
    Created on : 4 mai 2021, 11:48:07
    Author     : tanankem
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Formulaire reinitialisation mot de passe</title>
    </head>
    <body>
        <div align='center'>
            <img src="<c:url value="/ressources/images/logo.png"/>" alt='logo'>
            <h1><b>Formulaire réinitialisation de mot de passse</b></h1>
        </div>
        <div align="center">
            
            <span style="color:red"><b><c:out value="${message}"/></b></span><br><br>
            
            <form action="<c:url value="/reinitialiserMotDePasse/traitement/${id}"></c:url>" method='post'>
                Nouveau mot de passe: <input type='password' id="email" name="motDePasse1" required="true"/><br><br>
                Entrez de nouveau le mot de passe: <input type='password' id='motDePasse' name="motDePasse2" required="true"/><br>
                <br>
                <br>
                <input type="submit" value="Enregistrer"/>
            </form>
            <a href="/tontyn/connexion"><br><br><b>Se connecter</b></a>
            <br><br><br>
            <a href="/tontyn/creerCompte">Créer un compte</a>
            <br>
            <br>
        </div>
    </body>
</html>
