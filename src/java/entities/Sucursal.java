/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.List;
import java.util.Set;

/**
 *
 * @author RA302
 */
public class Sucursal {

    private int id_sucursal;
    private Lugar id_lugar;
    private String nombre_sucursal;
    private String direccion;
    
     private Set<Factura> listaFacturas;

    public int getId_sucursal() {
        return id_sucursal;
    }

    public void setId_sucursal(int id_sucursal) {
        this.id_sucursal = id_sucursal;
    }

    public Lugar getId_lugar() {
        return id_lugar;
    }

    public void setId_lugar(Lugar id_lugar) {
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

    public Set<Factura> getListaFacturas() {
        return listaFacturas;
    }

    public void setListaFacturas(Set<Factura> listaFacturas) {
        this.listaFacturas = listaFacturas;
    }

   
    
    

}
