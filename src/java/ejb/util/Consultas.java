/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.util;

import Conexion.HibernateUtil;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.hibernate.tuple.entity.EntityMetamodel;

/**
 *
 * @author ASUS1
 */
@ManagedBean
@SessionScoped
public class Consultas {
    
    public Object getEntidad(String consulta){
        HibernateUtil.start();
        
        try{
            EntityMetamodel model = 
        } catch(Exception e) {
            
        }
    }
}
