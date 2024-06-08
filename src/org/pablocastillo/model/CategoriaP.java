/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pablocastillo.model;

/**
 *
 * @author pablo
 */
public class CategoriaP {
    private int categoriaproductosId;
    private String nombreCategoria;
    private String descripcionCategoria;

    public CategoriaP() {
    }

    public CategoriaP(int categoriaproductosId, String nombreCategoria, String descripcionCategoria) {
        this.categoriaproductosId = categoriaproductosId;
        this.nombreCategoria = nombreCategoria;
        this.descripcionCategoria = descripcionCategoria;
    }

    public int getCategoriaproductosId() {
        return categoriaproductosId;
    }

    public void setCategoriaproductosId(int categoriaproductosId) {
        this.categoriaproductosId = categoriaproductosId;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getDescripcionCategoria() {
        return descripcionCategoria;
    }

    public void setDescripcionCategoria(String descripcionCategoria) {
        this.descripcionCategoria = descripcionCategoria;
    }

    @Override
    public String toString() {
         return "Id: " + categoriaproductosId + " | " + nombreCategoria ;
    }

    
    
    
}