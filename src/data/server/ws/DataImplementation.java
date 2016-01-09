package data.server.ws;

import data.server.model.HealthMeasureHistory;
import data.server.model.User;

import javax.jws.WebService;
import java.text.ParseException;
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
       Request to obtain a user and the details associated to that user from the list.
       Expected Input: uId (Integer)
       Expected Output: User and the details associated to that user. (String) */

    @Override
    public User readUser(int uId) {
        System.out.println("Reading User with id: " + uId);
        User user = User.getUserById(uId);
        if (user!=null) {
            System.out.println("Successfully found User with id: " + uId + " and name: " + user.getFirstName() + " " + user.getLastName());
            return user;
        } else {
            System.out.println("Unable to find any User with id: " + uId);
            return null;
        }
    }

    /* Request 3
        Request to add a new user in the list.
        Expected Input: User (Object)
        Expected Output: Newly created User with the details associated to that user. (String) */

    @Override
    public User createUser(User user) {
        User.saveUser(user);
        System.out.println("User successfully created.");
        return user;
    }

    /* Request 4
        Request to edit a user in the list.
        Expected Input: uId (Integer) and User (Object)
        Expected Output: Edited User with the details associated to that user. (String) */

    @Override
    public User updateUser(User user) {
        int uId = user.getUId();
        User existing = User.getUserById(uId);
        if (existing == null) {
            System.out.println("Cannot find user with id: " + uId);
        } else {
            String updatedFirstName = user.getFirstName();
            String updatedLastName = user.getLastName();
            String updatedBloodGroup = user.getBloodGroup();
            String updatedAddress = user.getAddress();

            if (updatedFirstName != null) {
                existing.setFirstName(updatedFirstName);
            }
            if (updatedLastName != null) {
                existing.setLastName(updatedLastName);
            }
            if (updatedBloodGroup != null) {
                existing.setBloodGroup(updatedBloodGroup);
            }
            if (updatedAddress != null) {
                existing.setAddress(updatedAddress);
            }
            if (user.getBirthDate() != null) {
                try {
                    existing.setBirthDate(user.getBirthDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            User.updateUser(existing);
        }
        System.out.println("Successfully updated personal information of user wid id: " + uId);
        return existing;
    }

     /* Request 5
        Request to delete a user from the list.
        Expected Input: uId (Integer)
        Expected Output: Response Message. */

    @Override
    public int deleteUser(int uId) {
        User user = User.getUserById(uId);
        if (user!=null) {
            User.removeUser(user);
            System.out.println("Successfully deleted User with id: " + uId );
            return 0;
        } else {
            System.out.println("Cannot find user with id: " + uId);
            return -1;
        }
    }

     /* Request 6
       Request to obtain all measure details about a measure of a user in the list.
       Expected Input: uId (Integer)
                       measureType (String)
       Expected Output: List of details of measure types. (String) */

    @Override
    public List<HealthMeasureHistory> readUserHistory(int uId, String measureType) {
        List<HealthMeasureHistory> healthMeasureHistory = HealthMeasureHistory.getHealthHistoryOfUserByMeasureType(uId, measureType);
        if (healthMeasureHistory == null)
            throw new RuntimeException("Get: User with " + uId + " not found");
        return healthMeasureHistory;
    }

    /* Request 7
        Request to obtain measure details about a particular measure of a user in the list.
        Expected Input: uId (Integer)
                        measureType (String)
                        hmhId (Integer)
        Expected Output: Details of a particular measure. (String) */

    @Override
    public List<HealthMeasureHistory> readUserMeasure(int uId, String measureType, int hmhId) {
        List<HealthMeasureHistory> healthMeasureHistory = HealthMeasureHistory.getMeasurebyHmhId(uId, measureType, hmhId);
        if (healthMeasureHistory == null)
            throw new RuntimeException("Get: User with " + uId + " not found");
        return healthMeasureHistory;
    }
}
