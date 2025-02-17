package lopicost.spd.persistence;

import java.sql.*;
import java.text.*;

import lopicost.spd.model.DivisionResidencia;
import lopicost.spd.robot.bean.DetallesTomasBean;
import lopicost.spd.robot.helper.PlantillaUnificadaHelper;
import lopicost.spd.robot.model.*;
import lopicost.spd.struts.bean.CabecerasXLSBean;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.bean.PacienteBean;
import lopicost.spd.utils.*;
import lopicost.config.logger.Logger;
import lopicost.config.pool.dbaccess.Conexion;

import java.util.*;
import java.util.Date;


public class XMLRobotDao
{
    static String className = "XMLRobotDao";
    static TreeMap<String, Integer> detalleBolsasTratadas=  new TreeMap<>();
    static TreeMap<String, Integer> detalleContenidoBolsasTratadas=  new TreeMap<>();

    //Paso1
/*	public static boolean borraProceso(String idUsuario, FicheroResiBean cab) throws ClassNotFoundException, SQLException {
        detalleBolsasTratadas.clear();
    	detalleContenidoBolsasTratadas.clear();
    	String queryBorrado1= "DELETE FROM dbo.SPD_XML_detallesTomasRobot WHERE idDivisionResidencia='"+cab.getIdDivisionResidencia()+"' AND idProceso='"+cab.getIdProceso()+"'";
    	return ejecutarSentencia(queryBorrado1);
	}
*/
	public static boolean borraProcesosResidencia(String idUsuario, FicheroResiBean cab) throws ClassNotFoundException, SQLException {
		System.out.println("inicio detalleBolsasTratadas.clear()");
		Logger.log("SPDLogger", "inicio detalleBolsasTratadas.clear()",Logger.INFO);	
	
		detalleBolsasTratadas.clear();
		Logger.log("SPDLogger", "inicio detalleContenidoBolsasTratadas.clear()",Logger.INFO);	
		detalleContenidoBolsasTratadas.clear();
		Logger.log("SPDLogger", "if cab !=null  " + cab==null?"nulo":"no Nulo",Logger.INFO);	
		Logger.log("SPDLogger", "inicio DELETE FROM dbo.SPD_XML_detallesTomasRobot WHERE idDivisionResidencia='"+cab.getIdDivisionResidencia()+"'" ,Logger.INFO);	
	  	String queryBorrado1= "DELETE FROM dbo.SPD_XML_detallesTomasRobot WHERE idDivisionResidencia='"+cab.getIdDivisionResidencia()+"' ";
    	return ejecutarSentencia(queryBorrado1);
	}
    //Paso2
	public static TomasOrdenadas getTomasOrdenadas(String idUsuario, FicheroResiBean cab) throws SQLException {
        // Obtener la correspondencia de posiciones
        Connection con = Conexion.conectar();
        ResultSet rs = null;
        PreparedStatement pstat = null;
        String ordenQuery = "SELECT posicionEnBBDD, posicionEnVistas, nombreToma, idToma FROM dbo.SPD_cabecerasXLS WHERE idDivisionResidencia = '"+cab.getIdDivisionResidencia()+"' ORDER BY posicionEnVistas ";
        pstat = con.prepareStatement(ordenQuery);
        rs = pstat.executeQuery();

        List<Integer> posiciones = new ArrayList<>();
        List<String> nombresTomas = new ArrayList<>();
        List<String> idTomas = new ArrayList<>();
//           List<String> contenidoTomas = new ArrayList<>();
        while (rs.next()) {
            posiciones.add(rs.getInt("posicionEnBBDD"));
            nombresTomas.add(rs.getString("nombreToma"));
            idTomas.add(rs.getString("idToma"));
        }
        TomasOrdenadas tomasOrdenadas = new TomasOrdenadas(posiciones, nombresTomas, idTomas);
        rs.close();
        pstat.close();
		return tomasOrdenadas;
	}

