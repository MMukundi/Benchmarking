import java.util.Map;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Random;
import java.util.Stack;

public class Benchmarker {
    public static void main(String[] args) {
        Benchmarker b = new Benchmarker();
        int x = 0;
        int y = 0;

        int n = 658116;

        //I was unsure about whether comparisons or assignments took longer, so I wrote up this little benchmark that tests just that
        //(here assignments are used for list traversal)

        // This method simulates one that traverses the entire current length of the list every iteration, but only performs log_2(n) comparisons
        // It is inspired by merge sort, which runs in log_n time
        b.start("checkFewQuitLate");
        int step = n / 2;
        for (int j = 0; j < n; j++, step /= 2)
            if (j < step)
                for (int i = 0; i < step; i++)
                    x=y;
            else
                for (int i = 0; i < step; i++)
                    y=x;
        b.end();

        // This method simulates only travels as much of the list as needed every iteration, but performs a comparison at every single step
        b.start("checkEveryQuitEarly");
        for (int j = 0; j < n; j++, step /= 2)
            for (int i = 0; i < j; i++)
                x=y;
        b.end();

        // Analysis: with the knowledge that a comparison is what bogs you down, it is no surprise which algorithm wins;
        // algorithm 1's worst case is n assignments, logn comparisons
        // algorithm 2's worst case is n assignments, n comparisons
        System.out.printf("%d,%d", b.getBenchmark("checkFewQuitLate"), b.getBenchmark("checkEveryQuitEarly"));
    }

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
        if (lastStarted.empty()) {
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

    // While possibly useful, I found would involve a "last ended" stack

    // public long getBenchmark() {
    // String lastEndedName = lastEnded.peek();
    // if (!benchmarks.containsKey(lastEndedName)) {
    // System.err.printf("Benchmark %s has not started\n", lastEndedName);
    // return -1;
    // }
    // Benchmark benchmark = benchmarks.get(lastEndedName);
    // if (!benchmark.ended) {
    // System.err.printf("Benchmark %s is still running\n", lastEndedName);
    // return -1;
    // }
    // return benchmark.end - benchmark.start;
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