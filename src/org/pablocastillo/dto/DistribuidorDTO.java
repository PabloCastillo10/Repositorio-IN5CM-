/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pablocastillo.dto;

import org.pablocastillo.model.Distribuidor;

/**
 *
 * @author pablo
 */
public class DistribuidorDTO {
    private static DistribuidorDTO instance;
    private Distribuidor Distribuidores;
    
    private DistribuidorDTO() {
        
    }
    
    public static DistribuidorDTO getDistribuidorDTO(){
        if(instance == null) {
            instance = new DistribuidorDTO();
        }
        return instance;
    }

    public Distribuidor getDistribuidores() {
        return Distribuidores;
    }

    public void setDistribuidores(Distribuidor Distribuidores) {
        this.Distribuidores = Distribuidores;
    }
    
    
}
