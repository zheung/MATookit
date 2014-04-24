package danor.matookit.natures.data.card;

public class NDataCardFull extends NDataCard
{
	private final String idCard;
	private final String idTown;
	private final String name;
	private final String desc;
	private final String illustrator;
	private final String cost;
	private final String star;
	private final String salePrice;
	private final String basHP;
	private final String basAK;
	private final String maxLV;
	private final String idImageNorrmal;
	private final String idImageArousal;
	private final String idForm;
	private final String sex;
	private final String version;
	private final String maxHP;
	private final String maxAK;
	private final String hloMaxLV;
	private final String cpdTar;
	private final String cpdResult;
	private final String cpdPrices;

	private final String lmtHP;
	private final String lmtAK;
	private final String lmtLV;
	private final String hloLmtLV;
	private final String hloEX;
	private final String isSync;
	
	private final NDataCardGrow grow;
	private final NDataCardSkill skill;
	
	public NDataCardFull(NDataCardTemp temp)
	{
		this.idCard = temp.idCard;
		this.idTown = temp.idTown;
		this.name = temp.name;
		this.desc = temp.desc;
		this.illustrator = temp.illustrator;
		this.cost = temp.cost;
		this.star = temp.star;
		this.salePrice = temp.salePrice;
		this.basHP = temp.basHP;
		this.basAK = temp.basAK;
		this.maxLV = temp.maxLV;
		this.idImageNorrmal = temp.idImageNorrmal;
		this.idImageArousal = temp.idImageArousal;
		this.idForm = temp.idForm;
		this.sex = temp.sex;
		this.version = temp.version;
		this.maxHP = temp.maxHP;
		this.maxAK = temp.maxAK;
		this.hloMaxLV = temp.hloMaxLV;
		this.cpdTar = temp.cpdTar;
		this.cpdResult = temp.cpdResult;
		this.cpdPrices = temp.cpdPrices;
		
		this.lmtHP = temp.lmtHP;
		this.lmtAK = temp.lmtAK;
		this.lmtLV = temp.lmtLV;
		this.hloLmtLV = temp.hloLmtLV;
		this.hloEX = temp.hloEX;
		this.isSync = temp.isSync;
		
		this.grow = temp.grow;
		this.skill = temp.skill;
	}
	
	public synchronized String idCard() { return idCard; }
	public synchronized String idTown() { return idTown; }
	public synchronized String name() { return name; }
	public synchronized String desc() { return desc; }
	public synchronized String illustrator() { return illustrator; }
	public synchronized String cost() { return cost; }
	public synchronized String star() { return star; }
	public synchronized String salePrice() { return salePrice; }
	public synchronized String basHP() { return basHP; }
	public synchronized String basAK() { return basAK; }
	public synchronized String maxLV() { return maxLV; }
	public synchronized String idImageNorrmal() { return idImageNorrmal; }
	public synchronized String idImageArousal() { return idImageArousal; }
	public synchronized String idForm() { return idForm; }
	public synchronized String sex() { return sex; }
	public synchronized String version() { return version; }
	public synchronized String maxHP() { return maxHP; }
	public synchronized String maxAK() { return maxAK; }
	public synchronized String hloMaxLV() { return hloMaxLV; }
	public synchronized String cpdTar() { return cpdTar; }
	public synchronized String cpdResult() { return cpdResult; }
	public synchronized String cpdPrices() { return cpdPrices; }

	public synchronized String lmtHP() { return lmtHP; }
	public synchronized String lmtAK() { return lmtAK; }
	public synchronized String lmtLV() { return lmtLV; }
	public synchronized String hloLmtLV() { return hloLmtLV; }
	public synchronized String hloEX() { return hloEX; }
	public synchronized String isSync() { return isSync; }

	public synchronized NDataCardGrow grow() { return grow; }
	public synchronized NDataCardSkill skill() { return skill; }
}
