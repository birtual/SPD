package spd_test.pruebasXML;

import java.io.File;
import java.io.StringWriter;
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;

import lopicost.spd.robot.model.Bottle;
import lopicost.spd.robot.model.DrugDM;
import lopicost.spd.robot.model.FiliaDM;


public class PruebaXML {
    static final private Logger LOGGER = Logger.getLogger("spd_test.pruebasXML");
    
    /*public static void main(String[] args) {
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
        
    }*/

        public void main(String[] args) {
            try {
                // Crear objetos y configurar datos
                Bottle bottle1 = new Bottle();
                bottle1.setBarcode("8470006500071");
                bottle1.setOneBottleQuantity(100);

                DrugDM drug1 = new DrugDM();
                drug1.setCode("650007");
                drug1.setName("DEPAKINE 200MG");
                drug1.setStockLocation("");
                drug1.setBottle(bottle1);

                Bottle bottle2 = new Bottle();
                bottle2.setBarcode("8470006506196");
                bottle2.setOneBottleQuantity(28);

                DrugDM drug2 = new DrugDM();
                drug2.setCode("650619");
                drug2.setName("SIMVASTATINA 20MG");
                drug2.setStockLocation("");
                drug2.setBottle(bottle2);

                Bottle bottle3 = new Bottle();
                bottle3.setBarcode("8470006506202");
                bottle3.setOneBottleQuantity(28);

                DrugDM drug3 = new DrugDM();
                drug3.setCode("650620");
                drug3.setName("SIMVASTATINA 40MG");
                drug3.setStockLocation("");
                drug3.setBottle(bottle3);

                FiliaDM request = new FiliaDM();
                request.setRequestType(10);
                request.setDrugs(Arrays.asList(drug1, drug2, drug3));

                // Configurar JAXB y marshalling
                JAXBContext context = JAXBContext.newInstance(FiliaDM.class);
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

                // Escribir a un archivo
                File file = new File("filiaServiceRequest.xml");
                marshaller.marshal(request, file);

                System.out.println("Archivo XML generado exitosamente: " + file.getAbsolutePath());

                
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

}