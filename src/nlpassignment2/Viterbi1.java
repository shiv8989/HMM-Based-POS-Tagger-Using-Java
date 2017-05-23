/*@author Shiv */

package nlpassignment2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Viterbi1 {

    static HashMap<String, Double> start_probability;
    static HashMap<String, Double> emission_probability;
    static HashMap<String, Double> transition_probability;
    static List<String> tag;
    static List<String> word;
    static HashMap<String, String> Index = new HashMap<String, String>();

    public static void main(String args[]) throws IOException {

        for (int th = 1; th < 6; th++) {
            String filepath_out = "C:\\Users\\Shiv\\Documents\\NetBeansProjects\\NLPASSIGNMENT2\\src\\Output\\out" + th + ".txt";
            File fp = new File(filepath_out);
            if(fp.createNewFile())
            {
                
            }
            String filepath_test = "C:\\Users\\Shiv\\Documents\\NetBeansProjects\\NLPASSIGNMENT2\\src\\Test\\test" + th + ".txt";
            FileWriter fw = new FileWriter(filepath_out);
            int lines = 0;

            PosTagger1 pos=new PosTagger1();
            pos.run(th);
            String[] b = null;
            List<String> sent = new ArrayList<String>();
            emission_probability = pos.emission_prob;
            transition_probability = pos.transition_prob;
            start_probability = pos.start_prob;
            tag = pos.TagSet;
            BufferedReader br = new BufferedReader(new FileReader(filepath_test));
            String line = "";

            while ((line = br.readLine()) != null) {
                int counter = 0;
                String[] tagw = new String[line.length()];
                lines = lines + 1;
                b = line.split("\\s+");
                //for(int i=0; i<b.length;i++)
                if (b.length < 2) {
                    continue;
                }

                for (int i = 0; i < b.length; i++) {
                    String[] a = b[i].split("_");
                    if (a.length < 2) {
                        continue;
                    }
                    sent.add(a[0]);

                    tagw[counter] = a[1];
                    //System.out.println("tag "+tagw[0]);
                    counter++;
                }

                Double max1 = 0.0;
                int ar = 0;
                for (int i = 0; i < pos.TagSet.size(); i++) {
                    Double val = (Double) start_probability.get(pos.TagSet.get(i));
                    double emission = 0.0;

                    if (emission_probability.containsKey(sent.get(0) + "_" + pos.TagSet.get(i))) {

                        emission = emission_probability.get(sent.get(0) + "_" + pos.TagSet.get(i));

                    } else {
                        emission = (double) 1 / (pos.Word_set_list.size()+1);
                    }

                    Double prob = val * emission;
                    if (prob > max1) {
                        max1 = prob;
                        ar = i;
                    }

                }

                //  	 fw.append(sent.get(0)+"\t"+pos.TagSet.get(ar)); 
                //System.out.println("values"+sent.get(0)+"\t"+pos.TagSet.get(ar));
                fw.append(sent.get(0) + "\t" + tagw[0] + "\t" + pos.TagSet.get(ar));
                Index.put(sent.get(0), pos.TagSet.get(ar));

                for (int j = 1; j < (sent.size()); j++) {
                    Double max11 = 0.0;
                    int arg = 0;
                    Double emission = 0.0;
                    for (int i = 0; i < pos.TagSet.size(); i++) {
                        if (emission_probability.containsKey(sent.get(j) + "_" + pos.TagSet.get(i))) {

                            emission = emission_probability.get(sent.get(j) + "_" + pos.TagSet.get(i));
                        } else {
                            emission = (double) 1
                                    / ((pos.Word_set_list.size()+1));
                        }

                        Double value = (Double) transition_probability.get(Index.get(sent.get(j - 1)) + "_" + pos.TagSet.get(i));
                        Double probability = value * emission;
                        if (probability > max11) {
                            max11 = probability;
                            arg = i;

                        }

                    }
                    fw.append("\n");

                    //System.out.println("values "+sent.get(j)+"\t"+ pos.TagSet.get(arg));
                    fw.append(sent.get(j) + "\t" + tagw[j] + "\t " + pos.TagSet.get(arg));

                    Index.put(sent.get(j), pos.TagSet.get(arg));
                }
                fw.append("\n");
                // System.out.println("number of line" +lines);   
                // System.out.println("lines are" +line);

                sent.clear();
            }
            fw.close();
            Set set1 = Index.entrySet();
            Iterator i21 = set1.iterator();

            // Display elements
            //  System.out.println(" \n \n hashmap for word to tag  mapping");
            while (i21.hasNext()) {
                Map.Entry me = (Map.Entry) i21.next();
                //      System.out.print(me.getKey() + ": ");
                //     System.out.println(me.getValue());
            }
            System.out.println("File Created Sucessfully...");
            br.close();
            fw.close();
        }
    }
}
