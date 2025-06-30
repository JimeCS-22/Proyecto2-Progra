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

    //Constructor que se encarga de crear el DOM y el documento XML
    private ClienteXmlData(String rutaDocumento, String nombreRaiz) throws IOException, JDOMException {
        File file = new File(rutaDocumento);
        if (file.exists()) {
            abrirDocumento(rutaDocumento);
        }else{
            ClienteXmlData autorData = new ClienteXmlData(rutaDocumento);
        }
        this.rutaDocumento = rutaDocumento ;
        this.raiz = new Element(nombreRaiz);
        this.document = new Document(raiz);
        guardar();
    }

    //Constructor que se encarga de parsear el documento XML
    public ClienteXmlData(String rutaDocumento) throws JDOMException, IOException {
        this.rutaDocumento = rutaDocumento;
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setIgnoringElementContentWhitespace(true);
        //parseo
        this.document =saxBuilder.build(rutaDocumento);
        this.raiz = document.getRootElement();
        this.rutaDocumento = rutaDocumento;
    }

    public static ClienteXmlData abrirDocumento(String rutaDocumento)throws JDOMException, IOException{
        if (new File(rutaDocumento).exists() == true) {
            return new ClienteXmlData(rutaDocumento);

        }else return new ClienteXmlData(rutaDocumento,"clientes");

    }

    private void guardar() throws FileNotFoundException, IOException{
        Format format = Format.getPrettyFormat();
        format.setEncoding("UTF-8");//Es buena práctica especificar la codificación
        XMLOutputter xmlOutputter = new XMLOutputter();
        PrintWriter printWriter = new PrintWriter(this.rutaDocumento);
        xmlOutputter.output(this.document, new PrintWriter(this.rutaDocumento));
        printWriter.close();//cerrarlo

        //imprimir en consola el DOM
        xmlOutputter.output(this.document,System.out);
    }

    public void insertarCliente(Cliente cliente) throws  IOException{
        //se trabaja con los 5 atributos e inserta al final
         //Verificar si ya existe un cliente con el mismo ID
        List<Element> listaClientes = raiz.getChildren("clientes");

        for (Element eCliente : listaClientes) {
            if (cliente.getIdCliente().equals(eCliente.getAttributeValue("idCliente"))) {
                System.out.println("Ya existe un cliente con ese ID, no se insertará.");
                return; // Sale del método sin insertar
            }
        }

        Element eCliente = new Element("cliente"); 
        eCliente.setAttribute("idCliente", String.valueOf(cliente.getIdCliente()));

        Element eNombre = new Element("nombre");
        eNombre.addContent(cliente.getNombre());

        Element eTelefono = new Element("telefono");
        eTelefono.addContent(cliente.getTelefono());

        Element eCelular = new Element("celular");
        eCelular.addContent(cliente.getCelular());

        Element eDireccion = new Element("direccion");
        eDireccion.addContent(cliente.getDireccion()); 

        eCliente.addContent(eNombre);
        eCliente.addContent(eTelefono);
        eCliente.addContent(eCelular);
        eCliente.addContent(eDireccion);

        raiz.addContent(eCliente);
        guardar();
    }//insertar

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
    
    //actualiza en el archivo con la info del nuevo cliente - TO DO REVISAR 
    public void actualizar(Cliente nuevoCliente, Cliente clienteActual){
        List<Element> eListaClientes = raiz.getChildren("cliente");

        for (Element eCliente : eListaClientes) {
            //si el id del nuevo cliente esta en la lista pasa a actualizar los atributos
            if(nuevoCliente.getIdCliente().equals(String.valueOf(eCliente.getAttribute("idCliente"))) ){
      
            clienteActual.setIdCliente(eCliente.getAttributeValue("idCliente"));
            clienteActual.setNombre(eCliente.getChildText("nombre"));
            clienteActual.setTelefono(eCliente.getChildText("telefono"));
            clienteActual.setCelular(eCliente.getChildText("celular"));
            clienteActual.setDireccion(eCliente.getChildText("direccion"));
            //clientes.add(clienteActual);
        }
           
        }
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
    //Metodo para buscar al cliente por ID
    public Cliente findById(String idBuscado) {
        if (raiz == null) {
            return null;
        }
        List<Element> eListaClientes = raiz.getChildren("cliente");
        for (Element eCliente : eListaClientes) {
            String idActual = eCliente.getChildText("id_cliente"); 
            if (idActual != null && idActual.equalsIgnoreCase(idBuscado)) {
                Cliente clienteEncontrado = new Cliente();
                clienteEncontrado.setIdCliente(idActual);
                clienteEncontrado.setNombre(eCliente.getChildText("nombre"));
                clienteEncontrado.setTelefono(eCliente.getChildText("telefono"));
                clienteEncontrado.setCelular(eCliente.getChildText("celular"));
                clienteEncontrado.setDireccion(eCliente.getChildText("direccion"));
                return clienteEncontrado;
            }
        }
        return null;
    }
}
