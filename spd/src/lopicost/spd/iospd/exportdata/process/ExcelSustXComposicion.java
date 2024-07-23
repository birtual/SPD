package lopicost.spd.iospd.exportdata.process;

import java.util.List;

import org.apache.poi.hssf.usermodel.*;

import lopicost.spd.model.SustXComposicion;
import lopicost.spd.struts.form.SustXComposicionForm;

public class ExcelSustXComposicion {

    public HSSFWorkbook createWorkbook(SustXComposicionForm formulari, List list) throws Exception {

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("User Data");

        /**
         * Setting the width of the first three columns.
         */
        sheet.setColumnWidth(0, 3500);
        sheet.setColumnWidth(1, 7500);
        sheet.setColumnWidth(2, 5000);

        /**
         * Style for the header cells.
         */
        HSSFCellStyle headerCellStyle = wb.createCellStyle();
        HSSFFont boldFont = wb.createFont();
        boldFont.setBold(false);
        headerCellStyle.setFont(boldFont);

        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellStyle(headerCellStyle);
        cell.setCellValue(new HSSFRichTextString("User Name"));
        cell = row.createCell(1);
        cell.setCellStyle(headerCellStyle);
        cell.setCellValue(new HSSFRichTextString("Email Id"));
        cell = row.createCell(2);
        cell.setCellStyle(headerCellStyle);
        cell.setCellValue(new HSSFRichTextString("Location"));

        for (int index = 1; index < list.size(); index++) {
            row = sheet.createRow(index);
            SustXComposicion trat = (SustXComposicion) list.get(index);
            
            cell = row.createCell(0);cell.setCellValue(trat.getNombreLab());
            cell = row.createCell(1);cell.setCellValue(trat.getCodGtVmpp());

            HSSFRichTextString grupoTerapeutico = new HSSFRichTextString(trat.getGrupoTerapeutico()); cell.setCellValue(grupoTerapeutico);
            
            cell = row.createCell(2);
            HSSFRichTextString resiNombrePaciente = new HSSFRichTextString(trat.getPonderacion()+"");
            cell.setCellValue(resiNombrePaciente);
            
          
        
        
        }

        
        
        return wb;
    }
}