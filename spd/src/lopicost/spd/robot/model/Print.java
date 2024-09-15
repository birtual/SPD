package lopicost.spd.robot.model;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"id", "text"})
public class Print {
    private int id;
    private String text;

    @XmlAttribute
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlAttribute
    public String getText() {
        return text;
    }

    public void setText(String text) {
    	if(text!=null && text.equals("999"))
    		text="***";
        this.text = " "+ text;
    }
}