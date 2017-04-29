/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import Conexion.HibernateUtil;
import dtos.PersonaDTO;
import entities.Persona;
import entities.TipoPersona;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@ManagedBean(name = "PersonaBean")
@SessionScoped
public class PersonaBean {

    private List<PersonaDTO> listPer;
    private Session session;

    public void loadPersonas() {
        listPer = new ArrayList<>();
        LeerCSV leerCsv = LeerCSV.getInstance();

        try {
            List<List<String>> personas = leerCsv.getData("D:/Personas.csv");
            personas = validarColumnas(personas);

            for (int i = 1; i < personas.size(); i++) {
                List<String> fila = personas.get(i);
                for(int j = 1; j < fila.size(); j++){
                    System.out.println(fila.get(j));
                }
                
                PersonaDTO perDTO;
                if(fila.size()==11){
                    perDTO = new PersonaDTO(fila.get(0), fila.get(2), fila.get(3), 
                        formatearFecha(fila.get(8)), fila.get(6), " ", fila.get(5), 
                        validarGenero(fila.get(4)), validarTipoPersona(fila.get(7)), fila.get(9), fila.get(10));
                }else{
                    perDTO = new PersonaDTO(fila.get(0), fila.get(2), fila.get(3), 
                        formatearFecha(fila.get(9)), fila.get(6), fila.get(7), fila.get(5), 
                        validarGenero(fila.get(4)), validarTipoPersona(fila.get(8)), fila.get(10), fila.get(11));
                }
                    
                
                listPer.add(perDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public String validarGenero(String genero) {
        if (genero.equalsIgnoreCase("FEMENINO")) {
            return "F";
        } else {
            return "M";
        }
    }

    public TipoPersona validarTipoPersona(String tipo) {
        if (tipo.equalsIgnoreCase("ADMINISTRADOR")) {
            return TipoPersona.AD;
        } else if (tipo.equalsIgnoreCase("CAJERO")) {
            return TipoPersona.CJ;
        } else {
            return TipoPersona.CC;
        }
    }

    public List<List<String>> validarColumnas(List<List<String>> categorias) {
        List<List<String>> resultado = Validaciones.eliminarColumna(categorias, 9);
        
        return resultado;
    }

    public String validarFecha(String fecha) {
        SimpleDateFormat parseador = new SimpleDateFormat("dd-MM-yy");
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = (Date) parseador.parse(fecha);
        } catch (ParseException ex) {
            Logger.getLogger(PersonaBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return formateador.format(d);
    }

    public Date formatearFecha(String fecha) {
        String aux = fecha;
        if (fecha.contains("/")) {
            String[] fields = fecha.split("/");
            aux = fields[2] + "-" + fields[1] + "-" + fields[0];
        }
        if (fecha.length() <= 8) {
            aux = validarFecha(fecha);
        }
        String[] fields = aux.split("-");

        return new Date(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), Integer.parseInt(fields[2]));
    }

    public void guardar(ActionEvent actionEvent) {
        HibernateUtil.start();
        session = HibernateUtil.getSession();

        for (PersonaDTO fila : listPer) {
            Persona p = consultar(fila.getCedula());
            if (p == null) {
                p = new Persona();
                p.setCedula(Integer.parseInt(fila.getCedula()));
                p.setNombre(fila.getNombre());
                p.setApellido(fila.getApellido());
                p.setFecha_nacimiento(fila.getFecha_nacimiento());
                p.setTelefono(fila.getTelefono());
                p.setDireccion(fila.getDireccion());
                p.setCorreo(fila.getCorreo());
                p.setSexo(fila.getSexo());
                p.setTipo_persona(fila.getTipo_persona());
                p.setUsuario(fila.getUsuario());
                p.setContrasenia(fila.getContrasenia());
                session.flush();
                session.save(p);
            }
        }

        HibernateUtil.commit();
    }

    private Persona consultar(String nombre) {
        String sql = "FROM Persona p WHERE p.cedula = :nombrec";
        Query q = session.createQuery(sql).setParameter("nombrec", nombre);

        return (Persona) q.uniqueResult();
    }

    public List<PersonaDTO> getListPer() {
        return listPer;
    }

    public void setListPer(List<PersonaDTO> listPer) {
        this.listPer = listPer;
    }
    
    

}
