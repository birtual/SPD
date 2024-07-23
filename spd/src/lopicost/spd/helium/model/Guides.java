package lopicost.spd.helium.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "guides")
public class Guides {
	@XmlElement(name = "guide")
    private List<Guide> guideList = new ArrayList<Guide>();



    public void setGuideList(List<Guide> guides) {
        this.guideList = guides;
    }

	public Guides() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void add(Guide guide) {
		guideList.add(guide);
		
	}

	public List<Guide> getList() {
		return guideList;
	}
    
}