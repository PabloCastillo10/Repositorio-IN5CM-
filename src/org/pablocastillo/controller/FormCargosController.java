/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pablocastillo.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
public class FormCargosController implements Initializable {
    private Main stage;
    private int op;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    
    
    @FXML
    TextField tfCargoId, tfNombreCargo;
    
    @FXML
    Button btnGuardar, btnCancelar;
    
    @FXML
    TextArea taDescripcionCargo;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnGuardar){
            if(op == 1){
                if(!tfNombreCargo.getText().equals("") && !taDescripcionCargo.getText().equals("")){
                    agregarCargos();
                    SuperKinalAlert.getInstance().mostrarAlertaInformacion(400);
                    stage.menuCargosView();
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInformacion(600);
                    tfNombreCargo.requestFocus();
                }  
                
            }else if(op == 2){
                if(!tfNombreCargo.getText().equals("") && !taDescripcionCargo.getText().equals("")){
                    if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(505).get() == ButtonType.OK){
                        editarCargos();
                        SuperKinalAlert.getInstance().mostrarAlertaInformacion(500);
                        CargoDTO.getCargosDTO().setCargos(null);
                        stage.menuCargosView();
                    }else{
                        stage.menuCargosView();
                    }
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInformacion(600);
                    tfNombreCargo.requestFocus();
                    
                }
            }
        }else if(event.getSource() == btnCancelar) {
                        stage.menuCargosView();
                        CargoDTO.getCargosDTO().setCargos(null);
        }
        
    }
                
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(CargoDTO.getCargosDTO().getCargos() !=null){
            cargarDatos(CargoDTO.getCargosDTO().getCargos());
        }
    }

    public void cargarDatos(Cargo cargos){
        tfCargoId.setText(Integer.toString(cargos.getCargoId()));
        tfNombreCargo.setText(cargos.getNombreCargo());
        taDescripcionCargo.setText(cargos.getDescripcionCargo());   
        
    
    }
    
    public void agregarCargos(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_AgregarCargos(?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombreCargo.getText());
            statement.setString(2, taDescripcionCargo.getText());
            statement.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null){
                    statement.close();
                }else if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
        op = 1;
    }
    
     public void editarCargos(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "Call sp_EditarCargo(?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfCargoId.getText()));
            statement.setString(2, tfNombreCargo.getText());
            statement.setString(3, taDescripcionCargo.getText());
            statement.execute();
        }catch(SQLException e){
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
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

    public void setOp(int op) {
        this.op = op;
    }

}


