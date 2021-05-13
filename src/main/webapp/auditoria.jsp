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
        <script src="resources\js\jquery-3.6.0.js"></script>     
        <script src="resources\js\bootstrap.bundle.min.js" async="async"></script>
        <title>AF-Auditor&iacutre;a</title>
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
            <div id="contenido">
                <br/>
                <h2 id="encabezado">Auditoría <c:out value="${auditoria.entidad.id}"/></h2>
                <br/>
                <h3 id="encabezadoDatos">Datos de la Organización Auditada</h3>
                <br/>
                RFC:  <c:out value="${auditoria.entidad.organizacion.getId().rfc}"/><br/>
                Nombre:  <c:out value="${auditoria.entidad.organizacion.nombre}"/><br/>
                Giro de la Empresa: <c:out value="${auditoria.entidad.organizacion.giro}"/><br/>
                Direccion de operacion:  <p><c:out value="${auditoria.entidad.organizacion.direccionO}"/></p>
                <br/>

                <h3 id="encabezadoEquipo">Equipo Auditor</h3>
                <br/>
                <div class="table-responsive">
                    <table class="table">
                        <thead>
                            <tr>
                                <th scope="col" class="text-center">e-mail</th>
                                <th scope="col" class="text-center">Nombre</th>
                                <th scope="col" class="text-center">Rol</th>
                                <th scope="col" class="text-center">Teléfono</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td><p class="text-center"><c:out value="${auditor.entidad.correo}"/></p></td>
                                <td><p class="text-center"><c:out value="${auditor.entidad.nombre}"/></p></td>
                                <td><p class="text-center">Auditor Líder</p></td>
                                <td><p class="text-center"><c:out value="${auditor.entidad.telefono}"/></p></td>
                            </tr>

                            <%--<c:forEach items ="${listaAuditoresAuxiliares}" var = "auditorAux">
                                <tr>
                                    <td><p class="text-center"><c:out value="${auditorAux.getCorreo()}"/></p></td>
                                    <td><p class="text-center"><c:out value="${auditorAux.getNombre()}"/></p></td>
                                    <td><p class="text-center">Auditor Auxiliar</p></td>
                                    <td><p class="text-center"><c:out value="${auditorAux.getTelefono()}"/></p></td>
                                </tr>
                            </c:forEach>--%>
                        </tbody>
                    </table>
                </div>
                <br/>
                <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalFormAuditorAuxiliar">
                    Agregar Auditor Auxiliar
                </button>

                <br/>
                <h3 id="encabezadoContactos">Contactos de Auditoría</h3>
                <br/>
                <div class="table-responsive">
                    <table class="table">
                        <thead>
                            <tr>
                                <th scope="col" class="text-center">e-mail</th>
                                <th scope="col" class="text-center">Nombre</th>
                                <th scope="col" class="text-center">Puesto</th>
                                <th scope="col" class="text-center">Teléfono</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%--<c:forEach items ="${listaContactos}" var = "contacto">
                                <tr>
                                    <td><p class="text-center"><c:out value="${contacto.getCorreo()}"/></p></td>
                                    <td><p class="text-center"><c:out value="${contacto.getNombre()}"/></p></td>
                                    <td><p class="text-center"><c:out value="${contacto.getPuesto()}"/></p></td>
                                    <td><p class="text-center"><c:out value="${contacto.getTelefono()}"/></p></td>
                                </tr>
                            </c:forEach>--%>
                        </tbody>
                    </table>
                </div>
                <br/>
                <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalFormContacto">
                    Agregar Contacto
                </button>

                <br/>
                <h3 id="encabezadoContactos">Productos de Auditados</h3>
                <br/>
                <div class="table-responsive">
                    <table class="table">
                        <thead>
                            <tr>
                                <th scope="col" class="text-center">Clave</th>
                                <th scope="col" class="text-center">Descripción</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%--<c:forEach items ="${listaProductos}" var = "producto">
                                <tr>
                                    <td><p class="text-center"><c:out value="${producto.getClave()}"/></p></td>
                                    <td><p class="text-center"><c:out value="${producto.getDescripcion()}"/></p></td>
                                </tr>
                            </c:forEach>--%>
                        </tbody>
                    </table>
                </div>
                <br/>
                <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalFormProducto">
                    Agregar Producto
                </button>

            </div>
        </div>
                            
        <!--modalFormAuditorAuxiliar  -->
        <div class="modal fade" id="modalFormAuditorAuxiliar" tabindex="-1" aria-labelledby="modalFormAuditorAuxiliarTitulo" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="modalFormAuditorAuxiliarTitulo">Agregar Auditor Auxiliar</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <form action="AuditoriaServlet?accion=AlmacenarAuditorAuxiliar" method="POST">
                            <div class="modal-body">
                                <p>Ingrese el correo del auditor, debe haberse registrado previamente</p>
                                <label for="txtEmailAuditor">Correo Electrónico</label>
                                <input type="email" class="form-control" id="txtEmailAuditor" name="txtEmailAuditor" placeholder="correo@electronico.com">  
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
        
        <!--modalFormContacto  -->
        <div class="modal fade" id="mmodalFormContacto" tabindex="-1" aria-labelledby="modalFormContactoTitulo" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="modalFormContactoTitulo">Agregar Contacto</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <form action="AuditoriaServlet?accion=AlmacenarContacto" method="POST">
                            <div class="modal-body">
                                <label for="txtNombreContacto">Nombre Completo</label>
                                <input type="text" class="form-control" id="txtNombreContacto" name="txtNombreContacto" placeholder="Nombre">
                                
                                <label for="txtEmailAuditor">Correo Electrónico</label>
                                <input type="email" class="form-control" id="txtEmailAuditor" name="txtEmailAuditor" placeholder="correo@electronico.com">  
                                
                                <label for="txtEmailAuditor">Puesto</label>
                                <input type="email" class="form-control" id="txtEmailAuditor" name="txtEmailAuditor" placeholder="correo@electronico.com">  
                                
                                <label for="txtTelefono">Teléfono</label>
                                <input type="tel" class="form-control" id="txtTelefono" name="txtTelefono" pattern="[0-9]{10}" placeholder="10 dígitos"> 
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

