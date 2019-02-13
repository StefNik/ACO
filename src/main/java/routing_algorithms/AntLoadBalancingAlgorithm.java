/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package routing_algorithms;

import important_classes.Edge;
import important_classes.Graph;
import important_classes.Vertex;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.DoubleStream;

/**
 *
 * @author StefNik auto einai to teleutaio version
 */
public class AntLoadBalancingAlgorithm {

    private final List<Vertex> nodes;
    private final List<Edge> edges;
    private final int alphaVal;
    private final int numOfAnts;

    private final int numOfEdges;

    private Set<Vertex> settledNodes;
    private Set<Vertex> unSettledNodes;
    private ArrayList<ArrayList<Edge>> pathOfEachAnt = new ArrayList<>();

    private int katiKainourgio = 1;

    public AntLoadBalancingAlgorithm(Graph graph, int alphaVal, int numOfAnts) {
        // create a copy of the array so that we can operate on this array
        this.nodes = new ArrayList<Vertex>(graph.getVertexes());
        this.edges = new ArrayList<Edge>(graph.getEdges());
        this.alphaVal = alphaVal;
        this.numOfAnts = numOfAnts;
        //this.numOfEdges = (int) graph.getEdges().stream().filter(p -> p.getId().contains("a")).count();
        this.numOfEdges = graph.getEdges().size() / 2;
    }

    public AntLoadBalancingAlgorithm(Graph graph, int alphaVal) {
        // create a copy of the array so that we can operate on this array
        this.nodes = new ArrayList<Vertex>(graph.getVertexes());
        this.edges = new ArrayList<Edge>(graph.getEdges());
        this.alphaVal = alphaVal;
        this.numOfAnts = 10;
        //this.numOfEdges = (int) graph.getEdges().stream().filter(p -> p.getId().contains("a")).count();
        this.numOfEdges = graph.getEdges().size() / 2;
    }

    public AntLoadBalancingAlgorithm(Graph graph) {
        this.nodes = new ArrayList<Vertex>(graph.getVertexes());
        this.edges = new ArrayList<Edge>(graph.getEdges());
        this.alphaVal = 1;
        this.numOfAnts = 100;
        //this.numOfEdges = (int) graph.getEdges().stream().filter(p -> p.getId().contains("a")).count();
        this.numOfEdges = graph.getEdges().size() / 2;
    }

