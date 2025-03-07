package com.ProductManagement.product.ErrorHandle;



public class EmptyRequestBodyException extends RuntimeException {
    public EmptyRequestBodyException(String message) {
        super(message);
    }
}
