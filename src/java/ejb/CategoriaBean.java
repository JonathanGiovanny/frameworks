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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.hibernate.Query;
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

    public void loadCategorias() {
        listCat = new ArrayList<>();
        LeerCSV leerCsv = LeerCSV.getInstance();

        try {
            List<List<String>> categorias = leerCsv.getData();
            categorias = validarColumnas(categorias);

            for (List<String> fila : categorias) {
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

        try {
            for (CategoriaDTO fila : listCat) {
                Categoria c = consultar(fila.getCatNombre());
                if (c == null) {
                    c = new Categoria();
                    c.setNombre_categoria(fila.getCatNombre());
                    if (fila.getCatPadre() != null) {
                        c.setId_categoriapadre(consultar(fila.getCatPadre()));
                    }
                    
                    HibernateUtil.getSession().save(c);
                }
            }

            HibernateUtil.commit();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            HibernateUtil.close();
        }
    }

    private Categoria consultar(String nombre) {
        String sql = "FROM Categoria c WHERE c.nombre_categoria = :nombrec";
        Query q = HibernateUtil.getSession().createQuery(sql).setParameter("nombrec", nombre);

        return (Categoria) q.uniqueResult();
    }

    public List<CategoriaDTO> getListCat() {
        return listCat;
    }

    public void setListCat(List<CategoriaDTO> listCat) {
        this.listCat = listCat;
    }
}
