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
  <h1>Insertar Clientes</h1>
  <div class="form-container">
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
    </div>

  <h2>Registro Clientes</h2>
    <form action="InsertarClienteServlet" method="get">
  <table border="1">
    <thead>
      <tr>
        <th>ID</th>
        <th>Nombre</th>
        <th>N.Teléfono</th>
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
