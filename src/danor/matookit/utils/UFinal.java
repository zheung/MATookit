package danor.matookit.utils;

public class UFinal<T>
{
	private T finalObject = null;
	
	private boolean finalAlready = false;
	
	public UFinal(T finalObject)
	{
		this.finalObject = finalObject;
		this.finalAlready = !this.finalAlready;
	}
	
	public UFinal()
	{
		
	}
	
	public void Burn(T finalObject) throws Exception
	{
		if(!finalAlready)
		{
			this.finalObject = finalObject;
			this.finalAlready = !this.finalAlready;
		}
		else
			throw new Exception("the Object already burned");
	}
	
	public T Gain()
	{
		return finalObject;
	}
}
