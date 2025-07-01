<%-- 
    Document   : ver_cliente
    Created on : 29 jun 2025, 8:51:40 p. m.
    Author     : FAMILIA
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cliente insertado</title>
          <style>
       body { font-family: Arial, sans-serif; margin: 20px; }
        h1 { color: #333; }
        form { background-color: #f9f9f9; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        label { font-weight: bold; margin-top: 10px; display: block; }
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
        <h1>Información del cliente ingresado</h1>
        <% int identificacion = Integer.parseInt(request.getParameter("idCliente"));
            String nombre = request.getParameter("nombre");
            String telefono = request.getParameter("telefono");
            String celular = request.getParameter("celular");
            String direccion = request.getParameter("direccion");
        %>
        Identificación: <%= identificacion %> <br>
        Nombre: <%= nombre %><br>
        Teléfono: <%= telefono %><br>
        Celular: <%= celular %><br>
        Dirección: <%= direccion %><br>
        
       <form action="insertarCliente.jsp">
    <button type="submit" onclick="window.location.href='insertarCliente.jsp'" >Regresar</button>
</form>
    </body>
</html>