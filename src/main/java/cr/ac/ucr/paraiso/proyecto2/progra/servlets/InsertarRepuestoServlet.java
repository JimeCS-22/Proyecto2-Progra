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
import org.jdom2.JDOMException;

/**
 *
 * @author jimen
 */
@WebServlet(name = "InsertarRepuestoServlet", urlPatterns = {"/InsertarRepuestoServlet"})
public class InsertarRepuestoServlet extends HttpServlet {

   private RepuestosXmlData repuestosData;
    private String rutaArchivos;

    @Override
    public void init() throws ServletException {
        super.init();
        rutaArchivos = getServletContext().getRealPath("WEB-INF") + File.separator + "archivos" + File.separator;
        try {
            File directorio = new File(rutaArchivos);
            if (!directorio.exists()) {
                directorio.mkdirs();
            }
            repuestosData = new RepuestosXmlData(rutaArchivos + "repuestos.xml");
        } catch (JDOMException | IOException ex) {
            throw new ServletException("Error al inicializar RepuestosXmlData", ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.getRequestDispatcher("/insertarRepuesto.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombreRepuesto = request.getParameter("nombreRepuesto");
        String cantidadStr = request.getParameter("cantidad");
        String precioStr = request.getParameter("precio");
        boolean fuePedido = request.getParameter("fuePedido") != null;

        String mensaje = "";
        String tipoMensaje = "";

        try {
            int cantidad = Integer.parseInt(cantidadStr);
            double precio = Double.parseDouble(precioStr);

            Repuesto nuevoRepuesto = new Repuesto(cantidad, nombreRepuesto, precio, fuePedido);

            Repuesto repuestoExistente = repuestosData.findByNombre(nombreRepuesto);
            if (repuestoExistente != null) {
                mensaje = "Error: Ya existe un repuesto con el nombre '" + nombreRepuesto + "'.";
                tipoMensaje = "error";
            } else {
                repuestosData.insertarRepuesto(nuevoRepuesto);
                mensaje = "Repuesto '" + nombreRepuesto + "' insertado exitosamente.";
                tipoMensaje = "success";
            }

        } catch (NumberFormatException e) {
            mensaje = "Error de formato: Asegúrate de ingresar números válidos para cantidad y precio.";
            tipoMensaje = "error";
            e.printStackTrace();
        } catch (IOException e) {
            mensaje = "Error de I/O al guardar el repuesto.";
            tipoMensaje = "error";
            e.printStackTrace();
        } finally {
            request.setAttribute("mensaje", mensaje);
            request.setAttribute("tipoMensaje", tipoMensaje);
            request.getRequestDispatcher("/insertarRepuesto.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet para insertar repuestos en un archivo XML";
    }
    
}
