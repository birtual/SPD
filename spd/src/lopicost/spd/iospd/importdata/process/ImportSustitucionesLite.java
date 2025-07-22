
package lopicost.spd.iospd.importdata.process;

import lopicost.spd.model.GestSustitucionesLite;
import lopicost.spd.persistence.GestSustitucionesLiteDAO;
import lopicost.spd.utils.StringUtil;
import  lopicost.spd.utils.TextManager;

import java.util.Vector;

/**
 * Método encargado de importar registros de sustituciones para cada residencia
 * Todos los registros del listado

 *
 */

public class ImportSustitucionesLite extends ImportProcessImpl
{

	public ImportSustitucionesLite(){
		super();
	}

	/**los ficheros han de venir con cabecera. Se tendrá en cuenta a partir de la fila 2**/	
    protected boolean beforeProcesarEntrada(Vector row) throws Exception 
    {
    	return true;
    }
    protected void afterprocesarEntrada(Vector row) throws Exception 
    {
    	
    }
    
    public void procesarCabecera(Vector row) throws Exception 
    {
    }
     
    public void procesarEntrada(String idRobot, String idDivisionResidencia, String idProceso, Vector row, int count) throws Exception 
    {
        if (row!=null && row.size()>=6)
        {
        	GestSustitucionesLite lite= new GestSustitucionesLite();
        	lite.setIdDivisionResidencia(idDivisionResidencia);
    		boolean result = creaRegistro(lite, row);
         }
        else 
            throw new Exception (TextManager.getMensaje("ImportData.error.ImportSustitucionesLite"));
    }
    
    private boolean creaRegistro(GestSustitucionesLite lite, Vector row) throws Exception {

     	boolean resultSust =true;
     	int i =0;
     	if(lite.getIdDivisionResidencia()==null || lite.getIdDivisionResidencia().equals(""))
     	{
        	lite.setIdDivisionResidencia((String) row.elementAt(0));							//idDivisionResidencia
        	i=i+1;
     	}
     	
     	lite.setResiCn(StringUtil.limpiarTextoEspaciosYAcentos((String) row.elementAt(i), false));		//resiCnOk  lo ponemos porque está con máscara "000000"
     	
     	lite.setResiMedicamento(StringUtil.limpiarTextoComentarios((String) row.elementAt(i+1)));		//resiMedicamento
    	lite.setSpdCn(StringUtil.limpiarTextoEspaciosYAcentos((String) row.elementAt(i+2), false));			//spdCn
    	lite.setSpdNombreBolsa(StringUtil.limpiarTextoComentarios((String) row.elementAt(i+3)));									//spdNombreBolsa
    	lite.setSpdFormaMedicacion(StringUtil.limpiarTextoComentarios((String) row.elementAt(i+4)));							//spdFormaMedicacion
    	lite.setSpdAccionBolsa((String) row.elementAt(i+5));									//spdAccionBolsa
    	try{
    		lite.setExcepciones((String) row.elementAt(i+6));										//excepciones
    	}catch(Exception e ){
    		lite.setExcepciones("");
    	}
    	try{
    	lite.setAux1((String) row.elementAt(i+7));											//aux1
    	}catch(Exception e ){
    		lite.setAux1("");
    	}
    	try{
    	lite.setAux2((String) row.elementAt(i+8));											//aux1
    	}catch(Exception e ){
    		lite.setAux2("");
    	}
		 //creamos en bbdd
    	//primero SustRaiz
	    if(lite!=null 
	    			&& lite.getSpdAccionBolsa()!=null 
	    			&& ( lite.getSpdAccionBolsa().equalsIgnoreCase("NO_PINTAR") 
	    					|| lite.getSpdAccionBolsa().equalsIgnoreCase("SOLO_INFO") 
	    					|| lite.getSpdAccionBolsa().equalsIgnoreCase("PASTILLERO"))
	    			)
	    {
	    	GestSustitucionesLite sust=  GestSustitucionesLiteDAO.buscaSustitucionLite(getSpdUsuario(), lite);
	    	if(sust==null)
	    		resultSust=GestSustitucionesLiteDAO.nuevoSustLite(lite);
	    	else 
	    		resultSust=GestSustitucionesLiteDAO.editaSustLite(getSpdUsuario(), lite);
	    }
		
	
	return resultSust;
	}

	private String getStringFechaArreglada(String elementAt) {
		// TODO Auto-generated method stub
		return null;
	}

	protected boolean beforeStart(String filein) throws Exception 
    {
 //   	ioSpdApi.eliminaRegistrosCIPIdproceso(this.);
        return true;
    }

    protected void afterStart() 
    {
    }

	@Override
	protected void procesarEntrada(String idRobot, String idDivisionResidencia, String idProceso, Vector row, int count, boolean cargaAnexa) throws Exception {
		procesarEntrada( idRobot,  idDivisionResidencia,  idProceso,  row,  count);
		
	}



    
}    


