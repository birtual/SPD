
package lopicost.spd.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TreeMap;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lopicost.config.pool.dbaccess.Conexion;
import lopicost.spd.controller.ControlSPD;
import lopicost.spd.struts.bean.FicheroResiBean;

/**
 * 
 */
public class AegerusHelper{
	
	
	/**
	 * 
    * id que se utiliza para detectar cambios = resiCIP + resiCn + resiMedicamento + resiInicioTratamiento + resiFinTratamiento +  
 	* + resiToma1...resiToma24
    * @param medResi
    * @return
	 * @throws Exception 
    */
   public static String getIDAegerus(FicheroResiBean medResi) throws Exception {
		
   	String keyOk="";
 
   	keyOk= medResi.getResiCIP()
   			+"_"+medResi.getResiCn()
   			+"_"+StringUtil.limpiarTextoTomas(medResi.getResiMedicamento())
   			+"_"+medResi.getResiInicioTratamiento()
   			+"_"+medResi.getResiFinTratamiento()
   			+"_"+medResi.getResiToma1()+"_"+medResi.getResiToma2()+"_"+medResi.getResiToma3()+"_"+medResi.getResiToma4()+"_"+medResi.getResiToma5()+"_"+medResi.getResiToma6()
   			+"_"+medResi.getResiToma7()+"_"+medResi.getResiToma8()+"_"+medResi.getResiToma9()+"_"+medResi.getResiToma10()+"_"+medResi.getResiToma11()+"_"+medResi.getResiToma12()
   			+"_"+medResi.getResiToma13()+"_"+medResi.getResiToma14()+"_"+medResi.getResiToma15()+"_"+medResi.getResiToma16()+"_"+medResi.getResiToma17()+"_"+medResi.getResiToma18()
   			+"_"+medResi.getResiToma19()+"_"+medResi.getResiToma20()+"_"+medResi.getResiToma21()+"_"+medResi.getResiToma22()+"_"+medResi.getResiToma23()+"_"+medResi.getResiToma24()
			+"_"+medResi.getResiPeriodo();
   		
   		//quitamos todos los caracteres que no son letras/números 
   		keyOk = keyOk.replaceAll("[^a-zA-Z0-9_]", "");
	    
	   	System.out.println("----- getID --> " + keyOk);
   	return keyOk;
	}

   

	/**
	 * Método que buscará los días de toma concretos del mes para los casos que la frecuencia sea mensual o quincenal
	 * @param medResi
	 * @throws Exception 
	 */
	public static boolean getDatosProduccionAnterior(String spdUsuario, FicheroResiBean medResi, boolean porDetallRow) throws Exception {
		
		FicheroResiBean medResiAnterior = HelperSPD.recuperaDatosAnteriores(spdUsuario, medResi, porDetallRow);
		if(medResiAnterior!=null) 
			AegerusHelper.actualizaCamposDelAnterior(spdUsuario, medResi, medResiAnterior);
		return medResiAnterior!=null;
			
	}
   

	
	public static void actualizaCamposDelAnterior(String spdUsuario, FicheroResiBean medResi, FicheroResiBean medResiAnterior) throws Exception {
		
		if(medResiAnterior!=null)
		{
			//ponemos la información del último registro editado
			medResi.setResiSiPrecisa(medResiAnterior.getResiSiPrecisa());
			medResi.setSpdCnFinal(medResiAnterior.getSpdCnFinal());
			medResi.setSpdNombreBolsa(medResiAnterior.getSpdNombreBolsa());
			medResi.setSpdFormaMedicacion(medResiAnterior.getSpdFormaMedicacion());
			medResi.setSpdAccionBolsa(medResiAnterior.getSpdAccionBolsa());
			medResi.setResiInicioTratamiento(medResiAnterior.getResiInicioTratamiento());
			medResi.setResiFinTratamiento(medResiAnterior.getResiFinTratamiento());

			medResi.setMensajesInfo(medResiAnterior.getMensajesInfo()); 
			medResi.setMensajesAlerta(medResiAnterior.getMensajesAlerta());
			medResi.setMensajesResidencia(medResiAnterior.getMensajesResidencia());
			medResi.setIncidencia(medResiAnterior.getIncidencia());
			medResi.setNumeroDeTomas(medResiAnterior.getNumeroDeTomas());
			medResi.setIdEstado(SPDConstants.REGISTRO_REUTILIZADO_DE_ANTERIOR_PRODUCCION); //estado
			medResi.setEditado(medResiAnterior.getEditado()); 
			medResi.setValidar(medResiAnterior.getValidar()); 
			medResi.setConfirmar(medResiAnterior.getConfirmar()); 
			medResi.setConfirmaciones(medResiAnterior.getConfirmaciones());
			
			if(medResi.getConfirmar().equalsIgnoreCase(SPDConstants.REGISTRO_CONFIRMADO)) //para control del número de validaciones
			{
				int confirmaciones = medResiAnterior.getConfirmaciones();
				int nConfirmaciones = SPDConstants.CTRL_PRINCIPIO_ACTIVO_N_VALIDACIONES;
				if(nConfirmaciones>confirmaciones)
					medResi.setConfirmaciones(medResiAnterior.getConfirmaciones()+1); 
			}

			
			medResi.setEditable(true);
		}
		
	}
   
   
	/**
	 */
	public final static String isNull(String str, String defaultValue) {
		String result =(str == null || str.isEmpty() || str.equals("")|| str.equals("0")) ? defaultValue : str ; 
	        return result;
	}
	    

