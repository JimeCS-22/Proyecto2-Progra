<%-- 
    Document   : gestionDetalles
    Created on : 30 jun 2025, 2:04:25 p. m.
    Author     : Camila
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>Ingreso Detalle Orden</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            padding: 20px;
        }
        h2 {
            margin-bottom: 20px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            font-weight: bold;
            display: inline-block;
            width: 150px;
        }
        input, select, textarea {
            padding: 5px;
            width: 200px;
        }
        .radio-group {
            display: flex;
            gap: 15px;
            align-items: center;
        }
        .button-container {
            margin-top: 20px;
            text-align: right;
        }
        .button-container button {
            padding: 10px 20px;
            background-color: gray;
            color: white;
            border: none;
            cursor: pointer;
        }
        .toggle-group {
            display: flex;
            gap: 20px;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>

    <h2>Ingreso Detalle Orden</h2>

    <div class="toggle-group" style="display: flex; align-items: center; gap: 3px;">
        <label>Tipo detalle:</label>
        <div class="radio-group">
            <input type="radio" id="servicios" name="tipoDetalle" value="Servicios" checked>
            <label for="servicios">Servicios</label>

            <input type="radio" id="repuestos" name="tipoDetalle" value="Repuestos">
            <label for="repuestos">Repuestos</label>
        </div>
    </div>

    <!-- Repuestos -->
    <div id="seccionRepuesto">
        <div class="form-group">
            <label for="nombreRepuesto">Nombre Repuesto:</label>
             <input id="nombreRepuesto" placeholder="Ex: Llantas">
        </div>

        <div class="form-group">
            <label for="cantidad">Cantidad:</label>
            <textarea id="cantidad" placeholder="Ex: 2"></textarea>
        </div>

        <div class="form-group">
            <label for="precioRepuesto">Precio:</label>
            <input type="text" id="precioRepuesto">
        </div>

        <div class="form-group" style="display: flex; align-items: center; gap: 5px;">
    <label style="margin-right: 10px;">¿Fue pedido?</label>
    <div class="radio-group" style="display: flex; gap: 1px; align-items: center;">
        
        <input type="radio" id="pedidoSi" name="pedido" value="Si" checked>
        <label for="pedidoSi" style="margin-right: 1px;">Sí</label>
        <br>
        <input type="radio" id="pedidoNo" name="pedido" value="No">
        <label for="pedidoNo">No</label>

    </div>
</div>
    </div>

    <!-- Servicios -->
    <div id="seccionServicio">
        <div class="form-group">
            <label for="servicioRequerido">Servicio requerido:</label>
             <input type="text" id="servicioRequerido">
        </div>

        <div class="form-group">
            <label for="precioServicio">Precio:</label>
             <input type="text" id="precioServicio">
        </div>

        <div class="form-group">
            <label for="costoManoObra">Costo mano de obra:</label>
            <input type="text" id="costoManoObra">
        </div>
    </div>
<form action="${pageContext.request.contextPath}/InsertarDetallesServlet" method="post">
    <div class="button-container">
        <button type="submit">Guardar</button>
    </div>
</form>
    <script>
        const radioServicios = document.getElementById("servicios");
        const radioRepuestos = document.getElementById("repuestos");
        const seccionRepuesto = document.getElementById("seccionRepuesto");
        const seccionServicio = document.getElementById("seccionServicio");

        function actualizarVista() {
            if (radioServicios.checked) {
                seccionServicio.style.display = "block";
                seccionRepuesto.style.display = "none";
            } else {
                seccionServicio.style.display = "none";
                seccionRepuesto.style.display = "block";
            }
        }

        radioServicios.addEventListener("change", actualizarVista);
        radioRepuestos.addEventListener("change", actualizarVista);

        // Mostrar solo la sección inicial correcta
        actualizarVista();
    </script>

</body>
</html>

