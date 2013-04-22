/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.osu.picola.etl;

import edu.osu.picola.businesslogic.MCStatisticHandler;
import edu.osu.picola.dao.AssignmentDAO;
import edu.osu.picola.dao.GroupDAO;
import edu.osu.picola.dataobjects.Assignment;
import edu.osu.picola.dataobjects.Group;
import edu.osu.picola.dataobjects.User;
import java.util.List;

/**
 *
 * @author Marci
 */
public class TEST {
    public static void main(String args[]) {
        List<Integer> data = MCStatisticHandler.getMCStatsForAssignment(1);
        
        System.out.println("a = "+data.get(0));
        System.out.println("b = "+data.get(1));
        System.out.println("c = "+data.get(2));
        System.out.println("d = "+data.get(3));
        System.out.println("e = "+data.get(4));
        System.out.println("no = "+data.get(5));
        
        
    }
}
