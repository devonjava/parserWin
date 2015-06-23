import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by micx on 20.06.2015.
 */
public class strPrepare {
    private static final Pattern en = Pattern.compile("[a-z[A-Z]]+");
    private static final Pattern ru = Pattern.compile("[а-я[А-Я]]+");
    private static final Pattern num = Pattern.compile("[0-9]+");


    private Matcher matcher;

    public String getStringInfo(String str) {
        String resault = "other";
        str = getPrepareString(str);

        matcher = en.matcher(str);
        if (matcher.matches() ){
            resault = "en";
        }

        matcher = ru.matcher(str);
        if(matcher.matches()) {
            resault ="ru";
        }

        matcher = num.matcher(str);
        if (matcher.matches()) {
            resault = "num";
        }

        return resault;
    }

    public String getPrepareString(String str) {
        str = str.replaceAll("\"","");
        return str;
    }

    public String parser(String str) {
        porterSteamerRu porter = new porterSteamerRu();
        str = porter.stem(str);
        return str;
    }

    public String checkEquals(String str1, String str2, String lang) {
        BoyerMoore bm = new BoyerMoore();

        if ( lang.equals("en")) {
            if (str1.length() < str2.length()) {
                //System.out.println(str1 + " < "+ str2 + " = " + bm.getFirstEntry(str1, str2));
                if (bm.getFirstEntry(str1, str2)) {
                  //    System.out.println("return str1");
                    return str1;
                }
            }
            if (str1.length() >= str2.length()) {
                //System.out.println(str1 + " > "+ str2 + " = " + bm.getFirstEntry(str1, str2));
                if (bm.getFirstEntry(str1, str2)) {
                  //    System.out.println("return str2");
                    return str2;
                }
            }

        }

        if (lang.equals("ru") || lang.equals("other") || lang.equals("num")) {
            if (str1.length() < str2.length() )  {
                if(str2.contains(str1)) {
                    return str1;
                }
            }
            if (str1.length() >= str2.length()) {
                if(str1.contains(str2)) {
                    return str2;
                }
            }
        }

        return null;
    }

}
