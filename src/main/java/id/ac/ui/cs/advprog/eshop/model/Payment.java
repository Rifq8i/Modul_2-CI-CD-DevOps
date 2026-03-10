package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;

import java.util.Map;
import java.util.Set;

@Getter
public class Payment {

    private static final String STATUS_SUCCESS = "SUCCESS";
    private static final String STATUS_REJECTED = "REJECTED";
    private static final String STATUS_WAITING = "WAITING_PAYMENT";
    private static final int VOUCHER_CODE_LENGTH = 16;
    private static final int VOUCHER_REQUIRED_DIGITS = 8;
    private static final String VOUCHER_PREFIX = "ESHOP";
    private static final Set<String> VALID_STATUSES = Set.of(
            STATUS_SUCCESS, STATUS_REJECTED, STATUS_WAITING, "CANCELLED"
    );

    private final String id;
    private final String method;
    private final Order order;
    private final Map<String, String> paymentData;
    private String status;

    public Payment(String id, String method, Order order, Map<String, String> paymentData) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        if (method == null || method.isEmpty()) {
            throw new IllegalArgumentException("Method cannot be empty");
        }
        this.id = id;
        this.method = method;
        this.order = order;
        this.paymentData = paymentData;
        this.status = determineInitialStatus(method, paymentData);
    }

    private String determineInitialStatus(String method, Map<String, String> data) {
        switch (method) {
            case "VOUCHER_CODE":
                return isValidVoucherCode(data) ? STATUS_SUCCESS : STATUS_REJECTED;
            case "CASH_ON_DELIVERY":
                return isValidCashOnDelivery(data) ? STATUS_SUCCESS : STATUS_REJECTED;
            default:
                return STATUS_WAITING;
        }
    }

    private boolean isValidVoucherCode(Map<String, String> data) {
        String code = data != null ? data.get("voucherCode") : null;
        if (code == null || code.isEmpty()) {
            return false;
        }
        if (code.length() != VOUCHER_CODE_LENGTH) {
            return false;
        }
        if (!code.startsWith(VOUCHER_PREFIX)) {
            return false;
        }
        long digitCount = code.chars().filter(Character::isDigit).count();
        return digitCount == VOUCHER_REQUIRED_DIGITS;
    }

    private boolean isValidCashOnDelivery(Map<String, String> data) {
        if (data == null) {
            return false;
        }
        String address = data.get("address");
        String deliveryFee = data.get("deliveryFee");
        return isNonEmpty(address) && isNonEmpty(deliveryFee);
    }

    private boolean isNonEmpty(String value) {
        return value != null && !value.isEmpty();
    }

    public void setStatus(String status) {
        if (!VALID_STATUSES.contains(status)) {
            throw new IllegalArgumentException("Invalid payment status: " + status);
        }
        this.status = status;
    }
}