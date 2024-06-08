/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pablocastillo.model;

import java.sql.Date;

public class Compra {
   private int compraId;
   private Date fechaCompra;
   private double total;

    public Compra(int compraId, Date fechaCompra, double total) {
        this.compraId = compraId;
        this.fechaCompra = fechaCompra;
        this.total = total;
    }

    public int getCompraId() {
        return compraId;
    }

    public void setCompraId(int compraId) {
        this.compraId = compraId;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Id: " + compraId + " | " + total;
    }
   
   
}
