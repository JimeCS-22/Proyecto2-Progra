/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.paraiso.proyecto2.progra.data;

import cr.ac.ucr.paraiso.proyecto2.progra.domain.Repuesto;
import java.io.File;
import java.io.FileWriter;
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
 * @author jimen
 */
public class RepuestosXmlData {

    private Document document;
    private Element raiz;
    private String rutaDocumento;

    public RepuestosXmlData(String rutaDocumento) throws JDOMException, IOException {
        this.rutaDocumento = rutaDocumento;
        File file = new File(rutaDocumento);
        if (file.exists() && file.length() > 0) {
            SAXBuilder builder = new SAXBuilder();
            builder.setIgnoringElementContentWhitespace(true);
            this.document = builder.build(file);
            this.raiz = document.getRootElement();
        } else {
            this.raiz = new Element("Repuestos");
            this.document = new Document(raiz);
            guardarDocumento(); 
        }
    }

    private void guardarDocumento() throws IOException {
        XMLOutputter xmlOutput = new XMLOutputter();
        xmlOutput.setFormat(Format.getPrettyFormat());

        try (PrintWriter printWriter = new PrintWriter(this.rutaDocumento)) {
            xmlOutput.output(document, printWriter);
        }

    }

    public void insertarRepuesto(Repuesto repuesto) throws IOException {

        List<Element> listaRepuestos = raiz.getChildren("Repuesto");
        for (Element eRepuesto : listaRepuestos) {
            if (repuesto.getNombreRepuesto().equalsIgnoreCase(eRepuesto.getChildText("nombreRepuesto"))) {
                System.out.println("Ya existe un repuesto con ese nombre: " + repuesto.getNombreRepuesto() + ", no se insertará.");
                return;
            }
        }

        Element eRepuesto = new Element("Repuesto");

        Element eCantidad = new Element("cantidad");
        eCantidad.addContent(String.valueOf(repuesto.getCantidad()));

        Element eNombreRepuesto = new Element("nombreRepuesto");
        eNombreRepuesto.addContent(repuesto.getNombreRepuesto());

        Element ePrecio = new Element("precio");
        ePrecio.addContent(String.valueOf(repuesto.getPrecio()));

        Element eFuePedido = new Element("fuePedido");
        eFuePedido.addContent(String.valueOf(repuesto.isFuePedido()));

        eRepuesto.addContent(eCantidad);
        eRepuesto.addContent(eNombreRepuesto);
        eRepuesto.addContent(ePrecio);
        eRepuesto.addContent(eFuePedido);

        raiz.addContent(eRepuesto);
        guardarDocumento();
    }

    public List<Repuesto> findAll() {
        List<Repuesto> repuestos = new ArrayList<>();
        List<Element> eListaRepuestos = raiz.getChildren("Repuesto");

        for (Element eRepuesto : eListaRepuestos) {
            Repuesto repuestoActual = new Repuesto();
            repuestoActual.setCantidad(Integer.parseInt(eRepuesto.getChildText("cantidad")));
            repuestoActual.setNombreRepuesto(eRepuesto.getChildText("nombreRepuesto"));
            repuestoActual.setPrecio(Double.parseDouble(eRepuesto.getChildText("precio")));
            repuestoActual.setFuePedido(Boolean.parseBoolean(eRepuesto.getChildText("fuePedido")));
            repuestos.add(repuestoActual);
        }
        return repuestos;
    }

    public void actualizarRepuesto(Repuesto repuestoActualizado) throws IOException {
        List<Element> eListaRepuestos = raiz.getChildren("Repuesto");

        for (Element eRepuesto : eListaRepuestos) {
            if (repuestoActualizado.getNombreRepuesto().equalsIgnoreCase(eRepuesto.getChildText("nombreRepuesto"))) {
                eRepuesto.getChild("cantidad").setText(String.valueOf(repuestoActualizado.getCantidad()));
                eRepuesto.getChild("precio").setText(String.valueOf(repuestoActualizado.getPrecio()));
                eRepuesto.getChild("fuePedido").setText(String.valueOf(repuestoActualizado.isFuePedido()));
                guardarDocumento();
                System.out.println("Repuesto '" + repuestoActualizado.getNombreRepuesto() + "' actualizado exitosamente.");
                return;
            }
        }
        System.out.println("No se encontró el repuesto '" + repuestoActualizado.getNombreRepuesto() + "' para actualizar.");
    }


    public Repuesto findByNombre(String nombreBuscado) {
        if (raiz == null) {
            return null;
        }
        List<Element> eListaRepuestos = raiz.getChildren("Repuesto");
        for (Element eRepuesto : eListaRepuestos) {
            String nombreActual = eRepuesto.getChildText("nombreRepuesto");
            if (nombreActual != null && nombreActual.equalsIgnoreCase(nombreBuscado)) {
                Repuesto repuestoEncontrado = new Repuesto();
                repuestoEncontrado.setCantidad(Integer.parseInt(eRepuesto.getChildText("cantidad")));
                repuestoEncontrado.setNombreRepuesto(nombreActual);
                repuestoEncontrado.setPrecio(Double.parseDouble(eRepuesto.getChildText("precio")));
                repuestoEncontrado.setFuePedido(Boolean.parseBoolean(eRepuesto.getChildText("fuePedido")));
                return repuestoEncontrado;
            }
        }
        return null;
    }

}
