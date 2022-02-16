package org.unitapps.wordle

import groovy.transform.CompileStatic

/**
 Encode each 5 letter word in a Java Long (64 bits), along with some meta data about the word:
 <ul>
 <li>25 bits for word characters; 5 letters/word * 5 bits/letter
 <li>26 bits for a letter mask
 <li> 3 bits for a unique letter count == number of bits in the letter mask
 <li> 1 bit for a solution|guess flag
 </ul>
 */
@CompileStatic
class WordEncode {
	// a..z 26 letters < 32 = 2^5, 5 bits/letter, 5 letters/word, 25 bits/word
	// 26 bits for letter mask
	// 25+26 = 51 bits < 64 bits/long, 64-51 = 13 bits left over

	static final int
			LETTER_SET_OFFSET = 0,
			LETTER_SET_SIZE   = 32,
			CHAR_BITS         = 5,
			CHAR4_OFFSET      = LETTER_SET_SIZE,
			CHAR3_OFFSET      = CHAR4_OFFSET+CHAR_BITS,
			CHAR2_OFFSET      = CHAR3_OFFSET+CHAR_BITS,
			CHAR1_OFFSET      = CHAR2_OFFSET+CHAR_BITS,
			CHAR0_OFFSET      = CHAR1_OFFSET+CHAR_BITS

	static final long
			LETER_SET_MASK    = 0x3FFFFFFL, // 26/4 == 6 rem 2
			CHAR_MASK         = 0x1FL, // 5 bits
			CHAR4_MASK        = CHAR_MASK << CHAR4_OFFSET,
			CHAR3_MASK        = CHAR_MASK << CHAR3_OFFSET,
			CHAR2_MASK        = CHAR_MASK << CHAR2_OFFSET,
			CHAR1_MASK        = CHAR_MASK << CHAR1_OFFSET,
			CHAR0_MASK        = CHAR_MASK << CHAR0_OFFSET

	static final String wordle_csv = './wordle-words/wordle.csv'
	static final byte A_BYTE = (byte)('a' as char)

	static final int lettersSet(byte[] letters) {
		int l_set = 0
		letters.each {
			byte letter
			->
				l_set  |= letter - A_BYTE
		}
		return l_set
	}

	static final int lettersSet(String word) {
		return lettersSet(word.toLowerCase().bytes)
	}

	static final long encodeLetters(byte[] letters) {
		assert letters.length==5
		long encoded = 0L
		encoded |= ((long)(letters[0] - A_BYTE))<<CHAR0_OFFSET
		encoded |= ((long)(letters[1] - A_BYTE))<<CHAR1_OFFSET
		encoded |= ((long)(letters[2] - A_BYTE))<<CHAR2_OFFSET
		encoded |= ((long)(letters[3] - A_BYTE))<<CHAR3_OFFSET
		encoded |= ((long)(letters[4] - A_BYTE))<<CHAR4_OFFSET
		return encoded
	}

	static final byte[] decodeLetters(long encoded) {
		byte[] chars = new byte[5];
		chars[0] = (byte)((encoded & CHAR0_MASK)>>CHAR0_OFFSET)+A_BYTE
		chars[1] = (byte)((encoded & CHAR1_MASK)>>CHAR1_OFFSET)+A_BYTE
		chars[2] = (byte)((encoded & CHAR2_MASK)>>CHAR2_OFFSET)+A_BYTE
		chars[3] = (byte)((encoded & CHAR3_MASK)>>CHAR3_OFFSET)+A_BYTE
		chars[4] = (byte)((encoded & CHAR4_MASK)>>CHAR4_OFFSET)+A_BYTE
		return chars
	}
	
	static long encodeWord(String word) {
		byte[] bytes = word.toLowerCase().bytes
		int word_len = bytes.length
		assert word_len==5 // all wordle words are 5 chars long

		long encoded = encodeLetters(bytes) | lettersSet(bytes)
		return encoded
	}


}