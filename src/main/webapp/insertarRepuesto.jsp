<%-- 
    Document   : repuestos
    Created on : 1 jul 2025, 5:32:08 p. m.
    Author     : jimen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Insertar Repuesto</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                margin: 0;
                padding: 20px;
                display: flex;
                flex-direction: column;
                align-items: center;
            }
            .container {
                background-color: #fff;
                padding: 30px;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                width: 100%;
                max-width: 500px;
            }
            h1 {
                color: #333;
                text-align: center;
                margin-bottom: 20px;
            }
            form {
                display: flex;
                flex-direction: column;
            }
            label {
                margin-bottom: 8px;
                font-weight: bold;
                color: #555;
            }
            input[type="text"],
            input[type="number"],
            input[type="checkbox"] {
                padding: 10px;
                margin-bottom: 15px;
                border: 1px solid #ddd;
                border-radius: 4px;
                font-size: 16px;
            }
            input[type="checkbox"] {
                width: auto;
                margin-right: 10px;
            }
            .checkbox-group {
                display: flex;
                align-items: center;
                margin-bottom: 15px;
            }
            input[type="submit"] {
                background-color: #4CAF50;
                color: white;
                padding: 12px 20px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-size: 18px;
                transition: background-color 0.3s ease;
            }
            input[type="submit"]:hover {
                background-color: #45a049;
            }
            .message {
                margin-top: 20px;
                padding: 10px;
                border-radius: 4px;
                text-align: center;
            }
            .success {
                background-color: #d4edda;
                color: #155724;
                border: 1px solid #c3e6cb;
            }
            .error {
                background-color: #f8d7da;
                color: #721c24;
                border: 1px solid #f5c6cb;
            }
            .back-link {
                margin-top: 20px;
                text-decoration: none;
                color: #007bff;
                font-size: 16px;
            }
            .back-link:hover {
                text-decoration: underline;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Insertar Nuevo Repuesto</h1>

            <%-- Muestra mensajes de éxito o error --%>
            <%
                String mensaje = (String) request.getAttribute("mensaje");
                String tipoMensaje = (String) request.getAttribute("tipoMensaje");
                if (mensaje != null) {
            %>
            <div class="message <%= tipoMensaje %>">
                <%= mensaje %>
            </div>
            <%
                }
            %>

            <form action="InsertarRepuestoServlet" method="post">
                <label for="nombreRepuesto">Nombre del Repuesto:</label>
                <input type="text" id="nombreRepuesto" name="nombreRepuesto" required>

                <label for="cantidad">Cantidad:</label>
                <input type="number" id="cantidad" name="cantidad" min="0" required>

                <label for="precio">Precio:</label>
                <input type="text" id="precio" name="precio" pattern="^\d+(\.\d{1,2})?$" placeholder="Ej: 10.50" required>

                <div class="checkbox-group">
                    <input type="checkbox" id="fuePedido" name="fuePedido" value="true">
                    <label for="fuePedido">¿Fue Pedido?</label>
                </div>

                <input type="submit" value="Insertar Repuesto">
            </form>
        </div>
        <a href="index.jsp" class="back-link">Volver al Menú Principal</a>
        <a href="verRepuestos.jsp" class="back-link">Listado de Repuestos</a>
    </body>
</html>
