package ID3;
import java.util.ArrayList;

public class Node
{
	String nodeName;
	String childAttributeName;  //For eg. 'wind', not 'wind:strong'
	ArrayList<Node> children = new ArrayList<Node>();
	String maxInformationGainAttribute_tempVar;  
	double informationGain;
	ArrayList<ArrayList<String>> reducedDataSet = new ArrayList<ArrayList<String>>();
	
	public Node(String nodeName,ArrayList<ArrayList<String>> reducedDataSet, double informationGain)
	{
		this.nodeName = nodeName;
		this.informationGain = informationGain;
		this.reducedDataSet = reducedDataSet;
	}
	
}
