//package ID3;
import java.util.*;
import java.io.*;
import java.util.ArrayList;

public class NewDriver
{
	static ArrayList<ArrayList<String>> dataSet = new ArrayList<ArrayList<String>>();
	static ArrayList<ArrayList<String>> testDataSet = new ArrayList<ArrayList<String>>();
	static LinkedHashMap<String, ArrayList<String>> attributeRangeHashMap = new LinkedHashMap<String, ArrayList<String>>();
  static ArrayList<Node> randomMainNode; //arrallist for storing nodes of random decision trees
	static int numberOfTrees=200;
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
	public static Node root;
	public static void main(String[] args) {
		NewDriver N = new NewDriver("ID3_dataSet.txt","ID3PreprocessData.txt","classListID3.txt","ID3_TEST_dataSet.txt","Test_dataSet_output.txt");
		Node mainNode = new Node("S:Main",dataSet);
		root = mainNode;
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

		System.out.println(findAccuracy(mainNode));

		System.out.println("****************************");
    //RandomForest randomForest=new RandomForest();
		//randomForest.makeForest();
		//Node mainNodeRandom;
		randomMainNode=new ArrayList<Node>();
		//mainNodeRandom
			for(int i=0;i<numberOfTrees;i++)
			{	Node mainNodeRandom = new Node("S:Main",dataSet);
				mainNodeRandom.informationGain = calculateEntropy(dataSet, " ", "");
				RandomForest(mainNodeRandom);
				randomMainNode.add(mainNodeRandom);
			}
			System.out.println(findAccuracyOfRandomForest(randomMainNode));
		//pruneTree(mainNode);
		//System.out.println(findAccuracy(mainNodeRandom));
		System.out.println(" ----****************************----- ");
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

	public static double findAccuracy(Node parent)
	{

		//System.out.println("*********");
			ArrayList<String> temp;                     //just a temporary ArrayList
			int count=0;                           // obvious
			for(int i=0;i<testDataSet.size();i++)       // iterating over testData
			{
					temp=testDataSet.get(i);
					/*
					if(checkPositiveCaseForTestData(temp,parent) )   //checking whether data is positive or not
					 {
						System.out.println("value : " + temp.get(14));
						count++;
					}
					*/

					if((checkPositiveCaseForTestData(temp,parent) && temp.get(14).equals("1")) || (!checkPositiveCaseForTestData(temp,parent) && temp.get(14).equals("0")))
				{
						count++;
				}
			}
			//System.out.println(testDataSet.size());
			double accuracy=(double)count/testDataSet.size();
			//System.out.println("num " + c);
			return accuracy;
	}

	public static boolean checkPositiveCaseForTestData(ArrayList<String> tempString,Node parent)   //returns true or false depending
	{
		/*if(parent.leafBit==1)
		{
			return parent.leafValue;
		}*/
		//System.out.println(parent.children.get(0).nodeName);
		if(!(parent.children.isEmpty())&& parent.children.get(0).leafBit==1) return parent.children.get(0).leafValue;
    //System.out.println(parent.children.get(0).leafBit);
	 // System.out.println(parent.nodeName + parent.children.size() + parent.leafBit) ;
		//System.out.println(parent.nodeName + parent.children.get(0).leafValue);
		String word="aa";
		if(!parent.children.isEmpty())
		{
			String[] words=parent.children.get(0).nodeName.split(":");  // for fetching out attributetype eg: wind from wind:high
			word=words[0];

		int attributeindextemp=new ArrayList<String>(attributeRangeHashMap.keySet()).indexOf(word); // calculating indexof element in hashmap so as to pick attributename (eg; high) from tempstring
		String attributeName=tempString.get(attributeindextemp); //getting the attributename from tempstring list
		String completeName = word + ":"+ attributeName; // making the nodename for finding node compared in children list

		for(int i=0;i<parent.children.size();i++)   //just interating to find that node with the name completename
		{
			if(parent.children.get(i).nodeName.equals(completeName))
			{
			//System.out.println(parent.nodeName + parent.children.size() + tempString);
				return checkPositiveCaseForTestData(tempString,parent.children.get(i)); //obvious
				//break;
			}
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
	public static void pruneTree(Node root) //driver method for pruning.
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
			int count =0;
			System.out.println("Entered function");
			Node nodeToBePruned = findBestLeafNode(root); //////gets the node which on pruning gives best accuracy
			count++;
			System.out.println("Got one - " + count);
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
	static Node best;
	static double maxAccuracy;
	public static Node findBestLeafNode( Node root )
	{
		maxAccuracy = 0; //maximum accuracy that can be achieved after pruning the present tree
		System.out.println("Start one DFS");
		DFS(root); //traverse the entire tree to know the best node to be used
		System.out.println("Traversed once");
		return  best;
	}

	public static void DFS (Node node)
	{
		if(node.leafBit == 1) //return if this node itself is a leaf
		{
			//System.out.println("Found a leaf");
			return;
		}
		//System.out.println("Not a leaf");
		//*****************************************
		//check if this node gives the best accuracy
		node.leafBit = 1; //make it a leaf
		//node.leafValue = findMostCommonOutput( node ); //check the majority output of the node
		double accuracy = findAccuracy(root); //get the accuracy if this node is made a leaf

		if(accuracy>maxAccuracy)
		{
			best = node; //make this the best node
			maxAccuracy = accuracy;//make this the best accuracy
		}
		node.leafBit = 0; //make the node an internal node again
		//*****************************************

		//System.out.println("Passinf to children");
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
			int lastIndex = datapoint.size()-1;
			String output = datapoint.get( lastIndex );

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

	public static void assignleafValueToNodes(Node node)
	{
		node.leafValue = findMostCommonOutput(node);

		if(node.leafBit!=0)
		{
			return;
		}
		for(Node child : node.children)
		{
			assignleafValueToNodes(child);
		}

	}
	//////////////////////////////////////end - for pruning////////////////////////////////////////////////////




	/************************************************************/
	//Functions for random Functions
	//Functions for random Functions
		static ArrayList<String> attributeList;
		static ArrayList<String> randomAttributeList;
		static ArrayList<ArrayList<String>> randomDataSet;
		static int numberOfAttributes;



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
			parent.informationGain=calculateEntropy(randomDataSet, " ", "");
			ID3Random(parent);
		}
		public static String calculateMaxIgAttributeRandom(Node parent)
		{
			double maxIG=0.0;
			String maxAttributeName = "aa";
			randomListGenerator();
				for(int i=0; i <randomAttributeList.size();i++)
				{
					double temp= calculateInformationGain(parent,randomAttributeList.get(i));
					String attributeKey=randomAttributeList.get(i);
					if(temp==1.0){ maxAttributeName = attributeKey;  return attributeKey;}
					else if(attributeKey.compareTo(parent.nodeName.split(":")[0])!=0 && maxIG <= temp)
					{
						//System.out.println("max1" + attributeKey);
						maxIG=temp;
						maxAttributeName=attributeKey;
						// stores a reference to node of highest Ig so as to be removed from attributeList
					}
					//again randomly generates a list
				}
			  	attributeList.remove(maxAttributeName);//removing the node from the attributeList

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
				//try{ fw.write("1" + true + "\n");}catch(Exception e){}
				c++;
				}
				else if(parent.allYes)
				{
					//try{ fw.write("2" + true + "\n");}catch(Exception e){}
					//System.out.println(parent.nodeName);
				parent.children.add(new Node(true));
				}
				else if(parent.allNo)
				{
					//try{ fw.write("3" + false + "\n");}catch(Exception e){}
					//System.out.println(parent.nodeName);
					parent.children.add(new Node(false));
				}

				else
				{
					if(attributeList.isEmpty()) return;
					String maxAttributeName=calculateMaxIgAttributeRandom(parent);
					int attributeIndex = new ArrayList<String>(attributeRangeHashMap.keySet()).indexOf(maxAttributeName);   //make a separate array list for keys
					ArrayList<String> attributeVariety=attributeRangeHashMap.get(maxAttributeName);
					//System.out.println(attributeVariety +" " +  maxAttributeName);
					if(maxAttributeName.compareTo(parent.nodeName.split(":")[0])==0)
					{
						int index =  new ArrayList<String>(attributeRangeHashMap.keySet()).indexOf(parent.nodeName.split(":")[0]);
						Boolean b = findMostCommon(parent.reducedDataSet, index,parent.nodeName.split(":")[1] );
						//try{ fw.write("x" + "\n");}catch(Exception e){}
						parent.children.add(new Node(b));
					}
					else{
					for(int j=0;j<attributeVariety.size();j++)
					{
						  //System.out.println("j "+j);
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
			//System.out.println("numberOfAttributes : "+numberOfAttributes);
			/*if(numberOfAttributes==1)
			{
				flag=1;
			}*/


				for(int i=0;i<numberOfAttributes;i++)
				{

					//int randomNumber = (int)(Math.random() * (attributeList.size()));
					Collections.shuffle(arrayList);
					randomAttributeList.add(arrayList.get(0));
					arrayList.remove(0);
					//attributeList.remove(randomNumber);
				}
				attributeList.clear();
				System.gc();
				attributeList=new ArrayList<String>(attributeRangeHashMap.keySet());

	}

////////////////////////////////////////function for calculating accuracy of random forest /////////////////////////////////
public static double findAccuracyOfRandomForest(ArrayList<Node> parent)
{

	//System.out.println("*********");
		ArrayList<String> temp;                     //just a temporary ArrayList
		int count1=0,count2=0,count=0;                           // obvious
		for(int i=0;i<testDataSet.size();i++)       // iterating over testData
		{
				temp=testDataSet.get(i);
				for(int j=0;j<numberOfTrees;j++)
				{
					if((checkPositiveCaseForTestData(temp,parent.get(j))))
					{
						count1++;
					}
					if(!checkPositiveCaseForTestData(temp,parent.get(j)))
					{
						count2++;
					}
				}
				if(((count2 > count1)&&(temp.get(temp.size()-1).equals("0")))||((count1>=count2)&&(temp.get(temp.size()-1).equals("1"))))
				{
						count++;
				}

		}
		//System.out.println(testDataSet.size());
		double accuracy=(double)count/testDataSet.size();
		//System.out.println("num " + c);
		return accuracy;
}
}
