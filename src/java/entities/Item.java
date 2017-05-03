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
    private double precio;
    private int cantidad;

    public ItemPk getItemPk() {
        return itemPk;
    }

    public void setItemPk(ItemPk itemPk) {
        this.itemPk = itemPk;
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
