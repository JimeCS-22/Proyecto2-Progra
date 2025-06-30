/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package cr.ac.ucr.paraiso.proyecto2.progra.servlets;

import cr.ac.ucr.paraiso.proyecto2.progra.data.ClienteXmlData;
import cr.ac.ucr.paraiso.proyecto2.progra.domain.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletContext;
import org.jdom2.JDOMException;

/**
 *
 * @author Camila M
 */
@WebServlet(name = "InsertarClienteServlet", urlPatterns = {"/InsertarClienteServlet"})
public class InsertarClienteServlet extends HttpServlet {
   private List<Cliente> clientes;

     @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(req.getParameter("idCliente"));
        cliente.setNombre(req.getParameter("nombre"));
        cliente.setTelefono(req.getParameter("telefono"));
        cliente.setCelular(req.getParameter("celular"));
        cliente.setDireccion(req.getParameter("direccion"));
     
        
        // deberiamos enviar el empleado para insertar a la b.d.
        clientes.add(cliente);
        req.getRequestDispatcher("/ver_cliente.jsp?idCliente="+cliente.getIdCliente() +
                "&nombre="+cliente.getNombre() + "&telefono=" + cliente.getTelefono() +"&celular="+ 
                cliente.getCelular() + "&direccion=" + cliente.getDireccion()).forward(req, resp);
    }
   
@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1) Ponemos siempre la lista de clientes en el request
        req.setAttribute("clientes", clientes);

        // 2) Vemos si llegó un parámetro idAutor para filtrar libros
        String idParam = req.getParameter("idCliente");
        if (idParam != null && !idParam.isEmpty()) {
            int idAutor = Integer.parseInt(idParam);
            List<Cliente> lista = new LinkedList<>();

            // Cargamos los libros desde el XML
            ServletContext ctx = req.getServletContext();
            String fullPathL = ctx.getRealPath("/WEB-INF/archivos/clientes.xml");
            try {
                ClienteXmlData data = ClienteXmlData.abrirDocumento(fullPathL);
                lista = data.findAll();
            } catch (JDOMException ex) {
                log("Error cargando libros", ex);
            }

            // 3) Asignamos la lista de libros al request
            req.setAttribute("clientes", lista);
        }

        // 4) Forward al JSP que mostrará el select + tabla
        req.getRequestDispatcher("/insertarCliente.jsp").forward(req, resp);
    }
  
     

  
}
