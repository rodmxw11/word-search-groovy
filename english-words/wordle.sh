letters=[^ruosdghbn]
grep -E -i "^e${letters}${letters}at\$" words.txt
grep -E -i "^${letters}e${letters}at\$" words.txt
grep -E -i "^${letters}${letters}eat\$" words.txt

