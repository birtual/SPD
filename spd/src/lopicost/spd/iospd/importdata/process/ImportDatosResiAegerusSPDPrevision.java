package lopicost.spd.iospd.importdata.process;

import lopicost.spd.struts.bean.FicheroResiBean;

import lopicost.spd.iospd.IOSpdApi;
import lopicost.spd.model.BdConsejo;
import lopicost.spd.model.DivisionResidencia;
import lopicost.spd.model.SustXComposicion;
import lopicost.spd.utils.DateUtilities;
import lopicost.spd.utils.HelperSPD;
import lopicost.spd.utils.SPDConstants;
import lopicost.spd.persistence.BdConsejoDAO;
import lopicost.spd.persistence.FicheroMedResiConPrevisionDAO;
import lopicost.spd.persistence.SustXComposicionDAO;
import lopicost.spd.utils.StringUtil;
import lopicost.spd.utils.TextManager;
import java.util.Vector;

import java.util.TreeMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.sql.SQLException;
import java.util.Collection;

public class ImportDatosResiAegerusSPDPrevision extends ImportProcessImpl
{
    TreeMap CIPSTratados =new TreeMap();;
    TreeMap CNSTratados =new TreeMap();;
    FicheroResiBean fila;
    boolean procesosAnterioresLimpiados;
    
    String idDivisionResidencia = null;
    String idProceso = null;

    
    
    
    protected boolean beforeProcesarEntrada(final Vector row) throws Exception {
        return true;
    }
    
    protected void afterprocesarEntrada(final Vector row) throws Exception {
    }
    
    public void procesarCabecera(final Vector row) throws Exception {
    }
    
    public boolean procesarEntrada(String idRobot, DivisionResidencia div, String idProceso, Vector row, int count, boolean cargaAnexa) throws Exception {
        boolean result=false;
        if(div==null)  return result;//y aseguramos que sea div!=null
        
    	if(count>1) //saltamos cabecera 
        {
        	this.idProceso=idProceso;
        	this.idDivisionResidencia=div.getIdDivisionResidencia();
        	 
        	 
        	if (row != null && row.size() >= 12) {
                result = false;
                
              //  final String element = (String) row.elementAt(1);
              //  if (!element.toUpperCase().contains("IDPROCESO")) {
                    result = this.creaRegistro(row);
              //  }
                return result;
            }
        }
    	return result;
        //throw new Exception(TextManager.getMensaje("ImportData.error.ImportDatosResiSPD"));
    }
    
