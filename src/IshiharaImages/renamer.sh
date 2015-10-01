for file in *.jpg
do
  mv "$file" "${file/-38/}"
done
