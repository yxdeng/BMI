/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bmi.calculator;

/**
 * Use to calculate the bmi
 * @author yxdeng
 */
public interface CalculateInterface {

    /**
     * use to calculate the bmi ,if the height and weight is ok
     * return the bmi or return -1
     * @param height
     * @param weighit
     * @return double bmi
     */
    public double calculateBmi(double height,double weighit);

    /**
     * use to get the bmi mean ,if bmi is ok return the string int
     * else return -1
     * @param bmi
     * @return int
     */
    public int getBmiMean(double bmi);
}
