/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import Conexion.HibernateUtil;
import dtos.FacturaDTO;
import entities.Factura;
import entities.Persona;
import entities.Proveedor;
import entities.Sucursal;
import entities.TipoFactura;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import org.hibernate.Query;
import org.hibernate.Session;
import utilidades.LeerCSV;
import utilidades.Validaciones;

/**
 *
 * @author ASUS1
 */
@ManagedBean(name = "FacturaBean")
public class FacturaBean {

    private List<FacturaDTO> listFac;
    private Session session;

    public void loadFacturas() {
        listFac = new ArrayList<>();
        LeerCSV leerCsv = LeerCSV.getInstance();

        try {
            List<List<String>> facturas = leerCsv.getData("D:/FacturasItem.csv");
            validarColumnas(facturas);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void validarColumnas(List<List<String>> facturas) {
        Validaciones.eliminarColumna(facturas, 5);
        for (List<String> fila : facturas) {
            FacturaDTO fac = new FacturaDTO();

            fac.setNombreSucursal(fila.get(0));
            try {
                fac.setIdFactura(Integer.parseInt(fila.get(1)));
            } catch (NumberFormatException e) {
                System.out.println("Id factura no es numerico, se deja enblanco para autogenerar");
            }
            fac.setFechaFactura(new java.sql.Date(Validaciones.validarFechas(fila.get(2)).getTime()));
            String tipoFac = fila.get(3) != null && fila.get(3).charAt(0) == 'C' ? "FC" : "FV";
            fac.setTipoFactura(tipoFac);
            if ("FC".equals(tipoFac)) {
                fac.setNombreProveedor(fila.get(4));
            } else {
                fac.setIdCliente(fila.get(4));
            }
            fac.setIdCajero(fila.get(5));

            listFac.add(fac);
        }
    }

    public void guardar(ActionEvent actionEvent) {
        HibernateUtil.start();
        session = HibernateUtil.getSession();

        for (FacturaDTO fila : listFac) {
            Factura f = consultarFactura(fila.getIdFactura());
           
            if (f == null) {
                f = new Factura();
                f.setId_factura(fila.getIdFactura());
                TipoFactura tipoFac = fila.getTipoFactura() != null ? TipoFactura.valueOf(fila.getTipoFactura()) : TipoFactura.FC;
                if (tipoFac == TipoFactura.FC) {
                    f.setId_proveedor(consultarProveedor(fila.getNombreProveedor()));
                }
                f.setFecha_compra(fila.getFechaFactura());
                f.setTipo_factura(tipoFac);
                f.setId_cajero(consultarPersona(fila.getIdCajero()));
                if (tipoFac == TipoFactura.FV) {
                    f.setId_cliente(consultarPersonaNombre(fila.getNombreProveedor()));
                }
                f.setId_sucursal(consultarSucursal(fila.getNombreSucursal()));

                session.flush();
                session.save(f);
            }
        }

        HibernateUtil.commit();
    }

    private Factura consultarFactura(Integer numeroFac) {
        String sql = "FROM Factura f WHERE f.id_factura = :numero";
        Query q = session.createQuery(sql).setParameter("numero", numeroFac);

        return (Factura) q.uniqueResult();
    }

    private Persona consultarPersona(String usuario) {
        String sql = "FROM Persona p WHERE p.usuario = :nombrep";
        Query q = session.createQuery(sql).setParameter("nombrep", usuario);

        return (Persona) q.list().get(0);
    }

    private Persona consultarPersonaNombre(String nombre) {
        String sql = "FROM Persona p WHERE p.nombre = :nombrep";
        Query q = session.createQuery(sql).setParameter("nombrep", nombre);

        return (Persona) q.list().get(0);
    }

    private Sucursal consultarSucursal(String nombre) {
        String sql = "FROM Sucursal s WHERE s.nombre_sucursal = :nombres";
        Query q = session.createQuery(sql).setParameter("nombres", nombre);

        return (Sucursal) q.list().get(0);
    }

    private Proveedor consultarProveedor(String nombre) {
        String sql = "FROM Proveedor p WHERE p.nombre = :nombrep";
        Query q = session.createQuery(sql).setParameter("nombrep", nombre);

        return (Proveedor) q.list().get(0);
    }

    public List<FacturaDTO> getListFac() {
        return listFac;
    }

    public void setListFac(List<FacturaDTO> listFac) {
        this.listFac = listFac;
    }
}
