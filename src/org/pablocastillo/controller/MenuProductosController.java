package org.pablocastillo.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.pablocastillo.dao.Conexion;
import org.pablocastillo.dto.ProductoDTO;
import org.pablocastillo.model.Producto;
import org.pablocastillo.system.Main;
import org.pablocastillo.utils.SuperKinalAlert;


public class MenuProductosController implements Initializable {
    
    private Main stage;
    private int op;
    
    private static Connection conexion;
    private static PreparedStatement statement;
    private static ResultSet resultSet;
    
    @FXML
    TableView tblProductos;
    
    @FXML
    TableColumn colProductoId,colNombreP,colDescripcionP,colCantidadStock,colPrecioVentaU,colPrecioVentaM,colPrecioC,colImagen,colDistribuidorId,colCategoriaPId;
    
    @FXML
    Button btnBack,btnAgregar,btnEditar,btnEliminar,btnBuscar;
    
    @FXML
    TextField tfProductoId;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
    
        if(event.getSource() == btnBack){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnAgregar){
            stage.formProductosView(1);
        }else if(event.getSource() == btnEditar){
            ProductoDTO.getProductoDTO().setProducto((Producto)tblProductos.getSelectionModel().getSelectedItem());
            stage.formProductosView(2);
        }else if(event.getSource() == btnEliminar){
            if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(404).get() == ButtonType.OK){
                eliminarProducto(((Producto)tblProductos.getSelectionModel().getSelectedItem()).getProductoId());
                cargarDatos();
            }
        }else if (event.getSource() == btnBuscar){
            tblProductos.getItems().clear();
            if(tfProductoId.getText().equals("")){
                cargarDatos();
            
            }else{
                op = 3;
                cargarDatos();
            }
        }
    }
    
    public void cargarDatos(){
        if(op == 3){
            tblProductos.getItems().add(buscarProducto());
            op = 0;
        }else{
            tblProductos.setItems(listarProductos()); 
        }
            colProductoId.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("productoId"));
            colNombreP.setCellValueFactory(new PropertyValueFactory<Producto, String>("nombreProducto"));
            colDescripcionP.setCellValueFactory(new PropertyValueFactory<Producto, String>("descripcionProducto"));
            colCantidadStock.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("cantidadStock"));
            colPrecioVentaU.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precioVentaUnitario"));
            colPrecioVentaM.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precioVentaMayor"));
            colPrecioC.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precioCompra"));
            colImagen.setCellValueFactory(new PropertyValueFactory<Producto, Blob>("imagenProducto"));
            colDistribuidorId.setCellValueFactory(new PropertyValueFactory<Producto, String>("distribuidor"));
            colCategoriaPId.setCellValueFactory(new PropertyValueFactory<Producto, String>("CategoriaP"));
        
    }
    
    public ObservableList<Producto> listarProductos(){
        ArrayList<Producto> productos = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = " CALL sp_ListarProductos()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int productoId = resultSet.getInt("productoId");
                String nombreProducto = resultSet.getString("nombreProducto");
                String descripcionProducto = resultSet.getString("descripcionProducto");
                int cantidadStock = resultSet.getInt("cantidadStock");
                double precioVentaUnitario = resultSet.getDouble("precioVentaUnitario");
                double precioVentaMayor = resultSet.getDouble("precioVentaMayor");
                double precioCompra = resultSet.getDouble("precioCompra");
                Blob imagenProducto = resultSet.getBlob("imagenProducto");
                String distribuidor = resultSet.getString("distribuidor");
                String categoria = resultSet.getString("categoria");
            
                productos.add(new Producto(productoId, nombreProducto, descripcionProducto, cantidadStock, precioVentaUnitario, precioVentaMayor,precioCompra,imagenProducto,distribuidor,categoria));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(resultSet != null){
                    resultSet.close();
                }
                
                if(statement != null){
                    statement.close();
                }
                
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
        
        
        return FXCollections.observableList(productos);
    }
     
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    
    public void eliminarProducto(int proId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_EliminarProducto(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1,proId);
            statement.execute();
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(resultSet != null){
                    resultSet.close();
                }
                
                if(statement != null){
                    statement.close();
                }
                
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    public Producto buscarProducto(){
        Producto producto = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_BuscarProducto(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1,Integer.parseInt(tfProductoId.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                 int productoId = resultSet.getInt("productoId");
                String nombreProducto = resultSet.getString("nombreProducto");
                String descripcionProducto = resultSet.getString("descripcionProducto");
                int cantidadStock = resultSet.getInt("cantidadStock");
                double precioVentaUnitario = resultSet.getDouble("precioVentaUnitario");
                double precioVentaMayor = resultSet.getDouble("precioVentaMayor");
                double precioCompra = resultSet.getDouble("precioCompra");
                Blob imagenProducto = resultSet.getBlob("imagenProducto");
                String distribuidor = resultSet.getString("distribuidor");
                String categoria = resultSet.getString("categoria");
            
                producto = new Producto(productoId, nombreProducto, descripcionProducto, cantidadStock, precioVentaUnitario, precioVentaMayor, precioCompra, imagenProducto, distribuidor, categoria);

            }   
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(resultSet != null){
                    resultSet.close();
                }
                
                if(statement != null){
                    statement.close();
                }
                
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return producto;
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }  
    
}
