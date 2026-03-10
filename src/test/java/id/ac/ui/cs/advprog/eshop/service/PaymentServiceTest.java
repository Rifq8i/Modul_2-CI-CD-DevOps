package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Order order;

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
    }

    @Test
    void testAddPaymentSavesToRepository() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP1234ABC5678");

        Payment mockPayment = new Payment("pay-001", "VOUCHER_CODE", order, data);
        when(paymentRepository.save(any(Payment.class))).thenReturn(mockPayment);

        Payment result = paymentService.addPayment(order, "VOUCHER_CODE", data);

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertNotNull(result);
    }

    @Test
    void testAddPaymentVoucherValidReturnsSuccess() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP1234ABC5678");

        when(paymentRepository.save(any(Payment.class))).thenAnswer(inv -> inv.getArgument(0));

        Payment result = paymentService.addPayment(order, "VOUCHER_CODE", data);

        assertEquals("SUCCESS", result.getStatus());
    }

    @Test
    void testAddPaymentVoucherInvalidReturnsRejected() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "INVALID");

        when(paymentRepository.save(any(Payment.class))).thenAnswer(inv -> inv.getArgument(0));

        Payment result = paymentService.addPayment(order, "VOUCHER_CODE", data);

        assertEquals("REJECTED", result.getStatus());
    }

    @Test
    void testAddPaymentCashOnDeliveryValidReturnsSuccess() {
        Map<String, String> data = new HashMap<>();
        data.put("address", "Jl. Merdeka No.17");
        data.put("deliveryFee", "15000");

        when(paymentRepository.save(any(Payment.class))).thenAnswer(inv -> inv.getArgument(0));

        Payment result = paymentService.addPayment(order, "CASH_ON_DELIVERY", data);

        assertEquals("SUCCESS", result.getStatus());
    }

    @Test
    void testAddPaymentCashOnDeliveryMissingAddressReturnsRejected() {
        Map<String, String> data = new HashMap<>();
        data.put("address", "");
        data.put("deliveryFee", "15000");

        when(paymentRepository.save(any(Payment.class))).thenAnswer(inv -> inv.getArgument(0));

        Payment result = paymentService.addPayment(order, "CASH_ON_DELIVERY", data);

        assertEquals("REJECTED", result.getStatus());
    }

    @Test
    void testSetStatusSuccessAlsoSetsOrderSuccess() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("pay-001", "VOUCHER_CODE", order, data);

        when(paymentRepository.save(any(Payment.class))).thenAnswer(inv -> inv.getArgument(0));

        Payment result = paymentService.setStatus(payment, "SUCCESS");

        assertEquals("SUCCESS", result.getStatus());
        assertEquals("SUCCESS", result.getOrder().getStatus());
    }

    @Test
    void testSetStatusRejectedAlsoSetsOrderFailed() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("pay-001", "VOUCHER_CODE", order, data);

        when(paymentRepository.save(any(Payment.class))).thenAnswer(inv -> inv.getArgument(0));

        Payment result = paymentService.setStatus(payment, "REJECTED");

        assertEquals("REJECTED", result.getStatus());
        assertEquals("FAILED", result.getOrder().getStatus());
    }

    @Test
    void testSetStatusInvalidThrowsException() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("pay-001", "VOUCHER_CODE", order, data);

        assertThrows(IllegalArgumentException.class,
                () -> paymentService.setStatus(payment, "INVALID_STATUS"));
    }

    @Test
    void testGetPaymentFound() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("pay-001", "VOUCHER_CODE", order, data);

        when(paymentRepository.findById("pay-001")).thenReturn(payment);

        Payment result = paymentService.getPayment("pay-001");

        assertNotNull(result);
        assertEquals("pay-001", result.getId());
    }

    @Test
    void testGetPaymentNotFoundThrowsException() {
        when(paymentRepository.findById("nonexistent")).thenReturn(null);

        assertThrows(NoSuchElementException.class,
                () -> paymentService.getPayment("nonexistent"));
    }

    @Test
    void testGetAllPayments() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP1234ABC5678");
        List<Payment> payments = List.of(
                new Payment("pay-001", "VOUCHER_CODE", order, data),
                new Payment("pay-002", "VOUCHER_CODE", order, data)
        );

        when(paymentRepository.findAll()).thenReturn(payments);

        List<Payment> result = paymentService.getAllPayments();

        assertEquals(2, result.size());
    }

    @Test
    void testGetAllPaymentsEmpty() {
        when(paymentRepository.findAll()).thenReturn(new ArrayList<>());

        List<Payment> result = paymentService.getAllPayments();

        assertTrue(result.isEmpty());
    }
}