    public LinkedList<Vertex> executeAndGetPath(Vertex source, Vertex destination) {

        for (int k = 0; k < 100; k++) {
            for (int j = 0; j < this.numOfAnts; j++) {
                pathOfEachAnt.add(j, new ArrayList<>());
                Vertex node = source;
                int count = 0;
                boolean isThePathCyclic = false;
                do {
                    List<Vertex> adjacentNodes = getNeighbors(node);
                    for (Vertex ver : adjacentNodes) {
                        System.out.println("The neighbour of " + node.getId() + " is " + ver.getId());
                    }

                    HashMap< Edge, Double> pheromoneOfEachPath = new HashMap<Edge, Double>();
                    HashMap< Edge, Double> transitionProbsOfVertex = new HashMap<Edge, Double>();

                    double sum = 0d; //here are sumed all the pheromone values in the power of alpha
                    //for each adjancent node return the edge that conencts it to the current node
                    //and return the pheromone of the path
                    //and then store it to the HashMap `pheromoneOfEachPath` allong with the pheromone value
                    //finally, sum it to the total pheromone depocited in the edges connecting the current node
                    //to others
                    for (Vertex v : adjacentNodes) {
                        // System.out.println("the pheromone in this path is: " + getPheromoneConcentration(node, v));
                        Edge temp = this.getEdgeConnectingTwoNodes(node, v);
                        pheromoneOfEachPath.put(temp, temp.getPheromone());
                        sum += Math.pow(temp.getPheromone(), this.alphaVal);
                    }
                    //  System.out.println("the total sum of the pheromones in the power of " + this.alphaVal + " is: " + sum);
                    //in this loop calculate the transition probability for all the edges
                    for (Map.Entry<Edge, Double> entry : pheromoneOfEachPath.entrySet()) {
                        transitionProbsOfVertex.put(entry.getKey(), Math.pow(entry.getValue(), this.alphaVal) / sum);
                        // System.out.println("The transition probability of the edge with source-> " + entry.getKey().getSource().getId() + " and destination-> " + entry.getKey().getDestination().getId() + " is: " + Math.pow(entry.getValue(), this.alphaVal) / sum + " " + entry.getValue() + " " + sum);
                    }

                    double p = Math.random();
                    double cumulativeProbability = 0.0d;
                    for (Map.Entry<Edge, Double> entry : transitionProbsOfVertex.entrySet()) {
                        cumulativeProbability += entry.getValue();
                        //System.out.println("the transition prob is: " + entry.getValue() + "the cumulative prop is:" + cumulativeProbability + " the prob is: " + p);
                        if (p <= cumulativeProbability) {

                            System.out.println("the selected edge is: " + entry.getKey().getDestination().getId());

                            pathOfEachAnt.get(j).stream().forEach(item -> System.out.print(item.getDestination().getId() + " "));
                            isThePathCyclic = pathOfEachAnt.get(j).stream().anyMatch(item -> {
                                return item.getSource().getId().equals(entry.getKey().getDestination().getId());
                            });

                            if (isThePathCyclic) {
                                break;
                            }
                            System.out.println("\nkai allo monopati prostethhke sth lush: " + node + " -> " + entry.getKey().getDestination().getId());
                            pathOfEachAnt.get(j).add(entry.getKey());
                            node = entry.getKey().getDestination();

                            break;
                        }
                    }
                    /*
                    if (count == 1) {
                        System.exit(1);
                    }
                     */

                    if (isThePathCyclic) {
                        System.out.println("The path is cyclic!!!");
                        //kill this ant
                        break;
                    }
                    //if the path has more edges than the graph kill the ant
                    if (count == numOfEdges + (int) numOfEdges / 3) {
                        break;
                        //System.exit(1);
                    }
                    count++;
                } while (!node.equals(destination));
                if (isThePathCyclic) {
                    System.out.println("The path is cyclic!!!");
                    //kill this ant
                    continue;
                }
                double totalLengthOfThePath = 0d;
                System.out.println("\n\nTo solution apo to ant: " + j + " einai:");
                System.out.print(pathOfEachAnt.get(j).get(0).getSource().getId() + "->" + pathOfEachAnt.get(j).get(0).getDestination().getId() + "->");
                for (int i = 1; i < pathOfEachAnt.get(j).size() - 1; i++) {
                    System.out.print(pathOfEachAnt.get(j).get(i).getDestination().getId() + "->");
                    totalLengthOfThePath += pathOfEachAnt.get(j).get(i).getLength();
                }

                final double depositedpheromone = 1 / totalLengthOfThePath;
                System.out.print(pathOfEachAnt.get(j).get(pathOfEachAnt.get(j).size() - 1).getDestination().getId());
                System.out.println();

                /*
                SOSOSOSOSOS: kane update th pheromonh gia to kathe path edw kai meta meiwse th 
                             pheromonh sta edges pou den peiran meros mesa sto path
                 */
                //find mean and median pheromone in the edges
                Double mu = 0d;
                for (Edge ed : edges) {
                    mu += ed.getPheromone();
                    System.out.println("The pheromone in the edge with source node->" + ed.getSource().getId() + " and destination node->" + ed.getDestination().getId() + " is: " + ed.getPheromone());
                }
                mu = mu / (double) edges.size();

                System.out.println("\nThe mean value of the deposited pheromone in the graph is: " + mu);

                //find median pheromone in the edges
                DoubleStream sortedEdges = edges.stream().mapToDouble(Edge::getPheromone).sorted();
                double median = edges.size() % 2 == 0
                        ? sortedEdges.skip(edges.size() / 2 - 1).limit(2).average().getAsDouble()
                        : sortedEdges.skip(edges.size() / 2).findFirst().getAsDouble();

                double valueOfEvaporatedPheromone = median / 100;
                System.out.println("the value of the evaporated pheromone is: " + valueOfEvaporatedPheromone);

                System.out.println("the value of the evaporated pheromone is: " + valueOfEvaporatedPheromone);

                //deposit pheromone in the path
                pathOfEachAnt.get(j).forEach(ed -> {
                    ed.depositePheromone(depositedpheromone);
                });

                //evaporate pheromone from all the edges
                edges.forEach((ed) -> {
                    //ed.evaporatePheromone(valueOfEvaporatedPheromone);
                    ed.evaporatePheromone(0.0001);
                });

            } // each ant path
        } // run the algorithm 50 times 
        return null;
    }

    //epistrefei olous tis geitonikes korufes
    //blepei pies akmes pou periexoun th dothhsa korufh katalhgoun se alles korufes
    private List<Vertex> getNeighbors(Vertex node) {
        List<Vertex> neighbors = new ArrayList<Vertex>();
        for (Edge edge : edges) {
            if (edge.getSource().equals(node)) {
                neighbors.add(edge.getDestination());
            }
        }
        return neighbors;
    }

    //epistrefh th feromonh metaksu duo korufwn
    private double getPheromoneConcentration(Vertex node, Vertex target) {
        for (Edge edge : edges) {
            if (edge.getSource().equals(node) && edge.getDestination().equals(target)) {
                // System.out.println("the weight is: " + edge.getWeight() + "\n and the pheromone is: " + edge.getPheromone());
                return edge.getPheromone();
            }
        }

        throw new RuntimeException("Should not happen");
    }

    //get edge between two nodes
    private Edge getEdgeConnectingTwoNodes(Vertex node, Vertex target) {
        for (Edge edge : edges) {
            if (edge.getSource().equals(node) && edge.getDestination().equals(target)) {
                return edge;
            }
        }

        throw new RuntimeException("Should not happen");
    }
    /*
    public LinkedList<Vertex> getPath(Vertex target) {
        LinkedList<Vertex> path = new LinkedList<Vertex>();
        Vertex step = target;
        // check if a path exists
        if (predecessors.get(step) == null) {
            System.out.println("kati");
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
            System.out.println("katiAllo");
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    } 
     */

}
