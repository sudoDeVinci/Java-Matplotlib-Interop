package src;
import java.util.function.Consumer;

/**
 * Pass in a method whioch takes in args to be timed.
 * Then, call the method corresponding to the time scale wanted.
 */
public class Timeit {
    private long start;
    private long stop;
    private Consumer<Object[]> timedMethod;

    /**
     * Take in the method to be measured.s
     * @param m
     */
    public Timeit(Consumer<Object[]> m) {
        this.timedMethod = m;
    }

    /**
     * Measure the execution of the given method in nanoseconds.
     * @param args
     * @return
     */
    public double measureNanos(Object...args) {
        this.start = System.nanoTime();
        timedMethod.accept(args);
        this.stop = System.nanoTime();
        double elapsed = (this.stop - this.start);
        return elapsed;
    }

    /**
     * Measure the execution of the given method in microseconds.
     * @param args
     * @return
     */
    public double measureMicros(Object...args) {
        double elapsed =  measureNanos(args);
        return elapsed/1e3;
    }

    /**
     * Measure the execution of the given method in miliseconds.
     * @param args
     * @return
     */
    public double measureMilis(Object...args) {
        double elapsed =  measureNanos(args);
        return elapsed/1e6;
    }

    /**
     * Measure the execution of the given method in seconds.
     * @param args
     * @return
     */
    public double measureSecs(Object...args) {
        double elapsed =  measureNanos(args);
        return elapsed/1e9;
    }
}
