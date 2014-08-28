package danor.matookit.functions;

import java.io.File;

public enum FServer
{
	JP1("JPS"), KR1("KRS"), TW1("TWS"),
	CN1("CNS"), CN2("CNS"), CN3("CNS"),
	KRK("KRS"), SG1("SGS"), MY1("MYS");
	
	public static final File dirGuiAll = new File("./wrk/all/gui");
	public static final File dirLogAll = new File("./wrk/all/log");
	public static final File dirDatAll = new File("./wrk/all/dat");
	private final String server;
	private final File dirPak;
	private final File dirDat;
	private final File dirExp;
	private final File dirRes;
	private final File fileArb;
	
	FServer(String name)
	{
		this.server = name;
		dirPak = new File("./wrk/"+name.toLowerCase()+"/pak/");
		dirDat = new File("./wrk/"+name.toLowerCase()+"/dat/");
		dirExp = new File("./wrk/"+name.toLowerCase()+"/dat/exp");
		dirRes = new File("./wrk/"+name.toLowerCase()+"/res/");
		fileArb = new File("./wrk/"+name.toLowerCase()+"/dat/arb.xml");
	}

	public String server() { return server; }
	public String res() { return server.substring(0, 2)+"R"; }
	public File dirPak() { return dirPak; }
	public File dirDat() { return dirDat; }
	public File dirPrg() { return dirExp; }
	public File dirRes() { return dirRes; }
	public File fileArb() { return fileArb; }
	
	public boolean isCN()
	{
		return (this == CN1)||(this == CN2)||(this == CN3);
	}

}