	//Paso3 - Recuperar Listado de detalleTomas ya ordenadas
	public static List<DetallesTomasBean> getDetalleTomasRobot(String idUsuario, FicheroResiBean cab, TomasOrdenadas tomasGlobal, PacienteBean pac)throws Exception {
		List<DetallesTomasBean> resultado = new ArrayList<DetallesTomasBean>();
        Connection con = Conexion.conectar();
        ResultSet rs = null;
        PreparedStatement pstat = null;
        try {
            List<Integer> posiciones = tomasGlobal.getPosiciones();
            List<String> nombresTomas = tomasGlobal.getNombresTomas();
            List<String> idTomas = tomasGlobal.getIdTomas();

            // Construir la consulta dinámica
            StringBuilder query = new StringBuilder(" SELECT ISNULL(p.planta, '') AS planta, d.resiCip as CIP, ISNULL(p.habitacion, '') AS habitacion, LEFT(d.spdCnFinal, 6) as CN  ");
            query.append(" , d.spdNombreBolsa, CASE d.spdAccionBolsa WHEN 'PASTILLERO' then 'S' ELSE 'N' END dispensar, d.spdNombreBolsa, d.spdAccionBolsa "  );
            query.append(" , d.fechaDesde, d.fechaHasta, d.resiD1, d.resiD2, d.resiD3, d.resiD4, d.resiD5, d.resiD6, d.resiD7  "  );
            //query.append(" , d.resiD1, d.resiD2, d.resiD3, d.resiD4, d.resiD5, d.resiD6, d.resiD7  "  );
            query.append(" , d.resiInicioTratamiento, d.resiFinTratamiento,  d.resiInicioTratamientoParaSPD, d.resiFinTratamientoParaSPD, d.resiPeriodo "  );
            for (int i = 0; i < posiciones.size(); i++) {
                query.append(", d.resiToma").append(posiciones.get(i)).append(" AS [").append(nombresTomas.get(i)).append("]");
            }
            query.append(" FROM SPD_ficheroResiDetalle d"  );
            query.append(" LEFT JOIN dbo.bd_pacientes p ON p.CIP=d.resiCIP "  );
            query.append(" WHERE d.idDivisionResidencia = '"+cab.getIdDivisionResidencia()+"'  ");
            query.append(" AND d.oidFicheroResiCabecera = '"+cab.getOidFicheroResiCabecera()+"'  ");
            query.append(" AND d.spdAccionBolsa in ('SOLO_INFO', 'PASTILLERO') ");
            query.append(" AND (p.SPD is null OR p.SPD <> 'N') ");
            if(pac!=null && pac.getCIP()!=null)
                query.append(" AND d.resiCIP='"+pac.getCIP()+"'  ");
      	
            query.append(" AND ISNUMERIC(RIGHT(d.spdCnFinal, 6))=1 ");
            //query.append(" AND resiCIP='SOFI1570624009' ");
            //query.append(" ORDER BY d.resiCip, d.spdNombreBolsa ");
            query.append(" ORDER BY d.resiCip, LEFT(d.spdCnFinal, 6) ");

            
            // Ejecutar la consulta
            pstat = con.prepareStatement(query.toString());
            rs = pstat.executeQuery();
            while (rs.next()) 
            {
            	DetallesTomasBean bean = creaBeanPaso1DetallesTomas(idUsuario, cab, rs, posiciones, nombresTomas);
            	resultado.add(bean);
              		  
            }
        } finally {
            if (rs != null) rs.close();
            if (pstat != null) pstat.close();
            if (con != null) con.close();
        }

        return resultado;
    }

	
	public static String procesarDetalleTomasRobot(String idUsuario, FicheroResiBean cab, DetallesTomasBean  bean, TomasOrdenadas tomasGlobal)throws SQLException, ParseException, ClassNotFoundException {
		
       	String sql = ""; 
        String idProceso=cab.getIdProceso();
        String idDivisionResidencia=cab.getIdDivisionResidencia();		
       
        List<Integer> posiciones = tomasGlobal.getPosiciones();
        List<String> nombresTomas = tomasGlobal.getNombresTomas();
        List<String> idTomas = tomasGlobal.getIdTomas();

        try {
        	
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			SimpleDateFormat sdfIdBolsa = new SimpleDateFormat("yyyyMMdd");

           	int count=0; String posicion="";
           	String nombreToma=""; 
           	String idToma="";
           	String valorToma="";
          	String fechaTomaInsert="yyyy/MM/dd";
           	String fechaTomaParaIdBolsa="yyyyMMdd";
           	int idLineaRX=0;
           	String tramoToma="";
           	String IDbolsaFechaDispTramo ="";
           	String idBolsa=""; 
           	String idFreeInformation=""; 
           	int numeroBolsa=0;

           	Date dateObjetivo = bean.getDateDesde(); // inicializamos para recorrer los días desde fechaDesde a fechaHasta
           	Calendar calendario = Calendar.getInstance();
           	boolean dateObjetivoMarcado=dateObjetivoMarcado(calendario, dateObjetivo, bean);
           	

            	
            //si el día está marcado, bucle de los días de producción SPD
           	//while(dateObjetivoMarcado && 
           	while( DateUtilities.isBeetwenTime(bean.getDateDesde(), bean.getDateHasta(), dateObjetivo) 
            		//&& DateUtilities.isBeetwenTime(bean.getDateInicioTratamiento(), bean.getDateFinTratamiento(), dateObjetivo)
            		&& count<32)  //count por seguridad en caso de de recibir fechas dispares por error
            {
            	//si la fecha inicio/fin SPD no está dentro pasamos al siguiente día objetivo en el bucle
            	if(!DateUtilities.isBeetwenTime(bean.getDateInicioTratamiento(), bean.getDateFinTratamiento(), dateObjetivo))
            	{
     	            //actualizamos calendario con la nueva fecha
            		dateObjetivo = (Date) DateUtilities.addDate(dateObjetivo, 1);
    	            dateObjetivoMarcado=dateObjetivoMarcado(calendario, dateObjetivo, bean);

               		continue;
    	                           	 
            	}
            	
            	fechaTomaInsert= sdf.format(dateObjetivo);
            	fechaTomaParaIdBolsa= sdfIdBolsa.format(dateObjetivo);
    				
            	//en este punto tenemos tres listados con la misma medida, posiciones,  nombresTomas y bean.getResiTomas
            	for(int i=0; i<posiciones.size(); i++)
            	{
            		valorToma=bean.getResiTomas().get(i);
            		posicion=String.format("%02d", posiciones.get(i));
           			nombreToma=nombresTomas.get(i);
           			idToma=idTomas.get(i);
            			
           			if(dateObjetivoMarcado && DataUtil.isNumero(valorToma) && DataUtil.isNumeroGreatherThanZero(valorToma))
           			{
           				int posicionVirtual=posiciones.indexOf(posiciones.get(i))+1;
           				String sPosicionVirtual=String.format("%02d", posicionVirtual);
           				
           				tramoToma="["+sPosicionVirtual+"]_["+nombreToma+"]";
           				IDbolsaFechaDispTramo = bean.getCIP() + "_" + dateObjetivo + "_" + bean.getDispensar()+ "_" + tramoToma;
           				numeroBolsa = getNumeroBolsas(IDbolsaFechaDispTramo, valorToma, bean.getDispensar());
           				//idBolsa = bean.getCIP() + fechaTomaParaIdBolsa + tramoToma + bean.getDispensar()+ String.format("%02d", numeroBolsa );
           				idBolsa = bean.getCIP() + fechaTomaParaIdBolsa + tramoToma + bean.getDispensar()+ numeroBolsa;
           				idLineaRX = getIdLineaRX(IDbolsaFechaDispTramo, numeroBolsa);
           				idFreeInformation = StringUtil.left(StringUtil.convertTextToAscii(StringUtil.makeFlat(bean.getCIP(), true) + "00000000000000"), 14);
           				idFreeInformation+=fechaTomaParaIdBolsa.substring(2, 8);
           				idFreeInformation+=StringUtil.substring(tramoToma, tramoToma.indexOf('[') + 1, 2);
           				idFreeInformation+=bean.getDispensar()!=null&&bean.getDispensar().equalsIgnoreCase("S")?"1":"0";
           				idFreeInformation+=numeroBolsa+"";
           				//parece que el robot tiene limitación de caracteres, con 34 daba error en la carga. Limitamos a 25
       					//idFreeInformation = idFreeInformation.length() > 25 ? idFreeInformation.substring(idFreeInformation.length() - 25) : idFreeInformation;

           				
                       	sql+= " ('"+idDivisionResidencia+"' ,'"+idProceso+"','"+bean.getCIP()+"','"+bean.getOrderNumber()+"','"+bean.getCN()+"','"+bean.getNombreMedicamento()+"'";
                       	sql+= ", '"+valorToma+"','"+bean.getDispensar()+"', '"+fechaTomaInsert+"' ";
                       	sql+= ", '"+tramoToma+"', '"+idLineaRX+"', '"+idToma+"', '"+nombreToma+"', '"+bean.getPlanta()+"','"+bean.getHabitacion()+"'";
                       	sql+= ", "+numeroBolsa+", '"+idBolsa+"', '"+idFreeInformation+"' , '"+UUID.randomUUID()+"' ) ," ;


                        
           			}
          		}
 	            //actualizamos calendario con la nueva fecha
        		dateObjetivo = (Date) DateUtilities.addDate(dateObjetivo, 1);
	            dateObjetivoMarcado=dateObjetivoMarcado(calendario, dateObjetivo, bean);

            }
            }catch(Exception e){
            	
            }

        return sql;
    }
	
