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
import org.pablocastillo.dto.CategoriaPDTO;
import org.pablocastillo.model.CategoriaP;
import org.pablocastillo.system.Main;
import org.pablocastillo.utils.SuperKinalAlert;


public class FormCategoriaPController implements Initializable {
    private Main stage;
    private int op;
    
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    
   @FXML
    Button btnRegresarFMA,btnGuardar;
   
   @FXML
   TextField tfCategoriaPId,tfNombreCategoriaP,tfDescripcionCategoriaP;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
    
        if(event.getSource() == btnRegresarFMA){
            CategoriaPDTO.getCategoriaPDTO().setCategoriaP(null);
            stage.menuCategoriaPView();
        }else if(event.getSource() == btnGuardar){
            if(op == 1){
                if(!tfNombreCategoriaP.getText().equals("") && !tfDescripcionCategoriaP.getText().equals("")){
                    agregarCategoriaP();
                    SuperKinalAlert.getInstance().mostrarAlertaInformacion(400);
                    stage.menuCategoriaPView();
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInformacion(33);
                    if(tfNombreCategoriaP.getText().equals("")){
                        tfNombreCategoriaP.requestFocus();
                    }else if(tfDescripcionCategoriaP.getText().equals("")){
                        tfDescripcionCategoriaP.requestFocus();
                    }
                }
                
            }else if(op == 2){
                if(!tfNombreCategoriaP.getText().equals("") && !tfDescripcionCategoriaP.getText().equals("")){
                    editarCategoriaP();
                    if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(505).get() == ButtonType.OK){
                        CategoriaPDTO.getCategoriaPDTO().setCategoriaP(null);
                        SuperKinalAlert.getInstance().mostrarAlertaInformacion(500);
                        stage.menuCategoriaPView();
                    }else{
                        stage.menuCategoriaPView();
                    }
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInformacion(33);
                    if(tfNombreCategoriaP.getText().equals("")){
                        tfNombreCategoriaP.requestFocus();
                    }else if(tfDescripcionCategoriaP.getText().equals("")){
                        tfDescripcionCategoriaP.requestFocus();
                    }
                }
                
            }
        }
    
    }
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       if(CategoriaPDTO.getCategoriaPDTO().getCategoriaP() != null){
            cargarDatos(CategoriaPDTO.getCategoriaPDTO().getCategoriaP());
        }
    }
    
    public void cargarDatos(CategoriaP categoriaP){
        tfCategoriaPId.setText(Integer.toString(categoriaP.getCategoriaproductosId()));
        tfNombreCategoriaP.setText(categoriaP.getNombreCategoria());
        tfDescripcionCategoriaP.setText(categoriaP.getDescripcionCategoria());

    }
    
    public void agregarCategoriaP(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_agregarCategoriaProducto(?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombreCategoriaP.getText());
            statement.setString(2, tfDescripcionCategoriaP.getText());
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
    
    public void editarCategoriaP(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_EditarCategoriaProducto(?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfCategoriaPId.getText()));
            statement.setString(2, tfNombreCategoriaP.getText());
            statement.setString(3, tfDescripcionCategoriaP.getText());
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
    
    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    public void setOp(int op) {
        this.op = op;
    }
    
}
