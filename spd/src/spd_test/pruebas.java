package spd_test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class pruebas {


		public static void main(String[] args) throws Exception {
			
/*	      	LocalDate currentDate = LocalDate.now();
	        LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String formattedDate = currentDate.format(formatter);
            System.out.println(formattedDate);
            System.out.println(formattedDate+new Date().getTime());
            System.out.println(formattedDate+new Date().getTime()+now.getNano());
     */
	        // Obt�n la fecha y hora actual
	        LocalDateTime now = LocalDateTime.now();
	        // Formatea la fecha y los milisegundos a yyyyMMddHHmmssSSS
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
	        String formattedDateTime = now.format(formatter);
	        // Genera un n�mero aleatorio de 18 d�gitos
	        Random random = new Random();
	        long randomNumber = 100000000000000000L + (long)(random.nextDouble() * 900000000000000000L);
	        // Convierte el n�mero aleatorio a una cadena con 14 d�gitos
	        String randomDigits = String.format("%018d", randomNumber);
	        // Concatena la fecha formateada con los 18 d�gitos aleatorios
	        String result = formattedDateTime + randomDigits;
            System.out.println(result);
            
            String cn ="1234567";
        	if(cn!=null && cn.length()>6)
        		cn = cn.substring(0, 6);
        	System.out.println(cn);
				
		}
		
	
	    
	}


    