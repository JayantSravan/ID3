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

	public PreprocessData(String rawDataSetFile,String outputDataSetFile)
	{
		//this.classListFile = classListFile;
		this.rawDataSetFile = rawDataSetFile;
		this.processedDataFile = outputDataSetFile;

	}

	public static void main(String[] args) {
		PreprocessData P = new PreprocessData("./ID3_dataSet.txt","./ID3PreprocessData.txt");
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

	public static ArrayList<ArrayList<String>> createDataPointsList() throws IOException
	{
		modifyDataSet();
		ArrayList<ArrayList<String>> dataPoints_ArrayList = new ArrayList<ArrayList<String>>();
		bfr = new BufferedReader(new FileReader(processedDataFile));

		while((line=bfr.readLine())!=null)
		{
			lineSplit = line.split(", ");
			ArrayList<String> lineArrayList = new ArrayList<String>(Arrays.asList(lineSplit));
			dataPoints_ArrayList.add(lineArrayList);
		}
		return dataPoints_ArrayList;
	}
}
