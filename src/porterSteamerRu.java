/**
 * Created by micx on 6/19/15.
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class porterSteamerRu {

    private static final Pattern PERFECTIVEGROUND = Pattern.compile("((ив|ивши|ившись|ыв|ывши|ывшись)|((=[ая])(в|вши|вшись)))$");

    private static final Pattern REFLEXIVE = Pattern.compile("(с[яь])$");

    private static final Pattern ADJECTIVE = Pattern.compile("(ее|ие|ые|ое|ими|ыми|ей|ий|ый|ой|ем|им|ым|ом|его|ого|ему|ому|их|ых|ую|юю|ая|яя|ою|ею|ский|ские|ская|ское|ского|ая|ое|ые|ого|ым|ом|ого|ому|ых|овый|овая|овое|овые|овую)$");

    private static final Pattern PARTICIPLE = Pattern.compile("((ивш|ывш|ующ)|((?<=[ая])(ем|н|вш|ющ|щ)))$");
    //private static final Pattern PARTICIPLE = Pattern.compile("((ивш|ывш|ующ)|((?<=[ая])(ем|нн|вш|ющ|щ)))$");

    private static final Pattern VERB = Pattern.compile("((ила|ыла|ена|ейте|уйте|ите|или|ыли|ей|уй|ил|ыл|им|ым|ен|ило|ыло|ено|ят|ует|уют|ит|ыт|ены|ить|ыть|ишь|ую|ю)|((?<=[ая])(ла|на|ете|йте|ли|й|л|ем|ло|но|ет|ют|ны|ть|ешь|нно)))$");
    //private static final Pattern VERB = Pattern.compile("((ила|ыла|ена|ейте|уйте|ите|или|ыли|ей|уй|ил|ыл|им|ым|ен|ило|ыло|ено|ят|ует|уют|ит|ыт|ены|ить|ыть|ишь|ую|ю)|((?<=[ая])(ла|на|ете|йте|ли|й|л|ем|н|ло|но|ет|ют|ны|ть|ешь|нно)))$");

    private static final Pattern NOUN = Pattern.compile("(а|ев|ов|ие|ье|е|иями|ями|ами|еи|ии|и|ией|ей|ой|ий|й|иям|ям|ием|ем|ам|ом|у|ах|иях|ях|ы|ь|ию|ью|ю|ия|ья|я)$");

    private static final Pattern RVRE = Pattern.compile("^(.*?[аеиоуыэюя])(.*)$");

    private static final Pattern DERIVATIONAL = Pattern.compile(".*[^аеиоуыэюя]+[аеиоуыэюя].*ость?$");

    private static final Pattern DER = Pattern.compile("ость?$");

    private static final Pattern SUPERLATIVE = Pattern.compile("(ейше|ейш)$");

    private static final Pattern I = Pattern.compile("и$");
    private static final Pattern P = Pattern.compile("ь$");
    private static final Pattern NN = Pattern.compile("нн$");

    private static final Pattern Kinds = Pattern.compile("ик$");

    public String stem(String word) {
        word = word.toLowerCase();
        word = word.replace('ё', 'е');
        Matcher m = RVRE.matcher(word);
        if (m.matches()) {
            String pre = m.group(1);
            String rv = m.group(2);
           // System.out.println("40pre[" + pre + "] rv[" + rv + "]");
            String temp = PERFECTIVEGROUND.matcher(rv).replaceFirst("");
           // System.out.println("42temp->" + temp);
            if (temp.equals(rv)) {
                rv = REFLEXIVE.matcher(rv).replaceFirst("");
                temp = ADJECTIVE.matcher(rv).replaceFirst("");
               // System.out.println("46pre[" + pre + "] rv[" + rv + "]");
               // System.out.println("47temp->" + temp);
                if (!temp.equals(rv)) {
                    rv = temp;
                 //   System.out.println("50pre[" + pre + "] rv[" + rv + "]");
                    rv = PARTICIPLE.matcher(rv).replaceFirst("");
                  //  System.out.println("52pre[" + pre + "] rv[" + rv + "]");
                } else {
                    temp = VERB.matcher(rv).replaceFirst("");
                  //  System.out.println("55temp->" + temp);
                    if (temp.equals(rv)) {
                        rv = NOUN.matcher(rv).replaceFirst("");
                        rv = Kinds.matcher(rv).replaceFirst("");
                    //    System.out.println("58pre[" + pre + "] rv[" + rv + "]");
                    } else {
                        rv = temp;
                     //   System.out.println("61pre[" + pre + "] rv[" + rv + "]");
                    }
                }

            } else {
                rv = temp;
                //System.out.println("67pre[" + pre + "] rv[" + rv + "]");
            }

            rv = I.matcher(rv).replaceFirst("");
           // System.out.println("71pre[" + pre + "] rv[" + rv + "]");
            if (DERIVATIONAL.matcher(rv).matches()) {
                rv = DER.matcher(rv).replaceFirst("");
               // System.out.println("74pre[" + pre + "] rv[" + rv + "]");
            }

            temp = P.matcher(rv).replaceFirst("");
            //System.out.println("78temp->" + temp);
            if (temp.equals(rv)) {
                rv = SUPERLATIVE.matcher(rv).replaceFirst("");
               // System.out.println("81pre[" + pre + "] rv[" + rv + "]");
                rv = NN.matcher(rv).replaceFirst("н");
               // System.out.println("83pre[" + pre + "] rv[" + rv + "]");
            }else{
                rv = temp;
               // System.out.println("86pre[" + pre + "] rv[" + rv + "]");
            }
            word = pre + rv;
        }

        return word;
    }

}