package com.mycompany.casetwosimulationloadbalancing;

import Experiments.TestDijkstraAlgorithm;
import Experiments.TestSIloadBalancingAlgorithm;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author StefNik
 */
public class Main {

    //SOSOSOSOSOSOSOSOSOSOSOSOS: na diavasw auto to API gia graphs: https://jgrapht.org
    //exei kai visualization capabilities 
    public static void main(String[] args) {

        //classes and code has been taken from: http://www.vogella.com/tutorials/JavaAlgorithmsDijkstra/article.html
        //TestDijkstraAlgorithm tD = new TestDijkstraAlgorithm();
        //tD.testExcute();

        TestSIloadBalancingAlgorithm tD2 = new TestSIloadBalancingAlgorithm();
        tD2.testExcute();

    }
}
