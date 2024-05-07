package com.izertis.SimpleShared.core.domain.exception;

public class NegativeExpenseAmountException extends Exception{
    public NegativeExpenseAmountException(String message) {
        super(message);
    }
}
