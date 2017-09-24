import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class PreprocessData
{
	static String classListFile;
	static String rawDataSetFile;
	static String processedDataFile;
	static String line;
	static String[] lineSplit;
	static BufferedReader bfr;
	
	public PreprocessData(String classListFile, String rawDataSetFile,String outputDataSetFile)
	{
		this.classListFile = classListFile;
		this.rawDataSetFile = rawDataSetFile;
		this.processedDataFile = outputDataSetFile;
		try
		{
			bfr = new BufferedReader(new FileReader(classListFile));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}		
	
	public static void main(String[] args) {
		PreprocessData P = new PreprocessData("C://Users//SUBHADIP JANA//Desktop//classList.txt","C://Users//SUBHADIP JANA//Desktop//ID3_dataSet.txt","C://Users//SUBHADIP JANA//Desktop//ID3PreprocessData.txt");
		try{
		//P.modifyDataSet();
		P.createDataPointsList();
		}
		catch(IOException ioe){System.out.println("File error");}

	
	}
	
	public static void modifyDataSet() throws IOException
	{
		bfr = new BufferedReader(new FileReader(rawDataSetFile));
		new FileWriter(processedDataFile);
		FileWriter fw = new FileWriter(processedDataFile);		
		while((line=bfr.readLine())!=null)
		{
			lineSplit = line.split(", ");
			if(lineSplit[lineSplit.length-1].contains("=")) lineSplit[lineSplit.length-1] = "0";
			else lineSplit[lineSplit.length-1] = "1";
			line = Arrays.toString(lineSplit);			
			fw.write(line.substring(1,line.length()-1) + "\n");			
		}
		fw.close();
	}
	
	public static String[][] createDataPointsList() throws IOException
	{
		modifyDataSet();
		ArrayList<String[]> dataPoints_ArrayList = new ArrayList<String[]>();
		bfr = new BufferedReader(new FileReader(processedDataFile));
		
		while((line=bfr.readLine())!=null)
		{
			dataPoints_ArrayList.add(line.split(", "));
		}
		
		return dataPoints_ArrayList.toArray(new String[0][0]);
	}
}
