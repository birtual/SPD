package spd_test;


import lopicost.config.pool.dbaccess.Conexion;
import lopicost.spd.struts.bean.FicheroResiBean;

 
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PruebasPaso1XML {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
     
            List<FicheroResiBean> resultados = new ArrayList<>();
            Connection con = Conexion.conectar();
            ResultSet rs = null;
            PreparedStatement pstat = null;
            String idProceso="feixa_llarga_20240710_20240716_454726";
            String idDivisionResidencia="general_feixa_llarga";
           // int oidDivisionResidencia=cab.getOidDivisionResidencia();		
            try {
               // Obtener la correspondencia de posiciones
                String ordenQuery = "SELECT posicionEnBBDD, posicionEnVistas, nombreToma  FROM dbo.SPD_cabecerasXLS WHERE idDivisionResidencia = '"+idDivisionResidencia+"' ORDER BY posicionEnVistas ";
                pstat = con.prepareStatement(ordenQuery);
                rs = pstat.executeQuery();

                List<Integer> posiciones = new ArrayList<>();
                List<String> nombresTomas = new ArrayList<>();
                while (rs.next()) {
                    posiciones.add(rs.getInt("posicionEnBBDD"));
                    nombresTomas.add(rs.getString("nombreToma"));
                }

                rs.close();
                pstat.close();

                // Construir la consulta dinámica
                StringBuilder query = new StringBuilder(" SELECT ISNULL(p.planta, '''') AS planta, d.resiCip as CIP, ISNULL(p.habitacion, '''') AS habitacion, RIGHT(d.spdCnFinal, 6) as CN  ");
                query.append(" , CASE d.spdAccionBolsa WHEN ''PASTILLERO'' then ''S'' ELSE ''N'' END dispensar, d.spdNombreBolsa, d.spdAccionBolsa "  );
               // query.append(" , d.fechaDesde, d.fechaHasta, d.resiD1, d.resiD2, d.resiD3, d.resiD4, d.resiD5, d.resiD6, d.resiD7  "  );
                query.append(" , d.resiD1, d.resiD2, d.resiD3, d.resiD4, d.resiD5, d.resiD6, d.resiD7  "  );
                query.append(" , d.resiInicioTratamiento, d.resiFinTratamiento,  d.resiInicioTratamientoParaSPD, d.resiFinTratamientoParaSPD, d.resiPeriodo "  );
                for (int i = 0; i < posiciones.size(); i++) {
                    query.append(", d.resiToma").append(posiciones.get(i)).append(" AS [").append(nombresTomas.get(i)).append("]");
                }
                query.append(" FROM SPD_ficheroResiDetalle d"  );
                query.append(" LEFT JOIN dbo.bd_pacientes p ON p.CIP=d.resiCIP "  );
                query.append(" WHERE d.idDivisionResidencia = '"+idDivisionResidencia+"'  ");
                query.append(" AND idProceso = '"+idProceso+"'  ");
                //query.append(" AND oidFicheroResiCabecera = '"+cab.getOidFicheroResiCabecera()+"'  ");
                query.append(" AND spdAccionBolsa in ('SOLO_INFO', 'PASTILLERO') ");
                query.append(" AND ISNUMERIC(RIGHT(spdCnFinal, 6))=1 ");
                //query.append(" AND resiCIP='SOFI1570624009' ");
                query.append(" ORDER BY resiCip, spdNombreBolsa ");
                
                pstat = con.prepareStatement(ordenQuery);
                rs = pstat.executeQuery();

                
            // Recorrer el resultado de la consulta y agregar elementos al XML
            while (rs.next()) {
            	
            }


            // Cerrar la conexión a la base de datos
            rs.close();
            pstat.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
   

    }
    
}
