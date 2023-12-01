Create database MasQueArquitectura;

use MasQueArquitectura;

create table Usuario(
Id_Usuario int primary key auto_increment not null,
Nombre varchar(50) not null,
Apellido varchar(50) not null,
Telefono varchar(15) not null,
Email nvarchar(30) not null,
Contrasena nvarchar(70) not null,
Tipo varchar(30) not null
);

create table Token(
id_token int primary key auto_increment not null,
token nvarchar(300) not null,
revocado tinyint default 1 not null,
expirado tinyint default 1 not null,
id_usuario int not null,

foreign key (id_usuario) references usuario(id_usuario)
on delete cascade
on update cascade
);

create table Cliente(
Id_Cliente int primary key not null,
Nombre varchar(50) not null,
Apellido varchar(50) not null,
Telefono varchar(15) not null,
Email nvarchar(30) default 'No registra'
);

create table Servicio(
Id_Servicio int primary key auto_increment not null,
Nombre varchar(100) not null,
Descripcion varchar(500) not null
);

create table Cita(
Id_Cita int primary key auto_increment not null,
Id_Usuario int not null,
id_Cliente int not null,
Fecha_Cita date not null,
Hora_Inicio time(2) not null,
Hora_Final time(2) not null,
foreign key (Id_Usuario) references Usuario (Id_Usuario) on delete cascade
on update cascade,
foreign key (id_Cliente) references Cliente (id_Cliente) on delete cascade 
on update cascade 
);

create table Cotizacion(
Id_Cotizacion int primary key auto_increment not null,
Id_Usuario int not null,
Id_Cliente int not null,
Fecha date not null,
Precio_Total decimal(10,2),
foreign key (Id_Usuario) references Usuario (Id_Usuario) on delete cascade
on update cascade,
foreign key (Id_Cliente) references Cliente (Id_Cliente) on delete cascade
on update cascade
);

create table Servicio_Detalle(
Id_Cotizacion int not null,
Id_Servicio int not null,
Precio decimal(10,2),
foreign key (Id_Cotizacion) references Cotizacion (Id_Cotizacion) on delete cascade
on update cascade,
foreign key (Id_Servicio) references Servicio (Id_Servicio) on delete cascade
on update cascade
);


