/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import Conexion.HibernateUtil;
import dtos.CategoriaDTO;
import entities.Categoria;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.primefaces.event.RowEditEvent;
import utilidades.LeerCSV;
import utilidades.Validaciones;

/**
 *
 * @author RA302
 */
@ManagedBean(name = "CategoriaBean")
@SessionScoped
public class CategoriaBean {

    private List<CategoriaDTO> listCat;
    private Session session;

    public void loadCategorias() {
        listCat = new ArrayList<>();
        LeerCSV leerCsv = LeerCSV.getInstance();

        try {
            List<List<String>> categorias = leerCsv.getData("D:/Categorias.csv");
            categorias = validarColumnas(categorias);

            for (int i = 1; i < categorias.size(); i++) {
                List<String> fila = categorias.get(i);
                CategoriaDTO catDTO = new CategoriaDTO(fila.get(1), fila.get(0));
                listCat.add(catDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public List<List<String>> validarColumnas(List<List<String>> categorias) {
        List<List<String>> resultado = Validaciones.eliminarColumna(categorias, 2);
        resultado = Validaciones.eliminarColumna(resultado, 2);

        return resultado;
    }

    public void guardar(ActionEvent actionEvent) {
        HibernateUtil.start();
        session = HibernateUtil.getSession();

        for (CategoriaDTO fila : listCat) {
            Categoria c = consultar(fila.getCatNombre());
            if (c == null) {
                c = new Categoria();
                c.setNombre_categoria(fila.getCatNombre());
                if (fila.getCatPadre() != null) {
                    c.setId_categoriapadre(consultar(fila.getCatPadre()));
                }
                session.flush();
                session.save(c);
            }
        }

        HibernateUtil.commit();
    }

    private Categoria consultar(String nombre) {
        String sql = "FROM Categoria c WHERE c.nombre_categoria = :nombrec";
        Query q = session.createQuery(sql).setParameter("nombrec", nombre);

        return (Categoria) q.uniqueResult();
    }

    public List<CategoriaDTO> getListCat() {
        return listCat;
    }

    public void setListCat(List<CategoriaDTO> listCat) {
        this.listCat = listCat;
    }
}
