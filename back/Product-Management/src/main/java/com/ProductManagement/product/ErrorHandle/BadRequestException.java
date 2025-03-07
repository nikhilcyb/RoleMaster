package com.ProductManagement.product.ErrorHandle;



public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}