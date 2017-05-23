/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nlpassignment2;

/**
 *
 * @author Shiv
 */


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ConfusionMatrix {
	int tagSize;
	List<String> tagList;
	static String test_output_file_path = "C:\\Users\\Shiv\\Documents\\NetBeansProjects\\NLPASSIGNMENT2\\src\\Output\\out" ;
	String confusion_Matrix_file_path = "C:\\Users\\Shiv\\Documents\\NetBeansProjects\\NLPASSIGNMENT2\\src\\confusion\\confusion_";
	int[][] confusionMatrix;

	// private int[][] confusion_Matrix;

	public ConfusionMatrix(String filePath) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		Set<String> set = new LinkedHashSet<>();
		String line = "";
		while ((line = br.readLine()) != null) {
			if (line.isEmpty() || line.contains("*End Sentence*"))
				continue;
			String cols[] = line.split("\\s+");
			if (cols.length < 3)
				continue;
			set.add(cols[1].trim().toUpperCase());
		}
		tagList = new ArrayList<String>(set);
		br.close();
	}
   /** 
    * @throws IOException
    * method to write the confusion matrix into a file
    */
	public void writeConfusionMatrix(String confusion_matrix_file_path)
			throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(
				confusion_matrix_file_path));
		for (int i = 0; i < tagSize; i++) {
			bw.write("\t" + tagList.get(i));
		}
		bw.write("\n");
		for (int i = 0; i < tagSize; i++) {
			bw.write(tagList.get(i) + "\t");
			for (int j = 0; j < tagSize; j++) {
				bw.write(confusionMatrix[i][j] + "\t");
			}
			bw.write("\n");
		}
		bw.close();
	}

	public static void main(String[] args) throws IOException {
        
            
		for (int i = 1; i <= 5; i++) {
			ConfusionMatrix cm = new ConfusionMatrix(test_output_file_path+i+".txt");
			//System.out.println(cm.tagList.size());
			cm.tagSize = cm.tagList.size();
			cm.confusionMatrix = new int[cm.tagSize][cm.tagSize];
			BufferedReader br = new BufferedReader(new FileReader(
					test_output_file_path+i+".txt"));
			String line = "";
			int indexOfActualTag;
			int indexOfPredictedTag;
			int c=0;
			while ((line = br.readLine()) != null) {
				c++;
				if (line.isEmpty() || line.contains("*End Sentence*"))
					continue;
				String cols[] = line.split("\\s+");
				if (cols.length < 3)
					continue;
				//System.out.println(c+i+line);
				indexOfActualTag = cm.tagList.indexOf(cols[1].trim()
						.toUpperCase());
				String actualTag = cm.tagList.get(indexOfActualTag);
				indexOfPredictedTag = cm.tagList.indexOf(cols[2].trim()
						.toUpperCase());
				if(indexOfPredictedTag<0){
					//System.out.println(indexOfPredictedTag);
					indexOfPredictedTag=1;
				}
				//System.out.println(actualTag + "  " + predictedTag);
				String predictedTag = cm.tagList.get(indexOfPredictedTag);
				
				if (cols[1].trim().equalsIgnoreCase(cols[2].trim())) {
					cm.confusionMatrix[indexOfActualTag][indexOfPredictedTag]++;
				} else {
					cm.confusionMatrix[indexOfActualTag][indexOfPredictedTag]++;
					cm.confusionMatrix[indexOfPredictedTag][indexOfActualTag]++;
				}

			}
			cm.writeConfusionMatrix(cm.confusion_Matrix_file_path + i + ".txt");
                        System.out.println("Matrix Created Successfully... ");
			br.close();
                         
		}
                            
	}
                       
}
