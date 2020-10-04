package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.*;


public class Main {
    public static void main(String[] args) {
        UserService us = new UserServiceImpl();
        us.createUsersTable();
        List<User> usrList = Arrays.asList(new User("Petrov", "Ivanov", (byte) 29),
                new User("Bashirov", "Oleg", (byte) 31),
                new User("Dukalis", "Anatoly", (byte) 56),
                new User("Kasanova", "Petr", (byte) 69));

        for (User usr : usrList) {
            us.saveUser(usr.getName(), usr.getLastName(), usr.getAge());
        }

        for (User usr : us.getAllUsers()) {
            System.out.println(usr);
        }
        us.cleanUsersTable();
        us.dropUsersTable();
     }
}
