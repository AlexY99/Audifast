<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="resources\css\bootstrap.css">
        <link rel="stylesheet" href="resources\css\infoAuditor.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
        <link rel="stylesheet" href="resources\plugins\alertifyjs\css\alertify.min.css" />
        <link rel="stylesheet" href="resources\plugins\alertifyjs\css\themes\bootstrap.min.css" />
        <script src="resources\js\jquery-3.6.0.js"></script>     
        <script src="resources\js\all.min.js"></script>     
        <script src="resources\js\bootstrap.bundle.min.js" async="async"></script>
        <script src="resources\plugins\alertifyjs\alertify.min.js"></script>
        <title>AF-InfoAuditor</title>
    </head>

    <body>        
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">
                    <img src="resources\imagenes\logo.png" width="150" height="50" alt="" class="d-inline-block align-top">
                </a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link" href="AuditorServlet?accion=Inicio">P&aacute;gina Principal</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="AuditorServlet?accion=Inicio">P&aacute;gina Principal</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="EmpresaServlet?accion=listaEmpresas">Empresas Registradas</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="NormaServlet?accion=listaNormas">Normas Registradas</a>
                        </li>
                        <li class="nav-item">
                            <form id="form-infoAuditor"action="AuditorServlet?accion=InfoAuditor" method="POST">
                                <input type="hidden" id="txtCorreo" value="${CorreoAuditor}" name="txtCorreo">
                                <input class="btn btn-link nav-link disabled" aria-current="page" type="submit" name="" value="Consulta de Auditor" form='form-infoAuditor'>
                            </form>                            
                        </li>
                    </ul>        
                    <ul class="navbar-nav ms-auto">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                <c:out value="${CorreoAuditor}"/>
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="AuditorServlet?accion=Logout">Cerrar Sesión</a></li>
                            </ul>
                        </li>      
                    </ul>
                </div>
            </div>
        </nav>
        <br/>

        <div class="container">
            <br/>
            <div id="datosAuditor" class="box">
                <h2 class="encabezado">Consulta de Auditor</h2>
                
                <form action="AuditorServlet?accion=InfoAuditor" method="POST">
                        <label for="txtCorreo">Correo Electrónico:</label>
                        <input type="email" class="form-control" id="txtCorreo" value="${auditor.entidad.correo}" name="txtCorreo" placeholder="correo@electronico.com">
                        <input type="submit" value="Consultar">
                </form>
                <br/>
                
                <label for="txtCorreo">Nombre Completo:</label>
                <input type="text" class="form-control" id="txtNombre" value="${auditor.entidad.nombre}" placeholder="Nombre Completo" disabled>

                <c:if test="${propioPerfil}">
                    <label for="txtTelefono">Teléfono:</label>
                    <input type="text" class="form-control" id="txtTelefono"value="${auditor.entidad.telefono}" placeholder="Teléfono" disabled>
                </c:if>
            </div>                        
        </div>
    </body>
</html>
