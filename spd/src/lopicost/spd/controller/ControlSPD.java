package lopicost.spd.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;
import lopicost.spd.model.BdConsejo;
import lopicost.spd.persistence.*;
import lopicost.spd.struts.bean.*;
import lopicost.spd.utils.*;


/** String Util: recopilación de utilidades para tratamiento de Strings
 * @author ccostap
 */
public class ControlSPD{
	
	
	/**
	 * Función para poder transformar las fechas Excel numéricas en días desde el 30/12/1899 al formato DD/MM/YYYY
	 * @param numeroDeDias
	 * @return Fecha formateada
	 */
	public static String convertirNumeroAFecha(String numeroDeDias) {
        int dias = Integer.parseInt(numeroDeDias);
        
        // Ajustar la fecha base a "30/12/1899" para que "45047" dé como resultado "01/05"
        LocalDate fechaBase = LocalDate.of(1899, 12, 30);
        LocalDate fechaCalculada = fechaBase.plusDays(dias);

        // Utilizar el formato "dd/MM" solo si el día es mayor que 0
        String resultado = fechaCalculada.getDayOfMonth() > 0
                ? fechaCalculada.format(DateTimeFormatter.ofPattern("dd/MM"))
                : "";

        return resultado;
    }

	
	/**
	 * Método que devuelve el total de dosis que se resultan del fichero de la residencia. Es la suma de las pautas del dia, multiplicado por las "X" marcadas.
	 * En caso que no lleguen X marcadas, se dan por marcadas los 7 días.
	 * Para los casos de los tratamientos periódicos,.... de momento miramos que la pauta que sale de origen sea igual o mayor que la pauta que se envía a robot
	 * PRECONDICION - Previamente ha de haber pasado por detectarPeriodoAlta 
	 * @param medResi
	 * @return 
	 */
	public static float contarDosisProduccionResi(FicheroResiBean medResi) {
		return contarDosisProduccion(medResi, SPDConstants.NUM_TOMAS_DEFAULT_RESI);
	}
	
	public static float contarDosisProduccionSPD(FicheroResiBean medResi) {
		return contarDosisProduccion(medResi, SPDConstants.NUM_TOMAS_SPD);
	}
	
