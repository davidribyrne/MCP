package mcp;


import space.dcce.commons.dns.exceptions.DnsClientConnectException;
import space.dcce.commons.netaddr.InvalidIPAddressFormatException;

public class Test
{



	public static void main(String[] args) throws DnsClientConnectException, InvalidIPAddressFormatException
	{

	}
	

	public static int bitsToMask(int bits)
	{
		return 0xFFFFFFFF << (32 - bits);
	}

}
