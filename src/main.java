import java.io.*;
import java.util.*;

/**
 * Created by micx on 6/19/15.
 */
public class main {


    public static void main(String[] args) throws IOException{

        File file = new File("sample.csv");
        BufferedReader br = null;

        strPrepare prepare = new strPrepare();

        try {
            br = new BufferedReader(new FileReader(file));
            String str;
            List<String> fullList = new LinkedList<String>();
            List<String> listEn = new LinkedList<String>();
            List<String> listRu = new LinkedList<String>();
            List<String> listNum = new LinkedList<String>();
            List<String> listOther = new LinkedList<String>();
            Map<String, HashSet> map = new TreeMap<String, HashSet>();
            HashSet<String> resaultSet = new HashSet<String>();
            /* -- -- --  -- -- --  -- -- --  -- -- --  -- -- --  -- -- --  -- -- --  -- -- --  -- -- -- */
            while((str = br.readLine()) != null) {
                if(str.length() > 0) {
                    str = prepare.getPrepareString(str);
                    String info = prepare.getStringInfo(str);
                    fullList.add(str);
                    if (info.equals("en")) {
                        listEn.add(str);
                    } else
                    if (info.equals("ru")){
                        listRu.add(str);
                    } else
                    if (info.equals("num")){
                        listNum.add(str);
                    } else
                    if (info.equals("other")){
                        listOther.add(str);
                    }
                }
            }
            System.out.println("full list size: " + fullList.size());
            System.out.println("ru list size: " + listRu.size());
            System.out.println("en list size: " + listEn.size());
            System.out.println("num list size: " + listNum.size());
            System.out.println("other list size: " + listOther.size());

            /* -- -- --  -- -- --  -- -- --  -- -- English  --  -- -- --  -- -- --  -- -- --  -- -- --  -- -- -- */
            if (listEn.size() > 0 ) {
                System.out.println("Start parsing English words");
                porterSteamerRu ps = new porterSteamerRu();
                for(String str1: listEn) {
                    String tmp1 = str1.toLowerCase();
                    resaultSet = new HashSet<String>();
                    for(String str2:listEn ) {
                        String tmp2 =str2.toLowerCase();
                        String cheking = "";
                        if (tmp1.length() >= tmp2.length()) {
                            cheking = prepare.checkEquals(tmp1, tmp2, "en");
                        } else {
                            cheking = prepare.checkEquals(tmp2, tmp1, "en");
                        }
                        if (cheking != null) {
                            resaultSet.add(str1);
                            map.put(cheking, resaultSet);
                            resaultSet.add(str2);
                            map.put(cheking, resaultSet);
                        }
                    }
                }
                System.out.println("End parsing English words");
            }
            /* -- -- --  -- -- --  -- -- --  -- -- -- Numbers  -- -- --  -- -- --  -- -- --  -- -- --  -- -- -- */
            if (listNum.size() > 0 ) {
                System.out.println("Start parsing Number words");
                porterSteamerRu ps = new porterSteamerRu();
                for(String str1: listNum) {
                    String tmp1 = ps.stem(str1);
                    resaultSet = new HashSet<String>();
                    for(String str2:fullList ) {
                        String tmp2 = ps.stem(str2);
                        String cheking = "";
                        if (tmp1.length() >= tmp2.length()) {
                            cheking = prepare.checkEquals(tmp1, tmp2, "num");
                        } else {
                            cheking = prepare.checkEquals(tmp2, tmp1, "num");
                        }
                        if (cheking != null) {
                            resaultSet.add(str1);
                            map.put(cheking, resaultSet);
                            resaultSet.add(str2);
                            map.put(cheking, resaultSet);
                        }
                    }
                }
                System.out.println("End parsing Number words");
            }
            /* -- -- --  -- -- --  -- -- --  -- -- -- Russia lang  -- -- --  -- -- --  -- -- --  -- -- --  -- -- -- */
            if (listRu.size() > 0 ) {
                System.out.println("Start parsing Russian words");
                for (String str1:listRu) {
                    String tmp1 = prepare.parser(str1);
                    String cheking = "";
                    resaultSet = new HashSet<String>();
                    for(String str2:listRu) {
                        String tmp2 = prepare.parser(str2);
                        cheking = prepare.checkEquals(tmp1, tmp2, "ru");
                        if( cheking != null) {
                            resaultSet.add(str1);
                            map.put(cheking, resaultSet);
                            resaultSet.add(str2);
                            map.put(cheking, resaultSet);
                        }
                    }
                }
                System.out.println("End parsing Russian words");
            }
            /* -- -- --  -- -- --  -- -- --  -- -- -- Other -- -- --  -- -- --  -- -- --  -- -- --  -- -- -- */
            if (listOther.size() > 0 ) {
                System.out.println("Start parsing symbols with words");
                porterSteamerRu ps = new porterSteamerRu();
                for(String str1: listOther) {
                    String tmp1 = ps.stem(str1);
                    resaultSet = new HashSet<String>();
                    for(String str2:fullList) {
                        String tmp2 = ps.stem(str2);
                        String cheking = "";
                        if (tmp1.length() > tmp2.length()) {
                            cheking = prepare.checkEquals(tmp1, tmp2, "other");
                        } else {
                            cheking = prepare.checkEquals(tmp2, tmp1, "other");
                        }
                        if (cheking != null) {
                            resaultSet.add(str1);
                            resaultSet.add(str2);
                            map.put(cheking, resaultSet);
                        }
                    }
                }
                System.out.println("End parsing Symbols words");
            }
           FileWriter fw = new FileWriter("out.csv");

            for (String e: map.keySet()) {
                fw.write(e + " :");
                fw.write(String.valueOf(map.get(e)));
                fw.write("\n");
            }
            fw.close();

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            if(br != null) {
                br.close();
            }

        }
    }



}
