package data.server.ws;

import data.server.model.User;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 * Created by bishruti on 1/4/16.
 */

@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use= SOAPBinding.Use.LITERAL) //optional
public interface Data {

    /* Request 1
        Request to obtain all the people and their details in the list.
        Expected Input: -
        Expected Output: List of people (String) */

    @WebMethod(operationName="readUserList")
//    @WebResult(name="user")
    public List<User> readUserList();

    /* Request 4
        Request to add a new person in the list.
        Expected Input: Person (Object)
        Expected Output: Newly created Person with the details associated to that person. (String) */

    @WebMethod(operationName="createUser")
    @WebResult(name="user")
    public User createUser(User user);

    @WebMethod String getHelloWorldAsString(User u);

}
