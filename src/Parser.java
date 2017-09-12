import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class Parser {
	public List<List<Attribute>> Parse(String fileToParse){
		List<List<Attribute>> list = new ArrayList<>();
		if(fileToParse == null || fileToParse.length() == 0){
			return list;
		}
		BufferedReader fileReader = null;
		try{
			String line = "";
			fileReader = new BufferedReader(new FileReader(fileToParse));
			String[] attribute_name = fileReader.readLine().split(",");
			line = fileReader.readLine();
			while(line != null){
				String[] attribute_val = line.split(",");
				List<Attribute> tuple = new ArrayList<>();
				for(int i = 0; i < attribute_val.length; i++){
					tuple.add(new Attribute(attribute_name[i], Integer.valueOf(attribute_val[i])));
				}
				list.add(tuple);
				line = fileReader.readLine();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				fileReader.close();
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public double calculateEntropy(List<List<Attribute>> list){
		if(list == null) return 0;
		int countPos = 0;
		int countNeg = 0;
		double entropy = 0;
		for(int i = 0; i < list.size(); i++){
			if(list.get(i).get(list.get(0).size() - 1).attr_val == 1){
				countPos++;
			}
			else{
				countNeg++;
			}
		}
		double probPos = (double)countPos / list.size();
		double probNeg = (double)countNeg / list.size();
		entropy -= probPos * (Math.log(probPos) / Math.log(2));
		entropy -= probNeg * (Math.log(probNeg) / Math.log(2));
		return entropy;
	}
	
	public double calculateGain(double remainEntropy, List<Double> childrenEntropy, List<Integer> childrenDataSize, double dataSize) {
		double gain = remainEntropy;
		for (int i = 0; i < childrenEntropy.size(); i++)
			gain -= (childrenDataSize.get(i) / dataSize) * childrenEntropy.get(i);
		return gain;
	}
	
	public double calculateImpurity(List<List<Attribute>> list){
		if(list == null) return 0;
		int[] count = new int[2];
		for(int i = 0; i < list.size(); i++){
			count[list.get(i).get(list.get(0).size()-1).attr_val]++;
		}
		double res = count[0] * count[1];
		return res / (list.size() * list.size());
	}
}
