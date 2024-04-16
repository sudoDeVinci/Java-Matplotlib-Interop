package src.plot;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import src.Util;

public class Plotter <X, Y> {
    private List<Plot<X, Y>> plots = new ArrayList<>();
    private String x_label = "_";
    private String y_label = "_";
    private String title = "_";
    private String name = "_";
    private int fontsize = 10;
    private String ROOT  = "src";
    private String SEP = File.separator;
    private String SAVES = ROOT + SEP + "saves";
    private String SCRIPTS = ROOT + SEP + "scripts";
    private final String PLOTSCRIPT = SCRIPTS + SEP + "pyplot.py";
    private String GRAPHS = ROOT + SEP + "graphs";

    /**
     * 
     */
    public Plotter() {
        ;
    }

    /**
     * @param filename
     */
    public Plotter(String filename) {
        this.name = filename;
    }

    /**
     * @param plot
     */
    public Plotter(Plot<X, Y> plot) {
        this.plots.add(plot);
    }

    /**
     * @param plot
     * @param filename
     */
    public Plotter(Plot<X, Y> plot, String filename) {
        this.plots.add(plot);
        this.name = filename;
    }

    /**
     * @param plotlist
     */
    public void add(List<Plot<X, Y>> plotlist) {
        for (Plot<X, Y> p : plotlist) this.plots.add(p);
    }

    /**
     * @param p
     */
    public void add(Plot<X, Y> p) {
        this.plots.add(p);
    }

    /**
     * change the x label.
     * @param xLabel
     */
    public void setXLabel(String xLabel) {
        this.x_label = xLabel;
    }

    /**
     * change y label.
     * @param yLabel
     */
    public void setYLabel(String yLabel) {
        this.y_label = yLabel;
    }

    /**
     * Change the title.
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @param filename
     */
    public void setName(String filename) {
        this.name = filename;
    }

    public void setFontSize(int fontsize) {
        this.fontsize = fontsize;
    }

    private String getKeyword() {
        String osName = System.getProperty("os.name").toLowerCase();

        String KEYWORD = 
            osName.contains("windows") ? "python" :
            osName.contains("mac") ? "python3" :
            osName.contains("nix") || osName.contains("nux") ? "python3" :
            "python";

        return KEYWORD;
        
    }

    public String toToml() {
        StringBuilder toml = new StringBuilder();
        toml.append("name = \"").append(this.name).append("\"");
        toml.append("\ntitle = \"").append(this.title).append("\"");
        toml.append("\nx_label = \"").append(this.x_label).append("\"");
        toml.append("\ny_label = \"").append(this.y_label).append("\"\n");
        toml.append("fontsize = ").append(this.fontsize).append("\n");
        for (int i = 0; i < plots.size(); i++) {
            Plot<X, Y> plot = plots.get(i);
            toml.append("\n").append(plot.toToml());
        }
        return toml.toString();
    }

    /**
     * @param filename
     */
    public void write(String filename) {
        String path = this.SAVES + this.SEP + filename + ".toml";
        System.out.println("Path is: " + path);
        Util.ensureFolderExists(this.SAVES);
        Util.ensureFolderExists(this.GRAPHS);
        
        File file = new File(path);
        try {
            if (!file.exists()) {
                System.out.println("Didn't exist!");
                file.createNewFile();
            } else {
                System.out.println("It exists!");
            }
        } catch (Exception e) {
            System.out.println("BAD");
            return;
        }
        try (FileWriter fileWriter = new FileWriter(file)){
            fileWriter.write(this.toToml());
            System.out.println("State Json file has been written successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     */
    public void write() {
        this.write(this.name);          
    }

    public void plot() {
        this.write();

        String savePath = this.SAVES + this.SEP + this.name + ".toml";
        String graphPath = this.GRAPHS + this.SEP + this.name + ".png";

        String[] command = new String[] {getKeyword(), this.PLOTSCRIPT, savePath, graphPath};
        System.out.println(Arrays.toString(command));

        try {
            ProcessBuilder pBuilder = new ProcessBuilder(command);
            pBuilder.redirectErrorStream(true);
            Process p = pBuilder.start();

            try(BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))){
                String line;

                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }

                reader.close();

                int exitCode = p.waitFor();
                System.out.println("Python script exited with code: " + exitCode);
            } catch (IOException e) {
                return ;
            }
        
        } catch (IOException e) {
                e.printStackTrace();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Waiting for process interrupted: " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        Plotter<Integer, Double> graph = new Plotter<Integer, Double>("Plot");
        graph.setTitle("Test Plot");
        graph.setXLabel("Test X Label");
        graph.setYLabel("Test Y Label");
        graph.setFontSize(20);
        Plot<Integer, Double> p1 = new Plot<Integer, Double>("Plot01", Plot.Type.LINEAR, new Integer[]{1, 2, 3, 4, 5}, new Double[]{1.0, 2.0, 3.0, 4.0, 5.0});
        p1.setSize(40);
        Plot<Integer, Double> p2 = new Plot<Integer, Double>("Plot02", Plot.Type.EXPONENTIAL, new Integer[]{1, 2, 3, 4, 5}, new Double[]{1.0, 4.0, 9.0, 16.0, 25.0});
        p2.setSize(40);
        graph.add(p1);
        graph.add(p2);
        graph.plot();
    }
}
