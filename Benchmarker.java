import java.util.Map;
import java.util.HashMap;

public class Benchmarker {
    Map<String, Benchmark> benchmarks= new HashMap<String, Benchmark>();
    String lastStarted;
    Benchmarker(){}
    public void start(String benchmarkName){
        if(benchmarks.containsKey(benchmarkName)){
            System.err.printf("Benchmark %s is already running\n",benchmarkName);
            return;
        }
        Benchmark newBenchmark = new Benchmark();
        benchmarks.put(benchmarkName, newBenchmark);


        // Start time recorded at the very end of the function to ensure execution as near as possible to next line
        newBenchmark.start = System.nanoTime();
    }
    public void end(String benchmarkName){
        // End time recorded at the very beginning of the function to ensure execution as near as possible to previous line
        long endTime = System.nanoTime();
        if(!benchmarks.containsKey(benchmarkName)){
            System.err.printf("Benchmark %s has not started\n",benchmarkName);
            return;
        }

        Benchmark toEnd = benchmarks.get(benchmarkName);
        if(toEnd.ended){
            System.err.printf("Benchmark %s has already ended\n",benchmarkName);
            return;
        }

       toEnd.end=endTime;
       toEnd.ended=true;
    }
    public long getBenchmark(String benchmarkName){
        if(!benchmarks.containsKey(benchmarkName)){
            System.err.printf("Benchmark %s has not started\n",benchmarkName);
            return -1;
        }
        Benchmark benchmark = benchmarks.get(benchmarkName);
        return benchmark.end-benchmark.start;
    }
}

class Benchmark{
    long start=Long.MIN_VALUE;
    long end=Long.MIN_VALUE;
    boolean ended=false;
    Benchmark(long start,long end){
        this.start=start;
        this.end=end;
    };
    Benchmark(long start){
        this.start=start;
    };
    Benchmark(){

    };
    // System
}