<%-- 
    Document   : plantilla
    Created on : 19 may. 2021, 12:45:52
    Author     : azul-
--%>
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
        <link rel="stylesheet" href="resources\css\IconColors.css">
        <script src="resources\js\all.min.js"></script>   
        <script src="resources\js\jquery-3.6.0.js"></script>     
        <script src="resources\js\bootstrap.bundle.min.js" async="async"></script>
        <title>AF-Auditoría</title>
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
                            <a class="nav-link active" href="AuditorServlet?accion=Inicio">P&aacute;gina Principal</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="EmpresaServlet?accion=listaEmpresas">Empresas Registradas</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="registroEmpresa.jsp">Registro Empresa</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="ServletPlantilla?accion=listaPlantillas">Mis plantillas</a>
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
            <h2 id="encabezado">Edición de Plantilla</h2>
            <h2 id="encabezado">Plantilla Ejemplo 1</h2>
            <%-- <h2 id="encabezado"><c:out value="${plantilla.entidad.nombre}"/></h2> --%>
            <br/>
            <div id="Procesos" class="apartado">
                <%! int contador = 0; %>
                <c:forEach begin="1" step="1" end="2" var="cuenta"> <%-- Cambiar el 2 por el numero de procesos guardados recibido del servlet --%>
                    <% contador = 0; %>
                    <h4 id="encabezadoProceso${cuenta}" class="encabezadoApartado"><c:out value="${cuenta}"/>. Proceso X<%-- <c:out value="${procesos[cuenta].entidad.descripcion}}"/> --%></h4>
                    <div class="table-responsive">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th scope="col">ID Requisito</th>
                                    <th scope="col">Norma Asociada</th>
                                    <th scope="col">Descripcion del Requisito</th>
                                    <th scope="col">Eliminar</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td><c:out value="${cuenta}"/>.<%= ++contador %></td>
                                    <td>iso-25010</td>
                                    <td>Calidad de software</td>
                                    <form method="POST" action="PlantillaServlet?accion=EliminarRequisito" id="form-${requisito.entidad.id}">
                                        <input type="hidden" name="txtIdRequisito" value='${requisito.entidad.id}' />
                                        <td>
                                            <button class='btn btn-danger' style="font-size: 0.6rem;" type='submit' form='form-${requisito.entidad.id}'>
                                                X
                                            </button>
                                        </td>
                                    </form>
                                </tr>
                            <%--<c:forEach items ="${listaRequisitos[cuenta-1]}" var = "requisito">
                            <tr>
                                <td><c:out value="${requisito.entidad.id}"/></td>
                                <td><c:out value="${requisito.entidad.norma}"/></td>
                                <td><c:out value="${prequisito.entidad.descripcion}"/></td>
                                <form method="POST" action="PlantillaServlet?accion=EliminarRequisito" id="form-${requisito.entidad.id}">
                                <input type="hidden" name="txtIdRequisito" value='${requisito.entidad.id}' />
                                <td>
                                    <button class='btn btn-danger' style="font-size: 0.6rem;" type='submit' form='form-${requisito.entidad.id}'>
                                        X
                                    </button>
                                </td>
                                </form>
                            </tr>
                        </c:forEach>--%>
                            </tbody>
                        </table>
                    </div>
                    <div class="row">
                        <div class="col-3">
                            <button type="button" class="btnAdd btn btn-primary" style="font-size: 0.8rem;" data-bs-toggle="modal" data-bs-target="#modalFormRequisito${cuenta}">
                                Agregar requisito
                            </button>
                        </div>
                        <div class="col-3">
                            <form method="POST" action="PlantillaServlet?accion=EliminarRequisito" id="formDelete-${listaProcesos[cuenta-1].entidad.id}">
                                <input type="hidden" name="txtIdProceso" value='${listaProcesos[cuenta-1].entidad.id}' />
                                <td>
                                    <button class='btn btn-danger' style="font-size: 0.8rem;" type='submit' form='form-${listaProcesos[cuenta].entidad.id}'>
                                        Eliminar proceso
                                    </button>
                                </td>
                            </form>
                        </div>
                    </div>

                    <!--modalFormAuditorAuxiliar  -->
                    <div class="modal fade" id="modalFormRequisito${cuenta}" tabindex="-1" aria-labelledby="modalFormNuevoRequisito" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered" role="document">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="modalFormAuditorAuxiliarTitulo">Agregar Requisito al proceso <c:out value="${cuenta}"/></h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <form action="PlantillaServlet?accion=AlmacenarRequisito" method="POST">
                                        <div class="modal-body">
                                            <label for="txtNorma">Clave de norma Asociada</label>
                                            <select name="select">
                                                <c:forEach items="${listaNormas}" var="norma">
                                                    <option value="${norma.entidad.clave}"><c:out value="${norma.entidad.clave}"/></option>
                                                </c:forEach>
                                                <option value="value1">iso-25100</option>
                                                <option value="value2" selected>Iso-25010</option>
                                            </select>
                                            <button type="button" class="btn " id="btnAdd" data-bs-toggle="modal" data-bs-target="#modalFormRegistroNorma"><i class="fas fa-plus bi-align-center fa-1x greenIcon"></i></button>
                                            
                                            <br/>
                                            <label for="txtDescripcion">Ingrese el nombre o descripcion del requisito</label>
                                            <textarea id='txtDescripcion' name="txtDescripcion" rows="4" cols="50"/></textarea>
                                            <input type="hidden" name="txtIdProceso" value='${listaProcesos[cuenta-1].entidad.id}' />
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

                    <br/>
                </c:forEach>
                <br/>
                <button type="button" class="btnAdd btn btn-primary" style="font-size: 0.8rem;" data-bs-toggle="modal" data-bs-target="#modalFormAgregarProceso">
                    Agregar Proceso
                </button>
            </div>
            <br/> 
        </div>

        <!--modalFormAgregarProceso  -->
        <div class="modal fade" id="modalFormAgregarProceso" tabindex="-1" aria-labelledby="modalFormAgregarProcesoTitulo" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="modalFormAuditorAuxiliarTitulo">Agregar Proceso</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <form action="PlantillaServlet?accion=AlmacenarProceso" method="POST">
                            <div class="modal-body">
                                <label for="txtProcesoNombre">Ingrese el nombre o descripcion del nuevo proceso.</label>
                                <textarea id='txtProcesoNombre' name="txtProcesoNombre" rows="4" cols="50"/></textarea>
                                <input type="hidden" name="txtIdPlantilla" value='${plantilla.entidad.id}'/>
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

        <!--modalmodalFormRegistroNorma -->
        <div class="modal fade" id="modalFormRegistroNorma" tabindex="-1" aria-labelledby="modalFormRegistroNormaTitulo" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="modalFormContactoTitulo">Agregar Contacto</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <form action="PlantillaServlet?accion=AlmacenarNorma" method="POST">
                            <div class="modal-body">
                                <label for="txtClaveNorma">Clave de la Norma</label>
                                <input type="text" class="form-control" id="txtClaveNorma" name="txtClave" placeholder="XXXX-XXXX-XXXX">
                                <br/>
                                <label for="txtNormaNombre">Nombre</label>
                                <input type="text" class="form-control" id="txtNormaNombre" name="txtNormaNombre" placeholder="Nombre de la norma">  
                                <br/>
                                <label for="txtNormaDesc">Descripci&oacute;n</label>
                                <input type="text" class="form-control" id="txtNormaDesc" name="txtNormaDesc" placeholder="Descripción de la norma">  
                                <input type="hidden" value="${plantilla.entidad.id}" name="txtIdPlantilla" />
                                <br/>
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
    </body>
</html>
