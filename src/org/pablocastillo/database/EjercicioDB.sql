use EjercicioDB;

-- CRUD CLIENTES

DELIMITER $$
CREATE PROCEDURE sp_agregarCliente(nom VARCHAR(30), ape VARCHAR(30), tel VARCHAR(15), dir VARCHAR(200), ni VARCHAR(15))
BEGIN
    INSERT INTO Clientes(nombre, apellido, telefono, direccion, nit)
    VALUES (nom, ape, tel, dir, ni);
END$$
DELIMITER ;


CALL sp_agregarCliente('Messi', 'Messi', '9142-6456', 'Ciudad Plata', '6254564570153');

DELIMITER $$
CREATE PROCEDURE sp_ListarClientes()
BEGIN
    SELECT clienteId, 
		nombre, 
		apellido, 
		telefono, 
		direccion, 
		nit 
		FROM Clientes;
END$$
DELIMITER ;


CALL sp_ListarClientes();


DELIMITER $$
CREATE PROCEDURE sp_EliminarCliente(IN clidId INT)
BEGIN
    DELETE FROM Clientes 
    WHERE clienteId = clidId;
END$$
DELIMITER ;


CALL sp_EliminarCliente(7);


DELIMITER $$
CREATE PROCEDURE sp_BuscarCliente(IN clidId INT)
BEGIN
    SELECT clienteId, 
			nombre, 
			apellido, 
			telefono, 
			direccion, 
			nit 
			FROM Clientes WHERE clienteId = clidId;
END$$
DELIMITER ;


CALL sp_BuscarCliente(3);


DELIMITER $$
CREATE PROCEDURE sp_EditarCliente(IN clidId INT, IN nom VARCHAR(30), IN ape VARCHAR(30), IN tel VARCHAR(15), IN dir VARCHAR(200), IN ni VARCHAR(15))
BEGIN
    UPDATE Clientes
    SET nombre = nom, apellido = ape, telefono = tel, direccion = dir, nit = ni
    WHERE clienteId = clidId;
END$$
DELIMITER ;


CALL sp_EditarCliente(2, 'Angeles', 'De Leon', '2242-3412', 'Ciudad Real', '9252515156857');

-- CRUD CARGOS*************************************************************************

DELIMITER $$
CREATE PROCEDURE sp_agregarCargos(nomC VARCHAR(30), descripC VARCHAR(100))
BEGIN
    INSERT INTO Cargos(nombreCargo, descripcionCargo)
    VALUES (nomC, descripC);
END$$
DELIMITER ;


CALL sp_agregarCargos('Contador', 'Elabora Presupuesto' );

DELIMITER $$
CREATE PROCEDURE sp_ListarCargos()
BEGIN
    SELECT cargoId, 
		nombreCargo, 
		descripcionCargo
		FROM Cargos;
END$$
DELIMITER ;


CALL sp_ListarCargos();

DELIMITER $$
CREATE PROCEDURE sp_EliminarCargo(IN carId INT)
BEGIN
    DELETE FROM Cargos 
    WHERE cargoId = carId;
END$$
DELIMITER ;


CALL sp_EliminarCargo(2);

DELIMITER $$
CREATE PROCEDURE sp_BuscarCargo(IN carId INT)
BEGIN
    SELECT cargoId, 
		nombreCargo, 
		descripcionCargo
		FROM Cargos WHERE cargoId = carId;
END$$
DELIMITER ;


CALL sp_BuscarCargo(2);

DELIMITER $$
CREATE PROCEDURE sp_EditarCargo(IN carId INT, IN nomC VARCHAR(30), IN descripC VARCHAR(100))
BEGIN
    UPDATE Cargos
    SET nombreCargo = nomC, descripcionCargo = descripC
    WHERE cargoId = carId;
END$$
DELIMITER ;


CALL sp_EditarCargo(2, 'Bodega', 'Recibir mercaderia');

-- CRUD EMPLEADOS*************************************************************************

DELIMITER $$
CREATE PROCEDURE sp_agregarEmpleado(nomE VARCHAR(30), apeE VARCHAR(30), sueldoE DECIMAL(10,2), horaEntradaE TIME, horaSalidaE TIME, cargoIdE INT)
BEGIN
    INSERT INTO Empleados(nombreEmpleado, apellidoEmpleado, sueldo, horaentrada, horaSalida, cargoId)
    VALUES (nomE, apeE, sueldoE, horaEntradaE, horaSalidaE, cargoIdE );
