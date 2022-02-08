not=[^lionsthbupycgd]
grep -E -i "^[arm][erm][ea]${not}${not}\$" words.txt
grep -E -i "^[arm][erm]${not}[earm]${not}\$" words.txt
grep -E -i "^[arm][erm]${not}${not}[earm]\$" words.txt
grep -E -i "^[arm]${not}[ea][earm]${not}\$" words.txt
grep -E -i "^[arm]${not}[ea]${not}[earm]\$" words.txt
grep -E -i "^[arm]${not}${not}[earm][earm]\$" words.txt
grep -E -i "^${not}[erm][ea][earm]${not}\$" words.txt
grep -E -i "^${not}[erm][ea]${not}[earm]\$" words.txt
grep -E -i "^${not}[erm]${not}[earm][earm]\$" words.txt
grep -E -i "^${not}${not}[ea][earm][earm]\$" words.txt



