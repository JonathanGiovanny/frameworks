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
public class Factura {

    private int id_factura;
    private Proveedor id_proveedor;
    private Date fecha_compra;
    private TipoFactura tipo_factura;
    private Persona id_cajero;
    private Persona id_cliente;
    private Sucursal id_sucursal;
    private List<Item> listaItems;

    public int getId_factura() {
        return id_factura;
    }

    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
    }

    public Date getFecha_compra() {
        return fecha_compra;
    }

    public void setFecha_compra(Date fecha_compra) {
        this.fecha_compra = fecha_compra;
    }

    public TipoFactura getTipo_factura() {
        return tipo_factura;
    }

    public void setTipo_factura(TipoFactura tipo_factura) {
        this.tipo_factura = tipo_factura;
    }

   

    public Sucursal getId_sucursal() {
        return id_sucursal;
    }

    public void setId_sucursal(Sucursal id_sucursal) {
        this.id_sucursal = id_sucursal;
    }

    public Persona getId_cajero() {
        return id_cajero;
    }

    public void setId_cajero(Persona id_cajero) {
        this.id_cajero = id_cajero;
    }

    public Persona getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Persona id_cliente) {
        this.id_cliente = id_cliente;
    }

    public List<Item> getListaItems() {
        return listaItems;
    }

    public void setListaItems(List<Item> listaItems) {
        this.listaItems = listaItems;
    }

    public Proveedor getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(Proveedor id_proveedor) {
        this.id_proveedor = id_proveedor;
    }
    
    

}
