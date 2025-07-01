/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package cr.ac.ucr.paraiso.proyecto2.progra.servlets;

import cr.ac.ucr.paraiso.proyecto2.progra.data.ClienteXmlData;
import cr.ac.ucr.paraiso.proyecto2.progra.data.VehiculosXmlData;
import cr.ac.ucr.paraiso.proyecto2.progra.domain.Cliente;
import cr.ac.ucr.paraiso.proyecto2.progra.domain.Vehiculos;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
import org.jdom2.JDOMException;

/**
 *
 * @author jimen
 */
@WebServlet(name = "ActualizarVehiculoServlet", urlPatterns = {"/ActualizarVehiculoServlet"})
public class ActualizarVehiculoServlet extends HttpServlet {

    private String rutaVehiculosXML;
    private String rutaClientesXML;

    @Override
    public void init() throws ServletException {
        String rutaBaseXML = getServletContext().getRealPath("WEB-INF") + File.separator + "archivos" + File.separator;
        this.rutaVehiculosXML = rutaBaseXML + "vehiculos.xml";
        this.rutaClientesXML = rutaBaseXML + "clientes.xml";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String placa = request.getParameter("placa");
        Vehiculos vehiculoAEditar = null;
        String mensaje = "";
        String tipoMensaje = "";

        List<Cliente> clientesDisponibles = new java.util.ArrayList<>();
        try {
            ClienteXmlData clientesData = ClienteXmlData.abrirDocumento(rutaClientesXML);
            clientesDisponibles = clientesData.findAll();
        } catch (JDOMException | IOException e) {
            mensaje = (mensaje != null && !mensaje.isEmpty() ? mensaje + "<br>" : "") +
                      "Advertencia: No se pudieron cargar los clientes para la selección. " + e.getMessage();
            tipoMensaje = "error";
            e.printStackTrace();
        }
        request.setAttribute("clientesDisponibles", clientesDisponibles); // Pasa la lista de clientes al JSP

        if (placa != null && !placa.trim().isEmpty()) {
            try {
                VehiculosXmlData vehiculosData = VehiculosXmlData.abrirDocumento(rutaVehiculosXML, rutaClientesXML);
                vehiculoAEditar = vehiculosData.findByPlaca(placa.trim());

                if (vehiculoAEditar == null) {
                    mensaje = "Vehículo con placa '" + placa + "' no encontrado.";
                    tipoMensaje = "error";
                }

            } catch (JDOMException | IOException e) {
                mensaje = "Error al cargar los datos del vehículo para edición: " + e.getMessage();
                tipoMensaje = "error";
                e.printStackTrace();
            }
        } else {
            mensaje = "No se especificó la placa del vehículo a editar.";
            tipoMensaje = "error";
        }

        request.setAttribute("vehiculoAEditar", vehiculoAEditar);
        request.setAttribute("mensaje", mensaje);
        request.setAttribute("tipoMensaje", tipoMensaje);

        request.getRequestDispatcher("/actualizarVehiculo.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String mensaje = "";
        String tipoMensaje = "";

        String placaActual = request.getParameter("placaActual");
        String nuevaPlaca = request.getParameter("nuevaPlaca");
        String idClienteSeleccionado = request.getParameter("idCliente");

        if (placaActual != null && !placaActual.trim().isEmpty() &&
            nuevaPlaca != null && !nuevaPlaca.trim().isEmpty() &&
            idClienteSeleccionado != null && !idClienteSeleccionado.trim().isEmpty()) {

            try {
                VehiculosXmlData vehiculosData = VehiculosXmlData.abrirDocumento(rutaVehiculosXML, rutaClientesXML);
                ClienteXmlData clientesData = ClienteXmlData.abrirDocumento(rutaClientesXML);

                Cliente clienteAsociado = clientesData.findById(idClienteSeleccionado.trim());

                if (clienteAsociado != null) {
                    Vehiculos vehiculoActualizado = new Vehiculos(nuevaPlaca.trim(), clienteAsociado);

                    boolean actualizado = vehiculosData.actualizarVehiculo(placaActual.trim(), vehiculoActualizado);

                    if (actualizado) {
                        mensaje = "Vehículo '" + placaActual.trim() + "' actualizado a '" + nuevaPlaca.trim() + "' con éxito.";
                        tipoMensaje = "success";
                    } else {
                        mensaje = "Error: No se pudo encontrar el vehículo con placa '" + placaActual.trim() + "' para actualizar o la nueva placa ya existe.";
                        tipoMensaje = "error";
                    }
                } else {
                    mensaje = "Error: El cliente seleccionado para el vehículo no fue encontrado.";
                    tipoMensaje = "error";
                }

            } catch (JDOMException | IOException e) {
                mensaje = "Error al operar con los archivos XML durante la actualización: " + e.getMessage();
                tipoMensaje = "error";
                e.printStackTrace();
            }
        } else {
            mensaje = "Error: Faltan datos necesarios para la actualización del vehículo.";
            tipoMensaje = "error";
        }

        
        response.sendRedirect(request.getContextPath() + "/ListarVehiculosServlet?mensaje=" +
                java.net.URLEncoder.encode(mensaje, "UTF-8") + "&tipoMensaje=" + tipoMensaje);
    }

    @Override
    public String getServletInfo() {
        return "Servlet para la edición de vehículos";
    }

}
