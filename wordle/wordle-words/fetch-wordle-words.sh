#!/bin/bash

# See: https://betterprogramming.pub/forget-luck-optimized-wordle-strategy-using-bigquery-c676771e316f

# set useful vars
GAME="wordle"
RAWGITHUB="https://raw.githubusercontent.com/hannahcode/wordle/"
SOLUTIONS=$RAWGITHUB"main/src/constants/wordlist.ts"
GUESSES=$RAWGITHUB"main/src/constants/validGuesses.ts"
REGEXP="'\K(\w{5})"

# fetch source code files
wget --quiet --output-document=$GAME.solutions $SOLUTIONS
wget --quiet --output-document=$GAME.guesses $GUESSES

# extract words, add type and store in game csv file
grep -Po $REGEXP $GAME.solutions | awk '{print "\""$0"\",\"solution\""}' > $GAME.csv
grep -Po $REGEXP $GAME.guesses | awk '{print "\""$0"\",\"guess\""}' >> $GAME.csv

printf "First three solution words:\n"
head --lines=3 $GAME.csv
printf "\nLast three guess words:\n"
tail --lines=3 $GAME.csv
printf "\nNumber of words per type:\n"
cut --fields=2 --delimiter=, $GAME.csv | uniq --count; wc --lines $GAME.csv
