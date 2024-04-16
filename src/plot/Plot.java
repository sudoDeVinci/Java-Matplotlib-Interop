package src.plot;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 */
public class Plot<X, Y> {
    private String label = "";
    private X[] x;
    private Y[] y;
    private Type type = Type.NONE;
    private int size = 10;

    public static enum Type {
        LINEAR("linear"),
        EXPONENTIAL("exponential"),
        SCATTER("scatter"),
        LOGARITHMIC("logarithmic"),
        LINE("line"),
        NONE("none");

        private final String type;

        private Type(String type) {
            this.type = type;
        }

        public String toString() {
            return type;
        }
    }

    /**
     * @param x_label
     * @param y_label
     * @param type
     */
    public Plot(String label) {
        this.label = label;
    }

    /**
     * @param x_label
     * @param y_label
     * @param type
     */
    public Plot(Plot.Type t) {
        this.type = t;
    }

    /**
     * @param x_label
     * @param y_label
     * @param type
     */
    public Plot(String label, Plot.Type t) {
        this.label = label;
        this.type = t;
    }

    /**
     * @param x_vals
     * @param y_vals
     */
    public Plot(X[] x_vals, Y[] y_vals) {
        this.x = x_vals;
        this.y = y_vals;
    }

    /**
     * @param t
     * @param x_vals
     * @param y_vals
     */
    public Plot(Plot.Type t, X[] x_vals, Y[] y_vals) {
        this.x = x_vals;
        this.y = y_vals;
        this.type = t;
    }

    /**
     * @param t
     * @param x_vals
     * @param y_vals
     */
    public Plot(String label, Plot.Type t, X[] x_vals, Y[] y_vals) {
        this.label = label;
        this.x = x_vals;
        this.y = y_vals;
        this.type = t;
    }

    /**
     * Change the plot type.
     * @param type
     */
    public void setType(Plot.Type t) {
        this.type = t;
    }

    /**
     * @param x_vals
     */
    public void setX(X[] x_vals) {
        this.x = x_vals;
    }

    /**
     * @param y_vals
     */
    public void setY(Y[] y_vals) {
        this.y = y_vals;
    }

    /**
     * 
     * @param label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String toToml() {
        StringBuilder tomlBuilder = new StringBuilder();
        tomlBuilder.append("[[plots]]\n");
        tomlBuilder.append("label = \"").append(label).append("\"\n");
        tomlBuilder.append("x = ").append(this.x == null ? "null" : Arrays.toString(this.x)).append("\n");
        tomlBuilder.append("y = ").append(this.y == null ? "null" : Arrays.toString(this.y)).append("\n");
        tomlBuilder.append("type = \"").append(type).append("\"\n");
        tomlBuilder.append("size = ").append(this.size).append("\n");
        return tomlBuilder.toString();
    }

    public static void main(String[] args) {
        Plot<Integer, Double> plot = new Plot<>("My Plot", Plot.Type.LINEAR);
        plot.setX(new Integer[]{1, 2, 3, 4, 5});
        plot.setY(new Double[]{1.0, 2.0, 3.0, 4.0, 5.0});

        String json = plot.toToml();
        System.out.println(json);
    }
}