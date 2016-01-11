package point.of.sale;

import java.math.BigDecimal;

/**
 * Created by Martyna on 2016-01-10.
 */
public class Product {
    private String exampleCode;
    private String name;
    private BigDecimal price;

    public Product(String exampleCode, String name, BigDecimal price) {
        this.exampleCode = exampleCode;
        this.name = name;
        this.price = price;
    }


    public BigDecimal getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}
