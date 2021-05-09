<%-- 
    Document   : reportReinitialisationMotDePasse
    Created on : 6 mai 2021, 03:23:47
    Author     : tanankem
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>report</title>
    </head>
    <body>
        <div align='center'>
            <img src="<c:url value="/ressources/images/logo.png"/>" alt='logo'>
            <h1><b>Réinitialisation mot de passe</b></h1>
        </div>
        
        <div align="center">
            <c:choose>
                <c:when test="${statut=='succes'}">
                    Le lien de réinitialisation de votre mot de passe a été envoyé.<br>
                    consultez votre boîte mail pour y accéder.<br>
                </c:when>
                <c:when test="${statut=='echec'}">
                    <span style="color: red">
                        Echec!!<br><br>
                        Aucun compte ne correspond à cette adresse mail.
                    </span>
                </c:when>
                <c:when test="${statut=='modifie'}">
                    <span style="color:green"><b>Mot de passe modifié avec succés.</b></span>
                </c:when>
            </c:choose>
        </div>
           
        <div align='center'>
            <a href="/tontyn/connexion"><br><br><b>Se connecter</b></a>
            <a href="/tontyn/creerCompte"><br><br><b>Créer un compte</b></a>
        </div>
        
    </body>
</html>