	public static void detectarToma(FicheroResiBean fila, String dosis, String horaToma) {
		if(horaToma!=null && !horaToma.equals("")){
			 if (horaToma.equals("01:00")) {
			        fila.setResiToma1(dosis);
			    } else if (horaToma.equals("02:00")) {
			        fila.setResiToma2(dosis);
			    } else if (horaToma.equals("03:00")) {
			        fila.setResiToma3(dosis);
			    } else if (horaToma.equals("04:00")) {
			        fila.setResiToma4(dosis);
			    } else if (horaToma.equals("05:00")) {
			        fila.setResiToma5(dosis);
			    } else if (horaToma.equals("06:00")) {
			        fila.setResiToma6(dosis);
			    } else if (horaToma.equals("07:00")) {
			        fila.setResiToma7(dosis);
			    } else if (horaToma.equals("08:00")) {
			        fila.setResiToma8(dosis);
			    } else if (horaToma.equals("09:00")) {
			        fila.setResiToma9(dosis);
			    } else if (horaToma.equals("10:00")) {
			        fila.setResiToma10(dosis);
			    } else if (horaToma.equals("11:00")) {
			        fila.setResiToma11(dosis);
			    } else if (horaToma.equals("12:00")) {
			        fila.setResiToma12(dosis);
			    } else if (horaToma.equals("13:00")) {
			        fila.setResiToma13(dosis);
			    } else if (horaToma.equals("14:00")) {
			        fila.setResiToma14(dosis);
			    } else if (horaToma.equals("15:00")) {
			        fila.setResiToma15(dosis);
			    } else if (horaToma.equals("16:00")) {
			        fila.setResiToma16(dosis);
			    } else if (horaToma.equals("17:00")) {
			        fila.setResiToma17(dosis);
			    } else if (horaToma.equals("18:00")) {
			        fila.setResiToma18(dosis);
			    } else if (horaToma.equals("19:00")) {
			        fila.setResiToma19(dosis);
			    } else if (horaToma.equals("20:00")) {
			        fila.setResiToma20(dosis);
			    } else if (horaToma.equals("21:00")) {
			        fila.setResiToma21(dosis);
			    } else if (horaToma.equals("22:00")) {
			        fila.setResiToma22(dosis);
			    } else if (horaToma.equals("23:00")) {
			        fila.setResiToma23(dosis);
			    } else if (horaToma.equals("24:00")) {
			        fila.setResiToma24(dosis);
			    } else if (horaToma.equals("00:00")) {
			        fila.setResiToma24(dosis);
			    }
			
		
		}
		
	}
	
	
	/**
	 * Valida fechas en formato "DD/MM/YYYY", "D/M/YYYY", "DD-MM-YYYY" y "D-M-YYYY". E
	 * @param fecha
	 * @return
	 */
	
	 public static boolean esFechaAegerus(String fecha) {
	        // Expresión regular para varios formatos posibles
	        String regex = "^(\\d{1,2})([/-])(\\d{1,2})([/-])(\\d{4})$";
	        // Compilar la expresión regular en un patrón
	        Pattern pattern = Pattern.compile(regex);
	        // Crear un matcher para la cadena de texto
	        Matcher matcher = pattern.matcher(fecha);
	        // Comprobar si la cadena coincide con el patrón
	        return matcher.matches();
	    }
	

