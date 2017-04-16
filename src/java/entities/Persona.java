/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author RA302
 */
public class Persona {
    
    private int id_persona;
    private int cedula;
    private String nombre;
    private String apellido;
    private Date fecha_nacimiento;
    private String telefono;
    private String direccion;
    private String correo;
    private String sexo;
    private TipoPersona tipo_persona;
    private String usuario;
    private String contrasenia;
    
    private List<Factura> listaFacturasCompra;
    private List<Factura> listaFacturasVenta;

    public int getId_persona() {
        return id_persona;
    }

    public void setId_persona(int id_persona) {
        this.id_persona = id_persona;
    }

    
    
    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public TipoPersona getTipo_persona() {
        return tipo_persona;
    }

    public void setTipo_persona(TipoPersona tipo_persona) {
        this.tipo_persona = tipo_persona;
    }

   

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public List<Factura> getListaFacturasCompra() {
        return listaFacturasCompra;
    }

    public void setListaFacturasCompra(List<Factura> listaFacturasCompra) {
        this.listaFacturasCompra = listaFacturasCompra;
    }

    public List<Factura> getListaFacturasVenta() {
        return listaFacturasVenta;
    }

    public void setListaFacturasVenta(List<Factura> listaFacturasVenta) {
        this.listaFacturasVenta = listaFacturasVenta;
    }

    
    

}
