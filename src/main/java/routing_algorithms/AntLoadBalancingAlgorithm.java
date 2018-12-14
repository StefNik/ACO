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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author StefNik
 */
public class AntLoadBalancingAlgorithm {

    private final List<Vertex> nodes;
    private final List<Edge> edges;
    private final int alphaVal;
    private final int numOfAnts;
    private Set<Vertex> settledNodes;
    private Set<Vertex> unSettledNodes;
    private Map<Vertex, Vertex> predecessors;

    public AntLoadBalancingAlgorithm(Graph graph, int alphaVal, int numOfAnts) {
        // create a copy of the array so that we can operate on this array
        this.nodes = new ArrayList<Vertex>(graph.getVertexes());
        this.edges = new ArrayList<Edge>(graph.getEdges());
        this.alphaVal = alphaVal;
        this.numOfAnts = numOfAnts;
    }

    public AntLoadBalancingAlgorithm(Graph graph, int alphaVal) {
        // create a copy of the array so that we can operate on this array
        this.nodes = new ArrayList<Vertex>(graph.getVertexes());
        this.edges = new ArrayList<Edge>(graph.getEdges());
        this.alphaVal = alphaVal;
        this.numOfAnts = 20;
    }

    public AntLoadBalancingAlgorithm(Graph graph) {
        this.nodes = new ArrayList<Vertex>(graph.getVertexes());
        this.edges = new ArrayList<Edge>(graph.getEdges());
        this.alphaVal = 1;
        this.numOfAnts = 20;
    }

    public LinkedList<Vertex> executeAndGetPath(Vertex source, Vertex destination) {

        for (int k = 0; k < 50; k++) {
            for (int j = 0; j < this.numOfAnts; j++) {
                predecessors = new HashMap<Vertex, Vertex>();

                Vertex node = source;
                int count = -1;
                do {
                    List<Vertex> adjacentNodes = getNeighbors(node);
                    List<Double> pheromoneOfEachPath = new ArrayList<Double>();
                    List<Double> transitionProbsOfVertex = new ArrayList<Double>();
                    for (Vertex v : adjacentNodes) {
                        // System.out.println("the pheromone in this path is: " + getPheromoneConcentration(node, v));
                        pheromoneOfEachPath.add(getPheromoneConcentration(node, v));
                    }
                    double sum = 0d;
                    for (double el : pheromoneOfEachPath) {
                        sum += Math.pow(el, this.alphaVal);
                    }
                    // System.out.println(" the sum is: " + sum);
                    for (double el : pheromoneOfEachPath) {
                        transitionProbsOfVertex.add(Math.pow(el, this.alphaVal) / sum);
                    }

                    double p = Math.random();
                    double cumulativeProbability = 0.0;
                    for (int i = 0; i < transitionProbsOfVertex.size(); i++) {
                        cumulativeProbability += transitionProbsOfVertex.get(i);
                        if (p <= cumulativeProbability) {
                            /*
                    SOSOSOSOOS:
                        Swse auto to bhma sto Map predecessors, protou kaneis 
                        kainourgia epanalhpsh (bale to palio stous episkeptomenous kombous)
                             */
                            System.out.println("kai allo monopati prostethhke sth lush: " + node + " -> " + adjacentNodes.get(i).getId());
                            predecessors.put(adjacentNodes.get(i), node);

                            node = adjacentNodes.get(i);
                        }
                        // System.out.println("the probability is: " + p + " \n and the sumCum is: " + cumulativeProbability + "\n last added probability: " + transitionProbsOfVertex.get(i));
                    }

                } while (!node.equals(destination));
                List<Vertex> lV = this.getPath(destination);
                System.out.println("\n\n h lush einai: ");
                for (Vertex v : lV) {
                    System.out.println(v.getName());
                }

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

    //epistrefh thn apostash metaksu duo korufwn
    private double getPheromoneConcentration(Vertex node, Vertex target) {
        for (Edge edge : edges) {
            if (edge.getSource().equals(node) && edge.getDestination().equals(target)) {
                // System.out.println("the weight is: " + edge.getWeight() + "\n and the pheromone is: " + edge.getPheromone());
                return edge.getPheromone();
            }
        }

        throw new RuntimeException("Should not happen");
    }

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
}
