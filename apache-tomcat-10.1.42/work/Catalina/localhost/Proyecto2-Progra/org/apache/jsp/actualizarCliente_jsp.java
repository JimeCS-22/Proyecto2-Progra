/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/10.1.42
 * Generated at: 2025-07-02 20:53:15 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.jsp.*;
import cr.ac.ucr.paraiso.proyecto2.progra.domain.Cliente;

public final class actualizarCliente_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports,
                 org.apache.jasper.runtime.JspSourceDirectives {

  private static final jakarta.servlet.jsp.JspFactory _jspxFactory =
          jakarta.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.LinkedHashSet<>(4);
    _jspx_imports_packages.add("jakarta.servlet");
    _jspx_imports_packages.add("jakarta.servlet.http");
    _jspx_imports_packages.add("jakarta.servlet.jsp");
    _jspx_imports_classes = new java.util.LinkedHashSet<>(2);
    _jspx_imports_classes.add("cr.ac.ucr.paraiso.proyecto2.progra.domain.Cliente");
  }

  private volatile jakarta.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public boolean getErrorOnELNotFound() {
    return false;
  }

  public jakarta.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final jakarta.servlet.http.HttpServletRequest request, final jakarta.servlet.http.HttpServletResponse response)
      throws java.io.IOException, jakarta.servlet.ServletException {

    if (!jakarta.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      final java.lang.String _jspx_method = request.getMethod();
      if ("OPTIONS".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        return;
      }
      if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET, POST or HEAD. Jasper also permits OPTIONS");
        return;
      }
    }

    final jakarta.servlet.jsp.PageContext pageContext;
    jakarta.servlet.http.HttpSession session = null;
    final jakarta.servlet.ServletContext application;
    final jakarta.servlet.ServletConfig config;
    jakarta.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    jakarta.servlet.jsp.JspWriter _jspx_out = null;
    jakarta.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("    <title>Actualizar Clientes</title>\n");
      out.write("    <style>\n");
      out.write("        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f4; }\n");
      out.write("        h1 { color: #333; text-align: center; margin-bottom: 30px; }\n");
      out.write("        .container { background-color: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); margin: 0 auto; max-width: 500px; }\n");
      out.write("        .form-group { margin-bottom: 15px; }\n");
      out.write("        .form-group label { display: block; margin-bottom: 5px; font-weight: bold; color: #555; }\n");
      out.write("        .form-group input[type=\"text\"], .form-group select {\n");
      out.write("            width: calc(100% - 22px); \n");
      out.write("            padding: 10px;\n");
      out.write("            border: 1px solid #ddd;\n");
      out.write("            border-radius: 4px;\n");
      out.write("            font-size: 16px;\n");
      out.write("            box-sizing: border-box; \n");
      out.write("        }\n");
      out.write("        .form-group input[type=\"submit\"] {\n");
      out.write("            background-color: #007bff;\n");
      out.write("            color: white;\n");
      out.write("            padding: 10px 20px;\n");
      out.write("            border: none;\n");
      out.write("            border-radius: 4px;\n");
      out.write("            cursor: pointer;\n");
      out.write("            font-size: 16px;\n");
      out.write("        }\n");
      out.write("        .form-group input[type=\"submit\"].search-button {\n");
      out.write("            background-color: #6c757d; \n");
      out.write("        }\n");
      out.write("        .form-group input[type=\"submit\"]:hover {\n");
      out.write("            background-color: #0056b3;\n");
      out.write("        }\n");
      out.write("        .form-group input[type=\"submit\"].search-button:hover {\n");
      out.write("            background-color: #5a6268;\n");
      out.write("        }\n");
      out.write("        .message { margin-top: 15px; padding: 10px; border-radius: 4px; }\n");
      out.write("        .success { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; }\n");
      out.write("        .error { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }\n");
      out.write("        .info { background-color: #cfe2ff; color: #055160; border: 1px solid #b6d4fe; } \n");
      out.write("        .action-link { display: inline-block; margin-top: 20px; margin-right: 15px; padding: 8px 12px; background-color: #6c757d; color: white; text-decoration: none; border-radius: 5px; }\n");
      out.write("        .action-link:hover { background-color: #5a6268; }\n");
      out.write("    </style>\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("    <h1>Actualizar Información Clientes</h1>\n");
      out.write("\n");
      out.write("    ");

        Cliente cliente = (Cliente) request.getAttribute("cliente");
        String mensaje = (String) request.getAttribute("mensaje");
        String mensajeExito = (String) request.getAttribute("mensajeExito");
        String mensajeError = (String) request.getAttribute("mensajeError");
        String idBuscado = request.getParameter("idCliente");
    
      out.write("\n");
      out.write("\n");
      out.write("    ");
 if (mensaje != null) { 
      out.write("\n");
      out.write("        <div class=\"message info\">");
      out.print( mensaje );
      out.write("</div>\n");
      out.write("    ");
 } 
      out.write("\n");
      out.write("    \n");
      out.write("    ");
 if (mensajeExito != null) { 
      out.write("\n");
      out.write("        <div class=\"message success\">");
      out.print( mensajeExito );
      out.write("</div>\n");
      out.write("    ");
 } 
      out.write("\n");
      out.write("    \n");
      out.write("    ");
 if (mensajeError != null) { 
      out.write("\n");
      out.write("        <div class=\"message error\">");
      out.print( mensajeError );
      out.write("</div>\n");
      out.write("    ");
 } 
      out.write("\n");
      out.write("\n");
      out.write("    <div class=\"container\">\n");
      out.write("        <h2>Buscar Cliente por Identificación</h2>\n");
      out.write("        <form action=\"");
      out.print( request.getContextPath() );
      out.write("/ActualizarClienteServlet\" method=\"get\">\n");
      out.write("            <div class=\"form-group\">\n");
      out.write("                <label for=\"searchId\">Ingrese la Identificación del Cliente a Actualizar:</label>\n");
      out.write("                <input type=\"text\" id=\"searchId\" name=\"idCliente\" value=\"");
      out.print( (idBuscado != null) ? idBuscado : "" );
      out.write("\" required>\n");
      out.write("            </div>\n");
      out.write("            <div class=\"form-group\">\n");
      out.write("                <input type=\"submit\" value=\"Buscar Cliente\" class=\"search-button\">\n");
      out.write("            </div>\n");
      out.write("        </form>\n");
      out.write("\n");
      out.write("        ");
 if (cliente != null) { 
      out.write("\n");
      out.write("            <hr>\n");
      out.write("            <h2>Datos del Cliente (Identificación: ");
      out.print( cliente.getIdCliente() );
      out.write(")</h2>\n");
      out.write("            <form action=\"");
      out.print( request.getContextPath() );
      out.write("/ActualizarClienteServlet\" method=\"post\">\n");
      out.write("                <input type=\"hidden\" name=\"idOriginal\" value=\"");
      out.print( cliente.getIdCliente() );
      out.write("\">\n");
      out.write("\n");
      out.write("                <div class=\"form-group\">\n");
      out.write("                    <label for=\"idCliente\">Identificación (No modificable):</label>\n");
      out.write("                    <input type=\"text\" id=\"idCliente\" name=\"idCliente\" value=\"");
      out.print( cliente.getIdCliente() );
      out.write("\" readonly>\n");
      out.write("                </div>\n");
      out.write("\n");
      out.write("                <div class=\"form-group\">\n");
      out.write("                    <label for=\"nombre\">Nuevo Nombre Cliente:</label>\n");
      out.write("                    <input type=\"text\" id=\"nombre\" name=\"nombre\" value=\"");
      out.print( cliente.getNombre() );
      out.write("\" required>\n");
      out.write("                </div>\n");
      out.write("                <div class=\"form-group\">\n");
      out.write("                    <label for=\"telefono\">Nuevo Teléfono Cliente:</label>\n");
      out.write("                    <input type=\"text\" id=\"telefono\" name=\"telefono\" value=\"");
      out.print( cliente.getTelefono() );
      out.write("\" required>\n");
      out.write("                </div>\n");
      out.write("                <div class=\"form-group\">\n");
      out.write("                    <label for=\"celular\">Nuevo Celular Cliente:</label>\n");
      out.write("                    <input type=\"text\" id=\"celular\" name=\"celular\" value=\"");
      out.print( cliente.getCelular() );
      out.write("\" required>\n");
      out.write("                </div>\n");
      out.write("                <div class=\"form-group\">\n");
      out.write("                    <label for=\"direccion\">Nueva Dirección Cliente:</label>\n");
      out.write("                    <input type=\"text\" id=\"direccion\" name=\"direccion\" value=\"");
      out.print( cliente.getDireccion() );
      out.write("\" required>\n");
      out.write("                </div>\n");
      out.write("                <div class=\"form-group\">\n");
      out.write("                    <input type=\"submit\" value=\"Actualizar Cliente\">\n");
      out.write("                </div>\n");
      out.write("            </form>\n");
      out.write("        ");
 } 
      out.write("\n");
      out.write("    </div>\n");
      out.write("\n");
      out.write("    <a href=\"");
      out.print( request.getContextPath() );
      out.write("/InsertarClienteServlet\" class=\"action-link\">Volver al Listado de Clientes</a>\n");
      out.write("    <a href=\"");
      out.print( request.getContextPath() );
      out.write("/index.html\" class=\"action-link\">Volver al Menú Principal</a>\n");
      out.write("</body>\n");
      out.write("</html>\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof jakarta.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