	public static float contarDosisProduccion(FicheroResiBean medResi, int resiTomas) {
		float totalProduccion =0;
		float totalDia = HelperSPD.getTotalComprimidosDia(medResi, resiTomas);
		long diasProduccion = HelperSPD.obtenerDiasProduccionSPD(medResi.getFechaDesde(), medResi.getFechaHasta());
		
		
		if(medResi.getResiPeriodo().contentEquals(SPDConstants.SPD_PERIODO_DIARIO) )
			totalProduccion = totalDia*diasProduccion;

		if(medResi.getResiPeriodo().contentEquals(SPDConstants.SPD_PERIODO_DIAS_SEMANA_CONCRETOS)
				|| medResi.getResiPeriodo().contentEquals(SPDConstants.SPD_PERIODO_SEMANAL) 
				)
			totalProduccion = totalDia*medResi.getDiasSemanaMarcados();
		
		//miramos si entran en la producción
		if(medResi.getResiPeriodo().contentEquals(SPDConstants.SPD_PERIODO_QUINCENAL))		
		{
			totalProduccion=totalDia;
		}
		if(medResi.getResiPeriodo().contentEquals(SPDConstants.SPD_PERIODO_MENSUAL))
		{
			int diasMesConcretosProduccion = 1; //por defecto
			try{
				String[] numerosSeparados = medResi.getDiasMesConcretos().split(",");
			      // Contar la cantidad de elementos en el array
				diasMesConcretosProduccion = numerosSeparados.length;
			}
			catch(Exception e){}
			totalProduccion=totalDia*diasMesConcretosProduccion;
		}
		if(medResi.getResiPeriodo().contentEquals(SPDConstants.SPD_PERIODO_ESPECIAL))
		{
			//no hacemos nada porque ya se hace el control en HelperSPD.desdoblarTratamientosSecuenciales 
		}

		return totalProduccion;
	}

	
	public static void controlComprimidos(String spdUsuario, FicheroResiBean medResi) throws ClassNotFoundException {
		
		boolean esAegerus =medResi.getIdProcessIospd()!=null && medResi.getIdProcessIospd().equalsIgnoreCase(SPDConstants.IDPROCESO_AEGERUS);    

		float epsilon = 0.2f; // Umbral de tolerancia
			if(!HelperSPD.checkSintrom(medResi)  
				&& !HelperSPD.checkTrazodona(medResi)  
				&&  medResi!=null && medResi.getSpdAccionBolsa()!=null 
				&& (medResi.getSpdAccionBolsa().equals(SPDConstants.SPD_ACCIONBOLSA_PASTILLERO) || medResi.getSpdAccionBolsa().equals(SPDConstants.SPD_ACCIONBOLSA_SOLO_INFO)))
		{
				
			if(esAegerus)
			{
				int diasConToma=AegerusHelper.dameDiasProduccion(medResi);
				medResi.setPrevisionSPD(diasConToma *  HelperSPD.getTotalComprimidosDia(medResi, 24));
			}
			else 
				medResi.setPrevisionSPD(ControlSPD.contarDosisProduccionSPD(medResi));
			
			
			
			//if(medResi.getPrevisionResi()==medResi.getPrevisionSPD())
			if (Math.abs(medResi.getPrevisionResi() - medResi.getPrevisionSPD()) < epsilon)
			{
				medResi.setControlNumComprimidos(SPDConstants.CTRL_NCOMPRIMIDOS_IGUAL);
				
			}
			else
			{
				medResi.setControlNumComprimidos(SPDConstants.CTRL_NCOMPRIMIDOS_DIFERENTE);
				if(!medResi.getConfirmar().equalsIgnoreCase(SPDConstants.REGISTRO_CONFIRMADO))
				{
					medResi.setValidar(SPDConstants.REGISTRO_VALIDAR);
					medResi.setConfirmar(SPDConstants.REGISTRO_CONFIRMAR);			
				}
			}
	
		}
		else  medResi.setControlNumComprimidos(SPDConstants.CTRL_NCOMPRIMIDOS_NOAPLICA);
		
		System.out.println( "ControlSPD controlComprimidos:  medResi.getPrevisionResi --> "  + medResi.getPrevisionResi() );
		System.out.println( "ControlSPD controlComprimidos:  medResi.getPrevisionSPD --> "  + medResi.getPrevisionSPD() );
		System.out.println( "ControlSPD controlComprimidos: epsilon   Math.abs(medResi.getPrevisionResi() - medResi.getPrevisionSPD() --> " + epsilon + " - "  + Math.abs(medResi.getPrevisionResi() - medResi.getPrevisionSPD() ));
		System.out.println( "ControlSPD controlComprimidos:  medResi.setControlNumComprimidos --> "  + medResi.getControlNumComprimidos() );

	}

	
	
	
	/**
	 * Control para ver si hay algún cambio en lo que se recibe y lo que se envía.
	 * Salta alerta en caso que se reciba lo mismo y se envíe algo diferente respecto a lo anterior.
	 * Salta alerta en caso que se reciba algo diferente y se envíe lo mismo que lo anterior.
	 * No salta alarma si se recibe lo mismo y se envía lo mismo
	 * No salta alarma si no se recibe lo mismo y no se envía lo mismo
	 * @param spdUsuario
	 * @param medResi
	 * @throws Exception
	 */
	public static void controlarCambiosRespectoAnterior(String spdUsuario, FicheroResiBean medResi) throws Exception {
		
		boolean esExcelStauros = medResi.getIdProcessIospd()!=null && medResi.getIdProcessIospd().equalsIgnoreCase(SPDConstants.IDPROCESO_STAUROS);    
		boolean esAegerus  = medResi.getIdProcessIospd()!=null && medResi.getIdProcessIospd().equalsIgnoreCase(SPDConstants.IDPROCESO_AEGERUS);    
		boolean esMontseny =medResi.getIdProcessIospd()!=null && medResi.getIdProcessIospd().equalsIgnoreCase(SPDConstants.IDPROCESO_MONTSENY);    
		System.out.println(new Date() + " esExcelStauros / esAegerus / esMontseny -->  " + esExcelStauros + " / " + esAegerus + " / " +  esMontseny);
		
		
		FicheroResiBean medResiAnterior = HelperSPD.recuperaDatosAnteriores(spdUsuario, medResi, true);
		
		//registro nuevo
		if(medResiAnterior==null || medResi==null){
			medResi.setControlRegistroAnterior(SPDConstants.CTRL_REGISTRO_ANTERIOR_RD_SD);
			return;
		}
		//además de ver lo que llega exactamente, miramos también este id para descartar cambios menores en formatos de fecha por ejemplo
		String idTratamientoCIPActual=medResi.getIdTratamientoCIP();
		String idTratamientoCIPAnterior=medResiAnterior.getIdTratamientoCIP();
		boolean idTratamientosCIPIguales = false;
		try{
			idTratamientosCIPIguales = idTratamientoCIPActual.equalsIgnoreCase(idTratamientoCIPAnterior);
		}catch(Exception e){}
		
		
		String detalleRowActual= "";
		String detalleRowKeyActual= "";
		String detalleRowKeyLiteActual= "";
		String detalleRowKeyLiteActualFechas= "";
		try{ 
			
				detalleRowActual = medResi!=null && medResi.getDetalleRow()!=null && !medResi.getDetalleRow().equals("") ? medResi.getDetalleRow().toUpperCase():"detalleResiActual"; 
				detalleRowActual = StringUtil.quitaEspacios(HelperSPD.getDetalleRowFechasOk(detalleRowActual));
				if(esAegerus) 
					detalleRowActual = AegerusHelper.getDetalleRowAegerus(detalleRowActual);
				detalleRowKeyActual  = medResi!=null && medResi.getDetalleRowKey()!=null && !medResi.getDetalleRowKey().equals("") ? medResi.getDetalleRowKey().toUpperCase():"detalleRowKeyActual"; 
				detalleRowKeyActual  = StringUtil.quitaEspacios(HelperSPD.getDetalleRowFechasOk(detalleRowKeyActual));

				detalleRowKeyLiteActual  = medResi!=null && medResi.getDetalleRowKeyLite()!=null && !medResi.getDetalleRowKeyLite().equals("") ? medResi.getDetalleRowKeyLite().toUpperCase():"detalleRowKeyActualLite"; 
				detalleRowKeyLiteActual  = StringUtil.quitaEspacios(HelperSPD.getDetalleRowFechasOk(detalleRowKeyLiteActual));

				//ya llega arreglado
				detalleRowKeyLiteActualFechas 		= medResi!=null && medResi.getDetalleRowKeyLiteFechas()!=null && !medResi.getDetalleRowKeyLiteFechas().equals("") ? medResi.getDetalleRowKeyLiteFechas().toUpperCase():"detalleRowKeyLiteFechasActual"; 
				
			
			System.out.println(new Date() + " detalleRowActual -->  " + detalleRowActual);
			System.out.println(new Date() + " detalleRowKeyActual -->  " + detalleRowKeyActual);
			System.out.println(new Date() + " detalleRowKeyLiteActual -->  " + detalleRowKeyLiteActual);
			System.out.println(new Date() + " detalleRowKeyLiteActualFechas -->  " + detalleRowKeyLiteActualFechas);
			
		}catch(Exception e){}
		
		String detalleRowAnterior= "";
		String detalleRowKeyAnterior= "";
		String detalleRowKeyLiteAnterior= "";
		String detalleRowKeyLiteAnteriorFechas= "";
		try{ 
			detalleRowAnterior = medResiAnterior!=null && medResiAnterior.getDetalleRow()!=null && !medResiAnterior.getDetalleRow().equals("") ? medResiAnterior.getDetalleRow().toUpperCase():"detalleResiAnterior"; 
			detalleRowAnterior=HelperSPD.getDetalleRowFechasOk(StringUtil.quitaEspacios(detalleRowAnterior));
			if(esAegerus) detalleRowAnterior = AegerusHelper.getDetalleRowAegerus(detalleRowAnterior);

			String a = medResiAnterior.getDetalleRowKeyLite();
			String b = medResiAnterior.getDetalleRowKeyLiteFechas();
			
			detalleRowKeyAnterior  = medResiAnterior!=null && medResiAnterior.getDetalleRowKey()!=null && !medResiAnterior.getDetalleRowKey().equals("") ? medResiAnterior.getDetalleRowKey().toUpperCase():"detalleRowKeyAnterior"; 
			detalleRowKeyAnterior=HelperSPD.getDetalleRowFechasOk(StringUtil.quitaEspacios(detalleRowKeyAnterior));

			detalleRowKeyLiteAnterior = medResiAnterior!=null && medResiAnterior.getDetalleRowKeyLite()!=null && !medResiAnterior.getDetalleRowKeyLite().equals("") ? medResiAnterior.getDetalleRowKeyLite().toUpperCase():"detalleRowKeyLiteAnterior"; 
			detalleRowKeyLiteAnterior = HelperSPD.getDetalleRowFechasOk(StringUtil.quitaEspacios(detalleRowKeyLiteAnterior));

			detalleRowKeyLiteAnteriorFechas  = medResiAnterior!=null && medResiAnterior.getDetalleRowKeyLiteFechas()!=null && !medResiAnterior.getDetalleRowKeyLiteFechas().equals("") ? medResiAnterior.getDetalleRowKeyLiteFechas().toUpperCase():"detalleRowKeyLiteFechasAnterior"; 

			
			System.out.println(new Date() + " detalleRowAnterior -->  " + detalleRowAnterior);
			System.out.println(new Date() + " detalleRowKeyAnterior -->  " + detalleRowKeyAnterior);

		}catch(Exception e){}
		
		String idTratamientoSPDActual= "";
		try{ idTratamientoSPDActual = medResi.getIdTratamientoSPD().replace("|_|", "|0|");  }catch(Exception e){} //para no tener en cuenta si se ha puesto '0' o es vacío
		
		String idTratamientoSPDAnterior = "";
		try{ idTratamientoSPDAnterior = medResiAnterior.getIdTratamientoSPD().replace("|_|", "|0|"); }catch(Exception e){}  //para no tener en cuenta si se ha puesto '0' o es vacío;
		
		boolean detalleRowKeyIguales =	
				   Objects.equals(detalleRowActual, detalleRowAnterior)
				|| Objects.equals(detalleRowKeyActual, detalleRowKeyAnterior)
				|| Objects.equals(detalleRowKeyActual, detalleRowAnterior)
				|| Objects.equals(detalleRowActual, detalleRowKeyAnterior)
				|| Objects.equals(detalleRowKeyLiteActual, detalleRowKeyLiteAnterior)
				;

		boolean idTratamientoSPDIguales = Objects.equals(idTratamientoSPDActual, idTratamientoSPDAnterior);

		System.out.println(new Date() + " detalleRowKeyActual / detalleRowKeyAnterior -->  " + detalleRowKeyIguales );

		//CASO 1 - Resi envía igual y SPD se envía igual - No alerta - Reutilizado ok
		if((detalleRowKeyIguales)  && idTratamientoSPDIguales)
			medResi.setControlRegistroAnterior(SPDConstants.CTRL_REGISTRO_ANTERIOR_RI_SI);
		
		//CASO 2 - Resi envía diferente y SPD también diferente - No alerta - Registro nuevo (Revisión si cabe)  
		if( !detalleRowKeyIguales && !idTratamientoSPDIguales)
			medResi.setControlRegistroAnterior(SPDConstants.CTRL_REGISTRO_ANTERIOR_RD_SD);
		
		//CASO 3 -  Resi envía igual y SPD envía diferente al anterior -  Alerta - CONFIRMAR 
		if(( detalleRowKeyIguales || idTratamientosCIPIguales ) && !idTratamientoSPDIguales)
		{
			//if(!medResi.getConfirmar().equalsIgnoreCase(SPDConstants.REGISTRO_CONFIRMADO)
			//if(!medResi.getResiPeriodo().contentEquals(SPDConstants.SPD_PERIODO_QUINCENAL)
			//	&& !medResi.getResiPeriodo().contentEquals(SPDConstants.SPD_PERIODO_MENSUAL))		
			//{
			//if(!medResi.getValidar().equals(SPDConstants.REGISTRO_VALIDADO))
			medResi.setValidar(SPDConstants.REGISTRO_VALIDAR);
				
			if(!medResi.getConfirmar().equalsIgnoreCase(SPDConstants.REGISTRO_CONFIRMADO) )
					medResi.setConfirmar(SPDConstants.REGISTRO_CONFIRMAR);			
			//}
			medResi.setControlRegistroAnterior(SPDConstants.CTRL_REGISTRO_ANTERIOR_RI_SD);
		}
		
		//CASO 4 -  Resi envía diferente y SPD se envía igual que el anterior -  Alerta - Confirmar 
		if(!detalleRowKeyIguales && !idTratamientosCIPIguales && idTratamientoSPDIguales)
		{
			//if(!medResi.getValidar().equals(SPDConstants.REGISTRO_VALIDADO))
				medResi.setValidar(SPDConstants.REGISTRO_VALIDAR);
			
			//if(!medResi.getConfirmar().equalsIgnoreCase(SPDConstants.REGISTRO_CONFIRMADO) )
			{
				medResi.setConfirmar(SPDConstants.REGISTRO_CONFIRMAR);			
			}
			medResi.setControlRegistroAnterior(SPDConstants.CTRL_REGISTRO_ANTERIOR_RD_SI);

		}
		System.out.println( "ControlSPD controlPrincActivos:  medResi.setControlRegistroAnterior --> "  + medResi.getControlRegistroAnterior() );
	
	}


