/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.util;

import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import utilidades.LeerCSV;

/**
 *
 * @author ASUS1
 */
@ManagedBean(name = "CargarArchivoBean")
@SessionScoped
public class CargarArchivoBean extends EJBBase {

    @PostConstruct
    public void init(){
        this.validarInicioSesion(FacesContext.getCurrentInstance().getExternalContext());
    }
    
    public void accionCargar(FileUploadEvent event) {
        UploadedFile archivo = event.getFile();
        LeerCSV l = LeerCSV.getInstance();

        try {
            l.setBr(archivo.getInputstream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        String url = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Volver");
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
