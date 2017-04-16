/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.Date;
import java.util.List;

/**
 *
 * @author RA302
 */
public class Producto {

    private Integer id_producto;
    private Categoria id_categoria;
    private String nombre;
    private String unidad_medida;
    private Date fecha_vencimiento;
    private Integer stock;
    private Integer cantidad_minima;
    private String marca;

    
    private List<Item> listaItems;

    public List<Item> getListaItems() {
        return listaItems;
    }

    public void setListaItems(List<Item> listaItems) {
        this.listaItems = listaItems;
    }
    
    
    
    public Integer getId_producto() {
        return id_producto;
    }

    public void setId_producto(Integer id_producto) {
        this.id_producto = id_producto;
    }

    public Categoria getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(Categoria id_categoria) {
        this.id_categoria = id_categoria;
    }

    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUnidad_medida() {
        return unidad_medida;
    }

    public void setUnidad_medida(String unidad_medida) {
        this.unidad_medida = unidad_medida;
    }

    public Date getFecha_vencimiento() {
        return fecha_vencimiento;
    }

    public void setFecha_vencimiento(Date fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getCantidad_minima() {
        return cantidad_minima;
    }

    public void setCantidad_minima(Integer cantidad_minima) {
        this.cantidad_minima = cantidad_minima;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

}
