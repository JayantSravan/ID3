package ID3;
import java.util.ArrayList;

public class Node
{
	String nodeName;
	String childAttributeName;  //like 'wind', not 'wind:strong'
	ArrayList<Node> children = new ArrayList<Node>();
	String maxInformationGainAttribute_tempVar;
	ArrayList<ArrayList<String>> reducedDataSet = new ArrayList<ArrayList<String>>();

	public Node(String nodeName,ArrayList<ArrayList<String>> reducedDataSet)
	{
		this.nodeName = nodeName;
		this.reducedDataSet = reducedDataSet;
	}

}
