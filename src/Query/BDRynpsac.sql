create database RYNPSAC;
use RYNPSAC;
create table productos(
	Código 			int auto_increment not null primary key,
    Nombre			nvarchar(120),
    Marca			nvarchar(120),
    Fecha			nvarchar(120),
    PCompra			float,
    Cantidad		int,
    Proveedor		nvarchar(120),
    PVenta			float,
    Balance			float
);
insert into productos values(null,'Placa Arduino Uno', 'Arduino', '18/07/23', 30, 12,'Arduino C.C.', 42, -360);
select * from productos;
