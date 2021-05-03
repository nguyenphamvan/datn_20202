package com.nguyenpham.oganicshop.service.impl;

import com.nguyenpham.oganicshop.config.PaypalPaymentIntent;
import com.nguyenpham.oganicshop.config.PaypalPaymentMethod;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaypalService {

    @Autowired
    private APIContext apiContext;

    public Payment createPayment(Double total, String currency, PaypalPaymentMethod method, PaypalPaymentIntent intent,
                                 String description, String cancelUrl, String successUrl) throws PayPalRESTException {

        Payer payer = getPayerInformation(method);
        RedirectUrls redirectUrls = getRedirectURLs(cancelUrl, successUrl);
        List<Transaction> transactions = getTransactionInformation(currency, total, description);

        Payment payment = new Payment();
        payment.setIntent(intent.toString());
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        payment.setRedirectUrls(redirectUrls);

        apiContext.setMaskRequestId(true);
        return payment.create(apiContext);
    }

    private Payer getPayerInformation(PaypalPaymentMethod method) {
        Payer payer = new Payer();
        payer.setPaymentMethod(method.toString());
        return payer;
    }

    private RedirectUrls getRedirectURLs(String CancelUrl, String setReturnUrl) {
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(CancelUrl);
        redirectUrls.setReturnUrl(setReturnUrl);
        return redirectUrls;
    }

    private List<Transaction> getTransactionInformation(String currency, Double total, String description) {
        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.format("%.2f", total));

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        return transactions;
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecute);
    }
}
