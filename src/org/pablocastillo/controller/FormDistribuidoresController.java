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
import javafx.scene.control.TextField;
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
public class FormDistribuidoresController implements Initializable {
    private Main stage;
    private int op;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    
    
    @FXML
    TextField tfDistribuidorId, tfNombre, tfDireccion, tfNit, tfTelefono, tfWeb;
    
    @FXML
    Button btnGuardar, btnCancelar;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnGuardar){
            if(op == 1){
                if(!tfNombre.getText().equals("") && !tfDireccion.getText().equals("") && !tfNit.getText().equals("")){
                    agregarDistribuidor();
                    SuperKinalAlert.getInstance().mostrarAlertaInformacion(400);
                    stage.menuDistribuidorView();
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInformacion(600);
                    tfNombre.requestFocus();
                }  
                
            }else if(op == 2){
                if(!tfNombre.getText().equals("") && !tfDireccion.getText().equals("") && !tfNit.getText().equals("")){
                    if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(505).get() == ButtonType.OK){
                        editarDistribuidor();
                        SuperKinalAlert.getInstance().mostrarAlertaInformacion(500);
                        DistribuidorDTO.getDistribuidorDTO().setDistribuidores(null);
                        stage.menuDistribuidorView();
                    }else{
                        stage.menuDistribuidorView();
                    }
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInformacion(600);
                    tfNombre.requestFocus();
                    
                }
            }
        }else if(event.getSource() == btnCancelar) {
                        stage.menuDistribuidorView();
                        DistribuidorDTO.getDistribuidorDTO().setDistribuidores(null);
        }
        
    }
                
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(DistribuidorDTO.getDistribuidorDTO().getDistribuidores() !=null){
            cargarDatos(DistribuidorDTO.getDistribuidorDTO().getDistribuidores());
        }
    }

    public void cargarDatos(Distribuidor distribuidores){
        tfDistribuidorId.setText(Integer.toString(distribuidores.getDistribuidorId()));
        tfNombre.setText(distribuidores.getNombreDistribuidor());
        tfDireccion.setText(distribuidores.getDireccionDistribuidor());
        tfNit.setText(distribuidores.getNitDistribuidor());
        tfTelefono.setText(distribuidores.getTelefonoDistribuidor());
        tfWeb.setText(distribuidores.getWeb());    
        
    
    }
    
    public void agregarDistribuidor(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarDistribuidor(?, ?, ?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombre.getText());
            statement.setString(2, tfDireccion.getText());
            statement.setString(3, tfNit.getText());
            statement.setString(4, tfTelefono.getText());
            statement.setString(5, tfWeb.getText());
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
    
     public void editarDistribuidor(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "Call sp_EditarDistribuidor(?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfDistribuidorId.getText()));
            statement.setString(2, tfNombre.getText());
            statement.setString(3, tfDireccion.getText());
            statement.setString(4, tfNit.getText());
            statement.setString(5, tfTelefono.getText());
            statement.setString(6, tfWeb.getText());
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


