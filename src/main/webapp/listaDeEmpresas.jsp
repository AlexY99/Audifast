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
                            <a class="nav-link active" href="#">P&aacute;gina Principal</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link  disabled" aria-current="page" href="EmpresaServlet?accion=listaEmpresas">Empresas Registradas</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="registroEmpresa.jsp">Registro Empresa</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Mis plantillas</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Datos Personales</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Cerrar Sesión</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>


        <div class="container">
            <br/>
            <h2 id="encabezado">Mis Empresas Registradas</h2>
            <br/>

            <div class="tab-content" id="listaEmpresas">
                <div id="lideradas" class="tab-pane fade show active" role="tabpanel" aria-labelledby="lideradas-tab">
                    <br/>
                    <h3>Empresas</h3>
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
                                <td><p class="text-center"><c:out value="${empresa.entidad.getId().rfc}"/></p></td>
                                <td><p class="text-center"><c:out value="${empresa.entidad.nombre}"/></p></td>
                                <td><p class="text-center"><c:out value="${empresa.entidad.giro}"/></p></td>
                                <td><p class="text-center"><c:out value="${empresa.entidad.direccionO}"/></p></td>
                                <td><p class="text-center"><c:out value="${empresa.entidad.direccionF}"/></p></td>
                                <td><a class='badge badge-danger' href ='EmpresaServlet?accion=eliminar&rfc=${empresa.entidad.getId().rfc}'>Eliminar</a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>


        </div>
    </body>
</html>

