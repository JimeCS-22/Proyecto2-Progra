/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package cr.ac.ucr.paraiso.proyecto2.progra.servlets;

import cr.ac.ucr.paraiso.proyecto2.progra.data.ClienteXmlData;
import cr.ac.ucr.paraiso.proyecto2.progra.domain.*;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.JDOMException;

/**
 * @author Camila M
 */
@WebServlet(name = "InsertarClienteServlet", urlPatterns = {"/InsertarClienteServlet"})
public class InsertarClienteServlet extends HttpServlet {
   private List<Cliente> clientes;

     @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
       try {
           Cliente cliente = new Cliente();
           cliente.setIdCliente(req.getParameter("idCliente"));
           cliente.setNombre(req.getParameter("nombre"));
           cliente.setTelefono(req.getParameter("telefono"));
           cliente.setCelular(req.getParameter("celular"));
           cliente.setDireccion(req.getParameter("direccion"));
           
           //colocar la ruta valida donde esta el xml
           String rutaRelativa = "/WEB-INF/archivos/clientes.xml";

           String rutaReal = getServletContext().getRealPath(rutaRelativa);
           
           ClienteXmlData data = new ClienteXmlData(rutaReal);
           data.insertarCliente(cliente);
  
           clientes.add(cliente);
           req.getRequestDispatcher("/ver_cliente.jsp?idCliente="+cliente.getIdCliente() +
                   "&nombre="+cliente.getNombre() + "&telefono=" + cliente.getTelefono() +"&celular="+
                   cliente.getCelular() + "&direccion=" + cliente.getDireccion()).forward(req, resp);
           
       }catch (JDOMException ex) {
           Logger.getLogger(InsertarClienteServlet.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
   
@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      
       try { 
        ServletContext ctx = req.getServletContext();// Cargamos los clientes desde el XML
        String fullPathL = ctx.getRealPath("/WEB-INF/archivos/clientes.xml");
        ClienteXmlData data = new ClienteXmlData(fullPathL);
        clientes = data.findAll();
        req.setAttribute("clientes", clientes);  //Ponemos siempre la lista de clientes en el request

        }catch (JDOMException ex) {
                log("Error cargando clientes", ex);
            }

        // 4) Forward al JSP que mostrará tabla
        req.getRequestDispatcher("/insertarCliente.jsp").forward(req, resp);
    }
}
