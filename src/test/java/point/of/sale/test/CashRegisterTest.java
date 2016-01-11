package point.of.sale.test;

import org.junit.Test;
import point.of.sale.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CashRegisterTest {

    private BarCodesScanner barCodesScannerMock = mock(BarCodesScanner.class);
    private LcdDisplayer lcdDisplayerMock = mock(LcdDisplayer.class);
    private Printer printerMock = mock(Printer.class);
    private ProductRepository productRepositoryMock = mock(ProductRepository.class);
    private SampleFormatter sampleFormatter = new SampleFormatter();

    private CashRegister cashRegister = new CashRegister(barCodesScannerMock, lcdDisplayerMock, printerMock, productRepositoryMock, sampleFormatter);

    private final String xxxCode = "XXX";
    private final String yyyCode = "YYY";

    private final Product xxxProduct = new Product(xxxCode, "XXXProduct", new BigDecimal(25.00));
    private final Product yyyProduct = new Product(yyyCode, "YYYProduct", new BigDecimal(30.00));
    List<Product> productList = new ArrayList<Product>() {{
        add(xxxProduct);
        add(yyyProduct);
    }};

    @Test
    public void shouldDisplayNameAndPrice() {

        //given
        given(barCodesScannerMock.getCode()).willReturn(xxxCode);
        given(productRepositoryMock.find(xxxCode)).willReturn(xxxProduct);

        //when
        cashRegister.scan();

        //then
        verify(lcdDisplayerMock).display(sampleFormatter.productNameAndPrice(xxxProduct));

    }

    @Test
    public void shouldDisplayProductNotFound() {

        //given
        given(barCodesScannerMock.getCode()).willReturn(xxxCode);
        given(productRepositoryMock.find(xxxCode)).willReturn(null);

        //when
        cashRegister.scan();

        //then
        verify(lcdDisplayerMock).display(CashRegister.productNotFoundMessage);

    }

    @Test
    public void shouldDisplayEmptyCodeMessage() {

        //given
        given(barCodesScannerMock.getCode()).willReturn(null);

        //when
        cashRegister.scan();

        //then
        verify(lcdDisplayerMock).display(CashRegister.emptyCodeMessage);

    }    @Test
    public void shouldReceipt() {

        //given
        given(barCodesScannerMock.getCode()).willReturn(xxxCode, yyyCode);
        given(productRepositoryMock.find(xxxCode)).willReturn(xxxProduct);
        given(productRepositoryMock.find(yyyCode)).willReturn(yyyProduct);


        //when
        cashRegister.scan();
        cashRegister.scan();
        cashRegister.exit();

        //then
        verify(printerMock).print(sampleFormatter.receipt(productList, sum() ));

    }

    private BigDecimal sum(){
        return productList.stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
