/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import Conexion.HibernateUtil;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.hibernate.Query;

/**
 *
 * @author ASUS1
 */
@ManagedBean(name = "LoginBean")
@SessionScoped
public class LoginBean {

    private String user;
    private String pass;
    private boolean existeUsuario = false;
    private String nombre;
    private String rol;

    @PostConstruct
    public void init() {
        HibernateUtil.start();
        HibernateUtil.close();
    }

    public void iniciarSesion(ActionEvent actionEvent) {
        try {
            if (validarUsuario()) {
                ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                ec.getSessionMap().put("Usuario", user);
                consultarNombre();
                consultarRol();
                ec.redirect("index.xhtml");
            } else {
                if (!existeUsuario) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "El usuario no se encuentra registrado en el sistema."));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "El usuario y/o la contraseña están incorrectos."));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validarUsuario() throws Exception {
        HibernateUtil.start();
        boolean resultado = false;

        String sql = "SELECT contrasenia FROM PERSONAS WHERE usuario = :user";

        try {
            Query q = HibernateUtil.getSession().createSQLQuery(sql);
            q.setString("user", user);

            List<String> contrasenias = q.list();
            if (!contrasenias.isEmpty()) {
                resultado = pass.equalsIgnoreCase(contrasenias.get(0));
                existeUsuario = true;
            } else {
                existeUsuario = false;
            }

        } finally {
            HibernateUtil.close();
        }

        return resultado;
    }

    private void consultarNombre() {
        HibernateUtil.start();

        String sql = "SELECT CONCAT(Nombre,  ' ', Apellido) as Nombre FROM PERSONAS WHERE usuario = :user";

        try {
            Query q = HibernateUtil.getSession().createSQLQuery(sql);
            q.setString("user", user);

            List<String> nombres = q.list();
            if (!nombres.isEmpty()) {
                nombre = nombres.get(0);
            }

        } finally {
            HibernateUtil.close();
        }
    }

    private void consultarRol() {
        HibernateUtil.start();

        String sql = "SELECT tipo_persona FROM PERSONAS WHERE usuario = :user";

        try {
            Query q = HibernateUtil.getSession().createSQLQuery(sql);
            q.setString("user", user);

            List<String> tipo = q.list();
            if (!tipo.isEmpty()) {
                rol = tipo.get(0).equalsIgnoreCase("AD") ? "Administrador" : "Cajero";
            }

        } finally {
            HibernateUtil.close();
        }
    }
    
    public void cerrarSesion(ActionEvent actionEvent) {
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.getSessionMap().put("Usuario", null);
            user = "";
            pass = "";
            ec.redirect("/Supermercado/login.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

}
