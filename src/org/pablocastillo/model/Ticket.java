/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pablocastillo.model;

/**
 *
 * @author josec
 */
public class Ticket {
    private int ticketId;
    private String descripcion;
    private String Estatus;
    private int clienteId;
    private String cliente;
    private int facturaId;
    private String factura;
    
    public Ticket(){
    }

    public Ticket(int ticketId, String descripcion, String Estatus, String cliente, String factura) {
        this.ticketId = ticketId;
        this.descripcion = descripcion;
        this.Estatus = Estatus;
        this.cliente = cliente;
        this.factura = factura;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstatus() {
        return Estatus;
    }

    public void setEstatus(String Estatus) {
        this.Estatus = Estatus;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(int facturaId) {
        this.facturaId = facturaId;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }
    
    

    @Override
    public String toString() {
        return "Id: " + ticketId + " | " + descripcion;
    }

    
}
