-- DROP DATABASE IF EXISTS EjercicioDB;

CREATE DATABASE IF NOT EXISTS EjercicioDB;

USE EjercicioDB;

-- ********************************** TABLAS ********************************** --

CREATE TABLE Clientes(
    clienteId INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(30) NOT NULL,    
    apellido VARCHAR(30) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    direccion VARCHAR(200) NOT NULL,
    nit VARCHAR(15) NOT NULL,
    PRIMARY KEY (clienteId)
);

CREATE TABLE Cargos(
    cargoId INT NOT NULL AUTO_INCREMENT,
    nombreCargo VARCHAR(30) NOT NULL,    
    descripcionCargo VARCHAR(100) NOT NULL,
    PRIMARY KEY (cargoId)
);

CREATE TABLE Empleados(
    empleadoId INT NOT NULL AUTO_INCREMENT,
    nombreEmpleado VARCHAR(30) NOT NULL,    
    apellidoEmpleado VARCHAR(30) NOT NULL,
    sueldo DECIMAL(10,2) NOT NULL,
    horaentrada TIME,
    horaSalida TIME,
    cargoId INT NOT NULL,
    encargadoId INT,
    PRIMARY KEY (empleadoId),
    CONSTRAINT FK_encargadoId_Empleados FOREIGN KEY (encargadoId)
        REFERENCES Empleados(empleadoId),
    CONSTRAINT FK_cargoId_Empleados FOREIGN KEY (cargoId)
        REFERENCES Cargos(cargoId)
);

CREATE TABLE Distribuidores(
    distribuidorId INT NOT NULL AUTO_INCREMENT,
    nombreDistribuidor VARCHAR(30) NOT NULL,
    direccionDistribuidor VARCHAR(200) NOT NULL,
    nitDistribuidor VARCHAR(15) NOT NULL,
    telefonoDistribuidor VARCHAR(15) NOT NULL,
    web VARCHAR(50),
    PRIMARY KEY (distribuidorId)
);

CREATE TABLE CategoriaProductos(
    categoriaproductosId INT NOT NULL AUTO_INCREMENT,
    nombreCategoria VARCHAR(30),
    descripcionCategoria VARCHAR(100),
    PRIMARY KEY (categoriaproductosId)
);

CREATE TABLE Compras(
    compraId INT NOT NULL AUTO_INCREMENT,
    fechaCompra DATE NOT NULL,
    totalCompra DECIMAL(10,2),
    PRIMARY KEY (compraId)
);

CREATE TABLE Facturas(
    facturaId INT NOT NULL AUTO_INCREMENT,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    clienteId INT NOT NULL,
    empleadoId INT NOT NULL,
    total DECIMAL(10,2),
    PRIMARY KEY (facturaId),
    CONSTRAINT FK_clienteId_Facturas FOREIGN KEY (clienteId)
        REFERENCES Clientes(clienteId),
    CONSTRAINT FK_empleadoId_Facturas FOREIGN KEY (empleadoId)
        REFERENCES Empleados(empleadoId)
);

CREATE TABLE TicketSoporte(
    ticketSoporteId INT NOT NULL AUTO_INCREMENT,
    descripcionTicket VARCHAR(250) NOT NULL,
    estatus VARCHAR(30) NOT NULL,
    clienteId INT NOT NULL,
    facturaId INT,
    PRIMARY KEY (ticketSoporteId),
    CONSTRAINT FK_clienteId_TicketSoporte FOREIGN KEY (clienteId)
        REFERENCES Clientes(clienteId),
    CONSTRAINT FK_facturaId_TicketSoporte FOREIGN KEY (facturaId)
        REFERENCES Facturas(facturaId)
);

CREATE TABLE Productos(
    productoId INT NOT NULL AUTO_INCREMENT,
    nombreProducto VARCHAR(50) NOT NULL,
    descripcionProducto VARCHAR(100),
    cantidadStock INT NOT NULL,
    precioVentaUnitario DECIMAL(10,2),
    precioVentaMayor DECIMAL(10,2),
    precioCompra DECIMAL(10,2),
    imagenProducto LONGBLOB,
    distribuidorId INT NOT NULL,
    categoriaproductosId INT NOT NULL,
    PRIMARY KEY (productoId),
    CONSTRAINT FK_distribuidorId_Productos FOREIGN KEY (distribuidorId)
        REFERENCES Distribuidores(distribuidorId),
    CONSTRAINT FK_categoriaproductosId_Productos FOREIGN KEY (categoriaproductosId)
        REFERENCES CategoriaProductos(categoriaproductosId)
);

CREATE TABLE detalleCompra(
    detalleCompraId INT NOT NULL AUTO_INCREMENT,
    cantidadCompra INT NOT NULL,
    productoId INT NOT NULL,
    compraId INT NOT NULL,
    PRIMARY KEY (detalleCompraId),
    CONSTRAINT FK_productoId_detalleCompra FOREIGN KEY (productoId)
        REFERENCES Productos(productoId),
    CONSTRAINT FK_compraId_detalleCompra FOREIGN KEY (compraId)
        REFERENCES Compras(compraId)
);

CREATE TABLE detalleFactura(
    detalleFacturaId INT NOT NULL AUTO_INCREMENT,
    productoId INT NOT NULL,
    facturaId INT NOT NULL,
    PRIMARY KEY (detalleFacturaId),
    CONSTRAINT FK_productoId_detalleFactura FOREIGN KEY (productoId)
        REFERENCES Productos(productoId),
    CONSTRAINT FK_facturaId_detalleFactura FOREIGN KEY (facturaId)
        REFERENCES Facturas(facturaId)
);

CREATE TABLE Promociones(
    promocionId INT NOT NULL AUTO_INCREMENT,
    precioPromocion DECIMAL(10,2) NOT NULL,
    descripcionPromocion VARCHAR(100),
    fechaInicio DATE NOT NULL,
    fechaFinalizacion DATE NOT NULL,
    productoId INT NOT NULL,
    PRIMARY KEY (promocionId),
    CONSTRAINT FK_productoId_Promociones FOREIGN KEY (productoId)
        REFERENCES Productos(productoId)
);

CREATE TABLE NivelesAcceso (
    nivelAccesoId INT NOT NULL AUTO_INCREMENT,
    nivelAcceso VARCHAR(40) NOT NULL,
    PRIMARY KEY (nivelAccesoId)
);

CREATE TABLE Usuarios (
    usuarioId INT NOT NULL AUTO_INCREMENT,
    usuario VARCHAR(30) NOT NULL,
    contrasenia VARCHAR(100) NOT NULL,
    nivelAccesoId INT NOT NULL,
    empleadoId INT NOT NULL,
    PRIMARY KEY (usuarioId),
    CONSTRAINT FK_Usuarios_NivelesAcceso FOREIGN KEY (nivelAccesoId)
        REFERENCES NivelesAcceso(nivelAccesoId),
    CONSTRAINT FK_Usuarios_Empleados FOREIGN KEY (empleadoId)
        REFERENCES Empleados(empleadoId)
);

INSERT INTO NivelesAcceso(nivelAcceso)VALUES 
('Admin'),
('User');


SET GLOBAL time_zone = '-6:00';
