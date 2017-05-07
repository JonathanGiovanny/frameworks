package ejb;

import Conexion.HibernateUtil;
import dtos.SucursalDTO;
import entities.Lugar;
import entities.Sucursal;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Query;
import utilidades.LeerCSV;
import utilidades.Validaciones;

/**
 *
 * @author ASUS1
 */
@ManagedBean(name = "SucursalBean")
@SessionScoped
public class SucursalBean {

    private List<SucursalDTO> listSuc;
    private LeerCSV leerCsv;

    @PostConstruct
    public void init() {
        leerCsv = LeerCSV.getInstance();
        if (!leerCsv.isFileLoad()) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("Volver", "Sucursales.xhtml");
                FacesContext.getCurrentInstance().getExternalContext().redirect("CargaArchivo.xhtml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void loadSucursales() {
        listSuc = new ArrayList<>();

        try {
            List<List<String>> sucursales = leerCsv.getData();
            sucursales = validarColumnas(sucursales);

            for (List<String> fila : sucursales) {
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

        try {
            for (SucursalDTO fila : listSuc) {
                Sucursal s = consultar(fila.getNombre_sucursal());
                if (s == null) {
                    s = new Sucursal();
                    s.setNombre_sucursal(fila.getNombre_sucursal());
                    s.setId_lugar(consultarLugar(fila.getId_lugar()));
                    s.setDireccion(fila.getDireccion());
                    HibernateUtil.getSession().save(s);
                }
            }

            HibernateUtil.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.close();
        }
    }

    private Sucursal consultar(String nombre) {
        String sql = "FROM Sucursal s WHERE s.nombre_sucursal = :nombrec";
        Query q = HibernateUtil.getSession().createQuery(sql).setParameter("nombrec", nombre);

        return (Sucursal) q.uniqueResult();
    }

    private Lugar consultarLugar(String nombre) {
        String sql = "FROM Lugar l WHERE l.nombre_lugar = :nombrec";
        Query q = HibernateUtil.getSession().createQuery(sql).setParameter("nombrec", nombre);
        Lugar l;

        try {
            l = (Lugar) q.uniqueResult();

        } catch (Exception e) {
            List<Lugar> lista = q.list();
            l = lista.get(0);
        }

        return l;
    }

    public List<SucursalDTO> getListSuc() {
        return listSuc;
    }

    public void setListSuc(List<SucursalDTO> listSuc) {
        this.listSuc = listSuc;
    }

}
