package io.github.nomeyho.conrec;

import com.google.common.base.Stopwatch;
import io.github.nomeyho.conrec.model.Level;
import io.github.nomeyho.conrec.data.BenchmarkData;
import io.github.nomeyho.conrec.data.BenchmarkDataParser;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ConrecBenchmark {

    // LongSummaryStatistics{count=229, sum=39355, min=51, average=171.855895, max=1765} - 20 contours
    public static void main(String[] args) throws Exception {
        final Path dir = Paths.get("/Users/vanberst/Documents/Workspace/Web/contour-benchmark/src/main/resources/data");
        final List<Path> paths = Files.list(dir).sorted().collect(Collectors.toList());
        final List<Duration> durations = new ArrayList<>();

        for (final Path path : paths) {
            final Duration duration = process(path);
            durations.add(duration);
        }

        System.out.println(durations.stream().mapToLong(Duration::toMillis).summaryStatistics());
    }

    private static Duration process(final Path path) {
        System.out.println("Processing " + path);
        final Stopwatch timer = Stopwatch.createStarted();
        final BenchmarkData data = BenchmarkDataParser.read(path);

        final List<Level> levels = Conrec.contour(data.getX(), data.getY(), data.getZ(), data.levels(20));
        System.out.println("-> Found " + levels.stream().mapToInt(l -> l.getContours().size()).sum() + " contours in " + timer.elapsed().toMillis() + "ms");

        return timer.elapsed();
    }
}
