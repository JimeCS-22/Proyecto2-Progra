/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.paraiso.proyecto2.progra.data;

import cr.ac.ucr.paraiso.proyecto2.progra.domain.Cliente;
import cr.ac.ucr.paraiso.proyecto2.progra.domain.Vehiculos;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jimena
 */
public class VehiculosXmlData {

    private Document document;
    private Element raiz;
    private String rutaDocumento;
    private ClienteXmlData clienteData; 

    
    private VehiculosXmlData(String rutaDocumento, String nombreRaiz) throws IOException, JDOMException {
        File file = new File(rutaDocumento);
        if (file.exists() && file.length() > 0) { 
            SAXBuilder saxBuilder = new SAXBuilder();
            saxBuilder.setIgnoringElementContentWhitespace(true);
            this.document = saxBuilder.build(new File(rutaDocumento));
            this.raiz = document.getRootElement();
        } else {
            this.raiz = new Element(nombreRaiz);
            this.document = new Document(raiz);
            guardar(); // Guarda el archivo inicial vacío
        }
        this.rutaDocumento = rutaDocumento;
       
    }

  
    public VehiculosXmlData(String rutaDocumento) throws JDOMException, IOException {
        this.rutaDocumento = rutaDocumento;
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setIgnoringElementContentWhitespace(true);
        this.document = saxBuilder.build(new File(rutaDocumento)); 
        this.raiz = document.getRootElement();
        this.rutaDocumento = rutaDocumento; 
    }

   
    public static VehiculosXmlData abrirDocumento(String rutaVehiculosXML, String rutaClientesXML) throws JDOMException, IOException {
        File file = new File(rutaVehiculosXML);
        VehiculosXmlData instance;
        if (file.exists() && file.length() > 0) {
            instance = new VehiculosXmlData(rutaVehiculosXML);
        } else {
            instance = new VehiculosXmlData(rutaVehiculosXML, "vehiculos");
        }
        instance.clienteData = ClienteXmlData.abrirDocumento(rutaClientesXML);
        return instance;
    }

    
    private void guardar() throws FileNotFoundException, IOException {
        Format format = Format.getPrettyFormat();
        format.setEncoding("UTF-8");
        XMLOutputter xmlOutputter = new XMLOutputter(format);
        try (PrintWriter printWriter = new PrintWriter(this.rutaDocumento)) {
            xmlOutputter.output(this.document, printWriter);
        }
       
    }

