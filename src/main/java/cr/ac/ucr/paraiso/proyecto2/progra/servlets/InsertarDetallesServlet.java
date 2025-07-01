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

/**
 *
 * @author Camila
 */
@WebServlet(name="/InsertarDetallesServlet",urlPatterns = {"/InsertarDetallesServlet"})
public class InsertarDetallesServlet extends HttpServlet {
    private String rutaDetallesXML; 
    @Override
    public void init() throws ServletException {
      
        String rutaBaseXML = getServletContext().getRealPath("WEB-INF") + File.separator + "archivos" + File.separator;
        this.rutaDetallesXML = rutaBaseXML + "detalles.xml"; 
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
            Repuesto e = new Repuesto();
            e.setNombreRepuesto(req.getParameter("nombreRepuesto"));
            e.setCantidad(Integer.parseInt(req.getParameter("cantidad")));
            e.setPrecio(Double.parseDouble(req.getParameter("precioRepuesto")));
            e.setFuePedido("Si".equals(req.getParameter("pedido")));
            detalle.getRepuesto().add(e);
        } else {
            // Campos servicio
            Servicio s = new Servicio();
            s.setDescripcion(req.getParameter("servicioRequerido"));
            s.setPrecio(Double.parseDouble(req.getParameter("precioServicio")));
            s.setCostoManoObra(Double.parseDouble(req.getParameter("costoManoObra")));
            detalle.getServicio().add(s);
        }

        try {
            // Insertar en el XML
            DetalleXmlData data = new DetalleXmlData(rutaDetallesXML);
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
