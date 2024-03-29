package de.htw.ai.kbe.exceptions;

import javax.ws.rs.core.Response;

public class ResponseException
{
	public static final String INVALID_ID = "The id is invalid.";
	public static final String INVALID_PAYLOAD = "The payload is invalid.";
	public static final String RESOURCE_ID_NOT_PAYLOAD_ID = "The resource id does not match the payload id.";
	public static final String RESOURCE_NOT_FOUND = "The resource could not be found.";
	
	public static Response build(int status, String exception)
	{
		return Response.status(status).entity(exception).build();
	}
}
