package lopicost.spd.robot.model;

import javax.xml.bind.annotation.XmlAttribute;

public class Bottle {
    private String barcode;
    private int oneBottleQuantity;

    @XmlAttribute
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @XmlAttribute
    public int getOneBottleQuantity() {
        return oneBottleQuantity;
    }

    public void setOneBottleQuantity(int oneBottleQuantity) {
        this.oneBottleQuantity = oneBottleQuantity;
    }
}
