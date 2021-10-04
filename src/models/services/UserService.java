package models.services;

import models.beans.User;
import models.dao.UserDao;

public class UserService {

    public boolean validateUser(User user) throws ClassNotFoundException {
         UserDao userDao = new UserDao();
        return userDao.selectUser(user);
    }

    public void registerUser(User user) throws ClassNotFoundException {
        UserDao userDao = new UserDao();
        userDao.insertUser(user);
    }

    public User getFreeDoctor() throws ClassNotFoundException {
        UserDao userDao = new UserDao();
        return userDao.selectDoctorsWithoutAppointments();
    }

}
