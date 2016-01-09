package data.server.ws;

import data.server.model.User;

import javax.jws.WebService;
import java.util.List;

/**
 * Created by bishruti on 1/4/16.
 */

@WebService(endpointInterface = "data.server.ws.Data", serviceName="DataService")
public class DataImplementation implements Data {

    /* Request 1
        Request to obtain all the users and their details in the list.
        Expected Input: -
        Expected Output: List of users (String) */

    @Override
    public List<User> readUserList() {
        System.out.println("Reading the list of User");
        return User.getAll();
    }

    /* Request 2
       Request to obtain a person and the details associated to that person from the list.
       Expected Input: PersonId (Integer)
       Expected Output: Person and the details associated to that person. (String) */

    @Override
    public User readUser(int id) {
        System.out.println("Reading Person with id: " + id);
        User user = User.getUserById(id);
        if (user!=null) {
            System.out.println("Successfully found Person with id: " + id + " and name: " + user.getFirstName() + " " + user.getLastName());
            return user;
        } else {
            System.out.println("Unable to find any Person with id: " + id);
            return null;
        }
    }

    /* Request 3
        Request to add a new user in the list.
        Expected Input: User (Object)
        Expected Output: Newly created User with the details associated to that user. (String) */

    @Override
    public User createUser(User user) {
        System.out.println("************************************");
        System.out.println(user.getFirstName());
        User.saveUser(user);
        System.out.println("User successfully created.");
        return user;
    }
}
