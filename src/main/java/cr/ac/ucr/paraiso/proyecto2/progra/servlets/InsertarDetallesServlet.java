/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package cr.ac.ucr.paraiso.proyecto2.progra.servlets;

import cr.ac.ucr.paraiso.proyecto2.progra.domain.DetalleOrdenTrabajo;
import cr.ac.ucr.paraiso.proyecto2.progra.data.DetalleXmlData;
import cr.ac.ucr.paraiso.proyecto2.progra.domain.Repuesto;
import cr.ac.ucr.paraiso.proyecto2.progra.domain.Servicio;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.JDOMException;

/**
 *
 * @author Camila
 */
@WebServlet(name="/InsertarDetallesServlet",urlPatterns = {"/InsertarDetallesServlet"})
public class InsertarDetallesServlet extends HttpServlet {
    private String rutaDetallesXML; 
    private DetalleXmlData data;
    @Override
    public void init() throws ServletException {
      
        try {
            String rutaBaseXML = getServletContext().getRealPath("WEB-INF") + File.separator + "archivos" + File.separator;
            this.rutaDetallesXML = rutaBaseXML + "detalles.xml";
            this.data = new DetalleXmlData(rutaDetallesXML);
            DetalleXmlData.abrirDocumento(rutaDetallesXML);
            
        } catch (JDOMException ex) {
            Logger.getLogger(InsertarDetallesServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InsertarDetallesServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
    resp.sendRedirect(req.getContextPath() + "/gestionDetalles.jsp");
}
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Leer tipo detalle
        String tipo = req.getParameter("tipoDetalle"); // "Servicios" o "Repuestos"
        
        // Crear el objeto detalle
        DetalleOrdenTrabajo detalle = new DetalleOrdenTrabajo();
        detalle.setTipoDetalle(tipo);

        
     if ("Repuestos".equals(tipo)) {
    // Campos repuesto
    String nombre = req.getParameter("nombreRepuesto");
    String cantidadStr = req.getParameter("cantidad");
    String precioStr = req.getParameter("precioRepuesto");
    String pedido = req.getParameter("pedido");

    if (nombre == null || nombre.trim().isEmpty() ||
        cantidadStr == null || cantidadStr.trim().isEmpty() ||
        precioStr == null || precioStr.trim().isEmpty()) {

        req.setAttribute("errorMsg", "Todos los campos de repuesto son obligatorios.");
        req.getRequestDispatcher("/gestionDetalles.jsp").forward(req, resp);
        return;
    }

    try {
        Repuesto e = new Repuesto();
        e.setNombreRepuesto(nombre.trim());
        e.setCantidad(Integer.parseInt(cantidadStr.trim()));
        e.setPrecio(Double.parseDouble(precioStr.trim()));
        e.setFuePedido("Si".equals(pedido));
        detalle.getRepuesto().add(e);

    } catch (NumberFormatException ex) {
        req.setAttribute("errorMsg", "Cantidad o precio inválidos. Deben ser números.");
        req.getRequestDispatcher("/gestionDetalles.jsp").forward(req, resp);
        return;
    }

} else {
    // Campos servicio
    String descripcion = req.getParameter("servicioRequerido");
    String precioStr = req.getParameter("precioServicio");
    String costoManoObraStr = req.getParameter("costoManoObra");

    if (descripcion == null || descripcion.trim().isEmpty() ||
        precioStr == null || precioStr.trim().isEmpty() ||
        costoManoObraStr == null || costoManoObraStr.trim().isEmpty()) {

        req.setAttribute("errorMsg", "Todos los campos de servicio son obligatorios.");
        req.getRequestDispatcher("/gestionDetalles.jsp").forward(req, resp);
        return;
    }

    try {
        Servicio s = new Servicio();
        s.setDescripcion(descripcion.trim());
        s.setPrecio(Double.parseDouble(precioStr.trim()));
        s.setCostoManoObra(Double.parseDouble(costoManoObraStr.trim()));
        detalle.getServicio().add(s);

    } catch (NumberFormatException ex) {
        req.setAttribute("errorMsg", "Precio o costo mano de obra inválidos. Deben ser números.");
        req.getRequestDispatcher("/gestionDetalles.jsp").forward(req, resp);
        return;
    }
}
        try {
            // Insertar en el XML
            //DetalleXmlData.abrirDocumento(rutaDetallesXML);
            data.insertarDetalle(detalle);
            
            // Redirigir o forward al JSP de confirmación/listado
            resp.sendRedirect(req.getContextPath() + "/detallesLista.jsp");
        } catch (Exception e) {
            // Log y mostrar error simple
            e.printStackTrace();
            req.setAttribute("errorMsg", "No se pudo insertar el detalle: " + e.getMessage());
            req.getRequestDispatcher("/errorPage.jsp").forward(req, resp);
        }
    }
  
}
