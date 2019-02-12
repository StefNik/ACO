/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package important_classes;

/**
 *
 * @author StefNik
 */
public class Edge {

    private final String id;
    private final Vertex source;
    private final Vertex destination;
    private int weight;
    private double pheromone;

    public Edge(String id, Vertex source, Vertex destination, int weight) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.weight = weight;
        if (this.weight != 0) {
            this.pheromone = 1d / (double) this.weight;
        } else {
            this.pheromone = 1d;
        }
    }

    public String getId() {
        return id;
    }

    public Vertex getDestination() {
        return destination;
    }

    public Vertex getSource() {
        return source;
    }

    public int getWeight() {
        return weight;
    }

    public double getPheromone() {
        return this.pheromone;
    }

    public void depositePheromone(double pheromone) {
        this.pheromone += pheromone;
    }

    //here I can have the latency as length
    public double getLength() {
        return this.weight;
    }

    public void evaporatePheromone(double pheromoneForEvaporation) {
        this.pheromone -= pheromoneForEvaporation;
    }

    public void updatePheromone(double pheromone) {
        this.pheromone = pheromone;
    }

    @Override
    public String toString() {
        return source + " " + destination;
    }

}