END$$
DELIMITER ;


CALL sp_agregarEmpleado('Pedro', 'Gonzales', 2400.00, '06:00:00', '14:00:00', 1);

DELIMITER $$
CREATE PROCEDURE sp_ListarEmpleados()
BEGIN
    SELECT EP.empleadoId, EP.nombreEmpleado, EP.apellidoEmpleado, EP.sueldo, EP.horaentrada, EP.horaSalida,
        CONCAT("Id: ", Ca.cargoId, " | ", Ca.nombreCargo) AS cargo, 
        CONCAT(EE.nombreEmpleado, ' ', EE.apellidoEmpleado) AS nombreEncargado
    FROM Empleados EP
    JOIN Cargos Ca ON EP.cargoId = Ca.cargoId
    LEFT JOIN Empleados EE ON EP.encargadoId = EE.empleadoId;
END$$
DELIMITER ;



CALL sp_ListarEmpleados();


DELIMITER $$
CREATE PROCEDURE sp_EliminarEmpleado(IN empId INT)
BEGIN
    DELETE FROM Empleados 
    WHERE empleadoId = empId;
END$$
DELIMITER ;

CALL sp_EliminarEmpleado(2);


DELIMITER $$
CREATE PROCEDURE sp_BuscarEmpleado(IN empId INT)
BEGIN
    SELECT EP.empleadoId, EP.nombreEmpleado, EP.apellidoEmpleado, EP.sueldo, EP.horaentrada, EP.horaSalida,
        CONCAT("Id: ", Ca.cargoId, " | ", Ca.nombreCargo) AS cargo, 
        CONCAT(EE.nombreEmpleado, ' ', EE.apellidoEmpleado) AS nombreEncargado
    FROM Empleados EP
    JOIN Cargos Ca ON EP.cargoId = Ca.cargoId
    LEFT JOIN Empleados EE ON EP.encargadoId = EE.empleadoId
	WHERE EP.empleadoId = empId;
END$$
DELIMITER ;

CALL sp_BuscarEmpleado(1);


DELIMITER $$
CREATE PROCEDURE sp_EditarEmpleado(IN empId INT, IN nomE VARCHAR(30), IN apeE VARCHAR(30), IN sueldoE DECIMAL(10,2), IN horaEntradaE TIME, IN horaSalidaE TIME, IN cargoIdE INT)
BEGIN
    UPDATE Empleados
    SET nombreEmpleado = nomE, apellidoEmpleado = apeE, sueldo = sueldoE, horaentrada = horaEntradaE, horaSalida = horaSalidaE, cargoId = cargoIdE 
    WHERE empleadoId = empId;
END$$
DELIMITER ;

CALL sp_EditarEmpleado(2, 'Fermin', 'López', 2240.00, '10:00:00', '19:00:00', 1);

-- ENCARGADO EMPLEADOS
Delimiter $$
create procedure sp_AsignarEncargado(In empId Int, In encarId int)
begin

	Update Empleados  
		Set 
			Empleados.encargadoId = encarId
			Where empleadoId = empId;
end$$
Delimiter ;

call sp_AsignarEncargado(1,1);

-- CRUD DISTRIBUIDORES*************************************************************************
DELIMITER $$
CREATE PROCEDURE sp_agregarDistribuidor(nomD VARCHAR(30), dirD VARCHAR(200), niD VARCHAR(15), telD VARCHAR(15), webD VARCHAR(50))
BEGIN
    INSERT INTO Distribuidores(nombreDistribuidor, direccionDistribuidor, nitDistribuidor, telefonoDistribuidor, web)
    VALUES (nomD, dirD, niD, telD, webD);
END$$
DELIMITER ;


CALL sp_agregarDistribuidor('Dany Lux', 'Mexico', '1423457493154', '423-231', 'www.DanyLux.com');

DELIMITER $$
CREATE PROCEDURE sp_ListarDistribuidores()
BEGIN
    SELECT distribuidorId, 
		nombreDistribuidor, 
		direccionDistribuidor,
		nitDistribuidor,
		telefonoDistribuidor,
		web
	FROM Distribuidores;
END$$
DELIMITER ;

CALL sp_ListarDistribuidores();


