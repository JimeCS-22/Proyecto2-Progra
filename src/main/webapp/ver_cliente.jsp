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
    </head>
    <body>
        <h1>Información del cliente</h1>
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
        
       <form action="pagina.html">
    <button type="submit" onclick="window.location.href='insertarCliente.jsp'" >Regresar</button>
</form>
    </body>
</html>