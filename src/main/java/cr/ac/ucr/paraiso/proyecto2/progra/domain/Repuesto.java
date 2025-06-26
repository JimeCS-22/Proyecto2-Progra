/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.paraiso.proyecto2.progra.domain;

/**
 *
 * @author fabia 
 * cantidad, nombre del repuesto, precio, si el repuesto fue pedido o no)
 */
public class Repuesto {
    private int cantidad;
    private String nombreRepuesto;
    private double precio;
    private boolean fuePedido;

    public Repuesto() {
    }

    public Repuesto(int cantidad, String nombreRepuesto, double precio, boolean fuePedido) {
        this.cantidad = cantidad;
        this.nombreRepuesto = nombreRepuesto;
        this.precio = precio;
        this.fuePedido = fuePedido;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombreRepuesto() {
        return nombreRepuesto;
    }

    public void setNombreRepuesto(String nombreRepuesto) {
        this.nombreRepuesto = nombreRepuesto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean isFuePedido() {
        return fuePedido;
    }

    public void setFuePedido(boolean fuePedido) {
        this.fuePedido = fuePedido;
    }
    
    
    
}
