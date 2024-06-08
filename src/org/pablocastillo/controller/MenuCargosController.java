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
import org.pablocastillo.dto.CargoDTO;
import org.pablocastillo.model.Cargo;
import org.pablocastillo.system.Main;
import org.pablocastillo.utils.SuperKinalAlert;


/**
 * FXML Controller class
 *
 * @author pablo
 */
public class MenuCargosController implements Initializable {
    private Main stage;
    public int op;
    
    private static Connection conexion;
    private static PreparedStatement statement;
    private static ResultSet resultSet;
    
    @FXML
    TableView tblCargos;
    
    @FXML
    TableColumn colCargoId, colNombreCargo, colDescripcionCargo;
    
    @FXML
    Button btnBack, btnAgregar, btnEditar, btnEliminar, btnBuscar;
    
    @FXML
    TextField tfCargoId;
    
     @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnBack){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnAgregar){
            stage.formCargosView(1);
        }else if(event.getSource() == btnEditar){
            CargoDTO.getCargosDTO().setCargos((Cargo)tblCargos.getSelectionModel().getSelectedItem());
            stage.formCargosView(2);
        }else if(event.getSource() == btnEliminar){
            if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(404).get() == ButtonType.OK){
                eliminarCargos(((Cargo)tblCargos.getSelectionModel().getSelectedItem()).getCargoId());
                cargarDatos(); 
            }
        }else if(event.getSource() == btnBuscar){
            tblCargos.getItems().clear();
            if(tfCargoId.getText().equals("")){
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
            tblCargos.getItems().add(buscarCargos());
            op = 0;
        }else{
            tblCargos.setItems(listarCargos());
        }
        colCargoId.setCellValueFactory(new PropertyValueFactory<Cargo, Integer>("cargoId"));
        colNombreCargo.setCellValueFactory(new PropertyValueFactory<Cargo, String>("nombreCargo"));
        colDescripcionCargo.setCellValueFactory(new PropertyValueFactory<Cargo, String>("descripcionCargo"));
    }
    
    public ObservableList<Cargo> listarCargos(){
        ArrayList<Cargo> Cargos = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarCargos()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int cargoId = resultSet.getInt("cargoId");
                String nombreCargo = resultSet.getString("nombreCargo");
                String descripcionCargo = resultSet.getString("descripcionCargo");     
                Cargos.add(new Cargo(cargoId, nombreCargo, descripcionCargo));
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
        return FXCollections.observableList(Cargos);
    }
    
    public void eliminarCargos(int carId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_EliminarCargo(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, carId);
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
    
    public Cargo buscarCargos(){
        Cargo cargos = null;
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_BuscarCargo(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfCargoId.getText()));
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                int cargoId = resultSet.getInt("cargoId");
                String nombreCargo = resultSet.getString("nombreCargo");
                String descripcionCargo = resultSet.getString("descripcionCargo");
                
                
                cargos = new Cargo(cargoId, nombreCargo, descripcionCargo);
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
        return cargos;
    }
    public void setStage(Main stage) {
        this.stage = stage;
    }

    public Main getStage() {
        return stage;
    }
}