	/**
	 * Control para saber si el tratamiento se traa en el robot o  no 
	 * @param spdUsuario
	 * @param medResi
	 */
	public static void controlRobot(String spdUsuario, FicheroResiBean medResi) {
		if(medResi!=null && medResi.getSpdAccionBolsa()!=null && (medResi.getSpdAccionBolsa().equals(SPDConstants.SPD_ACCIONBOLSA_PASTILLERO) || medResi.getSpdAccionBolsa().equals(SPDConstants.SPD_ACCIONBOLSA_SOLO_INFO)))
			medResi.setControlRegistroRobot(SPDConstants.CTRL_ROBOT_SE_ENVIA_A_ROBOT);
		else 
			medResi.setControlRegistroRobot(SPDConstants.CTRL_ROBOT_NO_SE_ENVIA);
		
		System.out.println( "ControlSPD controlPrincActivos:  medResi.setControlRegistroRobot --> "  + medResi.getControlRegistroRobot() );

		
	}

	/**
	 * Método para controlar principios activos que se desean controlar, ya sea por MTE (Margen terapéutico estrecho) o por cualqueir otro motivo. 
	 * @param spdUsuario
	 * @param medResi
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void controlPrincActivos(String spdUsuario, FicheroResiBean medResi, boolean nConfirmaciones) throws ClassNotFoundException, SQLException {
		boolean control = false;
	
		
		if( medResi!=null && medResi.getSpdAccionBolsa()!=null && ( medResi.getSpdAccionBolsa().equals(SPDConstants.SPD_ACCIONBOLSA_PASTILLERO)
					|| medResi.getSpdAccionBolsa().equals(SPDConstants.SPD_ACCIONBOLSA_SOLO_INFO)) )
			control = ControlPrincipioActivoDAO.contar(spdUsuario, medResi)>0 ;
		
		if(control) 
		{
			medResi.setControlPrincipioActivo(SPDConstants.CTRL_PRINCIPIO_ACTIVO_ALERTA);
			if(medResi.getIdEstado().equalsIgnoreCase(SPDConstants.REGISTRO_ORIGINAL) ) //añadimos mensaje de revisión de CN vs receta
			{
				if(!medResi.getMensajesResidencia().contains(SPDConstants.INFO_RESIDENCIA_AVISO_CN_RECETA) 
						&& !medResi.getEditado().equalsIgnoreCase("SI") //necesario para que no se vuelva a incluir al editarse
					)
					medResi.setMensajesResidencia(SPDConstants.INFO_RESIDENCIA_AVISO_CN_RECETA);
			}
		}
		else medResi.setControlPrincipioActivo(SPDConstants.CTRL_PRINCIPIO_ACTIVO_NO_ALERTA);
		 
		if(control && nConfirmaciones) confirmaNVeces( spdUsuario,  medResi, SPDConstants.CTRL_PRINCIPIO_ACTIVO_N_VALIDACIONES);
		
		System.out.println( "ControlSPD controlPrincActivos:  medResi.setControlPrincipioActivo --> "  + medResi.getControlPrincipioActivo() );
		System.out.println( "ControlSPD controlPrincActivos:  medResi.getConfirmaciones --> "  + medResi.getConfirmaciones());
		System.out.println( "ControlSPD controlPrincActivos:  medResi.getConfirmar --> "  + medResi.getConfirmar());

		
		
	}

	/***
 	 * Método para detectar tratamientos que tienen un GTVM (Principio activo) igual que otro tratamiento del mismo residente 
	 * @param idUsuario
	 * @param medResi
	 * @throws Exception 
	 */

	
	private static void controlarUnicoGtvm(String idUsuario, FicheroResiBean medResi) throws Exception {
		if(medResi!=null && medResi.getSpdAccionBolsa()!=null && (medResi.getSpdAccionBolsa().equals(SPDConstants.SPD_ACCIONBOLSA_PASTILLERO) || medResi.getSpdAccionBolsa().equals(SPDConstants.SPD_ACCIONBOLSA_SOLO_INFO)))
		{
			int cuantos = 0;
			try {
				cuantos = FicheroResiDetalleDAO.contarDistintosPActivosPorCIPyGTVM(medResi);
			}
			catch(Exception e){
				
			}
			System.out.println(new Date() + " 1 cuantos: " + cuantos);
			if(cuantos>0) 
				medResi.setControlUnicoGtvm(SPDConstants.CTRL_UNICO_GTVM_ALERTA);
			else  
				medResi.setControlUnicoGtvm(SPDConstants.CTRL_UNICO_GTVM_OK);

			System.out.println(new Date() + " 2 medResi.getControlUnicoGtvm(): " + medResi.getControlUnicoGtvm());

			if(medResi!=null && medResi.getControlUnicoGtvm().equalsIgnoreCase(SPDConstants.CTRL_UNICO_GTVM_ALERTA))
			{
				actualizaRestoGTVM(medResi, false); //para que si que se actualice el ALERTA en todos, aunque esté confirmado.
			
				if(!medResi.getConfirmar().equalsIgnoreCase(SPDConstants.REGISTRO_CONFIRMADO))
				{
					medResi.setValidar(SPDConstants.REGISTRO_VALIDAR);
					medResi.setConfirmar(SPDConstants.REGISTRO_CONFIRMAR);	
				}
					//actualizaRestoGTVM(medResi, true);	//para que aparezcan a validar los que no están confirmados 
			}

				
		}
		
	}

	
	private static void actualizaRestoGTVM(FicheroResiBean medResi,  boolean confirmar) throws SQLException {
		FicheroResiDetalleDAO.actualizaRestoGtvmCip(medResi, confirmar);
	}


