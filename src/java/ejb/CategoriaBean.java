/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import Conexion.HibernateUtil;
import entities.Categoria;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.hibernate.Query;
import org.hibernate.Session;
import utilidades.LeerCSV;

/**
 *
 * @author RA302
 */
@ManagedBean(name = "CategoriaBean")
@SessionScoped
public class CategoriaBean {

    private List<List<String>> listCat;
    private Session session;

    public void loadCategorias() {
        listCat = new ArrayList<>();
        LeerCSV leerCsv = LeerCSV.getInstance();

        try {
            List<List<String>> categorias = leerCsv.getData("D:/Categorias.csv");

            for (List<String> fila : categorias) {
                while (fila.size() <= 3) {
                    fila.add("");
                }

                listCat.add(fila);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void guardar() {
        HibernateUtil.start();
        session = HibernateUtil.getSession();
        
        for(List<String> fila : listCat){
            Categoria c = consultar(fila.get(1));
            if(c == null){
                c = new Categoria();
            }
            
        }
        
        HibernateUtil.commit();
    }

    private Categoria consultar(String nombre){
        String sql = "WHERE Categoria c WHERE c.nombre_categoria = :nombrec";
        Query q = session.createQuery(sql).setParameter("nombrec", nombre);
        
        return (Categoria) q.uniqueResult();
    }
    
    public List<List<String>> getListCat() {
        return listCat;
    }

    public void setListCat(List<List<String>> listCat) {
        this.listCat = listCat;
    }
}
