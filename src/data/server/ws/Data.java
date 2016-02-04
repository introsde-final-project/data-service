package data.server.ws;

import data.server.model.Activity;
import data.server.model.Goal;
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
    public int deleteUser(@WebParam(name="uId") int uId);

    /* Request 6
       Request to obtain all measure details about a measure of a user in the list.
       Expected Input: uId (Integer)
                       measureType (String)
       Expected Output: List of details of measure types. (String) */

    @WebMethod(operationName="readUserHistory")
    @WebResult(name="measure")
    public List<HealthMeasureHistory> readUserHistory(@WebParam(name="uId") int uId, @WebParam(name="measureType") String measureType);

    /* Request 7
        Request to obtain measure details about a particular measure of a user in the list.
        Expected Input: uId (Integer)
                        measureType (String)
                        hmhId (Integer)
        Expected Output: Details of a particular measure. (String) */

    @WebMethod(operationName="readUserMeasure")
    @WebResult(name="measure")
    public List<HealthMeasureHistory> readUserMeasure(@WebParam(name="uId") int uId, @WebParam(name="measureType") String measureType, @WebParam(name="hmhId") int hmhId);


    /* Request 8
        Request to create measure details about a measure of a user in the list.
        Expected Input: uId (Integer)
        measureType (String)
        MeasureDetails (Object)
        Expected Output:
        List of newly created measure. (String) */

    @WebMethod(operationName="saveUserMeasure")
    @WebResult(name="measure")
    public HealthMeasureHistory saveUserMeasure(@WebParam(name="uId") int uId, @WebParam(name="measure") HealthMeasureHistory healthMeasureHistory);

     /* Request 9
        Request to update measure details about a measure of a user in the list.
        Expected Input: uId (Integer)
        measureType (String)
        hmhId (Integer)
        MeasureDetails (Object)
        Expected Output:
        List of updated measure. (String) */

    @WebMethod(operationName="updateUserMeasure")
    @WebResult(name="measure")
    public HealthMeasureHistory updateUserMeasure(@WebParam(name="uId") int uId, @WebParam(name="measure") HealthMeasureHistory healthMeasureHistory);

    /* Request 10
        Request to delete measure details about a measure of a user in the list.
        Expected Input: uId (Integer)
        hmhId (Integer)
        Expected Output: Response Message. */

    @WebMethod(operationName="deleteMeasure")
    @WebResult(name="measure")
    public int deleteMeasure(@WebParam(name="uId") int uId, @WebParam(name="hmhId") int hmhId);

    /* Request 11
        Request to obtain all the goals and their details in the list.
        Expected Input: -
        Expected Output: List of goals (String) */

    @WebMethod(operationName="readGoalList")
    @WebResult(name="goal")
    public List<Goal> readGoalList();

    /* Request 12
       Request to obtain a goal and the details associated to that goal from the list.
       Expected Input: goalId (Integer)
       Expected Output: Goal and the details associated to that goal. (String) */

    @WebMethod(operationName="readGoal")
    @WebResult(name="goal")
    public Goal readGoal(@WebParam(name="goalId") int goalId);

    /* Request 13
       Request to obtain a goal and the details associated to that goal from the list by goalName.
       Expected Input: goalName (String)
       Expected Output: Goal and the details associated to that goal. (String) */

    @WebMethod(operationName="readGoalByName")
    @WebResult(name="goal")
    public Goal readGoalByName(@WebParam(name="goalName") String goalName);

    /* Request 14
        Request to add a new goal in the list.
        Expected Input: Goal (Object)
        Expected Output: Newly created Goal with the details associated to that goal. (String) */

    @WebMethod(operationName="createGoal")
    @WebResult(name="goal")
    public Goal createGoal(@WebParam(name = "goal") Goal goal);

    /* Request 15
        Request to edit a goal in the list.
        Expected Input: goalId (Integer) and Goal (Object)
        Expected Output: Edited Goal with the details associated to that goal. (String) */

    @WebMethod(operationName="updateGoal")
    @WebResult(name="goal")
    public Goal updateGoal(@WebParam(name = "goal") Goal goal);

     /* Request 16
        Request to delete a goal from the list.
        Expected Input: goalId (Integer)
        Expected Output: Response Message. */

    @WebMethod(operationName="deleteGoal")
    @WebResult(name="goal")
    public int deleteGoal(@WebParam(name="goalId") int goalId);

    /* Request 17
        Request to obtain all the activities and their details in the list.
        Expected Input: -
        Expected Output: List of activities (String) */

    @WebMethod(operationName="readActivityList")
    @WebResult(name="activity")
    public List<Activity> readActivityList();

    /* Request 18
       Request to obtain an activity and the details associated to that activity from the list.
       Expected Input: activityId (Integer)
       Expected Output: Activity and the details associated to that activity. (String) */

    @WebMethod(operationName="readActivity")
    @WebResult(name="activity")
    public Activity readActivity(@WebParam(name = "activityId") int activityId);

    /* Request 19
       Request to obtain an activity and the details associated to that activity from the list by activityName.
       Expected Input: activityName (String)
       Expected Output: Activity and the details associated to that activity. (String) */

    @WebMethod(operationName="readActivityByName")
    @WebResult(name="activity")
    public Activity readActivityByName(@WebParam(name = "activityName") String activityName);

    /* Request 20
        Request to add a new activity in the list.
        Expected Input: Activity (Object)
        Expected Output: Newly created Activity with the details associated to that activity. (String) */

    @WebMethod(operationName="createActivity")
    @WebResult(name="activity")
    public Activity createActivity(@WebParam(name = "activity") Activity activity);

    /* Request 21
        Request to edit an activity in the list.
        Expected Input: activityId (Integer) and Activity (Object)
        Expected Output: Edited activity with the details associated to that activity. (String) */

    @WebMethod(operationName="updateActivity")
    @WebResult(name="activity")
    public Activity updateActivity(@WebParam(name = "activity") Activity activity);

     /* Request 22
        Request to delete an activity from the list.
        Expected Input: activityId (Integer)
        Expected Output: Response Message. */
     @WebMethod(operationName="deleteActivity")
     @WebResult(name="activity")
     public int deleteActivity(@WebParam(name="activityId") int activityId);
}