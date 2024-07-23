package spd_test.test;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import lopicost.spd.helium.model.Dose;
import lopicost.spd.helium.model.Patient;
import lopicost.spd.helium.model.Treatment;
import lopicost.spd.model.SustXComposicion;
import lopicost.spd.persistence.FicheroResiDetalleDAO;
import lopicost.spd.persistence.SustXComposicionDAO;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.form.FicheroResiForm;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.ss.usermodel.Cell;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

public class buildXML {

	
	private static final TreeMap CIPSTratados =new TreeMap();
	private static final TreeMap CNSTratados =new TreeMap();

	public  void main(String[] args) throws Exception {

	    
		FicheroResiForm formulari =  new FicheroResiForm();
		formulari.setIdDivisionResidencia("general_onada_casserres");
		formulari.setIdProceso("20230605_20230611_168529204");
		
        List<FicheroResiBean> registros=FicheroResiDetalleDAO.getGestFicheroResiBolsa(null, -1, formulari,  0,100000, null, false, null, false, false); 
		
        Iterator it= registros.iterator();
        
        while (it!=null && it.hasNext())
        {
        	Patient patient = null;
        	Treatment treatment = null;
        	Dose dose = null;
        	FicheroResiBean registro = (FicheroResiBean)it.next();
        	String CIP = registro.getResiCIP();
        	if (!this.CIPSTratados.containsKey(CIP)) {
                 patient = creaPatient(registro);
                  this.CIPSTratados.put(CIP, patient);
        	 } 
        	 patient = (Patient) this.CIPSTratados.get(CIP);
        	 
        	 String CN = registro.getSpdCnFinal();
        	 if (!this.CNSTratados.containsKey(CIP+"_"+CN)) {
        		 treatment = creaTreatment(registro);
                  this.CNSTratados.put(CIP+"_"+CN, treatment);
        	 } 
        	 treatment= (Treatment) this.CNSTratados.get(CIP+"_"+CN);
        	 creaDoses(registro);
        	 
        	 
        	 
        	 
		
        }
}

	private void creaDoses(FicheroResiBean registro) {
		
		TreeMap doses = recuperaTreeMapDoses(registro); 
	    Collection c = doses.values();
	        Iterator itr = c.iterator();
	        while (itr.hasNext()) {
	            System.out.println(itr.next());
	        	
	        }

	}

	private TreeMap recuperaTreeMapDoses(FicheroResiBean registro) {
		TreeMap dosesRegistro =new TreeMap();
		//true:"1"?"2";
		if(registro.getResiToma1()!=null && !registro.getResiToma1().equals(""))
			dosesRegistro.put("0100", registro.getResiToma1());
		if(registro.getResiToma2()!=null && !registro.getResiToma2().equals(""))
			dosesRegistro.put("0200", registro.getResiToma2());
		if(registro.getResiToma3()!=null && !registro.getResiToma3().equals(""))
			dosesRegistro.put("0300", registro.getResiToma3());
		if(registro.getResiToma4()!=null && !registro.getResiToma4().equals(""))
			dosesRegistro.put("0400", registro.getResiToma4());		
		if(registro.getResiToma5()!=null && !registro.getResiToma5().equals(""))
			dosesRegistro.put("0500", registro.getResiToma5());		
		if(registro.getResiToma6()!=null && !registro.getResiToma6().equals(""))
			dosesRegistro.put("0600", registro.getResiToma6());		
		if(registro.getResiToma7()!=null && !registro.getResiToma7().equals(""))
			dosesRegistro.put("0700", registro.getResiToma7());		
		if(registro.getResiToma8()!=null && !registro.getResiToma8().equals(""))
			dosesRegistro.put("0800", registro.getResiToma8());		
		if(registro.getResiToma9()!=null && !registro.getResiToma9().equals(""))
			dosesRegistro.put("0900", registro.getResiToma9());		
		if(registro.getResiToma10()!=null && !registro.getResiToma10().equals(""))
			dosesRegistro.put("1000", registro.getResiToma10());		
		if(registro.getResiToma11()!=null && !registro.getResiToma11().equals(""))
			dosesRegistro.put("1100", registro.getResiToma11());		
		if(registro.getResiToma12()!=null && !registro.getResiToma12().equals(""))
			dosesRegistro.put("1200", registro.getResiToma12());
		if(registro.getResiToma13()!=null && !registro.getResiToma13().equals(""))
			dosesRegistro.put("1300", registro.getResiToma13());
		if(registro.getResiToma14()!=null && !registro.getResiToma14().equals(""))
			dosesRegistro.put("1400", registro.getResiToma14());		
		if(registro.getResiToma15()!=null && !registro.getResiToma15().equals(""))
			dosesRegistro.put("1500", registro.getResiToma15());		
		if(registro.getResiToma16()!=null && !registro.getResiToma16().equals(""))
			dosesRegistro.put("1600", registro.getResiToma16());		
		if(registro.getResiToma17()!=null && !registro.getResiToma17().equals(""))
			dosesRegistro.put("1700", registro.getResiToma17());		
		if(registro.getResiToma18()!=null && !registro.getResiToma18().equals(""))
			dosesRegistro.put("1800", registro.getResiToma18());		
		if(registro.getResiToma19()!=null && !registro.getResiToma19().equals(""))
			dosesRegistro.put("1900", registro.getResiToma19());		
		if(registro.getResiToma20()!=null && !registro.getResiToma20().equals(""))
			dosesRegistro.put("2000", registro.getResiToma20());		
		if(registro.getResiToma21()!=null && !registro.getResiToma21().equals(""))
			dosesRegistro.put("2100", registro.getResiToma21());		
		if(registro.getResiToma22()!=null && !registro.getResiToma22().equals(""))
			dosesRegistro.put("2200", registro.getResiToma22());
		if(registro.getResiToma23()!=null && !registro.getResiToma23().equals(""))
			dosesRegistro.put("2300", registro.getResiToma23());
		if(registro.getResiToma24()!=null && !registro.getResiToma24().equals(""))
			dosesRegistro.put("2400", registro.getResiToma24());
		return dosesRegistro;		
	}

	private Treatment creaTreatment(FicheroResiBean registro) {
		// TODO Auto-generated method stub
		return null;
	}

	private Patient creaPatient(FicheroResiBean registro) {
		// TODO Auto-generated method stub
		return null;
	}
}
