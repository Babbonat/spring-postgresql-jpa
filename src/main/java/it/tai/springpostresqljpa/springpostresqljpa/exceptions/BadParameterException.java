package it.tai.springpostresqljpa.springpostresqljpa.exceptions;

public class BadParameterException extends RuntimeException
{
    public BadParameterException(String field)
    {
        super(String.format("invalid parameter %s", field));
    }
}
