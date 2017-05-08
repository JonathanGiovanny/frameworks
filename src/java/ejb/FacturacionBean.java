/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import Conexion.HibernateUtil;
import dtos.ItemsFacturacionDTO;
import entities.Factura;
import entities.Item;
import entities.ItemPk;
import entities.Persona;
import entities.Producto;
import entities.Sucursal;
import entities.TipoFactura;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.hibernate.Query;
import org.hibernate.Session;


/**
 *
 * @author JulioCesar
 */
@ManagedBean(name = "FacturacionBean")
@SessionScoped
public class FacturacionBean {
    private Session session;
    private List<ItemsFacturacionDTO> listItems;
    
    private String producto;
    private String cliente;
    private String cajero;
    private String sucursal;
    private Date fecha;
    private int cantidad;
    private double precio;
    private int maxNumeroFactura;
     
    private String lblCajero;
    private String lblCliente;
    private String lblSucursal;
    
    List<Producto> listProd;
    List<Persona> listClientes;
    List<Persona> listCajeros;
    List<Sucursal> listSucursales;
    
    private int cedulaCliente;
    private String nombreCliente;
    private String apellidoCliente;
    private String direccionCliente;
    
    public FacturacionBean() {
        listItems = new ArrayList<>();
        HibernateUtil.start();
        session = HibernateUtil.getSession();
        
    }
    
    
    
    
    public void agregarItem(ActionEvent actionEvent){
        Producto p= consultarProducto();
        consultarPrecio();
        ItemsFacturacionDTO i= new ItemsFacturacionDTO();
        i.setId_producto(String.valueOf(p.getId_producto()));
        i.setNombreProducto(p.getNombre());
        i.setCantidad(cantidad);
        i.setPrecio(precio);
        i.setTotal(cantidad*precio);
        listItems.add(i);
    }
    
     public void agregarCliente(){
        Persona p = new Persona();
        p.setCedula(cedulaCliente);
        p.setApellido(apellidoCliente);
        p.setNombre(nombreCliente);
        p.setDireccion(direccionCliente);
        listClientes.add(p);
         System.out.println("agregado otro cliente");
    }
            
    
    
    
    
    public List<String> cargarProductos() {
        String sql = "FROM Producto";
        Query q = session.createQuery(sql);
        listProd=q.list();
        List<String>aux= new ArrayList<>();
        for (int i = 0; i < listProd.size(); i++) {
            aux.add(String.valueOf(listProd.get(i).getId_producto()));
        }

        return aux; 
    }
    
    public Producto consultarProducto() {
        Producto p=null;
            for (int i = 0; i < listProd.size(); i++) {
                if(listProd.get(i).getId_producto()==Integer.parseInt(producto)){
                    p= listProd.get(i);
                }
            }
        return p;
    }
    
    public Producto consultarProducto(String id) {
        Producto p=null;
            for (int i = 0; i < listProd.size(); i++) {
                if(listProd.get(i).getId_producto()==Integer.parseInt(id)){
                    p= listProd.get(i);
                }
            }
        return p;
    }
     
     
    public Persona consultarCliente() {
        Persona p=null;
            for (int i = 0; i < listClientes.size(); i++) {
                if(listClientes.get(i).getCedula()==Integer.parseInt(cliente)){
                    p= listClientes.get(i);
                }
            }
        return p;
    }
    
    public Persona consultarCajero() {
        Persona p=null;
            for (int i = 0; i < listCajeros.size(); i++) {
                if(listCajeros.get(i).getCedula()==Integer.parseInt(cajero)){
                    p= listCajeros.get(i);
                }
            }
        return p;
    }
    
    public Sucursal consultarSucursal() {
        Sucursal s=null;
            for (int i = 0; i < listSucursales.size(); i++) {
                if(listSucursales.get(i).getNombre_sucursal().equals(sucursal)){
                    s= listSucursales.get(i);
                }
            }
        return s;
    }

     
     public List<String> cargarCajeros() {
        String sql = "FROM Persona p where p.tipo_persona = 'CJ'";
        Query q = session.createQuery(sql);
        listCajeros=q.list();
        List<String>aux= new ArrayList<>();
        for (int i = 0; i < listCajeros.size(); i++) {
            aux.add(String.valueOf(listCajeros.get(i).getCedula()));
        }
        return aux; 
    }
     
     
     
