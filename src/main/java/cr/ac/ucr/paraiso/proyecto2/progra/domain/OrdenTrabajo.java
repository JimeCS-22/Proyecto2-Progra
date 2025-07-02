package cr.ac.ucr.paraiso.proyecto2.progra.domain;

import java.time.LocalDate; // ¡Vamos a usar LocalDate para fechas modernas!
import java.util.ArrayList; // Importamos ArrayList para inicializar listas
import java.util.Date; // Mantener por compatibilidad, pero preferimos LocalDate
import java.util.List;
import java.util.Objects;

/**
 * Clase de dominio que representa una Orden de Trabajo.
 * Contiene la información principal de la orden, el vehículo asociado
 * y una lista de los detalles de la orden (servicios y repuestos).
 * * @author fabia
 */
public class OrdenTrabajo {

    private String id;
    private String descripcionSolicitud;
    private String estado;
    private LocalDate fechaIngreso; // ¡Cambiado a LocalDate!
    private LocalDate fechaDevolucionEstimada; // ¡Cambiado a LocalDate!

    // Se llaman a las otras clases
    private List<DetalleOrdenTrabajo> detalles;
    private Vehiculos vehiculo; // Ya contiene al cliente

    /**
     * Constructor por defecto. Inicializa las listas para evitar NullPointerExceptions.
     */
    public OrdenTrabajo() {
        this.detalles = new ArrayList<>(); // Inicializamos la lista de detalles
    }

    /**
     * Constructor con todos los campos.
     * @param id Identificador único de la orden de trabajo.
     * @param descripcionSolicitud Descripción de la solicitud del cliente.
     * @param estado Estado actual de la orden (ej. "Pendiente", "En Proceso", "Completada").
     * @param fechaIngreso Fecha de ingreso del vehículo al taller.
     * @param fechaDevolucionEstimada Fecha estimada de devolución del vehículo.
     * @param detalles Lista de detalles de la orden (servicios y repuestos).
     * @param vehiculo Objeto Vehiculos asociado a esta orden.
     */
    public OrdenTrabajo(String id, String descripcionSolicitud, String estado, LocalDate fechaIngreso, LocalDate fechaDevolucionEstimada, List<DetalleOrdenTrabajo> detalles, Vehiculos vehiculo) {
        this.id = id;
        this.descripcionSolicitud = descripcionSolicitud;
        this.estado = estado;
        this.fechaIngreso = fechaIngreso;
        this.fechaDevolucionEstimada = fechaDevolucionEstimada;
        this.detalles = (detalles != null) ? detalles : new ArrayList<>(); // Aseguramos que no sea null
        this.vehiculo = vehiculo;
    }

    /**
     * Calcula el costo total sumando el precio y costo de mano de obra de los servicios,
     * y el precio por cantidad de los repuestos.
     * Si no hay detalles o están vacíos, retorna 0.0.
     * @return El costo total de la orden de trabajo.
     */
    public double calcularCostos() {
        double total = 0.0;

        // Utilizamos la lista ya inicializada, no necesita verificación de null
        for (DetalleOrdenTrabajo detalle : detalles) {
            // Si este detalle contiene servicios, los sumamos.
            if (detalle.getServicio() != null) {
                for (Servicio s : detalle.getServicio()) {
                    total += s.getPrecio() + s.getCostoManoObra();
                }
            }

            // Si este detalle contiene repuestos, los sumamos.
            if (detalle.getRepuesto() != null) {
                for (Repuesto r : detalle.getRepuesto()) {
                    // Multiplicamos el precio del repuesto por la cantidad especificada en el repuesto.
                    total += r.getPrecio() * r.getCantidad();
                }
            }
        }
        return total;
    }

    // --- Setters y Getters ---

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcionSolicitud() {
        return descripcionSolicitud;
    }

    public void setDescripcionSolicitud(String descripcionSolicitud) {
        this.descripcionSolicitud = descripcionSolicitud;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDate getFechaDevolucionEstimada() {
        return fechaDevolucionEstimada;
    }

    public void setFechaDevolucionEstimada(LocalDate fechaDevolucionEstimada) {
        this.fechaDevolucionEstimada = fechaDevolucionEstimada;
    }

    public List<DetalleOrdenTrabajo> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleOrdenTrabajo> detalles) {
        // Aseguramos que la lista nunca sea null al ser asignada
        this.detalles = (detalles != null) ? detalles : new ArrayList<>();
    }

    public Vehiculos getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculos vehiculo) {
        this.vehiculo = vehiculo;
    }

    @Override
    public String toString() {
        return "OrdenTrabajo{" +
                "id='" + id + '\'' +
                ", descripcionSolicitud='" + descripcionSolicitud + '\'' +
                ", estado='" + estado + '\'' +
                ", fechaIngreso=" + fechaIngreso +
                ", fechaDevolucionEstimada=" + fechaDevolucionEstimada +
                ", detalles=" + detalles +
                ", vehiculo=" + vehiculo +
                '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.descripcionSolicitud);
        hash = 59 * hash + Objects.hashCode(this.estado);
        hash = 59 * hash + Objects.hashCode(this.fechaIngreso);
        hash = 59 * hash + Objects.hashCode(this.fechaDevolucionEstimada);
        hash = 59 * hash + Objects.hashCode(this.detalles);
        hash = 59 * hash + Objects.hashCode(this.vehiculo);
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
        final OrdenTrabajo other = (OrdenTrabajo) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.descripcionSolicitud, other.descripcionSolicitud)) {
            return false;
        }
        if (!Objects.equals(this.estado, other.estado)) {
            return false;
        }
        if (!Objects.equals(this.fechaIngreso, other.fechaIngreso)) {
            return false;
        }
        if (!Objects.equals(this.fechaDevolucionEstimada, other.fechaDevolucionEstimada)) {
            return false;
        }
        if (!Objects.equals(this.detalles, other.detalles)) {
            return false;
        }
        return Objects.equals(this.vehiculo, other.vehiculo);
    }
    
    
}