	private static void confirmaNVeces(String spdUsuario, FicheroResiBean medResi, int nVeces) {
		int confirmaciones = medResi.getConfirmaciones()+1; //sumamos uno porque contamos con la que sería ésta
		
		//if(medResi.getConfirmar()!=null && (medResi.getConfirmar().equalsIgnoreCase(SPDConstants.REGISTRO_CONFIRMADO) || (medResi.getConfirmar().equalsIgnoreCase(SPDConstants.REGISTRO_ENPROCESO_CONFIRMACION))))
		{
			if(confirmaciones<= nVeces )
				//	medResi.setConfirmar(SPDConstants.REGISTRO_ENPROCESO_CONFIRMACION);
				medResi.setConfirmar(SPDConstants.REGISTRO_CONFIRMAR);
		}
		//else 
		//	medResi.setConfirmar(SPDConstants.REGISTRO_CONFIRMADO);
		
	}


	/**
	 * 
	 * @param spdUsuario
	 * @param medResi
	 */
	public static void controlNoSustituibles(String spdUsuario, FicheroResiBean medResi) {
		if(medResi==null || medResi.getResiCn()==null || medResi.getSpdCnFinal()==null )
		{
			medResi.setControlNoSustituible("");
			return;
		}
			
			
		if(medResi!=null && medResi.getBdConsejo()!=null && medResi.getSpdAccionBolsa()!=null && medResi.getSpdAccionBolsa().equals(SPDConstants.SPD_ACCIONBOLSA_PASTILLERO)) 
		{
			if (medResi.getBdConsejo().getSustituible()!=null && medResi.getBdConsejo().getSustituible().equalsIgnoreCase("N"))
			{
				String resiCn = medResi.getResiCn();
				if(resiCn.length()>6) 		
					resiCn = medResi.getResiCn().substring(0, 6);
				
				String spdCn  = medResi.getSpdCnFinal();
				//alerta en caso que sea NO sustituible y se haya sutituido
				if(!resiCn.equalsIgnoreCase(spdCn))
				{
					medResi.setControlNoSustituible(SPDConstants.CTRL_SUSTITUIBLE_ALERTA);
					if(!medResi.getConfirmar().equalsIgnoreCase(SPDConstants.REGISTRO_CONFIRMADO))
					{
						medResi.setValidar(SPDConstants.REGISTRO_VALIDAR);
						medResi.setConfirmar(SPDConstants.REGISTRO_CONFIRMAR);			
					}
					
					if(medResi.getIdEstado().equalsIgnoreCase(SPDConstants.REGISTRO_ORIGINAL) ) //añadimos mensaje de revisión de CN vs receta
					{
						if(!medResi.getMensajesResidencia().contains(SPDConstants.INFO_RESIDENCIA_AVISO_CN_RECETA) 
								&& !medResi.getEditado().equalsIgnoreCase("SI") //necesario para que no se vuelva a incluir al editarse
							)
							medResi.setMensajesResidencia(SPDConstants.INFO_RESIDENCIA_AVISO_CN_RECETA);
					}

					
					

				}
				else 
					medResi.setControlNoSustituible(SPDConstants.CTRL_SUSTITUIBLE_NOALERTA);
			}
			else 
				medResi.setControlNoSustituible(SPDConstants.CTRL_SUSTITUIBLE_NOALERTA);
		}
		else 
			medResi.setControlNoSustituible(SPDConstants.CTRL_SUSTITUIBLE_NOALERTA);
		
		System.out.println( "ControlSPD controlPrincActivos:  medResi.setControlNoSustituible --> "  + medResi.getControlNoSustituible() );

	}

/*
	public static void controlDiferentesGtvmp(String spdUsuario, FicheroResiBean medResi) {
		// TODO Esbozo de método generado automáticamente
		
	}
*/

