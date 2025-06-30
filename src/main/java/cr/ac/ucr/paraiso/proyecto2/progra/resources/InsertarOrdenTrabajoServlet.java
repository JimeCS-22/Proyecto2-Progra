package cr.ac.ucr.paraiso.proyecto2.progra.resources;

import cr.ac.ucr.paraiso.proyecto2.progra.data.OrdenTrabajoXmlData;
import cr.ac.ucr.paraiso.proyecto2.progra.domain.*;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import org.jdom2.JDOMException;

@WebServlet(name = "InsertarOrdenTrabajoServlet", urlPatterns = {"/InsertarOrdenTrabajoServlet"})
public class InsertarOrdenTrabajoServlet extends HttpServlet {

    private String RUTA_XML_ORDENES;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext context = getServletContext();
        RUTA_XML_ORDENES = context.getRealPath("/WEB-INF/archivos/ordenTrabajo.xml");
        
        File xmlFile = new File(RUTA_XML_ORDENES);
        File parentDir = xmlFile.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }
        System.out.println("Ruta XML Órdenes: " + RUTA_XML_ORDENES);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        String mensaje = "";
        String idOrdenGenerado = null;

        try {
            // Validar ruta primero
            if (RUTA_XML_ORDENES == null) {
                throw new ServletException("Error: Ruta del archivo XML no configurada");
            }

            // Obtener parámetros
            String id = request.getParameter("id");
            String descripcionSolicitud = request.getParameter("descripcionSolicitud");
            String estado = request.getParameter("estado");
            String placaVehiculo = request.getParameter("vehiculoPlaca");
            
            // Validar campos obligatorios
            if (descripcionSolicitud == null || descripcionSolicitud.trim().isEmpty()) {
            request.setAttribute("errorDescripcion", true); // Bandera para el JSP
            throw new IllegalArgumentException("Complete los datos requeridos");
        }
            
            if (estado == null || estado.trim().isEmpty()) {
                throw new IllegalArgumentException("El estado de la orden es requerido");
            }
            
            if (placaVehiculo == null || placaVehiculo.trim().isEmpty()) {
                throw new IllegalArgumentException("Debe seleccionar un vehículo");
            }
            
            // Procesamiento de fechas
            LocalDate fechaIngreso = parsearFecha(request.getParameter("fechaIngreso"), "Fecha de Ingreso");
            LocalDate fechaDevolucionEstimada = parsearFecha(request.getParameter("fechaDevolucionEstimada"), "Fecha de Devolución Estimada");
            
            // Validación lógica de fechas
            if (fechaDevolucionEstimada.isBefore(fechaIngreso)) {
                throw new IllegalArgumentException("La fecha de devolución debe ser posterior a la de ingreso");
            }

            // Crear y configurar la orden
            OrdenTrabajo orden = new OrdenTrabajo();
            orden.setId(id != null ? id : generarNuevoId());
            orden.setDescripcionSolicitud(descripcionSolicitud.trim());
            orden.setEstado(estado.trim());
            orden.setFechaIngreso(fechaIngreso);
            orden.setFechaDevolucionEstimada(fechaDevolucionEstimada);
            
            Vehiculos vehiculo = new Vehiculos();
            vehiculo.setPlaca(placaVehiculo.trim());
            orden.setVehiculo(vehiculo);
            orden.setDetalles(new ArrayList<>());

            // Operaciones con el DAO
            OrdenTrabajoXmlData ordenDAO = new OrdenTrabajoXmlData(RUTA_XML_ORDENES);
            
            // Insertar la nueva orden
            OrdenTrabajo ordenGuardada = ordenDAO.insertarOrden(orden);
            if (ordenGuardada != null) {
                mensaje = "Orden guardada exitosamente. ID: " + ordenGuardada.getId();
                idOrdenGenerado = ordenGuardada.getId();
            } else {
                mensaje = "Error: No se pudo guardar la Orden de Trabajo";
            }

        } catch (Exception ex) {
            mensaje = manejarExcepcion(ex);
            System.err.println("Error en InsertarOrdenTrabajoServlet: " + ex.getMessage());
            ex.printStackTrace();
            
            // Guardar los valores ingresados para mostrarlos nuevamente en el formulario
            request.setAttribute("valoresPrevios", request.getParameterMap());
        }

        request.setAttribute("mensaje", mensaje);
        
        if (idOrdenGenerado != null) {
            response.sendRedirect("gestionDetalles.jsp?ordenId=" + idOrdenGenerado);
        } else {
            request.getRequestDispatcher("formOrdenTrabajo.jsp").forward(request, response);
        }
    }

    private String generarNuevoId() {
        return "OT-" + System.currentTimeMillis() + "-" + (int)(Math.random() * 1000);
    }

    private LocalDate parsearFecha(String fechaStr, String fieldName) {
        if (fechaStr == null || fechaStr.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " es requerida");
        }
        try {
            return LocalDate.parse(fechaStr, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato inválido para " + fieldName + ". Use AAAA-MM-DD");
        }
    }

    private String manejarExcepcion(Exception ex) {
        if (ex instanceof IllegalArgumentException || ex instanceof DateTimeParseException) {
            return ex.getMessage(); // Mostrar directamente el mensaje de validación
        }
        return "Error al procesar la orden: " + ex.getMessage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}