    public void insertarVehiculo(Vehiculos vehiculo) throws IOException {
        if (findByPlaca(vehiculo.getPlaca()) != null) {
            System.out.println("Ya existe un vehículo con placa '" + vehiculo.getPlaca() + "', no se insertará.");
            return;
        }
        
        Element eVehiculo = new Element("vehiculo");

        Element ePlaca = new Element("placa");
        ePlaca.setText(vehiculo.getPlaca());
        eVehiculo.addContent(ePlaca);

        Element eCliente = new Element("cliente");
        if (vehiculo.getCliente() != null) {
            eCliente.addContent(new Element("id_cliente").setText(vehiculo.getCliente().getIdCliente()));
            eCliente.addContent(new Element("nombre").setText(vehiculo.getCliente().getNombre()));
            eCliente.addContent(new Element("telefono").setText(vehiculo.getCliente().getTelefono()));
           
        } else {
            eCliente.addContent(new Element("id_cliente").setText("N/A"));
            eCliente.addContent(new Element("nombre").setText("N/A")); 
            eCliente.addContent(new Element("telefono").setText("N/A")); 
        }
        eVehiculo.addContent(eCliente);

        raiz.addContent(eVehiculo);
        guardar();
    }

   
    public List<Vehiculos> findAll() {
        List<Vehiculos> vehiculos = new ArrayList<>();
        if (raiz == null) {
            return vehiculos;
        }

        List<Element> listaVehiculos = raiz.getChildren("vehiculo");

        for (Element eVehiculo : listaVehiculos) {
            String placa = eVehiculo.getChildText("placa");

            Cliente cliente = null;
            Element eCliente = eVehiculo.getChild("cliente"); 
            if (eCliente != null) {
                String idCliente = eCliente.getChildText("id_cliente"); 

              
                if (idCliente != null && !idCliente.equals("N/A") && !idCliente.isEmpty()) {
                    if (clienteData != null) { 
                        cliente = clienteData.findById(idCliente);
                    }
                }

                if (cliente == null) {
                    String nombreClienteAnidado = eCliente.getChildText("nombre");
                    String telefonoClienteAnidado = eCliente.getChildText("telefono");
                    
                    cliente = new Cliente();
                    cliente.setIdCliente(idCliente != null ? idCliente : "N/A"); 
                    cliente.setNombre(nombreClienteAnidado != null ? nombreClienteAnidado : "N/A");
                    cliente.setTelefono(telefonoClienteAnidado != null ? telefonoClienteAnidado : "N/A");
          
                }
            }
            vehiculos.add(new Vehiculos(placa, cliente)); 
        }
        return vehiculos;
    }

   
    public Vehiculos findByPlaca(String placaBuscada) {
        if (raiz == null) {
            return null;
        }
        List<Element> elementosVehiculos = raiz.getChildren("vehiculo");
        for (Element e : elementosVehiculos) {
            String placa = e.getChildText("placa");
            if (placa != null && placa.equalsIgnoreCase(placaBuscada)) {
                String idCliente = null;
                Element clienteElement = e.getChild("cliente");
                if (clienteElement != null) {
                    idCliente = clienteElement.getChildText("id_cliente");
                }

                Cliente clienteAsociado = null;
                
                if (idCliente != null && !idCliente.isEmpty() && !idCliente.equals("N/A") && clienteData != null) {
                    clienteAsociado = clienteData.findById(idCliente);
                }

                if (clienteAsociado == null) {
                    String nombreAnidado = clienteElement != null ? clienteElement.getChildText("nombre") : null;
                    String telefonoAnidado = clienteElement != null ? clienteElement.getChildText("telefono") : null;

                    clienteAsociado = new Cliente();
                    clienteAsociado.setIdCliente(idCliente != null ? idCliente : "N/A");
                    clienteAsociado.setNombre(nombreAnidado != null ? nombreAnidado : "N/A");
                    clienteAsociado.setTelefono(telefonoAnidado != null ? telefonoAnidado : "N/A");
                    
                }
                return new Vehiculos(placa, clienteAsociado);
            }
        }
        return null;
    }

   
     public boolean actualizarVehiculo(String placaOriginal, Vehiculos vehiculoActualizado) throws IOException, JDOMException {
        if (raiz == null) {
            return false;
        }

        List<Element> elementosVehiculos = raiz.getChildren("vehiculo");
        Element vehiculoEncontradoElement = null;

        for (Element e : elementosVehiculos) {
            String placaExistente = e.getChildText("placa");
            if (placaExistente != null && placaExistente.equalsIgnoreCase(placaOriginal)) {
                vehiculoEncontradoElement = e;
                break;
            }
        }

        if (vehiculoEncontradoElement == null) {
            return false; // Vehículo con la placa original no encontrado
        }

        // Si la placa ha cambiado, verificar que la nueva placa no exista ya en otro vehículo
        if (!placaOriginal.equalsIgnoreCase(vehiculoActualizado.getPlaca())) {
            for (Element otherVehiculo : elementosVehiculos) {
                // Asegurarse de no compararlo consigo mismo (elemento original)
                if (otherVehiculo != vehiculoEncontradoElement && otherVehiculo.getChildText("placa").equalsIgnoreCase(vehiculoActualizado.getPlaca())) {
                    return false; // La nueva placa ya existe en otro vehículo
                }
            }
        }

        // Actualizar la placa (si ha cambiado)
        vehiculoEncontradoElement.getChild("placa").setText(vehiculoActualizado.getPlaca());

        // Actualizar la información del cliente asociado
        Element clienteElement = vehiculoEncontradoElement.getChild("cliente");
        if (clienteElement == null) {
            clienteElement = new Element("cliente");
            vehiculoEncontradoElement.addContent(clienteElement);
        }

        Element idClienteEl = clienteElement.getChild("id_cliente");
        if (idClienteEl == null) {
            idClienteEl = new Element("id_cliente");
            clienteElement.addContent(idClienteEl);
        }
        Element nombreEl = clienteElement.getChild("nombre");
        if (nombreEl == null) {
            nombreEl = new Element("nombre");
            clienteElement.addContent(nombreEl);
        }
        Element telefonoEl = clienteElement.getChild("telefono");
        if (telefonoEl == null) {
            telefonoEl = new Element("telefono");
            clienteElement.addContent(telefonoEl);
        }

        if (vehiculoActualizado.getCliente() != null) {
            idClienteEl.setText(vehiculoActualizado.getCliente().getIdCliente());
            nombreEl.setText(vehiculoActualizado.getCliente().getNombre());
            telefonoEl.setText(vehiculoActualizado.getCliente().getTelefono());
        } else {
            // Si por alguna razón el cliente se desvincula o es nulo
            idClienteEl.setText("N/A");
            nombreEl.setText("N/A");
            telefonoEl.setText("N/A");
        }
        
        guardar();
        return true;
    }

    
    public void clear() throws IOException {
        File file = new File(rutaDocumento);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Archivo XML de vehículos eliminado: " + rutaDocumento);
            } else {
                System.err.println("No se pudo eliminar el archivo XML de vehículos: " + rutaDocumento);
            }
        } else {
            System.out.println("El archivo XML de vehículos no existe: " + rutaDocumento);
        }
        this.raiz = new Element("vehiculos"); 
        this.document = new Document(raiz); 
        guardar(); 
    }
}
