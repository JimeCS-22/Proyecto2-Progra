<%-- 
    Document   : insertarCliente
    Created on : 29 jun 2025, 2:41:28 p. m.
    Author     : Camila
--%>
<%@ page import="java.util.List" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="cr.ac.ucr.paraiso.proyecto2.progra.domain.Cliente" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
<head>
  <meta charset="UTF-8">
  <title>Drive Repair-Insertar Cliente</title>
      <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f4; }
        h1 { color: #333; text-align: center; margin-bottom: 30px; }
        .container { background-color: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); margin-bottom: 20px; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
        th { background-color: #f2f2f2; color: #555; font-weight: bold; }
        tr:nth-child(even) { background-color: #f9f9f9; }
        tr:hover { background-color: #f1f1f1; }
        .message { margin-top: 15px; padding: 10px; border-radius: 4px; }
        .success { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .error { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        .action-link { display: inline-block; margin-top: 20px; margin-right: 15px; padding: 8px 12px; background-color: #007bff; color: white; text-decoration: none; border-radius: 5px; }
        .action-link:hover { background-color: #0056b3; }
        .no-data { text-align: center; padding: 20px; color: #777; }
    </style>
</head>
<body>
  <h1>Insertar Clientes</h1>
<form action="InsertarClienteServlet" method="post">
   <div>
        <label for="idCliente">ID Cliente:</label>
        <input id="idCliente" name="idCliente" type="text" required>
   </div>
    <div>
        <label for="nombre">Nombre Completo: </label>
        <input id="nombre" name="nombre" type="text" required>
   </div>
   <div>
        <label for="telefono">Teléfono :</label>
        <input id="telefono" name="telefono" type="text" required>
    </div>
    <div>
        <label for="celular">Celular :</label>
        <input id="celular" name="celular" type="text" required>
    </div>
  
    <div>
        <label for="direccion">Dirección :</label>
        <input id="direccion" name="direccion" type="text" required>
    </div>
  <button type="submit">Insertar</button>
  <button type="submit">Actualizar</button>
  </form>
  

  <h2>Registro Clientes</h2>
    <form action="InsertarClienteServlet" method="get">
  <table border="1">
    <thead>
      <tr>
        <th>ID</th>
        <th>Nombre</th>
        <th>N.Telefono</th>
        <th>N.Celular</th>
         <th>Domicilio</th>
      </tr>
    </thead>
    <tbody>
      <%
        List<Cliente> clientes = (List<Cliente>) request.getAttribute("clientes");
      if (clientes != null && !clientes.isEmpty()) {
          for (Cliente cliente : clientes) {
      %>
        <tr>
          <td><%= cliente.getIdCliente() %></td>
          <td><%= cliente.getNombre() %></td>
          <td><%= cliente.getTelefono() %></td>
          <td><%= cliente.getCelular() %></td>
          <td><%= cliente.getDireccion() %></td>
        </tr>
      <% 
          }
        } else { 
      %>
        <tr>
          <td colspan="3">No hay clientes para mostrar.</td>
        </tr>
      <% } %>
    </tbody>
  </table>
      </form>
</body>
</html>
</f:view>
