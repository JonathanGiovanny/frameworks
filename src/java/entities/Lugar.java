/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.List;

/**
 *
 * @author RA302
 */
public class Lugar {

    private int id_lugar;
    private String nombre_lugar;
    private Lugar id_ubicacion;
    private TipoLugar tipo_lugar;
    private List<Sucursal> listaSucursales;
    private List<Lugar> listaLugares;
    

    public int getId_lugar() {
        return id_lugar;
    }

    public void setId_lugar(int id_lugar) {
        this.id_lugar = id_lugar;
    }

    public String getNombre_lugar() {
        return nombre_lugar;
    }

    public void setNombre_lugar(String nombre_lugar) {
        this.nombre_lugar = nombre_lugar;
    }

    public TipoLugar getTipo_lugar() {
        return tipo_lugar;
    }

    public void setTipo_lugar(TipoLugar tipo_lugar) {
        this.tipo_lugar = tipo_lugar;
    }

    

    public Lugar getId_ubicacion() {
        return id_ubicacion;
    }

    public void setId_ubicacion(Lugar id_ubicacion) {
        this.id_ubicacion = id_ubicacion;
    }

    public List<Sucursal> getListaSucursales() {
        return listaSucursales;
    }

    public void setListaSucursales(List<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public List<Lugar> getListaLugares() {
        return listaLugares;
    }

    public void setListaLugares(List<Lugar> listaLugares) {
        this.listaLugares = listaLugares;
    }
    
    
    
}
