package lopicost.spd.helium.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "lines")
public class Lines {
	@XmlElement(name = "line")
    private List<Line> lineList = new ArrayList<Line>();



    public void setLineList(List<Line> lines) {
        this.lineList = lines;
    }

	public Lines() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void add(Line line) {
		lineList.add(line);
		
	}


	public List<Line> getList() {
		return lineList;
	}
}