		/**
	     * Método que valida si la celda leida tiene un "NO" por lo que sabemos que es rtatamiento válido pero no se toma ese día
	     * @param dia
	     * @return
	     */
		public static boolean noTomarEseDia(String dia) {
			boolean esNo=false;
			if(dia!=null && !dia.equals("") && dia.contains("NO")){
				esNo =true;
			}
			return esNo;
		}
		
	    /**
	     * Método que valida si la celda leida tiene una fecha o un "NO" por lo que sabemos que es rtatamiento válido
	     * @param dia
	     * @return
	     */
		public static boolean checkFinalDias(String dia) {
			return !noTomarEseDia(dia) && !esFechaAegerus(dia);
		}


		public static void marcaTodosDias(FicheroResiBean fila) {
			//Aegerus nos envía los días por fecha, por lo tanto podemos marcar siempre todos los días 
			fila.setResiD1("X");
			fila.setResiD2("X");
			fila.setResiD3("X");
			fila.setResiD4("X");
			fila.setResiD5("X");
			fila.setResiD6("X");
			fila.setResiD7("X");
			fila.setResiDiasAutomaticos("SI");
		}

		/**
		 * Devuelve un TreeMap con las fechas del rango de dos fechas pasadas por parámetro. Clave Fecha, Valor Día de la fecha 
		 * @param fechaDesde
		 * @param fechaHasta
		 * @return
		 */
		public static TreeMap getTreeMapDiasRango(String fechaDesde, String fechaHasta) {
		   LocalDate desde = null;
		   LocalDate hasta = null;
	
		   TreeMap diasRango=new TreeMap();
		   try{
		       	desde = LocalDate.parse(fechaDesde, DateTimeFormatter.ofPattern("yyyyMMdd"));
		        hasta = LocalDate.parse(fechaHasta, DateTimeFormatter.ofPattern("yyyyMMdd"));
		   }
		   catch(Exception e) {}
		
		   while(desde.isBefore(hasta))
		   {
			   diasRango.put(desde, desde.getDayOfMonth());
			   desde=desde.plusDays(1);
		   }
		   diasRango.put(hasta, hasta.getDayOfMonth());
		   
		   return diasRango;
		}

		
		/**
		 * 
		 * @param fechaDesde
		 * @param fechaHasta
		 * @return
		 */
		public static String buscarDiasRangoSPD(String fechaDesde, String fechaHasta) {
		   	LocalDate desde = null;
		   	LocalDate hasta = null;
		   	LocalDate verificar = null;	
		   	String diasRangoSPD="";
		       try{
		       	desde = LocalDate.parse(fechaDesde, DateTimeFormatter.ofPattern("yyyyMMdd"));
		        hasta = LocalDate.parse(fechaHasta, DateTimeFormatter.ofPattern("yyyyMMdd"));
		       }
		       catch(Exception e) {}
		
			   while(desde.isBefore(hasta))
			   {
				   diasRangoSPD+=desde.getDayOfMonth()+",";
				   desde=desde.plusDays(1);
			   }
			   diasRangoSPD+=hasta.getDayOfMonth()+",";
				
				return diasRangoSPD;
			}



		public static int dameDiasProduccion(FicheroResiBean medResi) {
			int diasProduccion = 0;
			try{
				if(medResi.getResiPeriodo().equalsIgnoreCase(SPDConstants.SPD_PERIODO_MENSUAL))
				{
					diasProduccion=contarDiasProduccion(medResi.getDiasMesConcretos());
				}
				else if(medResi.getResiPeriodo().equalsIgnoreCase(SPDConstants.SPD_PERIODO_DIAS_SEMANA_CONCRETOS))
				{
					diasProduccion=0;
				}
				else if(medResi.getResiPeriodo().equalsIgnoreCase(SPDConstants.SPD_PERIODO_DIARIO))
				{
					diasProduccion = (int) HelperSPD.obtenerDiasProduccionSPD(medResi.getFechaDesde(), medResi.getFechaHasta());
				}
			}
			catch(Exception e){
				
			}
			return diasProduccion;
		}
		
		
		

