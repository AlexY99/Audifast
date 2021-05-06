<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <link rel="stylesheet" href="resources\css\bootstrap.css">
        <link rel="stylesheet" href="resources\css\registro.css">
        <script src="resources\js\jquery-3.6.0.js"></script>
        <script src="resources\js\bootstrap.js"></script>
        <title>AudiFast-Registro</title>
    </head>

    <body>
        <div class="container">
            <div class="col-md-auto">
                <div class="card">
                    <form action="AuditorServlet?accion=Almacenar" method="POST" class="box">
                        <h2 class="text-white">Registro de Usuario</h2>
                        <p class="text-muted">Introduzca sus datos de registro</p> 
                        <label for="Nombre">Nombre Completo</label>
                        <input type="text" class="form-control" id="txtNombre" name="txtNombre" placeholder="Nombre">

                        <label for="Correo">Correo Electrónico</label>
                        <input type="email" class="form-control" id="txtCorreo" name="txtCorreo" placeholder="correo@electronico.com">

                        <label for="Contraseña">Contraseña</label>
                        <input type="password" class="form-control" id="txtPswd" name="txtPswd" placeholder="Contraseña">

                        <label for="ConfContraseña">Confirme su Contraseña</label>
                        <input type="password" class="form-control" id="txtConfPswd" name="txtConfPswd" placeholder="Confirmación Contraseña">

                        <label for="Telefono">Teléfono</label>
                        <input type="tel" class="form-control" id="txtTelefono" name="txtTelefono" pattern="[0-9]{10}" placeholder="10 dígitos">

                        <input type="submit" name="" value="Registrarse">
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
