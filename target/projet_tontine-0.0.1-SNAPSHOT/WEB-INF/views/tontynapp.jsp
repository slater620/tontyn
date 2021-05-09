<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>Tontyn app</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="<c:url value="/ressources/style.css"/>"">
    </head>
    <body>
        <div align='center'>
            <img src="<c:url value="/ressources/images/logo.png"/>" alt='logo'>
            <h1><b>Tontyn app / Accueil</b><h1>
        </div>
        <br>
        
        <div align="center">
            Connecté en tant que <b><c:out value="${utilisateur.getNom()} ${utilisateur.getPrenom()}"/></b>
        </div>
        
        <br>
        <br>
        
        <div align='center' id='menu'>
            <div style="padding-bottom: 20px">
                <a href="" class="lienMenu">Créer une tontine</a>
            </div >
            
            <div style="padding-bottom: 30px">
                <a href="" class="lienMenu">Rejoindre une tontine</a>
            </div>
            
            <div align="center">
                <b>Mes Tontines</b>
            </div>
        </div>
        
        <div align="center">
            <a href="<c:out value="/tontyn/logout"/>"><b>Se déconnecter</b></a>
        </div>
        
    </body>
</html>