		public static int contarDiasProduccion(String diasAegerusNumerico) {
       // Dividir la cadena en un array de cadenas basado en la coma como delimitador
      String[] numerosSeparados = diasAegerusNumerico.split(",");
       
       // Contar la cantidad de elementos en el array
       return numerosSeparados.length;
	}



/**
 * Para que no salgan a validar/confirmar siempre todos los registros, nos quedaremos con los campos que hay hasta llegar a la primera fecha(columna 10)
 * MEDICAMENTO	CODIGO NACIONAL	CIP	NOMBRE	HABITACION	FECHA INICIO	FECHA FINAL	DOSIS	UNIDAD ADMINISTRACION	HORA
 * @param detalleResiActual
 */

		public static String getDetalleRowAegerus(String detalleResiActual) {
			String detalleRowAegerus=detalleResiActual;
			if(detalleResiActual!=null)
			{
				String[] textoSeparado = detalleResiActual.split(",");
			      // Contar la cantidad de elementos en el array
				if(textoSeparado.length >=10)
				{
					detalleRowAegerus=textoSeparado[0]+",";
					for(int i=1; i<=8;i++){
						detalleRowAegerus+=textoSeparado[i]+",";
					}
					detalleRowAegerus+=textoSeparado[9];
				}
			}
			return detalleRowAegerus;
				
			
		}
		
		public static void aplicarControles(String idUsuario, FicheroResiBean medResi, boolean nConfirmaciones) throws Exception {
			System.out.println( "ControlSPD aplicarControles: INICIO "  + new Date() );
			
			HelperSPD.actualizarIDTratamientoSPD(medResi); 
			ControlSPD.controlGtvmpCnResiCnSpd(idUsuario, medResi);//este control primero para rellenar BDConsejo en el medResi

			if( medResi.getSpdAccionBolsa().equals(SPDConstants.SPD_ACCIONBOLSA_PASTILLERO) || medResi.getSpdAccionBolsa().equals(SPDConstants.SPD_ACCIONBOLSA_SOLO_INFO))
			{
		    	// HelperSPD.controlarReceta(getSpdUsuario(), medResi);
		    	ControlSPD.controlComprimidos(idUsuario, medResi);
		    	ControlSPD.controlarCambiosRespectoAnterior(idUsuario, medResi);
			}
	    	ControlSPD.controlRobot(idUsuario, medResi);
	    	ControlSPD.controlNoSustituibles(idUsuario, medResi);
	    	ControlSPD.controlPrincActivos(idUsuario, medResi, nConfirmaciones); // como hay más confirmaciones en este método ha de ir después de las anteriores, 
	    	ControlSPD.controlValidarDatos(idUsuario, medResi);

			System.out.println("ControlSPD aplicarControles: INICIO");


		}

