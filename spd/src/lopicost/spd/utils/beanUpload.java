
package lopicost.spd.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.struts.upload.FormFile;

import lopicost.spd.model.DivisionResidencia;
import lopicost.spd.persistence.DivisionResidenciaDAO;

import java.util.Date;
import java.util.StringTokenizer;

public class beanUpload {
	
/*
 * filtroPorTipoDocumento :  si es true, aplica el filtro q especifica las extensiones de los ficheros aceptadas.
 * 							 si false, acepta todo tipo de documentos, sin importar si tipo
 * */
   public int UploadImage (FormFile file, StringBuffer nameFile, boolean filtroPorTipoDocumento, DivisionResidencia div)
   {
       String fileName= "";
       String ext = "";
       //retrieve the file representation
       boolean writeFile = false;
       int insertar = 1;
       Date fecha = new Date();
       String name = "file_" + fecha.getTime(); //nombre por defecto del fichero
       if(div!=null) 
    	   name = div.getIdDivisionResidencia() + "_" + fecha.getTime(); //nombre por defecto del fichero 
       if (existsFile(file))
        {
           //retrieve the file name
           fileName=file.getFileName();
           //retrieve the content type
           String contentType = file.getContentType();
           //retrieve the file size
           String size = (file.getFileSize() + " bytes");
           String data = null;
           //retrieve the file data
           ByteArrayOutputStream baos = new ByteArrayOutputStream();
           InputStream stream = null;
           try
           {
               stream= file.getInputStream();
           }
           catch (Exception e)
           {
               insertar = 5;
           }

           //Mirem si el fitxer a pujar té una extensió permesa
           try
           {
           	   if(filtroPorTipoDocumento){
	               String extensions = SPDConstants.FILEUPLOAD_EXTENSIONS;
	               StringTokenizer st = new StringTokenizer(extensions,",");
	               while (st.hasMoreTokens())
	               {
	                   if (fileName.indexOf(st.nextToken()) != -1)
	                   {
	                       writeFile = true;
	                       break;
	                   }
	               }  
           	   }
           	   else writeFile = true;

               //Mirem si el fitxer a pujar es mlt gran o no
               String tamFitxer = SPDConstants.FILEUPLOAD_MAXSIZE;
               if (file.getFileSize() > (Integer.parseInt(tamFitxer)*1024)) 
               {
                   writeFile=false;
                   return 3;
               }

               if (writeFile) {

                   //Mirem si el nom del la foto ja existeix
                   int i = 0;
                   int begin = fileName.lastIndexOf ('.');
                   int end = fileName.length ();
                   //String name = fileName.substring (0, begin);
                   ext = fileName.substring (begin, end);
                   //String cami = SPDConstants.FILEUPLOAD_REAL_PATH+"/"+SPDConstants.FILEUPLOAD_RELATIVE_PATH;
                   String cami = SPDConstants.PATH_DOCUMENTOS+SPDConstants.FILEUPLOAD_RELATIVE_PATH;
                   
                   String path = cami + "/" + name +ext;
                   while (new File (path).exists ())
                   {
                       path = cami + "/" + name + new Integer(++i).toString() + ext;
                       fileName= name + new Integer(i).toString() + ext;
                   }
                   OutputStream bos = new FileOutputStream(path);
                   int bytesRead = 0;
                   byte[] buffer = new byte[8192];
                   while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) 
                   {
                       bos.write(buffer, 0, bytesRead);
                   }
                   if (bytesRead>0)
                   	bos.write(buffer);
                   bos.close();
                   insertar = 1;
                   stream.close();
               }
               else
               {
                   return 2;
               }
           }
           catch (FileNotFoundException fnfe) 
           {
               return 4;
           }
           catch (IOException ioe) {
                    return 5;
           }
           catch (Exception e)
           {
               return 5;
           }
        }
        else
        {
           insertar = 4;
        }
        nameFile.append(SPDConstants.FILEUPLOAD_RELATIVE_PATH+"/"+name+ext);
        return insertar;
   }
   
   private boolean existsFile(FormFile file)
   {
       return (file!=null && file.getFileSize()>0);
   }

}
