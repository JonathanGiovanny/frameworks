/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import Conexion.HibernateUtil;
import dtos.ProductoDTO;
import entities.Categoria;
import entities.Producto;
import java.awt.event.ActionEvent;
import java.sql.Date;
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
@ManagedBean(name = "ProductoBean")
@SessionScoped
public class ProductoBean {
    private List<ProductoDTO> listProd;
    private Session session;

    public void loadProductos() {
        listProd = new ArrayList<>();
        LeerCSV leerCsv = LeerCSV.getInstance();

        try {
            List<List<String>> productos = leerCsv.getData("D:/Productos.csv");
            //productos = validarColumnas(productos);

            for (int i = 1; i < productos.size(); i++) {
                List<String> fila = productos.get(i);
                ProductoDTO prodDTO = new ProductoDTO(fila.get(0), fila.get(1),fila.get(2),new Date(17, 12, 12), Integer.parseInt(fila.get(3)),Integer.parseInt(fila.get(5)), fila.get(4) );
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
        session = HibernateUtil.getSession();

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
                
                
                session.flush();
                session.save(p);
            }else{
                p.setStock(p.getStock()+ fila.getStock());
                session.update(p);
            }
        }

        HibernateUtil.commit();
    }

    private Producto consultar(String nombre) {
        String sql = "FROM Producto p WHERE p.nombre = :nombrec";
        Query q = session.createQuery(sql).setParameter("nombrec", nombre);

        return (Producto) q.uniqueResult();
    }
    
    private Categoria consultarCategoria(String nombre){
        String sql = "FROM Categoria c WHERE c.nombre_categoria = :nombrec";
        Query q = session.createQuery(sql).setParameter("nombrec", nombre);

        return (Categoria) q.uniqueResult();
    }

    public List<ProductoDTO> getListProd() {
        return listProd;
    }

    public void setListProd(List<ProductoDTO> listProd) {
        this.listProd = listProd;
    }
    
    

    
}