	private static boolean dateObjetivoMarcado(Calendar calendario, Date dateObjetivo, DetallesTomasBean bean) {
      	calendario.setTime(dateObjetivo);
       	//miramos si está marcado el día de la semana
        int diaSemana =calendario.get(Calendar.DAY_OF_WEEK);
        // Ajustar para que Lunes sea 1, Martes sea 2, ..., Domingo sea 7
        diaSemana = (diaSemana == Calendar.SUNDAY) ? 7 : diaSemana - 1;
        
        boolean marcado = ( ( diaSemana==1 && bean.isSpdD1() ) || ( diaSemana==2 && bean.isSpdD2() )  || ( diaSemana==3 && bean.isSpdD3() ) || ( diaSemana==4 && bean.isSpdD4() )
        			 || ( diaSemana==5 && bean.isSpdD5() ) || ( diaSemana==6 && bean.isSpdD6() ) || ( diaSemana==7 && bean.isSpdD7() )
        			 );
		return marcado;

		
	}

	/*
	public static boolean procesarDetalleTomasRobotInsertUnoAUno(String idUsuario, FicheroResiBean cab, DetallesTomasBean  bean, TomasOrdenadas tomasGlobal)throws SQLException, ParseException, ClassNotFoundException {
		
        boolean resultado = true;
        Connection con = Conexion.conectar();
        ResultSet rs = null;
        PreparedStatement pstat = null;
        String idProceso=cab.getIdProceso();
        String idDivisionResidencia=cab.getIdDivisionResidencia();		
       
        List<Integer> posiciones = tomasGlobal.getPosiciones();
        List<String> nombresTomas = tomasGlobal.getNombresTomas();
        List<String> idTomas = tomasGlobal.getIdTomas();

        try {
        	
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			SimpleDateFormat sdfIdBolsa = new SimpleDateFormat("yyyyMMdd");

           	int count=0; String posicion="";
           	String nombreToma=""; 
           	String idToma="";
           	String valorToma="";
           	String sql = ""; 
           	String fechaTomaInsert="yyyy/MM/dd";
           	String fechaTomaParaIdBolsa="yyyyMMdd";
           	int idLineaRX=0;
           	String tramoToma="";
           	String IDbolsaFechaDispTramo ="";
           	String idBolsa=""; 
           	String idFreeInformation=""; 
           	int numeroBolsa=0;

           	Date dateObjetivo = bean.getDateDesde(); // inicializamos para recorrer los días desde fechaDesde a fechaHasta
           	Calendar calendario = Calendar.getInstance();
           	calendario.setTime(dateObjetivo);
                
           	//miramos si está marcado el día de la semana
            int diaSemana =calendario.get(Calendar.DAY_OF_WEEK);
            boolean marcado = ( ( diaSemana==1 && bean.isSpdD1() ) || ( diaSemana==2 && bean.isSpdD2() )  || ( diaSemana==3 && bean.isSpdD3() ) || ( diaSemana==4 && bean.isSpdD4() )
            			 || ( diaSemana==5 && bean.isSpdD5() ) || ( diaSemana==6 && bean.isSpdD6() ) || ( diaSemana==7 && bean.isSpdD7() )
            			 );
            	
            //si el día está marcado, bucle de los días de producción SPD
            while(marcado && DateUtilities.isBeetwenTime(bean.getDateDesde(), bean.getDateHasta(), dateObjetivo) && count<32)  //count por seguridad en caso de de recibir fechas dispares por error
            {
            	fechaTomaInsert= sdf.format(dateObjetivo);
            	fechaTomaParaIdBolsa= sdfIdBolsa.format(dateObjetivo);
    				
            	//en este punto tenemos tres listados con la misma medida, posiciones,  nombresTomas y bean.getResiTomas
            	for(int i=0; i<posiciones.size(); i++)
            	{
            		valorToma=bean.getResiTomas().get(i);
            		posicion=String.format("%02d", posiciones.get(i));
           			nombreToma=nombresTomas.get(i);
           			idToma=idTomas.get(i);
            			
           			if(DataUtil.isNumero(valorToma))
           			{
           				tramoToma="["+posicion+"]_["+nombreToma+"]";
           				IDbolsaFechaDispTramo = bean.getCIP() + "_" + dateObjetivo + "_" + bean.getDispensar()+ "_" + tramoToma;
           				numeroBolsa = getNumeroBolsas(IDbolsaFechaDispTramo, valorToma, bean.getDispensar());
           				//idBolsa = bean.getCIP() + fechaTomaParaIdBolsa + tramoToma + bean.getDispensar()+ String.format("%02d", numeroBolsa );
           				idBolsa = bean.getCIP() + fechaTomaParaIdBolsa + tramoToma + bean.getDispensar()+ numeroBolsa;
           				idLineaRX = getIdLineaRX(IDbolsaFechaDispTramo, numeroBolsa);
           				idFreeInformation = StringUtil.convertTextToAscii(StringUtil.left(StringUtil.makeFlat(bean.getCIP(), true) + "00000000000000", 14));
           				idFreeInformation+=fechaTomaParaIdBolsa.substring(2, 8);
           				idFreeInformation+=StringUtil.substring(tramoToma, tramoToma.indexOf('[') + 1, 2);
           				idFreeInformation+=bean.getDispensar()!=null&&bean.getDispensar().equalsIgnoreCase("S")?"1":"0";
           				idFreeInformation+=numeroBolsa+"";
            				
            					
           	           	sql = "INSERT INTO SPD_XML_detallesTomasRobot ( ";
           	           	sql+= " idDivisionResidencia, idProceso, CIP, CN, nombreMedicamento "; 
           	           	sql+= " , cantidadToma, dispensar, fechaToma  ";
           	           	sql+= " , tramoToma, idLineaRX,  idToma, nombreToma, planta, habitacion ";
           	           	sql+= " , numBolsa, idBolsa, idFreeInformation, idDetalle)  VALUES (";
                       	sql+= " '"+idDivisionResidencia+"' ,'"+idProceso+"','"+bean.getCIP()+"','"+bean.getCN()+"','"+bean.getNombreMedicamento()+"'";
                       	sql+= ", '"+valorToma+"','"+bean.getDispensar()+"', '"+fechaTomaInsert+"' ";
                       	sql+= ", '"+tramoToma+"', '"+idLineaRX+"', '"+idToma+"', '"+nombreToma+"', '"+bean.getPlanta()+"','"+bean.getHabitacion()+"'";
                       	sql+= ", "+numeroBolsa+", '"+idBolsa+"', '"+idFreeInformation+"' , '"+UUID.randomUUID()+"')" ;


                       	ejecutarSentencia(sql);
                        
           			}
          		}
	            dateObjetivo = (Date) DateUtilities.addDate(dateObjetivo, 1);
            }
         		  
      
        } finally {
            if (rs != null) rs.close();
            if (pstat != null) pstat.close();
            if (con != null) con.close();
        }

        return resultado;
    }
	*/

