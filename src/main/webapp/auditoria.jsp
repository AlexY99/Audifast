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
        <title>AF-Auditoría</title>
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


        <div class="container">
            <br/>
            <h2 id="encabezado">Auditoría <c:out value="${auditoria.entidad.id}"/></h2>
            <input type="hidden" id="txtIdAuditoria" name="txtIdAuditoria" value='${auditoria.entidad.id}' />

            <div id="datosOrganizacion" class="apartado">
                <h3 id="encabezadoDatos" class="encabezadoApartado">Datos de la Organización Auditada</h3>
                RFC:  <c:out value="${auditoria.entidad.organizacion.getId().rfc}"/><br/>
                Nombre:  <c:out value="${auditoria.entidad.organizacion.nombre}"/><br/>
                Giro de la Empresa: <c:out value="${auditoria.entidad.organizacion.giro}"/><br/>
                Direccion de operacion:  <p><c:out value="${auditoria.entidad.organizacion.direccionO}"/></p>
            </div>

            <br/>

            <div id="datosActa" class="apartado">
                <h3 id="encabezadoActa" class="encabezadoApartado">Acta de Auditor&iacute;a</h3>
                <c:choose>
                    <c:when test="${empty listaProcesoActa}">
                        <c:choose>
                            <c:when test="${permisoEdicion}">
                                <button type="button" class="btnAdd btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalFormActaAuditoria">
                                    Acta de Auditor&iacute;a
                                </button>  
                            </c:when>
                            <c:otherwise>
                                El auditor líder no ha asignado un acta de auditor&iacute;a.
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <c:choose>
                            <c:when test="${completa}">
                                <div class="row">
                                    <div class="col-6">
                                        <form target="_blank" method="POST" action="AuditoriaServlet?accion=Reporte" id="form-ReportePDF">
                                            <input type="hidden" name="txtIdAuditoria" value='${auditoria.entidad.id}' />
                                            <button class='btn btn-primary' type='submit' form='form-ReportePDF'>
                                                Reporte de Auditoria
                                            </button>
                                        </form>
                                    </div>
                                    <div class="col-6">
                                        <form method="POST" action="AuditoriaServlet?accion=Retroalimentacion" id="form-Retroalimentacion">
                                            <input type="hidden" name="txtIdAuditoria" value='${auditoria.entidad.id}' />
                                            <button class='btn btn-primary' type='submit' form='form-Retroalimentacion'>
                                                Espacio de Retroalimentaci&oacute;n
                                            </button>
                                        </form>
                                    </div>
                                </div>  
                            </c:when>
                            <c:otherwise>
                                <c:choose>
                                    <c:when test="${permisoEdicion}">
                                        <div class="row">
                                            <div class="col-6">
                                                <button type="button" class="btnAdd btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalEditarActaAuditoria">
                                                    Editar Acta Auditor&iacute;a
                                                </button>  
                                            </div>
                                            <div class="col-6">
                                                <form method="POST" action="AuditoriaServlet?accion=EliminarActa" id="form-EliminarActa">
                                                    <input type="hidden" name="txtIdAuditoria" value='${auditoria.entidad.id}' />
                                                    <button class='btn btn-danger' type='submit' form='form-EliminarActa'>
                                                        Eliminar Acta Auditor&iacute;a
                                                    </button>
                                                </form>
                                            </div>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <button type="button" class="btnAdd btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalConsultarActaAuditoria">
                                            Consultar Acta de Auditor&iacute;a
                                        </button> 
                                    </c:otherwise>
                                </c:choose>
                            </c:otherwise>
                        </c:choose>
                    </c:otherwise>
                </c:choose>
            </div>

            <br/>

            <div id="equipoAuditor" class="apartado">
                <h3 id="encabezadoEquipo" class="encabezadoApartado">Equipo Auditor</h3>
                <div class="table-responsive">
                    <table class="table">
                        <thead>
                            <tr>
                                <th scope="col">e-mail</th>
                                <th scope="col">Nombre</th>
                                <th scope="col">Rol</th>
                                <th scope="col">Teléfono</th>
                                <th scope="col">Acci&oacute;n</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td><c:out value="${auditorLider.entidad.correo}"/></td>
                                <td><c:out value="${auditorLider.entidad.nombre}"/></td>
                                <td>Auditor Líder</td>
                                <td><c:out value="${auditorLider.entidad.telefono}"/></td>
                            </tr>
                            <c:forEach items ="${auditoresAuxiliares}" var = "auditorAux">
                                <tr>
                                    <td><c:out value="${auditorAux.entidad.correo}"/></td>
                                    <td><c:out value="${auditorAux.entidad.nombre}"/></td>
                                    <td>Auditor Auxiliar</td>
                                    <td><c:out value="${auditorAux.entidad.telefono}"/></td>
                                    <c:if test="${permisoEdicion}">
                                <form method="POST" action="AuditoriaServlet?accion=EliminarAuditorAuxiliar" id="form-${auditorAux.entidad.correo}">
                                    <input type="hidden" name="txtCorreo" value='${auditorAux.entidad.correo}' />
                                    <input type="hidden" name="txtIdAuditoria" value='${auditoria.entidad.id}' />
                                    <td>
                                        <button class='btn btn-danger' style="font-size: 0.6rem;" type='submit' form='form-${auditorAux.entidad.correo}'>
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
                    <button type="button" class="btnAdd btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalFormAuditorAuxiliar">
                        Agregar Auditor Auxiliar
                    </button>  
                    <br/>
                </c:if>

            </div>    

            <br/>

            <div id="contactos" class="apartado">
                <h3 id="encabezadoContactos" class="encabezadoApartado">Contactos de Auditoría</h3>
                <div class="table-responsive">
                    <table class="table">
                        <thead>
                            <tr>
                                <th scope="col">e-mail</th>
                                <th scope="col">Nombre</th>
                                <th scope="col">Puesto</th>
                                <th scope="col">Teléfono</th>
                                <th scope="col">Acci&oacute;n</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items ="${listaContactos}" var = "contacto">
                                <tr>
                                    <td><c:out value="${contacto.entidad.getId().correo}"/></td>
                                    <td><c:out value="${contacto.entidad.nombre}"/></td>
                                    <td><c:out value="${contacto.entidad.puesto}"/></td>
                                    <td><c:out value="${contacto.entidad.telefono}"/></td>
                                    <c:if test="${permisoEdicion}">
                                <form method="POST" action="AuditoriaServlet?accion=EliminarContacto" id="form-${contacto.entidad.getId().correo}">
                                    <input type="hidden" name="txtCorreo" value='${contacto.entidad.getId().correo}' />
                                    <input type="hidden" name="txtIdAuditoria" value='${auditoria.entidad.id}' />
                                    <td>
                                        <button class='btn btn-danger' style="font-size: 0.6rem;" type='submit' form='form-${contacto.entidad.getId().correo}'>
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
                    <button type="button" class="btnAdd btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalFormContacto">
                        Agregar Contacto
                    </button>
                    <br/>
                </c:if>
            </div>

            <br/>

            <div id="productos" class="apartado">
                <h3 id="encabezadoProductos" class="encabezadoApartado">Productos de Auditados</h3>
                <div class="table-responsive">
                    <table class="table">
                        <thead>
                            <tr>
                                <th scope="col">Clave</th>
                                <th scope="col">Descripción</th>
                                <th scope="col">Acci&oacute;n</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items ="${listaProductos}" var = "producto">
                                <tr>
                                    <td><c:out value="${producto.entidad.getId().clave}"/></td>
                                    <td><c:out value="${producto.entidad.descripcion}"/></td>
                                    <c:if test="${permisoEdicion}">
                                <form method="POST" action="AuditoriaServlet?accion=EliminarProducto" id="form-${producto.entidad.getId().clave}">
                                    <input type="hidden" name="txtClave" value='${producto.entidad.getId().clave}' />
                                    <input type="hidden" name="txtIdAuditoria" value='${auditoria.entidad.id}' />
                                    <td>
                                        <button class='btn btn-danger' style="font-size: 0.6rem;" type='submit' form='form-${producto.entidad.getId().clave}'>
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
                    <button type="button" class="btnAdd btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalFormProducto">
                        Agregar Producto
                    </button>
                </c:if>
            </div>
            <br/>          
        </div>

        <c:if test="${permisoEdicion}">
            <!--modalFormAuditorAuxiliar  -->
            <div class="modal fade" id="modalFormAuditorAuxiliar" tabindex="-1" aria-labelledby="modalFormAuditorAuxiliarTitulo" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="modalFormAuditorAuxiliarTitulo">Agregar Auditor Auxiliar</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <form action="AuditoriaServlet?accion=AlmacenarAuditorAuxiliar" method="POST">
                            <div class="modal-body">
                                <p>Ingrese el correo del auditor, debe haberse registrado previamente.</p>
                                <label for="txtEmailAuditor">Correo Electrónico</label>
                                <input type="email" class="form-control" id="txtEmailAuditorAux" name="txtEmailAuditorAux" placeholder="correo@electronico.com">  
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

            <!--modalFormContacto  -->
            <div class="modal fade" id="modalFormContacto" tabindex="-1" aria-labelledby="modalFormContactoTitulo" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="modalFormContactoTitulo">Agregar Contacto</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <form action="AuditoriaServlet?accion=AlmacenarContacto" method="POST">
                            <div class="modal-body">
                                <label for="txtNombreContacto">Nombre Completo</label>
                                <input type="text" class="form-control" id="txtNombreContacto" name="txtNombreContacto" placeholder="Nombre">
                                <br/>
                                <label for="txtEmail">Correo Electrónico</label>
                                <input type="email" class="form-control" id="txtEmail" name="txtEmail" placeholder="correo@electronico.com">  
                                <br/>
                                <label for="txtPuesto">Puesto</label>
                                <input type="text" class="form-control" id="txtPuesto" name="txtPuesto" placeholder="Puesto que ocupa">  
                                <br/>
                                <label for="txtTelefono">Teléfono</label>
                                <input type="tel" class="form-control" id="txtTelefono" name="txtTelefono" pattern="[0-9]{10}" placeholder="10 dígitos"> 
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

            <!--modalFormActaAuditoria  -->
            <div class="modal fade" id="modalFormActaAuditoria" tabindex="-1" aria-labelledby="modalFormActaAuditoriaTitulo" aria-hidden="true">
                <div class="modal-dialog modal-lg modal-dialog-centered" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="modalFormActaAuditoriaTitulo">Seleccionar Plantilla como Acta de Auditor&iacute;a</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <c:choose>
                            <c:when test="${not empty listaMisPlantillas}">
                                <form action="AuditoriaServlet?accion=SeleccionarActa" method="POST">
                                    <div class="modal-body">
                                        <select id="select-plantilla" name="selectedPlantilla" class="form-select" aria-label="Plantilla">
                                            <option value="0">Seleccione una Plantilla</option>
                                            <c:forEach items ="${listaMisPlantillas}" var = "miPlantilla">
                                                <option value="${miPlantilla.entidad.id}">${miPlantilla.entidad.nombre}</option>
                                            </c:forEach>
                                        </select>
                                        <input type="hidden" value="${auditoria.entidad.id}" name="txtIdAuditoria" />
                                        <br/>
                                        <div id="div-procesos" class="container-fluid"></div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                                        <input type="submit" class="btn btn-primary" value="Registrar">
                                    </div>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <div class="modal-body">
                                    Agregue una plantilla para utilizar como Acta de Auditor&iacute;a
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                                </div>
                            </c:otherwise>
                        </c:choose>             
                    </div>
                </div>
            </div>

            <!--modalEditarActaAuditoria  -->
            <div class="modal fade" id="modalEditarActaAuditoria" tabindex="-1" aria-labelledby="modalEditarActaAuditoriaTitulo" aria-hidden="true">
                <div class="modal-dialog modal-lg modal-dialog-centered" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="modalEditorActaAuditoriaTitulo">Edici&oacute;n Acta de Auditor&iacute;a</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <form action="AuditoriaServlet?accion=EditarActa" method="POST">
                            <input type="hidden" value="${auditoria.entidad.id}" name="txtIdAuditoria"/>
                            <div class="modal-body">
                                <table id="tableProcesos" class="table table-bordered table-responsive">
                                    <thead>
                                        <tr>
                                            <th scope="col" class="text-center">Nombre del Proceso</th>
                                            <th scope="col" class="text-center">Ponderaci&oacute;n (%)</th>
                                            <th scope="col" class="text-center">Encargado</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:set var="i" value="0" scope="page" />
                                        <c:forEach items="${listaProcesos}" var="proceso">
                                            <tr>
                                                <th scope="row"><c:out value="${proceso.entidad.descripcion}"/></th>
                                                <td><input type="number" id="txtPonderacionProceso${proceso.entidad.id}" name="txtPonderacionProceso${proceso.entidad.id}" class="form-control" value="${listaProcesoActa[i].entidad.ponderacion}"/></td>
                                                <td>
                                                    <select  name="txtCorreoEncargadoProceso${proceso.entidad.id}" class="form-select" aria-label="encargado" >
                                                        <option  selected="selected" value="${listaProcesoActa[i].entidad.auditor.correo}"><c:out value="${listaProcesoActa[i].entidad.auditor.nombre}" /></option>
                                                        <c:choose>
                                                            <c:when test="${auditor.entidad.correo ne listaProcesoActa[i].entidad.auditor.correo}">
                                                                <option  value="${auditor.entidad.correo}"><c:out value="${auditor.entidad.nombre}" /></option>
                                                            </c:when>
                                                        </c:choose>
                                                        <c:forEach items="${auditoresAuxiliares}" var="auditorAux">
                                                            <c:choose>
                                                                <c:when test="${auditorAux.entidad.correo ne listaProcesoActa[i].entidad.auditor.correo}">
                                                                    <option value="${auditorAux.entidad.correo}"><c:out value="${auditorAux.entidad.nombre}"/></option>
                                                                </c:when>
                                                            </c:choose>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <c:set var="i" value="${i + 1}" scope="page"/>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                                <input type="submit" class="btn btn-primary" value="Editar">
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <!--modalFormProducto  -->
            <div class="modal fade" id="modalFormProducto" tabindex="-1" aria-labelledby="modalFormProductoTitulo" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="modalFormProductoTitulo">Agregar Producto</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <form action="AuditoriaServlet?accion=AlmacenarProducto" method="POST">
                            <div class="modal-body">
                                <label for="txtClaveProducto">Clave del Producto</label>
                                <input type="text" class="form-control" id="txtClaveProducto" name="txtClaveProducto" placeholder="Nombre">
                                <br/>
                                <label for="txtDescripcionProducto">Nombre / Descripción</label>
                                <input type="text" class="form-control" id="txtDescripcionProducto" name="txtDescripcion" placeholder="Descripcion"> 
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
        </c:if>

        <c:if test="${!permisoEdicion}">
            <!--modalConsultarActaAuditoria  -->
            <div class="modal fade" id="modalConsultarActaAuditoria" tabindex="-1" aria-labelledby="modalConsultarActaAuditoriaTitulo" aria-hidden="true">
                <div class="modal-dialog modal-lg modal-dialog-centered" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="modalConsultarActaAuditoriaTitulo">Acta de Auditor&iacute;a</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <table id="tableProcesos" class="table table-bordered table-responsive">
                                <thead>
                                    <tr>
                                        <th scope="col" class="text-center">Nombre del Proceso</th>
                                        <th scope="col" class="text-center">Ponderaci&oacute;n (%)</th>
                                        <th scope="col" class="text-center">Encargado</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:set var="i" value="0" scope="page" />
                                    <c:forEach items="${listaProcesos}" var="proceso">
                                        <tr>
                                            <th scope="row"><c:out value="${proceso.entidad.descripcion}"/></th>
                                            <td><c:out value="${listaProcesoActa[i].entidad.ponderacion}"/></td>
                                            <td><c:out value="${listaProcesoActa[i].entidad.auditor.nombre}"/></td>  
                                        </tr>
                                        <c:set var="i" value="${i + 1}" scope="page"/>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${not empty mensaje}">
            <script>
                alertify.alert('Datos incorrectos','<div class="text-center">${mensaje}</div>');
            </script>  
        </c:if>
    </body>
</html>

