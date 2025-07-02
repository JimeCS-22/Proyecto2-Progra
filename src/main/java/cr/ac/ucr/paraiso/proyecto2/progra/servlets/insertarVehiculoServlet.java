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
@WebServlet(name = "InsertarVehiculoServlet", urlPatterns = {"/InsertarVehiculoServlet"})
public class InsertarVehiculoServlet extends HttpServlet {

   private String rutaBaseXML;
    private String rutaVehiculosXML;
    private String rutaClientesXML;

    @Override
    public void init() throws ServletException {
        this.rutaBaseXML = getServletContext().getRealPath("WEB-INF") + File.separator + "archivos" + File.separator;
        this.rutaVehiculosXML = this.rutaBaseXML + "vehiculos.xml";
        this.rutaClientesXML = this.rutaBaseXML + "clientes.xml";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String mensaje = null; // Inicializar a null
        String tipoMensaje = null; // Inicializar a null
        List<Cliente> listaClientes = new java.util.ArrayList<>(); // Asegurarse de que la lista no sea null

        try {
            // Inicializar ClienteXmlData para cargar la lista de clientes
            ClienteXmlData clientesData = ClienteXmlData.abrirDocumento(rutaClientesXML);
            listaClientes = clientesData.findAll();

            // Si hay un mensaje de error previo (ej. de un doPost fallido), recupéralo
            String paramMensaje = request.getParameter("mensaje");
            String paramTipoMensaje = request.getParameter("tipoMensaje");
            if (paramMensaje != null && !paramMensaje.isEmpty()) {
                mensaje = paramMensaje;
                tipoMensaje = paramTipoMensaje;
            }

        } catch (JDOMException | IOException e) {
            mensaje = "Error al cargar los clientes para el formulario: " + e.getMessage();
            tipoMensaje = "error";
            e.printStackTrace(); // Imprime el error en la consola del servidor
        }

        // Siempre establece los atributos antes de hacer forward, incluso si son null/vacíos
        request.setAttribute("listaClientes", listaClientes);
        request.setAttribute("mensaje", mensaje);
        request.setAttribute("tipoMensaje", tipoMensaje);

        request.getRequestDispatcher("/insertarVehiculo.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String mensaje = "";
        String tipoMensaje = "";

        try {
            VehiculosXmlData vehiculosData = VehiculosXmlData.abrirDocumento(rutaVehiculosXML, rutaClientesXML);
            ClienteXmlData clientesData = ClienteXmlData.abrirDocumento(rutaClientesXML);

            String placa = request.getParameter("placa");
            String idClienteSeleccionado = request.getParameter("idCliente");

            if (placa != null && !placa.trim().isEmpty() && idClienteSeleccionado != null && !idClienteSeleccionado.trim().isEmpty()) {

                Cliente clienteAsociado = null;
                clienteAsociado = clientesData.findById(idClienteSeleccionado.trim());

                if (clienteAsociado != null) {
                    boolean placaExistente = false;
                    Vehiculos vehiculoExistente = vehiculosData.findByPlaca(placa.trim());
                    if (vehiculoExistente != null) {
                        placaExistente = true;
                    }

                    if (!placaExistente) {
                        Vehiculos nuevoVehiculo = new Vehiculos(placa.trim(), clienteAsociado);
                        vehiculosData.insertarVehiculo(nuevoVehiculo);
                        mensaje = "Vehículo '" + placa.trim() + "' registrado con éxito para el cliente: " + clienteAsociado.getNombre();
                        tipoMensaje = "success";
                    } else {
                        mensaje = "Error: Ya existe un vehículo registrado con la placa '" + placa.trim() + "'.";
                        tipoMensaje = "error";
                    }
                } else {
                    mensaje = "Error: El cliente seleccionado no fue encontrado. Por favor, asegúrese de que el cliente exista.";
                    tipoMensaje = "error";
                }
            } else {
                mensaje = "Error: La placa del vehículo y el cliente propietario son campos obligatorios.";
                tipoMensaje = "error";
            }

        } catch (JDOMException | IOException e) {
            mensaje = "Error al operar con los archivos XML: " + e.getMessage();
            tipoMensaje = "error";
            e.printStackTrace();
        }

        request.setAttribute("mensaje", mensaje);
        request.setAttribute("tipoMensaje", tipoMensaje);
        request.getRequestDispatcher("/insertarVehiculo.jsp").forward(request, response);

    }

    @Override
    public String getServletInfo() {
        return "Servlet para la inserción de vehículos";
    }
    
}
