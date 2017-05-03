package ejb;

import Conexion.HibernateUtil;
import dtos.LugarDTO;
import entities.Lugar;
import entities.TipoLugar;
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
 * @author ASUS1
 */
@ManagedBean(name = "LugarBean")
@SessionScoped
public class LugarBean {

    private List<LugarDTO> listLug;

    public void loadLugares() {
        listLug = new ArrayList<>();
        LeerCSV leerCsv = LeerCSV.getInstance();

        try {
            List<List<String>> lugares = leerCsv.getData("D:/Lugares.csv");
            lugares = validarColumnas(lugares);

            for (List<String> fila : lugares) {
                LugarDTO lugDTO = new LugarDTO(fila.get(1), fila.get(0), fila.get(2));
                listLug.add(lugDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public List<List<String>> validarColumnas(List<List<String>> lugares) {
        List<List<String>> resultado = Validaciones.eliminarColumna(lugares, 3);
        return resultado;
    }

    public void guardar(ActionEvent actionEvent) {
        HibernateUtil.start();

        try {
            for (LugarDTO fila : listLug) {
                boolean aux = consultar(fila.getNombre_lugar(), fila.getId_ubicacion());

                if (!aux) {
                    TipoLugar tipoPadre = fila.getTipo_lugar().equalsIgnoreCase("CIUDAD") ? TipoLugar.D
                            : fila.getTipo_lugar().equalsIgnoreCase("DEPARTAMENTO") ? TipoLugar.P : null;
                    Lugar l = new Lugar();
                    l.setNombre_lugar(fila.getNombre_lugar());
                    if (fila.getTipo_lugar().equalsIgnoreCase("CIUDAD")) {
                        l.setTipo_lugar(TipoLugar.C);
                        validarLugar(fila.getId_ubicacion(), TipoLugar.D);
                    } else if (fila.getTipo_lugar().equalsIgnoreCase("DEPARTAMENTO")) {
                        l.setTipo_lugar(TipoLugar.D);
                        validarLugar(fila.getId_ubicacion(), TipoLugar.P);
                    } else {
                        l.setTipo_lugar(TipoLugar.P);
                    }

                    if (fila.getId_ubicacion() != null) {
                        l.setId_ubicacion(extraerPadre(fila.getId_ubicacion(), tipoPadre));
                    }

                    HibernateUtil.getSession().save(l);
                }
            }

            HibernateUtil.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.close();
        }
    }

    public void validarLugar(String l, TipoLugar tipo) {
        if (!consultarPadre(l)) {
            Lugar lug = new Lugar();
            lug.setNombre_lugar(l);
            lug.setTipo_lugar(tipo);
            HibernateUtil.getSession().save(lug);
        }
    }

    private boolean consultar(String nombre, String ubicacion) {
        String sql = "FROM Lugar l WHERE l.nombre_lugar = :nombrec ";
        Query q = HibernateUtil.getSession().createQuery(sql).setParameter("nombrec", nombre);
        List<Lugar> l = q.list();
        boolean estado = false;
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i).getId_ubicacion() != null) {
                if (l.get(i).getId_ubicacion().getNombre_lugar().equalsIgnoreCase(ubicacion)) {
                    estado = true;
                }
            }
        }

        return estado;
    }

    public boolean consultarPadre(String nombre) {
        String sql = "FROM Lugar l WHERE l.nombre_lugar = :nombrec ";
        Query q = HibernateUtil.getSession().createQuery(sql).setParameter("nombrec", nombre);
        List<Lugar> l = q.list();
        boolean estado = false;
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i).getNombre_lugar().equalsIgnoreCase(nombre)) {
                estado = true;
            }
        }
        return estado;
    }

    public Lugar extraerPadre(String nombre, TipoLugar tipo) {
        String sql = "FROM Lugar l WHERE l.nombre_lugar = :nombrec AND l.tipo_lugar = :tipo ";
        Query q = HibernateUtil.getSession().createQuery(sql).setParameter("nombrec", nombre).setParameter("tipo", tipo);
        Lugar l;
        try {
            l = (Lugar) q.uniqueResult();
        } catch (Exception e) {
            List<Lugar> lista = q.list();
            l = lista.get(0);
        }
        return l;
    }

    public List<LugarDTO> getListLug() {
        return listLug;
    }

    public void setListLug(List<LugarDTO> listLug) {
        this.listLug = listLug;
    }

}
