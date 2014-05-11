package danor.matookit.functions;

import java.io.File;

public enum FServer
{
	CN1("CNS"), CN2("CNS"), CN3("CNS"),
	SG1("SGS"), TW1("TWS"), JP1("JPS"),
	KR1("KRS"), KRK("KRS"), MY1("MYS");
	
	public static final File dirGuiAll = new File("./wrk/res/gui");
	public static final File dirLogAll = new File("./wrk/log");
	public static final File dirDatAll = new File("./wrk/dat");
	private final String server;
	private final File dirPak;
	private final File dirDat;
	private final File dirExp;
	private final File dirRes;
	
	FServer(String name)
	{
		this.server = name;
		dirPak = new File("./wrk/pak/"+name.toLowerCase());
		dirDat = new File("./wrk/dat/"+name.toLowerCase());
		dirExp = new File("./wrk/dat/"+name.toLowerCase()+"/exp");
		dirRes = new File("./wrk/res/"+name.toLowerCase());
	}

	public String server() { return server; }
	public String res() { return server.substring(0, 1)+"R"; }
	public File dirPak() { return dirPak; }
	public File dirDat() { return dirDat; }
	public File dirPrg() { return dirExp; }
	public File dirRes() { return dirRes; }
	
	public boolean isCN()
	{
		return (this == CN1)||(this == CN2)||(this == CN3);
	}

}
