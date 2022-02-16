# 20220209: HUMOR : earth, lions
tfile=wordle.TMP
not=[^rthliondcky]
YES=[easu]
echo '' > $tfile
grep -E -i "^${not}${not}${YES}${YES}${YES}\$" words.txt >> $tfile  # 00111
grep -E -i "^${not}${YES}${not}${YES}${YES}\$" words.txt >> $tfile  # 01011
grep -E -i "^${not}${YES}${YES}${not}${YES}\$" words.txt >> $tfile  # 01101
grep -E -i "^${not}${YES}${YES}${YES}${not}\$" words.txt >> $tfile  # 01110
grep -E -i "^${YES}${not}${not}${YES}${YES}\$" words.txt >> $tfile  # 10011
grep -E -i "^${YES}${not}${YES}${not}${YES}\$" words.txt >> $tfile  # 10101
grep -E -i "^${YES}${not}${YES}${YES}${not}\$" words.txt >> $tfile  # 10110
grep -E -i "^${YES}${YES}${not}${not}${YES}\$" words.txt >> $tfile  # 11001
grep -E -i "^${YES}${YES}${not}${YES}${not}\$" words.txt >> $tfile  # 11010
grep -E -i "^${YES}${YES}${YES}${not}${not}\$" words.txt >> $tfile  # 11100

cat $tfile | grep -v -i "\." | grep -i "e" | grep -i "a" | grep -i "s" | grep -i "u" | \
    grep -i "[^e]a..[^s]" | sort | uniq




