<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <link rel="stylesheet" href="resources\css\bootstrap.css">
        <link rel="stylesheet" href="resources\css\inicio.css">
        <script src="resources\js\jquery-3.6.0.js"></script>     
        <script src="resources\js\bootstrap.min.js"></script>
        <title>AF-Inicio</title>
    </head>

    <body>        
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">Audifast</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="#">P&aacute;gina Principal</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Registro Empresa</a>
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
            <h2>Encabezado</h2>
            <br/>
            
            <div id="listasAuditorias-tab-holder">
                <ul class="nav nav-tabs" id="auditoriasTab" role="tablist">
                    <li class="nav-item" role="presentation">
                        <button class="nav-link active" id="lideradas-tab" data-bs-toggle="tab" data-bs-target="#lideradas" type="button" role="tab" aria-controls="home" aria-selected="true">Auditorías Lideradas</button>
                    </li>
                    <li class="nav-item" role="presentation">
                        <button class="nav-link" id="auxiliadas-tab" data-bs-toggle="tab" data-bs-target="#auxiliadas" type="button" role="tab" aria-controls="profile" aria-selected="false">Auditorías Auxiliadas</button>
                    </li>
                </ul>

                <div class="tab-content" id="listasAuditorias">
                    <div id="lideradas" class="tab-pane fade show active" role="tabpanel" aria-labelledby="lideradas-tab">
                        <br/>
                        <h3>Auditorías Lideradas</h3>
                        <table class="table">
                            <thead>
                                <tr>
                                    <th scope="col">#</th>
                                    <th scope="col">Organización</th>
                                    <th scope="col">Fecha de Registro</th>
                                    <th scope="col">Cancelar Auditoría</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <th scope="row">1</th>
                                    <td>DeliMix</td>
                                    <td>11/05/2021 15:00:00</td>
                                    <td>Cancelar</td>
                                </tr>
                                <tr>
                                    <th scope="row">2</th>
                                    <td>SuperCosméticos</td>
                                    <td>03/05/2021 15:00:00</td>
                                    <td>Cancelar</td>
                                </tr>
                                <tr>
                                    <th scope="row">3</th>
                                    <td>Limpiadores Martínez</td>
                                    <td>19/04/2021 15:00:00</td>
                                    <td>Cancelar</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div id="auxiliadas" class="tab-pane fade" role="tabpanel" aria-labelledby="auxiliadas-tab">
                        <br/>
                        <h3>Auditorías Auxiliadas</h3>
                        <table class="table">
                            <thead>
                                <tr>
                                    <th scope="col">#</th>
                                    <th scope="col">Organización</th>
                                    <th scope="col">Fecha de Registro</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <th scope="row">1</th>
                                    <td>DeliMix</td>
                                    <td>11/05/2021 15:00:00</td>
                                </tr>
                                <tr>
                                    <th scope="row">2</th>
                                    <td>SuperCosméticos</td>
                                    <td>03/05/2021 15:00:00</td>
                                </tr>
                                <tr>
                                    <th scope="row">3</th>
                                    <td>Limpiadores Martínez</td>
                                    <td>19/04/2021 15:00:00</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>

            </div>
            
            
        </div>
    </body>
</html>

