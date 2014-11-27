
package Modelo;

import Cache.InterfazCache;
import java.awt.Color;
import java.awt.Paint;

public class Candidato implements InterfazCache{

    private static final long serialVersionUID = -5372772868069600498L;

    private String nombre;
    private int numVotos;
    private int id;
    private Color color;

    public Candidato(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.numVotos = 0;
    }

    public Candidato(String nombre) {
        this.nombre = nombre;
        this.numVotos = 0;
    }

    public void agregarVoto() {
        ++numVotos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumVotos() {
        return numVotos;
    }


    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public int getId() {
        return id;
    }

    public Paint getColor() {
        return color;
    }

}
