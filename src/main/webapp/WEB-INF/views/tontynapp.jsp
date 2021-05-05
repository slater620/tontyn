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
    </head>
    <body>
        <div align='center'>
            <img src="<c:url value="/ressources/images/logo.png"/>" alt='logo'>
            <h1><b>Tontyn app</b><h1>
        </div>
        <br>
        
        Connecté en tant que .
        <br>
        <br>
        
        <div align="center">
            <a href="<c:out value="/tontyn/logout"/>"><b>Se déconnecter</b></a>
        </div>
        
    </body>
</html>