	public static void controlValidarDatos(String spdUsuario, FicheroResiBean medResi) {
		if(medResi!=null 
				&& (medResi.getValidar()!=null && medResi.getValidar().equalsIgnoreCase(SPDConstants.REGISTRO_VALIDAR) 
					|| (medResi.getConfirmar()!=null && medResi.getConfirmar().equalsIgnoreCase(SPDConstants.REGISTRO_CONFIRMAR)) 
					))
			medResi.setControlValidacionDatos(SPDConstants.CTRL_VALIDAR_ALERTA);
		else if(medResi!=null 
				&& (medResi.getValidar()!=null && medResi.getValidar().equalsIgnoreCase(SPDConstants.REGISTRO_VALIDADO)
				 || (medResi.getConfirmar()!=null && medResi.getConfirmar().equalsIgnoreCase(SPDConstants.REGISTRO_CONFIRMADO))
					))
			medResi.setControlValidacionDatos(SPDConstants.CTRL_VALIDAR_NO);
		else	medResi.setControlValidacionDatos(SPDConstants.CTRL_VALIDAR_NO);
		
		if(medResi!=null && ( medResi.getSpdCnFinal() ==null ||  medResi.getSpdCnFinal().equals("")
							|| medResi.getSpdNombreBolsa() ==null ||  medResi.getSpdNombreBolsa().equals("") 
							|| medResi.getSpdAccionBolsa() ==null ||  medResi.getSpdAccionBolsa().equals("") ))
			medResi.setControlValidacionDatos(SPDConstants.CTRL_VALIDAR_ALERTA);
	
		System.out.println( "ControlSPD controlPrincActivos:  medResi.setControlValidacionDatos --> "  + medResi.getControlValidacionDatos() );

	}

