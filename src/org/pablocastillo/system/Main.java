/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pablocastillo.system;

import java.io.InputStream;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.pablocastillo.controller.FormAsignarEController;
import org.pablocastillo.controller.FormClienteController;
import org.pablocastillo.controller.FormDistribuidoresController;
import org.pablocastillo.controller.MenuCargosController;
import org.pablocastillo.controller.MenuClientesController;
import org.pablocastillo.controller.MenuDistribuidoresController;
import org.pablocastillo.controller.MenuPrincipalController;
import org.pablocastillo.controller.MenuTicketSoporteController;
import org.pablocastillo.controller.FormCargosController;
import org.pablocastillo.controller.FormCategoriaPController;
import org.pablocastillo.controller.FormComprasController;
import org.pablocastillo.controller.FormDetalleCompraController;
import org.pablocastillo.controller.FormDetalleFacturaController;
import org.pablocastillo.controller.FormEmpleadosController;
import org.pablocastillo.controller.FormFacturasController;
import org.pablocastillo.controller.FormProductosController;
import org.pablocastillo.controller.FormPromocionesController;
import org.pablocastillo.controller.FormUsuarioController;
import org.pablocastillo.controller.LoginController;
import org.pablocastillo.controller.MenuCategoriaPController;
import org.pablocastillo.controller.MenuComprasController;
import org.pablocastillo.controller.MenuEmpleadosController;
import org.pablocastillo.controller.MenuFacturasController;
import org.pablocastillo.controller.MenuProductosController;
import org.pablocastillo.controller.MenuPromocionesController;

/**
 *
 * @author pablo
 */
public class Main extends Application {
    
    private Stage stage;
    private Scene scene;
    private final String URLVIEW = "/org/pablocastillo/view/";
    
    @Override
    public void start(Stage stage) throws Exception {
        
        this.stage = stage;
        stage.setTitle("Super Kinal App");
        formUsuarioView();
        stage.show();
    }
    
    public Initializable switchScene(String fxmlName, int width, int height) throws Exception{
        Initializable resultado;
        FXMLLoader loader = new FXMLLoader();
        
        InputStream file = Main.class.getResourceAsStream(URLVIEW + fxmlName);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(URLVIEW + fxmlName));
        
        scene = new Scene((AnchorPane) loader.load(file), width, height);
        stage.setScene(scene);
        stage.sizeToScene();
             
        resultado = (Initializable)loader.getController();
        
