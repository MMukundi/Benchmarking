import java.util.Map;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Random;
import java.util.Stack;

public class Benchmarker {
    // Random Character Generator
    static Random RCG = new Random();
    Map<String, Benchmark> benchmarks = new HashMap<String, Benchmark>();
    Stack<String> lastStarted = new Stack<String>();

    Benchmarker() {
    }

    public void start(String benchmarkName) {
        if (benchmarks.containsKey(benchmarkName)) {
            System.err.printf("Benchmark %s is already running\n", benchmarkName);
            return;
        }
        Benchmark newBenchmark = new Benchmark();
        benchmarks.put(benchmarkName, newBenchmark);

        // Start time recorded at the very end of the function to ensure execution as
        // near as possible to next line
        lastStarted.push(benchmarkName);
        newBenchmark.start = System.nanoTime();
    }

    public void end(String benchmarkName) {
        // End time recorded at the very beginning of the function to ensure execution
        // as near as possible to previous line
        long endTime = System.nanoTime();
        if (!benchmarks.containsKey(benchmarkName)) {
            System.err.printf("Benchmark %s has not started\n", benchmarkName);
            return;
        }
        Benchmark toEnd = benchmarks.get(benchmarkName);
        if (toEnd.ended) {
            System.err.printf("Benchmark %s has already ended\n", benchmarkName);
            return;
        }
        lastStarted.remove(benchmarkName);

        toEnd.end = endTime;
        toEnd.ended = true;
    }

    public long getBenchmark(String benchmarkName) {
        if (!benchmarks.containsKey(benchmarkName)) {
            System.err.printf("Benchmark %s has not started\n", benchmarkName);
            return -1;
        }
        Benchmark benchmark = benchmarks.get(benchmarkName);
        if (!benchmark.ended) {
            System.err.printf("Benchmark %s is still running\n", benchmarkName);
            return -1;
        }
        return benchmark.end - benchmark.start;
    }

    public String start() {
        // Generates a pseudo random newString
        StringWriter newString = new StringWriter();
        for (int i = 0; i < 10; i++) {
            newString.append((char) ('a' + RCG.nextInt(26)));
        }
        String s = newString.toString();

        start(s);
        return s;
    }

    public void end() {
        // End time recorded at the very beginning of the function to ensure execution
        // as near as possible to previous line
        long endTime = System.nanoTime();
        if(lastStarted.empty()){
            System.err.printf("No active benchmarks\n");
            return;
        }
        String toEndName = lastStarted.pop();
        if (!benchmarks.containsKey(toEndName)) {
            System.err.printf("Benchmark %s has not started\n", toEndName);
            return;
        }

        Benchmark toEnd = benchmarks.get(toEndName);
        if (toEnd.ended) {
            System.err.printf("Benchmark %s has already ended\n", toEndName);
            return;
        }

        toEnd.end = endTime;
        toEnd.ended = true;
    }

    //While possibly useful, I found would involve a "last ended" stack

    // public long getBenchmark() {
    //     String lastEndedName = lastEnded.peek();
    //     if (!benchmarks.containsKey(lastEndedName)) {
    //         System.err.printf("Benchmark %s has not started\n", lastEndedName);
    //         return -1;
    //     }
    //     Benchmark benchmark = benchmarks.get(lastEndedName);
    //     if (!benchmark.ended) {
    //         System.err.printf("Benchmark %s is still running\n", lastEndedName);
    //         return -1;
    //     }
    //     return benchmark.end - benchmark.start;
    // }
}

class Benchmark {
    long start = Long.MIN_VALUE;
    long end = Long.MIN_VALUE;
    boolean ended = false;

    Benchmark(long start, long end) {
        this.start = start;
        this.end = end;
    };

    Benchmark(long start) {
        this.start = start;
    };

    Benchmark() {

    };
    // System
}