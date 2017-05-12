/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import Conexion.HibernateUtil;
import dtos.FacturaDTO;
import ejb.util.EJBBase;
import entities.Factura;
import entities.Item;
import entities.ItemPk;
import entities.Persona;
import entities.Proveedor;
import entities.Producto;
import entities.Sucursal;
import entities.TipoFactura;
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
@ManagedBean(name = "FacturaBean")
@SessionScoped
public class FacturaBean extends EJBBase {

    private List<FacturaDTO> listFac;
    private LeerCSV leerCsv;

    @PostConstruct
    public void init() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        this.validarInicioSesion(ec);
        
        leerCsv = LeerCSV.getInstance();
        if (!leerCsv.isFileLoad()) {
            try {
                ec.getSessionMap().put("Volver", "Facturas.xhtml");
                ec.redirect("CargaArchivo.xhtml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void loadFacturas() {
        listFac = new ArrayList<>();

        try {
            List<List<String>> facturas = leerCsv.getData();
            validarColumnas(facturas);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void validarColumnas(List<List<String>> facturas) {
        Validaciones.eliminarColumna(facturas, 6);

        for (List<String> fila : facturas) {
            FacturaDTO fac = new FacturaDTO();

            fac.setNombreSucursal(fila.get(0));
            try {
                fac.setIdFactura(Integer.parseInt(fila.get(1)));
            } catch (NumberFormatException e) {
                System.out.println("Id factura no es numerico, se deja enblanco para autogenerar");
            }
            fac.setFechaFactura(new Date(Validaciones.validarFechas(fila.get(2)).getTime()));
            String tipoFac = fila.get(3) != null && fila.get(3).charAt(0) == 'C' ? "FC" : "FV";
            fac.setTipoFactura(tipoFac);
            if ("FC".equals(tipoFac)) {
                fac.setNombreProveedor(fila.get(4));
            } else {
                fac.setIdCliente(fila.get(4));
            }
            fac.setIdCajero(fila.get(5));
            //Item
            fac.setNombreProducto(fila.get(6));
            fac.setFechaVencimiento(new Date(Validaciones.validarFechas(fila.get(7)).getTime()));
            try {
                fac.setValorUnitario(Double.parseDouble(fila.get(8).replace("$", "").replace(",", ".").trim()));
            } catch (NumberFormatException e) {
                System.out.println("El vampo viene con caracteres no númericos");
            }
            try {
                fac.setCantidad(Integer.parseInt(fila.get(9).trim()));
            } catch (NumberFormatException e) {
                System.out.println("El vampo viene con caracteres no númericos");
            }

            listFac.add(fac);
        }
    }

    public void guardar(ActionEvent actionEvent) {
        HibernateUtil.start();

        try {
            for (FacturaDTO fila : listFac) {
                Factura f = consultarFactura(fila.getIdFactura());

                if (f == null) {
                    f = new Factura();
                    f.setNumero_factura(fila.getIdFactura());
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

                    HibernateUtil.getSession().save(f);
                }
            }

            HibernateUtil.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.close();
        }

        insertarItems();
    }

    private void insertarItems() {
        HibernateUtil.start();

        try {
            for (FacturaDTO fila : listFac) {
                ItemPk ipk = new ItemPk();
                Factura fac = consultarFactura(fila.getIdFactura());
                ipk.setId_factura(fac);
                Producto prod = consultarProducto(fila.getNombreProducto());
                ipk.setId_producto(prod);

                Item item = new Item();
                item.setItemPk(ipk);
                item.setPrecio(fila.getValorUnitario());
                item.setCantidad(fila.getCantidad());
                prod.setStock(prod.getStock() - fila.getCantidad());

                HibernateUtil.getSession().save(item);
                HibernateUtil.getSession().update(prod);

            }
            HibernateUtil.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.close();
        }
    }

    private Factura consultarFactura(Integer numeroFac) {
        String sql = "FROM Factura f WHERE f.numero_factura = :numero";
        Query q = HibernateUtil.getSession().createQuery(sql).setParameter("numero", numeroFac);

        return (Factura) q.uniqueResult();
    }

    private Persona consultarPersona(String usuario) {
        String sql = "FROM Persona p WHERE p.usuario = :nombrep";
        Query q = HibernateUtil.getSession().createQuery(sql).setParameter("nombrep", usuario);

        return (Persona) q.uniqueResult();
    }

    private Persona consultarPersonaNombre(String nombre) {
        String sql = "FROM Persona p WHERE p.nombre = :nombrep";
        Query q = HibernateUtil.getSession().createQuery(sql).setParameter("nombrep", nombre);

        return (Persona) q.uniqueResult();
    }

    private Sucursal consultarSucursal(String nombre) {
        String sql = "FROM Sucursal s WHERE s.nombre_sucursal = :nombres";
        Query q = HibernateUtil.getSession().createQuery(sql).setParameter("nombres", nombre);

        return (Sucursal) q.uniqueResult();
    }

    private Proveedor consultarProveedor(String nombre) {
        String sql = "FROM Proveedor p WHERE p.nombre = :nombrep";
        Query q = HibernateUtil.getSession().createQuery(sql).setParameter("nombrep", nombre);

        return (Proveedor) q.uniqueResult();
    }

    private Producto consultarProducto(String nombre) {
        String sql = "FROM Producto p WHERE p.nombre = :nombrep";
        Query q = HibernateUtil.getSession().createQuery(sql).setParameter("nombrep", nombre);

        return (Producto) q.uniqueResult();
    }

    public List<FacturaDTO> getListFac() {
        return listFac;
    }

    public void setListFac(List<FacturaDTO> listFac) {
        this.listFac = listFac;
    }
}