	public static boolean procesarBolsaContenidoRobot(String idUsuario, FicheroResiBean cab) {
		boolean resultado=true;
		
		return resultado;
	}
	
	/*
	public static boolean procesarTomasPautasRobot(String idUsuario, FicheroResiBean cab) throws ClassNotFoundException, SQLException {
		boolean resultado=true;
		
		String query =" INSERT INTO SPD_XML_detallesTomasRobot ( ";
			query+= " [fechaHoraProceso],[idDivisionResidencia],[idProceso],[planta],[CIP],[habitacion],[idBolsa],[fechaPauta],[tramoToma],[flagBolsa],[numBolsa],[idToma])  ";
			query+="  SELECT DISTINCT GETDATE() as fechaHoraPROCESO ";
			query+=" , idDivisionResidencia, idProceso  ";
			query+=" , ISNULL(planta,'') as planta, cip  ";
			query+=" , ISNULL(habitacion,'') as habitacion  ";
			query+=" , (cip  + SUBSTRING(REPLACE(fechatoma,'/',''),1,8) + ISNULL(tramoToma,'') + ISNULL(dispensar,'') + CAST(numBolsa AS VARCHAR(2))) as idBolsa  ";
			query+=" , CAST(fechatoma as DATE) as fechaPauta  ";
			//query+=" , CONVERT(DATE, REPLACE(fechatoma, '/', '-'), 120) as fechaPauta  ";
			//query+=" , REPLACE(CONVERT(char(10), fechatoma,126)  , '/', '-') as fechaPauta ";
			query+=" , ISNULL(tramoToma,'') as tramoToma ";
			query+=" , CAST((SELECT CASE dispensar WHEN 'S' THEN '1' WHEN 'N' THEN '0' ELSE '0' END)  AS INT)  as flagBolsa ";
			query+=" , CAST(numBolsa AS INT) as numBolsa ";
			query+=" , ISNULL(idToma,'') as idToma ";
			query+=" FROM [dbo].[SPD_XML_detallesTomasRobot] ";
			query+=" WHERE idDivisionResidencia ='"+cab.getIdDivisionResidencia()+"'";
			query+=" AND idProceso='"+cab.getIdProceso()+"'";
			query+=" ORDER BY idBolsa, fechaPauta , tramoToma, flagBolsa, numBolsa ";
		
			resultado=ejecutarSentencia(query);
		return resultado;
	}
	*/

	
	
	private static int getIdLineaRX(String iDbolsaFechaDispTramo, int numeroBolsas) {
		int idLineaRX=9; //inicializada a la anterior a la 10, que es la primera
		String key=iDbolsaFechaDispTramo + "_" + numeroBolsas;
		//recuperamos el último idLineaRX guardado
		idLineaRX =detalleContenidoBolsasTratadas.getOrDefault(key, 9);
		//incrementamos en 1
		idLineaRX++;
		//guardamos la nueva
		detalleContenidoBolsasTratadas.put(key, idLineaRX);
		return idLineaRX;
	}
	
	private static int getNumeroBolsas(String iDbolsaFechaDispTramo, String valorToma, String dispensar) {
		int valorAcumulado = 0;
		int valorNuevoAcumulado = 0;
		int numBolsasAcumuladas = 0;	
		//valor a partir del cual se cambia de bolsa, por defecto utilizamos PASTILLERO (el mínimo entre el máximo de líneas escritas y el máx de pastillas por bolsa). 
		int valorDeCorteBolsa=Math.min(SPDConstants.MAX_COMPRIMIDOS_POR_BOLSA,SPDConstants.MAX_LINEAS_PASTILLERO_POR_BOLSA); 
		
		if(dispensar!=null && dispensar.equals("N"))
		{
			valorDeCorteBolsa=SPDConstants.MAX_LINEAS_SOLO_INFO_POR_BOLSA;
			valorToma="1"; //si es solo_info solo miramos lineas, ponemos 1 para que no cuente de más como comp
		}
			
			//comprimidos hasta ahora (se redondea al entero más próximo porque media pastilla cuenta como una)
			valorAcumulado = detalleBolsasTratadas.getOrDefault(iDbolsaFechaDispTramo, 0);
			//miramos bolsas acumuladas hasta ahora, teniendo en cuenta el número menor entre max comprimidos o max lineas
			numBolsasAcumuladas =	(int) Math.ceil((double) valorAcumulado/valorDeCorteBolsa);

			
			//por defecto nos daría 0, pero lo ponemos igualmente
			try {
				//valor de la toma ...
		    	double valorDouble = Double.parseDouble(valorToma);
		        //... redondeado al entero superior
		        int valorTomaRedondeado = (int) Math.ceil(valorDouble);
		        valorNuevoAcumulado=    valorTomaRedondeado+valorAcumulado; 
		        //miramos las bolsas con el valor que se le añade 
		        int numBolsasAcumuladasNuevo =	(int) Math.ceil((double)  valorNuevoAcumulado/valorDeCorteBolsa);
		        //Si salta de bolsa, añadimos una bolsa más
		        if(numBolsasAcumuladasNuevo>numBolsasAcumuladas)
		        	numBolsasAcumuladas=numBolsasAcumuladasNuevo;
		                	
			}
			catch(Exception e){}
		       //almacenamos o  actualizamos el nuevo valor acumulado 
			detalleBolsasTratadas.put(iDbolsaFechaDispTramo, valorNuevoAcumulado);
		
		return numBolsasAcumuladas;
	}


