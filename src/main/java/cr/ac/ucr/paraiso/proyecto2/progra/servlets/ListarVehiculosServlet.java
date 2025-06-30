/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package cr.ac.ucr.paraiso.proyecto2.progra.servlets;

import cr.ac.ucr.paraiso.proyecto2.progra.data.VehiculosXmlData;
import cr.ac.ucr.paraiso.proyecto2.progra.domain.Vehiculos;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.JDOMException;

/**
 *
 * @author Jimena
 */
@WebServlet(name = "ListarVehiculosServlet", urlPatterns = {"/ListarVehiculosServlet"})
public class ListarVehiculosServlet extends HttpServlet {

     private String rutaVehiculosXML;
    private String rutaClientesXML;

    @Override
    public void init() throws ServletException {
        String rutaBaseXML = getServletContext().getRealPath("WEB-INF") + File.separator + "archivos" + File.separator;
        this.rutaVehiculosXML = rutaBaseXML + "vehiculos.xml";
        this.rutaClientesXML = rutaBaseXML + "clientes.xml";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String mensaje = request.getParameter("mensaje"); 
        String tipoMensaje = request.getParameter("tipoMensaje"); 
        List<Vehiculos> listaVehiculos = new ArrayList<>();

        try {
            
            VehiculosXmlData vehiculosData = VehiculosXmlData.abrirDocumento(rutaVehiculosXML, rutaClientesXML);

            listaVehiculos = vehiculosData.findAll();

        } catch (JDOMException | IOException e) {
            mensaje = "Error al cargar la lista de vehículos: " + e.getMessage();
            tipoMensaje = "error";
            e.printStackTrace();
        }

        request.setAttribute("listaVehiculos", listaVehiculos);
      
        if (request.getAttribute("mensaje") == null) {
            request.setAttribute("mensaje", mensaje);
            request.setAttribute("tipoMensaje", tipoMensaje);
        }

        request.getRequestDispatcher("/listadoVehiculos.jsp").forward(request, response);
        
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      
        doGet(request, response);
    }


    @Override
    public String getServletInfo() {
        return "Servlet para el listado de vehículos";
    }


}
