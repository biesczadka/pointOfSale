package point.of.sale;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Martyna on 2016-01-10.
 */
public interface Formatter {
    String productNameAndPrice(Product product);

    String receipt(List<Product> productList, BigDecimal sum);
}
