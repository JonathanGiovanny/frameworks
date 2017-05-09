/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import Conexion.HibernateUtil;
import entities.Persona;
import entities.TipoPersona;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author JulioCesar
 */
@ManagedBean(name = "ClienteBean")
@SessionScoped
public class ClienteBean {
    
    private int cedulaCliente;
    private String nombreCliente;
    private String apellidoCliente;
    private String direccionCliente;
    
    
    
    public void guardar(ActionEvent actionEvent){
        try {
            Persona p = new Persona();
            p.setCedula(cedulaCliente);
            p.setApellido(apellidoCliente);
            p.setNombre(nombreCliente);
            p.setDireccion(direccionCliente);
            p.setTipo_persona(TipoPersona.CC);
            
            HibernateUtil.getSession().save(p);
            HibernateUtil.commit();
           
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.close();
            try {
                
                FacesContext.getCurrentInstance().getExternalContext().redirect("Facturacion.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(ClienteBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
    }
    
    
    public int getCedulaCliente() {
        return cedulaCliente;
    }

    public void setCedulaCliente(int cedulaCliente) {
        this.cedulaCliente = cedulaCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }
    
}