	public static void aplicarControles(String idUsuario, FicheroResiBean medResi) throws Exception {
		aplicarControles( idUsuario,  medResi, true);  //por defecto se tendrán en cuenta las Nconfirmaciones 
	}

		
	public static void aplicarControles(String idUsuario, FicheroResiBean medResi, boolean nConfirmaciones) throws Exception {
		System.out.println(  new Date() +  "ControlSPD aplicarControles: INICIO "  );
		
		HelperSPD.actualizarIDTratamientoSPD(medResi); 

		//por último hacemos control de alertas
		//if(!medResi.getConfirmar().equalsIgnoreCase(SPDConstants.REGISTRO_CONFIRMADO) 
		//		&& ( medResi.getSpdAccionBolsa().equals(SPDConstants.SPD_ACCIONBOLSA_PASTILLERO) || medResi.getSpdAccionBolsa().equals(SPDConstants.SPD_ACCIONBOLSA_SOLO_INFO)))

		ControlSPD.revisarPeriodicos(idUsuario, medResi); //control en validaciones de los tratamientos periódicos
		
		ControlSPD.controlGtvmpCnResiCnSpd(idUsuario, medResi);//este control primero para rellenar BDConsejo en el medResi


		
		if( medResi.getSpdAccionBolsa().equals(SPDConstants.SPD_ACCIONBOLSA_PASTILLERO) || medResi.getSpdAccionBolsa().equals(SPDConstants.SPD_ACCIONBOLSA_SOLO_INFO))
		{
	    	// HelperSPD.controlarReceta(getSpdUsuario(), medResi);
			ControlSPD.controlCNValido(idUsuario, medResi);
	    	ControlSPD.controlComprimidos(idUsuario, medResi);
	    	ControlSPD.controlarCambiosRespectoAnterior(idUsuario, medResi);
		}
    	ControlSPD.controlRobot(idUsuario, medResi);
    	ControlSPD.controlNoSustituibles(idUsuario, medResi);
    	ControlSPD.controlPrincActivos(idUsuario, medResi, nConfirmaciones); // como hay más confirmaciones en este método ha de ir después de las anteriores, 
    	ControlSPD.controlValidarDatos(idUsuario, medResi);
		ControlSPD.controlarUnicoGtvm(idUsuario, medResi);

		System.out.println("ControlSPD aplicarControles: INICIO");


	}


