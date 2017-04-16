/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Set;

/**
 *
 * @author RA302
 */
public class Categoria {

    private int id_categoria;
    private String nombre_categoria;
    private Categoria id_categoriapadre;
    
    private Set<Producto> listaProductos;
    private Set<Categoria> listaCategorias;

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getNombre_categoria() {
        return nombre_categoria;
    }

    public void setNombre_categoria(String nombre_categoria) {
        this.nombre_categoria = nombre_categoria;
    }

    public Categoria getId_categoriapadre() {
        return id_categoriapadre;
    }

    public void setId_categoriapadre(Categoria id_categoriapadre) {
        this.id_categoriapadre = id_categoriapadre;
    }

    public Set<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(Set<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public Set<Categoria> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(Set<Categoria> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }

    

}
