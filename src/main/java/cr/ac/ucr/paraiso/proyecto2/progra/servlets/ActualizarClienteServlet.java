/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package cr.ac.ucr.paraiso.proyecto2.progra.servlets;

import cr.ac.ucr.paraiso.proyecto2.progra.data.ClienteXmlData;
import cr.ac.ucr.paraiso.proyecto2.progra.domain.Cliente;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.File;
import java.io.IOException;

@WebServlet(name = "ActualizarClienteServlet", urlPatterns = {"/ActualizarClienteServlet"})
public class ActualizarClienteServlet extends HttpServlet {
    private ClienteXmlData clienteData;
    private String rutaBaseXML;
    private String rutaVehiculosXML;
    private String rutaClientesXML;
    
    @Override
    public void init() throws ServletException {
        this.rutaBaseXML = getServletContext().getRealPath("WEB-INF") + File.separator + "archivos" + File.separator;
        this.rutaClientesXML = this.rutaBaseXML + "clientes.xml";
    
        try {
           clienteData = new ClienteXmlData(rutaClientesXML);
            ClienteXmlData.abrirDocumento(rutaClientesXML); // método que cargue el XML
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

      // Buscar cliente por ID
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idCliente = request.getParameter("idCliente");

        if (idCliente != null && !idCliente.isEmpty()) {
            Cliente cliente = clienteData.findById(idCliente);
            if (cliente != null) {
                request.setAttribute("cliente", cliente);
            } else {
                request.setAttribute("mensaje", "Cliente no encontrado con el ID: " + idCliente);
            }
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/actualizarCliente.jsp");
        dispatcher.forward(request, response);
    }

    // Actualizar solo los datos, sin modificar el ID
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String idCliente = request.getParameter("idCliente");
        String nombre = request.getParameter("nombre");
        String telefono = request.getParameter("telefono");
        String celular = request.getParameter("celular");
        String direccion = request.getParameter("direccion");

        Cliente clienteExistente = clienteData.findById(idCliente);

        if (clienteExistente != null) {
            // Solo se actualizan los campos que sí pueden cambiar
            clienteExistente.setNombre(nombre);
            clienteExistente.setTelefono(telefono);
            clienteExistente.setCelular(celular);
            clienteExistente.setDireccion(direccion);

            boolean actualizado = clienteData.actualizarCliente(idCliente, clienteExistente);

            if (actualizado) {
                request.setAttribute("mensajeExito", "Cliente actualizado correctamente.");
            } else {
                request.setAttribute("mensajeError", "Error al actualizar el cliente.");
            }
            request.setAttribute("cliente", clienteExistente);
        } else {
            request.setAttribute("mensajeError", "Cliente no encontrado.");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/actualizarCliente.jsp");
        dispatcher.forward(request, response);
    }
}

