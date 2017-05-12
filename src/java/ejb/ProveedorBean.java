/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import Conexion.HibernateUtil;
import dtos.ProveedorDTO;
import ejb.util.EJBBase;
import entities.Proveedor;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.hibernate.Query;
import utilidades.LeerCSV;
import utilidades.Validaciones;

/**
 *
 * @author ASUS1
 */
@ManagedBean(name = "ProveedorBean")
@SessionScoped
public class ProveedorBean extends EJBBase {

    private List<ProveedorDTO> listProv;
    private LeerCSV leerCsv;

    @PostConstruct
    public void init() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        this.validarInicioSesion(ec);
        
        leerCsv = LeerCSV.getInstance();
        if (!leerCsv.isFileLoad()) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("Volver", "Proveedores.xhtml");
                FacesContext.getCurrentInstance().getExternalContext().redirect("CargaArchivo.xhtml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void loadProveedores() {
        listProv = new ArrayList<>();

        try {
            List<List<String>> proveedores = leerCsv.getData();
            proveedores = validarColumnas(proveedores);

            for (List<String> fila : proveedores) {
                ProveedorDTO provDTO = new ProveedorDTO(fila.get(0), fila.get(1), fila.get(2), fila.get(3), fila.get(4));
                listProv.add(provDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public List<List<String>> validarColumnas(List<List<String>> proveedores) {
        List<List<String>> resultado = Validaciones.eliminarColumna(proveedores, 4);

        return resultado;
    }

    public void guardar(ActionEvent actionEvent) {
        HibernateUtil.start();

        try {
            for (ProveedorDTO fila : listProv) {
                Proveedor proveedor = consultar(fila.getNit());
                if (proveedor == null) {
                    proveedor = new Proveedor();
                    proveedor.setNit(fila.getNit());
                    proveedor.setNombre(fila.getNombre());
                    proveedor.setDireccion(fila.getDireccion());
                    proveedor.setTelefono(fila.getTelefono());
                    proveedor.setDescripcion(fila.getDescripcion());
                    HibernateUtil.getSession().save(proveedor);
                }
            }

            HibernateUtil.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.close();
        }
    }

    private Proveedor consultar(String nombre) {
        String sql = "FROM Proveedor p WHERE p.nit = :nombrec";
        Query q = HibernateUtil.getSession().createQuery(sql).setParameter("nombrec", nombre);

        return (Proveedor) q.uniqueResult();
    }

    public List<ProveedorDTO> getListProv() {
        return listProv;
    }

    public void setListProv(List<ProveedorDTO> listProv) {
        this.listProv = listProv;
    }

}
