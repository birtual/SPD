package spd_test;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import lopicost.config.pool.dbaccess.Conexion;

import java.sql.*;

public class SQLtoXMLConverter {

    public static void main(String[] args) throws ClassNotFoundException {
        try {
            // Conexión a la base de datos
        	Connection connection = Conexion.conectar();
           // Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=nombreBaseDatos", "usuario", "contraseña");
            Statement statement = connection.createStatement();

            // Consulta SQL
            String sqlQuery = "SELECT resiCIP, resiNombrePaciente FROM SPD_ficheroResiDetalle where idProceso='20230605_20230611_168529204' and resiCIP='CASA1400205005'";

            // Ejecutar consulta
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            // Crear un DocumentBuilderFactory y un DocumentBuilder
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // Crear un Documento XML
            Document doc = docBuilder.newDocument();

            // Crear el elemento raíz del XML
            Element rootElement = doc.createElement("RootNode");
            doc.appendChild(rootElement);

            // Recorrer el resultado de la consulta y agregar elementos al XML
            while (resultSet.next()) {
                // Crear un elemento para cada fila
                Element rowElement = doc.createElement("Row");
                rootElement.appendChild(rowElement);

                // Obtener los valores de las columnas y agregarlos como elementos hijos
                String columna1 = resultSet.getString("resiCIP");
                String columna2 = resultSet.getString("resiNombrePaciente");

                Element columna1Element = doc.createElement("resiCIP");
                columna1Element.appendChild(doc.createTextNode(columna1));
                rowElement.appendChild(columna1Element);

                Element columna2Element = doc.createElement("resiNombrePaciente");
                columna2Element.appendChild(doc.createTextNode(columna2));
                rowElement.appendChild(columna2Element);
            }

            // Crear un objeto TransformerFactory y un Transformer para generar el XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            // Configurar opciones de formato del XML
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            // Generar el XML y guardarlo en un archivo o mostrarlo en la consola
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(System.out); // Mostrar el XML en la consola
            transformer.transform(source, result);

            // Cerrar la conexión a la base de datos
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException | ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }
}