        return resultado;
    }
    
    public void menuPrincipalView(){
        try{
            MenuPrincipalController menuPrincipalView = (MenuPrincipalController)switchScene("MenuPrincipalView.fxml", 950, 700);
            menuPrincipalView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuClienteView(){
         try{
            MenuClientesController menuClienteView = (MenuClientesController)switchScene("MenuClientesView.fxml", 1200, 750);
            menuClienteView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void formClienteView(int op){
         try{
            FormClienteController formClienteView = (FormClienteController)switchScene("FormClientesView.fxml", 500, 750);
            formClienteView.setOp(op);
            formClienteView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuTicketSoporteView() {
        try{
            MenuTicketSoporteController menuTicketSoporteView = (MenuTicketSoporteController) switchScene("MenuTicketSoporteView.fxml", 1200, 750);
            menuTicketSoporteView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuDistribuidorView(){
        try{
            MenuDistribuidoresController menuDistribuidorView = (MenuDistribuidoresController) switchScene("MenuDistribuidoresView.fxml", 1200, 750);
            menuDistribuidorView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void formDistribuidorView(int op) {
        try{
            FormDistribuidoresController formDistribuidoresView = (FormDistribuidoresController) switchScene("FormDistribuidoresView.fxml", 500, 750);
            formDistribuidoresView.setOp(op);
            formDistribuidoresView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuCargosView(){
        try{
            MenuCargosController menuCargosView = (MenuCargosController) switchScene ("MenuCargosView.fxml", 1200, 750);
            menuCargosView.setStage(this);       
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void formCargosView(int op) {
        try{
           FormCargosController formCargosView = (FormCargosController) switchScene ("FormCargosView.fxml", 500, 750);
           formCargosView.setOp(op);
           formCargosView.setStage(this);
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void menuCategoriaPView(){
        try{
            MenuCategoriaPController menuCategoriaP = (MenuCategoriaPController)switchScene("MenuCategoriaPView.fxml", 1200, 750);
            menuCategoriaP.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void formCategoriaPView(int op){
        try{
            FormCategoriaPController formCategoriaP = (FormCategoriaPController)switchScene("FormCategoriaPView.fxml", 500, 750);
            formCategoriaP.setOp(op);
            formCategoriaP.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuEmpleadosView(){
        try{
            MenuEmpleadosController menuEmpleado = (MenuEmpleadosController)switchScene("MenuEmpleadosView.fxml", 1200, 750);
            menuEmpleado.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void formEmpleadosView(int op){
        try{
            FormEmpleadosController formEmpleado = (FormEmpleadosController)switchScene("FormEmpleadosView.fxml", 500, 1000);
            formEmpleado.setOp(op);
            formEmpleado.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void formAsignarEView(){
        try{
            FormAsignarEController formAsignarE = (FormAsignarEController)switchScene("FormAsignarEView.fxml", 500, 750);
            formAsignarE.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuProductosView(){
        try{
            MenuProductosController menuProductos = (MenuProductosController)switchScene("MenuProductosView.fxml", 1200, 750);
            menuProductos.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void formProductosView(int op){
        try{
            FormProductosController formProductos = (FormProductosController)switchScene("FormProductosView.fxml", 1200, 750);
            formProductos.setOp(op);
            formProductos.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuPromocionesView(){
        try{
            MenuPromocionesController menuPromociones = (MenuPromocionesController)switchScene("MenuPromocionesView.fxml", 1200, 750);
            menuPromociones.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void formPromocionesView(int op){
        try{
            FormPromocionesController formPromociones = (FormPromocionesController)switchScene("FormPromocionesView.fxml", 500, 750);
            formPromociones.setOp(op);
            formPromociones.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuFacturasView(){
        try{
            MenuFacturasController menuFacturas = (MenuFacturasController)switchScene("MenuFacturasView.fxml", 1200, 750);
            menuFacturas.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void formFacturasView(int op){
        try{
            FormFacturasController formFacturas = (FormFacturasController)switchScene("FormFacturasView.fxml", 500, 750);
            formFacturas.setOp(op);
            formFacturas.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void formDetalleFacturaView(int op){
        try{
            FormDetalleFacturaController formDetalleFactura = (FormDetalleFacturaController)switchScene("FormDetalleFacturaView.fxml", 500, 750);
            formDetalleFactura.setOp(op);
            formDetalleFactura.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuComprasView(){
        try{
            MenuComprasController menuCompras = (MenuComprasController)switchScene("MenuComprasView.fxml", 1200, 750);
            menuCompras.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void formComprasView(int op){
        try{
            FormComprasController formCompras = (FormComprasController)switchScene("FormComprasView.fxml", 500, 750);
            formCompras.setOp(op);
            formCompras.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void formDetalleCompraView(int op){
        try{
            FormDetalleCompraController formDetalleFactura = (FormDetalleCompraController)switchScene("FormDetalleCompraView.fxml", 500, 750);
            formDetalleFactura.setOp(op);
            formDetalleFactura.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void formUsuarioView(){
        try{
            FormUsuarioController formUsuarioView = (FormUsuarioController) switchScene ("FormUsuarioView.fxml", 500, 750);
            formUsuarioView.setStage(this);
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void loginView(){
        try{
            LoginController loginView = (LoginController) switchScene("LoginView.fxml", 500, 750);
            loginView.setStage(this);
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
