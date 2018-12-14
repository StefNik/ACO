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

    public void testExcute() {
        nodes = new ArrayList<Vertex>();
        edges = new ArrayList<Edge>();

        //create the node Source
        Vertex location0 = new Vertex("Source", "Source");
        nodes.add(location0);

        //create the five nodes  switch_1 ->switch_5  
        for (int i = 1; i < 6; i++) {
            Vertex location = new Vertex("Switch_" + i, "Switch_" + i);
            nodes.add(location);
        }
        //create the final node Destination
        Vertex location11 = new Vertex("Destination", "Destination");
        nodes.add(location11);

        //Create the bidirectional edge between Source <-> Swirch_1
        addLane("Edge_0_a", 0, 1, 10);
        addLane("Edge_0_b", 1, 0, 10);

        //Create the bidirectional edge between Swirch_1 <-> Swirch_2
        addLane("Edge_1_a", 1, 2, 1);
        addLane("Edge_1_b", 2, 1, 1);

        //Create the bidirectional edge between Swirch_1 <-> Swirch_3
        addLane("Edge_2_a", 1, 3, 7);
        addLane("Edge_2_b", 3, 1, 7);

        //Create the bidirectional edge between Swirch_1 <-> Swirch_4
        addLane("Edge_3_a", 1, 4, 3);
        addLane("Edge_3_b", 4, 1, 3);

        //Create the bidirectional edge between Swirch_2 <-> Swirch_3
        addLane("Edge_4_a", 2, 3, 5);
        addLane("Edge_4_b", 3, 2, 5);

        //Create the bidirectional edge between Swirch_3 <-> Swirch_4
        addLane("Edge_5_a", 3, 4, 1);
        addLane("Edge_5_b", 4, 3, 1);

        //Create the bidirectional edge between Swirch_2 <-> Swirch_5
        addLane("Edge_6_a", 2, 5, 20);
        addLane("Edge_6_b", 5, 2, 20);

        //Create the bidirectional edge between Swirch_3 <-> Swirch_5
        addLane("Edge_7_a", 3, 5, 10);
        addLane("Edge_7_b", 5, 3, 10);

        //Create the bidirectional edge between Swirch_4 <-> Swirch_5
        addLane("Edge_8_a", 4, 5, 12);
        addLane("Edge_8_b", 5, 4, 12);

        //Create the bidirectional edge between Swirch_5 <-> Destination
        addLane("Edge_9_a", 5, 6, 10);
        addLane("Edge_9_b", 6, 5, 10);

        System.out.println("the number of nodes in graph is: " + nodes.size());
        System.out.println("the number of edges in graph is: " + edges.size());
        // Lets check from location Loc_1 to Loc_10
        Graph graph = new Graph(nodes, edges);
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
        dijkstra.execute(nodes.get(0));
        LinkedList<Vertex> path = dijkstra.getPath(nodes.get(6));

        for (Vertex vertex : path) {
            System.out.println(vertex);
        }
    }

    //function that creates the edge between two givven nodes by their locNo
    private void addLane(String laneId, int sourceLocNo, int destLocNo, int duration) {
        Edge lane = new Edge(laneId, nodes.get(sourceLocNo), nodes.get(destLocNo), duration);
        edges.add(lane);
    }
}
