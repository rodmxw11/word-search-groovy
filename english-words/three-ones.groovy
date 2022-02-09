// all 5 digit binary numbers
for (int i=0;i<32;i++) {
  String digits = Integer.toBinaryString(32+i).substring(1)
  int one_count = digits.replaceAll('0','').length()
  if (one_count==3) {
     println "$one_count : $digits"
  }
}