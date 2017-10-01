package ID3;
import java.util.*;
import java.io.*;
import java.util.ArrayList;

//https://www.quora.com/How-can-I-find-a-real-step-by-step-example-of-a-decision-tree-pruning-to-overcome-overfitting
//https://github.com/gabrielcc2/ID3AlgorithmForLeaningDecisionTree/blob/master/src/ml/control/ID3Learning.java
public class NewDriver
{
	static ArrayList<ArrayList<String>> dataSet = new ArrayList<ArrayList<String>>();
	static ArrayList<ArrayList<String>> testDataSet = new ArrayList<ArrayList<String>>();
	static LinkedHashMap<String, ArrayList<String>> attributeRangeHashMap = new LinkedHashMap<String, ArrayList<String>>();
	static FileWriter fw;
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
		NewDriver N = new NewDriver("C:\\Users\\SUBHADIP JANA\\Desktop\\ID3_dataSet.txt","C:\\Users\\SUBHADIP JANA\\Desktop\\ID3PreprocessData.txt","C:\\Users\\SUBHADIP JANA\\Desktop\\classListID3.txt","C:\\Users\\SUBHADIP JANA\\Desktop\\ID3_TEST_dataSet.txt","C:\\Users\\SUBHADIP JANA\\Desktop\\Test_dataSet_output.txt");
		Node mainNode = new Node("S:Main",dataSet);
		mainNode.informationGain = calculateEntropy(dataSet, " ", "");
		System.out.println(mainNode.informationGain);
		try{
		 fw = new FileWriter("C:\\Users\\SUBHADIP JANA\\Desktop\\vali.txt");}
		catch(Exception e){}
		ID3(mainNode);
		for(Node child : mainNode.children)
		{
			System.out.println(child.nodeName);
		}
		try{fw.close();}catch(Exception e){}
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
		  System.out.println(parent.nodeName);
		 try{ fw.write(parent.nodeName + "\n");}catch(Exception e){}

		  /*
		  if(parent.nodeName.equals("native-country:United-States"))
		  {
			  if(parent.reducedDataSet.isEmpty()) System.out.println("jiji");
				for(ArrayList<String> dataRow : parent.reducedDataSet)
				{
					for(String str : dataRow)
					{
						System.out.print(str + " ");
					}
					System.out.println();
				}
		  }
		  */
	      if(parent.reducedDataSet.isEmpty())
	      {
	    	  parent.children.add(new Node(true));
	      }
	      else if(parent.allYes)
	      {
	    	 parent.children.add(new Node(true));
	      }
	      else if(parent.allNo)
	      {
	        parent.children.add(new Node(false));
	      }

	      else
	      {
	        String maxAttributeName=calculateMaxIgAttribute(parent);
	        //if(maxAttributeName.equals(parent.nodeName)) return;
	       // System.out.println(maxAttributeName);
	        int attributeIndex = new ArrayList<String>(attributeRangeHashMap.keySet()).indexOf(maxAttributeName);   //make a separate array list for keys
	        ArrayList<String> attributeVariety=attributeRangeHashMap.get(maxAttributeName);
	        if(maxAttributeName.compareTo(parent.nodeName.split(":")[0])==0)
	        {
	        	System.out.println("HELP");
	        	parent.children.add(new Node(true));
	        }
	        else{
	        for(int j=0;j<attributeVariety.size();j++)
	        {
	            Node child=new Node(maxAttributeName + ":" + attributeVariety.get(j),obtainReducedDataset(parent,attributeVariety.get(j),attributeIndex));
	            parent.children.add(child);
	            child.informationGain = calculateEntropy(parent.reducedDataSet, maxAttributeName, attributeVariety.get(j));
	           // System.out.println(child.nodeName);
	            ID3(child);
	        }}
	        //parent.reducedDataSet = null;
	      }
	  }

		public static double Find_accuracy(Node parent)
	  {
	      ArrayList<String> temp;                     //just a temporary ArrayList
	      double count=0.0;                           // obvious
	      for(int i=0;i<testDataSet.size();i++)       // iterating over testData
	      {
	          temp=testDataSet.get(i);
	          if(checkPositiveCaseForTestData(temp,parent))   //checking whether data is positive or not
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
	    String[] words=parent.children.get(0).nodeName.split(":");  // for fetching out attributetype eg: wind from wind:high
	    int attributeindextemp=attributeRangeHashMap.keySet().indexOf(words[0]); // calculating indexof element in hashmap so as to pick attributename (eg; high) from tempstring
	    String attributeName=tempString.get(attributeindextemp); //getting the attributename from tempstring list
	    String completeName = words[0] + ":"+attributeName; // making the nodename for finding node compared in children list
	    for(int i=0;i<parent.children.size();i++)   //just interating to find that node with the name completename
	    {
	      if(parent.children.get(i).nodeName.equals(completeName))
	      {
	        return checkPositiveCaseForTestData(tempString,parent.children.get(i)); //obvious
	        break;
	      }
	    }
	  }

}
