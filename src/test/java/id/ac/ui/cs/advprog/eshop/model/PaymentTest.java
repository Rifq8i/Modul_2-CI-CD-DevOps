package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    private Order order;
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        products.add(product);

        order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudrajat");

        paymentData = new HashMap<>();
    }

    @Test
    void testCreatePaymentSuccessful() {
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("pay-001", "VOUCHER_CODE", order, paymentData);

        assertEquals("pay-001", payment.getId());
        assertEquals("VOUCHER_CODE", payment.getMethod());
        assertEquals(order, payment.getOrder());
        assertEquals(paymentData, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentWithNullOrder() {
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        assertThrows(IllegalArgumentException.class,
                () -> new Payment("pay-001", "VOUCHER_CODE", null, paymentData));
    }

    @Test
    void testCreatePaymentWithEmptyMethod() {
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        assertThrows(IllegalArgumentException.class,
                () -> new Payment("pay-001", "", order, paymentData));
    }

    @Test
    void testSetStatusValid() {
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("pay-001", "VOUCHER_CODE", order, paymentData);
        payment.setStatus("SUCCESS");
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testSetStatusInvalid() {
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("pay-001", "VOUCHER_CODE", order, paymentData);
        assertThrows(IllegalArgumentException.class, () -> payment.setStatus("UNKNOWN_STATUS"));
    }

    @Test
    void testVoucherCodeValidBecomesSuccess() {
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("pay-001", "VOUCHER_CODE", order, paymentData);
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testVoucherCodeTooShortBecomesRejected() {
        paymentData.put("voucherCode", "ESHOP123ABC");
        Payment payment = new Payment("pay-001", "VOUCHER_CODE", order, paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testVoucherCodeNotStartingWithESHOPBecomesRejected() {
        paymentData.put("voucherCode", "WRONG1234ABC5678");
        Payment payment = new Payment("pay-001", "VOUCHER_CODE", order, paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testVoucherCodeFewerThan8DigitsBecomesRejected() {
        paymentData.put("voucherCode", "ESHOP123ABCDEFGH"); // only 3 digits
        Payment payment = new Payment("pay-001", "VOUCHER_CODE", order, paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testVoucherCodeNullBecomesRejected() {
        paymentData.put("voucherCode", null);
        Payment payment = new Payment("pay-001", "VOUCHER_CODE", order, paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testVoucherCodeEmptyBecomesRejected() {
        paymentData.put("voucherCode", "");
        Payment payment = new Payment("pay-001", "VOUCHER_CODE", order, paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCashOnDeliveryValidBecomesSuccess() {
        paymentData.put("address", "Jl. Kenangan No.1");
        paymentData.put("deliveryFee", "10000");
        Payment payment = new Payment("pay-002", "CASH_ON_DELIVERY", order, paymentData);
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCashOnDeliveryNullAddressBecomesRejected() {
        paymentData.put("address", null);
        paymentData.put("deliveryFee", "10000");
        Payment payment = new Payment("pay-002", "CASH_ON_DELIVERY", order, paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCashOnDeliveryEmptyAddressBecomesRejected() {
        paymentData.put("address", "");
        paymentData.put("deliveryFee", "10000");
        Payment payment = new Payment("pay-002", "CASH_ON_DELIVERY", order, paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCashOnDeliveryNullDeliveryFeeBecomesRejected() {
        paymentData.put("address", "Jl. Kenangan No.1");
        paymentData.put("deliveryFee", null);
        Payment payment = new Payment("pay-002", "CASH_ON_DELIVERY", order, paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCashOnDeliveryEmptyDeliveryFeeBecomesRejected() {
        paymentData.put("address", "Jl. Kenangan No.1");
        paymentData.put("deliveryFee", "");
        Payment payment = new Payment("pay-002", "CASH_ON_DELIVERY", order, paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testUnknownMethodDefaultsToWaitingPayment() {
        paymentData.put("someKey", "someValue");
        Payment payment = new Payment("pay-003", "OTHER_METHOD", order, paymentData);
        assertEquals("WAITING_PAYMENT", payment.getStatus());
    }
}