    private boolean creaRegistro(final Vector row) throws Exception {
        boolean result = true;
        //final String idDivisionResidencia = (String)row.elementAt(0);
        //final String idProceso = (String) row.elementAt(1);
        
        final String spdCnFinal = StringUtil.replaceInvalidCharsInNumeric((String)row.elementAt(0));
        final String CIP = StringUtil.replaceInvalidChars((String)row.elementAt(6));
        
    	/* Ahora lo hacemos CIP a CIP
        if (!this.procesosAnterioresLimpiados) {
            this.ioSpdApi.limpiarProcesoAnterioresResi(idDivisionResidencia);
            this.procesosAnterioresLimpiados = true;
        }
        */
        
       if (!this.CNSTratados.containsKey(String.valueOf(CIP) + "_" + spdCnFinal)) {
    	   // if (!this.CNSTratados.containsKey(String.valueOf(CIP))) {
            if (this.fila != null) {
        		//B�squeda de BDConsejo
        		BdConsejo bdConsejo=BdConsejoDAO.getByCN(fila.getSpdCnFinal()); 
        		if(bdConsejo!=null)
        		{
        			fila.setBdConsejo(bdConsejo);
        			fila.setSpdCodGtVm(bdConsejo.getCodGtVm());
        			fila.setSpdNomGtVm(bdConsejo.getNomGtVm());
        			fila.setSpdCodGtVmp(bdConsejo.getCodGtVmp());
        			fila.setSpdNomGtVmp(bdConsejo.getNomGtVmp());
        			fila.setSpdCodGtVmpp(bdConsejo.getCodGtVmpp());
        			fila.setSpdNomGtVmpp(bdConsejo.getNomGtVmpp());
        			fila.setSpdEmblistable(bdConsejo.getEmblistable());
        			fila.setSustituible(bdConsejo.getSustituible());
        		}
        		
        		if(fila.getSpdEmblistable()!=null&&fila.getSpdEmblistable().equalsIgnoreCase("SI") || (fila.getSpdAccionBolsa()!=null && fila.getSpdAccionBolsa().equalsIgnoreCase(SPDConstants.SPD_ACCIONBOLSA_PASTILLERO)))
        		{
        			fila.setSpdEmblistable("SI");
        		}
        		
             	try{
             		//String idProceso = fila.getIdProceso();
             		int fechaInicioInt = new Integer(idProceso.substring(idProceso.length()-8, idProceso.length())).intValue();
             		int fechaFinInt = new Integer(idProceso.substring(idProceso.length()-17, idProceso.length()-9)).intValue();
             		fila.setFechaInicioInt(fechaInicioInt);
             		fila.setFechaFinInt(fechaFinInt);
               	} catch(Exception e)
             	{
             		
             	}
              }
            (this.fila = new FicheroResiBean()).setIdDivisionResidencia(this.idDivisionResidencia);
            this.fila.setIdProceso(this.idProceso);
            this.fila.setResiCIP(StringUtil.replaceInvalidChars(CIP));
            this.fila.setResiNombrePaciente(StringUtil.replaceInvalidChars((String)row.elementAt(7)));
            this.CNSTratados.put(String.valueOf(CIP) + "_" + spdCnFinal, this.fila);
            if (!this.CIPSTratados.containsKey(CIP)) {
                this.CIPSTratados.put(CIP, CIP);
                this.ioSpdApi.limpiarCIPIdprocesoAnterior(idProceso, CIP);
            }
            this.fila.setSpdCnFinal(StringUtil.replaceInvalidCharsInNumeric((String)row.elementAt(0)));
            this.fila.setSpdNombreBolsa(StringUtil.replaceInvalidChars((String)row.elementAt(1)));
            this.fila.setSpdFormaMedicacion((String)row.elementAt(2));
            this.fila.setSpdAccionBolsa((String)row.elementAt(3));
            this.fila.setResiMedicamento(StringUtil.replaceInvalidChars((String)row.elementAt(4)));
            this.fila.setResiCn(StringUtil.replaceInvalidCharsInNumeric((String)row.elementAt(5)));
            this.fila.setResiObservaciones("");
            this.fila.setResiInicioTratamiento((String)row.elementAt(9));
            this.fila.setResiFinTratamiento((String)row.elementAt(10));
            this.fila.setResiPlanta("");
            this.fila.setResiHabitacion(new StringBuilder(String.valueOf(row.elementAt(8))).toString());
            this.fila.setResiSiPrecisa("");
            this.fila.setResiComentarios("");
            this.fila.setComprimidosDia((float)HelperSPD.getPautaToma((String)row.elementAt(11)));
            int conteoDias = 0;
            System.out.println(" row.size() --> " + row.size());
            for (int i = 14; i < row.size(); ++i) {
                System.out.println(" i --> " + i + " // " + row.elementAt(i));
                if (DateUtilities.isDateValid((String)row.elementAt(i), "dd/MM/yyyy")) {
                    ++conteoDias;
                }
            }
            if (this.fila.getSpdAccionBolsa() != null && this.fila.getSpdAccionBolsa().equalsIgnoreCase(SPDConstants.SPD_ACCIONBOLSA_PASTILLERO)) {
                this.fila.setDiasConToma(conteoDias);
                this.fila.setComprimidosDosSemanas(this.fila.getComprimidosDia() * conteoDias);	//son tratamientos de 14 d�as!!

                this.fila.setComprimidosSemana(this.fila.getComprimidosDosSemanas()/2);	//son tratamientos de 14 d�as!!
                
                this.fila.setComprimidosTresSemanas((this.fila.getComprimidosDosSemanas()/2)  * 3);
                this.fila.setComprimidosCuatroSemanas(this.fila.getComprimidosDosSemanas() * 2);
             }
        }
        else {//sumamos pautas del resto de horas
            int conteoDias = 0;
            float comprimidosDia=(float)HelperSPD.getPautaToma((String)row.elementAt(11));

            this.fila = (FicheroResiBean)this.CNSTratados.get(String.valueOf(CIP) + "_" + spdCnFinal);
            this.fila.setComprimidosDia( this.fila.getComprimidosDia()+ comprimidosDia  );
            for (int i = row.size() - 11; i < row.size(); ++i) {
                if (DateUtilities.isDateValid((String)row.elementAt(i), "dd/MM/yyyy")) {
                    ++conteoDias;
                }
            }
            
            if (this.fila.getSpdAccionBolsa() != null && this.fila.getSpdAccionBolsa().equalsIgnoreCase(SPDConstants.SPD_ACCIONBOLSA_PASTILLERO)) {
            	int comprimidos14dias=(int) (comprimidosDia * conteoDias); //son tratamientos de 14 d�as!!
            	
                this.fila.setComprimidosDosSemanas(this.fila.getComprimidosDosSemanas()+ comprimidos14dias);  //son tratamientos de 14 d�as!!
                this.fila.setComprimidosSemana(this.fila.getComprimidosSemana()+ (comprimidos14dias/2));

                this.fila.setComprimidosTresSemanas((this.fila.getComprimidosTresSemanas()+comprimidos14dias* 3.0f/2));
                this.fila.setComprimidosCuatroSemanas(this.fila.getComprimidosCuatroSemanas()+ comprimidos14dias * 2.0f);
             }
        }

		
        if (!result) {
            throw new Exception("No se ha podido crear el registro ImportDatosResiSPD (ctl_medicacioResi)");
        }
        
        return result;
    }
    
    protected boolean beforeStart(final String filein) throws Exception {
       // idProceso=this.idDivisionResidencia + "_"+ idProceso;
    	return true;
    }
    
    protected void afterStart() throws ClassNotFoundException, SQLException {
    	boolean result;
    	Set set = CNSTratados.entrySet();
    	Iterator it =(Iterator) set.iterator();
    	int i=0;
    	while(it.hasNext()) {
    		 Map.Entry me = (Map.Entry)it.next();
    		 System.out.println(" me.getKey() --> "  + me.getKey());
    		 FicheroResiBean fila = (FicheroResiBean)me.getValue();
    		 i++;
    		 result = FicheroMedResiConPrevisionDAO.nuevo(fila);
    	    	if(!result)
    				errors.add("No se crea el registro de la fila " + getProcessedRows()  + " key >> " + fila.getCipsFicheroOrigen() + " - CN: " + fila.getResiCn());
    	    } 
  	
    	
    	CNSTratados.clear();
		
		
		
        final TreeMap CIPSTratados = new TreeMap();
        final Collection c = this.CNSTratados.values();
        final Iterator itr = c.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
      
        IOSpdApi.borraErrores(getIdDivisionResidencia());

        
        
    }




}

