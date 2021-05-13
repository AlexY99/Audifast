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
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
        <script src="resources\js\jquery-3.6.0.js"></script>     
        <script src="resources\js\bootstrap.bundle.min.js" async="async"></script>
        <title>AF-Inicio</title>
    </head>

    <body>        
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">AudiFast</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="#">P&aacute;gina Principal</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="EmpresaServlet?accion=listaEmpresas">Empresas Registradas</a>
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
            <h2 id="encabezado">Listado de Auditorías</h2>
            <br/>
            
            
            <div id="listasAuditorias-tab-holder">
                
                <ul class="nav nav-tabs" id="auditoriasTab" role="tablist">
                    <li class="nav-item" role="presentation">
                        <button class="nav-link active" id="lideradas-tab" data-bs-toggle="tab" data-bs-target="#lideradas" type="button" role="tab" aria-controls="home" aria-selected="true">Auditorías Lideradas</button>
                    </li>
                    <li class="nav-item" role="presentation">
                        <button class="nav-link" id="auxiliadas-tab" data-bs-toggle="tab" data-bs-target="#auxiliadas" type="button" role="tab" aria-controls="profile" aria-selected="false">Auditorías Auxiliadas</button>
                    </li>
                    <li class="nav-item">
                        <button type="button" class="nav-link" id="btnAdd" data-bs-toggle="modal" data-bs-target="#modalRegistroAuditoria">Registrar Auditoría</button>
                    </li>
                </ul>

                    
                    
                <div class="tab-content" id="listasAuditorias">
                    <div id="lideradas" class="tab-pane fade show active" role="tabpanel" aria-labelledby="lideradas-tab">
                        <br/>
                        <h3>Auditorías Lideradas</h3>
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th scope="col" class="text-center">#</th>
                                    <th scope="col" class="text-center">Organización</th>
                                    <th scope="col" class="text-center">Fecha de Registro</th>
                                    <th scope="col" class="text-center">Cancelar Auditoría</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items ="${listaAuditoriasLideradas}" var = "auditoriaLid">
                                    <tr>
                                        
                                        <td class="text-center"><a href='AuditoriaServlet?accion=Info&id=${auditoriaLid.entidad.id}'><c:out value="${auditoriaLid.entidad.id}"/></a></td>
                                        <td class="text-center"><c:out value="${auditoriaLid.entidad.rfc_organizacion}"/></td>
                                        <td class="text-center"><c:out value="${auditoriaLid.entidad.fecha()}"/></td>
                                        <td class="text-center"><a href ='AuditoriaServlet?accion=Cancelar&id=${auditoriaLid.entidad.id}'><i class="bi bi-x-octagon-fill" style="font-size: 1.2rem; color: red;"/></a></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div id="auxiliadas" class="tab-pane fade" role="tabpanel" aria-labelledby="auxiliadas-tab">
                        <br/>
                        <h3>Auditorías Auxiliadas</h3>
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th scope="col" class="text-center">#</th>
                                    <th scope="col" class="text-center">Organización</th>
                                    <th scope="col" class="text-center">Correo Auditor Lider</th>
                                    <th scope="col" class="text-center">Fecha de Registro</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items ="${listaAuditoriasAuxiliadas}" var = "auditoriaAux">
                                    <tr>
                                        <td><p class="text-center"><c:out value="${auditoriaAux.entidad.id}"/></p></td>
                                        <td><p class="text-center"><c:out value="${auditoriaAux.entidad.rfc_organizacion}"/></p></td>
                                        <td><p class="text-center"><c:out value="${auditoriaAux.entidad.correo_auditor_lider}"/></p></td>
                                        <td><p class="text-center"><c:out value="${auditoriaAux.entidad.fecha_registro}"/></p></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
           
        </div>
                            
        <!--El modal  -->
        <div class="modal fade" id="modalRegistroAuditoria" tabindex="-1" aria-labelledby="modalTitulo" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="modalTitulo">Registrar Auditor&iacute;a</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <form action="AuditoriaServlet?accion=Almacenar" method="POST">
                            <div class="modal-body">
                                <p>Ingrese el RFC de la organización a auditar, debe haberla registrado previamente.</p>
                                <label for="txtRFC">RFC de la Organización a Auditar</label>
                                <input type="text" class="form-control" id="txtRFC" name="txtRFC" placeholder="RFC">  
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                                <input type="submit" class="btn btn-primary" value="Registrarse">
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        
    </body>
</html>

