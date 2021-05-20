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
        <script src="resources\js\jquery-3.6.0.js"></script>     
        <script src="resources\js\bootstrap.min.js"></script>
        <title>AF-Empresas</title>
    </head>

    <body>        
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">Audifast</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link active" href="AuditorServlet?accion=Inicio">P&aacute;gina Principal</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link  disabled" aria-current="page" href="EmpresaServlet?accion=listaEmpresas">Empresas Registradas</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="registroEmpresa.jsp">Registro Empresa</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" aria-current="page" href="AuditorServlet?accion=Plantillas">Mis plantillas</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Datos Personales</a>
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
            <h2 id="encabezado">Mis Empresas Registradas</h2>
            <br/>

            <div id="Empresas" class="apartado">
                <h3 id="encabezadoEmpresas" class="encabezadoApartado">Empresas</h3>
                <div class="table-responsive">
                    <table class="table">
                        <thead>
                            <tr>
                                <th scope="col">RFC</th>
                                <th scope="col">Nombre</th>
                                <th scope="col">Giro</th>
                                <th scope="col">Direcci&oacute;n Operaci&oacute;n</th>
                                <th scope="col">Direcci&oacute;n Fiscal</th>
                                <th scope="col">Acci&oacute;n</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items ="${listaDeEmpresas}" var = "empresa">
                                <tr>
                                    <td><p><c:out value="${empresa.entidad.getId().rfc}"/></p></td>
                                    <td><p><c:out value="${empresa.entidad.nombre}"/></p></td>
                                    <td><p><c:out value="${empresa.entidad.giro}"/></p></td>
                                    <td><p><c:out value="${empresa.entidad.direccionO}"/></p></td>
                                    <td><p><c:out value="${empresa.entidad.direccionF}"/></p></td>
                                <form method="POST" action="EmpresaServlet?accion=eliminar" id="form-${empresa.entidad.getId().rfc}">
                                    <input type="hidden" name="rfc" value='${empresa.entidad.getId().rfc}' />
                                    <td class="text-center">
                                        <button class='btn btn-danger' style="font-size: 0.6rem;" type='submit' form='form-${empresa.entidad.getId().rfc}'>
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
    </body>
</html>

