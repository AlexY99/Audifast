$('#select-plantilla').on('change', function() {
    var idPlantilla = this.value;
    var idAuditoria = $('#txtIdAuditoria').val()
    if(idPlantilla!=0){
        enviarDatos(idPlantilla,idAuditoria);
    }else{
        $('#div-procesos').html("");
    }
});

function enviarDatos(idP,idA){
    $.ajax({
        url: 'PlantillaServlet',
        type: 'POST',
        data: {accion:"getProcesosPlantilla",idPlantilla:idP,idAuditoria:idA},
        cache: false
    }).done(function(data){
        var resp = JSON.parse(data);
        var html = '<table id="tableProcesos" class="table table-bordered table-responsive">';
        html += '<thead>';
        html += '<tr>';
            html += '<th scope="col>Proceso</th>';
            html += '<th scope="col" class="text-center">Nombre del Proceso</th>';
            html += '<th scope="col" class="text-center">Ponderaci&oacute;n (%)</th>';
            html += '<th scope="col" class="text-center">Encargado</th>';
        html += '</tr>';
        html += '</thead>';
        html += '<tbody>';
        $.each(resp.procesos, function(key,value){
            html += '<tr>';
            html += '<th scope="row">'+value+'</th>';
            html += '<td><input type="number" id="txtPonderacionProceso'+key+'" name="txtPonderacionProceso'+key+'" class="form-control"/></td>';
            html += '<td><select name="txtCorreoEncargadoProceso'+key+'" class="form-select" aria-label="encargado">';
            $.each(resp.auditores, function(key,value){
                html += '<option value='+key+'>'+value+'</option>';
            });
            html += '</select></td>';
        });
        html += '</tbody>';
        html += '</table>';
        $('#div-procesos').html(html);
    }); 
}