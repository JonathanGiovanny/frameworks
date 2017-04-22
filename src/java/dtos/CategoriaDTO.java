/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;

/**
 *
 * @author RA302
 */
public class CategoriaDTO implements Serializable {

    private String catNombre;
    private String catPadre;

    public CategoriaDTO() {
        //Constructor vacio
    }

    public CategoriaDTO(String cat, String catPadre) {
        this.catNombre = cat;
        this.catPadre = catPadre;
    }

    public String getCatNombre() {
        return catNombre;
    }

    public void setCatNombre(String catNombre) {
        this.catNombre = catNombre;
    }

    public String getCatPadre() {
        return catPadre;
    }

    public void setCatPadre(String catPadre) {
        this.catPadre = catPadre;
    }

}
