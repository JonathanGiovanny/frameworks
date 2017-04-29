/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.sql.Date;

/**
 *
 * @author ASUS1
 */
public class FacturaDTO {

    private int idFactura;
    private Date fechaFactura;
    private String tipoFactura;
    private String nombreSucursal;
    private String idCajero;
    private String idCliente;
    private String nombreProveedor;

    public FacturaDTO() {
        //Constructor vacio
    }

    public FacturaDTO(int idFactura, Date fechaFactura, String tipoFactura, String nombreSucursal, String idCajero, String idCliente, String nombreProveedor) {
        this.idFactura = idFactura;
        this.fechaFactura = fechaFactura;
        this.tipoFactura = tipoFactura;
        this.nombreSucursal = nombreSucursal;
        this.idCajero = idCajero;
        this.idCliente = idCliente;
        this.nombreProveedor = nombreProveedor;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public Date getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(Date fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public String getTipoFactura() {
        return tipoFactura;
    }

    public void setTipoFactura(String tipoFactura) {
        this.tipoFactura = tipoFactura;
    }

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

    public String getIdCajero() {
        return idCajero;
    }

    public void setIdCajero(String idCajero) {
        this.idCajero = idCajero;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }
}
