<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="resources\css\bootstrap.css">
        <link rel="stylesheet" href="resources\css\auditoria.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
        <link rel="stylesheet" href="resources\plugins\alertifyjs\css\alertify.min.css" />
        <link rel="stylesheet" href="resources\plugins\alertifyjs\css\themes\bootstrap.min.css" />
        <script src="resources\js\jquery-3.6.0.js"></script>     
        <script src="resources\js\all.min.js"></script>     
        <script src="resources\js\bootstrap.bundle.min.js" async="async"></script>
        <script src="resources\plugins\alertifyjs\alertify.min.js"></script>
        <title>AF-Retroalimentación</title>
    </head>
    <body>
        <c:choose>
            <c:when test="${Invitado}">
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
            </c:when>
            <c:otherwise>
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
                                    <a class="nav-link" href="NormaServlet?accion=listaNormas">Normas Registradas</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="AuditorServlet?accion=Plantillas">Mis plantillas</a>
                                </li>
                                <li class="nav-item">
                                    <form id="form-infoAuditor"action="AuditorServlet?accion=InfoAuditor" method="POST">
                                        <input type="hidden" id="txtCorreo" value="${CorreoAuditor}" name="txtCorreo">
                                        <input class="btn btn-link nav-link" type="submit" name="" value="Consulta de Auditor" form='form-infoAuditor'>
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
            </c:otherwise>
        </c:choose>

        <div class="container">
            <br/>
            <h2 id="encabezado">Retroalimentaci&oacute;n de Auditoría <c:out value="${auditoria.entidad.id}"/></h2>
            <div id="datosOrganizacion" class="apartado">
                <h3 id="encabezadoDatos" class="encabezadoApartado">Datos de la Organización Auditada</h3>
                RFC:  <c:out value="${auditoria.entidad.organizacion.getId().rfc}"/><br/>
                Nombre:  <c:out value="${auditoria.entidad.organizacion.nombre}"/><br/>
                Giro de la Empresa: <c:out value="${auditoria.entidad.organizacion.giro}"/><br/>
                Direccion de operacion:  <p><c:out value="${auditoria.entidad.organizacion.direccionO}"/></p>
            </div>

            <br/>

            <div id="datosActa" class="apartado">
                <h3 id="encabezadoActa" class="encabezadoApartado">Documentos Auditor&iacute;a</h3>
                <div class="row">
                    <div class="col-4">
                        <form target="_blank" method="POST" action="AuditoriaServlet?accion=Reporte" id="form-ReportePDF">
                            <input type="hidden" name="txtIdAuditoria" value='${auditoria.entidad.id}' />
                            <button class='btn btn-primary' type='submit' form='form-ReportePDF'>
                                Reporte de Auditoria
                            </button>
                        </form>
                    </div>

                    <c:choose>
                        <c:when test="${Invitado}">
                            <div class="col-6">
                                <button type="button" class="btnAdd btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalFormCorrectivo">
                                    Subir Plan de Acci&oacute;n
                                </button>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="col-6">
                                <form target="_blank" method="POST" action="RetroalimentacionServlet?accion=ConsultaCorrectivo" id="form-CorrectivoPDF">
                                    <input type="hidden" name="txtIdAuditoria" value='${auditoria.entidad.id}' />
                                    <button class='btn btn-primary' type='submit' form='form-CorrectivoPDF'>
                                        Consulta Plan de Acci&oacute;n Correctivo
                                    </button>
                                </form>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
                <br/>
            </div>
            <br/>
            <c:if test="${!Invitado}">
                <div id="Claves" class="apartado">
                    <h3 id="encabezadoProductos" class="encabezadoApartado">Claves de acceso</h3>
                    <div class="table-responsive">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th scope="col">Correo</th>
                                    <th scope="col">Acci&oacute;n</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items ="${Claves}" var = "clave">
                                    <tr>
                                        <td><c:out value="${clave.entidad.correo}"/></td>
                                        <c:if test="${permisoEdicion}">
                                    <form method="POST" action="RetroalimentacionServlet?accion=EliminarClave" id="form-${clave.entidad.clave}">
                                        <input type="hidden" name="txtClave" value='${clave.entidad.clave}' />
                                        <input type="hidden" name="txtIdAuditoria" value='${auditoria.entidad.id}' />
                                        <td>
                                            <button class='btn btn-danger' style="font-size: 0.6rem;" type='submit' form='form-${clave.entidad.clave}'>
                                                X
                                            </button>
                                        </td>
                                    </form>
                                </c:if>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <c:if test="${permisoEdicion}">
                        <button type="button" class="btnAdd btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalFormClave">
                            Agregar Clave
                        </button>
                    </c:if>
                </div>
            </c:if>
        </div>

        <div class="modal fade" id="modalFormCorrectivo" tabindex="-1" aria-labelledby="modalFormCorrectivoTitulo" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="modalFormCorrectivoTitulo">Plan de acciones correctivas</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <form action="RetroalimentacionServlet?accion=PlanPrueba" method="POST">
                        <div class="modal-body">
                            <p>Subir Plan de Acci&oacute;n Correctivo</p>
                            <input type="file" id="CorrectivoFile" name="CorrectivoFile">
                            <input type="hidden" value="${auditoria.entidad.id}" name="txtIdAuditoria" />
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                            <input type="submit" class="btn btn-primary" value="Subir">
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="modal fade" id="modalFormClave" tabindex="-1" aria-labelledby="modalFormClaveTitulo" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="modalFormClaveTitulo">Agregar Clave</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <form action="RetroalimentacionServlet?accion=AlmacenarClave" method="POST">
                        <div class="modal-body">
                            <label for="txtCorreo">Correo</label>
                            <input type="text" class="form-control" id="txtCorreo" name="txtCorreo" placeholder="Correo para dar acceso"> 
                            <br/>
                            <label for="txtClaveAcceso">Clave</label>
                            <input type="text" class="form-control" id="txtClaveAcceso" name="txtClaveAcceso" placeholder="Clave">
                            <input type="hidden" value="${auditoria.entidad.id}" name="txtIdAuditoria" />
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                            <input type="submit" class="btn btn-primary" value="Registrar">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>

