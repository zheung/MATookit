package danor.matookit.natures;

public class NShop
{
	/* Login: header>you_data
	 * mc = cp|Million Cost
	 */
	private String mc;//header>you_data>cp
	
	public synchronized String mc() { return mc; }
	public synchronized void mc(String mc) { this.mc = mc; }
}
