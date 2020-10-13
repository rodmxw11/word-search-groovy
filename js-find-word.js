const fs = require('fs')
const util = require('util')

const MIN_LENGTH = 3
const MAX_LENGTH = 7
const A_BYTE = 22
const LETTERS_MASK = (1<<26)-1 // 26 ones

const word_list = []
const word_letter_mask = []
let word_count = 0


// https://github.com/dwyl/english-words
const ENGLISH_WORDS_FILE = './english-words/words_alpha.txt'
const AMERICAN_WORDS_FILE = '/usr/share/dict/american-english'

const readFile = util.promisify(fs.readFile)

readFile(AMERICAN_WORDS_FILE).then(
    data => {
	console.log('starting split data.length='+data.length)
	console.log(typeof data.toString())
	data.toString().split(/\r?\n/mg).forEach(
	    line => {
		const len = line.length
		if (MIN_LENGTH>=len && len<=MAX_LENGTH && line.indexOf("'")<0) {
		    word_list.push( line.toUpperCase() )
		    word_count++
		    console.log('line='+line)
		} //endif
	    } //endarrow line
	) //endforEach
	console.log('!~!!!!!!!!!!! DONE @@@@@@ word_count='+word_count)
    } //endarrow data
).catch( err => {} ) //endthen


