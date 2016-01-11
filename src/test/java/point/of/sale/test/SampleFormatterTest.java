package point.of.sale.test;

import org.junit.Test;
import point.of.sale.Product;
import point.of.sale.SampleFormatter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * Created by Martyna on 2016-01-11.
 */
public class SampleFormatterTest {

    private SampleFormatter sampleFormatter = new SampleFormatter();

    private final String xxxCode = "XXX";
    private final String yyyCode = "YYY";

    private final Product xxxProduct = new Product(xxxCode, "XXXProduct", new BigDecimal(25.50));
    private final Product yyyProduct = new Product(yyyCode, "YYYProduct", new BigDecimal(30.50));
    List<Product> productList = new ArrayList<Product>() {{
        add(xxxProduct);
        add(yyyProduct);
    }};

    @Test
    public void shouldformatNameAndPrice() {

        //when
        String nameAndPrice = sampleFormatter.productNameAndPrice(xxxProduct);

        //then
        assertEquals(nameAndPrice, "XXXProduct 25,50");

    }

    @Test
    public void shouldformatProductList() {

        //when
        String receipt = sampleFormatter.receipt(productList, new BigDecimal(56.00));

        //then
        assertEquals(receipt, "XXXProduct 25,50\nYYYProduct 30,50\n56,00");

    }
}
