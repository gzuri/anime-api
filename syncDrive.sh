#!/bin/bash

storage=$1

echo "Storage: " $storage

json="["
sep=""
for file in */;
do
    file=${file//\\/\\\\}
    file=${file//\"/\\\"}
    file=${file//\'/\\\'}

    printf -v json '%s%s"%s"' "$json" "$sep" "$file"
    sep=,
done
json+="]"


echo $json
curl -H "Content-Type: application/json" -X POST -d '{"storage":"nas","namesOnDisk": ['$json'}' http://localhost:8080/anime/syncDrive

echo "done"