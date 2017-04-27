package mcp.commons;

import java.io.Serializable;

public class HttpTransaction implements Serializable
{
	private static final long serialVersionUID = 1L;
	private byte[] request;
	private byte[] response;

	public HttpTransaction()
	{
	}


	public HttpTransaction(byte[] request, byte[] response) 
	{
		super();
		this.request = request;
		this.response = response;
	}

	public byte[] getRequest()
	{
		return request;
	}
	public void setRequest(byte[] request)
	{
		this.request = request;
	}
	public byte[] getResponse()
	{
		return response;
	}
	public void setResponse(byte[] response)
	{
		this.response = response;
	}

}
