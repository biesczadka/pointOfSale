package point.of.sale;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Martyna on 2016-01-10.
 */
public class SampleFormatter implements Formatter {


    DecimalFormat df = new DecimalFormat("#.00");

    public String productNameAndPrice(Product product){

        return product.getName()+" "+df.format(product.getPrice());
    }

    public  String receipt(List<Product> productList, BigDecimal sum){
        return productList.stream().map(p-> productNameAndPrice(p)).collect(Collectors.joining("\n"))+"\n"+df.format(sum);
    }
}
