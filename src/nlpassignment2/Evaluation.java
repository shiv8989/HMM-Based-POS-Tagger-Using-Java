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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Evaluation {
	// List of tag class
	List<Tag> tagList;
	String testedOutputFilePath;
	String accuracyFilePath;
	String uniqueTagsFilePath;
	List<String> tags;

	public List<Tag> getTagList() {
		return tagList;
	}

	public void setTagList(List<Tag> tagList) {
		this.tagList = tagList;
	}

	public String getTestedOutputFilePath() {
		return testedOutputFilePath;
	}

	public void setTestedOutputFilePath(String testedOutputFilePath) {
		this.testedOutputFilePath = testedOutputFilePath;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public void init() {
		for (int i = 0; i < tags.size(); i++) {
			Tag tag = new Tag();
			tag.setTagIndex(i);
			tag.setTagname(tags.get(i));
			tagList.add(tag);
		}
	}

	public void run() {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(testedOutputFilePath));
			String line = "";
			while ((line = br.readLine()) != null) {
				if (line.isEmpty() || line.contains("**End Sentence**"))
					continue;
				String[] cols = line.split("\\s+");
				if (cols.length < 3)
					continue;
				String actualTag = cols[1];
				String predictedTag = cols[2];
				for (int i = 0; i < tags.size(); i++) {
					Tag temp = tagList.get(i);
					if (temp.getTagname().equalsIgnoreCase(actualTag)
							&& actualTag.equalsIgnoreCase(predictedTag)) {
						int tpCount = temp.getTp() + 1;
						temp.setTp(tpCount);
					} else if (temp.getTagname().equalsIgnoreCase(actualTag)) {
						int fnCount = temp.getFn() + 1;
						temp.setFn(fnCount);
					} else if (temp.getTagname().equalsIgnoreCase(predictedTag)) {
						int fpCount = temp.getFp() + 1;
						temp.setFp(fpCount);
					} else {
						int tnCount = temp.getTn() + 1;
						temp.setFn(tnCount);
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeAcc() {
		BufferedWriter bw = null;
		double precisionTotal=0.0;
		double recallTotal=0.0;
		double fmeasureTotal=0.0;
		try {
			bw = new BufferedWriter(new FileWriter(accuracyFilePath));
			bw.write("Tag\tPrecision\tRecall\tFMeasure\n");
			for (int i = 0; i < tagList.size(); i++) {
				Tag temp = tagList.get(i);
				bw.write(temp.getTagname() + "\t" + temp.getPrecision()
							+ "\t" + temp.getRecall() + "\t"
							+ temp.getFmeasure() + "\n");
					precisionTotal +=temp.getPrecision();
					recallTotal +=temp.getRecall();
					fmeasureTotal +=temp.getFmeasure();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("overall precision "+(precisionTotal+1)/((double)tagList.size()+1));
		System.out.println("overall recall "+(1+recallTotal)/(1+(double)tagList.size()));
		System.out.println("overall F-measure "+(1+fmeasureTotal)/(1+(double)tagList.size()));
                
	}

	public void getTag() {
		BufferedReader br = null;
		String line = "";
		Set<String> tagSet=new LinkedHashSet<String>();
		try {
			br = new BufferedReader(new FileReader(uniqueTagsFilePath));
			while ((line = br.readLine()) != null) {
				if (line.isEmpty() || line.contains("*End Sentence*"))
					continue;
				String cols[] = line.split("\\s+");
				if (cols.length < 3)
					continue;
				tagSet.add(cols[1].trim().toUpperCase());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		tags=new ArrayList<>(tagSet);

	}

	public static void main(String[] args) {
		Evaluation ev = new Evaluation();
	for (int i = 1; i <= 5; i++) {
			System.out.println(i);
			ev.tagList = new ArrayList<Tag>();
			ev.tags = new ArrayList<>();
			ev.uniqueTagsFilePath = "C:\\Users\\Shiv\\Documents\\NetBeansProjects\\NLPASSIGNMENT2\\src\\Output\\out" +  i+".txt";
			ev.getTag();
			System.out.println("Fold " + "Evaluating");
			ev.testedOutputFilePath ="C:\\Users\\Shiv\\Documents\\NetBeansProjects\\NLPASSIGNMENT2\\src\\Output\\out" +  i+".txt" ;
			ev.accuracyFilePath = "C:\\Users\\Shiv\\Documents\\NetBeansProjects\\NLPASSIGNMENT2\\src\\Accuracy\\accuracy_fold_" + i + ".txt";
			ev.init();
			ev.run();
			ev.writeAcc();
			System.out.println("Successfully Completed...");
		}

	}

}
