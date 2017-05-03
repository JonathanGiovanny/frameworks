package ejb;

import Conexion.HibernateUtil;
import dtos.PersonaDTO;
import entities.Persona;
import entities.TipoPersona;
import java.awt.event.ActionEvent;
import java.sql.Date;
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
@ManagedBean(name = "PersonaBean")
@SessionScoped
public class PersonaBean {

    private List<PersonaDTO> listPer;

    public void loadPersonas() {
        listPer = new ArrayList<>();
        LeerCSV leerCsv = LeerCSV.getInstance();

        try {
            List<List<String>> personas = leerCsv.getData("D:/Personas.csv");
            personas = validarColumnas(personas);

            for (List<String> fila : personas) {
                PersonaDTO perDTO;
                if (fila.size() == 11) {
                    perDTO = new PersonaDTO(fila.get(0), fila.get(2), fila.get(3),
                            new Date(Validaciones.validarFechas(fila.get(8)).getTime()), fila.get(6), " ", fila.get(5),
                            validarGenero(fila.get(4)), validarTipoPersona(fila.get(7)), fila.get(9), fila.get(10));
                } else {
                    perDTO = new PersonaDTO(fila.get(0), fila.get(2), fila.get(3),
                            new Date(Validaciones.validarFechas(fila.get(9)).getTime()), fila.get(6), fila.get(7), fila.get(5),
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
        List<List<String>> resultado = Validaciones.eliminarColumna(categorias, 10);

        return resultado;
    }

    public void guardar(ActionEvent actionEvent) {
        HibernateUtil.start();

        try{
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
                
                HibernateUtil.getSession().save(p);
            }
        }

        HibernateUtil.commit();
        
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.close();
        }
    }

    private Persona consultar(String cedula) {
        String sql = "FROM Persona p WHERE p.cedula = :nombrec";
        Query q = HibernateUtil.getSession().createQuery(sql).setParameter("nombrec", Integer.parseInt(cedula));

        return (Persona) q.uniqueResult();
    }

    public List<PersonaDTO> getListPer() {
        return listPer;
    }

    public void setListPer(List<PersonaDTO> listPer) {
        this.listPer = listPer;
    }

}
