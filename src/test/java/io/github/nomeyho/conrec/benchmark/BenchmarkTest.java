package io.github.nomeyho.conrec.benchmark;

import com.google.common.base.Stopwatch;
import io.github.nomeyho.conrec.Conrec;
import io.github.nomeyho.conrec.model.ConrecLevel;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.stream.Stream;


class BenchmarkTest {

    private static final Path TEST_DATA_DIR = Paths.get("src/test/resources/benchmark-data");
    private static final int NUMBER_LEVELS = 2;
    private static LongSummaryStatistics stats;
    private static Stopwatch globalStopwatch;
    private Stopwatch testStopwatch;

    @BeforeAll
    static void beforeAll() {
        stats = new LongSummaryStatistics();
        globalStopwatch = Stopwatch.createStarted();
    }

    @AfterAll
    static void afterAll() {
        log("Executed benchmark in " + globalStopwatch);
        log(stats);
    }

    @BeforeEach
    void beforeEach() {
        testStopwatch = Stopwatch.createStarted();
    }

    @AfterEach
    void afterEach() {
        stats.accept(testStopwatch.elapsed().toMillis());
    }

    @ParameterizedTest
    @MethodSource("testData")
    void benchmark(BenchmarkData data) {
        final List<ConrecLevel> levels = Conrec.contour(data.getX(), data.getY(), data.getZ(), data.levels(NUMBER_LEVELS));
        log("[" + data.getFilename() + "] Found " + countContours(levels) + " contours in " + testStopwatch.elapsed().toMillis() + "ms");
    }

    private static Stream<Arguments> testData() throws Exception {
        return Files.list(TEST_DATA_DIR)
                .sorted()
                .map(BenchmarkDataParser::read)
                .map(Arguments::of);
    }

    private static void log(Object message) {
        System.out.println(message);
    }

    private static int countContours(final List<ConrecLevel> levels) {
        return levels.stream().mapToInt(l -> l.getContours().size()).sum();
    }
}
