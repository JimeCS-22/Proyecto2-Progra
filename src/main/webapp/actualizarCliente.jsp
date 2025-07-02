<%-- 
    Document   : actualizarCliente
    Created on : 2 jul 2025, 11:03:27 a. m.
    Author     : Camila
--%>

<%@page import="cr.ac.ucr.paraiso.proyecto2.progra.domain.Cliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Actualizar Clientes</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f4; }
        h1 { color: #333; text-align: center; margin-bottom: 30px; }
        .container { background-color: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); margin: 0 auto; max-width: 500px; }
        .form-group { margin-bottom: 15px; }
        .form-group label { display: block; margin-bottom: 5px; font-weight: bold; color: #555; }
        .form-group input[type="text"], .form-group select {
            width: calc(100% - 22px); 
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 16px;
            box-sizing: border-box; 
        }
        .form-group input[type="submit"] {
            background-color: #007bff;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        .form-group input[type="submit"].search-button {
            background-color: #6c757d; 
        }
        .form-group input[type="submit"]:hover {
            background-color: #0056b3;
        }
        .form-group input[type="submit"].search-button:hover {
            background-color: #5a6268;
        }
        .message { margin-top: 15px; padding: 10px; border-radius: 4px; }
        .success { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .error { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        .info { background-color: #cfe2ff; color: #055160; border: 1px solid #b6d4fe; } 
        .action-link { display: inline-block; margin-top: 20px; margin-right: 15px; padding: 8px 12px; background-color: #6c757d; color: white; text-decoration: none; border-radius: 5px; }
        .action-link:hover { background-color: #5a6268; }
    </style>
</head>
<body>
    <h1>Actualizar Información Clientes</h1>

    <%
        Cliente cliente = (Cliente) request.getAttribute("cliente");
        String mensaje = (String) request.getAttribute("mensaje");
        String mensajeExito = (String) request.getAttribute("mensajeExito");
        String mensajeError = (String) request.getAttribute("mensajeError");
        String idBuscado = request.getParameter("idCliente");
    %>

    <% if (mensaje != null) { %>
        <div class="message info"><%= mensaje %></div>
    <% } %>
    
    <% if (mensajeExito != null) { %>
        <div class="message success"><%= mensajeExito %></div>
    <% } %>
    
    <% if (mensajeError != null) { %>
        <div class="message error"><%= mensajeError %></div>
    <% } %>

    <div class="container">
        <h2>Buscar Cliente por Identificación</h2>
        <form action="<%= request.getContextPath() %>/ActualizarClienteServlet" method="get">
            <div class="form-group">
                <label for="searchId">Ingrese la Identificación del Cliente a Actualizar:</label>
                <input type="text" id="searchId" name="idCliente" value="<%= (idBuscado != null) ? idBuscado : "" %>" required>
            </div>
            <div class="form-group">
                <input type="submit" value="Buscar Cliente" class="search-button">
            </div>
        </form>

        <% if (cliente != null) { %>
            <hr>
            <h2>Datos del Cliente (Identificación: <%= cliente.getIdCliente() %>)</h2>
            <form action="<%= request.getContextPath() %>/ActualizarClienteServlet" method="post">
                <input type="hidden" name="idOriginal" value="<%= cliente.getIdCliente() %>">

                <div class="form-group">
                    <label for="idCliente">Identificación (No modificable):</label>
                    <input type="text" id="idCliente" name="idCliente" value="<%= cliente.getIdCliente() %>" readonly>
                </div>

                <div class="form-group">
                    <label for="nombre">Nuevo Nombre Cliente:</label>
                    <input type="text" id="nombre" name="nombre" value="<%= cliente.getNombre() %>" required>
                </div>
                <div class="form-group">
                    <label for="telefono">Nuevo Teléfono Cliente:</label>
                    <input type="text" id="telefono" name="telefono" value="<%= cliente.getTelefono() %>" required>
                </div>
                <div class="form-group">
                    <label for="celular">Nuevo Celular Cliente:</label>
                    <input type="text" id="celular" name="celular" value="<%= cliente.getCelular() %>" required>
                </div>
                <div class="form-group">
                    <label for="direccion">Nueva Dirección Cliente:</label>
                    <input type="text" id="direccion" name="direccion" value="<%= cliente.getDireccion() %>" required>
                </div>
                <div class="form-group">
                    <input type="submit" value="Actualizar Cliente">
                </div>
            </form>
        <% } %>
    </div>

    <a href="<%= request.getContextPath() %>/InsertarClienteServlet" class="action-link">Volver al Listado de Clientes</a>
    <a href="<%= request.getContextPath() %>/index.html" class="action-link">Volver al Menú Principal</a>
</body>
</html>
