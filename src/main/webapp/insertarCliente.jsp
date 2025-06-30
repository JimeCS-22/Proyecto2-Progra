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
