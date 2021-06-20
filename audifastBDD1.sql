drop database if exists AudiFast;
create database AudiFast;
use AudiFast;

create table auditor(
    correo nvarchar(50) not null primary key,
    nombre nvarchar(50) not null,
    pswd nvarchar(30) not null,
    telefono nvarchar(10)
);

create table organizacion(
    rfc nvarchar(13) not null,
    nombre nvarchar(50) not null,
    giro nvarchar(50) not null,
    direccion_operacion nvarchar(50) not null,
    direccion_fiscal nvarchar(50) not null,
    correo_auditor nvarchar(50) not null,
    foreign key(correo_auditor) references auditor(correo),
    primary key(rfc,correo_auditor)
);

create table norma(
	id int(10) auto_increment primary key,
	clave nvarchar(10) not null,
	correo_auditor nvarchar(50) not null,
    nombre nvarchar(50) not null,
    foreign key(correo_auditor) references auditor(correo)
);

create table auditoria(
    id int(10) auto_increment primary key,
    rfc_organizacion nvarchar(13),
    fecha_registro timestamp default current_timestamp,
    correo_auditor_lider nvarchar(50),    
    foreign key(rfc_organizacion) references organizacion(rfc) on delete cascade on update cascade,
    foreign key(correo_auditor_lider) references auditor(correo) on delete cascade on update cascade
);

create table auditor_auxiliar(
    correo_auditor nvarchar(50) not null,
    idAuditoria int(10) not null,
    primary key(correo_auditor,idAuditoria),
    foreign key(correo_auditor) references auditor(correo)
		on delete cascade on update cascade
);

create table contacto_auditoria(
	correo nvarchar(50),
    idAuditoria int(10),
    puesto nvarchar(50) not null,
    nombre nvarchar(50) not null,
	telefono nvarchar(10),
    primary key(correo,idAuditoria),
    foreign key(idAuditoria) references Auditoria(id)
		on delete cascade on update cascade
);

create table producto(
	clave nvarchar(30),
    idAuditoria int(10),
    descripcion nvarchar(100),
    primary key(clave,idAuditoria),
    foreign key(idAuditoria) references Auditoria(id)
		on delete cascade on update cascade
);

create table espacio_retroalimentacion(
	idAuditoria int(10) primary key,
    url_plan nvarchar(50),
    foreign key(idAuditoria) references auditoria(id)
		on delete cascade on update cascade
);

create table mensajes_retroalimentacion(
	id int(10) auto_increment primary key,
    idAuditoria int(10),
    mensaje nvarchar(100),
    correo_remitente nvarchar(50),
    fecha timestamp default current_timestamp,
    foreign key(idAuditoria) references espacio_retroalimentacion(idAuditoria)
		on delete cascade on update cascade
);

create table clave_acceso(
	correo nvarchar(50) not null,
    clave nvarchar(100) primary key,
    idAuditoria int(10) not null,
	foreign key(idAuditoria) references espacio_retroalimentacion(idAuditoria)
		on delete cascade on update cascade
);

create table plantilla_auditor(
    correo_auditor nvarchar(50) not null,
    id int(10) auto_increment primary key,
    nombre nvarchar(50) not null,
    foreign key(correo_auditor) references auditor(correo)
		on delete cascade on update cascade
);

create table proceso(
    id int(10) auto_increment primary key,
    idPlantilla int(10), 
    descripcion nvarchar(100),
    foreign key(idPlantilla) references plantilla_auditor(id)
		on delete cascade on update cascade
);

create table requisito(
    id int(10) auto_increment primary key,
    clave_norma nvarchar(20) not null,
    descripcion nvarchar(100) not null,
    idProceso int(10) not null,
    foreign key(idProceso) references proceso(id)
		on delete cascade on update cascade
);

create table proceso_acta(
    id int(10) auto_increment primary key,
    idAuditoria int(10),
    correo_encargado nvarchar(50) not null,
    idProceso int(10) not null,
    ponderacion float not null,
    evaluado boolean not null default false,
    resultado float not null,
    observaciones nvarchar(100),
    foreign key(idAuditoria) references auditoria(id)
		on delete cascade on update cascade,
    foreign key(idProceso) references proceso(id),
    foreign key(correo_encargado) references auditor(correo)
);

create table requisito_acta(
    id int(10) auto_increment primary key,
    idRequisito int(10) not null,
    idProcesoActa int(10) not null,
    cumplimiento int(1),
    foreign key(idProcesoActa) references proceso_acta(id)
		on delete cascade on update cascade,
    foreign key(idRequisito) references requisito(id) 
);

delimiter **
create procedure sp_login(correoLogin varchar(50),pswdLogin varchar(20))
begin
	declare msj varchar(30);
    if (select count(*) from Auditor where correo = correoLogin  and pswd = pswdLogin)>0 then
		set msj = correoLogin;
    else
		set msj = "Correo/Contraseña Incorrecto";
	end if;
    select msj;
end ** 
delimiter ;

insert into Auditor values('xtarevolution@yahoo.com.mx','Ricardo Cruz','abc123','5561997887');
insert into Auditor values('carmacmor@yahoo.com','Carmen Macias','abc123','5523686257');

insert into Norma(clave,correo_auditor,nombre) values ('NOM-Q016','xtarevolution@yahoo.com.mx','Norma Mexicana Q016');
insert into Norma(clave,correo_auditor,nombre) values ('ISO:9000','xtarevolution@yahoo.com.mx','Norma ISO 9000');
insert into Norma(clave,correo_auditor,nombre) values ('ISO:25010','xtarevolution@yahoo.com.mx','Norma ISO modelo SQUARE');
insert into plantilla_auditor values('xtarevolution@yahoo.com.mx',1,'Plantilla 1');
insert into plantilla_auditor values('xtarevolution@yahoo.com.mx',2,'Plantilla 2');

insert into proceso(idPlantilla,descripcion)values(1,"Control de plagas");
insert into requisito(clave_norma,descripcion,idproceso)values('NOM-Q016','Se tienen protocolos para el control de fauna nociva',1);
insert into requisito(clave_norma,descripcion,idproceso)values('NOM-Q016','Se hacen inspecciones periodicas para el control de fauna nociva',1);

insert into proceso(idPlantilla,descripcion)values(1,"Higiene de instalaciones");
insert into requisito(clave_norma,descripcion,idproceso)values('ISO:9000','Se realiza limpieza periodica de las instalaciones',2);


insert into proceso(idPlantilla,descripcion)values(2,"Control de plagas");
insert into requisito(clave_norma,descripcion,idproceso)values('NOM-Q016','Se tienen protocolos para el control de fauna nociva',3);
insert into requisito(clave_norma,descripcion,idproceso)values('NOM-Q016','Se hacen inspecciones periodicas para el control de fauna nociva',3);

insert into proceso(idPlantilla,descripcion)values(2,"Protección del personal");
insert into requisito(clave_norma,descripcion,idproceso)values('ISO:9000','El personal usa equipo de protección en las instalaciones',4);

insert into proceso(idPlantilla,descripcion)values(2,"Higiene de instalaciones");
insert into requisito(clave_norma,descripcion,idproceso)values('ISO:9000','El personal usa equipo de protección en las instalaciones',5);