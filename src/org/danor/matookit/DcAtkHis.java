package org.danor.matookit;

public class DcAtkHis
{
	protected String uid;
	protected String name;//user_name
	protected String atkhp;//attack_point
	protected String atkcount;//attack_times

	protected void Print()
	{
		System.out.print(uid);
		System.out.print(name);
		System.out.print(atkhp);
		System.out.print(atkcount);
	}
}
