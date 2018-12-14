/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Experiments;

import important_classes.Edge;
import important_classes.Graph;
import important_classes.Vertex;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import routing_algorithms.DijkstraAlgorithm;

public class TestDijkstraAlgorithm {

    private List<Vertex> nodes;
    private List<Edge> edges;

    public void test1Excute() {
        nodes = new ArrayList<Vertex>();
        edges = new ArrayList<Edge>();
        for (int i = 0; i < 11; i++) {
            Vertex location = new Vertex("Node_" + i, "Node_" + i);
            nodes.add(location);
        }

        addLane("Edge_0", 0, 1, 85);
        addLane("Edge_0", 1, 0, 85);

        addLane("Edge_1", 0, 2, 217);
        addLane("Edge_1", 0, 2, 217);

        addLane("Edge_2", 0, 4, 173);
        addLane("Edge_2", 4, 0, 173);

        addLane("Edge_3", 2, 6, 186);
        addLane("Edge_3", 6, 2, 186);

        addLane("Edge_4", 2, 7, 103);
        addLane("Edge_4", 7, 2, 103);

        addLane("Edge_5", 3, 7, 183);
        addLane("Edge_5", 7, 3, 183);

        addLane("Edge_6", 5, 8, 250);
        addLane("Edge_6", 8, 5, 250);

        addLane("Edge_7", 8, 9, 84);
        addLane("Edge_7", 9, 8, 84);

        addLane("Edge_8", 9, 7, 167);
        addLane("Edge_8", 7, 9, 167);

        addLane("Edge_9", 4, 9, 502);
        addLane("Edge_9", 9, 4, 502);

        addLane("Edge_10", 9, 10, 40);
        addLane("Edge_10", 10, 9, 40);

        addLane("Edge_11", 1, 10, 600);
        addLane("Edge_11", 10, 1, 600);

        System.out.println("the number of nodes in graph is: " + edges.size());
        // Lets check from location Loc_1 to Loc_10
        Graph graph = new Graph(nodes, edges);
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
        dijkstra.execute(nodes.get(0));
        LinkedList<Vertex> path = dijkstra.getPath(nodes.get(10));

        for (Vertex vertex : path) {
            System.out.println(vertex);
        }
    }

    public void test2Excute() {
        nodes = new ArrayList<Vertex>();
        edges = new ArrayList<Edge>();

        Vertex location0 = new Vertex("Source", "Source");
        nodes.add(location0);
        for (int i = 1; i < 6; i++) {
            Vertex location = new Vertex("Switch_" + i, "Switch_" + i);
            nodes.add(location);
        }
        Vertex location11 = new Vertex("Destination", "Destination");
        nodes.add(location11);

        addLane("Edge_0_a", 0, 1, 10);
        addLane("Edge_0_b", 1, 0, 10);

        addLane("Edge_1_a", 1, 2, 1);
        addLane("Edge_1_b", 2, 1, 1);

        addLane("Edge_2_a", 1, 3, 7);
        addLane("Edge_2_b", 3, 1, 7);

        addLane("Edge_3_a", 1, 4, 3);
        addLane("Edge_3_b", 4, 1, 3);

        addLane("Edge_4_a", 2, 3, 5);
        addLane("Edge_4_b", 3, 2, 5);

        addLane("Edge_5_a", 3, 4, 1);
        addLane("Edge_5_b", 4, 3, 1);

        addLane("Edge_6_a", 2, 5, 20);
        addLane("Edge_6_b", 5, 2, 20);

        addLane("Edge_7_a", 3, 5, 10);
        addLane("Edge_7_b", 5, 3, 10);

        addLane("Edge_8_a", 4, 5, 12);
        addLane("Edge_8_b", 5, 4, 12);

        addLane("Edge_9_a", 5, 6, 10);
        addLane("Edge_9_b", 6, 5, 10);

        System.out.println("the number of nodes in graph is: " + edges.size());
        // Lets check from location Loc_1 to Loc_10
        Graph graph = new Graph(nodes, edges);
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
        dijkstra.execute(nodes.get(0));
        LinkedList<Vertex> path = dijkstra.getPath(nodes.get(6));

        for (Vertex vertex : path) {
            System.out.println(vertex);
        }
    }

    private void addLane(String laneId, int sourceLocNo, int destLocNo, int duration) {
        Edge lane = new Edge(laneId, nodes.get(sourceLocNo), nodes.get(destLocNo), duration);
        edges.add(lane);
    }
}
