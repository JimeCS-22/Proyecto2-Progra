<%-- 
    Document   : listadoVehiculos
    Created on : 30 jun 2025, 9:34:06 a. m.
    Author     : jimen
--%>

<%@page import="cr.ac.ucr.paraiso.proyecto2.progra.domain.Vehiculos"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Listado de Vehículos</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f4; }
        h1 { color: #333; text-align: center; margin-bottom: 30px; }
        .container { background-color: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); margin-bottom: 20px; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
        th { background-color: #f2f2f2; color: #555; font-weight: bold; }
        tr:nth-child(even) { background-color: #f9f9f9; }
        tr:hover { background-color: #f1f1f1; }
        .message { margin-top: 15px; padding: 10px; border-radius: 4px; }
        .success { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .error { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        .action-link { display: inline-block; margin-top: 20px; margin-right: 15px; padding: 8px 12px; background-color: #007bff; color: white; text-decoration: none; border-radius: 5px; }
        .action-link:hover { background-color: #0056b3; }
        .no-data { text-align: center; padding: 20px; color: #777; }
    </style>
</head>
<body>
    <h1>Listado de Vehículos Registrados</h1>

    <%
        // Obtener los datos y mensajes del request, enviados por el Servlet
        List<Vehiculos> listaVehiculos = (List<Vehiculos>) request.getAttribute("listaVehiculos");
        String mensaje = (String) request.getAttribute("mensaje");
        String tipoMensaje = (String) request.getAttribute("tipoMensaje");
    %>

    <%-- Mostrar mensajes de feedback (si los hay) --%>
    <% if (mensaje != null && !mensaje.isEmpty()) { %>
        <div class="message <%= tipoMensaje %>">
            <%= mensaje %>
        </div>
    <% } %>

    <div class="container">
        <%
        if (listaVehiculos == null || listaVehiculos.isEmpty()) {
        %>
            <p class="no-data">No hay vehículos registrados aún.</p>
        <%
        } else {
        %>
            <table>
                <thead>
                    <tr>
                        <th>Placa</th>
                        <th>ID Cliente</th>
                        <th>Nombre Cliente</th>
                        <th>Teléfono Cliente</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for (Vehiculos v : listaVehiculos) {
                    %>
                        <tr>
                            <td><%= v.getPlaca() %></td>
                            <td><%= (v.getCliente() != null) ? v.getCliente().getIdCliente() : "N/A" %></td>
                            <td><%= (v.getCliente() != null) ? v.getCliente().getNombre() : "N/A" %></td>
                            <td><%= (v.getCliente() != null) ? v.getCliente().getTelefono() : "N/A" %></td>
                        </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        <%
        }
        %>
    </div>

    <a href="<%= request.getContextPath() %>/InsertarVehiculoServlet" class="action-link">Registrar Nuevo Vehículo</a>
    <a href="<%= request.getContextPath() %>/ActualizarVehiculosServlet" class="action-link">Actualizar Vehiculos</a>
    <a href="<%= request.getContextPath() %>/index.jsp" class="action-link">Volver al Menú Principal</a>
</body>
</html>