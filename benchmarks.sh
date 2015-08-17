#!/bin/sh

TRIMOU_VERSIONS="1.6.2.Final 1.7.3.Final 1.8.1.Final";
#TRIMOU_VERSIONS="1.6.2.Final";
#TRIMOU_VERSIONS="1.7.3.Final";
#TRIMOU_VERSIONS="1.8.1-SNAPSHOT";

echo $TRIMOU_VERSIONS;
TRIMOU_VERSIONS_ARRAY=$(echo $TRIMOU_VERSIONS);

for i in $TRIMOU_VERSIONS_ARRAY
do
  mvn package -Dversion.trimou=$i
  java -jar target/trimou-benchmarks.jar -rf json -rff target/results-$i.json
  java -cp target/trimou-benchmarks.jar org.trimou.benchmark.chart.ChartGenerator target
done;
