package ejb.util;

import Conexion.HibernateUtil;
import com.mysql.jdbc.Connection;
import java.sql.ResultSetMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author ASUS1
 */
@ManagedBean(name = "Consultas")
@SessionScoped
public class Consultas {

    private String consulta;
    private List<List<String>> tabla;
    private List<String> nomColumnas;

    @PostConstruct
    public void init() {
        tabla = new ArrayList<>();
        nomColumnas = new ArrayList<>();
    }

    public void consultar() {
        HibernateUtil.start();
        String sql = "SELECT P.NOMBRE, C.NOMBRE_CATEGORIA FROM PRODUCTOS P, CATEGORIAS C"
                + " WHERE P.ID_CATEGORIA = C.ID_CATEGORIA";

        Connection conn = null;

        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/supermercado?user=root&password=root");

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsm = rs.getMetaData();
            System.out.println("Columnas: " + rsm.getColumnCount());

            while (rs.next()) {
                List<String> fila = new ArrayList<>();

                for (int i = 1; i <= rsm.getColumnCount(); i++) {
                    fila = new ArrayList<>();
                    fila.add(rs.getString(i));
                    if (nomColumnas.size() <= rsm.getColumnCount()) {
                        nomColumnas.add(rsm.getColumnName(i));
                    }
                }

                tabla.add(fila);
            }

            invertirFilasColumnas();

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
    }

    public void invertirFilasColumnas() {
        List<List<String>> listaCols = new ArrayList<>();

        for (int i = 0; i < tabla.size(); i++) {
            listaCols.add(getColumna(tabla, i));
        }

        this.tabla = listaCols;
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
