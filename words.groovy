def words='/usr/share/dict/american-english'

// read in 99k words ...
def aaa= new File(words).readLines()

// remove words with punctuation ...
aaa = aaa.grep { it.indexOf("'")==-1 && it.indexOf('.')==-1 }

// make all words uppercase ...
aaa = aaa.collect { it.toUpperCase() }

// sort words in alphabetical order
aaa = aaa.sort()

// remove duplicate words
aaa = aaa.inject([[],'']) {
  word_list, this_word
  ->
  def prev_word = word_list[1]
  if (prev_word<this_word) {
     word_list[0] << this_word
  }
  word_list[1] = this_word

  return word_list
}[0]

// 71972 word remain !!!



println "aaa.size=${aaa.size()}"

println aaa[0..10]

def max_keyset = [0,[0:''],'']

aaa.eachWithIndex {
  word, i
  ->
  if (i%10==0) println "... $i"
  def bbb = word.split('').countBy { it }
  def ccc = bbb.values().max()
  if (ccc>max_keyset[0]) {
     max_keyset = [ccc,word,bbb]
  }
}

println "max_keyset=$max_keyset"


/* ================================================
def bbb = aaa.groupBy { it.length() }
println "bbb.keyset=${bbb.keySet().sort()}"
// group by word length
bbb.keySet().sort().each {
    int word_len
    ->
    println "$word_len ... ${bbb[word_len].size()}"
    if (word_len>=19) println bbb[word_len]
}
================================================= */
