/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.TipoPersona;
import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author JulioCesar
 */
public class PersonaDTO implements Serializable{
    private String cedula;
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

    public PersonaDTO() {
    }

    public PersonaDTO(String cedula, String nombre, String apellido, Date fecha_nacimiento, String telefono, String direccion, String correo, String sexo, TipoPersona tipo_persona, String usuario, String contrasenia) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fecha_nacimiento = fecha_nacimiento;
        this.telefono = telefono;
        this.direccion = direccion;
        this.correo = correo;
        this.sexo = sexo;
        this.tipo_persona = tipo_persona;
        this.usuario = usuario;
        this.contrasenia = contrasenia;
    }
    
    
    
    

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
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
    
    
    
    
    
}
