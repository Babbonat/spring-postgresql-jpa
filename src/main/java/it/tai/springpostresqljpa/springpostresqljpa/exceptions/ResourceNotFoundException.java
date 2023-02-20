package it.tai.springpostresqljpa.springpostresqljpa.exceptions;

import lombok.Data;

public class ResourceNotFoundException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String msg)
    {
        super(msg);
    }

    public ResourceNotFoundException(String msg, long id)
    {
        super(String.format("msg: %s, entityId: %d", msg, id));
    }
}
