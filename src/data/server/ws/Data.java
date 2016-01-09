package data.server.ws;

import data.server.model.HealthMeasureHistory;
import data.server.model.User;

import javax.jws.WebMethod;
import javax.jws.WebParam;
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
        Request to obtain all the users and their details in the list.
        Expected Input: -
        Expected Output: List of users (String) */

    @WebMethod(operationName="readUserList")
    @WebResult(name="user")
    public List<User> readUserList();

    /* Request 2
        Request to obtain a user and the details associated to that user from the list.
        Expected Input: uId (Integer)
        Expected Output: User and the details associated to that user. (String) */

    @WebMethod(operationName="readUser")
    @WebResult(name="user")
    public User readUser(@WebParam(name="uId") int uId);

    /* Request 3
        Request to add a new person in the list.
        Expected Input: Person (Object)
        Expected Output: Newly created Person with the details associated to that person. (String) */

    @WebMethod(operationName="createUser")
    @WebResult(name="user")
    public User createUser(@WebParam(name="user") User user);

    /* Request 4
        Request to edit a user in the list.
        Expected Input: uId (Integer) and User (Object)
        Expected Output: Edited User with the details associated to that user. (String) */

    @WebMethod(operationName="updateUser")
    @WebResult(name="user")
    public User updateUser(@WebParam(name="user") User user);

     /* Request 5
        Request to delete a user from the list.
        Expected Input: uId (Integer)
        Expected Output: Response Message. */

    @WebMethod(operationName="deleteUser")
    @WebResult(name="user")
    public int deleteUser(@WebParam(name="uId") int id);

    /* Request 6
       Request to obtain all measure details about a measure of a user in the list.
       Expected Input: uId (Integer)
                       measureType (String)
       Expected Output: List of details of measure types. (String) */

    @WebMethod(operationName="readUserHistory")
    @WebResult(name="measure")
    public List<HealthMeasureHistory> readUserHistory(@WebParam(name="uId") int id, @WebParam(name="measureType") String measureType);


}
