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

    private Factura id_factura;
    private Producto id_producto;

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id_factura);
        hash = 67 * hash + Objects.hashCode(this.id_producto);
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
        if (!Objects.equals(this.id_factura, other.id_factura)) {
            return false;
        }
        if (!Objects.equals(this.id_producto, other.id_producto)) {
            return false;
        }
        return true;
    }

}
