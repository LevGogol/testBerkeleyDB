import java.io.Serializable;

/**
 * Created by Лев on 07.08.2017.
 */

public class MyData implements Serializable {
    private double price;
    private String name;
    private static final long serialVersionUID = 481188819320992415L;

    MyData() {
        price = 0.0;
        name = null;
    }

    public void setPrice(double data) {
        price = data;
    }

    public void setName(String data) {
        name = data;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}