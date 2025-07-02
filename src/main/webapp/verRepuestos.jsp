<%-- 
    Document   : verRepuesto
    Created on : 1 jul 2025, 5:38:07 p. m.
    Author     : jimen
--%>

<%@page import="java.util.List"%>
<%@page import="cr.ac.ucr.paraiso.proyecto2.progra.domain.Repuesto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ver Repuestos</title>
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
                max-width: 800px;
            }
            h1 {
                color: #333;
                text-align: center;
                margin-bottom: 20px;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }
            th, td {
                padding: 12px;
                border: 1px solid #ddd;
                text-align: left;
            }
            th {
                background-color: #f2f2f2;
                color: #333;
            }
            tr:nth-child(even) {
                background-color: #f9f9f9;
            }
            tr:hover {
                background-color: #f1f1f1;
            }
            .no-data {
                text-align: center;
                margin-top: 20px;
                font-style: italic;
                color: #777;
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
            <h1>Lista de Repuestos</h1>

            <%
                List<Repuesto> repuestos = (List<Repuesto>) request.getAttribute("repuestos");
                if (repuestos != null && !repuestos.isEmpty()) {
            %>
            <table>
                <thead>
                    <tr>
                        <th>Nombre</th>
                        <th>Cantidad</th>
                        <th>Precio</th>
                        <th>¿Fue Pedido?</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (Repuesto repuesto : repuestos) {%>
                    <tr>
                        <td><%= repuesto.getNombreRepuesto()%></td>
                        <td><%= repuesto.getCantidad()%></td>
                        <td><%= String.format("%.2f", repuesto.getPrecio())%></td>
                        <td><%= repuesto.isFuePedido() ? "Sí" : "No"%></td>
                    </tr>
                    <% }%>
                </tbody>
            </table>
            <%
            } else {
            %>
            <p class="no-data">No hay repuestos registrados.</p>
            <%
                }
            %>
        </div>
        <a href="index.jsp" class="back-link">Volver al Menú Principal</a>
        
        <a href="insertarRepuesto.jsp" class="back-link">Insertar Repuestos </a>
        
    </body>
</html>

