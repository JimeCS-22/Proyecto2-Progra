/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.paraiso.proyecto2.progra.domain;

import java.util.List;
import java.util.Objects;

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

    @Override
    public String toString() {
        return "DetalleOrdenTrabajo{" + "idDetalle=" + idDetalle + ", cantidad=" + cantidad + ", observaciones=" + observaciones + ", tipoDetalle=" + tipoDetalle + ", estado=" + estado + ", servicio=" + servicio + ", repuesto=" + repuesto + '}';
    }
    
    //to the asserts

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.idDetalle);
        hash = 83 * hash + this.cantidad;
        hash = 83 * hash + Objects.hashCode(this.observaciones);
        hash = 83 * hash + Objects.hashCode(this.tipoDetalle);
        hash = 83 * hash + Objects.hashCode(this.estado);
        hash = 83 * hash + Objects.hashCode(this.servicio);
        hash = 83 * hash + Objects.hashCode(this.repuesto);
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
        final DetalleOrdenTrabajo other = (DetalleOrdenTrabajo) obj;
        if (this.cantidad != other.cantidad) {
            return false;
        }
        if (!Objects.equals(this.idDetalle, other.idDetalle)) {
            return false;
        }
        if (!Objects.equals(this.observaciones, other.observaciones)) {
            return false;
        }
        if (!Objects.equals(this.tipoDetalle, other.tipoDetalle)) {
            return false;
        }
        if (!Objects.equals(this.estado, other.estado)) {
            return false;
        }
        if (!Objects.equals(this.servicio, other.servicio)) {
            return false;
        }
        return Objects.equals(this.repuesto, other.repuesto);
    }
    
    
    
}
