i=1
for file in /Users/BiruLyu/Downloads/NYD/NYD/*.html
do
	echo "process file no.:$i"
	echo $file
	java -jar tika-app.jar --text $file >> big.txt
	i=$((i+1))
done