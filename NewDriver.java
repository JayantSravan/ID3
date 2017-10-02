//package ID3;
import java.util.*;
import java.io.*;
import java.util.ArrayList;

public class NewDriver
{
	static ArrayList<ArrayList<String>> dataSet = new ArrayList<ArrayList<String>>();
	static ArrayList<ArrayList<String>> testDataSet = new ArrayList<ArrayList<String>>();
	static LinkedHashMap<String, ArrayList<String>> attributeRangeHashMap = new LinkedHashMap<String, ArrayList<String>>();
	static FileWriter fw;
	static int c=0;
	public NewDriver(String rawDataSetFile, String outputDataSetFile,String classListFilePath,String testDataSetFile,String output_TEST_dataSetFile)
	{
		try
		{
			dataSet = new PreprocessData(rawDataSetFile, outputDataSetFile).createDataPointsList();
			testDataSet = new PreprocessData(testDataSetFile, output_TEST_dataSetFile).createDataPointsList();
			ResolveMissing_and_ContinuousValues R = new ResolveMissing_and_ContinuousValues(dataSet);
			ResolveMissing_and_ContinuousValues T = new ResolveMissing_and_ContinuousValues(testDataSet);
			dataSet = R.dataSet;
			testDataSet=T.dataSet;
			attributeRangeHashMap = R.attributeRangeHashMap;
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}

	public static void main(String[] args) {
		NewDriver N = new NewDriver("ID3_dataSet.txt","ID3PreprocessData.txt","classListID3.txt","ID3_TEST_dataSet.txt","Test_dataSet_output.txt");
		Node mainNode = new Node("S:Main",dataSet);
		mainNode.informationGain = calculateEntropy(dataSet, " ", "");
		//System.out.println(mainNode.informationGain);
		try{
		 fw = new FileWriter("vali.txt");}
		catch(Exception e){}
		ID3(mainNode);
		for(Node child : mainNode.children)
		{
			//System.out.println(child.nodeName);
		}
		try{fw.close();}catch(Exception e){}

		//System.out.println(Find_accuracy(mainNode));

		System.out.println("****************************");
    //RandomForest randomForest=new RandomForest();
		//randomForest.makeForest();
		RandomForest(mainNode);
		System.out.println("----****************************-----");
	}


	public static double calculateEntropy(ArrayList<ArrayList<String>> reducedDataSet, String attribute,String attributeValue)
	{
		double entropy;
		int positive_examples=0;
		int negative_examples = 0;
		int attributeIndex=0;
		if(attribute.equals(" "))
		{
			for(ArrayList<String> dataRow : reducedDataSet)
			{
				if(dataRow.get(14).equals("1")) positive_examples++;
				else negative_examples++;
			}
		}

		else{
		for(String keyAttribute : attributeRangeHashMap.keySet())
		{
			if(keyAttribute.equals(attribute)) break;
			attributeIndex++;
		}
		/*
		for(ArrayList<String> dataRow : reducedDataSet)
		{
			for(String str : dataRow)
			{
				System.out.print(str + " ");
			}
			System.out.println(attributeValue);
		}
		*/

		for(ArrayList<String> dataRow : reducedDataSet)
		{
			if(dataRow.get(attributeIndex).equals(attributeValue))
			{
				if(dataRow.get(dataRow.size()-1).equals("1")) positive_examples++;
				else negative_examples++;
			}
		}}
		if(positive_examples==0 && negative_examples==0) return 0.0;

		double p_positive = positive_examples/(double)(positive_examples + negative_examples);
		double p_negative = negative_examples/(double)(positive_examples+negative_examples);
		if(positive_examples==0 && negative_examples==0) return 0.0;
		double x1 = 0.0,x2 = 0.0;
		if(p_positive==0.0) x1 = 0.0;
		else x1 = Math.log(p_positive);
		if(p_negative==0.0) x2=0.0;
		else x2 = Math.log(p_negative);

		entropy = (-(p_positive*x1)-(x2*p_negative))/Math.log(2);
		return entropy;
	}

	public static double calculateInformationGain(Node parent,String attribute)
	{
		ArrayList<ArrayList<String>> reducedDataSet = parent.reducedDataSet;
		double attributeInformationGain = 0.0;
		int numOfRows_attributePresent = 0;
		int attributeIndex = new ArrayList<String>(attributeRangeHashMap.keySet()).indexOf(attribute);
		ArrayList<String> attributeRange = attributeRangeHashMap.get(attribute);
 		for(String attributeValue : attributeRange)
		{
			numOfRows_attributePresent = 0;
			for(ArrayList<String> dataRow : reducedDataSet)
			{
				if(dataRow.get(attributeIndex).equals(attributeValue))
					numOfRows_attributePresent++;
			}
			attributeInformationGain = attributeInformationGain + ((double)numOfRows_attributePresent/reducedDataSet.size())*calculateEntropy(reducedDataSet, attribute, attributeValue);
		}

		return parent.informationGain - attributeInformationGain;
	}

	public static ArrayList<ArrayList<String>> obtainReducedDataset(Node parent,String attributeValue,int attributeIndex)    // j is wrong, it should be the index of attribute in hashmAp
	{
		ArrayList<ArrayList<String>> temp= new ArrayList<ArrayList<String>>();
		for(int i=0;i<parent.reducedDataSet.size();i++)
		{
			if(parent.reducedDataSet.get(i).get(attributeIndex).equals(attributeValue))  //might create some problem when similar name wit
			{
					temp.add(parent.reducedDataSet.get(i));
			}
		}
		return temp;
	}

	public boolean checkAllPositive(Node parent)
	{
  		for(int i=0;i<parent.reducedDataSet.size();i++)
			{
				if(parent.reducedDataSet.get(i).get(14).equals("0")) //here 15 is needed to be changed
				{
					return false;
				}
			}
			return true;
	}

	public boolean checkAllNegative(Node parent)
	{
		for(int i=0;i<parent.reducedDataSet.size();i++)
		{
				if(parent.reducedDataSet.get(i).get(14).equals("1")) //15 hardcode
				{
					return false;
				}
		}
		return true;
	}

	public static String calculateMaxIgAttribute(Node parent)
	{
	  double maxIG=0.0;
	  String maxAttributeName = "aa";
	  for(String attributeKey : attributeRangeHashMap.keySet())
	  {
	    double temp= calculateInformationGain(parent,attributeKey);
	    //System.out.println(temp + " " + attributeKey);
	    if(temp==1.0){ maxAttributeName = attributeKey;  return attributeKey;}
	    else if(temp!=parent.informationGain && maxIG <= temp)
	    {
	    	//System.out.println("max1" + attributeKey);
	      maxIG=temp;
	      maxAttributeName=attributeKey;
	    }

	    /*
	    if( maxIG <= temp)
	    {
	      maxIG=temp;
	      maxAttributeName=attributeKey;
	    }
	    */
	  }
	 // System.out.println(maxAttributeName + "maxi");
	   return maxAttributeName;
	}

	public static  void ID3(Node parent)
	{
	// System.out.println(parent.nodeName);
	try{ fw.write(parent.nodeName + "\n");}catch(Exception e){}


			if(parent.reducedDataSet.isEmpty())
			{
				//parent.children.add(new Node(true));
				int index =  new ArrayList<String>(attributeRangeHashMap.keySet()).indexOf(parent.nodeName.split(":")[0]);
				Boolean b = findMostCommon(parent.reducedDataSet, index,parent.nodeName.split(":")[1] );
			parent.children.add(new Node(b));
			try{ fw.write("1" + true + "\n");}catch(Exception e){}
			c++;
			}
			else if(parent.allYes)
			{
				try{ fw.write("2" + true + "\n");}catch(Exception e){}
				//System.out.println(parent.nodeName);
			parent.children.add(new Node(true));
			}
			else if(parent.allNo)
			{
				try{ fw.write("3" + false + "\n");}catch(Exception e){}
				//System.out.println(parent.nodeName);
				parent.children.add(new Node(false));
			}

			else
			{
				String maxAttributeName=calculateMaxIgAttribute(parent);
				int attributeIndex = new ArrayList<String>(attributeRangeHashMap.keySet()).indexOf(maxAttributeName);   //make a separate array list for keys
				ArrayList<String> attributeVariety=attributeRangeHashMap.get(maxAttributeName);
				if(maxAttributeName.compareTo(parent.nodeName.split(":")[0])==0)
				{
					int index =  new ArrayList<String>(attributeRangeHashMap.keySet()).indexOf(parent.nodeName.split(":")[0]);
					Boolean b = findMostCommon(parent.reducedDataSet, index,parent.nodeName.split(":")[1] );
					try{ fw.write("x" + "\n");}catch(Exception e){}
					parent.children.add(new Node(b));
				}
				else{
				for(int j=0;j<attributeVariety.size();j++)
				{
						Node child=new Node(maxAttributeName + ":" + attributeVariety.get(j),obtainReducedDataset(parent,attributeVariety.get(j),attributeIndex));
						parent.children.add(child);
						child.informationGain = calculateEntropy(parent.reducedDataSet, maxAttributeName, attributeVariety.get(j));
						ID3(child);
				}}
			}
	}

		public static double Find_accuracy(Node parent)
	  {
			//System.out.println("*********");
	      ArrayList<String> temp;                     //just a temporary ArrayList
	      double count=0.0;                           // obvious
	      for(int i=0;i<testDataSet.size();i++)       // iterating over testData
	      {
	          temp=dataSet.get(i);
						boolean returnedValueFromTree=checkPositiveCaseForTestData(temp,parent);
	          if((returnedValueFromTree && temp.get(temp.size()-1).equals("1"))||((!returnedValueFromTree) && temp.get(temp.size()-1).equals("0")))   //checking whether data is positive or not
	           {
	            count++;
	          }
	      }
	      double accuracy=(double)count/testDataSet.size();
	      return accuracy;
	  }
	 public static boolean checkPositiveCaseForTestData(ArrayList<String> tempString,Node parent)   //returns true or false depending
	  {
	    if(parent.leafBit==1)
	    {
	      return parent.leafValue;
	    }
			if(parent.children.get(0).leafBit==1) return parent.children.get(0).leafValue;
	    //System.out.println(parent.nodeName + parent.children.size() + parent.leafBit) ;
	    //System.out.println(parent.children);
	    String[] words=parent.children.get(0).nodeName.split(":");  // for fetching out attributetype eg: wind from wind:high

	    int attributeindextemp=new ArrayList<String>(attributeRangeHashMap.keySet()).indexOf(words[0]); // calculating indexof element in hashmap so as to pick attributename (eg; high) from tempstring
	    String attributeName=tempString.get(attributeindextemp); //getting the attributename from tempstring list
	    String completeName = words[0] + ":"+ attributeName; // making the nodename for finding node compared in children list
	    for(int i=0;i<parent.children.size();i++)   //just interating to find that node with the name completename
	    {
	      if(parent.children.get(i).nodeName.equals(completeName))
	      {
	        return checkPositiveCaseForTestData(tempString,parent.children.get(i)); //obvious
	        //break;
	      }
	    }

		return false;
	  }

		public static Boolean findMostCommon(ArrayList<ArrayList<String>> reducedDataSet, int attributeIndex, String attributeValue)
 		{
 			int i=0,j=0;
 			for(ArrayList<String> dataRow : reducedDataSet)
 			{
 				if(dataRow.get(attributeIndex).equals(attributeValue))
 				{
 					if(dataRow.get(14).equals("1")) i++;
 					else if (dataRow.get(14).equals("0")) j++;
 				}
 			}
 			if(i>j) return true;
 			else return false;
 	}


	///////////////////////////////////////for Pruning ///////////////////////////////////////////////////////
	public static pruneTree(Node root) //driver method for pruning.
	{
		/*
		1. Iterate over the tree multiple times. Stop when pruning can no longer increase the accuracy.
		2. In each iteration, make that node a leaf and check accuracy.
		3. Each time, mark the node with the maximum accuracy and prune from that node.
		4. Continue this till no node increases the accuracy of the tree.
		 */
		boolean iterateCondition = true;
		double accuracy = findAccuracy(root);
		while(iterateCondition)
		{
			Node nodeToBePruned = findBestLeafNode(root); //////gets the node which on pruning gives best accuracy
			nodeToBePruned.leafBit=1;   //make the best one a leaf
			double maxAccuracy = findAccuracy(root);
			if(maxAccuracy > accuracy) ///checking if pruning is any better
			{
				accuracy = maxAccuracy;
			}
			else
			{
				iterateCondition = false; //to break out in the next iteration
				nodeToBePruned.leafBit = 0; //revert back the changes made
			}
		}
	}
	static Node best = root;
	static double maxAccuracy;
	public static Node findBestLeafNode( Node root )
	{
		maxAccuracy = 0; //maximum accuracy that can be achieved after pruning the present tree
		DFS(root); //traverse the entire tree to know the best node to be used
		return  best;
	}

	public static void DFS (Node node)
	{
		if(node.leafBit == 1) //return if this node itself is a leaf
		{
			return 0;
		}

		//*****************************************
		//check if this node gives the best accuracy
		node.leafBit = 1; //make it a leaf
		node.leafValue = findMostCommonOutput( node ); //check the majority output of the node
		double accuracy = findAccuracy(root); //get the accuracy if this node is made a leaf
		if(accuracy>maxAccuracy)
		{
			best = node; //make this the best node
			maxAccuracy = accuracy;//make this the best accuracy
		}
		node.leafBit = 0; //make the node an internal node again
		//*****************************************


		for(Node child : node.children) //repeat the process for its children also
		{
			DFS(child);
		}
	}

	public static boolean findMostCommonOutput( Node node)  //Computes if the dataset in a node says YES or NO if it were a leaf
	{
		int i=0;   //counters
		int j=0;
		for(ArrayList<String> datapoint : node.reducedDataSet)  //traverse through and count
		{
			String output = datapoint.get(lastIndexOf(datapoint));
			if(output.equals("1"))
			{
				i++;
			}
			else
			{
				j++;
			}
		}
		if(i>=j)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	//////////////////////////////////////end - for pruning////////////////////////////////////////////////////




	/************************************************************/
	//Functions for random Functions
	static ArrayList<String> attributeList;
	static ArrayList<String> randomAttributeList;
	static ArrayList<ArrayList<String>> randomDataSet;
	static int numberOfAttributes;
	static int flag=0;


	public static void RandomForest(Node parent)
	{	attributeList=new ArrayList<String>(attributeRangeHashMap.keySet());
		randomAttributeList=new ArrayList<String>();
		randomDataSet=new ArrayList<ArrayList<String>>();

		/*for(int i=0;i<4;i++)
		{
			randomNumber = (int)(Math.random() * (attributeList.size()));
			randomAttributeList.add(attributeList.get(randomNumber));
			attributeList.remove(randomNumber);
		}*/
		for(int i=0;i<dataSet.size();i++)
		{
			randomDataSet.add(dataSet.get((int)(Math.random() * (dataSet.size()))));
		}
		parent.reducedDataSet=randomDataSet;
		ID3Random(parent);
}
	public static String calculateMaxIgAttributeRandom(Node parent)
	{
		double maxIG=0.0;
		String maxAttributeName = "aa";
		Node maxIgNode;
		randomListGenerator();

		/*if(numberOfAttributes==1)
		{
			i++;
		}*/

		if(flag==0)
		{	for(int i=0; i <randomAttributeList.size();i++)
			{
				/*System.out.println("-------------------------");
				System.out.println(randomAttributeList.get(i));
				System.out.println("-------------------------");*/

				double temp= calculateInformationGain(parent,randomAttributeList.get(i));
				//System.out.println(temp + " " + attributeKey);
				String attributeKey=randomAttributeList.get(i);
				if(temp==1.0){ maxAttributeName = attributeKey;  return attributeKey;}
				else if(temp!=parent.informationGain && maxIG <= temp)
				{
					//System.out.println("max1" + attributeKey);
					maxIG=temp;
					maxAttributeName=attributeKey;
					// stores a reference to node of highest Ig so as to be removed from attributeList
				}

				//again randomly generates a list
			}
		  	attributeList.remove(maxAttributeName);//removing the node from the attributeList



		}
		return maxAttributeName;
	}
	public static  void ID3Random(Node parent)
	{
	// System.out.println(parent.nodeName);
	try{ fw.write(parent.nodeName + "\n");}catch(Exception e){}


			if(parent.reducedDataSet.isEmpty())
			{
				//parent.children.add(new Node(true));
				int index =  new ArrayList<String>(attributeRangeHashMap.keySet()).indexOf(parent.nodeName.split(":")[0]);
				Boolean b = findMostCommon(parent.reducedDataSet, index,parent.nodeName.split(":")[1] );
			parent.children.add(new Node(b));
			try{ fw.write("1" + true + "\n");}catch(Exception e){}
			c++;
			}
			else if(parent.allYes)
			{
				try{ fw.write("2" + true + "\n");}catch(Exception e){}
				System.out.println(parent.nodeName);
			parent.children.add(new Node(true));
			}
			else if(parent.allNo)
			{
				try{ fw.write("3" + false + "\n");}catch(Exception e){}
				System.out.println(parent.nodeName);
				parent.children.add(new Node(false));
			}

			else
			{
				String maxAttributeName=calculateMaxIgAttributeRandom(parent);
				int attributeIndex = new ArrayList<String>(attributeRangeHashMap.keySet()).indexOf(maxAttributeName);   //make a separate array list for keys
				ArrayList<String> attributeVariety=attributeRangeHashMap.get(maxAttributeName);
				if(maxAttributeName.compareTo(parent.nodeName.split(":")[0])==0)
				{
					int index =  new ArrayList<String>(attributeRangeHashMap.keySet()).indexOf(parent.nodeName.split(":")[0]);
					Boolean b = findMostCommon(parent.reducedDataSet, index,parent.nodeName.split(":")[1] );
					try{ fw.write("x" + "\n");}catch(Exception e){}
					parent.children.add(new Node(b));
				}
				else{
				for(int j=0;j<attributeVariety.size() && (flag==0);j++)
				{
					  System.out.println("j "+j);
						Node child=new Node(maxAttributeName + ":" + attributeVariety.get(j),obtainReducedDataset(parent,attributeVariety.get(j),attributeIndex));
						parent.children.add(child);
						child.informationGain = calculateEntropy(parent.reducedDataSet, maxAttributeName, attributeVariety.get(j));
						ID3Random(child);
				}}
			}
	}

	public static void randomListGenerator()
	{
		if(!(randomAttributeList.isEmpty()))
		{
			randomAttributeList.clear();
		}

		numberOfAttributes = (int)(Math.sqrt(attributeList.size())) +1;
		System.out.println("numberOfAttributes : "+numberOfAttributes);
		/*if(numberOfAttributes==1)
		{
			flag=1;
		}*/

		if(flag==0)
		{	for(int i=0;i<numberOfAttributes;i++)
			{

				int randomNumber = (int)(Math.random() * (attributeList.size()));
				System.out.println(" random number: "+randomNumber);
				randomAttributeList.add(attributeList.get(randomNumber));
				//attributeList.remove(randomNumber);
			}
			if(numberOfAttributes==1)
			{
				flag=1;
			}
			//attributeList.clear();
			System.gc();
			//attributeList=new ArrayList<String>(attributeRangeHashMap.keySet());
	}
}
}
