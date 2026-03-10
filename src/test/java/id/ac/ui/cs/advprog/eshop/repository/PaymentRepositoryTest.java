package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    private PaymentRepository paymentRepository;
    private Order order;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();

        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        products.add(product);

        order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudrajat");
    }

    private Payment createVoucherPayment(String id) {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP1234ABC5678");
        return new Payment(id, "VOUCHER_CODE", order, data);
    }

    @Test
    void testSaveNewPayment() {
        Payment payment = createVoucherPayment("pay-001");
        Payment saved = paymentRepository.save(payment);

        assertNotNull(saved);
        assertEquals("pay-001", saved.getId());
    }

    @Test
    void testSaveUpdatesExistingPayment() {
        Payment payment = createVoucherPayment("pay-001");
        paymentRepository.save(payment);

        Map<String, String> newData = new HashMap<>();
        newData.put("voucherCode", "ESHOP9999XYZ1234");
        Payment updated = new Payment("pay-001", "VOUCHER_CODE", order, newData);
        paymentRepository.save(updated);

        Payment found = paymentRepository.findById("pay-001");
        assertEquals("ESHOP9999XYZ1234", found.getPaymentData().get("voucherCode"));
    }

    @Test
    void testFindByIdFound() {
        Payment payment = createVoucherPayment("pay-001");
        paymentRepository.save(payment);

        Payment found = paymentRepository.findById("pay-001");
        assertNotNull(found);
        assertEquals("pay-001", found.getId());
    }

    @Test
    void testFindByIdNotFound() {
        Payment found = paymentRepository.findById("nonexistent-id");
        assertNull(found);
    }

    @Test
    void testFindAllPayments() {
        paymentRepository.save(createVoucherPayment("pay-001"));
        paymentRepository.save(createVoucherPayment("pay-002"));

        List<Payment> all = paymentRepository.findAll();
        assertEquals(2, all.size());
    }

    @Test
    void testFindAllEmpty() {
        List<Payment> all = paymentRepository.findAll();
        assertTrue(all.isEmpty());
    }
}