package point.of.sale;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martyna on 2016-01-10.
 */
public class CashRegister {

    private BarCodesScanner barCodesScanner;
    private LcdDisplayer lcdDisplayer;
    private Printer printer;
    private ProductRepository productRepository;
    private Formatter formatter;

    public final static String emptyCodeMessage = "Invalid bar-code";
    public final static String productNotFoundMessage = "Product not found";

    private List<Product> productList = new ArrayList<Product>();


    public CashRegister(BarCodesScanner barCodesScanner, LcdDisplayer lcdDisplayer, Printer printer, ProductRepository productRepository, Formatter formatter) {
        this.barCodesScanner = barCodesScanner;
        this.lcdDisplayer = lcdDisplayer;
        this.printer = printer;
        this.productRepository = productRepository;
        this.formatter = formatter;
    }

    public void scan(){
        String code = barCodesScanner.getCode();
        if(code != null) sale(code);
        else  lcdDisplayer.display(emptyCodeMessage);
    }

    public void sale(String code){
        Product product = productRepository.find(code);
        if(product != null) {
            lcdDisplayer.display(formatter.productNameAndPrice(product));
            productList.add(product);
        }
        else lcdDisplayer.display(productNotFoundMessage);
    }

    public void exit(){
        printer.print(formatter.receipt(productList, sum()));
    }

    private BigDecimal sum(){
        return productList.stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
