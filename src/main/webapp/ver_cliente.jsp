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
        
      
    <a href="<%= request.getContextPath() %>/InsertarClienteServlet" class="action-link">Volver al Listado de Clientes</a>
    <a href="<%= request.getContextPath() %>/index.html" class="action-link">Volver al Menú Principal</a>


    </body>
</html>