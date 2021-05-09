package io.github.nomeyho.contour.msquare;

import com.google.common.base.Stopwatch;
import io.github.nomeyho.contour.parser.Data;
import io.github.nomeyho.contour.parser.DataParser;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static io.github.nomeyho.contour.utils.LevelUtils.levels;

public class MSquareBenchmark {

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

        MarchingSquare.contour(data.getX(), data.getY(), data.getZ(), levels(data));
        System.out.println("-> Found " + 0 + " contours in " + timer.elapsed().toMillis() + "ms");

        return timer.elapsed();
    }
}
