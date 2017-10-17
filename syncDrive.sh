#!/bin/bash

storage=$1

echo "Storage: " $storage

json="["
sep=""
for file in */;
do
    file=${file//\\/\\\\}
    file=${file//\"/\\\"}
    file=${file//\'/} #remove single quotes

    printf -v json '%s%s"%s"' "$json" "$sep" "$file"
    sep=,
done
json+="]"


echo curl -H "Content-Type: application/json" -X POST -d '{"storage":"nas","namesOnDisk":'$json' }' https://af551ca8-7648-4533-9456-e6b44ee1cc09.mock.pstmn.io/anime/syncDrive

echo "done"