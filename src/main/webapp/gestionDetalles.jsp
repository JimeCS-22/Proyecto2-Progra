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

    <div class="toggle-group">
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
            <select id="nombreRepuesto">
                <option>Select</option>
            </select>
        </div>

        <div class="form-group">
            <label for="cantidad">Cantidad:</label>
            <textarea id="cantidad" placeholder="Ex: 2"></textarea>
        </div>

        <div class="form-group">
            <label for="precioRepuesto">Precio:</label>
            <input type="text" id="precioRepuesto">
        </div>

        <div class="form-group">
            <label>¿Fue pedido?</label>
            <div class="radio-group">
                <input type="radio" id="pedidoSi" name="pedido" value="Si" checked>
                <label for="pedidoSi">Sí</label>

                <input type="radio" id="pedidoNo" name="pedido" value="No">
                <label for="pedidoNo">No</label>
            </div>
        </div>
    </div>

    <!-- Servicios -->
    <div id="seccionServicio">
        <div class="form-group">
            <label for="servicioRequerido">Servicio requerido:</label>
            <select id="servicioRequerido">
                <option>Select</option>
            </select>
        </div>

        <div class="form-group">
            <label for="precioServicio">Precio:</label>
            <select id="precioServicio">
                <option>Select</option>
            </select>
        </div>

        <div class="form-group">
            <label for="costoManoObra">Costo mano de obra:</label>
            <input type="text" id="costoManoObra">
        </div>
    </div>

    <div class="button-container">
        <button>Guardar</button>
    </div>

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

