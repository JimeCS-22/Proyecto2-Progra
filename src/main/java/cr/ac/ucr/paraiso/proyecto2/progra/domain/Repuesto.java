/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.paraiso.proyecto2.progra.domain;

import java.util.Objects;

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

    @Override
    public String toString() {
        return "Repuesto{" + "cantidad=" + cantidad + ", nombreRepuesto=" + nombreRepuesto + ", precio=" + precio + ", fuePedido=" + fuePedido + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + this.cantidad;
        hash = 89 * hash + Objects.hashCode(this.nombreRepuesto);
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.precio) ^ (Double.doubleToLongBits(this.precio) >>> 32));
        hash = 89 * hash + (this.fuePedido ? 1 : 0);
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
        final Repuesto other = (Repuesto) obj;
        if (this.cantidad != other.cantidad) {
            return false;
        }
        if (Double.doubleToLongBits(this.precio) != Double.doubleToLongBits(other.precio)) {
            return false;
        }
        if (this.fuePedido != other.fuePedido) {
            return false;
        }
        return Objects.equals(this.nombreRepuesto, other.nombreRepuesto);
    }
    
    
    
}