DELIMITER $$
CREATE PROCEDURE sp_EliminarDistribuidor(IN distId INT)
BEGIN
    DELETE FROM Distribuidores 
    WHERE distribuidorId = distId;
END$$
DELIMITER ;

CALL sp_EliminarDistribuidor(2);


DELIMITER $$
CREATE PROCEDURE sp_BuscarDistribuidor(IN distId INT)
BEGIN
    SELECT distribuidorId, 
		nombreDistribuidor, 
		direccionDistribuidor,
		nitDistribuidor,
		telefonoDistribuidor,
		web
	FROM Distribuidores 
	WHERE distribuidorId = distId;
END$$
DELIMITER ;

CALL sp_BuscarDistribuidor(2);


DELIMITER $$
CREATE PROCEDURE sp_EditarDistribuidor(IN distId INT, IN nomD VARCHAR(30), IN dirD VARCHAR(200), IN niD VARCHAR(15), IN telD VARCHAR(15), IN webD VARCHAR(50))
BEGIN
    UPDATE Distribuidores
    SET nombreDistribuidor = nomD, direccionDistribuidor = dirD, nitDistribuidor = niD, telefonoDistribuidor = telD, web = webD
    WHERE distribuidorId = distId;
END$$
DELIMITER ;

CALL sp_EditarDistribuidor(1, 'Real Madrid', 'Ciudad Deportiva Madrid,Madrid,España', '1111111111111', '777-7777', 'www.RealMadrid.com');

-- CRUD CATEGORIA PRODUCTO*************************************************************************

DELIMITER $$
CREATE PROCEDURE sp_agregarCategoriaProducto(nomCP VARCHAR(30), descCP VARCHAR(100))
BEGIN
    INSERT INTO CategoriaProductos(nombreCategoria, descripcionCategoria)
    VALUES (nomCP, descCP);
END$$
DELIMITER ;

CALL sp_agregarCategoriaProducto('Tecnologicos', 'Productos tecnologicos');

DELIMITER $$
CREATE PROCEDURE sp_ListarCategoriasProductos()
BEGIN
    SELECT categoriaproductosId, 
		nombreCategoria, 
		descripcionCategoria
	FROM CategoriaProductos;
END$$
DELIMITER ;

CALL sp_ListarCategoriasProductos();


DELIMITER $$
CREATE PROCEDURE sp_EliminarCategoriaProducto(IN catProdId INT)
BEGIN
    DELETE FROM CategoriaProductos 
    WHERE categoriaproductosId = catProdId;
END$$
DELIMITER ;

CALL sp_EliminarCategoriaProducto(2);


DELIMITER $$
CREATE PROCEDURE sp_BuscarCategoriaProducto(IN catProdId INT)
BEGIN
    SELECT categoriaproductosId, 
		nombreCategoria, 
		descripcionCategoria
	FROM CategoriaProductos 
	WHERE categoriaproductosId = catProdId;
END$$
DELIMITER ;

CALL sp_BuscarCategoriaProducto(2);


DELIMITER $$
CREATE PROCEDURE sp_EditarCategoriaProducto(IN catProdId INT, IN nomCP VARCHAR(30), IN descCP VARCHAR(100))
BEGIN
    UPDATE CategoriaProductos
    SET nombreCategoria = nomCP, descripcionCategoria = descCP
    WHERE categoriaproductosId = catProdId;
END$$
DELIMITER ;

CALL sp_EditarCategoriaProducto(1, 'Comida', 'Comida para delgazar');

-- CRUD COMPRA *************************************************************************

DELIMITER $$
CREATE PROCEDURE sp_agregarCompra()
BEGIN
    INSERT INTO Compras(fechaCompra)
    VALUES (curdate());
END$$
DELIMITER ;

CALL sp_agregarCompra();

DELIMITER $$
CREATE PROCEDURE sp_ListarCompras()
BEGIN
    SELECT compraId, 
		fechaCompra, 
		totalCompra
	FROM Compras;
END$$
DELIMITER ;

CALL sp_ListarCompras();


DELIMITER $$
CREATE PROCEDURE sp_EliminarCompra(IN compId INT)
BEGIN
    DELETE FROM Compras 
    WHERE compraId = compId;
END$$
DELIMITER ;

CALL sp_EliminarCompra(2);


