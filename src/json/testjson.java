package json;

import java.util.ArrayList;
import java.util.List;

public class testjson {
	
	//default values
	public String test1 = "my penis is 7 inches";
	public List<Integer> list;
	public double dinosaur;
	
	//parameters are changes to the defaults
	public testjson( String paramTest, List<Integer> paramList, double paramDino ) {
		
		if ( paramTest != null ) {
			this.test1 = paramTest;
		}
		this.list = paramList;
		this.dinosaur = paramDino;
		
	}
	
	public testjson( String paramTest, double paramDino ) {
		
		if ( paramTest != null ) {
			this.test1 = paramTest;
		}
		this.list = new ArrayList<Integer>();
		list.add(4);
		this.dinosaur = paramDino;
		
	}

}
