package org.danor.matookit;

public class FcResult 
{
	protected final Object object;
	protected final String error;
	
	public FcResult(Object setObject, String setError)
	{
		object = setObject;
		error = setError;
	}
	public FcResult(Object setObject)
	{
		object = setObject;
		error = null;
	}
}