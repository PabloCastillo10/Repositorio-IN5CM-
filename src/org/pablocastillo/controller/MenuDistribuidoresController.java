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
import org.pablocastillo.dto.DistribuidorDTO;
import org.pablocastillo.model.Distribuidor;
import org.pablocastillo.system.Main;
import org.pablocastillo.utils.SuperKinalAlert;


/**
 * FXML Controller class
 *
 * @author pablo
 */
public class MenuDistribuidoresController implements Initializable {
    private Main stage;
    public int op;
    
    private static Connection conexion;
    private static PreparedStatement statement;
    private static ResultSet resultSet;
    
    @FXML
    TableView tblDistribuidores;
    
    @FXML
    TableColumn colDistribuidorId, colNombre, colDireccion, colNit, colTelefono, colWeb;
    
    @FXML
    Button btnBack, btnAgregar, btnEditar, btnEliminar, btnBuscar;
    
    @FXML
    TextField tfDistribuidorId;
    
     @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnBack){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnAgregar){
            stage.formDistribuidorView(1);
        }else if(event.getSource() == btnEditar){
            DistribuidorDTO.getDistribuidorDTO().setDistribuidores((Distribuidor)tblDistribuidores.getSelectionModel().getSelectedItem());
            stage.formDistribuidorView(2);
        }else if(event.getSource() == btnEliminar){
            if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(404).get() == ButtonType.OK){
                eliminarDistribuidor(((Distribuidor)tblDistribuidores.getSelectionModel().getSelectedItem()).getDistribuidorId());
                cargarDatos(); 
            }
        }else if(event.getSource() == btnBuscar){
            tblDistribuidores.getItems().clear();
            if(tfDistribuidorId.getText().equals("")){
                cargarDatos();
            }else{
                op = 3;
                cargarDatos();
            }
        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

     public void cargarDatos(){
        if(op == 3){
            tblDistribuidores.getItems().add(buscarDistribuidor());
            op = 0;
        }else{
            tblDistribuidores.setItems(listarDistribuidor());
        }
        colDistribuidorId.setCellValueFactory(new PropertyValueFactory<Distribuidor, Integer>("distribuidorId"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Distribuidor, String>("nombreDistribuidor"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Distribuidor, String>("direccionDistribuidor"));
        colNit.setCellValueFactory(new PropertyValueFactory<Distribuidor, String>("NitDistribuidor"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Distribuidor, String>("telefonoDistribuidor"));
        colWeb.setCellValueFactory(new PropertyValueFactory<Distribuidor, String>("web"));
    }
    
    public ObservableList<Distribuidor> listarDistribuidor(){
        ArrayList<Distribuidor> Distribuidores = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarDistribuidores()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int distribuidorId = resultSet.getInt("distribuidorId");
                String nombreDistribuidor = resultSet.getString("nombreDistribuidor");
                String direccionDistribuidor = resultSet.getString("direccionDistribuidor");
                String NitDistribuidor = resultSet.getString("NitDistribuidor");
                String telefonoDistribuidor = resultSet.getString("telefonoDistribuidor");
                String web = resultSet.getString("web");
                
                Distribuidores.add(new Distribuidor(distribuidorId, nombreDistribuidor, direccionDistribuidor, NitDistribuidor, telefonoDistribuidor, web));
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
        return FXCollections.observableList(Distribuidores);
    }
    
    public void eliminarDistribuidor(int disId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_EliminarDistribuidor(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, disId);
            statement.execute();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }finally{
            try{
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
    
    public Distribuidor buscarDistribuidor(){
        Distribuidor distribuidores = null;
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_BuscarDistribuidor(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfDistribuidorId.getText()));
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                int distribuidorId = resultSet.getInt("distribuidorId");
                String nombreDistribuidor = resultSet.getString("nombreDistribuidor");
                String direccionDistribuidor = resultSet.getString("direccionDistribuidor");
                String NitDistribuidor = resultSet.getString("NitDistribuidor");
                String telefonoDistribuidor = resultSet.getString("telefonoDistribuidor");
                String web = resultSet.getString("web");
                
                distribuidores = new Distribuidor(distribuidorId, nombreDistribuidor, direccionDistribuidor, NitDistribuidor, telefonoDistribuidor, web);
            }
            
        }catch(Exception e){
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
        return distribuidores;
    }
    public void setStage(Main stage) {
        this.stage = stage;
    }

    public Main getStage() {
        return stage;
    }
}
