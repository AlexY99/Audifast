<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="resources\css\bootstrap.css">
        <link rel="stylesheet" href="resources\css\inicio.css">
        <link rel="stylesheet" href="resources\css\auditoria.css">
        <link rel="stylesheet" href="resources\plugins\alertifyjs\css\alertify.min.css" />
        <link rel="stylesheet" href="resources\plugins\alertifyjs\css\themes\bootstrap.min.css" />
        <script src="resources\js\jquery-3.6.0.js"></script>     
        <script src="resources\js\all.min.js"></script>     
        <script src="resources\js\bootstrap.bundle.min.js" async="async"></script>
        <script src="resources\plugins\alertifyjs\alertify.min.js"></script>
        <title>AF-Empresas</title>
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
                            <a class="nav-link" href="EmpresaServlet?accion=listaEmpresas">Empresas Registradas</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link disabled" aria-current="page" href="NormaServlet?accion=listaNormas">Normas Registradas</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" aria-current="page" href="AuditorServlet?accion=Plantillas">Mis plantillas</a>
                        </li>
                        <li class="nav-item">
                            <form id="form-infoAuditor"action="AuditorServlet?accion=InfoAuditor" method="POST">
                                <input type="hidden" id="txtCorreo" value="${CorreoAuditor}" name="txtCorreo">
                                <input class="btn btn-link nav-link" aria-current="page" type="submit" name="" value="Consulta de Auditor" form='form-infoAuditor'>
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


        <div class="container">
            <br/>
            <h2 id="encabezado">Mis Normas Registradas</h2>
            <br/>

            <div id="Empresas" class="apartado">
                
                <ul class="nav nav-tabs">
                    <li class="nav-item">
                        <button class="nav-link active">Normas</button>
                    </li>
                    <li class="nav-item">
                        <button type="button" class="nav-link" id="btnAdd" data-bs-toggle="modal" data-bs-target="#modalRegistroNorma">Registrar Norma</button>
                    </li>
                </ul>
                
                <div class="table-responsive">
                    <table class="table">
                        <thead>
                            <tr>
                                <th scope="col">Clave</th>
                                <th scope="col">Nombre</th>
                                <th scope="col" class="text-center">Eliminar</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items ="${listaDeNormas}" var = "norma">
                                <tr>
                                    <td><p><c:out value="${norma.entidad.clave}"/></p></td>
                                    <td><p><c:out value="${norma.entidad.nombre}"/></p></td>
                                <form method="POST" action="NormaServlet?accion=eliminar" id="form-${norma.entidad.id}">
                                    <input type="hidden" name="id" value='${norma.entidad.id}' />
                                    <td class="text-center">
                                        <button class='btn btn-danger' style="font-size: 0.6rem;" type='submit' form='form-${norma.entidad.id}'>
                                            X
                                        </button>
                                    </td>
                                </form>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>


        </div>
                            
                            
        <!--El modal  -->
        <div class="modal fade" id="modalRegistroNorma" tabindex="-1" aria-labelledby="modalTitulo" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="modalTitulo">Registrar Norma</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <form action="NormaServlet?accion=almacenar" method="POST">
                            <div class="modal-body">
                                <p class="text-muted">Introduzca los datos de la norma a registrar</p> 
                                
                                <label for="txtClave">Clave</label>
                                <input type="text" class="form-control" id="txtClave" name="txtClave" placeholder="Clave de la norma">
                                <br/>
                                <label for="txtNombre">Nombre</label>
                                <input type="text" class="form-control" id="txtNombre" name="txtNombre" placeholder="Nombre">
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                                <input type="submit" class="btn btn-primary" value="Registrar">
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <c:if test="${not empty mensaje}">
            <script>
                alertify.alert('Mensaje','<div class="text-center">${mensaje}</div>');
            </script>  
        </c:if>
    </body>
</html>

