package lopicost.spd.iospd.exportdata.process;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;

import lopicost.spd.helium.model.Dose;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.form.FicheroResiForm;
import lopicost.spd.utils.SPDConstants;

public class ExcelFicheroResiDetallePlantUnifLite {

    public HSSFWorkbook createWorkbook(FicheroResiForm formulari, List list) throws Exception {

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("data");
        // Crear un estilo de celda con el color deseado
        
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        
        HSSFCellStyle styleAqua = wb.createCellStyle();         styleAqua.cloneStyleFrom(cellStyle);
        HSSFCellStyle styleGreen = wb.createCellStyle();        styleGreen.cloneStyleFrom(cellStyle);
        HSSFCellStyle styleOrange = wb.createCellStyle();		styleOrange.cloneStyleFrom(cellStyle);
        HSSFCellStyle styleRed = wb.createCellStyle();        	styleRed.cloneStyleFrom(cellStyle);
        HSSFCellStyle styleYellow = wb.createCellStyle();       styleYellow.cloneStyleFrom(cellStyle);
        
        styleAqua.setFillForegroundColor(IndexedColors.AQUA.index); // Color de fondo para cabecera
        styleOrange.setFillForegroundColor(IndexedColors.ORANGE.index); // Color de fondo para cabecera
        styleGreen.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.index); // Color de fondo para cabecera
        styleRed.setFillForegroundColor(IndexedColors.RED.index); // Color de fondo rojo para alertas
        styleYellow.setFillForegroundColor(IndexedColors.YELLOW.index); // Color de fondo amarillo para las columnas de spd Cn-Nombre-Accion
        //styleGreen.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleAqua.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleGreen.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleOrange.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleRed.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleYellow.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        /**
         * Setting the width of the first three columns.
         */
        int nCol = 0; //indice para columnas
        sheet.setColumnWidth(nCol, 3500); nCol++;	//resi
        sheet.setColumnWidth(nCol, 7500); nCol++;//idproceso
        sheet.setColumnWidth(nCol, 4000); nCol++;	//CIP
        sheet.setColumnWidth(nCol, 8000); nCol++;//NombrePaciente
        sheet.setColumnWidth(nCol, 2000); nCol++;//CN_RESI
        sheet.setColumnWidth(nCol, 8000); nCol++;	//MEDICAMENTO_resi
        sheet.setColumnWidth(nCol, 1000); nCol++;//posologia
        sheet.setColumnWidth(nCol, 3000); nCol++;	//fechaDesde
        sheet.setColumnWidth(nCol, 3000); nCol++;	//fechaHasta
        sheet.setColumnWidth(nCol, 5000); nCol++;	//Observaciones
        sheet.setColumnWidth(nCol, 5000); nCol++;//Comentarios
        sheet.setColumnWidth(nCol, 2000); nCol++;//CN
        sheet.setColumnWidth(nCol, 5000); nCol++;//NombreProducto
        sheet.setColumnWidth(nCol, 5000); nCol++;	//FORMA
        sheet.setColumnWidth(nCol, 5000); nCol++;	//tipoBolsa
        sheet.setColumnWidth(nCol, 700); nCol++;	//L
        sheet.setColumnWidth(nCol, 700); nCol++;	//M
        sheet.setColumnWidth(nCol, 700); nCol++;	//X
        sheet.setColumnWidth(nCol, 700); nCol++;	//J
        sheet.setColumnWidth(nCol, 700); nCol++;	//V
        sheet.setColumnWidth(nCol, 700); nCol++;	//V	
        sheet.setColumnWidth(nCol, 700); nCol++;	//S
        sheet.setColumnWidth(nCol, 700); nCol++;	//D
   /*     sheet.setColumnWidth(23, 600);	//Primera hora
        sheet.setColumnWidth(24, 600);	//esmorzar
        sheet.setColumnWidth(25, 600);	//dinar
        sheet.setColumnWidth(26, 600);	//berenar
        sheet.setColumnWidth(27, 600);	//sopar
        sheet.setColumnWidth(28, 600);	//Ressopó
        sheet.setColumnWidth(29, 600);	//21:30h
        sheet.setColumnWidth(30, 600);	//22h
        sheet.setColumnWidth(31, 8000);	//Mensaje INFO
        sheet.setColumnWidth(32, 8000);	//Mensaje ALERTA*/

