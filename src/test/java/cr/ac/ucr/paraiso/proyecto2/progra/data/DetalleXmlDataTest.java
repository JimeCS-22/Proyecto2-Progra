/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package cr.ac.ucr.paraiso.proyecto2.progra.data;

import cr.ac.ucr.paraiso.proyecto2.progra.domain.DetalleOrdenTrabajo;
import cr.ac.ucr.paraiso.proyecto2.progra.domain.Repuesto;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.JDOMException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Camila M
 */
public class DetalleXmlDataTest {
    private String realPath;
    private String rutaDocumento;
    private List<Repuesto> repuestos;
    private List<DetalleOrdenTrabajo> detalles;
    
    @BeforeEach
    public void setUp() {
         String baseWebapp = "src/main/webapp";
        this.rutaDocumento = "/WEB-INF/archivos/detalles.xml";
        File f = new File(baseWebapp + rutaDocumento);
          
        this.realPath = f.getAbsolutePath();
        try {
            // Crear archivo si no existe
            if (!f.exists()) {
                f.createNewFile(); // Crear el archivo vacío
            }
            ClienteXmlData.abrirDocumento(realPath);
            
        } catch (JDOMException ex) {
            Logger.getLogger(ClienteXmlDataTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClienteXmlDataTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    public void clear() {
        File f = new File(realPath);
        if (f.exists()) {
            f.delete();
        }
    }
  
    /**
     * Test of insertarDetalle method, of class DetalleXmlData.
     */
    @Test
    public void testInsertarDetalle() throws Exception {
        System.out.println("Insertar Detalle Test");
        repuestos = new ArrayList<Repuesto>();
         detalles = new ArrayList<DetalleOrdenTrabajo>();
        repuestos.add(new Repuesto(3,"Manguera",25000.00,true));
        System.out.println(repuestos);
        DetalleOrdenTrabajo detalle = new DetalleOrdenTrabajo("01",1, "Se utilizaron 3","Repuesto","En proceso",null, repuestos);
        DetalleXmlData instance = new DetalleXmlData(realPath);
        instance.insertarDetalle(detalle);
        detalles.add(detalle);//add to list to find all
        System.out.println("Se insertó: "+ detalle);
    }

    /**
     * Test of findAll method, of class DetalleXmlData.
     */
    @Test
    public void findAllWorks() {
        try {
            
            System.out.println("Find All Detalles");
            detalles = new ArrayList<DetalleOrdenTrabajo>();
            DetalleXmlData instance = new DetalleXmlData(realPath);
            instance.clear();
            DetalleOrdenTrabajo detalle = new DetalleOrdenTrabajo("02",5, "Se utilizaron 4","Repuesto","Completado",null, repuestos);
            detalles.add(detalle);//add to list
            List<DetalleOrdenTrabajo> expResult = detalles;
            instance.insertarDetalle(detalle);
            
        
            List<DetalleOrdenTrabajo> result = instance.findAll();
            System.out.println(result);
            //assertEquals(expResult, result);
            //assertEquals(2, result.size()); compara si el tamaño es igual
            //assertEquals(detalle.getIdDetalle(), result.get(0).getIdDetalle());
            
        } catch (JDOMException ex) {
            Logger.getLogger(DetalleXmlDataTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DetalleXmlDataTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    //Si no se quiere comparar listas enteras, podés validar los tamaños o los campos clave:
//assertEquals(1, result.size());
//assertEquals(detalle.getIdDetalle(), result.get(0).getIdDetalle());
}