DELIMITER $$
CREATE PROCEDURE sp_BuscarCompra(IN compId INT)
BEGIN
    SELECT compraId, 
		fechaCompra, 
		totalCompra
	FROM Compras 
	WHERE compraId = compId;
END$$
DELIMITER ;

CALL sp_BuscarCompra(2);


DELIMITER $$
CREATE PROCEDURE sp_EditarCompra(IN compId INT, IN fechaComp DATE)
BEGIN
    UPDATE Compras
    SET fechaCompra = fechaComp
    WHERE compraId = compId;
END$$
DELIMITER ;

CALL sp_EditarCompra(2, '2024-03-24');

-- CRUD FACTURAS *************************************************************************

DELIMITER $$
CREATE PROCEDURE sp_agregarFactura(cliId INT, empId INT)
BEGIN
    INSERT INTO Facturas(fecha, hora, clienteId, empleadoId)
    VALUES (curdate(), curtime(), cliId, empId);
END$$
DELIMITER ;

CALL sp_agregarFactura(1, 1);

DELIMITER $$
CREATE PROCEDURE sp_ListarFacturas()
BEGIN
    SELECT F.facturaId, F.fecha, F.hora, 
		   CONCAT("Id: ", C.clienteId, " | ", C.nombre, " ", C.apellido) AS cliente,
		   CONCAT("Id: ", E.empleadoId, " | ", E.nombreEmpleado, " ", E.apellidoEmpleado) AS empleado,
		   F.total
	FROM Facturas F
	JOIN Clientes C ON F.clienteId = C.clienteId
	JOIN Empleados E ON F.empleadoId = E.empleadoId;

END$$
DELIMITER ;

CALL sp_ListarFacturas();


DELIMITER $$
CREATE PROCEDURE sp_EliminarFactura(IN factId INT)
BEGIN
    DELETE FROM Facturas 
    WHERE facturaId = factId;
END$$
DELIMITER ;

CALL sp_EliminarFactura(2);


DELIMITER $$
CREATE PROCEDURE sp_BuscarFactura(IN factId INT)
BEGIN
    SELECT F.facturaId, F.fecha, F.hora, 
		   CONCAT("Id: ", C.clienteId, " | ", C.nombre, " ", C.apellido) AS cliente,
		   CONCAT("Id: ", E.empleadoId, " | ", E.nombreEmpleado, " ", E.apellidoEmpleado) AS empleado,
		   F.total
		FROM Facturas F
	JOIN Clientes C ON F.clienteId = C.clienteId
	JOIN Empleados E ON F.empleadoId = E.empleadoId
	WHERE facturaId = factId;
END$$
DELIMITER ;

CALL sp_BuscarFactura(1);


DELIMITER $$
CREATE PROCEDURE sp_EditarFactura(IN factId INT, IN cliId INT, IN empId INT)
BEGIN
    UPDATE Facturas
    SET fecha = curdate(), hora = curtime(), clienteId = cliId, empleadoId = empId
    WHERE facturaId = factId;
END$$
DELIMITER ;

CALL sp_EditarFactura(2, 1, 1);


-- CRUD TicketSoporte *************************************************************************

DELIMITER $$
CREATE PROCEDURE sp_agregarTicketSoporte(descTicket VARCHAR(250), clienteIdTicket INT, facturaIdTicket INT)
BEGIN
    INSERT INTO TicketSoporte(descripcionTicket, estatus, clienteId, facturaId)
    VALUES (descTicket, 'Recién creado', clienteIdTicket, facturaIdTicket);
END$$
DELIMITER ;


CALL sp_agregarTicketSoporte('Problema con el ingreso', 1, Null);

DELIMITER $$
CREATE PROCEDURE sp_ListarTicketsSoporte()
BEGIN
    SELECT TS.ticketSoporteId, TS.descripcionTicket, TS.estatus, 
    CONCAT("Id: ", C.clienteId," | ", C.nombre, " ", C.apellido) AS cliente, TS.facturaId FROM TicketSoporte TS
    JOIN Clientes C on TS.clienteId = C.clienteId;
END$$
DELIMITER ;

CALL sp_ListarTicketsSoporte();


DELIMITER $$
CREATE PROCEDURE sp_EliminarTicketSoporte(IN ticketId INT)
BEGIN
    DELETE FROM TicketSoporte 
    WHERE ticketSoporteId = ticketId;
END$$
DELIMITER ;

