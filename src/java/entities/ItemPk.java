/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author RA302
 */
public class ItemPk implements Serializable {

    private int id_producto;
    private int id_factura;

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getId_factura() {
        return id_factura;
    }

    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.id_producto;
        hash = 89 * hash + this.id_factura;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ItemPk other = (ItemPk) obj;
        if (this.id_producto != other.id_producto) {
            return false;
        }
        if (this.id_factura != other.id_factura) {
            return false;
        }
        return true;
    }

   
}
