package lopicost.spd.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;

import lopicost.spd.helium.model.Dose;
import lopicost.config.pool.dbaccess.Conexion;
import java.util.regex.Pattern;


public class DoseDAO
{
    static String className = "DoseDAO";

    
    
    public static Dose getTomaCabecera(String idDivisionResidencia, String nomDose, int lang, int numeroToma) throws ClassNotFoundException, SQLException {
        
         String qry = " SELECT "; 
    	qry+=  " c.idDivisionResidencia, c.idToma, c.nombreToma, c.horaToma ";
    	qry+=  " , c.posicionEnBBDD, c.posicionEnVistas, c.langToma, c.tipo  ";
    	qry+=  " FROM SPD_cabecerasXLS c	";
    	qry+=  " WHERE c.idDivisionResidencia ='"+idDivisionResidencia+"' ";
    	qry+=  " AND c.nombreToma ='"+nomDose+"'	";
    	qry+=  " AND c.langToma ='"+lang+"' ";
    	//qry+=  " order by c.posicionEnVistas ";
    	
         final Connection con = Conexion.conectar();
        System.out.println(String.valueOf(DoseDAO.className) + "--> getTomaCabecera  -->" + qry);
        ResultSet resultSet = null;
    	
        Dose dose = new Dose();
       	dose.setIdDivisionResidencia(idDivisionResidencia);
    	dose.setNombreDose(nomDose);
      	dose.setLang(lang);
      	dose.setName(nomDose);//es lo que saldrá en la bolsa, ahora coincide con la cabecera, pero en un futuro habría que analizarlo
      	
  	
        try {
            final PreparedStatement pstat = con.prepareStatement(qry);
            resultSet = pstat.executeQuery();
            while (resultSet.next()) {
            	
            	
            	String nombreDose=resultSet.getString("nombreToma");
            	if(nombreDose==null || nombreDose.equals("")) nombreDose=nomDose;
            	dose.setNombreDose(nombreDose);
             	
            	String idDose=resultSet.getString("idToma");
            	dose.setIdDose(idDose);
             	
            	String hour=resultSet.getString("horaToma");
            	dose.setHour(hour);

            	String name=resultSet.getString("nombreToma");
            	if(name==null || name.equals("")) name=nomDose;
            	dose.setName(name);
            	
            	dose.setTipo(resultSet.getString("tipo"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        finally {
 
         	if(dose.getCodeDose()==null)
        			generaDoseBasica(dose, numeroToma);

            con.close();
        }
		return dose;
    }
    
    
 /*   
    public static Dose getDoseByFilters(String idDivisionResidencia, String nomDose, int lang, int numeroToma) throws ClassNotFoundException, SQLException {
        
    	
        
        String qry = " SELECT def.idDivisionResidencia as defecto, def.nombreDose as nombreDoseDef, "; 
    	qry+=  " def.idDose as idDoseDef, def.hour as hourDef, def.name as nameDef,	 ";
    	qry+=  " res.idDivisionResidencia, res.nombreDose as nombreDoseRes, res.idDose as idDoseRes, ";
    	qry+=  " res.hour as hourRes, res.name as nameRes ";
    	qry+=  " , COALESCE(res.nombreDose, def.nombreDose) as  nombreDose ";
    	qry+=  " , COALESCE(res.idDose, def.idDose) as  idDose ";
    	qry+=  " , COALESCE(res.hour, def.hour) as  hour ";
    	qry+=  " , COALESCE(res.name, def.name) as  name ";
    	qry+=  " FROM (	";
    	qry+=  "	SELECT r.idDivisionResidencia, r.nombreDose, r.idDose, r.hour, r.name, r.lang ";
    	qry+=  "	FROM dbo.SPD_resiDoses r ";
    	qry+=  "	WHERE 1=1 ";
    	qry+=  "	AND r.idDivisionResidencia ='defecto' ";
    	qry+=  " 	AND r.nombreDose ='"+nomDose+"'	 ";
    	qry+=  "	AND r.lang ='"+lang+"' ";
    	qry+=  " ) def FULL OUTER JOIN	 ";
    	qry+=  " ( ";
    	qry+=  "	SELECT r.idDivisionResidencia, r.nombreDose, r.idDose, r.hour, r.name, r.lang ";
    	qry+=  "	FROM dbo.SPD_resiDoses r ";
    	qry+=  "	WHERE 1=1 ";
    	qry+=  "	AND r.idDivisionResidencia ='"+idDivisionResidencia+"' ";
    	qry+=  "	AND r.nombreDose ='"+nomDose+"'	 ";
    	qry+=  "	AND r.lang ='"+lang+"' ";
    	qry+=  " ) res ";
    	qry+=  " ON (def.nombreDose=res.nombreDose) ";
     

        //qry+=  " order by r.idDose  ";
        final Connection con = Conexion.conectar();
        System.out.println(String.valueOf(DoseDAO.className) + "--> getDoseByFilters  -->" + qry);
        ResultSet resultSet = null;
    	
        Dose dose = new Dose();
       	dose.setIdDivisionResidencia(idDivisionResidencia);
    	dose.setNombreDose(nomDose);
      	dose.setLang(lang);
      	dose.setName(nomDose);//es lo que saldrá en la bolsa, ahora coincide con la cabecera, pero en un futuro habría que analizarlo
  	
        try {
            final PreparedStatement pstat = con.prepareStatement(qry);
            resultSet = pstat.executeQuery();
            while (resultSet.next()) {
            	
            	
            	String nombreDose=resultSet.getString("nombreDose");
            	if(nombreDose==null || nombreDose.equals("")) nombreDose=nomDose;
            	dose.setNombreDose(nombreDose);
            	
              	//String idDose=resultSet.getString("idDoseRes"); if(idDose==null || idDose.equals("")) idDose=resultSet.getString("idDoseDef");
              	//String idDose=resultSet.getString("idDose"); if(idDose==null || idDose.equals("")) idDose=resultSet.getString("idDose");
            	
            	String idDose=resultSet.getString("idDose");
            	dose.setIdDose(idDose);
               	
            	//String hour=resultSet.getString("hourRes"); if(hour==null || hour.equals("")) hour=resultSet.getString("hourDef");
            	//String hour=resultSet.getString("hour"); if(hour==null || hour.equals("")) hour=resultSet.getString("hour");
            	
            	String hour=resultSet.getString("hour");
            	dose.setHour(hour);

            	String name=resultSet.getString("name");
            	if(name==null || name.equals("")) name=nomDose;
            	dose.setName(name);
            	
            	//String name=resultSet.getString("nameRes"); if(name==null || name.equals("")) name=resultSet.getString("nameDef");
               	//String name=resultSet.getString("name"); if(name==null || name.equals("")) name=resultSet.getString("name");
            	//dose.setName(name);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        finally {
 
        		//dose =  getDoseByFilters( "defecto",  resiNombreDose,  lang);
        		
        	if(dose.getCodeDose()==null)
        			generaDoseBasica(dose, numeroToma);

            con.close();
        }
		return dose;
    }
*/
	private static Dose generaDoseBasica(Dose dose, int numeroToma) {
		
		if(Pattern.matches("\\d{1,2}h", dose.getNombreDose()))  //Formato Xh o XXh
		{
			dose.setIdDose(dose.getNombreDose().replace("h", "") +"00");
			dose.setHour(dose.getNombreDose().replace("h", "") + ":00");
			dose.setName(dose.getNombreDose());
		}
		if(Pattern.matches("\\d{1,2}", dose.getNombreDose()))   //Formato X o XX
		{
			dose.setIdDose(dose.getNombreDose()+"00");
			dose.setHour(dose.getNombreDose()+":00");
			dose.setName(dose.getNombreDose()+"h");
		}
		if(Pattern.matches("\\d{2}:\\d{2}", dose.getNombreDose()))   //Formato X o XX
		{
			dose.setIdDose(dose.getNombreDose().replace(":", ""));
			dose.setHour(dose.getNombreDose());
			dose.setName(dose.getNombreDose()+"h");
		}
		
        return dose;
    }

	public static boolean validateFormat1(String input) {
        // Utilizar expresión regular para verificar el formato "XXh"
        String regex = "\\d{1,2}h";
        return Pattern.matches(regex, input);
    }
	
	
	public static Dose generaDoseNoPintar(String idDivisionResidencia, int lang) {

			Dose dose = new Dose();
			dose.setIdDivisionResidencia(idDivisionResidencia);
			dose.setIdDose("OTROS");
			dose.setHour("24:01");
			dose.setName("OTROS");
			dose.setLang(lang);
			
        return dose;
    }


    
    
    
}