     public List<String> cargarClientes() {
        String sql = "FROM Persona p where p.tipo_persona = 'CC'";
        Query q = session.createQuery(sql);
        listClientes=q.list();
        List<String>aux= new ArrayList<>();
        for (int i = 0; i < listClientes.size(); i++) {
            aux.add(String.valueOf(listClientes.get(i).getCedula()));
        }
        return aux; 
    }
     
     
     public List<String> cargarSucursales() {
        String sql = "FROM Sucursal";
        Query q = session.createQuery(sql);
        listSucursales=q.list();
        List<String>aux= new ArrayList<>();
        for (int i = 0; i < listSucursales.size(); i++) {
            aux.add(listSucursales.get(i).getNombre_sucursal());
        }
        return aux; 
    }
     
     

     
     public void consultarPrecio(){
          String sql = "Select precio FROM Item i inner join Facturas f on i.id_factura=f.id_factura where i.id_producto=:id order by fecha_compra desc";
          Query q = session.createSQLQuery(sql).setParameter("id", Integer.parseInt(producto));
          
        try {
            precio = (double) q.uniqueResult();
        } catch (Exception e) {
            List lista = q.list();
            precio = (double)lista.get(0);
        }
     }
     
     public void consultarNumeroFactura(){
         String sql = "Select max(numero_factura) FROM Facturas";
          Query q = session.createSQLQuery(sql);
          maxNumeroFactura = (int) q.uniqueResult();
     }
     
     
     public void guardar(){
        try {
         consultarNumeroFactura();
         Factura f= new Factura();
         f.setFecha_compra(new java.sql.Date(fecha.getTime()));
         f.setNumero_factura(maxNumeroFactura+1);
         f.setTipo_factura(TipoFactura.FV);
         f.setId_cajero(consultarCajero());
         f.setId_cliente(consultarCliente());
         f.setId_sucursal(consultarSucursal());
         session.save(f);
         
         for (int i = 0; i < listItems.size(); i++) {
            producto=listItems.get(i).getId_producto();
            ItemPk ipk = new ItemPk();
            ipk.setId_factura(f);
            ipk.setId_producto(consultarProducto(listItems.get(i).getId_producto()));
            
            Item it= new Item();
            it.setItemPk(ipk);
            it.setCantidad(listItems.get(i).getCantidad());
            it.setPrecio(listItems.get(i).getPrecio());
             
            session.save(it);
         }
           HibernateUtil.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
         
     }
     
    public void cambiarCajero(){
         
         lblCajero=consultarCajero().getNombre()+" "+consultarCajero().getApellido();
    }
     
    public void cambiarCliente(){
         lblCliente= consultarCliente().getNombre()+" "+ consultarCliente().getApellido();
    }
       
    public void cambiarSucursal(){
        lblSucursal= consultarSucursal().getDireccion();
    }
     
    
    public void imprimirPDF(){
        
        pdf("/resources/factura.jasper", listItems, "FacturaDeVenta.pdf");
        guardar();
        
    }
    
    public void pdf(String jasper, List<?> data, String fileName){
      
        try {
            Map<String, Object> param2 = new HashMap<>();
            param2.put("Fecha", fecha);
            param2.put("Cajero", lblCajero);
            param2.put("Cliente", lblCliente);
            param2.put("Sucursal",lblSucursal);
            param2.put("Factura", (maxNumeroFactura+1));
            param2.put("Ruta", FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Recursos/Imagenes/Logo.PNG"));
            
            
            String path=FacesContext.getCurrentInstance().getExternalContext().getRealPath(jasper);
            File file = new File(path);
            JRBeanCollectionDataSource source= new JRBeanCollectionDataSource(listItems, false);
            JasperPrint jasperPrint= JasperFillManager.fillReport(file.getPath(), param2, source);
            HttpServletResponse response=(HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment;filename="+ fileName);
            ServletOutputStream stream=response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
            FacesContext.getCurrentInstance().responseComplete();
        } catch (JRException ex) {
            Logger.getLogger(FacturacionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FacturacionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    } 
     
     
     
     
     
     

    public List<ItemsFacturacionDTO> getListItems() {
        return listItems;
    }

    public void setListItems(List<ItemsFacturacionDTO> listItems) {
        this.listItems = listItems;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getCajero() {
        return cajero;
    }

    public void setCajero(String cajero) {
        this.cajero = cajero;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getLblCajero() {
        return lblCajero;
    }

    public void setLblCajero(String lblCajero) {
        this.lblCajero = lblCajero;
    }

    public String getLblCliente() {
        return lblCliente;
    }

    public void setLblCliente(String lblCliente) {
        this.lblCliente = lblCliente;
    }

    public String getLblSucursal() {
        return lblSucursal;
    }

    public void setLblSucursal(String lblSucursal) {
        this.lblSucursal = lblSucursal;
    }

    public int getCedulaCliente() {
        return cedulaCliente;
    }

    public void setCedulaCliente(int cedulaCliente) {
        this.cedulaCliente = cedulaCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    
    
    
    
     
     
    
}
