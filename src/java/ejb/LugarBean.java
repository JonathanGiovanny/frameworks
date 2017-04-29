/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import org.hibernate.Session;
import utilidades.LeerCSV;
import utilidades.Validaciones;

/**
 *
 * @author JulioCesar
 */
@ManagedBean(name = "LugarBean")
@SessionScoped
public class LugarBean {
    private List<LugarDTO> listLug;
    private Session session;
    
    
    public void loadLugares() {
        listLug = new ArrayList<>();
        LeerCSV leerCsv = LeerCSV.getInstance();

        try {
            List<List<String>> lugares = leerCsv.getData("D:/Lugares.csv");
            lugares = validarColumnas(lugares);

            for (int i = 1; i < lugares.size(); i++) {
                List<String> fila = lugares.get(i);
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
        session = HibernateUtil.getSession();

        for (LugarDTO fila : listLug) {
            Lugar l = consultar(fila.getNombre_lugar(), fila.getTipo_lugar());
            if (l == null) {
                l = new Lugar();
                l.setNombre_lugar(fila.getNombre_lugar());
                if(fila.getTipo_lugar().equalsIgnoreCase("CIUDAD")){
                     l.setTipo_lugar(TipoLugar.C);
                     validarLugar(fila.getId_ubicacion(), TipoLugar.D);
                }else if(fila.getTipo_lugar().equalsIgnoreCase("DEPARTAMENTO")) {
                     l.setTipo_lugar(TipoLugar.D);
                     validarLugar(fila.getId_ubicacion(), TipoLugar.P);
                }else{
                     l.setTipo_lugar(TipoLugar.P);
                }
                
                if (fila.getId_ubicacion() != null) {
                    l.setId_ubicacion(consultar(fila.getId_ubicacion(), fila.getTipo_lugar()));
                }
                session.flush();
                session.save(l);
            }
        }

        HibernateUtil.commit();
    }
    
    public void validarLugar(String l, TipoLugar tipo){
        if(consultar(l, tipo.toString())== null){
            Lugar lug = new Lugar();
            lug.setNombre_lugar(l);
            lug.setTipo_lugar(tipo);
            session.save(lug);
        }
    }
            

    private Lugar consultar(String nombre, String tipo) {
        String sql = "SELECT distinct l FROM Lugar l WHERE l.nombre_lugar = :nombrec ";
        Query q = session.createQuery(sql).setParameter("nombrec", nombre);
        List<Lugar> l= q.list();
        for (int i = 0; i < l.size(); i++) {
            System.out.println(l.get(i).getNombre_lugar());
        }
        System.out.println(q.toString());
        return (Lugar) q.uniqueResult();
    }

    public List<LugarDTO> getListLug() {
        return listLug;
    }

    public void setListLug(List<LugarDTO> listLug) {
        this.listLug = listLug;
    }
    
    
}
