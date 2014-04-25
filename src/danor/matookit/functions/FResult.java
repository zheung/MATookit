package danor.matookit.functions;

public class FResult 
{
	protected final Object object;
	protected final String error;
	
	public FResult(Object setObject, String setError)
	{
		object = setObject;
		error = setError;
	}
	public FResult(Object setObject)
	{
		object = setObject;
		error = null;
	}
}