	/**
	 * Control del CN numérico
	 * @param idUsuario
	 * @param medResi
	 */
	private static boolean controlCNValido(String idUsuario, FicheroResiBean medResi) {
		boolean result =true;
			try{
				if(!DataUtil.isNumeroGreatherThanZero(medResi.getSpdCnFinal()))
				{
					medResi.setConfirmar(SPDConstants.REGISTRO_VALIDAR);
					medResi.setConfirmar(SPDConstants.REGISTRO_CONFIRMAR);
					medResi.setControlValidacionDatos(SPDConstants.CTRL_VALIDAR_ALERTA);
					medResi.setMensajesAlerta(SPDConstants.INFO_INTERNA_CNFINAL_NOVALIDO);
					medResi.setIncidencia("SI");
					result =false;
				}
				else
				{
					if(medResi.getMensajesAlerta().contains(SPDConstants.INFO_INTERNA_CNFINAL_NOVALIDO)) //eliminamos mensaje anterior
						HelperSPD.borrarMensajeAvisoAnterior(medResi, SPDConstants.ALERTA_INTERNA_CNFINAL_NOVALIDO, "ALERTA");
				}
		
			}catch(Exception e){
			}
		return result;
	}


	/*Los registros periódicos o que no son diarios se han de validar al menos una vez
	 * 
	 * 
	 */
	private static void revisarPeriodicos(String idUsuario, FicheroResiBean medResi) {
		if((medResi.getResiPeriodo().equalsIgnoreCase(SPDConstants.SPD_PERIODO_DIAS_SEMANA_CONCRETOS) 
			|| medResi.getResiPeriodo().equalsIgnoreCase(SPDConstants.SPD_PERIODO_SEMANAL)
			|| medResi.getResiPeriodo().equalsIgnoreCase(SPDConstants.SPD_PERIODO_QUINCENAL)
			|| medResi.getResiPeriodo().equalsIgnoreCase(SPDConstants.SPD_PERIODO_MENSUAL)
			|| medResi.getResiPeriodo().equalsIgnoreCase(SPDConstants.SPD_PERIODO_SEMESTRAL)
			|| medResi.getResiPeriodo().equalsIgnoreCase(SPDConstants.SPD_PERIODO_ANUAL)					
			|| medResi.getResiPeriodo().equalsIgnoreCase(SPDConstants.SPD_PERIODO_ESPECIAL))
		&& ( medResi.getConfirmar()!=null &&  !medResi.getConfirmar().equalsIgnoreCase(SPDConstants.REGISTRO_CONFIRMADO) )	
		&& ( medResi.getSpdAccionBolsa().equals(SPDConstants.SPD_ACCIONBOLSA_PASTILLERO) || medResi.getSpdAccionBolsa().equals(SPDConstants.SPD_ACCIONBOLSA_SOLO_INFO))
		)
			
			medResi.setConfirmar(SPDConstants.REGISTRO_CONFIRMAR);
		
		System.out.println( "ControlSPD revisarPeriodicos:  medResi.setConfirmar --> "  + medResi.getConfirmar() );

		
	}


