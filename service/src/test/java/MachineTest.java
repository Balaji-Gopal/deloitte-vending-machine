import com.deloitte.base.DropBox;
import com.deloitte.base.enm.Coin;
import com.deloitte.base.enm.Product;
import com.deloitte.base.exception.IncompletePaymentException;
import com.deloitte.base.exception.NoChangeException;
import com.deloitte.base.exception.OutOfStockException;
import com.deloitte.base.intrfc.Machine;
import com.deloitte.service.MachineImpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class MachineTest {
    //private static Machine machine;
    private Machine machine= new MachineImpl();

    @BeforeClass
    public static void setUp(){
        //machine = Factory.newMachine();
    }

    @AfterClass
    public static void destroy(){
        //machine = null;
    }

    @Test
    public void getProductAndRemainingPrice_ChocolateFor25Coins_ChocolateAnd10CoinBalance(){
        long price = machine.getPrice(Product.CHOCOLATE);
        assertEquals(Product.CHOCOLATE.getPrice(),price);
        machine.putCoin(Coin.QUARTER);
        DropBox<Product, List<Coin>> dropBox = machine.getProductAndRemainingPrice();
        Product product = dropBox.getItem();
        List<Coin> coins = dropBox.getCoins();
        assertEquals(Product.CHOCOLATE,product);
        assertEquals(coins.get(0),Coin.DIME);

    }

    @Test(expected = IncompletePaymentException.class)
    public void getProductAndRemainingPrice_CoolDrinkFor10Coins_IncompletePayment(){
        long price = machine.getPrice(Product.COOL_DRINK);
        machine.putCoin(Coin.CENT);
        DropBox<Product,List<Coin>> dropBox = machine.getProductAndRemainingPrice();
    }

    @Test(expected = OutOfStockException.class)
    public void getPrice_ForCoolDrink_OutOfStock(){
       Machine testMachine = new MachineImpl();
       machine.reset();
       machine.getPrice(Product.COOL_DRINK);
    }

    @Test(expected = NoChangeException.class)
    public void getProductAndRemainingPrice_CoolDrinkChocolateWithExtraMoney_NoChange(){
        IntStream.range(1,10).forEach((int i) ->{
            machine.getPrice(Product.CANDY);
            machine.putCoin(Coin.QUARTER);
            machine.putCoin(Coin.QUARTER);
            machine.putCoin(Coin.QUARTER);
            machine.getProductAndRemainingPrice();
            machine.getPrice(Product.CHOCOLATE);
            machine.putCoin(Coin.QUARTER);
            machine.putCoin(Coin.QUARTER);
            machine.putCoin(Coin.QUARTER);
            machine.getProductAndRemainingPrice();
        });
    }

    @Test
    public void getRefund_BuyProductWithMoreCoin_balance(){
        long price = machine.getPrice(Product.CHOCOLATE);
        machine.putCoin(Coin.QUARTER);
        machine.putCoin(Coin.DIME);
        assertEquals(Arrays.asList(new Coin[]{Coin.QUARTER,Coin.DIME}),machine.cancel());
    }

    @Test
    public void getProductAndRemainingPrice_CoolDrinkFor50Coins_CoolDrinkAnd25CoinBalance(){
        long price = machine.getPrice(Product.COOL_DRINK);
        assertEquals(Product.COOL_DRINK.getPrice(),price);
        machine.putCoin(Coin.HALF_DOLLAR);
        DropBox<Product, List<Coin>> dropBox = machine.getProductAndRemainingPrice();
        Product product = dropBox.getItem();
        List<Coin> coins = dropBox.getCoins();
        assertEquals(Product.COOL_DRINK,product);
        assertEquals(coins.get(0),Coin.QUARTER);
    }

}