CALL sp_EliminarTicketSoporte(1);


DELIMITER $$
CREATE PROCEDURE sp_BuscarTicketSoporte(IN ticketId INT)
BEGIN
    SELECT ticketSoporteId, 
		descripcionTicket, 
		estatus,
		clienteId,
		facturaId
	FROM TicketSoporte 
	WHERE ticketSoporteId = ticketId;
END$$
DELIMITER ;

CALL sp_BuscarTicketSoporte(2);


DELIMITER $$
CREATE PROCEDURE sp_EditarTicketSoporte(IN ticketId INT, IN descTicket VARCHAR(250), IN estatusTicket VARCHAR(30), IN clienteIdTicket INT, IN facturaIdTicket INT)
BEGIN
    UPDATE TicketSoporte
    SET descripcionTicket = descTicket, estatus = estatusTicket, clienteId = clienteIdTicket, facturaId = facturaIdTicket
    WHERE ticketSoporteId = ticketId;
END$$
DELIMITER ;

CALL sp_EditarTicketSoporte(2, 'Messi el mejor', 'Resuelto', 1, NULL);


-- CRUD PRODUCTOS *************************************************************************

DELIMITER $$
CREATE PROCEDURE sp_agregarProductos(nomP VARCHAR(50), descP VARCHAR(100), cantStock INT, precioVenta DECIMAL(10,2), precioVentaM DECIMAL(10,2), precioComp DECIMAL(10,2), imgP longblob, distId INT, cateId INT)
BEGIN
	INSERT INTO Productos(nombreProducto, descripcionProducto, cantidadStock, precioVentaUnitario, precioVentaMayor, precioCompra, imagenProducto, distribuidorId, categoriaproductosId)
	VALUES (nomP, descP, cantStock, precioVenta, precioVentaM, precioComp, imgP, distId, cateId);
END$$
DELIMITER ;

CALL sp_agregarProductos('Laptop HP', 'I5 6 RAM 256GBssd', 200, 5000, 1500, 1500, NULL, 1, 1);

DELIMITER $$
CREATE PROCEDURE sp_ListarProductos()
BEGIN
	SELECT P.productoId, P.nombreProducto, P.descripcionProducto, P.cantidadStock, P.precioVentaUnitario, P.precioVentaMayor,  P.precioCompra,P.imagenProducto, 
       CONCAT("Distribuidor: ", D.nombreDistribuidor) AS distribuidor,
       CONCAT("Categoría: ", CP.nombreCategoria) AS categoria
	FROM Productos P
	LEFT JOIN Distribuidores D ON P.distribuidorId = D.distribuidorId
	LEFT JOIN CategoriaProductos CP ON P.categoriaproductosId = CP.categoriaproductosId;

END$$
DELIMITER ;

CALL sp_ListarProductos();

DELIMITER $$
CREATE PROCEDURE sp_EliminarProducto(IN prodId INT)
BEGIN
	DELETE FROM Productos 
		WHERE productoId = prodId;
END$$
DELIMITER ;

CALL sp_EliminarProducto(2);

DELIMITER $$
CREATE PROCEDURE sp_BuscarProducto(IN prodId INT)
BEGIN
	SELECT P.productoId, P.nombreProducto, P.descripcionProducto, P.cantidadStock, P.precioVentaUnitario, P.precioVentaMayor,  P.precioCompra,P.imagenProducto, 
       CONCAT("Distribuidor: ", D.nombreDistribuidor) AS distribuidor,
       CONCAT("Categoría: ", CP.nombreCategoria) AS categoria
		FROM Productos P
		LEFT JOIN Distribuidores D ON P.distribuidorId = D.distribuidorId
		LEFT JOIN CategoriaProductos CP ON P.categoriaproductosId = CP.categoriaproductosId
		WHERE productoId = prodId;
END$$
DELIMITER ;

CALL sp_BuscarProducto(1);

DELIMITER $$
CREATE PROCEDURE sp_EditarProducto(IN prodId INT, IN nomP VARCHAR(50), IN descP VARCHAR(100), IN cantStock INT, IN precioVenta DECIMAL(10,2), IN precioVentaM DECIMAL(10,2), IN precioComp DECIMAL(10,2), IN imgP longblob, IN distId INT, IN catId INT)
BEGIN
	UPDATE Productos 
	SET nombreProducto = nomP, descripcionProducto = descP, cantidadStock = cantStock, precioVentaUnitario = precioVenta, precioVentaMayor = precioVentaM, precioCompra = precioComp, imagenProducto = imgP, distribuidorId = distId, categoriaproductosId = catId 
    WHERE productoId = prodId;
