package edu.thejunglegiant.store;

import edu.thejunglegiant.store.controller.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ControllersTest {

    @Autowired
    private AuthController authController;

    @Autowired
    private CartController cartController;

    @Autowired
    private GoodsController goodsController;

    @Autowired
    private OrdersController ordersController;

    @Autowired
    private ProfileController profileController;

    @Test
    public void testAuth() throws Exception {
        assertNotNull(authController);
    }

    @Test
    public void testCart() throws Exception {
        assertNotNull(cartController);
    }

    @Test
    public void testGoods() throws Exception {
        assertNotNull(goodsController);
    }

    @Test
    public void testOrders() throws Exception {
        assertNotNull(ordersController);
    }

    @Test
    public void testProfile() throws Exception {
        assertNotNull(profileController);
    }
}
