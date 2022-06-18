package com.example.springboot.transaction.helperFunctions;

public class MissingFieldsException extends RuntimeException {

    // error thrown if ther is missing fields in user's submitted fields (when compare to SSOTs)
    public MissingFieldsException(String string) {
        super(string);
    }
    
}
