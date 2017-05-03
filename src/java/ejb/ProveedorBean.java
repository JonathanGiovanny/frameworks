/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import Conexion.HibernateUtil;
import dtos.ProveedorDTO;
import entities.Proveedor;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.hibernate.Query;
import org.hibernate.Session;
import utilidades.LeerCSV;
import utilidades.Validaciones;

/**
 *
 * @author ASUS1
 */
@ManagedBean(name = "ProveedorBean")
@SessionScoped
public class ProveedorBean {

    private List<ProveedorDTO> listProv;
    private Session session;

    public void loadProveedores() {
        listProv = new ArrayList<>();
        LeerCSV leerCsv = LeerCSV.getInstance();

        try {
            List<List<String>> proveedores = leerCsv.getData("D:/Proveedores.csv");
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
        session = HibernateUtil.getSession();

        for (ProveedorDTO fila : listProv) {
            Proveedor proveedor = consultar(fila.getNit());
            if (proveedor == null) {
                proveedor = new Proveedor();
                proveedor.setNit(fila.getNit());
                proveedor.setNombre(fila.getNombre());
                proveedor.setDireccion(fila.getDireccion());
                proveedor.setTelefono(fila.getTelefono());
                proveedor.setDescripcion(fila.getDescripcion());
                session.flush();
                session.save(proveedor);
            }
        }

        HibernateUtil.commit();
    }

    private Proveedor consultar(String nombre) {
        String sql = "FROM Proveedor p WHERE p.nit = :nombrec";
        Query q = session.createQuery(sql).setParameter("nombrec", nombre);

        return (Proveedor) q.uniqueResult();
    }

    public List<ProveedorDTO> getListProv() {
        return listProv;
    }

    public void setListProv(List<ProveedorDTO> listProv) {
        this.listProv = listProv;
    }

}
