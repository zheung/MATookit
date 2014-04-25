package danor.matookit.functions;

import java.io.File;
import java.util.List;

import danor.matookit.utils.UUtil;

public class FRank
{
	protected static void rank(FAction action, String idRank, int rank) throws Exception
	{
		List<String[]> lstRank = action.RankN("2085781", idRank);
		
//		int topRank = Integer.parseInt(lstRank.get(0)[2]);
//		
//		boolean flgFind = false;
		
		while(true)
		{
//			if(topRank > rank)
//				lstRank = action.RankP(lstRank.get(0)[0], idRank);
//			else if(rank - topRank < 15)
//			{
//				for(String[] r:lstRank)
//					if(String.valueOf(rank).equals(r[2]))
//						return r;
//					else;
//			}
//			else
//				lstRank = action.RankN(lstRank.get(lstRank.size()-1)[0], idRank);
		
			for(String[] r:lstRank)
			{
				UUtil.pp(r[0]+"|"+r[1]+"|"+r[2]+"|"+r[3]);
				UUtil.Output(new File("./cfg/rank"), (r[1]+"|"+r[2]+"|"+r[3]+"\r\n").getBytes(), true);
			}
			
			lstRank = action.RankN(lstRank.get(lstRank.size()-1)[0], idRank);
		}
		
	}
}
