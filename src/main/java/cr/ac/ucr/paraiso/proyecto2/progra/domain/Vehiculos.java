package cr.ac.ucr.paraiso.proyecto2.progra.domain;

/**
 * Representa un vehículo asociado a una orden de trabajo.
 * Contiene la información de la placa y los datos del cliente propietario.
 */
public class Vehiculos {
    private String placa;
    private Cliente cliente; // Agregamos la referencia a la clase Cliente

    public Vehiculos() {
        // Constructor por defecto
    }

    /**
     * Constructor para inicializar un objeto Vehiculos.
     * @param placa La placa del vehículo.
     * @param cliente El objeto Cliente propietario del vehículo.
     */
    public Vehiculos(String placa, Cliente cliente) {
        this.placa = placa;
        this.cliente = cliente;
    }

    // --- Getters y Setters ---

    /**
     * Obtiene la placa del vehículo.
     * @return La placa del vehículo.
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * Establece la placa del vehículo.
     * @param placa La nueva placa del vehículo.
     */
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    /**
     * Obtiene el objeto Cliente propietario del vehículo.
     * @return El objeto Cliente.
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Establece el objeto Cliente propietario del vehículo.
     * @param cliente El nuevo objeto Cliente.
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "Vehiculos{" +
               "placa='" + placa + '\'' +
               ", cliente=" + (cliente != null ? cliente.getNombre() : "N/A") + // Muestra el nombre del cliente si existe
               '}';
    }
}