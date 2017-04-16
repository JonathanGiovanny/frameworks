/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author RA302
 */
public class Item {
    
    private ItemPk itemPk;
    private Factura id_factura;
    private Producto id_producto;
    private double precio;
    private int cantidad;

    public ItemPk getItemPk() {
        return itemPk;
    }

    public void setItemPk(ItemPk itemPk) {
        this.itemPk = itemPk;
    }

    
    
    public Factura getId_factura() {
        return id_factura;
    }

    public void setId_factura(Factura id_factura) {
        this.id_factura = id_factura;
    }

    public Producto getId_producto() {
        return id_producto;
    }

    public void setId_producto(Producto id_producto) {
        this.id_producto = id_producto;
    }

    
    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

}
