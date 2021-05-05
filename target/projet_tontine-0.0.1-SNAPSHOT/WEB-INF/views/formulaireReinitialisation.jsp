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
        <div align="center">
            Id: <c:out value="${id}"/>
            
            <form action="<c:url value="/reinitialisatonMotDePasse/traitement"></c:url>" method='post'>
                Nouveau mot de passe: <input type='password' id="email" name="motDePasse1" required="true"/><br><br>
                Entrez de nouveau le mot de passe: <input type='password' id='motDePasse' name="motDePasse2" required="true"/><br>
                <br>
                
                <input type="submit" value="Enregistrer"/>
            </form>
            <br>
            <br>
            <a href="/tontyn/mot_de_passe_oublie/">Mot de passe oublié</a><br><br>
            <a href="/tontyn/creerCompte">Créer un compte</a>
            <br>
            <br>
        </div>
    </body>
</html>
