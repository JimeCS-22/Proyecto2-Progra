<%@page import="cr.ac.ucr.paraiso.proyecto2.progra.domain.Vehiculos"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insertar Orden de Trabajo</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        h1 { color: #333; }
        form { background-color: #f9f9f9; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        label { font-weight: bold; margin-top: 10px; display: block; }
        input[type="text"], input[type="date"], select, textarea {
            width: calc(100% - 22px);
            padding: 10px;
            margin-top: 5px;
            margin-bottom: 15px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        input[type="submit"], button {
            background-color: #4CAF50;
            color: white;
            padding: 12px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            margin-right: 10px;
        }
        button.add-detail-btn {
            background-color: #007bff;
        }
        .message {
            padding: 10px;
            margin-bottom: 15px;
            border-radius: 5px;
            font-weight: bold;
        }
        .message.success {
            background-color: #d4edda;
            color: #155724;
            border-color: #c3e6cb;
        }
        .message.error {
            background-color: #f8d7da;
            color: #721c24;
            border-color: #f5c6cb;
        }
    </style>
</head>
<body>
    <h1>Insertar Nueva Orden de Trabajo</h1>

    <%
        String mensaje = (String) request.getAttribute("mensaje");
        if (mensaje != null && !mensaje.isEmpty()) {
            String cssClass = mensaje.contains("Error") ? "message error" : "message success";
    %>
            <div class="<%=cssClass%>"><%= mensaje %></div>
    <%
        }
        
        LocalDate hoy = LocalDate.now();
        LocalDate manana = hoy.plusDays(1);
        DateTimeFormatter formatoHTML = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    %>

    <form id="ordenForm" action="InsertarOrdenTrabajoServlet" method="post">
        <h2>Datos de la Orden de Trabajo</h2>
        
        <!-- Campo oculto para el ID que se generará automáticamente -->
        <input type="hidden" id="id" name="id">
        
        <label for="descripcionSolicitud">Descripción de la Solicitud:</label>
        <textarea id="descripcionSolicitud" name="descripcionSolicitud" rows="4" required></textarea><br>

        <label for="estado">Estado:</label>
        <select id="estado" name="estado" required>
            <option value="Pendiente">Pendiente</option>
            <option value="En Proceso">En Proceso</option>
            <option value="Completada">Completada</option>
            <option value="Cancelada">Cancelada</option>
        </select><br>

        <label for="fechaIngreso">Fecha de Ingreso:</label>
        <input type="date" id="fechaIngreso" name="fechaIngreso" 
               value="<%= hoy.format(formatoHTML) %>" required><br>

        <label for="fechaDevolucionEstimada">Fecha de Devolución Estimada:</label>
        <input type="date" id="fechaDevolucionEstimada" name="fechaDevolucionEstimada" 
               value="<%= manana.format(formatoHTML) %>" required><br>

        <h3>Vehículo Asociado</h3>
        <label for="vehiculo">Seleccione Vehículo:</label>
        <select id="vehiculo" name="vehiculoPlaca" required>
            <%
                List<Vehiculos> vehiculosDisponibles = (List<Vehiculos>) request.getAttribute("vehiculosDisponibles");
                if (vehiculosDisponibles != null && !vehiculosDisponibles.isEmpty()) {
                    for (Vehiculos v : vehiculosDisponibles) {
                        out.println("<option value=\"" + v.getPlaca() + "\">" + v.getPlaca() + " (Dueño: " + (v.getCliente() != null ? v.getCliente().getNombre() : "N/A") + ")</option>");
                    }
                } else {
                    out.println("<option value=\"\">No hay vehículos disponibles</option>");
                }
            %>
        </select><br><br>

        <input type="submit" value="Guardar Orden de Trabajo">
        <button type="button" class="add-detail-btn" id="btnAddDetalles">Añadir/Editar Detalles</button>
    </form>

    <script>
        // Generar ID automático al cargar la página
        document.addEventListener('DOMContentLoaded', function() {
            // Generar un ID único (usando timestamp y un número aleatorio)
            const id = 'OT-' + Date.now() + '-' + Math.floor(Math.random() * 1000);
            document.getElementById('id').value = id;
            
            // Configurar el botón de detalles
            document.getElementById('btnAddDetalles').addEventListener('click', function() {
                const ordenId = document.getElementById('id').value;
                if (ordenId) {
                    window.location.href = 'gestionDetalles.jsp?ordenId=' + encodeURIComponent(ordenId);
                }
            });
        });

        // Validación de fechas
        document.getElementById('ordenForm').addEventListener('submit', function(e) {
            const fechaIngreso = new Date(document.getElementById('fechaIngreso').value);
            const fechaDevolucion = new Date(document.getElementById('fechaDevolucionEstimada').value);
            
            if (fechaDevolucion < fechaIngreso) {
                e.preventDefault();
                alert('Error: La fecha de devolución debe ser posterior a la de ingreso');
                document.getElementById('fechaDevolucionEstimada').focus();
            }
        });
    </script>
</body>
</html>