
package lopicost.spd.struts.helper;

import java.util.ArrayList;
import java.util.List;

import lopicost.spd.persistence.ControlDataImportDAO;

import lopicost.spd.struts.bean.ControlDataImportBean;
import lopicost.spd.utils.SPDConstants;


/**
 
 *Logica de negocio 
 */
public class ControlDataImportHelper {


	private final static String cLOGGERHEADER = "ControlDataImportHelper: ";
	private final String cLOGGERHEADER_ERROR = cLOGGERHEADER + "ERROR: ControlDataImportHelper: ";


	/**
	 * @param spdUsuario
	 * @return
	 * @throws Exception
	 * bd_pacientesFarmatic
	 * bd_consejo		
	 * hst_CodigosRecetasDispensados 
	 * hst_recetasCaducadas
	 * hst_ProductosDispensadosResis
	 * hst_insertResidente
	 * 
	 * [dbo].[tbl_comprasXventasXproveedor] 3 Farmacias
	 * 
	 */



	public static List<ControlDataImportBean> getDatosTablas(String idUsuario) throws Exception {
		
		List<ControlDataImportBean> listaBeans= new ArrayList<ControlDataImportBean>();

		/** 
		 * 1- bd_pacientesFarmatic
		 */
		ControlDataImportBean tabla1 = new ControlDataImportBean();
		tabla1.setNombreTabla("bd_pacientesFarmatic");
		tabla1.setRelaciones("Report Farmatic vs Gestión pacientes");
		tabla1.setDetalleBeans(ControlDataImportDAO.getDatosBdPacientesFarmatic(idUsuario, SPDConstants.DIAS_MAX_PROCESOS_IMPORT));
		tabla1.setUltimaFechaEnOrigen("NO");
		tabla1.setCuantos(-1);
		listaBeans.add(tabla1);
	
		/** 
		 * 2- bd_consejo
		 */
		ControlDataImportBean tabla2 = new ControlDataImportBean();
		tabla2.setNombreTabla("bd_consejo (cada 7 días)");
		tabla2.setIdFarmacia("NO");
		tabla2.setIdDivisionResidencia("NO");
		tabla2.setNumeroCIPs(-1);
		tabla2.setUltimaFechaEnOrigen("NO");
		tabla2.setCuantos(-1);
		
		tabla2.setRelaciones("diferentes reports ");
		tabla2.setDetalleBeans(ControlDataImportDAO.getDatosBdConsejo(idUsuario, SPDConstants.DIAS_MAX_PROCESOS_IMPORT));
		listaBeans.add(tabla2);
		
		/**
		 * 3 - dbo.hst_ProductosDispensadosResis
		 */
		ControlDataImportBean tabla3 = new ControlDataImportBean();
		tabla3.setNombreTabla("hst_ProductosDispensadosResis");
		tabla3.setRelaciones("Report Pedido FL");
		tabla3.setNumeroCIPs(-1);
	//	tabla3.setUltimaFechaRecogida("NO");
		tabla3.setDetalleBeans(ControlDataImportDAO.getProductosDispensadosResis(idUsuario, 7));
		listaBeans.add(tabla3);
		
		/**
		 * 4 - hst_CodigosRecetasDispensados
		 */
		ControlDataImportBean tabla4 = new ControlDataImportBean();
		tabla4.setNombreTabla("hst_CodigosRecetasDispensados (dispensados en los últimos 31 días)");
		tabla4.setRelaciones("Vista v_pacientesComalisFarmatic / Report Farmatic vs Gestión pacientes / Report Pedido FL ");
		tabla4.setNumeroCIPs(-1);
		//	tabla4.setUltimaFechaRecogida("NO");
		tabla4.setDetalleBeans(ControlDataImportDAO.getCodigosRecetasDispensados(idUsuario, 7));
		listaBeans.add(tabla4);
		/**
		 * 5 - hst_recetasCaducadas
		 
		ControlDataImportBean tabla5 = new ControlDataImportBean();
		tabla5.setNombreTabla("hst_recetasCaducadas");
		tabla5.setRelaciones("Report... ");
//		tabla5.setIdFarmacia("NO");
		tabla5.setUltimaFechaEnOrigen("NO");
		tabla5.setNumeroCIPs(-1);		
		tabla5.setCuantos(-1);
		tabla5.setDetalleBeans(ControlDataImportDAO.getRecetasCaducadas(idUsuario, SPDConstants.DIAS_MAX_PROCESOS_IMPORT));
		listaBeans.add(tabla5);	
*/
		/**
		 * 6 - [dbo].[tbl_comprasXventasXproveedor] 3 Farmacias
		 */
		ControlDataImportBean tabla6 = new ControlDataImportBean();
		tabla6.setNombreTabla("tbl_comprasXventasXproveedor (alerta si más de 32 días)");
		tabla6.setRelaciones("compras recepcionadas a FL en Farmatic ");
		tabla6.setIdDivisionResidencia("NO");
		tabla6.setCuantos(-1);
		tabla6.setNumeroCIPs(-1);
		tabla6.setDetalleBeans(ControlDataImportDAO.getComprasVentasXProveedor(idUsuario, 32));
		listaBeans.add(tabla6);	
		return listaBeans;
	}



	

	
		
}
	


	
	
	
	
	
	
	
	
	