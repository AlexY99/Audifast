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
                            <a class="nav-link" href="AuditorServlet?accion=Plantillas">Mis plantillas</a>
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
            <h2 id="encabezado"><c:out value="${plantilla.entidad.nombre}"/></h2>
            <br/>
            <div id="Procesos" class="apartado">
                <c:set var="cuenta" value="${0}"/>
                <c:forEach items ="${listaProcesos}" var = "proceso">
                    <c:set var="cuenta" value="${cuenta+1}"/>
                    <h4 id="encabezadoProceso${cuenta}" class="encabezadoApartado"><c:out value="${cuenta}"/>. <c:out value="${proceso.entidad.descripcion}"/> </h4>
                    <div class="table-responsive">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th scope="col">No. Requisito</th>
                                    <th scope="col">Norma Asociada</th>
                                    <th scope="col">Descripcion del Requisito</th>
                                    <th scope="col">Eliminar</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:set var="cuentaReq" value="${0}"/>
                                <c:forEach items ="${proceso.entidad.requisitos}" var = "requisito">
                                    <c:set var="cuentaReq" value="${cuentaReq+1}"/>
                                    <tr>
                                        <td><c:out value="${cuenta}"/>.<c:out value="${cuentaReq}"/></td>
                                        <td><c:out value="${requisito.norma.clave}"/></td>
                                        <td><c:out value="${requisito.descripcion}"/></td>
                                        <form method="POST" action="PlantillaServlet" id="formEliminarRequisito-${requisito.id}">
                                            <input type="hidden" name="accion" value="EliminarRequisito"/>
                                            <input type="hidden" name="idRequisito" value='${requisito.id}' />
                                            <input type="hidden" name="idPlantilla" value='${plantilla.entidad.id}' />
                                            <td>
                                                <button class='btn btn-danger' style="font-size: 0.6rem;" type='submit' form='formEliminarRequisito-${requisito.id}'>
                                                    X
                                                </button>
                                            </td>
                                        </form>
                                    </tr>
                            </c:forEach>
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
                            <form method="POST" action="PlantillaServlet" id="formEliminarProceso-${proceso.entidad.id}">
                                <input type="hidden" name="accion" value="EliminarProceso" />
                                <input type="hidden" name="idProceso" value='${proceso.entidad.id}'/>
                                <input type="hidden" name="idPlantilla" value='${plantilla.entidad.id}'/>
                                <td>
                                    <button class='btn btn-danger' style="font-size: 0.8rem;" type='submit' form='formEliminarProceso-${proceso.entidad.id}'>
                                        Eliminar proceso
                                    </button>
                                </td>
                            </form>
                        </div>
                    </div>

                    <!--modalFormRequisito  -->
                    <div class="modal fade" id="modalFormRequisito${cuenta}" tabindex="-1" aria-labelledby="modalFormNuevoRequisito" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered" role="document">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="modalFormAuditorAuxiliarTitulo">Agregar Requisito al proceso <c:out value="${cuenta}"/></h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>

                                    <c:choose>
                                        <c:when test="${not empty listaNormas}">
                                            <form action="PlantillaServlet" method="POST">
                                                <input type="hidden" name="accion" value="AlmacenarRequisito"/>
                                                <div class="modal-body">
                                                    <label for="txtClaveNorma">Clave de norma Asociada</label>
                                                    <select name="txtClaveNorma">
                                                        <c:forEach items="${listaNormas}" var="norma">
                                                            <option value="${norma.entidad.clave}"><c:out value="${norma.entidad.clave}"/></option>
                                                        </c:forEach>
                                                    </select>
                                                    <br/>
                                                    <br/>
                                                    <label for="txtDescripcion">Ingrese el nombre o descripcion del requisito</label>
                                                    <textarea id='txtDescripcion' name="txtDescripcion" rows="4" cols="50"/></textarea>
                                                    <input type="hidden" name="idProceso" value='${proceso.entidad.id}' />
                                                    <input type="hidden" name="idPlantilla" value='${plantilla.entidad.id}' />
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                                                    <input type="submit" class="btn btn-primary" value="Registrar">
                                                </div>
                                            </form>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="modal-body">
                                                Registre una norma para asociar al requisito
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>

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
                        <form action="PlantillaServlet" method="POST">
                            <input type="hidden" name="accion" value="AlmacenarProceso"/>
                            <div class="modal-body">
                                <label for="txtProcesoNombre">Ingrese el nombre o descripcion del nuevo proceso.</label>
                                <textarea id='txtProcesoNombre' name="txtProcesoNombre" rows="4" cols="50"/></textarea>
                                <input type="hidden" name="idPlantilla" value='${plantilla.entidad.id}'/>
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
