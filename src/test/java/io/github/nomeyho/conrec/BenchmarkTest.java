package io.github.nomeyho.conrec;

import com.google.common.base.Stopwatch;
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

import static org.junit.jupiter.api.Assertions.assertEquals;


class BenchmarkTest {

    private static final Path TEST_DATA_CSV = Paths.get("src/test/resources/benchmark-data.csv");
    private static final int NUMBER_LEVELS = 20;
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
    void benchmark(BenchmarkData data, int expectedNumberContours) {
        final List<ConrecLevel> levels = Conrec.contour(data.getX(), data.getY(), data.getZ(), data.levels(NUMBER_LEVELS));
        final int numberContours = numberContours(levels);
        log("[" + data.getFilename() + "] Found " + numberContours + " contours in " + testStopwatch.elapsed().toMillis() + "ms");
        assertEquals(expectedNumberContours, numberContours);
    }

    private static Stream<Arguments> testData() throws Exception {
        return Files.readAllLines(TEST_DATA_CSV)
                .stream()
                .skip(1)
                .map(BenchmarkTest::testDataArgument);
    }

    private static Arguments testDataArgument(final String line) {
        final String[] tokens = line.split(", ");
        final Path path = TEST_DATA_CSV.getParent().resolve("benchmark-data").resolve(tokens[0]);

        final BenchmarkData testData = BenchmarkDataParser.read(path);
        final int expectedCount = Integer.parseInt(tokens[1]);

        return Arguments.of(testData, expectedCount);
    }

    private static void log(Object message) {
        System.out.println(message);
    }

    private static int numberContours(final List<ConrecLevel> levels) {
        return levels.stream().mapToInt(l -> l.getContours().size()).sum();
    }
}
