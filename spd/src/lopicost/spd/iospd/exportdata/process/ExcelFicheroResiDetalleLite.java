package lopicost.spd.iospd.exportdata.process;

import java.util.List;

import org.apache.poi.hssf.usermodel.*;

import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.form.FicheroResiForm;

public class ExcelFicheroResiDetalleLite {

    public HSSFWorkbook createWorkbook(FicheroResiForm formulari, List list) throws Exception {

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("data");

        /**
         * Setting the width of the first three columns.
         */
        sheet.setColumnWidth(0, 3500);
        sheet.setColumnWidth(1, 7500);
        sheet.setColumnWidth(2, 5000);

        /**
         * Style for the header cells.
         */

        //primera cabecera, con nombre residencia e identificador del proceso	
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0); cell.setCellValue("Residencia: " + formulari.getIdDivisionResidencia());
        cell = row.createCell(1); cell.setCellValue("Proceso: "  + formulari.getFiltroProceso());
        
        //segunda cabecera, con los diferentes campos
        row = sheet.createRow(1);
        
        cell = row.createCell(0);       cell.setCellValue(new HSSFRichTextString("CIP"));
        cell = row.createCell(1);       cell.setCellValue(new HSSFRichTextString("Nombre paciente"));
        cell = row.createCell(2);		cell.setCellValue(new HSSFRichTextString("CN"));
        cell = row.createCell(3);       cell.setCellValue(new HSSFRichTextString("Medicamento resi"));
        cell = row.createCell(4);       cell.setCellValue(new HSSFRichTextString("Si Precisa"));
        cell = row.createCell(5);       cell.setCellValue(new HSSFRichTextString("Tratamiento Inicio"));
        cell = row.createCell(6);       cell.setCellValue(new HSSFRichTextString("Tratamiento Fin"));
        cell = row.createCell(7);		cell.setCellValue(new HSSFRichTextString("Producir SPD Desde"));
        cell = row.createCell(8);		cell.setCellValue(new HSSFRichTextString("Producir SPD Hasta"));
        cell = row.createCell(9);		cell.setCellValue(new HSSFRichTextString("Observaciones"));
        cell = row.createCell(10);		cell.setCellValue(new HSSFRichTextString("Variante"));
        cell = row.createCell(11);      cell.setCellValue(new HSSFRichTextString("Comentarios"));
        cell = row.createCell(12);		cell.setCellValue(new HSSFRichTextString("Periodo"));
        cell = row.createCell(13);		cell.setCellValue(new HSSFRichTextString("L"));
        cell = row.createCell(14);		cell.setCellValue(new HSSFRichTextString("M"));
        cell = row.createCell(15);      cell.setCellValue(new HSSFRichTextString("X"));
        cell = row.createCell(16);      cell.setCellValue(new HSSFRichTextString("J"));
        cell = row.createCell(17);      cell.setCellValue(new HSSFRichTextString("V"));
        cell = row.createCell(18);      cell.setCellValue(new HSSFRichTextString("S"));
        cell = row.createCell(19);		cell.setCellValue(new HSSFRichTextString("D"));
        cell = row.createCell(20);      cell.setCellValue(new HSSFRichTextString("Marcado Aut?"));      
        
        List doses = formulari.getListaTomasCabecera();
        cell = row.createCell(21);  
        if(doses!=null && doses.size()>0)	cell.setCellValue(doses.get(0).toString()); else cell.setCellValue(new HSSFRichTextString("T1"));
        	
        cell = row.createCell(22);
     	if(doses!=null && doses.size()>1)	cell.setCellValue(doses.get(1).toString());	else cell.setCellValue(new HSSFRichTextString("T2"));  
     	
        cell = row.createCell(23);      
     	if(doses!=null && doses.size()>2)	cell.setCellValue(doses.get(2).toString());	else cell.setCellValue(new HSSFRichTextString("T3"));
     	
        cell = row.createCell(24);    
        if(doses!=null && doses.size()>3)	cell.setCellValue(doses.get(3).toString());	else cell.setCellValue(new HSSFRichTextString("T4"));
        
        cell = row.createCell(25); 
        if(doses!=null && doses.size()>4)	cell.setCellValue(doses.get(4).toString());	else cell.setCellValue(new HSSFRichTextString("T5"));
        
        cell = row.createCell(26);
        if(doses!=null && doses.size()>5)	cell.setCellValue(doses.get(5).toString());	else cell.setCellValue(new HSSFRichTextString("T6"));
        
        cell = row.createCell(27);     
        if(doses!=null && doses.size()>6)	cell.setCellValue(doses.get(6).toString());	else cell.setCellValue(new HSSFRichTextString("T7"));

        cell = row.createCell(28);     
        if(doses!=null && doses.size()>7)	cell.setCellValue(doses.get(7).toString());	else cell.setCellValue(new HSSFRichTextString("T8"));