	private static DetallesTomasBean creaBeanPaso1DetallesTomas(String idUsuario, FicheroResiBean cab, ResultSet rs, List<Integer> posiciones, List<String> nombresTomas) throws Exception {

		DetallesTomasBean bean = new DetallesTomasBean();

		FicheroResiBean cabeceraTop=FicheroResiCabeceraDAO.getFicheroResiCabeceraByOid(idUsuario, cab.getOidFicheroResiCabecera());
		
	   	bean.setCIP(rs.getString("CIP"));
	   	
	   	bean.setCIP(rs.getString("CIP"));
	   	bean.setPlanta(rs.getString("planta"));
	   	bean.setHabitacion(rs.getString("habitacion"));
	   	bean.setCN(rs.getString("CN"));
	   	if(bean.getCN()!=null && bean.getCN().length()>6)
	   		bean.setCN(bean.getCN().substring(0, 6));
		bean.setNombreMedicamento(rs.getString("spdNombreBolsa"));

		String spdAccionBolsa=rs.getString("spdAccionBolsa");
		
		if(spdAccionBolsa!=null && spdAccionBolsa.equalsIgnoreCase("PASTILLERO"))
			bean.setDispensar("S");
			
		bean.setOrderNumber(PlantillaUnificadaHelper.getOrderNumber());
		
	   	SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat formatoObjetivo = new SimpleDateFormat("dd/MM/yyyy");

        //fecha de inicio SPD, teniendo en cuenta las posibles nuevas Fechas modificadas 
        String fechaInicioSPD = cabeceraTop.getNuevaFechaDesde();
        if(fechaInicioSPD==null || fechaInicioSPD.equals("")|| fechaInicioSPD.equals("null"))
        	fechaInicioSPD = cabeceraTop.getFechaDesde();
        if(fechaInicioSPD==null || fechaInicioSPD.equals("")|| fechaInicioSPD.equals("null"))
        	fechaInicioSPD=DateUtilities.getDateOrDateDefault(rs.getString("fechaDesde"), "yyyyMMdd", "dd/MM/yyyy");
        Date dateDesde = (Date) formatoObjetivo.parse(fechaInicioSPD);
        	
        //fecha fin de SPD
        String fechaFinSPD = cabeceraTop.getNuevaFechaHasta();
        if(fechaFinSPD==null || fechaFinSPD.equals("") || fechaFinSPD.equals("null"))
        	fechaFinSPD=DateUtilities.getDateOrDateDefault(cabeceraTop.getFechaHasta(), "yyyyMMdd", "dd/MM/yyyy");
        if(fechaFinSPD==null || fechaFinSPD.equals("")|| fechaFinSPD.equals("null"))
        	fechaFinSPD=DateUtilities.getDateOrDateDefault(rs.getString("fechaHasta"), "yyyyMMdd", "dd/MM/yyyy");
        Date dateHasta = (Date) formatoObjetivo.parse(fechaFinSPD);
        	
 
       	String fechaInicioTratamiento=rs.getString("resiInicioTratamiento");
       	String fechaFinTratamiento=rs.getString("resiFinTratamiento");
      	String fechaInicioTratamientoSPD=rs.getString("resiInicioTratamientoParaSPD");
       	String fechaFinTratamientoSPD=rs.getString("resiFinTratamientoParaSPD");
    	if(fechaInicioTratamientoSPD!=null && !fechaInicioTratamientoSPD.equals("") && fechaFinTratamientoSPD!=null && !fechaFinTratamientoSPD.equals(""))
    	{
    		fechaInicioTratamiento=fechaInicioTratamientoSPD;
    		fechaFinTratamiento=fechaFinTratamientoSPD;
    	}
    	if(fechaFinTratamiento==null || fechaFinTratamiento.equals(""))
    		fechaFinTratamiento="31/12/2099";
    		
    	Date dateInicioTratamiento = (Date) formatoObjetivo.parse(fechaInicioTratamiento);
    	Date dateFinTratamiento = (Date) formatoObjetivo.parse(fechaFinTratamiento);

 		bean.setDateInicioTratamiento(dateInicioTratamiento);
    	bean.setDateFinTratamiento(dateFinTratamiento);
    	
    	bean.setDateDesde(dateDesde);
    	bean.setDateHasta(dateHasta);
    	
       	String D1 = rs.getString("resiD1"); 
       	bean.setSpdD1(D1!=null&&D1.equalsIgnoreCase("X")); 
    	String D2 = rs.getString("resiD2"); 
    	bean.setSpdD2(D2!=null&&D2.equalsIgnoreCase("X"));
    	String D3 = rs.getString("resiD3"); 
    	bean.setSpdD3(D3!=null&&D3.equalsIgnoreCase("X"));
    	String D4 = rs.getString("resiD4"); 
    	bean.setSpdD4(D4!=null&&D4.equalsIgnoreCase("X"));
    	String D5 = rs.getString("resiD5"); 
    	bean.setSpdD5(D5!=null&&D5.equalsIgnoreCase("X"));
    	String D6 = rs.getString("resiD6"); 
    	bean.setSpdD6(D6!=null&&D6.equalsIgnoreCase("X"));
    	String D7 = rs.getString("resiD7"); 
    	bean.setSpdD7(D7!=null&&D7.equalsIgnoreCase("X"));

    	
    	 // Llenar las tomas dinámicas
        List<String> resiTomaList = new ArrayList<>();
        for (int i = 0; i < nombresTomas.size(); i++) {
           // String tomaColumnName = "resiToma" + posiciones.get(i);
        	//  String tomaColumnName = "["+nombresTomas.get(i)+"]";
        	 String tomaColumnName = nombresTomas.get(i);
        	 String valorToma=rs.getString(tomaColumnName);
        	 if(valorToma==null) valorToma="";
             if(valorToma.contains(",")) 
            	 valorToma=valorToma.replace(",", ".") ;
              resiTomaList.add(valorToma);
                 
        }
        bean.setResiTomas(resiTomaList);
    	

  		return bean;
	}


