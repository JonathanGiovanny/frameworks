/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import Conexion.HibernateUtil;
import dtos.ProductoDTO;
import ejb.util.EJBBase;
import entities.Categoria;
import entities.Producto;
import java.awt.event.ActionEvent;
import java.sql.Date;
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
@ManagedBean(name = "ProductoBean")
@SessionScoped
public class ProductoBean extends EJBBase {

    private List<ProductoDTO> listProd;
    private LeerCSV leerCsv;

    @PostConstruct
    public void init() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        this.validarInicioSesion(ec);
        
        leerCsv = LeerCSV.getInstance();
        if (!leerCsv.isFileLoad()) {
            try {
                ec.getSessionMap().put("Volver", "Productos.xhtml");
                ec.redirect("CargaArchivo.xhtml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void loadProductos() {
        listProd = new ArrayList<>();

        try {
            List<List<String>> productos = leerCsv.getData();

            for (List<String> fila : productos) {
                ProductoDTO prodDTO = new ProductoDTO(fila.get(0), fila.get(1), fila.get(2), new Date(17, 12, 12), Integer.parseInt(fila.get(3)), Integer.parseInt(fila.get(5)), fila.get(4));
                listProd.add(prodDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public List<List<String>> validarColumnas(List<List<String>> categorias) {
        List<List<String>> resultado = Validaciones.eliminarColumna(categorias, 6);

        return resultado;
    }

    public void guardar(ActionEvent actionEvent) {
        HibernateUtil.start();

        try {
            for (ProductoDTO fila : listProd) {
                Producto p = consultar(fila.getNombre());

                if (p == null) {
                    p = new Producto();
                    p.setNombre(fila.getNombre());
                    p.setId_categoria(consultarCategoria(fila.getId_categoria()));
                    p.setUnidad_medida(fila.getUnidad_medida());
                    p.setFecha_vencimiento(fila.getFecha_vencimiento());
                    p.setStock(fila.getStock());
                    p.setCantidad_minima(fila.getCantidad_minima());
                    p.setMarca(fila.getMarca());

                    HibernateUtil.getSession().save(p);

                } else {
                    p.setStock(p.getStock() + fila.getStock());
                    HibernateUtil.getSession().update(p);
                }
            }

            HibernateUtil.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.close();
        }
    }

    private Producto consultar(String nombre) {
        String sql = "FROM Producto p WHERE p.nombre = :nombrec";
        Query q = HibernateUtil.getSession().createQuery(sql).setParameter("nombrec", nombre);

        return (Producto) q.uniqueResult();
    }

    private Categoria consultarCategoria(String nombre) {
        String sql = "FROM Categoria c WHERE c.nombre_categoria = :nombrec";
        Query q = HibernateUtil.getSession().createQuery(sql).setParameter("nombrec", nombre);

        Categoria c;
        try {
            c = (Categoria) q.uniqueResult();

        } catch (Exception e) {
            List<Categoria> lista = q.list();
            c = lista.get(0);
        }

        return c;
    }

    public List<ProductoDTO> getListProd() {
        return listProd;
    }

    public void setListProd(List<ProductoDTO> listProd) {
        this.listProd = listProd;
    }

}
