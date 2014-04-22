package org.danor.matookit;

public class DcPoint
{
	protected int nowAP;//header>you_data>ap(bc)>current
	protected int maxAP;//header>you_data>ap(bc)>max
	protected int revAP;//header>you_data>ap(bc)>current_time - header>you_data>ap>last_update_time
	
	protected int nowBC;//header>you_data>ap(bc)>current
	protected int maxBC;//header>you_data>ap(bc)>max
	protected int revBC;//header>you_data>ap(bc)>current_time - header>you_data>ap>last_update_time
	
	protected McPoint timer;
}