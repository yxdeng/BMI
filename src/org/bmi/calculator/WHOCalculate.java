/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bmi.calculator;

/**
 *
 * @author yxdeng
 */
public class WHOCalculate implements CalculateInterface{

    public WHOCalculate() {

    }


    /**
     * use to calculate the bmi ,if the height and weight is ok
     * return the bmi or return -1
     * @param height
     * @param weighit
     * @return double bmi
     */
    public double calculateBmi(double height, double weight) {
//        throw new UnsupportedOperationException("Not supported yet.");
        if(height <= 0 || weight <= 0)
            return -1;
        else return weight / (height * height);
    }

    /**
     * use to get the bmi mean ,if bmi is ok return the string int
     * else return -1
     * @param bmi
     * @return int
     */
    public int getBmiMean(double bmi) {
//        throw new UnsupportedOperationException("Not supported yet.");
        if(bmi <= 0)
            return -1;
//        if(bmi < 18.5)
        return 0;

    }

    private double standards[]; //= {18.5,25.0,30.0,35.0,40.0};
}