	public static FiliaDM getMedicamentosDeProceso(String spdUsuario, FicheroResiBean cab) throws SQLException {
		   Connection con = Conexion.conectar();
		   List<DrugDM>  result = new ArrayList();
		   FiliaDM dm = new FiliaDM();

		   String 	qry = " WITH cte AS ( ";
	 				qry+= "		SELECT DISTINCT RIGHT(d.spdCnFinal, 6) as code ";
	 				qry+= " 	, SUBSTRING(CONVERT(varchar(45), d.spdNombreBolsa) COLLATE Cyrillic_General_CI_AI ,1,45) as name  ";
	 				//qry+= "  	--		, SUBSTRING(CONVERT(varchar(45), b.nombre+' ' +b.presentacion) COLLATE Cyrillic_General_CI_AI ,1,45) as name ";
	 				qry+= "  	, '' as stockLocation ";
	 				qry+= "  	, '847000' + dbo.CalcularCN7(d.spdCnFinal) as barcode ";
	 				qry+= "  	, COALESCE(b.unidades, bd.contenido, 0)  as oneBottleQuantity ";
	 				qry+= "  	, ROW_NUMBER() OVER ( ";
	 				qry+= "  			PARTITION BY RIGHT(d.spdCnFinal, 6)  ";
	 				qry+= "  			 ORDER BY SUBSTRING(CONVERT(varchar(45), d.spdNombreBolsa) COLLATE Cyrillic_General_CI_AI, 1, 45), d.spdCnFinal ";
	 				qry+= "  	) AS rn ";
	 				qry+= "  	FROM SPD_ficheroResiDetalle d ";
	 				qry+= "  	INNER JOIN SPD_ficheroResiCabecera c ON c.oidFicheroResiCabecera=d.oidFicheroResiCabecera ";
	 				qry+= "  	LEFT JOIN bd_consejo b ON b.codigo=d.spdCnFinal ";
	 				qry+= "  	LEFT JOIN bd_pacientes p ON d.resiCIP=p.CIP ";
	 				qry+= "  	LEFT JOIN dbo.BDprescripcion bd ON bd.Codigo=RIGHT(d.spdCnFinal, 6) ";		    		
					qry+= "  	WHERE 1=1  ";
					qry+= "  	AND c.oidFicheroResiCabecera= '"+cab.getOidFicheroResiCabecera()+"'  ";
					qry+= "  	AND d.spdAccionBolsa in ('SOLO_INFO', 'PASTILLERO')  ";
					qry+= "  	AND ISNUMERIC(RIGHT(d.spdCnFinal, 6))=1 ";
                    qry+= "  	AND (p.SPD='S' or p.SPD is null)  ";
                    qry+= "  	AND COALESCE(NULLIF(resiInicioTratamientoParaSPD, ''), NULLIF(resiInicioTratamiento, ''))<= CAST(d.fechaHasta AS DATE)  "; //-- fechaInicioResi<= hastaSPD  
                    qry+= "  	AND COALESCE(NULLIF(resiFinTratamientoParaSPD, ''),  NULLIF(resiFinTratamiento, ''), CAST('2999-12-31' AS DATE))  >= CAST(d.fechaDesde AS DATE)   "; //  -- fechaFinResi >= inicioSPD   
                    		                      
                    qry+= "  	) ";
                    qry+= "  SELECT code, name, stockLocation, barcode, oneBottleQuantity ";
                    qry+= "  FROM cte ";
                    qry+= "  WHERE rn = 1; ";                    

		    		System.out.println(className + "--> getMedicamentosDeProceso" +qry );		
			     	ResultSet resultSet = null;
		 	 	
		 	    try {
		 	         PreparedStatement pstat = con.prepareStatement(qry);
		 	         resultSet = pstat.executeQuery();
		 	         while (resultSet.next()) {
		 	        	DrugDM d = new DrugDM();
		 	        	d.setCode(StringUtil.makeFlat(resultSet.getString("code"), false));
		 	        	d.setName(StringUtil.limpiarTextoTomas(resultSet.getString("name"), false));
		 	        	d.setStockLocation(resultSet.getString("stockLocation"));
		 	        	Bottle b = new Bottle();
		 	        	b.setBarcode(StringUtil.makeFlat(resultSet.getString("barcode"), false));
		 	        	b.setOneBottleQuantity(resultSet.getInt("oneBottleQuantity"));
			 	       	d.setBottle(b);
			 	       	result.add(d);
		 	            }
		 	     } catch (SQLException e) {
		 	         e.printStackTrace();
		 	     }

		 	    dm.setDrugs(result);
		 	     return dm;
	}
    

	public static boolean ejecutarSentencia(String sql) throws ClassNotFoundException, SQLException {
		
		int result=0;
		  Connection con = Conexion.conectar();
	  	 

			System.out.println(className + "--> nuevoPaso1DetallesTomas.ejecutarSentencia -->" +sql );		
			
	    try {
	         PreparedStatement pstat = con.prepareStatement(sql);
	         result=pstat.executeUpdate();
	         con.commit();
	         con.close();
	         pstat.close();
	       
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }
		
		return result>0;
	}




