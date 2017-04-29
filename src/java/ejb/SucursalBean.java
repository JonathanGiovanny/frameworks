/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import Conexion.HibernateUtil;
import dtos.SucursalDTO;
import entities.Lugar;
import entities.Sucursal;
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
 * @author JulioCesar
 */
@ManagedBean(name = "SucursalBean")
@SessionScoped
public class SucursalBean {
    
     private List<SucursalDTO> listSuc;
    private Session session;

    public void loadSucursales() {
        listSuc = new ArrayList<>();
        LeerCSV leerCsv = LeerCSV.getInstance();

        try {
            List<List<String>> sucursales = leerCsv.getData("D:/Sucursales.csv");
            sucursales = validarColumnas(sucursales);

            for (int i = 1; i < sucursales.size(); i++) {
                List<String> fila = sucursales.get(i);
                SucursalDTO sucDTO = new SucursalDTO(fila.get(0), fila.get(1), fila.get(2));
                listSuc.add(sucDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public List<List<String>> validarColumnas(List<List<String>> categorias) {
        List<List<String>> resultado = Validaciones.eliminarColumna(categorias, 0);
        resultado = Validaciones.eliminarColumna(resultado, 1);
        resultado = Validaciones.eliminarColumna(resultado, 3);
        resultado = Validaciones.eliminarColumna(resultado, 3);
        return resultado;
    }

    public void guardar(ActionEvent actionEvent) {
        HibernateUtil.start();
        session = HibernateUtil.getSession();

        for (SucursalDTO fila : listSuc) {
            Sucursal s = consultar(fila.getNombre_sucursal());
            if (s == null) {
                s = new Sucursal();
                s.setNombre_sucursal(fila.getNombre_sucursal());
                s.setId_lugar(consultarLugar(fila.getId_lugar()));
                s.setDireccion(fila.getDireccion());
                session.flush();
                session.save(s);
            }
        }
        HibernateUtil.commit();
    }

    private Sucursal consultar(String nombre) {
        String sql = "FROM Sucursal s WHERE s.nombre_sucursal = :nombrec";
        Query q = session.createQuery(sql).setParameter("nombrec", nombre);

        return (Sucursal) q.uniqueResult();
    }
    
    private Lugar consultarLugar(String nombre) {
        String sql = "FROM Lugar l WHERE l.nombre_lugar = :nombrec";
        Query q = session.createQuery(sql).setParameter("nombrec", nombre);

        return (Lugar) q.uniqueResult();
    }

    public List<SucursalDTO> getListSuc() {
        return listSuc;
    }

    public void setListSuc(List<SucursalDTO> listSuc) {
        this.listSuc = listSuc;
    }
    
    
    
}
