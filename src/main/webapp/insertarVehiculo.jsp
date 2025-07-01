<%-- 
    Document   : insertarVehiculo
    Created on : 30 jun 2025, 7:32:33 a. m.
    Author     : jimen
--%>
<%@page import="cr.ac.ucr.paraiso.proyecto2.progra.domain.Cliente"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insertar Nuevo Vehículo</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f4; }
        h1 { color: #333; text-align: center; margin-bottom: 30px; }
        .container { background-color: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); margin: 0 auto; max-width: 500px; }
        .form-group { margin-bottom: 15px; }
        .form-group label { display: block; margin-bottom: 5px; font-weight: bold; color: #555; }
        .form-group input[type="text"], .form-group select {
            width: calc(100% - 22px); /* Ajuste para padding y borde */
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 16px;
            box-sizing: border-box; /* Incluye padding y border en el ancho */
        }
        .form-group input[type="submit"] {
            background-color: #28a745;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        .form-group input[type="submit"]:hover {
            background-color: #218838;
        }
        .message { margin-top: 15px; padding: 10px; border-radius: 4px; }
        .success { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .error { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        .action-link { display: inline-block; margin-top: 20px; margin-right: 15px; padding: 8px 12px; background-color: #007bff; color: white; text-decoration: none; border-radius: 5px; }
        .action-link:hover { background-color: #0056b3; }
    </style>
</head>
<body>
    <h1>Insertar Nuevo Vehículo</h1>

    <%
        String mensaje = (String) request.getAttribute("mensaje");
        String tipoMensaje = (String) request.getAttribute("tipoMensaje");
        List<Cliente> listaClientes = (List<Cliente>) request.getAttribute("listaClientes"); // Obtener la lista de clientes
    %>

    <% if (mensaje != null && !mensaje.isEmpty()) { %>
        <div class="message <%= tipoMensaje %>">
            <%= mensaje %>
        </div>
    <% } %>

    <div class="container">
        <form action="<%= request.getContextPath() %>/InsertarVehiculoServlet" method="post">
            <div class="form-group">
                <label for="placa">Placa:</label>
                <input type="text" id="placa" name="placa" required>
            </div>
            <div class="form-group">
                <label for="idCliente">Cliente Propietario:</label>
                <select id="idCliente" name="idCliente" required>
                    <option value="">-- Seleccione un Cliente --</option>
                    <%
                        if (listaClientes != null && !listaClientes.isEmpty()) {
                            for (Cliente cliente : listaClientes) {
                    %>
                                <option value="<%= cliente.getIdCliente() %>"><%= cliente.getNombre() %> (ID: <%= cliente.getIdCliente() %>)</option>
                    <%
                            }
                        } else {
                    %>
                            <option value="" disabled>No hay clientes disponibles</option>
                    <%
                        }
                    %>
                </select>
            </div>
            <div class="form-group">
                <input type="submit" value="Registrar Vehículo">
            </div>
        </form>
    </div>

    <a href="<%= request.getContextPath() %>/ListarVehiculosServlet" class="action-link">Volver al Listado</a>
    <a href="<%= request.getContextPath() %>/index.jsp" class="action-link">Volver al Menú Principal</a>
</body>
</html>