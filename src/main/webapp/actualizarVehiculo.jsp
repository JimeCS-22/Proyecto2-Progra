<%-- 
    Document   : actualizarVehiculo
    Created on : 30 jun 2025, 3:57:51 p. m.
    Author     : jimen
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="cr.ac.ucr.paraiso.proyecto2.progra.data.ClienteXmlData"%>
<%@page import="cr.ac.ucr.paraiso.proyecto2.progra.domain.Cliente"%>
<%@page import="cr.ac.ucr.paraiso.proyecto2.progra.domain.Vehiculos"%>
<%@page import="java.util.List"%>
<%@page import="java.io.File"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Actualizar Vehículo</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f4; }
        h1 { color: #333; }
        .container { background-color: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); margin-bottom: 20px; }
        form div { margin-bottom: 10px; }
        label { display: inline-block; width: 150px; font-weight: bold; }
        input[type="text"], select { width: 300px; padding: 8px; border: 1px solid #ddd; border-radius: 4px; }
        button { padding: 10px 15px; background-color: #007bff; color: white; border: none; border-radius: 4px; cursor: pointer; font-size: 16px; }
        button:hover { background-color: #0056b3; }
        .message { margin-top: 15px; padding: 10px; border-radius: 4px; }
        .success { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .error { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        .back-link { display: block; margin-top: 20px; color: #007bff; text-decoration: none; }
        .back-link:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <h1>Actualizar Información del Vehículo</h1>

    <%
        
        Vehiculos vehiculoAEditar = (Vehiculos) request.getAttribute("vehiculoAEditar");
        String mensaje = (String) request.getAttribute("mensaje");
        String tipoMensaje = (String) request.getAttribute("tipoMensaje");
        
        
        String rutaClientesXML = application.getRealPath("WEB-INF/archivos") + File.separator + "clientes.xml";
        List<Cliente> clientesDisponibles = new ArrayList<>();
        try {
            ClienteXmlData clientesData = ClienteXmlData.abrirDocumento(rutaClientesXML);
            clientesDisponibles = clientesData.findAll();
        } catch (Exception e) {
            mensaje = (mensaje != null && !mensaje.isEmpty() ? mensaje + "<br>" : "") + 
                      "Advertencia: No se pudieron cargar los clientes para la selección. " + e.getMessage();
            tipoMensaje = "error";
            e.printStackTrace();
        }
    %>

    <% if (mensaje != null && !mensaje.isEmpty()) { %>
        <div class="message <%= tipoMensaje %>">
            <%= mensaje %>
        </div>
    <% } %>

    <div class="container">
        <% if (vehiculoAEditar != null) { %>
            <form action="<%= request.getContextPath() %>/ActualizarVehiculoServlet" method="POST">
                <div>
                    <label for="placaOriginal">Placa Original:</label>
                    
                    <input type="text" id="placaOriginal" name="placaOriginal" value="<%= vehiculoAEditar.getPlaca() %>" readonly>
                    
                    <input type="hidden" name="placaActual" value="<%= vehiculoAEditar.getPlaca() %>">
                </div>
                <div>
                    <label for="nuevaPlaca">Nueva Placa (si cambia):</label>
                    <input type="text" id="nuevaPlaca" name="nuevaPlaca" value="<%= vehiculoAEditar.getPlaca() %>" required placeholder="Ej: XYZ-456">
                </div>
                <div>
                    <label for="idCliente">Cliente Propietario:</label>
                    <select id="idCliente" name="idCliente" required>
                        <option value="">-- Seleccione un Cliente --</option>
                        <% 
                        if (clientesDisponibles.isEmpty()) {
                        %>
                            <option value="" disabled>No hay clientes registrados.</option>
                        <%
                        } else {
                            String idClienteActual = (vehiculoAEditar.getCliente() != null) ? vehiculoAEditar.getCliente().getIdCliente() : "";
                            for (Cliente cliente : clientesDisponibles) {
                                String selected = (cliente.getIdCliente().equals(idClienteActual)) ? "selected" : "";
                        %>
                                <option value="<%= cliente.getIdCliente() %>" <%= selected %>><%= cliente.getNombre() %> (ID: <%= cliente.getIdCliente() %>)</option>
                        <%
                            }
                        }
                        %>
                    </select>
                </div>
                <button type="submit">Guardar Cambios</button>
            </form>
        <% } else { %>
            <p class="no-data">No se encontró el vehículo para editar o no se especificó la placa.</p>
        <% } %>
    </div>

    <a href="<%= request.getContextPath() %>/ListarVehiculosServlet" class="back-link">Volver al Listado de Vehículos</a>
    <a href="<%= request.getContextPath() %>/index.jsp" class="back-link">Volver al Menú Principal</a>
</body>
</html>
