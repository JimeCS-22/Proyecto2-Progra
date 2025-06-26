/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.paraiso.proyecto2.progra.domain;


/**
 *
 * @author fabia
 */
class Servicio {
    
    
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
    
}
