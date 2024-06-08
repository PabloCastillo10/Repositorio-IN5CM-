/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pablocastillo.controller;

import java.awt.Button;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import org.pablocastillo.report.GenerarReporte;
import org.pablocastillo.system.Main;

/**
 *
 * @author pablo
 */
public class MenuPrincipalController implements Initializable {
    private Main stage;
    
    @FXML
    MenuItem btnMenuClientes, btnTicketSoporte,btnCargos,btnDistribuidores,btnCategoriaP,btnEmpleados,btnProductos,btnPromociones,btnFacturas,btnCompras,btnClientes3,btnProductos3;
    
    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnMenuClientes) {
            stage.menuClienteView();
        } else if(event.getSource() == btnTicketSoporte){
            stage.menuTicketSoporteView();
        } else if(event.getSource() == btnCargos){
            stage.menuCargosView();
        } else if(event.getSource() == btnDistribuidores){
            stage.menuDistribuidorView();
        } else if(event.getSource() == btnCategoriaP){
            stage.menuCategoriaPView();
        } else if(event.getSource() == btnEmpleados){
            stage.menuEmpleadosView();
        }else if(event.getSource() == btnProductos){
            stage.menuProductosView();
        }else if(event.getSource() == btnPromociones){
            stage.menuPromocionesView();
        }else if(event.getSource() == btnFacturas){
            stage.menuFacturasView();
        }else if(event.getSource() == btnCompras){
            stage.menuComprasView();
        }else if(event.getSource() == btnClientes3){
            GenerarReporte.getInstance().generarClientes();
        }else if(event.getSource() == btnProductos3){
            GenerarReporte.getInstance().generarProductos();
        }
    }
    
    public void setStage(Main stage) {
        this.stage = stage;
    }

    public Main getStage() {
        return stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
}
