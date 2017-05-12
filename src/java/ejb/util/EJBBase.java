/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.util;

import javax.faces.context.ExternalContext;

/**
 *
 * @author ASUS1
 */
public class EJBBase {

    public void validarInicioSesion(ExternalContext ec) {
        try {
            String usuario = null;
            if (ec != null) {
                usuario = (String) ec.getSessionMap().get("Usuario");
            }
            if (usuario == null || ec.getSession(false) == null) {
                ec.redirect("/Supermercado/login.xhtml");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