END$$
DELIMITER ;

CALL sp_EditarProducto(1, 'Iphone 15 Pro', '512GB 6 RAM 48 Camara', 140, 6500.00, 3200.00, 2300.00, NULL, 1,1 );

-- CRUD DETALLE COMPRA *************************************************************************

DELIMITER $$
CREATE PROCEDURE sp_agregarDetalleCompra(cantComp INT, prodIdComp INT, compId INT)
BEGIN
    INSERT INTO detalleCompra(cantidadCompra, productoId, compraId)
    VALUES (cantComp, prodIdComp, compId);
END$$
DELIMITER ;


CALL sp_agregarDetalleCompra(5, 1, 1);

DELIMITER $$
CREATE PROCEDURE sp_ListarDetalleCompra()
BEGIN
    SELECT detalleCompraId, 
		cantidadCompra, 
		productoId, 
		compraId
	FROM detalleCompra;
END$$
DELIMITER ;

CALL sp_ListarDetalleCompra();


DELIMITER $$
CREATE PROCEDURE sp_EliminarDetalleCompra(IN detCompId INT)
BEGIN
    DELETE FROM detalleCompra 
    WHERE detalleCompraId = detCompId;
END$$
DELIMITER ;

CALL sp_EliminarDetalleCompra(1);


DELIMITER $$
CREATE PROCEDURE sp_BuscarDetalleCompra(IN detCompId INT)
BEGIN
    SELECT detalleCompraId, 
		cantidadCompra, 
		productoId, 
		compraId
	FROM detalleCompra 
	WHERE detalleCompraId = detCompId;
END$$
DELIMITER ;

CALL sp_BuscarDetalleCompra(2);


DELIMITER $$
CREATE PROCEDURE sp_EditarDetalleCompra(IN detCompId INT, IN cantComp INT, IN prodIdComp INT, IN compId INT)
BEGIN
    UPDATE detalleCompra
    SET cantidadCompra = cantComp, productoId = prodIdComp, compraId = compId
    WHERE detalleCompraId = detCompId;
END$$
DELIMITER ;

CALL sp_EditarDetalleCompra(1, 10, 1, 1);



-- CRUD DETALLE FACTURA *************************************************************************

DELIMITER $$
CREATE PROCEDURE sp_agregarDetalleFactura(prodIdFact INT, factIdFact INT)
BEGIN
    INSERT INTO detalleFactura(productoId, facturaId)
    VALUES (prodIdFact, factIdFact);
END$$
DELIMITER ;

CALL sp_agregarDetalleFactura(1, 1);

DELIMITER $$
CREATE PROCEDURE sp_ListarDetalleFactura()
BEGIN
    SELECT detalleFacturaId, 
        productoId, 
        facturaId
    FROM detalleFactura;
END$$
DELIMITER ;

CALL sp_ListarDetalleFactura();


DELIMITER $$
CREATE PROCEDURE sp_EliminarDetalleFactura(IN detFactId INT)
BEGIN
    DELETE FROM detalleFactura 
    WHERE detalleFacturaId = detFactId;
END$$
DELIMITER ;

CALL sp_EliminarDetalleFactura(1);


DELIMITER $$
CREATE PROCEDURE sp_BuscarDetalleFactura(IN detFactId INT)
BEGIN
    SELECT detalleFacturaId, 
        productoId, 
        facturaId
    FROM detalleFactura 
    WHERE detalleFacturaId = detFactId;
END$$
DELIMITER ;

CALL sp_BuscarDetalleFactura(2);


DELIMITER $$
CREATE PROCEDURE sp_EditarDetalleFactura(IN detFactId INT, IN prodIdFact INT, IN factIdFact INT)
BEGIN
    UPDATE detalleFactura
    SET productoId = prodIdFact, facturaId = factIdFact
    WHERE detalleFacturaId = detFactId;
END$$
DELIMITER ;

CALL sp_EditarDetalleFactura(1, 1, 1);


-- CRUD PROMOCIONES *************************************************************************

