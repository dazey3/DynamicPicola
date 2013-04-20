/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.osu.picola.etl;

import edu.osu.picola.dao.GroupDAO;
import edu.osu.picola.dataobjects.Group;
import edu.osu.picola.dataobjects.User;
import java.util.List;

/**
 *
 * @author Marci
 */
public class TEST {
    public static void main(String args[]) {
        List<Group> group = GroupDAO.getAllGroupsAssignedAssignment(1);
//        System.out.println("SIZE = "+ group.size() );
//        
//        for (Group g : group) {
//            for (User u : g) {
//                System.out.println(u.getF_name() + " "+ u.getL_name());
//            }
//            System.out.println("==============");
//        }
        boolean b=false;
        int a=0;
        System.out.println(a);
    }
}
