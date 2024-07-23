package lopicost.spd.struts.bean;

import java.util.ArrayList;
import java.util.List;

import lopicost.spd.model.SustXComposicion;


public class SustXComposicionBean {

	 List<SustXComposicion> resultList = new ArrayList<SustXComposicion>();
	    public List<SustXComposicion> getResultList() {
	            return resultList;
	        }
	    public void setResultList(List<SustXComposicion> resultList) {
	            this.resultList = resultList;
	        }
}
