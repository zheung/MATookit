package danor.matookit.utils;

import java.util.Iterator;
import java.util.List;

public class UReverseEnum<T> implements Iterable<T>
{
	protected T[] elements;

	public UReverseEnum(T[] elements)
	{
		this.elements = elements;
	}

	@SuppressWarnings("unchecked")
	public UReverseEnum(List<T> list)
	{
		this.elements = (T[])list.toArray();
	}

	public Iterator<T> iterator()
	{
		return new Iterator<T>()
		{
			private int current = elements.length - 1;

			public boolean hasNext()
			{
				return current > -1;
			}

			public T next()
			{
				return elements[current--];
			}

		};
	}
}