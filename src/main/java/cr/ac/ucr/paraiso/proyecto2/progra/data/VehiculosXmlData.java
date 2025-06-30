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

    private VehiculosXmlData(String rutaDocumento, String nombreRaiz) throws IOException, JDOMException {

        File file = new File(rutaDocumento);

        if (file.exists()) {

            abrirDocumento(rutaDocumento);

        } else {

            VehiculosXmlData vehiculoData = new VehiculosXmlData(rutaDocumento);

        }

        this.rutaDocumento = rutaDocumento;
        this.raiz = new Element(nombreRaiz);
        this.document = new Document(raiz);

    }

    public VehiculosXmlData(String rutaDocumento) throws JDOMException, IOException {

        this.rutaDocumento = rutaDocumento;
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setIgnoringElementContentWhitespace(true);
        //parseo
        this.document = saxBuilder.build(rutaDocumento);
        this.raiz = document.getRootElement();
        this.rutaDocumento = rutaDocumento;

    }

    public static VehiculosXmlData abrirDocumento(String rutaDocumento) throws JDOMException, IOException {

        if (new File(rutaDocumento).exists() == true) {

            return new VehiculosXmlData(rutaDocumento);

        } else {

            return new VehiculosXmlData(rutaDocumento, "Vehiculos");

        }
    }

    private void guardar() throws FileNotFoundException, IOException {
        Format format = Format.getPrettyFormat();
        format.setEncoding("UTF-8");//Es buena práctica especificar la codificación
        XMLOutputter xmlOutputter = new XMLOutputter(format);
        try (PrintWriter printWriter = new PrintWriter(this.rutaDocumento)) {
            xmlOutputter.output(this.document, printWriter);
        }

    }

    public void insertarVehiculo(Vehiculos vehiculo) throws IOException {
        Element eVehiculo = new Element("vehiculo");

        Element ePlaca = new Element("placa");
        ePlaca.setText(vehiculo.getPlaca());
        eVehiculo.addContent(ePlaca);

        Element eCliente = new Element("cliente");
        if (vehiculo.getCliente() != null) {
    
            Element eClienteNombre = new Element("nombre");
            eClienteNombre.setText(vehiculo.getCliente().getNombre());
            eCliente.addContent(eClienteNombre);

            Element eClienteTelefono = new Element("telefono");
            eClienteTelefono.setText(vehiculo.getCliente().getTelefono()); 
            eCliente.addContent(eClienteTelefono);

        } else {
           
            eCliente.setText("N/A"); 
        }
        eVehiculo.addContent(eCliente);

        raiz.addContent(eVehiculo);
        guardar();
    }
    
    public List<Vehiculos> findAll() {
        List<Vehiculos> vehiculos = new ArrayList<>();
        
        List<Element> listaVehiculos = raiz.getChildren("vehiculo");

        for (Element eVehiculo : listaVehiculos) {
            String placa = eVehiculo.getChildText("placa");

            Cliente cliente = null;
            Element eCliente = eVehiculo.getChild("cliente");
            if (eCliente != null && !eCliente.getTextTrim().equals("N/A")) {
                
                String clienteId = eCliente.getChildText("id");
                String clienteNombre = eCliente.getChildText("nombre");
                String clienteTelefono = eCliente.getChildText("telefono");
                
                cliente = new Cliente(); 
                cliente.setIdCliente(clienteId);
                cliente.setNombre(clienteNombre);
                cliente.setTelefono(clienteTelefono);
            }

            vehiculos.add(new Vehiculos(placa, cliente));
        }
        return vehiculos;
    }

   
    public void clear() {
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
        // Asumiendo que "Vehiculos" es la raíz por defecto
        this.document = new Document(raiz);
    }

}