	public static boolean procesarExcepciones(String idUsuario, FicheroResiBean cabGlobal, FicheroResiBean cabDetalle) throws Exception {
		//Excepción Falguera, eliminar bolsas anteriores a las 16h del primer día y posteriores a las 15h del último día
		boolean result=true;
		String sql ="";
		
		String fechaDesde = (cabGlobal.getNuevaFechaDesde()!=null && !cabGlobal.getNuevaFechaDesde().equals("")?cabGlobal.getNuevaFechaDesde():cabGlobal.getFechaDesde());
		if(fechaDesde==null || fechaDesde.equals(""))
			fechaDesde = cabDetalle.getFechaDesde();
		
		String fechaHasta = (cabGlobal.getNuevaFechaHasta()!=null && !cabGlobal.getNuevaFechaHasta().equals("")?cabGlobal.getNuevaFechaHasta():cabGlobal.getFechaHasta());
		if(fechaHasta==null || fechaHasta.equals(""))
			fechaHasta = cabDetalle.getFechaHasta();
		
		fechaDesde = DateUtilities.convertFormatDateString(fechaDesde, "dd/MM/yyyy", "yyyyMMdd");
		fechaHasta = DateUtilities.convertFormatDateString(fechaHasta, "dd/MM/yyyy", "yyyyMMdd");

		
		//tratamiento de las tomas de inicio en el primer día y las finales del último. Por el siguiente orden:
		//1 - Se recupera de la configuración de la cabecera global de la producción en curso por si se han modificado. 
		//2 - En caso que no exista se recupera de la configuración general de las tomas de la residencia
		//3 - Si existe alguno de los dos valores se borran los registros previos o posteriores.
		
		String horaInicioPrimerDia = cabGlobal.getNuevaTomaDesde();
		String horaFinUltimoDia = cabGlobal.getNuevaTomaHasta();
		
		CabecerasXLSBean primerDiaDesdeToma = CabecerasXLSDAO.findByFilters(cabGlobal.getOidDivisionResidencia(), -1, -1, null, null, null, true, false);
		CabecerasXLSBean ultimoDiaHastaToma = CabecerasXLSDAO.findByFilters(cabGlobal.getOidDivisionResidencia(), -1, -1, null, null, null, false, true);
		
		horaInicioPrimerDia=(cabGlobal.getNuevaTomaDesde()!=null && !cabGlobal.getNuevaTomaDesde().equals("")?cabGlobal.getNuevaTomaDesde():primerDiaDesdeToma.getIdToma());
		horaFinUltimoDia=(cabGlobal.getNuevaTomaHasta()!=null && !cabGlobal.getNuevaTomaHasta().equals("")?cabGlobal.getNuevaTomaHasta():ultimoDiaHastaToma.getIdToma());

		if((horaInicioPrimerDia!=null && !horaInicioPrimerDia.equals("")) || (horaFinUltimoDia!=null && !horaFinUltimoDia.equals("")))
		//if(cabDetalle.getIdDivisionResidencia()!=null && cabDetalle.getIdDivisionResidencia().equalsIgnoreCase("general_mt_falguera")) 
		{
			 //horaInicioPrimerDia="1600";
			 //horaFinUltimoDia="1500";

			sql=" 	DELETE SPD_XML_detallesTomasRobot where idDivisionResidencia='"+cabDetalle.getIdDivisionResidencia()+"' and idProceso='"+cabDetalle.getIdProceso()+"' ";
			sql+=" 	AND ( ";
			sql+=" 	(substring(idBolsa,charindex('[',idBolsa)-8,8) = '"+fechaDesde+"' AND CAST(idToma AS INT)  < CAST('" + horaInicioPrimerDia + "' AS INT))";
			sql+=" 	 OR ";
			sql+=" 	(substring(idBolsa,charindex('[',idBolsa)-8,8) = '"+fechaHasta+"' AND CAST(idToma AS INT)  > CAST('" + horaFinUltimoDia + "' AS INT))";
			sql+=" 	) ";
			result=ejecutarSentencia(sql) ;
		}

		return result;
	}




