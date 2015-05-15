package org.trimou.benchmark.chart;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xeiam.xchart.BitmapEncoder;
import com.xeiam.xchart.BitmapEncoder.BitmapFormat;
import com.xeiam.xchart.Chart;
import com.xeiam.xchart.ChartBuilder;
import com.xeiam.xchart.StyleManager.ChartType;

/**
 *
 * @author Martin Kouba
 */
public class ChartGenerator {

    public static void main(String[] args) throws IOException {

        if (args.length == 0) {
            return;
        }

        List<File> files = new ArrayList<File>();
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            File file = new File(arg);
            if (!file.canRead()) {
                throw new IllegalArgumentException("Unable to read the data file: " + file);
            }
            if (file.isFile()) {
                files.add(file);
            } else if (file.isDirectory()) {
                Collections.addAll(files, file.listFiles(new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        return pathname.isFile() && !pathname.isHidden() && pathname.getName().endsWith(".json");
                    }
                }));
            }
        }

        if (files.isEmpty()) {
            return;
        }
        Collections.sort(files, new Comparator<File>() {
            @Override
            public int compare(File f1, File f2) {
                return f1.getName().compareTo(f2.getName());
            }
        });

        Chart chart = new ChartBuilder().chartType(ChartType.Bar).width(1024).height(768).title("Trimou Microbenchmarks").xAxisTitle("Benchmark")
                .yAxisTitle("Ops/s").build();
        chart.getStyleManager().setXAxisTicksVisible(true);

        for (File file : files) {

            JsonArray series = readJsonElementFromFile(file).getAsJsonArray();

            List<String> benchmars = new ArrayList<String>();
            List<BigDecimal> scores = new ArrayList<BigDecimal>();
            List<BigDecimal> errors = new ArrayList<BigDecimal>();

            for (JsonElement jsonElement : series) {
                JsonObject benchmark = jsonElement.getAsJsonObject();
                String benchmarkName = benchmark.get("benchmark").getAsString();
                benchmarkName = benchmarkName.substring(0, benchmarkName.lastIndexOf('.'));
                benchmarkName = benchmarkName.substring(benchmarkName.lastIndexOf('.') + 1, benchmarkName.length());
                benchmars.add(benchmarkName);
                scores.add(benchmark.get("primaryMetric").getAsJsonObject().get("score").getAsBigDecimal());
                errors.add(benchmark.get("primaryMetric").getAsJsonObject().get("scoreError").getAsBigDecimal());
            }
            chart.addSeries(file.getName().replace("results-", "").replace(".json", ""), benchmars, scores, errors);
        }

        // Save as png
        BitmapEncoder.saveBitmap(chart, "./trimou-microbenchmarks", BitmapFormat.PNG);
    }

    static JsonElement readJsonElementFromFile(File inputFile) throws IOException {
        try (Reader reader = Files.newBufferedReader(inputFile.toPath(), Charset.forName("UTF-8"))) {
            JsonParser jsonParser = new JsonParser();
            return jsonParser.parse(reader);
        }
    }

}
