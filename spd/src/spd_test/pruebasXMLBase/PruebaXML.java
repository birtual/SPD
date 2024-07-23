package spd_test.pruebasXMLBase;




import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;


public class PruebaXML {
    static final private Logger LOGGER = Logger.getLogger("spd_test.pruebasXML");
    
    public static void main(String[] args) {
        try {
            EjemploXML ejemploXML = new EjemploXML();
            Document documento = ejemploXML.crearDocumento();
            
            System.out.println(ejemploXML.convertirString(documento));
            
            ejemploXML.escribirArchivo(documento, "ejemplo.xml");            
            
        } catch (ParserConfigurationException ex) {
            LOGGER.log(Level.SEVERE, "Error de configuracion");
            LOGGER.log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            LOGGER.log(Level.SEVERE, "Error de transformacion XML a String");
            LOGGER.log(Level.SEVERE, null, ex);
        }
        
    }
}