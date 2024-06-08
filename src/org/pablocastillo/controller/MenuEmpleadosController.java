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
import java.sql.Time;
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
import org.pablocastillo.dto.EmpleadoDTO;
import org.pablocastillo.model.Empleado;
import org.pablocastillo.system.Main;
import org.pablocastillo.utils.SuperKinalAlert;


public class MenuEmpleadosController implements Initializable {
    private Main stage;
    private int op;
    
    private static Connection conexion;
    private static PreparedStatement statement;
    private static ResultSet resultSet;
    
    @FXML
    TableView tblEmpleados;
    
    @FXML
    TableColumn colEmpleadoId,colNombreE,colApellidoE,colSueldo,colHoraEntrada,colHoraSalida,colCargoId,colEncargadoId;
    
    @FXML
    Button btnBack,btnAgregar,btnEditar,btnEliminar,btnBuscar,btnAsignarE;
    
    @FXML
    TextField tfEmpleadoId;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
    
        if(event.getSource() == btnBack){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnAgregar){
            stage.formEmpleadosView(1);
        }else if(event.getSource() == btnEditar){
            EmpleadoDTO.getEmpleadoDTO().setEmpleado((Empleado)tblEmpleados.getSelectionModel().getSelectedItem());
            stage.formEmpleadosView(2);
        }else if(event.getSource() == btnEliminar){
            if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(404).get() == ButtonType.OK){
                eliminarEmpleado(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getEmpleadoId());
                cargarDatos();
            }
        }else if (event.getSource() == btnBuscar){
            tblEmpleados.getItems().clear();
            if(tfEmpleadoId.getText().equals("")){
                cargarDatos();
            
            }else{
                op = 3;
                cargarDatos();
            }
        }else if(event.getSource() == btnAsignarE){
            EmpleadoDTO.getEmpleadoDTO().setEmpleado((Empleado)tblEmpleados.getSelectionModel().getSelectedItem());
            stage.formAsignarEView();
        }
    
    }
    
    public void cargarDatos(){
        if(op == 3){
            tblEmpleados.getItems().add(buscarEmpleado());
            op = 0;
            
        }else{
            tblEmpleados.setItems(listarEmpleados()); 

            colEmpleadoId.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("empleadoId"));
            colNombreE.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombreE"));
            colApellidoE.setCellValueFactory(new PropertyValueFactory<Empleado, String>("apellidoE"));
            colSueldo.setCellValueFactory(new PropertyValueFactory<Empleado, Double>("sueldo"));
            colHoraEntrada.setCellValueFactory(new PropertyValueFactory<Empleado, Time>("horaEntrada"));
            colHoraSalida.setCellValueFactory(new PropertyValueFactory<Empleado, Time>("horaSalida"));
            colCargoId.setCellValueFactory(new PropertyValueFactory<Empleado, String>("cargo"));
            colEncargadoId.setCellValueFactory(new PropertyValueFactory<Empleado, String>("encargado"));
        }
        
        
    }
    
    public ObservableList<Empleado> listarEmpleados(){
        ArrayList<Empleado> empleados = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = " CALL sp_ListarEmpleados()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int empleadoId = resultSet.getInt("empleadoId");
                String nombreEmpleado = resultSet.getString("nombreEmpleado");
                String apellidoEmpleado = resultSet.getString("apellidoEmpleado");
                double sueldo = resultSet.getDouble("sueldo");
                Time horaentrada = resultSet.getTime("horaentrada");
                Time horaSalida = resultSet.getTime("horaSalida");
                String cargoId = resultSet.getString("cargo");
                String encargadoId = resultSet.getString("nombreEncargado");
            
                empleados.add(new Empleado(empleadoId, nombreEmpleado, apellidoEmpleado, sueldo, horaentrada, horaSalida,cargoId,encargadoId));
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
        
        
        return FXCollections.observableList(empleados);
    }
    
    public void eliminarEmpleado(int empId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_EliminarEmpleado(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1,empId);
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
    
    public Empleado buscarEmpleado(){
        Empleado empleado = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_BuscarEmpleado(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1,Integer.parseInt(tfEmpleadoId.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int empleadoId = resultSet.getInt("empleadoId");
                String nombreEmpleado = resultSet.getString("nombreEmpleado");
                String apellidoEmpleado = resultSet.getString("apellidoEmpleado");
                double sueldo = resultSet.getDouble("sueldo");
                Time horaentrada = resultSet.getTime("horaentrada");
                Time horaSalida = resultSet.getTime("horaSalida");
                String cargoId = resultSet.getString("cargo");
                String encargadoId = resultSet.getString("nombreEncargado");
            
                empleado = new Empleado(empleadoId, nombreEmpleado, apellidoEmpleado, sueldo, horaentrada, horaSalida,cargoId,encargadoId);

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
        return empleado;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
}
    

