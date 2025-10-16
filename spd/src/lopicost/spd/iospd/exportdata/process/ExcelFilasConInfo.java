package lopicost.spd.iospd.exportdata.process;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.IndexedColors;


import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.form.FicheroResiForm;

public class ExcelFilasConInfo {

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
        
        
        

        /**
         * Setting the width of the first three columns.
         */
        sheet.setColumnWidth(0, 4000);	//idProceso
        sheet.setColumnWidth(0, 4000);	//idResidencia
        sheet.setColumnWidth(1, 4000);	//CIP
        sheet.setColumnWidth(2, 9000);	//Nombre residente
        sheet.setColumnWidth(3, 3000);	//CN_RESI
        sheet.setColumnWidth(4, 15000);	//MEDICAMENTO_resi
        sheet.setColumnWidth(5, 30000);	//Mensaje resi
        sheet.setColumnWidth(6, 30000);	//Mensaje interno
        sheet.setColumnWidth(7, 5000);	//Otros

        	
        /**
         * Style for the header cells.
         */

        
        int i =0;
        int t=2;//primera fila, para buscarv
        //segunda cabecera, con los diferentes campos
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = null;

        cell = row.createCell(i);   cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("Residencia")); i++;
        cell = row.createCell(i);   cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("Proceso")); i++;
        cell = row.createCell(i);	cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("CIP")); i++;
        cell = row.createCell(i);	cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("Nombre residente")); i++;
        cell = row.createCell(i);	cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("CN Residencia")); i++;
        cell = row.createCell(i);   cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("Medicamento residencia")); i++;
        cell = row.createCell(i);   cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("Mensaje para la residencia"));  i++;
        cell = row.createCell(i);   cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("Mensaje interno "));  i++;
        cell = row.createCell(i);   cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("Otros "));  i++;
      
  
        for (int index = 0; index < list.size(); index++) {
            row = sheet.createRow(index+1);
            
            FicheroResiBean trat = (FicheroResiBean) list.get(index);
            i =0;
            cell = row.createCell(i);       cell.setCellStyle(cellStyle); cell.setCellValue(trat.getIdDivisionResidencia()); i++;
            cell = row.createCell(i);       cell.setCellStyle(cellStyle); cell.setCellValue(trat.getIdProceso()); i++;
            cell = row.createCell(i);		cell.setCellStyle(cellStyle); cell.setCellValue(trat.getResiCIP()); i++;
            cell = row.createCell(i);		cell.setCellStyle(cellStyle); cell.setCellValue(trat.getResiApellidosNombre()); i++;
            cell = row.createCell(i);		cell.setCellStyle(cellStyle); cell.setCellValue(trat.getResiCn()); i++;
            cell = row.createCell(i);       cell.setCellStyle(cellStyle); cell.setCellValue(trat.getResiMedicamento()); i++;
            
            /**
             * sustituimos los 1# para que no aparezcan en el Excel
             */
            // patrón de búsqueda
            String patron = "\\d+#";
            // Crear un objeto Pattern y un objeto Matcher
            Pattern pattern = Pattern.compile(patron);
            Matcher matcher = pattern.matcher(trat.getMensajesResidencia());
            // Reemplazar todas las coincidencias
            String mensajesResidencia = matcher.replaceAll("");
            
            
            cell = row.createCell(i);      	cell.setCellStyle(cellStyle); cell.setCellValue(mensajesResidencia); i++;
            cell = row.createCell(i);      	cell.setCellStyle(cellStyle); cell.setCellValue(trat.getMensajesInfo()); i++;
            cell = row.createCell(i);      	cell.setCellStyle(cellStyle); cell.setCellValue(trat.getMensajesAlerta()); i++;
   
          }

   



        
        return wb;
    }
}