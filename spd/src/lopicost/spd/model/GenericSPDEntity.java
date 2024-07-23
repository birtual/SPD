package lopicost.spd.model;

import java.lang.reflect.Field;

public class GenericSPDEntity {
 
 
  public GenericSPDEntity(){}
   
  public boolean hasScope(){
   Field[] fields = getClass().getDeclaredFields();
   boolean trobat=false;

   for (int x = 0; x < fields.length; x++) {
    	if (fields[x].getName().equals("scope")){
    		trobat=true;
    	}
   }

   return trobat;
 }
}