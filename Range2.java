package ID3;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.io.FileReader;

public class Range2
{
	LinkedHashMap<String, ArrayList<String>> attributeRangeHashMap = new LinkedHashMap<String, ArrayList<String>>();
	String classListFilePath;
	
	public Range2(String classListFilePath)
	{
		this.classListFilePath = classListFilePath;
		createAttributeRange();
	}	
	
	private void createAttributeRange() 
	{
		String lineSplit[];
		String line,key;
		try
		{
			BufferedReader bfr = new BufferedReader(new FileReader(classListFilePath));
			while((line = bfr.readLine())!=null)
			{
				lineSplit = line.split(":");
				key = lineSplit[0];
				lineSplit = lineSplit[1].split(" ,");
				lineSplit[0] = lineSplit[0].substring(1);				
				attributeRangeHashMap.put(key, new ArrayList<String>(Arrays.asList(lineSplit)));
			}
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}	
}
