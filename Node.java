package ID3;
import java.util.ArrayList;

public class Node
{
	String nodeName;
	String childAttributeName;  //For eg. 'wind', not 'wind:strong'
	ArrayList<Node> children = new ArrayList<Node>();
<<<<<<< HEAD
	String maxInformationGainAttribute_tempVar;  
	double informationGain;
=======
	String maxInformationGainAttribute_tempVar;
	String finalReutrnValue;
>>>>>>> 19f918c85dae075f17eaf0a850e38796c90bedd9
	ArrayList<ArrayList<String>> reducedDataSet = new ArrayList<ArrayList<String>>();
	
	public Node(String nodeName,ArrayList<ArrayList<String>> reducedDataSet, double informationGain)
	{
		this.nodeName = nodeName;
		this.informationGain = informationGain;
		this.reducedDataSet = reducedDataSet;
	}
<<<<<<< HEAD
	
=======

	ArrayList<ArrayList<String>> obtainReducedDataset(String attribute,int j)    // j is wrong, it should be tge index of attribute in hashmAp
	{
  	ArrayList<ArrayList<String>> temp= new ArrayList<ArrayList<String>>();
    for(int i=0;i<reducedDataSet.size();i++)
		{
			if(reducedDataSet.get(i).get(j).equals(attribute))  //might create some problem when similar name wit
			{
					temp.add(reducedDataSet.get(i));
			}
		}
		return temp;
	}

	boolean checkAllPositive(Node parent)
	{
  		for(int i=0;i<reducedDataSet.size();i++)
			{
				if(reducedDataSet.get(i).get(15).equals("0")) //here 15 is needed to be changed
				{
					return false;
				}
			}
			return true;
	}

	boolean checkAllNegative(Node parent)
	{
		for(i=0;reducedDataSet.size();i++)
		{
				if(reducedDataSet.get(i).get(15).equals("1"))
				{
					return false;
				}
		}
		return true;
	}

	String calculateMaxIgAttribute(Node parent)
	{
	  double maxIG=0.0;
	  String maxAttributeName;
	  for(ArrayList<String> attributeKey : attributeRangeHashMap.keySet())
	  {
	    double temp= calculateInformationGain(parent,attributeKey);
	    if(maxIG < temp)
	    {
	      maxIG=temp;
	      maxAttributeName=attributeKey;
	    }
	  }
	   return maxAttributeName;
	}

>>>>>>> 19f918c85dae075f17eaf0a850e38796c90bedd9
}
