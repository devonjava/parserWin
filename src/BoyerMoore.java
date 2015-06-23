


public class BoyerMoore {

    public BoyerMoore() {

    }
    public Boolean getFirstEntry(String str, String pat) {

        int R = 256;
        int[] right = new int[R];
        for (int c = 0; c < R; c++)
            right[c] = -1;
        for (int j = 0; j < pat.length(); j++)
            right[pat.charAt(j)] = j;

        int M = pat.length();
        int N = str.length();
        int skip;
        for (int i = 0; i <= N - M; i += skip) {
            skip = 0;
            for (int j = M-1; j >= 0; j--) {
                if (pat.charAt(j) != str.charAt(i+j)) {
                    skip = Math.max(1, j - right[str.charAt(i+j)]);
                    break;
                }
            }
            if (skip == 0) return true;    // found
        }
        return false;                       // not found
    }

    public static void main(String[] args) {
        String pat = "auto";
        String txt = "autopoint";
        //pat = pat.toLowerCase();
        //txt = txt.toLowerCase();
       // pat = pat.toLowerCase(); supply :[Group, Super, Supply, UPS, Up, up, FlipUp, group, Tupperware]
       // char[] pattern = pat.toCharArray();
       // char[] text    = txt.toCharArray();

        BoyerMoore boyermoore1 = new BoyerMoore();
        //BoyerMoore boyermoore2 = new BoyerMoore(pattern, 256);
        Boolean offset1 = boyermoore1.getFirstEntry(txt, pat);
        //int offset2 = boyermoore2.search(text);

        // print results
        System.out.print(pat);
        System.out.println("\t" + txt + "?: " + offset1);




       // System.out.print("pattern: ");
      //  for (int i = 0; i < offset2; i++)
  //         System.out.print(" ");
    //    System.out.println(pat);
    }
}