        cell = row.createCell(29);          
        if(doses!=null && doses.size()>8)	cell.setCellValue(doses.get(8).toString());	else cell.setCellValue(new HSSFRichTextString("T9"));
        
        cell = row.createCell(30);      
        if(doses!=null && doses.size()>9)	cell.setCellValue(doses.get(9).toString());	else cell.setCellValue(new HSSFRichTextString("T10"));

        cell = row.createCell(31);     
        if(doses!=null && doses.size()>10)	cell.setCellValue(doses.get(10).toString());	else cell.setCellValue(new HSSFRichTextString("T11"));

        cell = row.createCell(32);      
        if(doses!=null && doses.size()>11)	cell.setCellValue(doses.get(11).toString());	else cell.setCellValue(new HSSFRichTextString("T12"));

        cell = row.createCell(33);
        if(doses!=null && doses.size()>12)	cell.setCellValue(doses.get(12).toString());	else cell.setCellValue(new HSSFRichTextString("T13"));

        cell = row.createCell(34);
        if(doses!=null && doses.size()>13)	cell.setCellValue(doses.get(13).toString());	else cell.setCellValue(new HSSFRichTextString("T14"));

        cell = row.createCell(35);
        if(doses!=null && doses.size()>14)	cell.setCellValue(doses.get(14).toString());	else cell.setCellValue(new HSSFRichTextString("T15"));
        
        cell = row.createCell(36);
        if(doses!=null && doses.size()>15)	cell.setCellValue(doses.get(15).toString());	else cell.setCellValue(new HSSFRichTextString("T16"));
        
        cell = row.createCell(37);
        if(doses!=null && doses.size()>16)	cell.setCellValue(doses.get(16).toString());	else cell.setCellValue(new HSSFRichTextString("T17"));
        
        cell = row.createCell(38);
        if(doses!=null && doses.size()>17)	cell.setCellValue(doses.get(17).toString());	else cell.setCellValue(new HSSFRichTextString("T18"));
        
        cell = row.createCell(39);
        if(doses!=null && doses.size()>18)	cell.setCellValue(doses.get(18).toString());	else cell.setCellValue(new HSSFRichTextString("T19"));

        cell = row.createCell(40);
        if(doses!=null && doses.size()>19)	cell.setCellValue(doses.get(19).toString());	else cell.setCellValue(new HSSFRichTextString("T20"));

        cell = row.createCell(41);                 
        if(doses!=null && doses.size()>20)	cell.setCellValue(doses.get(20).toString());	else cell.setCellValue(new HSSFRichTextString("T21"));
        
        cell = row.createCell(42);
        if(doses!=null && doses.size()>21)	cell.setCellValue(doses.get(21).toString());	else cell.setCellValue(new HSSFRichTextString("T22"));
        
        cell = row.createCell(43);
        if(doses!=null && doses.size()>22)	cell.setCellValue(doses.get(22).toString());	else cell.setCellValue(new HSSFRichTextString("T23"));

        cell = row.createCell(44); 
        if(doses!=null && doses.size()>23)	cell.setCellValue(doses.get(23).toString());	else cell.setCellValue(new HSSFRichTextString("T24"));

