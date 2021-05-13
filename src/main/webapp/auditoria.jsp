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
            <h2 id="encabezado">Auditoría <c:out value="${auditoria.entidad.id}"/></h2>
            
            <div id="datosOrganizacion" class="apartado">
                <h3 id="encabezadoDatos" class="encabezadoApartado">Datos de la Organización Auditada</h3>
                RFC:  <c:out value="${auditoria.entidad.organizacion.getId().rfc}"/><br/>
                Nombre:  <c:out value="${auditoria.entidad.organizacion.nombre}"/><br/>
                Giro de la Empresa: <c:out value="${auditoria.entidad.organizacion.giro}"/><br/>
                Direccion de operacion:  <p><c:out value="${auditoria.entidad.organizacion.direccionO}"/></p>
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
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td><c:out value="${auditor.entidad.correo}"/></td>
                                <td><c:out value="${auditor.entidad.nombre}"/></td>
                                <td>Auditor Líder</td>
                                <td><c:out value="${auditor.entidad.telefono}"/></td>
                            </tr>

                            <%--<c:forEach items ="${listaAuditoresAuxiliares}" var = "auditorAux">
                                <tr>
                                    <td><c:out value="${auditorAux.getCorreo()}"/></td>
                                    <td><c:out value="${auditorAux.getNombre()}"/></td>
                                    <td>Auditor Auxiliar</td>
                                    <td><c:out value="${auditorAux.getTelefono()}"/></p></td>
                                </tr>
                            </c:forEach>--%>
                        </tbody>
                    </table>
                </div>
                <button type="button" class="btnAdd btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalFormAuditorAuxiliar">
                    Agregar Auditor Auxiliar
                </button>
                <br/>
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
                            </tr>
                        </thead>
                        <tbody>
                            <%--<c:forEach items ="${listaContactos}" var = "contacto">
                                <tr>
                                    <td><c:out value="${contacto.getCorreo()}"/></td>
                                    <td><c:out value="${contacto.getNombre()}"/></td>
                                    <td><c:out value="${contacto.getPuesto()}"/></td>
                                    <td><c:out value="${contacto.getTelefono()}"/></td>
                                </tr>
                            </c:forEach>--%>
                        </tbody>
                    </table>
                </div>
                <button type="button" class="btnAdd btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalFormContacto">
                    Agregar Contacto
                </button>
                <br/>
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
                            </tr>
                        </thead>
                        <tbody>
                            <%--<c:forEach items ="${listaProductos}" var = "producto">
                                <tr>
                                    <td><c:out value="${producto.getClave()}"/></td>
                                    <td><c:out value="${producto.getDescripcion()}"/></td>
                                </tr>
                            </c:forEach>--%>
                        </tbody>
                    </table>
                </div>
                <button type="button" class="btnAdd btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalFormProducto">
                    Agregar Producto
                </button>
            </div>
                        
            <br/>          
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
                                <p>Ingrese el correo del auditor, debe haberse registrado previamente.</p>
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
        <div class="modal fade" id="modalFormContacto" tabindex="-1" aria-labelledby="modalFormContactoTitulo" aria-hidden="true">
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
                                <br/>
                                <label for="txtEmailAuditor">Correo Electrónico</label>
                                <input type="email" class="form-control" id="txtEmailAuditor" name="txtEmailAuditor" placeholder="correo@electronico.com">  
                                <br/>
                                <label for="txtEmailAuditor">Puesto</label>
                                <input type="email" class="form-control" id="txtEmailAuditor" name="txtEmailAuditor" placeholder="correo@electronico.com">  
                                <br/>
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
        
        <!--modalFormProducto  -->
        <div class="modal fade" id="modalFormProducto" tabindex="-1" aria-labelledby="modalFormProductoTitulo" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-dialog">
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

