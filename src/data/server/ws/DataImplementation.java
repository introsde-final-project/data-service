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
        Request to obtain all the people and their details in the list.
        Expected Input: -
        Expected Output: List of people (String) */

    @Override
    public List<User> readUserList() {
        System.out.println("Reading the list of User");
        return User.getAll();
    }

    /* Request 4
        Request to add a new person in the list.
        Expected Input: Person (Object)
        Expected Output: Newly created Person with the details associated to that person. (String) */

    @Override
    public User createUser(User user) {
        System.out.println("____________________-----------------------------------------------");
        System.out.println(user);
        User.saveUser(user);
        System.out.println("User successfully created.");
        return user;
//        return User.getAll().get(0);
    }

    @Override
    public String getHelloWorldAsString(User u) {
        return "Hello World JAX-WS " + u.getFirstName();
    }
}
