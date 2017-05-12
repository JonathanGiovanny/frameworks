package ejb.util;

import Conexion.HibernateUtil;
import com.mysql.jdbc.Connection;
import java.awt.event.ActionEvent;
import java.sql.ResultSetMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author ASUS1
 */
@ManagedBean(name = "ConsultasBean")
@SessionScoped
public class ConsultasBean extends EJBBase {

    private String consulta;
    private List<List<String>> tabla;
    private List<String> nomColumnas;

    @PostConstruct
    public void init() {
        this.validarInicioSesion(FacesContext.getCurrentInstance().getExternalContext());
        if (tabla == null || nomColumnas == null) {
            tabla = new ArrayList<>();
            nomColumnas = new ArrayList<>();
        }
    }

    public void consultar(ActionEvent actionEvent) {
        HibernateUtil.start();

        Connection conn = null;

        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/supermercado?user=root&password=root");

            ps = conn.prepareStatement(this.consulta);
            rs = ps.executeQuery();

            ResultSetMetaData rsm = rs.getMetaData();

            while (rs.next()) {
                List<String> fila = new ArrayList<>();

                for (int i = 1; i <= rsm.getColumnCount(); i++) {
                    fila.add(rs.getString(i));

                    if (nomColumnas.size() < rsm.getColumnCount()) {
                        nomColumnas.add(rsm.getColumnName(i));
                    }
                }

                tabla.add(fila);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.close();
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("ResultadoConsulta.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void regresar(ActionEvent actionEvent) {
        consulta = "";
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("Consultar.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getColumna(List<List<String>> filas, int pos) {
        List<String> columna = new ArrayList<>();

        for (List<String> fila : filas) {
            columna.add(fila.get(pos));
        }

        return columna;
    }

    public String getConsulta() {
        return consulta;
    }

    public void setConsulta(String consulta) {
        this.consulta = consulta;
    }

    public List<List<String>> getTabla() {
        return tabla;
    }

    public void setTabla(List<List<String>> tabla) {
        this.tabla = tabla;
    }

    public List<String> getNomColumnas() {
        return nomColumnas;
    }

    public void setNomColumnas(List<String> nomColumnas) {
        this.nomColumnas = nomColumnas;
    }
}