DELIMITER $$
CREATE PROCEDURE sp_agregarPromocion(precioProm DECIMAL(10,2), descProm VARCHAR(100), fechaInicioProm DATE, fechaFinProm DATE, prodIdProm INT)
BEGIN
    INSERT INTO Promociones(precioPromocion, descripcionPromocion, fechaInicio, fechaFinalizacion, productoId)
    VALUES (precioProm, descProm, fechaInicioProm, fechaFinProm, prodIdProm);
END$$
DELIMITER ;

CALL sp_agregarPromocion(2500.00, 'Semana Gamer', '2024-05-02', '2025-04-30', 1);

DELIMITER $$
CREATE PROCEDURE sp_ListarPromociones()
BEGIN
    SELECT 
		PR.promocionId, 
		PR.precioPromocion, 
		PR.descripcionPromocion, 
		PR.fechaInicio, 
		PR.fechaFinalizacion, 
		CONCAT("Id: ", P.productoId," | ", P.nombreProducto) AS Producto
		
	FROM 
		Promociones PR
	JOIN 
		Productos P ON PR.productoId = P.productoId;

END$$
DELIMITER ;

CALL sp_ListarPromociones();


DELIMITER $$
CREATE PROCEDURE sp_EliminarPromocion(IN promId INT)
BEGIN
    DELETE FROM Promociones 
    WHERE promocionId = promId;
END$$
DELIMITER ;

CALL sp_EliminarPromocion(1);


DELIMITER $$
CREATE PROCEDURE sp_BuscarPromocion(IN promId INT)
BEGIN
    SELECT 
		PR.promocionId, 
		PR.precioPromocion, 
		PR.descripcionPromocion, 
		PR.fechaInicio, 
		PR.fechaFinalizacion, 
		CONCAT("Id: ", P.productoId," | ", P.nombreProducto) AS Producto
	FROM 
		Promociones PR
	JOIN 
		Productos P ON PR.productoId = P.productoId
	WHERE promocionId = promId;
END$$
DELIMITER ;

CALL sp_BuscarPromocion(2);


DELIMITER $$
CREATE PROCEDURE sp_EditarPromocion(IN promId INT, IN precioProm DECIMAL(10,2), IN descProm VARCHAR(100), IN fechaInicioProm DATE, IN fechaFinProm DATE, IN prodIdProm INT)
BEGIN
    UPDATE Promociones
    SET precioPromocion = precioProm, descripcionPromocion = descProm, fechaInicio = fechaInicioProm, fechaFinalizacion = fechaFinProm, productoId = prodIdProm
    WHERE promocionId = promId;
END$$
DELIMITER ;

CALL sp_EditarPromocion(1, 1200, 'Promoción de invierno', '2024-05-02', '2024-05-30', 2);

-- PROCEDIMIENTOS PARA FACTURAS
Delimiter $$
create procedure sp_asignarTotalFactura(in factId int, in totalFact decimal (10,2))
Begin
	Update facturas
		set total = totalFact
			where facturaId =factId; 
End $$
Delimiter ;

Delimiter $$
create procedure sp_modificarStock(in detaFactId int, in stockActual int)
begin
	Update productos
		set cantidadStock = stockActual
			where productoId = detaFactId;
end $$
Delimiter ;

Delimiter $$
create procedure sp_asignarTotalCompra(in compId int, in totalC decimal (10,2))
Begin
	Update compras
		set totalCompra = totalC
			where compraId =compId; 
End $$
Delimiter ;

Delimiter $$
create procedure sp_modificarStockCompra(in productId int, in stockActual int)
begin
	Update productos
		set cantidadStock = stockActual
			where productoId = productId;
end $$
Delimiter ;

DELIMITER $$

CREATE PROCEDURE sp_agregarUsuario(
    IN us VARCHAR(30), IN con VARCHAR(100), IN nivAccId INT, IN emp INT)
BEGIN
    INSERT INTO Usuarios(usuario, contrasenia, nivelAccesoId, empleadoId) 
    VALUES (us, con, nivAccId, emp);
END $$

DELIMITER ;

Delimiter $$
create procedure sp_buscarUsuario(us varchar(30))
begin
	select * from Usuarios
		where usuario = us;
end $$
delimiter ;


Delimiter $$
create procedure sp_listarNivelAcceso()
begin
	select * from nivelesAcceso;
end $$
delimiter ;
select * from Usuarios;




