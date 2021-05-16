<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <link rel="stylesheet" href="resources\css\bootstrap.css">
        <link rel="stylesheet" href="resources\css\registro.css">
        <script src="resources\js\jquery-3.6.0.js"></script>     
        <script src="resources\js\bootstrap.min.js"></script>
        <title>AF-Registro-Empresa</title>
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
                            <a class="nav-link active" aria-current="page" href="#">Registro Empresa</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Mis plantillas</a>
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
            <div class="col-md-auto">
                <div class="card">
                    <form action="EmpresaServlet?accion=almacenar" method="POST" class="box">
                        <h2 class="text-white">Registro de Empresa</h2>
                        <p class="text-muted">Introduzca los datos a registrar</p> 
                        <label for="txtNombre">Nombre</label>
                        <input type="text" class="form-control" id="txtNombre" name="txtNombre" placeholder="Nombre">

                        <label for="txtRFC">RFC</label>
                        <input type="text" class="form-control" id="txtRFC" name="txtRFC" placeholder="RFC de la empresa">

                        <label for="txtGiro">Giro</label>
                        <input type="text" class="form-control" id="txtGiro" name="txtGiro" placeholder="Giro de la empresa">

                        <label for="txtDireccionOp">Dirección de operaci&oacute;n</label>
                        <input type="text" class="form-control" id="txtDireccionOp" name="txtDireccionOp" placeholder="Dirección de operación">

                        <label for="txtDirecciónF">Direcci&oacute;n F&iacute;sica</label>
                        <input type="text" class="form-control" id="txtDireccionF" name="txtDireccionF" placeholder="Dirección Física">

                        <input type="submit" name="" value="Registrar">
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
