/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.paraiso.proyecto2.progra.data;

import cr.ac.ucr.paraiso.proyecto2.progra.domain.Cliente;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 *
 * @author Camila
 * manejar datos de los clientes del clientes.xml
 */
public class ClienteXmlData {
      private Document document;
    private Element raiz;
    private String rutaDocumento;

    // Constructor público que carga el XML existente o crea uno nuevo
    public ClienteXmlData(String rutaDocumento) throws JDOMException, IOException {
        File file = new File(rutaDocumento);
        this.rutaDocumento = rutaDocumento;

        if (file.exists()) {
            SAXBuilder saxBuilder = new SAXBuilder();
            saxBuilder.setIgnoringElementContentWhitespace(true);
            this.document = saxBuilder.build(rutaDocumento);
            this.raiz = document.getRootElement();
        } else {
            this.raiz = new Element("clientes");
            this.document = new Document(raiz);
            guardar();
        }
    }

    // Método estático para facilitar la apertura o creación del documento
    public static ClienteXmlData abrirDocumento(String rutaDocumento) throws JDOMException, IOException {
        return new ClienteXmlData(rutaDocumento);
    }

    // Guardar los cambios al XML
    private void guardar() throws FileNotFoundException, IOException {
        Format format = Format.getPrettyFormat();
        format.setEncoding("UTF-8");
        XMLOutputter xmlOutputter = new XMLOutputter();
        PrintWriter printWriter = new PrintWriter(this.rutaDocumento);
        xmlOutputter.output(this.document, printWriter);
        printWriter.close();
    }

   public void insertarCliente(Cliente cliente) throws IOException {
    List<Element> listaClientes = raiz.getChildren("cliente"); 

    // Verificar si ya existe un cliente con el mismo ID
    for (Element eCliente : listaClientes) {
        if (cliente.getIdCliente().equals(eCliente.getAttributeValue("idCliente"))) {
            System.out.println("Ya existe un cliente con ese ID, no se insertará.");
            return;
        }
    }

    // Crear nuevo elemento cliente
    Element eCliente = new Element("cliente");
    eCliente.setAttribute("idCliente", cliente.getIdCliente());

    Element eNombre = new Element("nombre").setText(cliente.getNombre());
    Element eTelefono = new Element("telefono").setText(cliente.getTelefono());
    Element eCelular = new Element("celular").setText(cliente.getCelular());
    Element eDireccion = new Element("direccion").setText(cliente.getDireccion());

    eCliente.addContent(eNombre);
    eCliente.addContent(eTelefono);
    eCliente.addContent(eCelular);
    eCliente.addContent(eDireccion);

    // Insertar en la posición correcta según orden alfabético
    int index = 0;
    for (Element existente : listaClientes) {
        String nombreExistente = existente.getChildText("nombre");
        if (nombreExistente != null && nombreExistente.compareToIgnoreCase(cliente.getNombre()) > 0) {
            break;
        }
        index++;
    }

    raiz.addContent(index, eCliente);
    guardar();
}


    //para recorrer el archivo y retornar todos los clientes
    public List<Cliente> findAll(){
        List<Cliente> clientes = new ArrayList<>();
        List<Element> eListaClientes = raiz.getChildren("cliente");

        for (Element eCliente : eListaClientes) {
            Cliente clienteActual = new Cliente();
            clienteActual.setIdCliente(eCliente.getAttributeValue("idCliente"));
            clienteActual.setNombre(eCliente.getChildText("nombre"));
            clienteActual.setTelefono(eCliente.getChildText("telefono"));
            clienteActual.setCelular(eCliente.getChildText("celular"));
            clienteActual.setDireccion(eCliente.getChildText("direccion"));
            clientes.add(clienteActual);
        }

        return clientes;
    }
    
    //actualiza en el archivo con la info del nuevo cliente 
   public boolean actualizarCliente(String idOriginal, Cliente clienteActualizado) throws IOException {
    if (raiz == null) {
        return false;
    }

    List<Element> elementosClientes = raiz.getChildren("cliente");
    Element clienteEncontradoElement = null;

    // Buscar el cliente por ID original
    for (Element e : elementosClientes) {
        String idExistente = e.getAttributeValue("idCliente"); 
        if (idExistente != null && idExistente.equalsIgnoreCase(idOriginal)) {
            clienteEncontradoElement = e;
            break;
        }
    }

    if (clienteEncontradoElement == null) {
        return false; // Cliente no encontrado
    }

    // NO se permite cambiar el ID, solo actualizar otros campos
    actualizarCampoSeguro(clienteEncontradoElement, "nombre", clienteActualizado.getNombre());
    actualizarCampoSeguro(clienteEncontradoElement, "telefono", clienteActualizado.getTelefono());
    actualizarCampoSeguro(clienteEncontradoElement, "celular", clienteActualizado.getCelular());
    actualizarCampoSeguro(clienteEncontradoElement, "direccion", clienteActualizado.getDireccion());

    guardar();
    return true;
}
   //Para evitar NullPointerException si el nodo no existe
private void actualizarCampoSeguro(Element padre, String nombreCampo, String nuevoValor) {
    Element campo = padre.getChild(nombreCampo);
    if (campo == null) {
        campo = new Element(nombreCampo);
        padre.addContent(campo);
    }
    campo.setText(nuevoValor);
}


        
        //eliminar del archivo un cliente, atributo por atributo -no se pide pero puede ser útil
        //que devuelva una lista con los clientes sin el eliminado?
      public void eliminar(Cliente clienteToDelete){
        List<Element> eListaClientes = raiz.getChildren("cliente");

        for (int i = 0; i < eListaClientes.size(); i++) {
            Element eCliente = eListaClientes.get(i);

            if (clienteToDelete.getIdCliente().equals(eCliente.getAttributeValue("id"))) {
                eListaClientes.remove(i);
                break; // salir, ya se eliminó
            }
        }
}
    public Cliente findById(String idBuscado) {
        if (raiz == null) {
            System.out.println("DEBUG: La raíz del documento de clientes es null en findById().");
            return null;
        }
        List<Element> eListaClientes = raiz.getChildren("cliente");
        for (Element eCliente : eListaClientes) {
          
            String idActual = eCliente.getAttributeValue("idCliente"); 

            if (idActual != null && idActual.equalsIgnoreCase(idBuscado)) {
                Cliente clienteEncontrado = new Cliente();
                clienteEncontrado.setIdCliente(idActual);
                clienteEncontrado.setNombre(eCliente.getChildText("nombre"));
                clienteEncontrado.setTelefono(eCliente.getChildText("telefono"));
                clienteEncontrado.setCelular(eCliente.getChildText("celular"));
                clienteEncontrado.setDireccion(eCliente.getChildText("direccion"));
                System.out.println("DEBUG: Cliente con ID " + idActual + " encontrado."); // Debug
                return clienteEncontrado;
            }
        }
        System.out.println("DEBUG: Cliente con ID " + idBuscado + " NO encontrado."); // Debug
        return null;
    }
}
