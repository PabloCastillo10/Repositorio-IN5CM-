/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pablocastillo.utils;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author pablo
 */
public class SuperKinalAlert {
    private static SuperKinalAlert instance;
    
    private SuperKinalAlert(){
        
    }
    
    public static SuperKinalAlert getInstance() {
        if(instance == null) {
            instance = new SuperKinalAlert();
            
        }
        return instance;
    }
    
    public void mostrarAlertaInformacion(int code) {
        if(code == 400) { //AGREGAR REGISTROS 400
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmacion  Registro");
            alert.setHeaderText("Confirmacion Registro");
            alert.setContentText("Registro Realizado con exito!");
            alert.showAndWait();
        } else if(code == 500) { //EEDITAR REGISTROS 500
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Edición Registro");
            alert.setHeaderText("Edicion Registro");
            alert.setContentText("¡Edición Realizada con exito!");
            alert.showAndWait();
        } else if(code == 600) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos Pendientes");
            alert.setHeaderText("Campos Pendientes");
            alert.setContentText("Algunos campos necesarios para el registro están vacios");
            alert.showAndWait();
        }else if(code == 602) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Usuario incorrecto");
            alert.setHeaderText("Usuario incorrecto");
            alert.setContentText("Verifique el usuario");
            alert.showAndWait();
        }else if(code == 005) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Contraseña incorrecta");
            alert.setHeaderText("Contraseña incorrecta");
            alert.setContentText("Verifique la contraseña");
            alert.showAndWait();
        }    
    }
        
    
    public Optional<ButtonType> mostrarAlertaConfirmacion(int code){
        Optional<ButtonType> action = null;
        
        if(code == 404){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminacion Registro");
            alert.setHeaderText("Eliminacion Registro");
            alert.setContentText("¿Desea confirmar la eliminacion del registro?");
            action = alert.showAndWait();
        }else if(code == 505) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Edicion Registro");
            alert.setHeaderText("Edicion Registro");
            alert.setContentText("¿Desea Confirmar la edicion del registro?");
            action = alert.showAndWait();
        }
        
        return action;
    }
    
    public void alertaSaludo(String usuario) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bienvenido");
        alert.setHeaderText("Bienvenido " + usuario);
        alert.showAndWait();
            
    }
}