	public static void controlGtvmpCnResiCnSpd(String idUsuario, FicheroResiBean medResi) throws ClassNotFoundException, SQLException {
		boolean iguales =true;
		String cnresi=medResi.getResiCn();
		if(cnresi!=null && !cnresi.isEmpty()&& cnresi.length() > 6) 
			cnresi = cnresi.substring(0, 6);
		
		String cnspd=medResi.getSpdCnFinal();
		if(cnspd!=null && !cnspd.isEmpty()&& cnspd.length() > 6) 
			cnspd = cnspd.substring(0, 6);
	        
		BdConsejo cnResi= BdConsejoDAO.getByCN(cnresi);
		BdConsejo cnSpd= BdConsejoDAO.getByCN(cnspd);
		
		rellenarBdConsejo(medResi, cnSpd);
		
		String gtvmpResi="";
		String gtvmpSpd="";
		if(cnResi!=null && cnSpd!=null) 
		{
			gtvmpResi=cnResi.getNomGtVmp();
			gtvmpSpd=cnSpd.getNomGtVmp();
		}
		else 
		{
			medResi.setControlDiferentesGtvmp("");
			return;
		}

		if(gtvmpResi!=null && gtvmpSpd!=null && !gtvmpResi.isEmpty() && !gtvmpSpd.isEmpty() && !gtvmpResi.equalsIgnoreCase(gtvmpSpd))
			iguales=false;
		
		//eliminamos posibles mensajes anteriores, sobre este tipo de mensaje (info2)
		String mensaje=HelperSPD.recuperaMensajeActualizado(medResi.getMensajesResidencia(), "1#");
		//System.out.println("iguales" + iguales);
		if(!iguales)
		{
			//preparamos el nuevo info2 y lo añadimos
			String mensajeNuevoInfo1 ="1# -  Corregir Código nacional -  El CN " + medResi.getResiCn() + " pertenece a " + gtvmpResi + ". Si se trata de " + gtvmpSpd + " sugerimos cambiarlo por el CN " + medResi.getSpdCnFinal() + " - 1#";
			mensaje=mensaje + " " +  mensajeNuevoInfo1;
			if(!medResi.getConfirmar().equalsIgnoreCase(SPDConstants.REGISTRO_CONFIRMADO))
			{
				medResi.setValidar(SPDConstants.REGISTRO_VALIDAR);
				medResi.setConfirmar(SPDConstants.REGISTRO_CONFIRMAR);			
			}
					

			medResi.setControlDiferentesGtvmp(SPDConstants.CTRL_DIFERENTE_GTVMP_ALERTA);
			
 		}
		else {
			medResi.setNomGtVmpp(gtvmpSpd);
			medResi.setControlDiferentesGtvmp(SPDConstants.CTRL_DIFERENTE_GTVMP_OK);
		}
		//medResi.setMensajesInfo(mensaje);
		medResi.setMensajesResidencia(mensaje);
		
		System.out.println( "ControlSPD controlGtvmpCnResiCnSpd:  medResi.setControlDiferentesGtvmp --> "  + medResi.getControlDiferentesGtvmp() );

	}


	private static void rellenarBdConsejo(FicheroResiBean medResi, BdConsejo cnSpd) {
		if(medResi!=null && medResi.getBdConsejo()==null && cnSpd!=null)
		{
			medResi.setBdConsejo(cnSpd);
			medResi.setSpdCodGtVm(cnSpd.getCodGtVm());
			medResi.setSpdNomGtVm(cnSpd.getNomGtVm());
			medResi.setSpdCodGtVmp(cnSpd.getCodGtVmp());
			medResi.setSpdNomGtVmp(cnSpd.getNomGtVmp());
			medResi.setSpdCodGtVmpp(cnSpd.getCodGtVmpp());
			medResi.setSpdNomGtVmpp(cnSpd.getNomGtVmpp());
			medResi.setSpdEmblistable(cnSpd.getEmblistable());
			medResi.setSustituible(cnSpd.getSustituible());
		}
		
		
	}
	
	  public static String getMetodoLlamada() {
	    	String metodoLlamada="";
	    	
	    	try
	    	{
		    	// Obtener la pila de llamadas
		        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
			 if (stackTrace.length >= 4) { // al menos dos elementos en la pila
			            StackTraceElement metodoAnterior = stackTrace[3];
			            metodoLlamada=metodoAnterior.getMethodName();
			            System.out.println("Método anterior: " + metodoAnterior.getMethodName());
				 }
	    	}
	    	catch(Exception e)
	    	{
	    		
	    	}
			 return metodoLlamada;
	    }


	public static boolean registroValidable(FicheroResiBean frbean) {
		
		//if(frbean.getValidar().equalsIgnoreCase(SPDConstants.REGISTRO_VALIDAR) && (frbean.getIncidencia()==null || (frbean.getIncidencia()!=null && !frbean.getIncidencia().equalsIgnoreCase("SI"))))
		if(frbean!=null && frbean.getValidar()!=null && frbean.getValidar().equalsIgnoreCase(SPDConstants.REGISTRO_VALIDAR))
		{
			return true;
		}

		return false;
	}

	public static boolean registroConfirmable(FicheroResiBean frbean) {
		
		if(frbean!=null && frbean.getConfirmar()!=null &&frbean.getConfirmar().equalsIgnoreCase(SPDConstants.REGISTRO_CONFIRMAR) && (frbean.getIncidencia()==null || (frbean.getIncidencia()!=null && !frbean.getIncidencia().equalsIgnoreCase("SI"))))
		{
			return true;
		}

		return false;
	}

	public static boolean registroConfirmableAutomatico(FicheroResiBean frbean) {
		
		if(frbean!=null && frbean.getConfirmar()!=null &&frbean.getConfirmar().equalsIgnoreCase(SPDConstants.REGISTRO_CONFIRMAR) && (frbean.getMensajesAlerta()==null || (frbean.getMensajesAlerta().equals("") )))
		{
			return true;
		}

		return false;
	} 
	
	


}
