package com.artist.top5.exceptions;

public class RecordNotFoundException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public RecordNotFoundException(String exception) {
        super(exception);
    }
}