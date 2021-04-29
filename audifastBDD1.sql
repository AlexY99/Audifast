drop database if exists AudiFast;
create database AudiFast;
use AudiFast;

create table auditor(
    usuario nvarchar(30) not null,
    nombre nvarchar(50) not null,
    correo nvarchar(50) not null primary key,
    pswd nvarchar(30) not null,
	telefono nvarchar(10)

);

create table organizacion(
	rfc nvarchar(13) primary key,
    nombre nvarchar(50) not null,
    giro nvarchar(50) not null,
    direccion_operacion nvarchar(50) not null,
    direccion_fiscal nvarchar(50) not null,
    correo_auditor nvarchar(50) not null,
    foreign key(correo_auditor) references auditor(correo)
);

create table norma(
	clave nvarchar(10) primary key,
    nombre nvarchar(50) not null,
    descripcion nvarchar(100)
);

create table rol_auditor(
	id int(1) not null primary key,
    descripcion nvarchar(10) not null
);

create table auditoria(
	id int(10) auto_increment primary key,
    rfc_organizacion nvarchar(13),
    fecha_registro timestamp default current_timestamp,
    foreign key(rfc_organizacion) references organizacion(rfc) on delete cascade on update cascade
);

create table equipo_auditoria(
	correo_auditor nvarchar(50) not null,
    idRol int(1),
    idAuditoria int(10) not null,
    primary key(correo_auditor,idAuditoria),
    foreign key(correo_auditor) references auditor(correo),
    foreign key(idRol) references rol_auditor(id)
);

create table contactos_auditoria(
	correo nvarchar(50) primary key,
    puesto nvarchar(50) not null,
    nombre nvarchar(50) not null,
	telefono nvarchar(10)
);

create table conjunto_contactos(
	correo_contacto nvarchar(50),
    idAuditoria int(10),
    primary key(correo_contacto,idAuditoria),
    foreign key(correo_contacto) references contactos_auditoria(correo),
    foreign key(idAuditoria) references auditoria(id)
);

create table producto(
	clave nvarchar(30) primary key,
    nombre nvarchar(50) not null,
    descripcion nvarchar(100)
);

create table conjunto_productos(
	clave_producto nvarchar(30),
    idAuditoria int(10),
    primary key(clave_producto,idAuditoria),
    foreign key(clave_producto) references producto(clave),
    foreign key(idAuditoria) references auditoria(id)
);

create table espacio_retroalimentacion(
	idAuditoria int(10) primary key,
    url_informe nvarchar(50),
    url_plan nvarchar(50),
    foreign key(idAuditoria) references auditoria(id)
);

create table mensajes_retroalimentacion(
	id int(10) auto_increment primary key,
    idAuditoria int(10),
    mensaje nvarchar(100),
    correo_remitente nvarchar(50),
    fecha timestamp default current_timestamp,
    foreign key(idAuditoria) references espacio_retroalimentacion(idAuditoria)
);

create table clave_acceso(
	correo nvarchar(50) not null,
    clave nvarchar(100) primary key,
    idAuditoria int(10) not null,
	foreign key(idAuditoria) references espacio_retroalimentacion(idAuditoria)
);

create table plantilla_auditor(
	correo_auditor nvarchar(50) not null,
    id int(10) auto_increment primary key,
    nombre nvarchar(50) not null,
    foreign key(correo_auditor) references auditor(correo)
);

create table proceso(
	id int(10) auto_increment primary key,
    idPlantilla int(10), 
    descripcion nvarchar(100),
    foreign key(idPlantilla) references plantilla_auditor(id)
);

create table requisito(
	id int(10) auto_increment primary key,
    clave_norma nvarchar(10) not null,
    descripcion nvarchar(100) not null,
    idProceso int(10) not null,
    foreign key(clave_norma) references norma(clave),
    foreign key(idProceso) references proceso(id)
);

create table lista_requisitos(
	idRequisito int(10),
    idPlantilla int(10),
    valor_cumplimiento int(1),
    primary key(idRequisito,idPlantilla),
    foreign key(idRequisito) references requisito(id),
    foreign key(idPlantilla) references plantilla_auditor(id)
);

create table acta_auditoria(
    idAuditoria int(10) primary key,
    idPlantilla int(10) not null,
    evaluacion float,
    foreign key(idAuditoria) references auditoria(id),
    foreign key(idPlantilla) references plantilla_auditor(id)
);

create table ponderaciones(
	id int(10) not null primary key,
	idProceso int(10) not null,
    ponderacion float not null,
    resultado float,
    idAuditoria int(10) not null,
    foreign key(idProceso) references proceso(id),
    foreign key(idAuditoria) references auditoria(id)
);