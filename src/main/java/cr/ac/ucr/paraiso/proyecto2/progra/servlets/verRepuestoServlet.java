/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package cr.ac.ucr.paraiso.proyecto2.progra.servlets;

import cr.ac.ucr.paraiso.proyecto2.progra.data.RepuestosXmlData;
import cr.ac.ucr.paraiso.proyecto2.progra.domain.Repuesto;
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
@WebServlet(name = "verRepuestoServlet", urlPatterns = {"/verRepuestoServlet"})
public class verRepuestoServlet extends HttpServlet {

    private RepuestosXmlData repuestosData;
    private String rutaArchivos;

    @Override
    public void init() throws ServletException {
        super.init();
        // La ruta de los archivos XML dentro de WEB-INF/archivos
        rutaArchivos = getServletContext().getRealPath("WEB-INF") + File.separator + "archivos" + File.separator;
        try {
            // Asegurarse de que el directorio exista
            File directorio = new File(rutaArchivos);
            if (!directorio.exists()) {
                directorio.mkdirs(); // Crea el directorio y los padres si no existen
            }
            // Inicializar RepuestosXmlData con la ruta completa del archivo XML
            repuestosData = new RepuestosXmlData(rutaArchivos + "repuestos.xml");
        } catch (JDOMException | IOException ex) {
            // Manejar la excepción
            throw new ServletException("Error al inicializar RepuestosXmlData", ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Repuesto> repuestos = repuestosData.findAll();
            request.setAttribute("repuestos", repuestos);
            request.getRequestDispatcher("/verRepuestos.jsp").forward(request, response);
        } catch (Exception e) {
            // Manejo de errores
            request.setAttribute("mensaje", "Error al cargar los repuestos: " + e.getMessage());
            request.setAttribute("tipoMensaje", "error");
            request.getRequestDispatcher("/verRepuestos.jsp").forward(request, response);
            e.printStackTrace();
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet para ver todos los repuestos desde un archivo XML";
    }
    
}