	public static FiliaRX getTratamientosDeProceso(String spdUsuario, FicheroResiBean cab, DivisionResidencia div) throws SQLException, ParseException {
		   Connection con = Conexion.conectar();
		   List<DrugRX>  result = new ArrayList();
		   FiliaRX rx = new FiliaRX();
		   Basic basic = new Basic();
		   //basic.setLocationId(div.getNombreBolsa());
		   try{
			   // String fechaSpdDesde=DateUtilities.convertFormatDateString(cab.getFechaDesde(), "YYYYMMDD", "DDMM");
			   //String fechaSpdHasta=DateUtilities.convertFormatDateString(cab.getFechaHasta(), "YYYYMMDD", "DDMM");
			   // basic.setLocationId(div.getLocationId()+"_"+fechaSpdDesde+"_"+fechaSpdHasta);
			   basic.setLocationId(div.getLocationId()+"_"+cab.getFechaDesde());
		   }catch(Exception e)
		   {
			   basic.setLocationId(div.getNombreBolsa());
		   }
		   basic.setMachineNumber(1);
		   rx.setBasic(basic);
		   TreeMap<String, Patient> CIPS_TreeMap =new TreeMap<String, Patient>();
		   TreeMap<String, Pouch> bolsas_TreeMap =new TreeMap<String, Pouch>();
		   // * requestType	locationId	machineNumber	orderType	orderNumber	patientId	patientName	startDate	flag	offsetDays	doseTime	layout	freeInformation	code	val	id	text
		   /*
--, res.layoutBolsa as layout

 	*/
		   String qry = " SELECT dtr.CIP AS patientId ";
		   			qry+=  " , dtr.orderNumber ";
	   				//qry+=  " , frd.resiApellidosNombre as patientName  ";
		   			qry+=  " , (select top 1 resiApellidosNombre from SPD_ficheroResiDetalle where resiCIP=dtr.CIP) as patientName "; 
					qry+=  " , dtr.planta ";
					qry+=  " , dtr.habitacion ";
	   				qry+=  " , dtr.fechaToma AS startDate  ";
	   				qry+=  " , '' as stockLocation ";
					qry+=  " , CAST((SELECT CASE dispensar WHEN 'S' THEN '1' WHEN 'N' THEN '0' ELSE '0' END)  AS INT)  as flagBolsa  ";
					//qry+=  " , DATEDIFF(d, fechaDesde, dtr.fechaToma) as offsetDays ";
					qry+=  " , DATEDIFF(d, (select top 1 fechaDesde from SPD_ficheroResiDetalle where idProceso=dtr.idProceso), dtr.fechaToma) as offsetDays ";
					qry+=  " , nombreToma as doseTime ";
					qry+=  " , dtr.idFreeInformation as freeInformation ";
					qry+=  " , dtr.idBolsa as idBolsa ";
					qry+=  " , dtr.fechaToma ";
					qry+=  " , dtr.CN as  code ";
					qry+=  " , dtr.cantidadToma as val ";
					qry+=  " , dtr.idLineaRX as id ";
					qry+=  " , dtr.nombreMedicamento as textMedicamento ";
					qry+=  " FROM SPD_XML_detallesTomasRobot dtr ";
					//qry+=  " LEFT JOIN SPD_ficheroResiDetalle frd ON dtr.Cip=frd.resiCIP AND dtr.idDivisionResidencia=frd.idDivisionResidencia AND dtr.idProceso=frd.idProceso AND dtr.CN=frd.spdCnFinal ";
					qry+=  " WHERE 1=1 ";
					qry+=  " AND dtr.idDivisionResidencia='"+cab.getIdDivisionResidencia()+"' ";
					qry+=  " AND dtr.idProceso='"+cab.getIdProceso()+"' ";
					qry+=  " order by dtr.idBolsa, dtr.dispensar, dtr.numbolsa, dtr.idLineaRX  ";
					//ordenación importante  idBolsa contiene el cip y orden de la toma. dispensar para que salgan primero las N. Numero bolsa para que no mezcle contenido
					
		    		System.out.println(className + "--> getTratamientosDeProceso" +qry );		
			     	ResultSet resultSet = null;
	 	 	
		 	    try {
		 	         PreparedStatement pstat = con.prepareStatement(qry);
		 	         resultSet = pstat.executeQuery();
		 	         String CIP="";
		 	         Patient patient = null; 
		 	        
		 	         while (resultSet.next()) {
		 	        	 String idBolsa="";
		 	        	 CIP=resultSet.getString("patientId");
		 	        	//comprobación que el CIP está creado. En caso contrario se crea el objeto y se añade al TreeMap
		 	        	if (!CIPS_TreeMap.containsKey(CIP)) 
		 		      	{
		 		      		// se crea el Patient si no se ha tratado préviamente 
		 		      		patient = new Patient();
		 		      		//patient.setOrderNumber(resultSet.getString("freeInformation"));
		 		      		patient.setOrderNumber(resultSet.getString("orderNumber"));
		 		      		
		 		      		patient.setOrderType("0"); //está puesto a piñón en los ficheros 4
		 		      		patient.setStartDate(StringUtil.makeFlat(resultSet.getString("fechaToma").replace("/", ""), false));
		 		      		patient.setPatientId(StringUtil.makeFlat(CIP, true));
		 		      		patient.setPatientName(StringUtil.makeFlat(resultSet.getString("patientName"), false));
		 				    rx.getPatients().add(patient);

		 		      		//añadimos en treemap
		 			        CIPS_TreeMap.put(CIP, patient);
		 		      	}
		 	        	else 
		 	        	{
		 	        		patient = CIPS_TreeMap.get(CIP);
		 	        	}
		 	        		
		 	        	idBolsa=resultSet.getString("idBolsa");
		 	        	Pouch pouch = null;
		 	        	//comprobación que la bolsa está creada para añadir objetos en ella o crearla 
		 	        	if (!bolsas_TreeMap.containsKey(idBolsa)) 
		 		      	{
		 	        		pouch = new Pouch(); 
		 	        		pouch.setFlag(resultSet.getInt("flagBolsa"));
		 	        		pouch.setOffsetDays(resultSet.getInt("offsetDays"));
		 	        		pouch.setDoseTime(resultSet.getString("doseTime"));
		 	        		pouch.setLayout(div.getIdLayout());
		 	        		pouch.setFreeInformation(resultSet.getString("freeInformation"));
		 	        		//pouch.setFreeInformation(idBolsa);
		 	        		
		 	        		
		 	        		Print printNombre = new Print();
		 	        		printNombre.setId( 1);			//nombre residente en id=1
		 	        		printNombre.setText(StringUtil.makeFlat(resultSet.getString("patientName"), false));
		 	        		pouch.getPrints().add(printNombre);
		 	        		
		 	        		//tratamiento de las fechas
		 	        		Print printFecha = new Print();
		 	        		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy/MM/dd");
		 	               	Date date = inputFormat.parse(resultSet.getString("startDate"));
		 	        		SimpleDateFormat outputFormat = new SimpleDateFormat("EEEE, dd MMM yyyy", new Locale("es", "ES"));  //castellano por defecto, pero habría que configurarlo
		 	                String formattedDate = StringUtil.makeFlat(outputFormat.format(date), true);
		 	        		//String fechaBolsa = DateUtilities.convertFormatDateString(formattedDate, "yyyy/MM/dd", "EEEE, dd MMM yyyy");
		 	        		printFecha.setId( 51);			//nombre fecha en id=51
		 	        		printFecha.setText(formattedDate);
		 	        		pouch.getPrints().add(printFecha);

		 	        		//nombre de la toma
		 	        		Print printToma = new Print();
		 	        		printToma.setId( 52);	
		 	        		printToma.setText(StringUtil.makeFlat(resultSet.getString("doseTime"), false));
		 	        		pouch.getPrints().add(printToma);

		 	        		//nombre de la toma
		 	        		Print printResi = new Print();
		 	        		printResi.setId( 50);	
		 	        		printResi.setText(div.getNombreBolsa());
		 	        		pouch.getPrints().add(printResi);

		 	        		//nombre de la planta
		 	        		Print printPlanta = new Print();
		 	        		printPlanta.setId( 62);	
		 	        		printPlanta.setText(StringUtil.makeFlat(resultSet.getString("planta"), false));
		 	        		pouch.getPrints().add(printPlanta);

		 	        		//nombre de la habitacion
		 	        		Print printHabitacion = new Print();
		 	        		printHabitacion.setId( 63);	
		 	        		printHabitacion.setText(StringUtil.makeFlat(resultSet.getString("habitacion"), false));
		 	        		pouch.getPrints().add(printHabitacion);

		 	        		bolsas_TreeMap.put(idBolsa, pouch);
		 	        		patient.getPouches().add(pouch);
		 	        		
		 		      	}
		 	        	else
		 	        	{
		 	        		pouch = bolsas_TreeMap.get(idBolsa);
		 	        	}

	 	        		
	 	        		DrugRX drug = new DrugRX();
	 	        		drug.setCode(StringUtil.makeFlat(resultSet.getString("code"), false));
	 	        		drug.setVal(resultSet.getString("val"));
	 	        		pouch.getDrugs().add(drug);
	 	        		
	 	        		
	 	        		//nombre medicamento
	 	        		Print printMedicamento = new Print();
	 	        		printMedicamento.setId( resultSet.getInt("id"));
	 	        		printMedicamento.setText(StringUtil.makeFlat(resultSet.getString("textMedicamento"), false));
	 	        		pouch.getPrints().add(printMedicamento);
	 	        		
	 	        		//dosis
	 	        		Print printDosis = new Print();
	 	        		printDosis.setId( printMedicamento.getId()+ 20 );  // el valor de la dosis es el id del nombre medicamento + 20 
	 	        		printDosis.setText(resultSet.getString("val"));
	 	        		pouch.getPrints().add(printDosis);


		 	            }
		 	     } catch (SQLException e) {
		 	         e.printStackTrace();
		 	     }

		 	     return rx;
	}




	
}