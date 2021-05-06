<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        
        <link rel="stylesheet" href="resources\css\bootstrap.css">
        <link rel="stylesheet" href="resources\css\index.css">
	<script src="resources\js\jquery-3.6.0.js"></script>
	<script src="resources\js\bootstrap.js"></script>
	<title>AudiFast-Inicio</title>
</head>
<div class="container">
    <div class="">
        <div class="col-md-2">
            <div class="card">
                <form action="AuditorServlet?accion=Login" method="POST" class="box">
                    <h2 class="text-white">Inicio de Sesión</h2>
                    <p class="text-muted"> Introduzca su correo electrónico y contraseña</p> <input type="text" name="txtCorreo" placeholder="Correo Electrónico"> <input type="password" name="txtPswd" placeholder="Contraseña"> <input type="submit" name="" value="Iniciar Sesión">
                    <a href="registro.jsp">Registrarse</a>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="container">
    <div class="">
        <div class="col-md-16">
            <div class="card">
                <form onsubmit="event.preventDefault()" class="box">
                    <h2 class="text-white">Acceda a una Auditoría</h2>
                    <p class="text-muted"> Introduzca su correo y clave de acceso</p> <input type="text" name="correo" placeholder="Correo"> <input type="password" name="clave" placeholder="Clave de acceso"> <input type="submit" name="" value="Acceder" href="#">
                </form>
            </div>
        </div>
    </div>
</div>

<body>
	
</body>
</html>
