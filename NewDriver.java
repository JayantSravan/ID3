package ID3;
import java.util.*;
import java.io.*; 
import java.util.ArrayList;

public class NewDriver
{
	ArrayList<ArrayList<String>> dataSet = new ArrayList<ArrayList<String>>();
	LinkedHashMap<String, ArrayList<String>> attributeRangeHashMap = new LinkedHashMap<String, ArrayList<String>>();
	
	public NewDriver(String rawDataSetFile, String outputDataSetFile,String classListFilePath)
	{
		try
		{
			dataSet = new PreprocessData(rawDataSetFile, outputDataSetFile).createDataPointsList();
			attributeRangeHashMap = new Range2(classListFilePath).attributeRangeHashMap;
			dataSet = new ResolveMissing_and_ContinuousValues(dataSet).dataSet;
		}
		catch(IOException ioe)
		{
			
		}
	}
	
	public double calculateEntropy(ArrayList<ArrayList<String>> reducedDataSet, String attribute,String attributeValue)
	{
		double entropy;
		int positive_examples=0;
		int negative_examples = 0;
		if(attribute.equals(" ")) attribute = "Output";
		int attributeIndex=0;
		
		for(String keyAttribute : attributeRangeHashMap.keySet())
		{
			if(keyAttribute.equals(attribute)) break;
			attributeIndex++;
		}
		
		for(ArrayList<String> dataRow : reducedDataSet)
		{
			if(dataRow.get(attributeIndex).equals(attributeValue))
			{
				if(dataRow.get(dataRow.size()-1).equals("1")) positive_examples++;
				else negative_examples++;
			}
		}
		double p_positive = positive_examples/reducedDataSet.size();
		double p_negative = negative_examples/reducedDataSet.size();
		entropy = -(Math.log(p_positive)*(p_positive))-(Math.log(p_negative)*(p_negative))/Math.log(2);
		
		return entropy;
		
	}

	public double calculateInformationGain(Node parent,String attribute)
	{
		ArrayList<ArrayList<String>> reducedDataSet = parent.reducedDataSet;
		double attributeInformationGain = 0.0;
		int numOfRows_attributePresent = 0;
		for(String attributeValue : attributeRangeHashMap.get(attribute))
		{
			for(ArrayList<String> dataRow : reducedDataSet)
			{
				numOfRows_attributePresent++;
			}
			attributeInformationGain = attributeInformationGain + ((double)numOfRows_attributePresent/reducedDataSet.size())*calculateEntropy(reducedDataSet, attribute, attributeValue);			
		}
		return parent.informationGain - attributeInformationGain;
	}
}
