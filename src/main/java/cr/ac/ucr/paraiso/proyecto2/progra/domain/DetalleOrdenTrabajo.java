/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.paraiso.proyecto2.progra.domain;

import java.util.List;

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
    
    private List<Servicio> servicio;
    private List<Repuesto> repuesto;

    public DetalleOrdenTrabajo() {
    }

    public DetalleOrdenTrabajo(String idDetalle, int cantidad, String observaciones, String tipoDetalle, String estado, List<Servicio> servicio, List<Repuesto> repuesto) {
        this.idDetalle = idDetalle;
        this.cantidad = cantidad;
        this.observaciones = observaciones;
        this.tipoDetalle = tipoDetalle;
        this.estado = estado;
        this.servicio = servicio;
        this.repuesto = repuesto;
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

    public List<Servicio> getServicio() {
        return servicio;
    }

    public void setServicio(List<Servicio> servicio) {
        this.servicio = servicio;
    }

    public List<Repuesto> getRepuesto() {
        return repuesto;
    }

    public void setRepuesto(List<Repuesto> repuesto) {
        this.repuesto = repuesto;
    }
    
    
    
    
}
