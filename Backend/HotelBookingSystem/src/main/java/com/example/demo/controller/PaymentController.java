package com.example.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.Payment;
import com.example.demo.service.PaymentServiceImplementation;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/payments")
public class PaymentController {

    private PaymentServiceImplementation paymentSer;

    @Autowired
    public PaymentController(PaymentServiceImplementation paymentSer) {
        this.paymentSer = paymentSer;
    }

    @GetMapping("/list")
    public List<Payment> getAllPaymentsData() {
        List<Payment> payments = paymentSer.displayAll();
        return payments;
    }

    @GetMapping("/list/{paymentId}")
    public Payment getPaymentsData(@PathVariable("paymentId") int paymentId) {
        Payment payment = paymentSer.getById(paymentId);
        if (payment == null) {
            throw new RuntimeException("Payment not found with the given payment id");
        }
        return payment;
    }

    @PostMapping("/list")
    public Payment insertPayment(@RequestBody Payment payment) {
        // Set paymentId to 0 to ensure it's treated as a new payment
        payment.setPaymentId(0);
        paymentSer.insertOrUpdate(payment);
        return payment; // Return the newly created payment
    }

    @PutMapping("/list/{paymentId}")
    public Payment updatePayment(@PathVariable("paymentId") int paymentId, @RequestBody Payment payment) {
        // Ensure the payment ID in the request matches the URL
        if (payment.getPaymentId() != paymentId) {
            throw new RuntimeException("Payment ID in the request does not match the URL");
        }
        paymentSer.insertOrUpdate(payment);
        return payment; // Return the updated payment
    }

    @DeleteMapping("/list/{paymentId}")
    public String deletePaymentsData(@PathVariable("paymentId") int paymentId) {
        Payment payment = paymentSer.getById(paymentId);
        if (payment == null) {
            throw new RuntimeException("Payment not found with the given id");
        }
        paymentSer.removeById(paymentId);
        return "Deleted payment id = " + paymentId;
    }
}
