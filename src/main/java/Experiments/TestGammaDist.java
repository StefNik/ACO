/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Experiments;

/**
 *
 * @author stefanos
 */
import org.apache.commons.math3.distribution.GammaDistribution;

//More information for Gamma Distribution ca be found here: http://commons.apache.org/proper/commons-math/javadocs/api-3.4/org/apache/commons/math3/distribution/GammaDistribution.html
public class TestGammaDist {

    public static void main(String[] args) {
        //Constructor:  GammaDistribution(double shape, double scale); //shape->k  , scale->θ 
        GammaDistribution gamma = new GammaDistribution(10, 50);

        System.out.println("the mean of the distribution is: " + gamma.getNumericalMean());

        for (int i = 0; i < 15; i++) {
            System.out.println(gamma.sample());
        }

    }

}
