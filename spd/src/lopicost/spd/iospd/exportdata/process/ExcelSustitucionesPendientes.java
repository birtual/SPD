package lopicost.spd.iospd.exportdata.process;

import java.util.List;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.IndexedColors;


import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.form.FicheroResiForm;

public class ExcelSustitucionesPendientes {

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
        sheet.setColumnWidth(0, 5000);	//resi
        sheet.setColumnWidth(4, 3000);	//CN_RESI
        sheet.setColumnWidth(4, 3000);	//CN_RESI
        sheet.setColumnWidth(5, 10000);	//MEDICAMENTO_resi
        sheet.setColumnWidth(11, 3000);	//SPD_CN
        sheet.setColumnWidth(12, 10000);	//SPD_NombreProducto
        sheet.setColumnWidth(13, 5000);	//SPD_FORMA
        sheet.setColumnWidth(14, 5000);	//SPD_tipoBolsa
        sheet.setColumnWidth(14, 5000);	//excepciones
        sheet.setColumnWidth(14, 5000);	//aux

        	
        /**
         * Style for the header cells.
         */

        int i =0;
        int t=2;//primera fila, para buscarv
        //segunda cabecera, con los diferentes campos
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = null;

        cell = row.createCell(i);   cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("resi")); i++;
        cell = row.createCell(i);	cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("CN_RESI")); i++;
        cell = row.createCell(i);	cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("CN_RESI")); i++;
        cell = row.createCell(i);   cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("MEDICAMENTO_resi")); i++;
        cell = row.createCell(i);   cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("CN sugerido"));  i++;
        cell = row.createCell(i);   cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("NombreProducto sugerido"));  i++;
        cell = row.createCell(i);   cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("FORMA"));  i++;
        cell = row.createCell(i);   cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("Accion sugerida"));  i++;
        cell = row.createCell(i);   cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("Excepciones"));  i++;
        cell = row.createCell(i);   cell.setCellStyle(cellStyle); cell.setCellValue(new HSSFRichTextString("nomGtVmp"));  i++;               
      
  
        for (int index = 0; index < list.size(); index++) {
            row = sheet.createRow(index+1);
            
            FicheroResiBean trat = (FicheroResiBean) list.get(index);
            i =0;
            cell = row.createCell(i);       cell.setCellStyle(cellStyle); cell.setCellValue(trat.getIdDivisionResidencia()); i++;
            cell = row.createCell(i);		cell.setCellStyle(cellStyle); cell.setCellValue(trat.getResiCn()); i++;
            cell = row.createCell(i);		cell.setCellStyle(cellStyle); cell.setCellValue(trat.getResiCn()); i++;
            cell = row.createCell(i);       cell.setCellStyle(cellStyle); cell.setCellValue(trat.getResiMedicamento()); i++;
            cell = row.createCell(i);      	cell.setCellStyle(cellStyle); cell.setCellValue(trat.getSpdCnFinal()); i++;
            cell = row.createCell(i);      	cell.setCellStyle(cellStyle); cell.setCellValue(trat.getSpdNombreBolsa()); i++;
            cell = row.createCell(i);      	cell.setCellStyle(cellStyle); cell.setCellValue(trat.getSpdFormaMedicacion()); i++;
         	cell = row.createCell(i);      	cell.setCellStyle(cellStyle); cell.setCellValue(trat.getSpdAccionBolsa()); i++;
         	cell = row.createCell(i);      	cell.setCellStyle(cellStyle); cell.setCellValue(""); i++;
         	cell = row.createCell(i);      	cell.setCellStyle(cellStyle); cell.setCellValue(trat.getSpdNomGtVmp());  i++;
   
          }

        
        return wb;
    }
}