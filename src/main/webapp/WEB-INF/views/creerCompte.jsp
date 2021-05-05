<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>Creer compte</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <div align='center'>
            <img src="<c:url value="/ressources/images/logo.png"/>" alt='logo'>
            <h1><b>Créer compte</b></h1>
        </div>
        
        <div align='center'>
            <c:choose>
                <c:when test="${statut=='success'}">
                    <span style="color: green"><br>Votre compte a bien été créé!</span>
                </c:when>
                    
                <c:otherwise>
                    <c:if test="${statut=='failed'}">
                        <span style="color: red">Echec! Un compte a déjà été créé avec cette adresse mail.<br><br></span>
                    </c:if>
                    <form action="/tontyn/creerCompte/traitement" method="post">
                        Nom: <input id="nom" name='nom' value="${utilisateur.nom}" required="true"/><br><br>
                        Prénom: <input id="prenom" name='prenom' value='${utilisateur.prenom}' required="true"/><br><br>
                        Adresse mail: <input id="email" name="email" required="true"/><br><br>
                        Mot de passe: <input type="password" id="motDePasse" name="motDePasse" required="true"/><br><br>

                        <input type="submit" value="Créer compte"/>
                   </form>
                </c:otherwise>
            </c:choose>
            
            <a href="/tontyn/connexion"><br><br><b>Se connecter</b></a>
            <a href="/tontyn/creerCompte"><br><br><b>Créer un compte</b></a>
            
        </div>
        
    </body>
</html>
