package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Set<String> VALID_STATUSES = Set.of(
            "SUCCESS", "REJECTED", "WAITING_PAYMENT", "CANCELLED"
    );

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        Payment payment = new Payment(UUID.randomUUID().toString(), method, order, paymentData);
        paymentRepository.save(payment);
        return payment;
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        if (!VALID_STATUSES.contains(status)) {
            throw new IllegalArgumentException("Invalid payment status: " + status);
        }
        payment.setStatus(status);
        updateOrderStatus(payment, status);
        paymentRepository.save(payment);
        return payment;
    }

    private void updateOrderStatus(Payment payment, String paymentStatus) {
        if ("SUCCESS".equals(paymentStatus)) {
            payment.getOrder().setStatus("SUCCESS");
        } else if ("REJECTED".equals(paymentStatus)) {
            payment.getOrder().setStatus("FAILED");
        }
    }

    @Override
    public Payment getPayment(String paymentId) {
        Payment payment = paymentRepository.findById(paymentId);
        if (payment == null) {
            throw new NoSuchElementException("Payment not found: " + paymentId);
        }
        return payment;
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}