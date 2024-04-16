package src;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;

/**
 * A Utility class made for statistical functions and useful static methods.
 */
public class Util {

    
    public static Double[] log2Arr(double intercept, double slope, Integer[] x_axis) {
        // y = intercept * log2(slope * x)
        // y = intercept * (x ^ slope)

        Double[] out = new Double[x_axis.length];
        for(int i = 0; i < x_axis.length; i++) {
            out[i] = intercept * Math.pow(x_axis[i], slope);
        }
        return out;
    } 

    public static Double[] line(double intercept, double slope, Integer[] x_axis) {
        // y = intercept * log2(slope * x)
        // y = intercept * (x ^ slope)

        Double[] out = new Double[x_axis.length];
        for(int i = 0; i < x_axis.length; i++) {
            out[i] = (slope * x_axis[i]) + intercept;
        }
        return out;
    } 

    public static Integer[] seqIntArray(int SIZE, int STEPS, int START) {
        int arraySize = (SIZE - START) / STEPS;
        Integer[] unions = new Integer[arraySize];

        for (int i = 0; i < arraySize; i++) {
            unions[i] = START + i * STEPS;
        }

        return unions;
    }
    
    /**
     * This finds the quartile specified in a list of some number type.
     * The type must be an object, not a primitive.
     * @param <T> Array type, eg: Float, Integer, etc. 
     * @param data
     * @param percentile
     * @return
     */
    public static <T> T Quartile(T[] data, int percentile) {
        int index = (percentile * data.length + 99) / 100 - 1;
        return data[index];
    }

    /**
     * This finds the quartile specified in a list of floats.
     * @param data
     * @param percentile
     * @return
     */
    public static double Quartile(double[] data, int percentile) {
        int index = (percentile * data.length + 99) / 100 - 1;
        return data[index];
    }

    public static double mean(double[] samples)   {
        double sum = 0;
        for(double i: samples) sum+=i; 
        return sum/samples.length;
    }

    public static double stdDev(double[] samples) {
        int n = samples.length;
        double sum = 0.0;
        double mean = mean(samples);

        for (double num: samples) sum += Math.pow(num-mean, 2);

        return Math.sqrt(sum/(n-1));
    }

    /**
     * Find the IQR, remove outliers, then find the average.
     * @param samples
     * @return
     */
    public static double sampleMean(double[] samples) {
        Arrays.sort(samples);
        double q1 = Quartile(samples, 25);
        double q3 = Quartile(samples, 75);
        double iqr = q3 - q1;

        double ub =  q3 + 1.5*iqr;
        double lb =  q1 - 1.5*iqr;

        int count = 0;
        double sum = 0;
    
        for (int i = 0; i < samples.length; i++) {
            double value = samples[i];
            if (value >= lb && value <= ub) {
                sum += value;
                count++;
            }
        }

        return count > 0 ? sum / count : 0;
    }

    public static Double[] sampleMean(double[][] samples) {
        Double [] times = new Double[samples[0].length];
        double[] sampleArr = new double[samples.length];

        for(int i = 0; i < samples[0].length; i++) {
            for (int j=0; j < samples.length; j++) sampleArr[j] = samples[j][i];     
            times[i] = sampleMean(sampleArr);
        }

        return times;
    }

    public static Double[] mean(double[][] samples) {
        Double [] times = new Double[samples[0].length];
        double[] sampleArr = new double[samples.length];

        for(int i = 0; i < samples[0].length; i++) {
            for (int j=0; j < samples.length; j++) sampleArr[j] = samples[j][i];     
            times[i] = mean(sampleArr);
        }

        return times;
    }

    /**
     * Generate an N-long array of bounded x-y coords.
     * @param N
     * @param bound
     * @return
     */
    public static Integer[][] genXYPairs(int N, int bound) {
        if (N < 0 || bound < 0) {
            throw new IllegalArgumentException("N and bound must be non-negative");
        }
        
        Random rand = new Random(42); // Seed for reproducibility
        Integer[][] pairs = new Integer[N][2];
    
        for (int i = 0; i < N; i++) {
            int xCoordinate = rand.nextInt(bound);
            int yCoordinate = rand.nextInt(bound);
            while(yCoordinate == xCoordinate) yCoordinate = rand.nextInt(bound);
    
            pairs[i][0] = xCoordinate;
            pairs[i][1] = yCoordinate;
        }
    
        return pairs;
    }

    public static Integer[][] genXYPairs(int N) {
        if (N < 0) {
            throw new IllegalArgumentException("N must be non-negative");
        }
        
        Random rand = new Random(42); // Seed for reproducibility
        Integer[][] pairs = new Integer[N][2];
    
        for (int i = 0; i < N; i++) {
            int xCoordinate = rand.nextInt();
            int yCoordinate = rand.nextInt();
            while(yCoordinate == xCoordinate) yCoordinate = rand.nextInt();
    
            pairs[i][0] = xCoordinate;
            pairs[i][1] = yCoordinate;
        }
    
        return pairs;
    }

    public static Integer[] genIntArray(int N) {
        if (N < 0) {
            throw new IllegalArgumentException("N and bound must be non-negative");
        }
    
        Random rand = new Random(42); // Seed for reproducibility
        Integer[] arr = new Integer[N];
        for (int i = 0; i < N; i++) {
            int x = rand.nextInt();
    
            arr[i] = x;
        }
    
        return arr;
    }

    public static Integer[] reverse(Integer[] a, int N) 
    { 
        Integer[] b = new Integer[N];
        int j = N; 
        for (int i = 0; i < N; i++) { 
            b[j - 1] = a[i]; 
            j = j - 1; 
        } 
  
        return b;
    }
    
    public static Double[] reverse(Double[] a, int N) 
    { 
        Double[] b = new Double[N];
        int j = N; 
        for (int i = 0; i < N; i++) { 
            b[j - 1] = a[i]; 
            j = j - 1; 
        } 
  
        return b;
    } 

    public static Integer[] copy(Integer[] arr, int n) {
        Integer[] out = new Integer[arr.length-n];
        for(int i = 0; i < out.length; i++) out[i] = arr[i+n];
        return out;
    }

    public static Double[] copy(Double[] arr, int n) {
        Double[] out = new Double[arr.length-n];
        for(int i = 0; i < out.length; i++) out[i] = arr[i+n];
        return out;
    }



    /**
     * Ensure a folder exists.
     * @param path Path to folder
     */
    public static void ensureFolderExists(String path) {
        Path folder = Paths.get(path);
        if (Files.exists(folder) && Files.isDirectory(folder)) {
           return;
        }

        try {
            Files.createDirectories(folder);
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
    }

    public static void writeCSV(String path, String[] headers, String[][] content) {
        try (FileWriter writer = new FileWriter(path)) {
            
            String joined = String.join("\t", headers);
            writer.append(joined); 
            writer.append('\n');

            for (int row =1; row<content.length;row++) {
                joined = String.join("\t", content[row]);
                writer.append(joined);
                writer.append('\n'); 
            }

        } catch (IOException e) { 
            e.printStackTrace();
        }
    }

    public static void writeJson(String path, String json) {
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
