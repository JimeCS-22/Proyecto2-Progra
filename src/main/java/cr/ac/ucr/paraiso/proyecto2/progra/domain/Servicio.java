/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.paraiso.proyecto2.progra.domain;

import java.util.Objects;


/**
 *
 * @author fabia
 */
public class Servicio {
    
    
    private String descripcion;
    private double precio;
    private double costoManoObra;

    public Servicio() {
    }

    public Servicio(String descripcion, double precio, double costoManoObra) {
        this.descripcion = descripcion;
        this.precio = precio;
        this.costoManoObra = costoManoObra;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getCostoManoObra() {
        return costoManoObra;
    }

    public void setCostoManoObra(double costoManoObra) {
        this.costoManoObra = costoManoObra;
    }

    @Override
    public String toString() {
        return "Servicio{" + "descripcion=" + 
                descripcion + ", precio=" + 
                precio + ", costoManoObra=" + 
                costoManoObra + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.descripcion);
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.precio) ^ (Double.doubleToLongBits(this.precio) >>> 32));
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.costoManoObra) ^ (Double.doubleToLongBits(this.costoManoObra) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Servicio other = (Servicio) obj;
        if (Double.doubleToLongBits(this.precio) != Double.doubleToLongBits(other.precio)) {
            return false;
        }
        if (Double.doubleToLongBits(this.costoManoObra) != Double.doubleToLongBits(other.costoManoObra)) {
            return false;
        }
        return Objects.equals(this.descripcion, other.descripcion);
    }
    
    
}
