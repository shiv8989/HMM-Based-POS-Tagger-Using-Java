package nlpassignment2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.Bag;
import org.apache.commons.collections.bag.HashBag;

public class PosTagger1 {

    static HashMap<String, Double> start_prob = new HashMap();
    static HashMap<String, Double> emission_prob = new HashMap();
    static HashMap<String, Double> transition_prob = new HashMap();

    static List<String> TagSet;
    static List<String> START_TAG;
    static List<Integer> count_Tag = new ArrayList<Integer>();
    static List<Integer> count_tag_appeared = new ArrayList<Integer>();
    static List<String> Transition;
    static List<String> Transition_set;
    static List<Integer> TransitionCount = new ArrayList<Integer>();
    static List<Integer> CountTransition = new ArrayList<Integer>();
    static List<String> Transition_first_tag = new ArrayList<String>();
    static List<String> WordTag_list;
    static List<String> tran = new ArrayList<String>();
    static List<String> WordTag_Tag = new ArrayList<String>();
    static List<String> Word_set_list;
    static List<String> Emission_combination_list;
    static List<String> Emission_combination_list_unique;

    public static void run(int th) throws IOException {
        //for (int th = 1; th < 6; th++) {
        String file1 = "C:\\Users\\Shiv\\Documents\\NetBeansProjects\\NLPASSIGNMENT2\\src\\Train\\train" + th + ".txt";
        Bag Initial_transition = new HashBag();
        Bag Word_bag = new HashBag();
        Set Word_set = new HashSet();
        Bag start_tag = new HashBag();
        Set Start_tag_set = new HashSet();
        Bag WordTag = new HashBag();
        Bag Tag = new HashBag();
        Bag TransitionBag = new HashBag();
        Set TransitionSet = new HashSet();
        Bag Emission_combination = new HashBag();
        Set Emission_combination_set = new HashSet();
        HashSet start_set = new HashSet();
        HashSet WordTag_set = new HashSet();
        LinkedHashSet Tag_set = new LinkedHashSet();
        HashSet TransitionBag_set = new HashSet();
        Double start_probability;
        Double emission_probability;
        Double Transition_probability;
        int countSent = 0;
        int count = 0;
        int lines = 0;
        int count1 = 0;
        int[] counting = null;
        String[] tag = null;
        BufferedReader br = new BufferedReader(new FileReader(file1));
        String line = "";
        while ((line = br.readLine()) != null) {
            lines = lines + 1;

            String[] b = line.split("\\s+");
            if (b.length < 2) {
                continue;
            }
            for (int i = 0; i < b.length; i++) {

                WordTag.add(b[i]);

                WordTag_set.add(b[i]);
                String[] a = b[i].split("_");

                if (a.length < 2) {
                    continue;
                }
                Tag.add(a[1]);
                if (i == 0) {
                    start_tag.add(a[1]);
                    Start_tag_set.add(a[1]);
                }

                Tag_set.add(a[1]);
                Word_bag.add(a[0]);
                Word_set.add(a[0]);

            }
        }
        br.close();
        TagSet = new ArrayList<String>(Tag_set);
        Word_set_list = new ArrayList<String>(Word_set);
        START_TAG = new ArrayList<String>(start_tag);

        //Calculate the start probability.....
        for (int i = 0; i < Tag_set.size(); i++) {
            String FirstTag = TagSet.get(i);
            int c = start_tag.getCount(FirstTag);
            int k = Tag.getCount(FirstTag);
            count_tag_appeared.add(k);
            count_Tag.add(c);

        }

        for (int i = 0; i < TagSet.size(); i++) {
            double k = (double) (count_Tag.get(i) + 1);
            double l = lines + Tag_set.size();
            start_probability = (k + 1) / (l + 1);
            start_prob.put(TagSet.get(i), start_probability);
        }
        //System.out.println("\n \n hashmap for start probability");
        Set set = start_prob.entrySet();
        Iterator i = set.iterator();

        // Display elements
        while (i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
            //   System.out.print(me.getKey() + ": ");
            //   System.out.println(me.getValue());
        }
        //  System.out.println();
        WordTag_list = new ArrayList<String>(WordTag_set);

        //***********Emission Probability***********************
        Emission_combination_set.clear();
        Emission_combination.clear();
        
        for (int p = 0; p < Word_set.size(); p++) {
            String first = Word_set_list.get(p);
            for (int p1 = 0; p1 < Tag_set.size(); p1++) {
                String second = TagSet.get(p1);
                Emission_combination.add(first + "_" + second);
                //Emission_combination_set.add(first + "_" + second);
            }
        }

        Emission_combination_list = new ArrayList<String>(Emission_combination);
   //   Emission_combination_list_unique = new ArrayList<String>(Emission_combination);

        // System.out.println("emission probability combination" +Emission_combination_list);   
        for (int p2 = 0; p2 < Emission_combination_list.size(); p2++) {
            String c = Emission_combination_list.get(p2);
            String[] d = c.split("_");
            if (d.length < 2) {
                continue;
            }
            WordTag_Tag.add(d[1]);
        }

        for (int p3= 0; p3 < Emission_combination_list.size(); p3++) {
            String first = Emission_combination_list.get(p3);

            double cnt2 = WordTag.getCount(first) + 1;

            String second = WordTag_Tag.get(p3);

            double l = Tag.getCount(second) + Tag_set.size();

            emission_probability = (cnt2 + 1) / (1 + l);

            emission_prob.put(first, emission_probability);

        }

        //  System.out.println("\n \n hashmap for emission probability");	
        Set set11 = emission_prob.entrySet();
        Iterator i2 = set11.iterator();

        // Display elements
        while (i2.hasNext()) {
            Map.Entry me = (Map.Entry) i2.next();
            //   System.out.print(me.getKey() + ": ");
            //  System.out.println(me.getValue());
        }
        // System.out.println();

        	   
        //add transitions
        for (int p3 = 0; p3 < Tag_set.size(); p3++) {
            for (int q3 = 0; q3 < Tag_set.size(); q3++) {
                String firstTag = TagSet.get(p3);
                String secondTag = TagSet.get(q3);
                TransitionBag.add(firstTag + "_" + secondTag);
                TransitionSet.add(firstTag + "_" + secondTag);

            }
        }

        Transition = new ArrayList<String>(TransitionBag);
        Transition_set = new ArrayList<String>(TransitionSet);

        BufferedReader br1 = new BufferedReader(new FileReader(file1));
        String line1 = "";
        while ((line1 = br1.readLine()) != null) {
            //	System.out.println(line1);
            lines = lines + 1;
            String[] b = line1.split("\\s+");
            if (b.length < 2) {
                continue;
            }
            for (int i1 = 0; i1 < b.length - 1; i1++) {

                String[] a = b[i1].split("_");
                Tag.add(a[1]);
                String[] a1 = b[i1 + 1].split("_");
                if (a.length < 2) {
                    continue;
                }
                if (a1.length < 2) {
                    continue;
                }
                String tg = a[1] + "_" + a1[1];
                Initial_transition.add(tg);

                if (i1 == b.length - 1) {
                    Initial_transition.add(a[1] + "_.");
                }
            }
        }

        //RECAlCULATION OF TRANSITION PROBABILITY
        for (int k = 0; k < Transition.size(); k++) {
            String t = Transition.get(k);
            String[] b = t.split("_");
            String first = b[0];
            Double transition1 = (double) (Initial_transition.getCount(t) + 1);
            Double transition2 = (double) (Tag.getCount(first) + Tag_set.size());
            Double trans = (transition1 + 1) / (transition2 + 1);

            transition_prob.put(t, trans);

        }

        Set set1 = transition_prob.entrySet();
        Iterator i21 = set1.iterator();

        // Display elements
		//System.out.println(" \n \n hashmap for Transition probability");
        while (i21.hasNext()) {
            Map.Entry me = (Map.Entry) i21.next();
            //  System.out.print(me.getKey() + ": ");
            //  System.out.println(me.getValue());
        }
//  System.out.println();
        br.close();
    }
    // }

}
