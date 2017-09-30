package ID3;
import java.util.ArrayList;

public class Node
{
	String nodeName;
	ArrayList<Node> children = new ArrayList<Node>();
	double informationGain;
	String finalReutrnValue;
	int leafBit=0;
	Boolean leafValue;
	ArrayList<ArrayList<String>> reducedDataSet = new ArrayList<ArrayList<String>>();
	
	boolean allYes = true;
	boolean allNo = true;
	
	public Node(String nodeName,ArrayList<ArrayList<String>> reducedDataSet)
	{
		this.nodeName = nodeName;		
		this.reducedDataSet = reducedDataSet;
		int i=0,j=0;
		for(ArrayList<String> dataRow : reducedDataSet)
		{
			if(dataRow.get(14).equals("0")) i++;		
			else if(dataRow.get(14).equals("1")) j++;	
			
			if(i>=1 && j>=1) 
			{
				allNo = false;
				allYes = false;
				break;
			}
		}
		if(i==reducedDataSet.size()) allYes = false;	
		if(j == reducedDataSet.size()) allNo = false;	
	}
	public Node(Boolean leafValue)
	{
		leafBit = 1;
		this.leafValue = leafValue; 
	}
	


}
