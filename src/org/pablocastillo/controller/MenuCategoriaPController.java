/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pablocastillo.controller;

import java.net.URL;
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
import org.pablocastillo.dto.CategoriaPDTO;
import org.pablocastillo.model.CategoriaP;
import org.pablocastillo.system.Main;
import org.pablocastillo.utils.SuperKinalAlert;


public class MenuCategoriaPController implements Initializable {

    private Main stage;
    private int op;
    
    private static Connection conexion;
    private static PreparedStatement statement;
    private static ResultSet resultSet;
    
    @FXML
    TableView tblCategoriasP;
    
    @FXML
    TableColumn colCategoriaPId,colNombreCategoria,colDescripcionCategoria;
    
    @FXML
    Button btnBack,btnAgregar,btnEditar,btnEliminar,btnBuscar;
    
    @FXML
    TextField tfCategoriaPId;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
    
        if(event.getSource() == btnBack){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnAgregar){
            stage.formCategoriaPView(1);
        }else if(event.getSource() == btnEditar){
            CategoriaPDTO.getCategoriaPDTO().setCategoriaP((CategoriaP)tblCategoriasP.getSelectionModel().getSelectedItem());
            stage.formCategoriaPView(2);
        }else if(event.getSource() == btnEliminar){
            if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(404).get() == ButtonType.OK){
                eliminarCategoriaP(((CategoriaP)tblCategoriasP.getSelectionModel().getSelectedItem()).getCategoriaproductosId());
                cargarDatos();
            }
        }else if (event.getSource() == btnBuscar){
            tblCategoriasP.getItems().clear();
            if(tfCategoriaPId.getText().equals("")){
                cargarDatos();
            
            }else{
                op = 3;
                cargarDatos();
            }
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }   
    
    
    public void cargarDatos(){
        if(op == 3){
            tblCategoriasP.getItems().add(buscarCategoriaP());
            op = 0;
            
        }else{
            tblCategoriasP.setItems(listarCategoriasP()); 
        }
        colCategoriaPId.setCellValueFactory(new PropertyValueFactory<CategoriaP, Integer>("categoriaproductosId"));
        colNombreCategoria.setCellValueFactory(new PropertyValueFactory<CategoriaP, String>("nombreCategoria"));
        colDescripcionCategoria.setCellValueFactory(new PropertyValueFactory<CategoriaP, String>("descripcionCategoria"));
        
    }
    
    public ObservableList<CategoriaP> listarCategoriasP(){
        ArrayList<CategoriaP> categoriasP = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = " CALL sp_ListarCategoriasProductos()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int categoriaPId = resultSet.getInt("categoriaproductosId");
                String nombreCategoria = resultSet.getString("nombreCategoria");
                String descripcionCategoria = resultSet.getString("descripcionCategoria");
            
                categoriasP.add(new CategoriaP(categoriaPId, nombreCategoria, descripcionCategoria));
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
        
        
        return FXCollections.observableList(categoriasP);
    }
    
    public void eliminarCategoriaP(int catProdId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_EliminarCategoriaProducto(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1,catProdId);
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
    
    public CategoriaP buscarCategoriaP(){
        CategoriaP categoriaP = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_BuscarCategoriaProducto(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1,Integer.parseInt(tfCategoriaPId.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int categoriaPId = resultSet.getInt("categoriaproductosId");
                String nombreCategoriaP = resultSet.getString("nombreCategoria");
                String descripcionCategoriaP = resultSet.getString("descripcionCategoria");
                
                categoriaP = new CategoriaP(categoriaPId, nombreCategoriaP, descripcionCategoriaP);
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
        return categoriaP;
    }
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
}
