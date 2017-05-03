/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;

/**
 *
 * @author ASUS1
 */
public class SucursalDTO implements Serializable{
     
    private String id_lugar;
    private String nombre_sucursal;
    private String direccion;

    public SucursalDTO() {
    }

    public SucursalDTO(String id_lugar, String nombre_sucursal, String direccion) {
        this.id_lugar = id_lugar;
        this.nombre_sucursal = nombre_sucursal;
        this.direccion = direccion;
    }
    
    

    public String getId_lugar() {
        return id_lugar;
    }

    public void setId_lugar(String id_lugar) {
        this.id_lugar = id_lugar;
    }

    public String getNombre_sucursal() {
        return nombre_sucursal;
    }

    public void setNombre_sucursal(String nombre_sucursal) {
        this.nombre_sucursal = nombre_sucursal;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    
    
}
