
import groovy.transform.CompileStatic
/**
Saturday 16 February 2019

This is a program to help solve Fleur's favorite Wordscapes app
*/

@CompileStatic
class find_words {

  
   
   // https://github.com/dwyl/english-words
   final static String ENGLISH_WORDS_FILE = './english-words/words_alpha.txt'


   final static String AMERICAN_WORDS_FILE = '/usr/share/dict/american-english'
   final static int MIN_LENGTH = 3
   final static int MAX_LENGTH = 7
   final static byte A_byte = 'A'.bytes[0]
   final static int LETTERS_MASK = (1<<26)-1 // 26 ones

   static List<String> word_list = new ArrayList<String>()
   static int word_count = 0
   static int[] word_letter_mask = null

   static final int getWordMask(String word) {
      int mask = 0
      byte[] word_bytes = word.getBytes('US-ASCII')
      for (int j=0;j<word_bytes.length;j++) {
	    mask |= (1<<(word_bytes[j]-A_byte))
      } //endfor j
      return mask
   }

   static final boolean isAsubsetB(int mask_a, int mask_b) {
      int letters_not_in_b = LETTERS_MASK & (~mask_b)
      int a_letters_not_in_b = letters_not_in_b & mask_a
      return a_letters_not_in_b==0
   }


  static final byte[] getLetterCounts(String word) {
     byte[] counts = new byte[26]
     byte[] word_bytes = word.getBytes('US-ASCII')
     for (int i=0;i<word_bytes.length;i++) {
         counts[word_bytes[i]-A_byte]++
     }
     return counts
  }

  static final boolean isLessthanOrEqual(byte[] a_counts, byte[] b_counts) {
     boolean answer = true
     for (int i=0;i<b_counts.length;i++) {
        answer &= (a_counts[i]<=b_counts[i])
     }
     return answer
  }

  static boolean lettersAareinB(String word_a, String word_b) {
     byte[] count_a = getLetterCounts(word_a)
     byte[] count_b = getLetterCounts(word_b)
     return isLessthanOrEqual(count_a, count_b)
  }
   
   static void get_words_3_to_7(String filename) {
      // read in and filter the words ...
      new File(filename).eachLine {
         String word
	 ->
	 int len = word.length()
	 if (
	      len<MIN_LENGTH || len>MAX_LENGTH
	      || word.indexOf("'")>=0
	    ) {
	    return
	 }

         word_list << word.toUpperCase()
	 
      } //endeachline

      word_list = word_list.sort()

      // for now don't worry about duplicates

      word_count = word_list.size()

      word_letter_mask = new int[word_count]

      for (int i=0;i<word_count;i++) {
         word_letter_mask[i] = getWordMask(word_list[i])
      } //endfor i
   }

  

   public static void main(String[] args) {
      // these are the letters in the puzzle
      String letters = args[0].toUpperCase()

      int min_len = 3
      if (args.length>1) {
         min_len = Integer.parseInt(args[1])
      }

      String filename = AMERICAN_WORDS_FILE
      if (args.length>2) {
         filename = ENGLISH_WORDS_FILE
      }

      // puzzle mask
      int mask = getWordMask(letters)

      get_words_3_to_7(filename)

      for (int i=0;i<word_count;i++) {
         if (isAsubsetB(word_letter_mask[i],mask)) {
	    String found_word = word_list[i]
	    if (lettersAareinB(found_word,letters)) {
	       if (found_word.length()>=min_len) {
	          println found_word
	       } //endif min_len
	    } //endif count letters
	 } //endif
      } //endfor i
   }
}