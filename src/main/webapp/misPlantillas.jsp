<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="es">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="resources\css\bootstrap.css">
        <link rel="stylesheet" href="resources\css\all.min.css">
        <link rel="stylesheet" href="resources\css\misPlantillas.css">
        <link rel="stylesheet" href="resources\css\IconColors.css">
        <link rel="stylesheet" href="resources\plugins\alertifyjs\css\alertify.min.css" />
        <link rel="stylesheet" href="resources\plugins\alertifyjs\css\themes\bootstrap.min.css" />
        <script src="resources\js\jquery-3.6.0.js"></script>     
        <script src="resources\js\all.min.js"></script>     
        <script src="resources\js\bootstrap.bundle.min.js" async="async"></script>
        <script src="resources\plugins\alertifyjs\alertify.min.js"></script>
        <title>AF-Plantillas</title>
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
                            <a class="nav-link disabled" aria-current="page" href="AuditorServlet?accion=Plantillas">Mis plantillas</a>
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


        <br/>
        <div class="d-flex justify-content-center">
            <h2 class="encabezado">Plantillas Predefinidas</h2>
        </div>
        <hr class="my-4"/>
        <div class="row align-items-start justify-content-left">
 
            <div class="col-lg-3 col-md-4 col-sm-6">
                <div class="card" style="width: 14rem; margin-top: 20px; margin-left: 30px; ">
                    <div class="card-body">
                        <h5 class="card-title">Plantilla</h5>
                        <p class="card-text">Cosas de la plantilla xd</p>
                        <button type="button" class="btn btn-info">NORMA X4AE</button>
                    </div>
                    <div class="card-footer text-muted">
                    </div>
                </div>
            </div>
        </div>

        <br/>
        <div class="d-flex justify-content-center">
            <h2 class="encabezado">Mis plantillas</h2>
        </div>
        <hr class="my-4"/>
        <div class="row align-items-start justify-content-left">
            <div class="col-lg-3 col-md-4 col-sm-6">
                <div class="card" style="width: 14rem; margin-top: 20px; margin-left: 30px; ">
                    <div class="card-body text-center">
                        <h5 class="card-title">Agregar Plantilla</h5>
                        <p class="card-text"> </p>
                        <button type="button" class="btn" id="btnAdd" data-bs-toggle="modal" data-bs-target="#modalRegistroPlantilla"><i class="fas fa-plus bi-align-center fa-4x blueIcon "></i></button>
                    </div>
                    <div class="card-footer text-muted">
                    </div>
                </div>
            </div>

            <c:forEach items ="${listaMisPlantillas}" var = "miPlantilla">
                <div class="col-lg-3 col-md-4 col-sm-6">
                    <div class="card" style="width: 14rem; margin-top: 20px; margin-left: 30px; ">
                        <div class="card-body text-center">
                            <h5 class="card-title"><c:out value="${miPlantilla.entidad.nombre}"/></h5>
                            <p class="card-text"> </p>
                            <a class="btn btn-dark" href="PlantillaServlet?accion=Editar&id=${miPlantilla.entidad.id}">Editar</a>
                        </div>
                        
                        <div class="card-footer text-muted text-center">
                            <form method="POST" action="PlantillaServlet" id="form-${miPlantilla.entidad.id}">
                                <input type="hidden" name="accion" value="Eliminar"/>
                                <input type="hidden" name="id" value='${miPlantilla.entidad.id}' />
                                <td class="text-center">
                                    <button class='btn btn-danger' type='submit' form='form-${miPlantilla.entidad.id}'>
                                        Eliminar
                                    </button>
                                </td>
                            </form>
                        </div>
                    </div>
                </div>
            </c:forEach>
            
        </div>
          
        <!--El modal  -->
        <div class="modal fade" id="modalRegistroPlantilla" tabindex="-1" aria-labelledby="modalTitulo" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header text-center">
                            <h5 class="modal-title" id="modalTitulo">Registrar Plantilla</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <form action="PlantillaServlet?accion=Almacenar" method="POST">
                            <div class="modal-body">
                                <label for="txtNombre">Ingrese el nombre de la plantilla que desea crear.</label>
                                <input type="text" class="form-control" id="txtNombre" name="txtNombre" placeholder="Nombre de la Plantilla"> 
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                                <input type="submit" class="btn btn-primary" value="Crear">
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <c:if test="${not empty mensaje}">
            <script>
                alertify.alert('Datos incorrectos','<div class="text-center">${mensaje}</div>');
            </script>  
        </c:if>

    </body>
</html>