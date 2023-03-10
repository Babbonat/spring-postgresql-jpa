package it.tai.springpostresqljpa.springpostresqljpa.exceptions;

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

    public ResourceNotFoundException(String msg, String name)
    {
        super(String.format("msg: %s, entityName: %d", msg, name));
    }
}
