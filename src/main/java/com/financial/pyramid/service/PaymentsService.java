package com.financial.pyramid.service;

/**
 * User: dbudunov
 * Date: 07.08.13
 * Time: 22:44
 */
public interface PaymentsService {

    public void processPayment(String payee, String memo, Double amount);

}
