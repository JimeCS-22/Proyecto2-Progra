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
@WebServlet(name = "insertarVehiculoServlet", urlPatterns = {"/insertarVehiculoServlet"})
public class insertarVehiculoServlet extends HttpServlet {

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
        request.getRequestDispatcher("/insertarVehiculo.jsp").forward(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String mensaje = "";
        String tipoMensaje = ""; 

        try {
           
            VehiculosXmlData vehiculosData = VehiculosXmlData.abrirDocumento(rutaVehiculosXML);
            ClienteXmlData clientesData = ClienteXmlData.abrirDocumento(rutaClientesXML);

          
            String placa = request.getParameter("placa");
            String idClienteSeleccionado = request.getParameter("idCliente");

            
            if (placa != null && !placa.trim().isEmpty() && idClienteSeleccionado != null && !idClienteSeleccionado.trim().isEmpty()) {
               
                Cliente clienteAsociado = null;
                List<Cliente> todosLosClientes = clientesData.findAll();
                for (Cliente c : todosLosClientes) {
                    if (c.getIdCliente().equals(idClienteSeleccionado)) {
                        clienteAsociado = c;
                        break;
                    }
                }

                if (clienteAsociado != null) {
                    
                    boolean placaExistente = false;
                    List<Vehiculos> vehiculosExistentes = vehiculosData.findAll();
                    for (Vehiculos v : vehiculosExistentes) {
                        if (v.getPlaca().equalsIgnoreCase(placa.trim())) { 
                            placaExistente = true;
                            break;
                        }
                    }

                    if (!placaExistente) {
                        // Crear el nuevo objeto Vehiculos y guardarlo
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
