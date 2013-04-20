package edu.osu.picola.beans;

import edu.osu.picola.dao.UserDAO;
import java.io.Serializable;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

public class AccountManagementBean implements Serializable, ActionListener{

    @Override
    public void processAction(ActionEvent event) throws AbortProcessingException {
        System.out.println("Changed Account Information");
        //hange things for user thats logged in based on attributes in THIS class.
        UserDAO.updateUser(LoginBean.user);
    }
}