		/**
		 * UPDATE [SPDAC].[dbo].[tmp_detallesTomas_mtFalguera] SET [tramoToma] = '09H00' 
			WHERE UPPER([tramoToma]) = '08H00' 
			AND CIP <>'BEGA1451215004' --Salud Bellido García (10 sept 2021)
			AND 
			(
				--CIP <>'CACA1461116002' --María Jesús Cascudo Cascudo exitus 11/10/2022
				--19112018 Solo mantenemos las 8h en caso que sea el CIP 'CACA1461116002' 
				--25102022 Mantenemos las 8h si es TARDYFERON
				(
					--CIP ='CACA1461116002' AND
					CN NOT IN  (SELECT [CN_OK] FROM [SPDAC].[dbo].[tbl_xls_mtFalguera] WHERE UPPER(NOMBRE_CORTO) LIKE '%TARDYFERON%' )
				)
			)
			
			GO
			UPDATE [SPDAC].[dbo].[tmp_detallesTomas_mtFalguera] SET [tramoToma] = '09H00' WHERE UPPER([tramoToma]) like '9%'
			GO
			UPDATE [SPDAC].[dbo].[tmp_detallesTomas_mtFalguera] SET [tramoToma] = '20H00' WHERE UPPER([tramoToma]) like '19%'
			GO
			UPDATE [SPDAC].[dbo].[tmp_detallesTomas_mtFalguera] SET [tramoToma] = '22H00' WHERE UPPER([tramoToma]) like '21%'
			GO

		 * @param spdUsuario
		 * @param medResi
		 * @param qery
		 * @throws Exception
		 */
		public static void aplicarExcepcionesFalguera(String spdUsuario, FicheroResiBean medResi)  throws Exception {
			boolean result=false;
			String query =" UPDATE dbo.SPD_ficheroResiDetalle SET  resiToma9 = resiToma8 ";
					query+=" WHERE TIPOREGISTRO <>  'CABECERA'  AND  LEN(resiToma9)=0 AND LEN(resiToma8)>0 ";
					query+=" AND idProceso='"+medResi.getIdProceso()+"' AND resiCIP <>'BEGA1451215004'"  ;
					query+=" AND spdNombreBolsa like '%TARDYFERON%'"  ;
			ejecutaSentencia(query);
			
			query =" UPDATE dbo.SPD_ficheroResiDetalle set resiToma8 = 0 ";
			query+=" WHERE TIPOREGISTRO <>  'CABECERA'  AND LEN(resiToma8)>0 AND idProceso='"+medResi.getIdProceso()+"' AND resiCIP <>'BEGA1451215004'"  ;
			query+=" AND spdNombreBolsa like '%TARDYFERON%'"  ;
			ejecutaSentencia(query);

			ejecutaSentencia(" UPDATE dbo.SPD_ficheroResiDetalle SET resiToma20 = resiToma19 WHERE TIPOREGISTRO <>  'CABECERA'  AND LEN(resiToma20)=0 AND LEN(resiToma19)>0 AND idProceso='"+medResi.getIdProceso()+"'");
			ejecutaSentencia(" UPDATE dbo.SPD_ficheroResiDetalle SET resiToma19 = 0 WHERE TIPOREGISTRO <>  'CABECERA'  AND LEN(resiToma19)>0 AND idProceso='"+medResi.getIdProceso()+"'");
			ejecutaSentencia(" UPDATE dbo.SPD_ficheroResiDetalle SET resiToma22 = resiToma21 WHERE TIPOREGISTRO <>  'CABECERA'  AND LEN(resiToma22)=0 AND LEN(resiToma21)>0 AND idProceso='"+medResi.getIdProceso()+"'");
			ejecutaSentencia(" UPDATE dbo.SPD_ficheroResiDetalle SET resiToma21 = 0 WHERE TIPOREGISTRO <>  'CABECERA'  AND LEN(resiToma21)>0 AND idProceso='"+medResi.getIdProceso()+"'");
		  	 
			
	}


/**
 * 
 * @param spdUsuario
 * @param medResi
 * @throws Exception
 */
		public static void ejecutaSentencia(String qry)  throws Exception {
					boolean result=false;
					  Connection con = Conexion.conectar();
	
			      		 
				    try {
				         PreparedStatement pstat = con.prepareStatement(qry);
				         result=pstat.executeUpdate()>0;
				       
				     } catch (SQLException e) {
				         e.printStackTrace();
				     }finally {con.close();}
				
			}






	public static Vector comenzarEnMediodia(String fechaDesde, Vector row) {
		LocalDate desde = LocalDate.parse(fechaDesde, DateTimeFormatter.ofPattern("yyyyMMdd"));
		String hora = (String) row.get(9);
		String[] partesHora = hora.split(":");
		int horas = Integer.parseInt(partesHora[0]);
		if (horas <= 15) {
			for (int i = 10; i < row.size(); i++) {		//desde la posición 10, que empiezan los días
				String fecha = (String) row.get(i);
				try{
					LocalDate fechaDate = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					if(desde.isEqual(fechaDate)) row.set(i, "NO_PONER");	//es fechaDesde y hora igual o menor que 15:00
				}
				catch(Exception e){}
		}
		}
		return row;
	}
	
	public static Vector acabarEnMediodia(String fechaHasta, Vector row) {
		LocalDate hasta = LocalDate.parse(fechaHasta, DateTimeFormatter.ofPattern("yyyyMMdd"));
		String hora = (String) row.get(9);
		String[] partesHora = hora.split(":");
		int horas = Integer.parseInt(partesHora[0]);
		if (horas > 15) {
			for (int i = 10; i < row.size(); i++) {		//desde la posición 10, que empiezan los días
				String fecha = (String) row.get(i);
				try{
					LocalDate fechaDate = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					if(hasta.isEqual(fechaDate)) row.set(i, "NO_PONER");	//es fechaHasta y hora mayor que 15:00
				}
				catch(Exception e){}

		}
		}
		return row;
	}

}