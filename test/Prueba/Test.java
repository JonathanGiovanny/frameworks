/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Prueba;

import Conexion.HibernateUtil;
import entities.Categoria;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import utilidades.LeerCSV;

/**
 *
 * @author RA302
 */
public class Test {

    public static void main(String[] args) {

        List<List<String>> l = new ArrayList<>();

        List<String> l2 = new ArrayList<>();
        l2.add("1");
        l2.add("2");
        l2.add("3");
        l.add(l2);

        List<String> l3 = new ArrayList<>();
        l2.add("4");
        l2.add("5");
        l2.add("6");
        l.add(l3);

        List<String> l4 = new ArrayList<>();
        l2.add("7");
        l2.add("8");
        l2.add("9");
        l.add(l4);

        for (List<String> list : l) {
            if (list.contains("1")) {
                System.out.println("Pto: true");
                break;
            }
        }

//        String fila = "1;2;;4;5";
//        String fila2 = "1;2;;;5";
//        
//        String[] campos = fila.split(";");
//        String[] campos1 = fila2.split(";");
//        
//        System.out.println("Campos: " + campos.length);
//        for(String s : campos)
//            System.out.print(s + " ");
//        System.out.println("\nCampos: " + campos1.length);
//        for(String s : campos1)
//            System.out.print(s + " ");
//        
//        try {
//            HibernateUtil.start();
//            Session session = HibernateUtil.getSession();
//            LeerCSV csv = new LeerCSV();
//            List<List<String>> categorias = csv.getData("D:/Categorias.csv");
//
//            Query q = session.createQuery("Select c.id_categoria From Categoria c");
//            List<Integer> listCat = q.list();
//
//            for (List<String> filas : categorias) {
//                if (listCat.isEmpty() || !listCat.contains(Integer.parseInt(filas.get(0)))) {
//                    Categoria cat = new Categoria();
//                    if (filas.get(0).length() != 0) {
//                        cat.setId_categoria(Integer.parseInt(filas.get(0)));
//                    }
//                    cat.setNombre_categoria(filas.get(1));
//
//                    if (filas.size() == 3) {
//                        q = session.createQuery("FROM Categoria c WHERE id_categoria = " + Integer.parseInt(filas.get(2)));
//                        Categoria catPadre = (Categoria) q.uniqueResult();
//                        cat.setId_categoriapadre(catPadre);
//                    }
//
//                    session.save(cat);
//                }
//            }
//
//            HibernateUtil.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            HibernateUtil.close();
//            System.exit(0);
//        }
    }

}
