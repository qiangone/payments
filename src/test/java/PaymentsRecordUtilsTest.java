import com.hsbc.PaymentsRecord;
import com.hsbc.PaymentsRecordUtils;
import org.junit.Assert;
import org.junit.Test;


public class PaymentsRecordUtilsTest {

    @Test
    public void test1(){

        PaymentsRecord pr = new PaymentsRecord("USD",88);

        PaymentsRecordUtils.addPayment(pr);

        int result = PaymentsRecordUtils.getAmountByCurrency("USD");

        Assert.assertEquals(result,88);

    }


    @Test
    public void test2(){
        PaymentsRecordUtils.loadPaymentsFromFile("/payments.txt");

        int result = PaymentsRecordUtils.getAmountByCurrency("CNY");

        Assert.assertEquals(result,33);
    }
}