        	//Periodicidad	¿Es excepción?	Incidencia	Estado linea	Marcado Aut?	ResultLog	Nombre	Apellido1Paciente	Apellido2Paciente	FechaDesde	FechaHasta

        /**
         * Style for the header cells.
         */
        //HSSFCellStyle headerCellStyle = wb.createCellStyle();
        //HSSFFont boldFont = wb.createFont();
        //boldFont.setBold(false);
        //headerCellStyle.setFont(boldFont);

          
        int i =0;
        int t=2;//primera fila, para buscarv
        //segunda cabecera, con los diferentes campos
        HSSFRow row = sheet.createRow(0);
       // row.setRowStyle(rowStyle);
        HSSFCell cell = null;
 
        
        cell = row.createCell(i);   cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("resi")); i++;
        cell = row.createCell(i);   cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("idproceso")); i++;
        cell = row.createCell(i);   cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("CIP")); i++;
        cell = row.createCell(i);   cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("NombrePaciente")); i++;
        cell = row.createCell(i);	cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("CN_RESI")); i++;
        cell = row.createCell(i);   cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("MEDICAMENTO_resi")); i++;
        cell = row.createCell(i);   cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("posologia")); i++;
        cell = row.createCell(i);   cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("Fecha Desde")); i++;
        
        
        cell = row.createCell(i);   cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("FechaHasta")); i++;
        cell = row.createCell(i);	cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("Observaciones")); i++;
        cell = row.createCell(i);	cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("Comentarios")); i++;
        cell = row.createCell(i);   cell.setCellStyle(styleYellow); cell.setCellValue(new HSSFRichTextString("CN"));  i++;
        cell = row.createCell(i);   cell.setCellStyle(styleYellow); cell.setCellValue(new HSSFRichTextString("NombreProducto"));  i++;
        cell = row.createCell(i);   cell.setCellStyle(styleYellow); cell.setCellValue(new HSSFRichTextString("FORMA"));  i++;
        cell = row.createCell(i);   cell.setCellStyle(styleYellow); cell.setCellValue(new HSSFRichTextString("tipoBolsa"));  i++;
        cell = row.createCell(i);	cell.setCellStyle(styleOrange); cell.setCellValue(new HSSFRichTextString("L")); i++;
        cell = row.createCell(i);	cell.setCellStyle(styleOrange); cell.setCellValue(new HSSFRichTextString("M")); i++;
        cell = row.createCell(i);   cell.setCellStyle(styleOrange); cell.setCellValue(new HSSFRichTextString("X")); i++;
        cell = row.createCell(i);   cell.setCellStyle(styleOrange); cell.setCellValue(new HSSFRichTextString("J")); i++;
        cell = row.createCell(i);   cell.setCellStyle(styleOrange); cell.setCellValue(new HSSFRichTextString("V")); i++;
        cell = row.createCell(i);   cell.setCellStyle(styleOrange); cell.setCellValue(new HSSFRichTextString("S")); i++;
        cell = row.createCell(i);	cell.setCellStyle(styleOrange); cell.setCellValue(new HSSFRichTextString("D")); i++;
        
              
       
        
        List<Dose> doses = formulari.getListaTomasCabecera();
        int maxDoses = doses.size(); // Número máximo de celdas a mostrar
        
        for (int index = 0; index < maxDoses; index++) {
        	sheet.setColumnWidth(nCol, 900);	//Primera hora
        	nCol++;
            cell = row.createCell(i);
            cell.setCellStyle(cellStyle); 
            cell.setCellStyle(styleGreen); 
            i++;

            if (doses != null && doses.size() > index) {
                Dose dose = doses.get(index);
                cell.setCellValue(dose.getNombreDose());
            } else {
                cell.setCellValue(new HSSFRichTextString("T" + (index + 1)));
            }
        }
        
       // cell = row.createCell(i);	cell.setCellStyle(styleOrange); cell.setCellValue(new HSSFRichTextString("detalleRow")); i++;
       
        
        //resto columnas de info
        //sheet.setColumnWidth(nCol, 8000); nCol++;	//Mensaje INFO
        //sheet.setColumnWidth(nCol, 8000); nCol++;	//Mensaje ALERTA
        /*
        cell = row.createCell(i);   cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("Mensaje INFO"));    i++;      
        cell = row.createCell(i);	cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("key")); i++;
        cell = row.createCell(i);	cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("keyBuscarv")); i++;      
      
        cell = row.createCell(i);   cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("Mensaje ALERTA"));   i++;       
        cell = row.createCell(i);   cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("Periodicidad"));   i++;       
        cell = row.createCell(i);   cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("¿Es excepción?"));  i++;
        cell = row.createCell(i);   cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("Incidencia"));  i++;
        cell = row.createCell(i);   cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("Estado linea")) ;     i++;   
        cell = row.createCell(i);   cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("Marcado Aut?")); i++;
        cell = row.createCell(i);   cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("ResultLog"));     i++;     
        cell = row.createCell(i);   cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("Nombre")); i++;
        cell = row.createCell(i);   cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("Apellido1Paciente")); i++;
        cell = row.createCell(i);   cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("Apellido2Paciente")); i++;
        cell = row.createCell(i);	cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("FechaDesde")); i++;
        cell = row.createCell(i);	cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("FechaHasta")); i++;
       
          */
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
       //aplicamos estilo en la cabecera
        for (int z = 0; z < row.getLastCellNum(); z++) {
        	if(!row.getCell(z).getCellStyle().equals(styleOrange) && !row.getCell(z).getCellStyle().equals(styleGreen) )
            row.getCell(z).setCellStyle(styleAqua);
        }
        
        
        // Crear un estilo de celda para el formato de fecha
        HSSFCellStyle cellStyleDate = wb.createCellStyle();
        cellStyleDate.cloneStyleFrom(cellStyle);
        CreationHelper createHelper = wb.getCreationHelper();
        cellStyleDate.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));

        
        
        
        for (int index = 0; index < list.size(); index++) {
        	
            row = sheet.createRow(index+1);
           // row.setRowStyle(rowStyle);
          // String key = "";
            
            FicheroResiBean trat = (FicheroResiBean) list.get(index);
            i =0;
            cell = row.createCell(i);       cell.setCellStyle(cellStyle);  cell.setCellValue(trat.getIdDivisionResidencia()); i++;
            cell = row.createCell(i);       cell.setCellStyle(cellStyle);  cell.setCellValue(trat.getIdProceso()); i++;
            cell = row.createCell(i);       cell.setCellStyle(cellStyle);  cell.setCellValue(trat.getResiCIP()); i++;
            cell = row.createCell(i);       cell.setCellStyle(cellStyle);  cell.setCellValue(trat.getResiApellidosNombre()); i++;
            cell = row.createCell(i);		cell.setCellStyle(cellStyle);  cell.setCellValue(trat.getResiCn()); i++;
            cell = row.createCell(i);     	cell.setCellStyle(cellStyle);  cell.setCellValue(trat.getResiMedicamento()); i++;
            
            String valorPosologia=trat.getResiSiPrecisa();
         
            try{
            	if(formulari.getIdDivisionResidencia()!=null && formulari.getIdDivisionResidencia().contentEquals("hestia_stauros"))
                	valorPosologia=(trat.getResiHabitacion()!=null?trat.getResiHabitacion():"")+(trat.getResiPlanta()!=null?trat.getResiPlanta():"");
            }
            catch(Exception e){}

            cell = row.createCell(i);     	cell.setCellStyle(cellStyle);  cell.setCellValue(valorPosologia); i++;
                
 	        
                
            String fechaDesde =trat.getResiInicioTratamiento();
            		if(trat.getResiInicioTratamientoParaSPD()!=null && !trat.getResiInicioTratamientoParaSPD().equals(""))
            			fechaDesde=trat.getResiInicioTratamientoParaSPD();
           String fechaHasta =trat.getResiFinTratamiento();
            		if(trat.getResiFinTratamientoParaSPD()!=null && !trat.getResiFinTratamientoParaSPD().equals(""))
            			fechaHasta=trat.getResiFinTratamientoParaSPD();

           Date fecha = null;
           cell = row.createCell(i);   
           cell.setCellStyle(cellStyleDate);  
         //fecha DESDE
           try { fecha = sdf.parse(fechaDesde);	} catch (ParseException e) {}
           if (fecha != null) {
        	   cell.setCellStyle(cellStyleDate);
            	cell.setCellValue(fecha);
            } else {            	cell.setCellValue(fechaDesde);
            }
           i++;
            
           //fecha HASTA
           fecha = null;
           cell = row.createCell(i);   
           cell.setCellStyle(cellStyleDate);  
           try { fecha = sdf.parse(fechaHasta);	} catch (ParseException e) {}
           if (fecha != null) {
        	   cell.setCellStyle(cellStyleDate);
            	cell.setCellValue(fecha);
            } else { cell.setCellValue(fechaHasta);
            }
           i++;

            
            
           // cell = row.createCell(i);       cell.setCellValue(fechaDesde); i++;
           //  cell = row.createCell(i);       cell.setCellValue(fechaHasta); i++;
           
            
/*            + " - " + trat.getResiFinTratamiento()
             cell = row.createCell(i);		
             String observacionesComentarios=trat.getResiObservaciones()+ " /// " +trat.getResiComentarios();
            
            if(observacionesComentarios.startsWith(" /// "))
            	observacionesComentarios=observacionesComentarios.replace(" /// ","");
            
            cell.setCellValue(observacionesComentarios);  i++;
            
            String variantePeriodo =trat.getResiPeriodo()+ " /// " +trat.getResiVariante();
            if(variantePeriodo.startsWith(" /// "))
            	variantePeriodo=variantePeriodo.replace(" /// ","");
            cell.setCellValue(variantePeriodo); i++;
            
 */											
            cell = row.createCell(i);      	cell.setCellStyle(cellStyle);  cell.setCellValue(trat.getResiObservaciones()+ " /// " + trat.getResiVariante());   i++;
            if(cell.getStringCellValue().startsWith(" /// "))
            	cell.setCellValue(cell.getStringCellValue().replaceAll(" /// ", "").replaceAll("null", ""));
            
           
            cell = row.createCell(i);		cell.setCellStyle(cellStyle);  cell.setCellValue(trat.getResiComentarios());  i++;
            
            //key+= trat.getResiCIP()+"_"+trat.getResiCn()+"_"+trat.getResiSiPrecisa()+"_"+fechaDesde+"_"+fechaHasta+"_"+trat.getResiObservaciones()+"_"+trat.getResiComentarios()+"_";
            //key+= trat.getSpdCnFinal()+"_"+trat.getSpdNombreBolsa()+"_"+trat.getSpdAccionBolsa()+"_";
            //key+= trat.getResiD1()+"_"+trat.getResiD2()+"_"+trat.getResiD3()+"_"+trat.getResiD4()+"_"+trat.getResiD5()+"_"+trat.getResiD6()+"_"+trat.getResiD7();
                      
            cell = row.createCell(i);      	cell.setCellStyle(styleYellow);	cell.setCellValue(trat.getSpdCnFinal());  i++;
            cell = row.createCell(i);      	cell.setCellStyle(styleYellow);	cell.setCellValue(trat.getSpdNombreBolsa());  i++;
            cell = row.createCell(i);      	cell.setCellStyle(styleYellow);	cell.setCellValue(trat.getSpdFormaMedicacion());  i++;
            
            //Para el tratamiento de la plantilla unificada enviamos NO_PINTAR en el caso de los SI_PRECISA
            if(trat.getSpdAccionBolsa()!=null && trat.getSpdAccionBolsa().equalsIgnoreCase(SPDConstants.SPD_ACCIONBOLSA_SI_PRECISA))
            	trat.setSpdAccionBolsa(SPDConstants.SPD_ACCIONBOLSA_NO_PINTAR);
            	
           	cell = row.createCell(i);      cell.setCellStyle(styleYellow);	cell.setCellValue(trat.getSpdAccionBolsa());  i++;
                      
            cell = row.createCell(i);		cell.setCellStyle(cellStyle);  cell.setCellValue(trat.getResiD1()); i++;
            cell = row.createCell(i);		cell.setCellStyle(cellStyle);  cell.setCellValue(trat.getResiD2()); i++;
            cell = row.createCell(i);      	cell.setCellStyle(cellStyle);  cell.setCellValue(trat.getResiD3()); i++;
            cell = row.createCell(i);      	cell.setCellStyle(cellStyle);  cell.setCellValue(trat.getResiD4()); i++;
            cell = row.createCell(i);      	cell.setCellStyle(cellStyle);  cell.setCellValue(trat.getResiD5()); i++;
            cell = row.createCell(i);      	cell.setCellStyle(cellStyle);  cell.setCellValue(trat.getResiD6()); i++;
            cell = row.createCell(i);		cell.setCellStyle(cellStyle);  cell.setCellValue(trat.getResiD7()); i++;
            
            
            int numTomas = doses.size(); // Número total de tomas
            Class<?> tratClass = trat.getClass();

            try {
                for (int toma = 1; toma <= numTomas; toma++) {
                    cell = row.createCell(i);
                    cell.setCellStyle(cellStyle);  
                    Method method = tratClass.getMethod("getResiToma" + toma);
                    Object value = method.invoke(trat);
                    cell.setCellValue(value != null ? value.toString() : ""); // Ajusta el valor si es nulo
                    i++;
                   // key+= "_"+value;
                    		 
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace(); // Manejo de excepciones, ajusta según tus necesidades
            }
            
            
          //  cell = row.createCell(i);      	cell.setCellStyle(cellStyle);  cell.setCellValue(trat.getDetalleRow());   i++;
            //cell = row.createCell(i);      	cell.setCellStyle(cellStyle);  cell.setCellValue(trat.getMensajesInfo());   i++;       

            
            /*
            key=StringUtil.makeFlat(key.replace("/", "").replace(" ", "").replace(",50", ",5").replace(",", "").replace(".", ""), true);
            cell = row.createCell(i);		cell.setCellStyle(cellStyle);  cell.setCellValue(key); i++;
            
            cell = row.createCell(i);		cell.setCellStyle(cellStyle);  cell.setCellValue("=BUSCARV(AP"+t+";robot!Ac:Ac;1;0)"); i++;t++;

            //resto columnas de info
            cell = row.createCell(i);      	cell.setCellStyle(cellStyle);  cell.setCellValue(trat.getMensajesAlerta()); i++;         
            cell = row.createCell(i);		cell.setCellStyle(cellStyle);  cell.setCellValue(trat.getResiPeriodo()+ " /// " +trat.getDiasMesConcretos()+ " /// " +trat.getSecuenciaGuide());  i++;
            if(cell.getStringCellValue().startsWith(" /// ") || cell.getStringCellValue().endsWith(" /// "))
            	cell.setCellValue(cell.getStringCellValue().replaceAll(" /// ", " ").replaceAll("null", " "));
            cell = row.createCell(i);      	cell.setCellStyle(cellStyle);  cell.setCellValue(trat.getEsExcepcion());  i++;
            cell = row.createCell(i);      	cell.setCellStyle(cellStyle);  cell.setCellValue(trat.getIncidencia());  i++;
            if(cell.getStringCellValue().equalsIgnoreCase("SI"))
            	cell.setCellStyle(styleRed);
            	//{
                // Aplicar el estilo a toda la fila
               // for (int z = 0; z < row.getLastCellNum(); z++) {
            	//     row.getCell(z).setCellStyle(styleRed);
            	//}
            	//}
            cell = row.createCell(i);      	cell.setCellStyle(cellStyle);  cell.setCellValue(trat.getIdEstado()) ;     i++;    
            cell = row.createCell(i);      	cell.setCellStyle(cellStyle);  cell.setCellValue(trat.getResiDiasAutomaticos());   i++;      
            cell = row.createCell(i);      	cell.setCellStyle(cellStyle);  cell.setCellValue(trat.getResultLog());       i++;   
            cell = row.createCell(i);       cell.setCellStyle(cellStyle);  cell.setCellValue(trat.getResiNombrePaciente()); i++;
            cell = row.createCell(i);       cell.setCellStyle(cellStyle);  cell.setCellValue(trat.getResiApellido1()); i++;
            cell = row.createCell(i);       cell.setCellStyle(cellStyle);  cell.setCellValue(trat.getResiApellido2()); i++;
            cell = row.createCell(i);		cell.setCellStyle(cellStyle);  cell.setCellValue(trat.getFechaDesde()); i++;
            cell = row.createCell(i);		cell.setCellStyle(cellStyle);  cell.setCellValue(trat.getFechaHasta()); i++;
            */
           }

        
        return wb;
    }
}