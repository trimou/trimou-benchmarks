#!/bin/sh

#TRIMOU_VERSIONS="1.8.2.Final 1.8.3-SNAPSHOT";
TRIMOU_VERSIONS="1.6.2.Final 1.7.3.Final 1.8.1.Final 1.8.2.Final";

# Set max to use Runtime.getRuntime().availableProcessors()
THREADS="1"

# Benchmarks to run
if [ "$1" ]; then
    BENCHMARKS=$1
else
    BENCHMARKS="Basic|Helper|Partial|TemplateInheritance"
fi

echo "Trimou versions to test: $TRIMOU_VERSIONS";
echo "Benchmarks to run: $BENCHMARKS"

TRIMOU_VERSIONS_ARRAY=$(echo $TRIMOU_VERSIONS);

for i in $TRIMOU_VERSIONS_ARRAY
do
  mvn package -Dversion.trimou=$i
  java -jar target/trimou-benchmarks.jar -t $THREADS -rf json -rff target/results-$i.json $BENCHMARKS
  java -cp target/trimou-benchmarks.jar org.trimou.benchmark.chart.ChartGenerator target
done;
