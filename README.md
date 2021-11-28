# Conrec Algorithm for Java 
> An implementation of the Conrec Algorithm (http://paulbourke.net/papers/conrec/) for Java

## Description
In contrast to most implementation, this implementation doesn't rely on a drawing method.
Instead, `ConrecContour` objects are optimally instantiated and filled with the points making up the contour.
This allows the contours to be exchanged, for example, between a server and a browser.

## Usage
```
double[] x, y, z = ...
double[] levels = ...

// Compute the iso-contours for some levels
List<ConrecLevel> levels = Conrec.contour(x, y, z, levels);

// Get the computed contours at the first level
List<ConrecContour> contours = levels.get(0).getContours();

// Get the points composing the first contour
List<ConrecPoint> points = contours.get(0).getPoints();
```

## Benchmark
Execution time on a set of 200+ real-world data ranging from 500K to 4M data points (20 levels).
| Number of levels | Count | Sum | Min | Average | Max |
| ----- | ----- | ----- | ----- | ----- | ----- |
| 20  | 237 | 25s | 36ms | 105ms | 374ms |
| 50  | 237 | 30s | 36ms | 127ms | 1025ms |
| 100 | 237 | 34s | 38ms | 229ms | 963ms |
See `BenchmarkTest.java` for more details.

## TODO
* Improve unit test coverage
* Publish to Maven Central
