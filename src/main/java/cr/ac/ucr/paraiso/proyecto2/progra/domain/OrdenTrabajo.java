/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.paraiso.proyecto2.progra.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 *
 * @author fabia
 */
public class OrdenTrabajo {
    
    private String id;
    private String descripcionSolicitud;
    private String estado;
    private Date fechaIngreso;
    private Date fechaDevolucionEstimada;
    
    //Se llaman a las otras claases 
    private List<DetalleOrdenTrabajo> detalles;
    private Vehiculos vehiculo;// Ya contiene al cliente

    public OrdenTrabajo() {
    }

    public OrdenTrabajo(String id, String descripcionSolicitud, String estado, Date fechaIngreso, Date fechaDevolucionEstimada, List<DetalleOrdenTrabajo> detalles, Vehiculos vehiculo) {
        this.id = id;
        this.descripcionSolicitud = descripcionSolicitud;
        this.estado = estado;
        this.fechaIngreso = fechaIngreso;
        this.fechaDevolucionEstimada = fechaDevolucionEstimada;
        this.detalles = detalles;
        this.vehiculo = vehiculo;
    }

    //Suma el costo total de todos los servicios y repuestos
    public double calcularCostos(){
     double total = 0.0;

    if (detalles != null) {
        for (DetalleOrdenTrabajo detalle : detalles) {
            if (detalle.getServicio() != null) {
                Servicio s = detalle.getServicio();
                total += s.getPrecio() + s.getCostoManoObra();
            } else if (detalle.getRepuesto() != null) {
                Repuesto r = detalle.getRepuesto();
                total += r.getPrecio() * detalle.getCantidad();
            }
        }
    }

    return total;
    }
    
    
    //Setters y getters
    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getDescripcionSolicitud() {return descripcionSolicitud;}

    public void setDescripcionSolicitud(String descripcionSolicitud) {this.descripcionSolicitud = descripcionSolicitud;}

    public String getEstado() {return estado;}

    public void setEstado(String estado) {this.estado = estado;}

    public Date getFechaIngreso() {return fechaIngreso;}

    public void setFechaIngreso(Date fechaIngreso) {this.fechaIngreso = fechaIngreso;}

    public Date getFechaDevolucionEstimada() {return fechaDevolucionEstimada;}

    public void setFechaDevolucionEstimada(Date fechaDevolucionEstimada) {this.fechaDevolucionEstimada = fechaDevolucionEstimada;}

    public List<DetalleOrdenTrabajo> getDetalles() {return detalles;}

    public void setDetalles(List<DetalleOrdenTrabajo> detalles) {this.detalles = detalles;}

    public Vehiculos getVehiculo() {return vehiculo;}

    public void setVehiculo(Vehiculos vehiculo) {this.vehiculo = vehiculo;}

    //
    @Override
    public String toString() {
        return "OrdenTrabajo{" + "id=" + id + ", descripcionSolicitud=" + descripcionSolicitud + 
                ", estado=" + estado + ", fechaIngreso=" + fechaIngreso + 
                ", fechaDevolucionEstimada=" + fechaDevolucionEstimada + 
                ", detalles=" + detalles + ", vehiculo=" + vehiculo + '}';
    }
    
    
    
    
    
    
}
