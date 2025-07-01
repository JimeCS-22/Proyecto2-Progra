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
        String mensaje = null;
        String tipoMensaje = null;
        Vehiculos vehiculo = null;
        List<Cliente> listaClientes = new java.util.ArrayList<>();

        String placaParam = request.getParameter("placa"); 

        try {
            VehiculosXmlData vehiculosData = VehiculosXmlData.abrirDocumento(rutaVehiculosXML, rutaClientesXML);
            ClienteXmlData clientesData = ClienteXmlData.abrirDocumento(rutaClientesXML);
            listaClientes = clientesData.findAll(); 

            if (placaParam != null && !placaParam.trim().isEmpty()) {
                vehiculo = vehiculosData.findByPlaca(placaParam.trim());

                if (vehiculo == null) {
                    mensaje = "Error: El vehículo con placa '" + placaParam + "' no fue encontrado.";
                    tipoMensaje = "error";
                } 
            } else {
                
                mensaje = "Ingrese la placa del vehículo a buscar.";
                tipoMensaje = "info";
            }

        } catch (JDOMException | IOException e) {
            mensaje = "Error al cargar los datos: " + e.getMessage();
            tipoMensaje = "error";
            e.printStackTrace();
        }

        request.setAttribute("vehiculo", vehiculo);
        request.setAttribute("listaClientes", listaClientes);
        request.setAttribute("mensaje", mensaje);
        request.setAttribute("tipoMensaje", tipoMensaje);
        request.setAttribute("placaParam", placaParam); 

        request.getRequestDispatcher("/actualizarVehiculo.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String mensaje = "";
        String tipoMensaje = "";
        Vehiculos vehiculoActualizado = null;
        List<Cliente> listaClientes = new java.util.ArrayList<>();

        String placaOriginal = request.getParameter("placaOriginal");
        String nuevaPlaca = request.getParameter("placa");
        String idClienteSeleccionado = request.getParameter("idCliente");

        try {
            VehiculosXmlData vehiculosData = VehiculosXmlData.abrirDocumento(rutaVehiculosXML, rutaClientesXML);
            ClienteXmlData clientesData = ClienteXmlData.abrirDocumento(rutaClientesXML);
            listaClientes = clientesData.findAll();

            if (placaOriginal != null && !placaOriginal.trim().isEmpty() &&
                nuevaPlaca != null && !nuevaPlaca.trim().isEmpty() &&
                idClienteSeleccionado != null && !idClienteSeleccionado.trim().isEmpty()) {

                Cliente clienteAsociado = clientesData.findById(idClienteSeleccionado.trim());

                if (clienteAsociado != null) {
                    vehiculoActualizado = new Vehiculos(nuevaPlaca.trim(), clienteAsociado);

                    boolean actualizado = vehiculosData.actualizarVehiculo(placaOriginal.trim(), vehiculoActualizado);

                    if (actualizado) {
                        mensaje = "Vehículo con placa '" + placaOriginal.trim() + "' actualizado con éxito a '" + nuevaPlaca.trim() + "'.";
                        tipoMensaje = "success";
                        
                        vehiculoActualizado = vehiculosData.findByPlaca(nuevaPlaca.trim());
                        
                        placaOriginal = nuevaPlaca.trim();
                    } else {
                        mensaje = "Error: No se pudo actualizar el vehículo. La nueva placa '" + nuevaPlaca.trim() + "' ya está registrada o hubo otro problema.";
                        tipoMensaje = "error";
                        
                        vehiculoActualizado = new Vehiculos(nuevaPlaca.trim(), clienteAsociado); 
                    }
                } else {
                    mensaje = "Error: El cliente seleccionado no fue encontrado. Por favor, asegúrese de que el cliente exista.";
                    tipoMensaje = "error";
                    
                    vehiculoActualizado = vehiculosData.findByPlaca(placaOriginal.trim());
                    
                    
                }
            } else {
                mensaje = "Error: Todos los campos (Placa, Cliente Propietario) son obligatorios.";
                tipoMensaje = "error";
               
                if (placaOriginal != null && !placaOriginal.trim().isEmpty()) {
                    vehiculoActualizado = vehiculosData.findByPlaca(placaOriginal.trim());
                }
            }

        } catch (JDOMException | IOException e) {
            mensaje = "Error al operar con los archivos XML: " + e.getMessage();
            tipoMensaje = "error";
            e.printStackTrace();
            
            if (placaOriginal != null && !placaOriginal.trim().isEmpty()) {
                try {
                    VehiculosXmlData vehiculosData = VehiculosXmlData.abrirDocumento(rutaVehiculosXML, rutaClientesXML);
                    vehiculoActualizado = vehiculosData.findByPlaca(placaOriginal.trim());
                } catch (JDOMException | IOException ex) {
                    ex.printStackTrace(); 
                }
            }
        }

        request.setAttribute("vehiculo", vehiculoActualizado);
        request.setAttribute("listaClientes", listaClientes);
        request.setAttribute("mensaje", mensaje);
        request.setAttribute("tipoMensaje", tipoMensaje);
        request.setAttribute("placaParam", (vehiculoActualizado != null) ? vehiculoActualizado.getPlaca() : nuevaPlaca);

        request.getRequestDispatcher("/actualizarVehiculo.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet para la actualización de vehículos";
    } 
}
