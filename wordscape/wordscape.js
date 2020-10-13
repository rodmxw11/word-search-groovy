const fs = require('fs')
const readline = require('readline')

const MIN_LENGTH = 3
const MAX_LENGTH = 7
const LETTERS_MASK = (1<<26)-1 // 26 ones

const word_list = []
let word_letter_mask = null
let word_count = 0

const A_code = 'A'.charCodeAt(0)

// https://github.com/dwyl/english-words
const ENGLISH_WORDS_FILE = '../groovy/english-words/words_alpha.txt'
const AMERICAN_WORDS_FILE = '/usr/share/dict/american-english'

/**
 * Return a bitmask of letters in a word
 * @param word should be all caps
 * @returns 26 bits; bit 0 is 'A' ... bit 25 is 'Z'
 */
const get_word_mask = word => {
    let mask = 0
    for (let i=0;i<word.length;i++) {
        let code = word.charCodeAt(i)
        mask |= (1<<(code-A_code))
    }
    return mask
}

/**
 * Process the word_list that has been read into memory
 * from the dictionary
 */
const post_process_word_list = () => {
    word_list.sort()

    // for now don't worry about duplicates

    word_count = word_list.length

    word_letter_mask = new Uint32Array(word_count)

    for (let i=0;i<word_count;i++) {
        word_letter_mask[i] = get_word_mask(word_list[i])
    } //endfor i
}

/**
 * Returns true if all of the letters in word_b (mask_b)
 * appear in word_a (mask_a)
 * @param mask_a
 * @param mask_b
 * @returns true if mask_b is subset of mask_a
 */
const isAsubsetB = (mask_a,mask_b) => {
    const letters_not_in_b = LETTERS_MASK & (~mask_b)
    const a_letters_not_in_b = letters_not_in_b & mask_a
    return a_letters_not_in_b===0
}

/**
 * Returns a n array of 26 uint8 integers that represent
 * the letter count in word
 * @param word
 * @returns {Uint8Array}
 */
const getLetterCounts = word => {
    const counts = new Uint8Array(26)
    for (let i=0;i<word.length;i++) {
        counts[word.charCodeAt(i)-A_code]++
    }
    return counts
}

/**
 * Returns true if word_a has enough letters to spell word_b
 * @param a_counts
 * @param b_counts
 * @returns {boolean}
 */
const isLessThanOrEqual = (a_counts,b_counts) => {
    let answer = true
    for (let i=0;i<b_counts.length;i++) {
        answer &= (a_counts[i]<=b_counts[i])
    }
    return answer
}

/**
 * Returns true if word_a has enough letters to spell word_b
 * @param word_a
 * @param word_b
 * @returns {boolean}
 */
const lettersAareinB = (word_a,word_b) => {
    const count_a = getLetterCounts(word_a)
    const count_b = getLetterCounts(word_b)
    return isLessThanOrEqual(count_a,count_b)
}

/**
 * Find wordscape words for a given set of letters
 * @param letters_jumble the letters
 * @param min_len minimun word length being sought
 */
const find_words = (letters_jumble, min_len=3) => {
    const letters = letters_jumble.toUpperCase()
    const mask = get_word_mask(letters_jumble)
    for (let i=0;i<word_count;i++) {
        if (isAsubsetB(word_letter_mask[i],mask)) {
            const found_word = word_list[i]
            if (lettersAareinB(found_word,letters)) {
                if (found_word.length>=min_len) {
                    console.log(found_word)
                } //endif
            } //endif count letters
        } //endif mask
    } //endfor i
}

readline.createInterface({
    input: fs.createReadStream(ENGLISH_WORDS_FILE)
}).on('line',line => {
    const len = line.length
    if (
        len>=MIN_LENGTH && len<=MAX_LENGTH
        && !line.includes("'")
    ) {
        word_list.push(line.toUpperCase())
    }
}).on('close', () => {
    console.log("ALL DONE!!!")
    console.log(`.... found ${word_list.length} words`)
    console.log(`argv[2] = ${process.argv[2]}`)
    post_process_word_list()
    find_words(process.argv[2] || 'treecol', process.argv[3]||3)
    process.exit(0)
})
