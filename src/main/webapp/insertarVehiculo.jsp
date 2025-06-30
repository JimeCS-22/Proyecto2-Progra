<%-- 
    Document   : insertarVehiculo
    Created on : 30 jun 2025, 7:32:33 a. m.
    Author     : jimen
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="cr.ac.ucr.paraiso.proyecto2.progra.data.ClienteXmlData"%>
<%@page import="cr.ac.ucr.paraiso.proyecto2.progra.domain.Cliente"%>
<%@page import="java.util.List"%>
<%@page import="java.io.File"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insertar Nuevo Vehículo</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f4; }
        h1 { color: #333; }
        .container { background-color: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); margin-bottom: 20px; }
        form div { margin-bottom: 10px; }
        label { display: inline-block; width: 150px; font-weight: bold; }
        input[type="text"], select { width: 300px; padding: 8px; border: 1px solid #ddd; border-radius: 4px; }
        button { padding: 10px 15px; background-color: #28a745; color: white; border: none; border-radius: 4px; cursor: pointer; font-size: 16px; }
        button:hover { background-color: #218838; }
        .message { margin-top: 15px; padding: 10px; border-radius: 4px; }
        .success { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .error { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        .back-link { display: block; margin-top: 20px; color: #007bff; text-decoration: none; }
        .back-link:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <h1>Insertar Nuevo Vehículo</h1>

    <%
       
        String mensaje = (String) request.getAttribute("mensaje");
        String tipoMensaje = (String) request.getAttribute("tipoMensaje");
        
        String rutaClientesXML = application.getRealPath("WEB-INF") + File.separator + "clientes.xml";
        List<Cliente> clientesDisponibles = new ArrayList<>();
        try {
            ClienteXmlData clientesData = ClienteXmlData.abrirDocumento(rutaClientesXML);
            clientesDisponibles = clientesData.findAll();
        } catch (Exception e) {
            // Manejar error al cargar clientes para el dropdown
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
        <form action="<%= request.getContextPath() %>/InsertarVehiculoServlet" method="POST">
            <div>
                <label for="placa">Placa del Vehículo:</label>
                <input type="text" id="placa" name="placa" required placeholder="Ej: ABC-123">
            </div>
            <div>
                <label for="idCliente">Cliente Propietario:</label>
                <select id="idCliente" name="idCliente" required>
                    <option value="">-- Seleccione un Cliente --</option>
                    <% 
                    if (clientesDisponibles.isEmpty()) {
                    %>
                        <option value="" disabled>No hay clientes registrados. Registre uno primero.</option>
                    <%
                    } else {
                        for (Cliente cliente : clientesDisponibles) {
                    %>
                        <option value="<%= cliente.getIdCliente() %>"><%= cliente.getNombre() %> (ID: <%= cliente.getIdCliente() %>)</option>
                    <%
                        }
                    }
                    %>
                </select>
            </div>
            <button type="submit">Registrar Vehículo</button>
        </form>
    </div>

    <a href="<%= request.getContextPath() %>/WEB-INF/vehiculos/listarVehiculos.jsp" class="back-link">Ver Listado de Vehículos</a> 
    <a href="<%= request.getContextPath() %>/index.jsp" class="back-link">Volver al Menú Principal</a> 
</body>
</html>