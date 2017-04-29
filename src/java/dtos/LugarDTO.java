/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;

/**
 *
 * @author JulioCesar
 */
public class LugarDTO implements Serializable{
    
    private String nombre_lugar;
    private String id_ubicacion;
    private String tipo_lugar;

    public LugarDTO() {
    }

    public LugarDTO(String nombre_lugar, String id_ubicacion, String tipo_lugar) {
        this.nombre_lugar = nombre_lugar;
        this.id_ubicacion = id_ubicacion;
        this.tipo_lugar = tipo_lugar;
    }

    public String getNombre_lugar() {
        return nombre_lugar;
    }

    public void setNombre_lugar(String nombre_lugar) {
        this.nombre_lugar = nombre_lugar;
    }

    public String getId_ubicacion() {
        return id_ubicacion;
    }

    public void setId_ubicacion(String id_ubicacion) {
        this.id_ubicacion = id_ubicacion;
    }

    public String getTipo_lugar() {
        return tipo_lugar;
    }

    public void setTipo_lugar(String tipo_lugar) {
        this.tipo_lugar = tipo_lugar;
    }
    
    
    
}