        cell = row.createCell(45);      cell.setCellValue(new HSSFRichTextString("ResultLog"));         
        cell = row.createCell(46);      cell.setCellValue(new HSSFRichTextString("Mensaje INFO"));         
        cell = row.createCell(47);      cell.setCellValue(new HSSFRichTextString("Mensaje ALERTA"));         
        cell = row.createCell(48);      cell.setCellValue(new HSSFRichTextString("spdCnFinal")); 
        cell = row.createCell(49);      cell.setCellValue(new HSSFRichTextString("Nombre en bolsa")); 
        cell = row.createCell(50);      cell.setCellValue(new HSSFRichTextString("forma Medicacion")); 
        cell = row.createCell(51);      cell.setCellValue(new HSSFRichTextString("acción bolsa")); 
        cell = row.createCell(52);      cell.setCellValue(new HSSFRichTextString("¿Es excepción?")); 
        cell = row.createCell(53);      cell.setCellValue(new HSSFRichTextString("Incidencia")); 
        cell = row.createCell(54);      cell.setCellValue(new HSSFRichTextString("Estado linea")) ;       
         
        
        for (int index = 0; index < list.size(); index++) {
            row = sheet.createRow(index+2);
            FicheroResiBean trat = (FicheroResiBean) list.get(index);
            
            cell = row.createCell(0);       	cell.setCellValue(trat.getResiCIP());
            cell = row.createCell(1);       	cell.setCellValue(trat.getResiNombrePaciente());
            cell = row.createCell(2);			cell.setCellValue(trat.getResiCn());
            cell = row.createCell(3);       	cell.setCellValue(trat.getResiMedicamento());
            cell = row.createCell(4);       	cell.setCellValue(trat.getResiSiPrecisa());
            cell = row.createCell(5);       	cell.setCellValue(trat.getResiInicioTratamiento() + " - " + trat.getResiFinTratamiento());
            cell = row.createCell(6);       	cell.setCellValue(trat.getResiInicioTratamientoParaSPD() + " - " + trat.getResiFinTratamientoParaSPD());
            cell = row.createCell(7);			cell.setCellValue(trat.getFechaDesde());
            cell = row.createCell(8);			cell.setCellValue(trat.getFechaHasta());
            cell = row.createCell(9);			cell.setCellValue(trat.getResiObservaciones());
            cell = row.createCell(10);			cell.setCellValue(trat.getResiVariante());
            cell = row.createCell(11);      	cell.setCellValue(trat.getResiComentarios());
            cell = row.createCell(12);			cell.setCellValue(trat.getResiPeriodo());
            cell = row.createCell(13);			cell.setCellValue(trat.getResiD1());
            cell = row.createCell(14);			cell.setCellValue(trat.getResiD2());
            cell = row.createCell(15);      	cell.setCellValue(trat.getResiD3());
            cell = row.createCell(16);      	cell.setCellValue(trat.getResiD4());
            cell = row.createCell(17);      	cell.setCellValue(trat.getResiD5());
            cell = row.createCell(18);      	cell.setCellValue(trat.getResiD6());
            cell = row.createCell(19);			cell.setCellValue(trat.getResiD7());
            cell = row.createCell(20);      	cell.setCellValue(trat.getResiDiasAutomaticos());        
            cell = row.createCell(21);      	cell.setCellValue(trat.getResiToma1());  
            cell = row.createCell(22);     		cell.setCellValue(trat.getResiToma2());  
            cell = row.createCell(23);      	cell.setCellValue(trat.getResiToma3());  
            cell = row.createCell(24);      	cell.setCellValue(trat.getResiToma4());  
            cell = row.createCell(25);      	cell.setCellValue(trat.getResiToma5());  
            cell = row.createCell(26);      	cell.setCellValue(trat.getResiToma6());      
            cell = row.createCell(27);      	cell.setCellValue(trat.getResiToma7());  
            cell = row.createCell(28);      	cell.setCellValue(trat.getResiToma8());  
            cell = row.createCell(29);      	cell.setCellValue(trat.getResiToma9());                       
            cell = row.createCell(30);      	cell.setCellValue(trat.getResiToma10());   
            cell = row.createCell(31);      	cell.setCellValue(trat.getResiToma11());  
            cell = row.createCell(32);      	cell.setCellValue(trat.getResiToma12());     
            cell = row.createCell(33);      	cell.setCellValue(trat.getResiToma13());  
            cell = row.createCell(34);      	cell.setCellValue(trat.getResiToma14());  
            cell = row.createCell(35);      	cell.setCellValue(trat.getResiToma15());  
            cell = row.createCell(36);      	cell.setCellValue(trat.getResiToma16());  
            cell = row.createCell(37);      	cell.setCellValue(trat.getResiToma17());  
            cell = row.createCell(38);      	cell.setCellValue(trat.getResiToma18());     
            cell = row.createCell(39);      	cell.setCellValue(trat.getResiToma19());   
            cell = row.createCell(40);      	cell.setCellValue(trat.getResiToma20());  
            cell = row.createCell(41);      	cell.setCellValue(trat.getResiToma21());                 
            cell = row.createCell(42);      	cell.setCellValue(trat.getResiToma22());  
            cell = row.createCell(43);      	cell.setCellValue(trat.getResiToma23());  
            cell = row.createCell(44);      	cell.setCellValue(trat.getResiToma24());  
            cell = row.createCell(45);      	cell.setCellValue(trat.getResultLog());         
            cell = row.createCell(46);      	cell.setCellValue(trat.getMensajesInfo());         
            cell = row.createCell(47);      	cell.setCellValue(trat.getMensajesAlerta());         
            cell = row.createCell(48);      	cell.setCellValue(trat.getSpdCnFinal()); 
            cell = row.createCell(49);      	cell.setCellValue(trat.getSpdNombreBolsa()); 
            cell = row.createCell(50);      	cell.setCellValue(trat.getSpdFormaMedicacion()); 
            cell = row.createCell(51);      	cell.setCellValue(trat.getSpdAccionBolsa()); 
            cell = row.createCell(52);      	cell.setCellValue(trat.getEsExcepcion()); 
            cell = row.createCell(53);      	cell.setCellValue(trat.getIncidencia()); 
            cell = row.createCell(54);      	cell.setCellValue(trat.getIdEstado()) ;        
          }

        
        return wb;
    }
}