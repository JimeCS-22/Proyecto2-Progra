/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.paraiso.proyecto2.progra.domain;

/**
 *
 * @author Camila
 * 
 * Detalle de Orden 
 */
public class DetalleOrdenTrabajo {
    private String idDetalle;
    private int cantidad;
    private String observaciones;
    private String tipoDetalle;
    private String estado;

    public DetalleOrdenTrabajo(String idDetalle, int cantidad, String observaciones, String tipoDetalle, String estado) {
        this.idDetalle = idDetalle;
        this.cantidad = cantidad;
        this.observaciones = observaciones;
        this.tipoDetalle = tipoDetalle;
        this.estado = estado;
    }

    public String getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(String idDetalle) {
        this.idDetalle = idDetalle;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getTipoDetalle() {
        return tipoDetalle;
    }

    public void setTipoDetalle(String tipoDetalle) {
        this.tipoDetalle = tipoDetalle;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
    
}
