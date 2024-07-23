package lopicost.spd.struts.bean;

import java.util.ArrayList;
import java.util.List;

import lopicost.spd.model.GestSustituciones;

public class GestSustitucionesBean {

	 List<GestSustituciones> resultList = new ArrayList<GestSustituciones>();
	    public List<GestSustituciones> getResultList() {
	            return resultList;
	        }
	    public void setResultList(List<GestSustituciones> resultList) {
	            this.resultList = resultList;
	        }
}
