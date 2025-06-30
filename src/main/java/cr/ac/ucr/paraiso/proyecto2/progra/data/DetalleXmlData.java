/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.paraiso.proyecto2.progra.data;

import cr.ac.ucr.paraiso.proyecto2.progra.domain.Cliente;
import cr.ac.ucr.paraiso.proyecto2.progra.domain.DetalleOrdenTrabajo;
import cr.ac.ucr.paraiso.proyecto2.progra.domain.Repuesto;
import cr.ac.ucr.paraiso.proyecto2.progra.domain.Servicio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author Camila
 */
public class DetalleXmlData {
    
    private Document document;
    private Element raiz;
    private String rutaDocumento;

    //Constructor que se encarga de crear el DOM y el documento XML
    private DetalleXmlData(String rutaDocumento, String nombreRaiz) throws IOException, JDOMException {
        File file = new File(rutaDocumento);
        if (file.exists()) {
            abrirDocumento(rutaDocumento);
        }else{
            DetalleXmlData autorData = new DetalleXmlData(rutaDocumento);
        }
        this.rutaDocumento = rutaDocumento ;
        this.raiz = new Element(nombreRaiz);
        this.document = new Document(raiz);
        guardar();
    }

    //Constructor que se encarga de parsear el documento XML
    public DetalleXmlData(String rutaDocumento) throws JDOMException, IOException {
        this.rutaDocumento = rutaDocumento;
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setIgnoringElementContentWhitespace(true);
        //parseo
        this.document =saxBuilder.build(rutaDocumento);
        this.raiz = document.getRootElement();
        this.rutaDocumento = rutaDocumento;
    }

    public static DetalleXmlData abrirDocumento(String rutaDocumento)throws JDOMException, IOException{
        if (new File(rutaDocumento).exists() == true) {
            return new DetalleXmlData(rutaDocumento);

        }else return new DetalleXmlData(rutaDocumento,"clientes");

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

    public void insertarDetalle(DetalleOrdenTrabajo detalle) throws  IOException{
       // Validación para evitar duplicados por idDetalle
    List<Element> eListaDetalles = raiz.getChildren("detalle");
    for (Element eDetalleExistente : eListaDetalles) {
        String idExistente = eDetalleExistente.getAttributeValue("idDetalle");
        if (idExistente != null && idExistente.equals(detalle.getIdDetalle())) {
            System.out.println(" Ya existe un detalle con el ID: " + idExistente + ". No se puede agregar.");
            return; // Salir sin insertar
        }
    }
        //se trabaja con los 5 atributos + servicio y repuesto e inserta al final
        Element eDetalle = new Element("detalle");
        eDetalle.addContent(detalle.getIdDetalle());
    
        Element eCantidad = new Element("cantidad");
        eCantidad.addContent(String.valueOf(detalle.getCantidad())); //es int entonces parseo
        Element eObservaciones = new Element("observaciones");
        eObservaciones.addContent(detalle.getObservaciones());
        Element eTipoDetalle = new Element("tipoDetalle");
        eTipoDetalle.addContent(detalle.getTipoDetalle());
        Element eStatus = new Element("estado");
        eStatus.addContent(detalle.getEstado());
        
      Element eServicio = new Element("servicios");
       for (Servicio s : detalle.getServicio()) {
            Element eS = new Element("servicio");
            eS.addContent(s.getDescripcion()); // ajustá según tus atributos
            eServicio.addContent(eS);
        }

        Element eRepuesto = new Element("repuestos");
        for (Repuesto r : detalle.getRepuesto()) {
            Element eR = new Element("repuesto");
            eR.addContent(r.getNombreRepuesto()); // ajustá según tus atributos
            eRepuesto.addContent(eR);
        }
        eDetalle.setAttribute("idDetalle",String.valueOf(detalle.getIdDetalle()));
        eDetalle.addContent(eCantidad);
        eDetalle.addContent(eObservaciones);
        eDetalle.addContent(eTipoDetalle);
        eDetalle.addContent(eStatus);
        eDetalle.addContent(eServicio);
        eDetalle.addContent(eRepuesto);
        
        raiz.addContent(eDetalle);
        guardar();
    }//insertar

    //para recorrer el archivo y retornar todos los clientes
    public List<DetalleOrdenTrabajo> findAll() {
    List<DetalleOrdenTrabajo> detalles = new ArrayList<>();

    Element eListaDetalles = raiz.getChild("detalles");
    if (eListaDetalles == null) {
        return detalles; // Si no hay detalles, lista vacía
    }

    List<Element> listaDetalles = eListaDetalles.getChildren("detalle");

    for (Element eDetalle : listaDetalles) {
        DetalleOrdenTrabajo dActual = new DetalleOrdenTrabajo();
        dActual.setIdDetalle(eDetalle.getAttributeValue("idDetalle"));
        dActual.setCantidad(Integer.parseInt(eDetalle.getChildText("cantidad")));
        dActual.setObservaciones(eDetalle.getChildText("observaciones"));
        dActual.setTipoDetalle(eDetalle.getChildText("tipoDetalle"));
        dActual.setEstado(eDetalle.getChildText("estado"));

        // Servicios
        List<Servicio> servicios = new ArrayList<>();
        Element eServicios = eDetalle.getChild("servicios");
        if (eServicios != null) {
            for (Element eService : eServicios.getChildren("servicio")) {
                Servicio service = new Servicio();
                service.setDescripcion(eService.getChildText("descripcion"));
                service.setPrecio(Double.parseDouble(eService.getChildText("precio")));
                service.setCostoManoObra(Double.parseDouble(eService.getChildText("costoManoObra")));
                servicios.add(service);
            }
        }
        dActual.setServicio(servicios);

        // Repuestos
        List<Repuesto> repuestos = new ArrayList<>();
        Element eRepuestos = eDetalle.getChild("repuestos");
        if (eRepuestos != null) {
            for (Element eRepuesto : eRepuestos.getChildren("repuesto")) {
                Repuesto repuesto = new Repuesto();
                repuesto.setCantidad(Integer.parseInt(eRepuesto.getChildText("cantidad")));
                repuesto.setNombreRepuesto(eRepuesto.getChildText("nombreRepuesto"));
                repuesto.setPrecio(Double.parseDouble(eRepuesto.getChildText("precio")));
                repuesto.setFuePedido(Boolean.parseBoolean(eRepuesto.getChildText("fuePedido")));
                repuestos.add(repuesto);
            }
        }
        dActual.setRepuesto(repuestos);

        detalles.add(dActual);
    }

    return detalles;
}

    
     public void clear() {
       File file = new File(rutaDocumento);
        if (file.exists()) {
           file.delete();
          }
     } 
}
