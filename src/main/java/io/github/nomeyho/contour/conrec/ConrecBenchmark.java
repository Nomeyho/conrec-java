package io.github.nomeyho.contour.conrec;

import com.google.common.base.Stopwatch;
import io.github.nomeyho.contour.conrec.model.Level;
import io.github.nomeyho.contour.parser.Data;
import io.github.nomeyho.contour.parser.DataParser;
import org.openjdk.jmh.annotations.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static io.github.nomeyho.contour.utils.LevelUtils.levels;

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
        final Data data = DataParser.read(path);

        final List<Level> levels = Conrec.contour(data.getX(), data.getY(), data.getZ(), levels(data));
        System.out.println("-> Found " + levels.stream().mapToInt(l -> l.getContours().size()).sum() + " contours in " + timer.elapsed().toMillis() + "ms");

        return timer.elapsed();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1, warmups = 1)
    @Warmup(iterations = 1)
    public void init() {
        // Do nothing
    }
}
