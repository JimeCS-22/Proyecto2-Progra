/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package cr.ac.ucr.paraiso.proyecto2.progra.data;

import cr.ac.ucr.paraiso.proyecto2.progra.domain.Cliente;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.JDOMException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Camila 
 */
public class ClienteXmlDataTest {
    //private String realPath;
    private String rutaDocumento;
    private List<Cliente> lista; 
    private ClienteXmlData instance;

    @BeforeEach
    public void setUp() {
        try {
            String baseWebapp = "src/main/webapp";
            this.rutaDocumento = baseWebapp + "/WEB-INF/archivos/clientes.xml";

            File archivo = new File(rutaDocumento);

            // Crear el archivo y cargar la instancia
            this.instance = ClienteXmlData.abrirDocumento(rutaDocumento);

        } catch (JDOMException | IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error en setUp: " + ex.getMessage());
        }
    }
    
    //@AfterEach
    public void tearDown() {
        File f = new File(rutaDocumento);
        if (f.exists()) {
            f.delete();
        }
    }

    /**
     * Test of insertarCliente method, of class ClienteXmlData.
     */
    @Test
    public void testInsertarCliente(){
        try {
            System.out.println("insertarCliente");
            Cliente cliente1 = new Cliente("001","Camila Montoya","86621670","85961620","Paraiso");
            Cliente cliente2 = new Cliente("002","Jimena Calvo","87821670","85961621","Cartago");
            Cliente cliente3 = new Cliente("003", "Mateo Sanabria", "89991670", "85961622", "San José");
            Cliente cliente4 = new Cliente("004", "María Solis", "81231670", "85961623", "Alajuela");
            Cliente cliente5 = new Cliente("005", "Carlos Montero", "83451670", "85961624", "Heredia");
            Cliente cliente6 = new Cliente("006", "Daniela Jimenez", "85671670", "85961625", "Limón");
            Cliente cliente7 = new Cliente("007", "José Blanco", "87781670", "85961626", "Puntarenas");
            Cliente cliente8 = new Cliente("008", "Sofía Barboza", "88891670", "85961627", "Cartago");
            Cliente cliente9 = new Cliente("009", "Esteban Oporta", "81101670", "85961628", "San José");
            Cliente cliente10 = new Cliente("010", "Valeria Martinéz", "82211670", "85961629", "Paraiso");
            ClienteXmlData instance = new ClienteXmlData(rutaDocumento);
            instance.insertarCliente(cliente1);
            instance.insertarCliente(cliente2);
            instance.insertarCliente(cliente3);
            instance.insertarCliente(cliente4);
            instance.insertarCliente(cliente5);
            instance.insertarCliente(cliente6);
            instance.insertarCliente(cliente7);
            instance.insertarCliente(cliente8);
            instance.insertarCliente(cliente9);
            instance.insertarCliente(cliente10);
            
            lista = instance.findAll();
            assertFalse(lista.isEmpty());
            assertEquals("001", lista.get(0).getIdCliente());
        } catch (JDOMException ex) {
            Logger.getLogger(ClienteXmlDataTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClienteXmlDataTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of findAll method, of class ClienteXmlData.
     */
    @Test
    public void testFindAll() {
        try {
            ClienteXmlData instance = new ClienteXmlData(rutaDocumento);
            lista = instance.findAll();
            //assertFalse(lista.isEmpty());
            assertEquals("001", lista.get(0).getIdCliente());
        } catch (JDOMException ex) {
            Logger.getLogger(ClienteXmlDataTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClienteXmlDataTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of actualizar method, of class ClienteXmlData.
     */
    @Test
    public void testActualizar() {
       
        try {
            Cliente c = new Cliente("010", "Sofia Velgara", "82211670", "85961629", "L.A");
            Cliente cn = new Cliente("010", "Valeria Gúzman", "82211670", "85961629", "Paraiso");
            System.out.println("actualizar");
       
            Cliente nuevoCliente = c;
            Cliente clienteActual = cn;
            ClienteXmlData instance = new ClienteXmlData(rutaDocumento);
            
            if (instance.findById(c.getIdCliente()) == null) {
            instance.insertarCliente(c);
        }
            boolean actualiza = instance.actualizarCliente(nuevoCliente.getIdCliente(), clienteActual);
            System.out.println("Resultado de la actualización: "+ actualiza);
        } catch (JDOMException ex) {
            Logger.getLogger(ClienteXmlDataTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClienteXmlDataTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

  
    
}
