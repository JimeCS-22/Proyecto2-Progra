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
      body { font-family: Arial, sans-serif; margin: 20px; }
        h1 { color: #333; }
        form { background-color: #f9f9f9; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        label { font-weight: bold; margin-top: 10px; display: block;font-size: 16px; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
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

    <h2>Ingreso Detalle Orden</h2>

   <div class="toggle-group" style="display: flex; align-items: center; gap: 10px;">
    <label style="white-space: nowrap;">Tipo detalle:</label>
    
    <div class="radio-group" style="display: flex; align-items: center; gap: 15px;">
        
        <div style="display: flex; align-items: center; gap: 5px;">
            <input type="radio" id="servicios" name="tipoDetalle" value="Servicios" checked>
            <label for="servicios">Servicios</label>
        </div>

        <div style="display: flex; align-items: center; gap: 5px;">
            <input type="radio" id="repuestos" name="tipoDetalle" value="Repuestos">
            <label for="repuestos">Repuestos</label>
        </div>

    </div>
</div>


    <!-- Repuestos -->
    <div id="seccionRepuesto">
        <div class="form-group">
            <label for="nombreRepuesto">Nombre Repuesto:</label>
             <input id="nombreRepuesto" placeholder="Ex: Llantas" name="nombreRepuesto">
        </div>

        <div class="form-group">
            <label for="cantidad">Cantidad:</label>
            <textarea id="cantidad" placeholder="Ex: 2" name ="cantidad"></textarea>
        </div>

        <div class="form-group">
            <label for="precioRepuesto">Precio:</label>
            <input type="text" id="precioRepuesto" name="precioRepuesto">
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
             <input type="text" id="servicioRequerido" name="servicioRequerido">
        </div>

        <div class="form-group">
            <label for="precioServicio">Precio:</label>
             <input type="text" id="precioServicio" name="precioServicio">
        </div>

        <div class="form-group">
            <label for="costoManoObra">Costo mano de obra:</label>
            <input type="text" id="costoManoObra" name="costoManoObra" >
          
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

