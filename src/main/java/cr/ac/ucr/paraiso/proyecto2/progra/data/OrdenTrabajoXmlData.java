package cr.ac.ucr.paraiso.proyecto2.progra.data;

import cr.ac.ucr.paraiso.proyecto2.progra.domain.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate; // Importar LocalDate
import java.time.format.DateTimeFormatter; // Importar DateTimeFormatter
import java.time.format.DateTimeParseException; // Importar para manejar errores de parseo de fecha
import java.util.ArrayList;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class OrdenTrabajoXmlData {
    private Document document;
    private Element raiz;
    private String rutaDocumento;

    // Formato para guardar y leer fechas en XML (yyyy-MM-dd) usando LocalDate
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Constructor que inicializa o carga el documento XML para las órdenes de trabajo.
     * Si el archivo no existe o está vacío, crea la estructura XML inicial.
     * @param rutaDocumento La ruta completa del archivo XML (e.g., ".../WEB-INF/archivos/ordenes.xml").
     * @throws JDOMException Si ocurre un error al parsear el documento XML.
     * @throws IOException Si ocurre un error de E/S al leer o escribir el archivo.
     */
    public OrdenTrabajoXmlData(String rutaDocumento) throws JDOMException, IOException {
        this.rutaDocumento = rutaDocumento;
        File file = new File(rutaDocumento);
        if (file.exists() && file.length() > 0) {
            SAXBuilder builder = new SAXBuilder();
            builder.setIgnoringElementContentWhitespace(true);
            this.document = builder.build(file);
            this.raiz = document.getRootElement();
        } else {
            this.raiz = new Element("ordenesDeTrabajo");
            this.document = new Document(raiz);
            guardarDocumento(); // Guarda el XML inicial vacío
        }
    }

    /**
     * Guarda el documento XML actual en la ruta especificada.
     * @throws IOException Si ocurre un error de E/S.
     */
    private void guardarDocumento() throws IOException {
        XMLOutputter xmlOutput = new XMLOutputter();
        xmlOutput.setFormat(Format.getPrettyFormat()); // Formato legible con indentación
        xmlOutput.output(document, new FileWriter(rutaDocumento));
    }

    /**
     * Inserta una nueva Orden de Trabajo en el archivo XML.
     * Si una orden con el mismo ID ya existe, no la inserta.
     * @param orden La OrdenTrabajo a insertar.
     * @return La OrdenTrabajo insertada, o null si ya existía.
     * @throws IOException Si ocurre un error de E/S.
     */
    public OrdenTrabajo insertarOrden(OrdenTrabajo orden) throws IOException {
        // Usamos buscarOrdenTrabajo, como lo tienes en tu código.
        if (buscarOrdenTrabajo(orden.getId()) != null) {
            return null; // La orden ya existe
        }

        Element nuevaOrden = crearElementoOrden(orden);
        raiz.addContent(nuevaOrden);
        guardarDocumento();
        return orden;
    }

    /**
     * Actualiza solo los campos principales de una Orden de Trabajo existente en el archivo XML.
     * No modifica la lista de detalles existente.
     * @param orden La OrdenTrabajo con los datos principales actualizados.
     * @return La OrdenTrabajo actualizada, o null si no se encontró.
     * @throws IOException Si ocurre un error de E/S.
     */
    public OrdenTrabajo actualizarOrdenPrincipal(OrdenTrabajo orden) throws IOException {
        Element ordenExistente = null;
        for (Element el : raiz.getChildren("ordenTrabajo")) {
            if (el.getChildText("id") != null && el.getChildText("id").equals(orden.getId())) {
                ordenExistente = el;
                break;
            }
        }

        if (ordenExistente != null) {
            // Actualizar campos principales de la orden
            if (orden.getDescripcionSolicitud() != null) {
                Element descElement = ordenExistente.getChild("descripcionSolicitud");
                if (descElement == null) {
                    descElement = new Element("descripcionSolicitud");
                    ordenExistente.addContent(descElement);
                }
                descElement.setText(orden.getDescripcionSolicitud());
            }
            if (orden.getEstado() != null) {
                Element estadoElement = ordenExistente.getChild("estado");
                if (estadoElement == null) {
                    estadoElement = new Element("estado");
                    ordenExistente.addContent(estadoElement);
                }
                estadoElement.setText(orden.getEstado());
            }
            if (orden.getFechaIngreso() != null) {
                Element fechaIngresoElement = ordenExistente.getChild("fechaIngreso");
                if (fechaIngresoElement == null) {
                    fechaIngresoElement = new Element("fechaIngreso");
                    ordenExistente.addContent(fechaIngresoElement);
                }
                fechaIngresoElement.setText(orden.getFechaIngreso().format(DATE_FORMATTER)); // Usar format para LocalDate
            }
            if (orden.getFechaDevolucionEstimada() != null) {
                Element fechaDevolucionElement = ordenExistente.getChild("fechaDevolucionEstimada");
                if (fechaDevolucionElement == null) {
                    fechaDevolucionElement = new Element("fechaDevolucionEstimada");
                    ordenExistente.addContent(fechaDevolucionElement);
                }
                fechaDevolucionElement.setText(orden.getFechaDevolucionEstimada().format(DATE_FORMATTER)); // Usar format para LocalDate
            }

            // Actualizar Vehículo (placa)
            Element vehiculoElement = ordenExistente.getChild("vehiculo");
            if (orden.getVehiculo() != null && orden.getVehiculo().getPlaca() != null) {
                if (vehiculoElement == null) {
                    vehiculoElement = new Element("vehiculo");
                    ordenExistente.addContent(vehiculoElement);
                }
                Element placaElement = vehiculoElement.getChild("placa");
                if (placaElement == null) {
                    placaElement = new Element("placa");
                    vehiculoElement.addContent(placaElement);
                }
                placaElement.setText(orden.getVehiculo().getPlaca());
            } else {
                if (vehiculoElement != null) {
                    ordenExistente.removeChild("vehiculo");
                }
            }

            guardarDocumento();
            return orden;
        }
        return null; // Orden no encontrada para actualizar
    }

    /**
     * Actualiza la lista completa de detalles para una Orden de Trabajo específica.
     * Esto reemplaza los detalles existentes con los nuevos proporcionados.
     * @param ordenId El ID de la orden cuyos detalles se van a actualizar.
     * @param nuevosDetalles La nueva lista de DetalleOrdenTrabajo.
     * @return true si los detalles fueron actualizados, false si la orden no se encontró.
     * @throws IOException Si ocurre un error de E/S.
     */
    public boolean actualizarDetallesOrden(String ordenId, List<DetalleOrdenTrabajo> nuevosDetalles) throws IOException {
        Element ordenExistenteElement = null;
        for (Element el : raiz.getChildren("ordenTrabajo")) {
            if (el.getChildText("id") != null && el.getChildText("id").equals(ordenId)) {
                ordenExistenteElement = el;
                break;
            }
        }

        if (ordenExistenteElement != null) {
            // Elimina el elemento 'detalles' existente si lo hay
            ordenExistenteElement.removeChild("detalles");

            // Si hay nuevos detalles, los añade
            if (nuevosDetalles != null && !nuevosDetalles.isEmpty()) {
                Element detallesElement = new Element("detalles");
                for (DetalleOrdenTrabajo detalle : nuevosDetalles) {
                    detallesElement.addContent(crearElementoDetalle(detalle));
                }
                ordenExistenteElement.addContent(detallesElement);
            }
            guardarDocumento();
            return true;
        }
        return false; // Orden no encontrada
    }

    /**
     * Busca y retorna una Orden de Trabajo por su ID.
     * @param id El ID de la orden a buscar.
     * @return La OrdenTrabajo encontrada, o null si no existe.
     */
    public OrdenTrabajo buscarOrdenTrabajo(String id) {
        for (Element ordenElement : raiz.getChildren("ordenTrabajo")) {
            if (ordenElement.getChildText("id") != null && ordenElement.getChildText("id").equals(id)) {
                try {
                    return construirOrdenDesdeElemento(ordenElement);
                } catch (DateTimeParseException e) { // Cambiado a DateTimeParseException
                    System.err.println("Error al parsear fecha para la orden con ID " + id + ": " + e.getMessage());
                    return null;
                }
            }
        }
        return null; // No encontrada
    }

    /**
     * Retorna una lista de todas las Ordenes de Trabajo en el XML.
     * @return Una lista de OrdenTrabajo.
     */
    public List<OrdenTrabajo> getOrdenesDeTrabajo() {
        List<OrdenTrabajo> ordenes = new ArrayList<>();
        for (Element ordenElement : raiz.getChildren("ordenTrabajo")) {
            try {
                ordenes.add(construirOrdenDesdeElemento(ordenElement));
            } catch (DateTimeParseException e) { // Cambiado a DateTimeParseException
                System.err.println("Error al parsear fecha para una orden: " + e.getMessage());
            }
        }
        return ordenes;
    }

    /**
     * Elimina una Orden de Trabajo por su ID.
     * @param id El ID de la orden a eliminar.
     * @return true si la orden fue eliminada, false si no se encontró.
     * @throws IOException Si ocurre un error de E/S.
     */
    public boolean eliminarOrdenPorId(String id) throws IOException {
        Element ordenAEliminar = null;
        for (Element ordenElement : raiz.getChildren("ordenTrabajo")) {
            if (ordenElement.getChildText("id") != null && ordenElement.getChildText("id").equals(id)) {
                ordenAEliminar = ordenElement;
                break;
            }
        }

        if (ordenAEliminar != null) {
            raiz.removeContent(ordenAEliminar);
            guardarDocumento();
            return true;
        }
        return false;
    }

    /**
     * Método auxiliar para construir un objeto OrdenTrabajo desde un elemento XML.
     * @param ordenElement El elemento XML que representa una orden.
     * @return El objeto OrdenTrabajo construido.
     * @throws DateTimeParseException Si ocurre un error al parsear las fechas.
     */
    private OrdenTrabajo construirOrdenDesdeElemento(Element ordenElement) { // No throws DateTimeParseException aquí, se maneja internamente
        OrdenTrabajo orden = new OrdenTrabajo();
        orden.setId(ordenElement.getChildTextTrim("id"));
        orden.setDescripcionSolicitud(ordenElement.getChildTextTrim("descripcionSolicitud"));
        orden.setEstado(ordenElement.getChildTextTrim("estado"));

        String fechaIngresoStr = ordenElement.getChildTextTrim("fechaIngreso");
        if (fechaIngresoStr != null && !fechaIngresoStr.isEmpty()) {
            try {
                orden.setFechaIngreso(LocalDate.parse(fechaIngresoStr, DATE_FORMATTER)); // Usar parse con formatter
            } catch (DateTimeParseException e) {
                System.err.println("Error al parsear fecha de ingreso: " + fechaIngresoStr + " - " + e.getMessage());
                // Considera cómo manejar este error: dejar la fecha nula, una fecha por defecto, etc.
            }
        }

        String fechaDevolucionStr = ordenElement.getChildTextTrim("fechaDevolucionEstimada");
        if (fechaDevolucionStr != null && !fechaDevolucionStr.isEmpty()) {
            try {
                orden.setFechaDevolucionEstimada(LocalDate.parse(fechaDevolucionStr, DATE_FORMATTER)); // Usar parse con formatter
            } catch (DateTimeParseException e) {
                System.err.println("Error al parsear fecha de devolución estimada: " + fechaDevolucionStr + " - " + e.getMessage());
            }
        }

        // Reconstruir Vehiculo
        Element vehiculoElement = ordenElement.getChild("vehiculo");
        if (vehiculoElement != null) {
            Vehiculos vehiculo = new Vehiculos();
            Element placaChild = vehiculoElement.getChild("placa");
            if (placaChild != null) {
                vehiculo.setPlaca(placaChild.getTextTrim());
            }
            orden.setVehiculo(vehiculo);
        }

        // Reconstruir Detalles de la Orden
        Element detallesElement = ordenElement.getChild("detalles");
        if (detallesElement != null) {
            List<DetalleOrdenTrabajo> detallesOrden = new ArrayList<>();
            for (Element detalleElement : detallesElement.getChildren("detalle")) {
                detallesOrden.add(construirDetalleDesdeElemento(detalleElement));
            }
            orden.setDetalles(detallesOrden);
        } else {
            orden.setDetalles(new ArrayList<>());
        }
        return orden;
    }

    /**
     * Método auxiliar para construir un objeto DetalleOrdenTrabajo desde un elemento XML.
     * @param detalleElement El elemento XML que representa un detalle.
     * @return El objeto DetalleOrdenTrabajo construido.
     */
    private DetalleOrdenTrabajo construirDetalleDesdeElemento(Element detalleElement) {
        DetalleOrdenTrabajo detalle = new DetalleOrdenTrabajo();
        detalle.setIdDetalle(detalleElement.getChildTextTrim("idDetalle"));

        String cantidadStr = detalleElement.getChildTextTrim("cantidad");
        if (cantidadStr != null && !cantidadStr.isEmpty()) {
            try {
                detalle.setCantidad(Integer.parseInt(cantidadStr));
            } catch (NumberFormatException e) {
                System.err.println("Error de formato de número en cantidad para detalle: " + detalle.getIdDetalle() + ". Valor: " + cantidadStr);
                detalle.setCantidad(0);
            }
        } else {
            detalle.setCantidad(0);
        }

        detalle.setObservaciones(detalleElement.getChildTextTrim("observaciones"));
        detalle.setTipoDetalle(detalleElement.getChildTextTrim("tipoDetalle"));
        detalle.setEstado(detalleElement.getChildTextTrim("estadoDetalle"));

        // Reconstruir Servicios del detalle
        Element serviciosElement = detalleElement.getChild("servicios");
        if (serviciosElement != null) {
            List<Servicio> serviciosDetalle = new ArrayList<>();
            for (Element servicioElement : serviciosElement.getChildren("servicio")) {
                serviciosDetalle.add(construirServicioDesdeElemento(servicioElement));
            }
            detalle.setServicio(serviciosDetalle);
        } else {
            detalle.setServicio(new ArrayList<>());
        }

        // Reconstruir Repuestos del detalle
        Element repuestosElement = detalleElement.getChild("repuestos");
        if (repuestosElement != null) {
            List<Repuesto> repuestosDetalle = new ArrayList<>();
            for (Element repuestoElement : repuestosElement.getChildren("repuesto")) {
                repuestosDetalle.add(construirRepuestoDesdeElemento(repuestoElement));
            }
            detalle.setRepuesto(repuestosDetalle);
        } else {
            detalle.setRepuesto(new ArrayList<>());
        }
        return detalle;
    }

    /**
     * Método auxiliar para construir un objeto Servicio desde un elemento XML.
     * @param servicioElement El elemento XML que representa un servicio.
     * @return El objeto Servicio construido.
     */
    private Servicio construirServicioDesdeElemento(Element servicioElement) {
        Servicio servicio = new Servicio();
        servicio.setDescripcion(servicioElement.getChildTextTrim("descripcion"));

        String precioServicioStr = servicioElement.getChildTextTrim("precio");
        if (precioServicioStr != null && !precioServicioStr.isEmpty()) {
            try {
                servicio.setPrecio(Double.parseDouble(precioServicioStr));
            } catch (NumberFormatException e) {
                System.err.println("Error de formato de número en precio para servicio: " + precioServicioStr);
                servicio.setPrecio(0.0);
            }
        } else {
            servicio.setPrecio(0.0);
        }

        String costoManoObraStr = servicioElement.getChildTextTrim("costoManoObra");
        if (costoManoObraStr != null && !costoManoObraStr.isEmpty()) {
            try {
                servicio.setCostoManoObra(Double.parseDouble(costoManoObraStr));
            } catch (NumberFormatException e) {
                System.err.println("Error de formato de número en costoManoObra para servicio: " + costoManoObraStr);
                servicio.setCostoManoObra(0.0);
            }
        } else {
            servicio.setCostoManoObra(0.0);
        }
        return servicio;
    }

    /**
     * Método auxiliar para construir un objeto Repuesto desde un elemento XML.
     * @param repuestoElement El elemento XML que representa un repuesto.
     * @return El objeto Repuesto construido.
     */
    private Repuesto construirRepuestoDesdeElemento(Element repuestoElement) {
        Repuesto repuesto = new Repuesto();

        String cantidadRepuestoStr = repuestoElement.getChildTextTrim("cantidad");
        if (cantidadRepuestoStr != null && !cantidadRepuestoStr.isEmpty()) {
            try {
                repuesto.setCantidad(Integer.parseInt(cantidadRepuestoStr));
            } catch (NumberFormatException e) {
                System.err.println("Error de formato de número en cantidad para repuesto: " + cantidadRepuestoStr);
                repuesto.setCantidad(0);
            }
        } else {
            repuesto.setCantidad(0);
        }

        repuesto.setNombreRepuesto(repuestoElement.getChildTextTrim("nombreRepuesto"));

        String precioRepuestoStr = repuestoElement.getChildTextTrim("precio");
        if (precioRepuestoStr != null && !precioRepuestoStr.isEmpty()) {
            try {
                repuesto.setPrecio(Double.parseDouble(precioRepuestoStr));
            } catch (NumberFormatException e) {
                System.err.println("Error de formato de número en precio para repuesto: " + precioRepuestoStr);
                repuesto.setPrecio(0.0);
            }
        } else {
            repuesto.setPrecio(0.0);
        }

        String fuePedidoStr = repuestoElement.getChildTextTrim("fuePedido");
        if (fuePedidoStr != null && !fuePedidoStr.isEmpty()) {
            repuesto.setFuePedido(Boolean.parseBoolean(fuePedidoStr));
        } else {
            repuesto.setFuePedido(false);
        }
        return repuesto;
    }


    /**
     * Método auxiliar para crear un Elemento XML de OrdenTrabajo desde un objeto OrdenTrabajo.
     * Incluye los detalles, servicios y repuestos.
     * @param orden El objeto OrdenTrabajo a convertir en Elemento XML.
     * @return El Elemento XML de la orden.
     */
    private Element crearElementoOrden(OrdenTrabajo orden) {
        Element ordenElement = new Element("ordenTrabajo");
        ordenElement.addContent(new Element("id").setText(orden.getId() != null ? orden.getId() : ""));
        ordenElement.addContent(new Element("descripcionSolicitud").setText(orden.getDescripcionSolicitud() != null ? orden.getDescripcionSolicitud() : ""));
        ordenElement.addContent(new Element("estado").setText(orden.getEstado() != null ? orden.getEstado() : ""));
        // Formatear LocalDate a String para guardar en XML
        ordenElement.addContent(new Element("fechaIngreso").setText(orden.getFechaIngreso() != null ? orden.getFechaIngreso().format(DATE_FORMATTER) : ""));
        ordenElement.addContent(new Element("fechaDevolucionEstimada").setText(orden.getFechaDevolucionEstimada() != null ? orden.getFechaDevolucionEstimada().format(DATE_FORMATTER) : ""));

        // Guardar información del Vehículo
        if (orden.getVehiculo() != null && orden.getVehiculo().getPlaca() != null) {
            Element vehiculoElement = new Element("vehiculo");
            vehiculoElement.addContent(new Element("placa").setText(orden.getVehiculo().getPlaca()));
            ordenElement.addContent(vehiculoElement);
        }

        // Guardar los Detalles de la Orden
        if (orden.getDetalles() != null && !orden.getDetalles().isEmpty()) {
            Element detallesElement = new Element("detalles");
            for (DetalleOrdenTrabajo detalle : orden.getDetalles()) {
                detallesElement.addContent(crearElementoDetalle(detalle));
            }
            ordenElement.addContent(detallesElement);
        }
        return ordenElement;
    }

    /**
     * Método auxiliar para crear un Elemento XML de DetalleOrdenTrabajo desde un objeto DetalleOrdenTrabajo.
     * Incluye los servicios y repuestos.
     * @param detalle El objeto DetalleOrdenTrabajo a convertir en Elemento XML.
     * @return El Elemento XML del detalle.
     */
    private Element crearElementoDetalle(DetalleOrdenTrabajo detalle) {
        Element detalleElement = new Element("detalle");
        detalleElement.addContent(new Element("idDetalle").setText(detalle.getIdDetalle() != null ? detalle.getIdDetalle() : ""));
        detalleElement.addContent(new Element("cantidad").setText(String.valueOf(detalle.getCantidad())));
        detalleElement.addContent(new Element("observaciones").setText(detalle.getObservaciones() != null ? detalle.getObservaciones() : ""));
        detalleElement.addContent(new Element("tipoDetalle").setText(detalle.getTipoDetalle() != null ? detalle.getTipoDetalle() : ""));
        detalleElement.addContent(new Element("estadoDetalle").setText(detalle.getEstado() != null ? detalle.getEstado() : ""));

        // Guardar Servicios dentro del detalle
        if (detalle.getServicio() != null && !detalle.getServicio().isEmpty()) {
            Element serviciosElement = new Element("servicios");
            for (Servicio servicio : detalle.getServicio()) {
                serviciosElement.addContent(crearElementoServicio(servicio));
            }
            detalleElement.addContent(serviciosElement);
        }

        // Guardar Repuestos dentro del detalle
        if (detalle.getRepuesto() != null && !detalle.getRepuesto().isEmpty()) {
            Element repuestosElement = new Element("repuestos");
            for (Repuesto repuesto : detalle.getRepuesto()) {
                repuestosElement.addContent(crearElementoRepuesto(repuesto));
            }
            detalleElement.addContent(repuestosElement);
        }
        return detalleElement;
    }

    /**
     * Método auxiliar para crear un Elemento XML de Servicio desde un objeto Servicio.
     * @param servicio El objeto Servicio a convertir en Elemento XML.
     * @return El Elemento XML del servicio.
     */
    private Element crearElementoServicio(Servicio servicio) {
        Element servicioElement = new Element("servicio");
        servicioElement.addContent(new Element("descripcion").setText(servicio.getDescripcion() != null ? servicio.getDescripcion() : ""));
        servicioElement.addContent(new Element("precio").setText(String.valueOf(servicio.getPrecio())));
        servicioElement.addContent(new Element("costoManoObra").setText(String.valueOf(servicio.getCostoManoObra())));
        return servicioElement;
    }

    /**
     * Método auxiliar para crear un Elemento XML de Repuesto desde un objeto Repuesto.
     * @param repuesto El objeto Repuesto a convertir en Elemento XML.
     * @return El Elemento XML del repuesto.
     */
    private Element crearElementoRepuesto(Repuesto repuesto) {
        Element repuestoElement = new Element("repuesto");
        repuestoElement.addContent(new Element("cantidad").setText(String.valueOf(repuesto.getCantidad())));
        repuestoElement.addContent(new Element("nombreRepuesto").setText(repuesto.getNombreRepuesto() != null ? repuesto.getNombreRepuesto() : ""));
        repuestoElement.addContent(new Element("precio").setText(String.valueOf(repuesto.getPrecio())));
        repuestoElement.addContent(new Element("fuePedido").setText(String.valueOf(repuesto.isFuePedido())));
        return repuestoElement;
    }
}