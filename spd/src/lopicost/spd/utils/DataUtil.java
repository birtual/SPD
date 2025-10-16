package lopicost.spd.utils;

import lopicost.spd.commons.struts.SPDClientMessageResourcesFactory;

import org.apache.struts.util.MessageResources;

/**
 * Utilitat per accedir als literals de struts.
 * Suporta multiidioma i literals especifics.
 */
public class DataUtil {
	
	private static final DataUtil instance = new DataUtil();
	
	private MessageResources resources;

	protected DataUtil() {
		super();
		SPDClientMessageResourcesFactory factory = new SPDClientMessageResourcesFactory();
		resources = factory.createResources(SPDConstants.MESSAGE_RESOURCE_PROPERTIES);
	}
	
	/**
	 * Devuelve la instancia de esta clase asociada al locale del usuario
	 */
	public static DataUtil instance()
	{
		return instance;
	}
    /**
     * Mètode que valida si el String és un numèric o si és buit (null o "")
     * Creat pels casos en que s'accepta un string buit, que desprès serà substituit per un 0
     * @param string
     * @return cert si l'string és un número o és null o és igual a ""
     * 		   fals la resta de casos
     */
    public boolean isNumeroOrEmpty(String string) {
    	
    	if (string == null || string.equals("") || isNumero(string) )
    		return true;
    	else
    		return false;
    }
    
    public static boolean isNumeroGreatherThanZero(String string) {
    	boolean result=false;
    	if (string != null)
    	{
    		if (isNumero(string)&& !string.equals("0") )
    			return true;
    	}

    		return result;
		
    }
    
    
    //Funció que retorna true si es un numero y false si no ho és
    public static boolean isNumero(String nom) {
    	if(nom==null || nom.isEmpty()) return false;
     	boolean esNumeric = true;
    	try {
    		Double.parseDouble(nom.replace(',', '.'));
    		//new Long(nom);
    	} catch (NumberFormatException e) {
    		esNumeric = false;
    	}
    	
    	return esNumeric;
   
}    

    /**
     * Método que devuelve un código nacional de 7 dígitos a partir de uno de 6
     * A - CODIGO6	707044
     * B - CODIGO7	=SI(LARGO(A2)=7;C;C&I2)
     * D1 -	dígito1	7
     * D2 -	dígito2	0
     * D3 -	dígito3	7
     * D4 -	dígito4	0
     * D5 -	dígito	4
     * D6 -	dígito	4
     * A -	Algoritmo			=3*D2+3*D4+3*D6+D1+D32+D5+27
     * E -	Entero				=ENTERO(A2/10)
     * R -	Residuo				=RESIDUO(A2;10)
     * B -	base				=SI(R2>0;E2+1;E2)
     * DS -	Decena siguiente	=B2*10
     * RS -	Resultado			=DS2-A2
     * @param cn6
     * @return cn7
     */
	public static int getCN7(int cn6) {
    	
     	int cn7 =-1;
      	String number = String.valueOf(cn6);
     	char[] digits1 = number.toCharArray();
    	try {
         	int dig1		=	Character.getNumericValue(digits1[0]);	//C
    		int dig2		=	Character.getNumericValue(digits1[1]);	//D
    		int dig3		=	Character.getNumericValue(digits1[2]);	//E
    		int dig4		=	Character.getNumericValue(digits1[3]);	//F
    		int dig5		=	Character.getNumericValue(digits1[4]);	//G
    		int dig6		=	Character.getNumericValue(digits1[5]);	//H
  
    		int algoritmo	=	3*dig2 + 3*dig4 + 3*dig6 + dig1 + dig3 + dig5 + 27;	 //J		=3*D2+3*F2+3*H2+C2+E2+G2+27
            int entero = algoritmo/10; 
            int residuo = algoritmo - entero*10;
            int base =entero;
            if(residuo>0) base+=1;
            int decenaSiguiente=base*10;
            int resultado	=	decenaSiguiente-algoritmo;	//I		=N2-J2
            
            cn7=cn6*10+resultado;
    		System.out.println("algoritmo " + algoritmo);
            System.out.println("entero " + entero);
            System.out.println("residuo " + residuo);
            System.out.println("base " + base);
            System.out.println("decenaSiguiente " + decenaSiguiente);
            System.out.println("resultado " + resultado);
            System.out.println("cn7 " + cn7);
     

    	} catch (NumberFormatException e) {
    		cn7 = -1;
    	}
    	
    	return cn7;
   
}    
    

}
