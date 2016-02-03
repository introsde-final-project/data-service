package data.server.ws;

import data.server.model.*;

import javax.jws.WebService;
import javax.persistence.NoResultException;
import java.util.Date;
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
            Date updatedBirthDate = user.getBirthDate();
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
            if (updatedBirthDate != null) {
                existing.setBirthDate(updatedBirthDate);
            }
            User.updateUser(existing);
        }
        System.out.println("Successfully updated personal information of user with id: " + uId);
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

    /* Request 8
        Request to create measure details about a measure of a user in the list.
        Expected Input: uId (Integer)
        measureType (String)
        MeasureDetails (Object)
        Expected Output:
        List of newly created measure. (String) */

    @Override
    public HealthMeasureHistory saveUserMeasure(int uId, HealthMeasureHistory healthMeasureHistory) {
        System.out.println("Creating new Health Measure History...");
        User user = User.getUserById(uId);
        if (user == null) {
            System.out.println("Unable to find the user with id: " + uId);
            return null;
        }
        else {
            List<HealthProfile> healthProfiles = user.getMeasureType();
            if (healthProfiles.size() == 0) {
                HealthProfile healthProfile = new HealthProfile();
                healthProfile.setMeasureType(healthMeasureHistory.getMeasureType());
                healthProfile.setMeasureValue(healthMeasureHistory.getMeasureValue());
                healthProfile.setMeasureValueType(healthMeasureHistory.getMeasureValueType());
                healthProfile.setDateRegistered(new Date());
                healthProfile.setUser(user);
                HealthProfile.saveHealthProfile(healthProfile);
            }
            else {
                for (HealthProfile healthProfile : healthProfiles) {
                    if (healthProfile.getMeasureType().equalsIgnoreCase(healthMeasureHistory.getMeasureType())) {
                        healthProfile.setMeasureValue(healthMeasureHistory.getMeasureValue());
                        healthProfile.setMeasureValueType(healthMeasureHistory.getMeasureValueType());
                        healthProfile.setDateRegistered(new Date());
                        HealthProfile.updateHealthProfile(healthProfile);
                    }
                }
            }
            healthMeasureHistory.setMeasureType(healthMeasureHistory.getMeasureType());
            healthMeasureHistory.setMeasureValue(healthMeasureHistory.getMeasureValue());
            healthMeasureHistory.setMeasureValueType(healthMeasureHistory.getMeasureValueType());
            healthMeasureHistory.setDateRegistered(new Date());
            healthMeasureHistory.setUser(user);
            return HealthMeasureHistory.saveHealthMeasureHistory(healthMeasureHistory);
        }
    }
    /* Request 9
        Request to update measure details about a measure of a user in the list.
        Expected Input: uId (Integer)
        measureType (String)
        hmhId (Integer)
        MeasureDetails (Object)
        Expected Output:
        List of updated measure. (String) */

    @Override
    public HealthMeasureHistory updateUserMeasure(int uId, HealthMeasureHistory healthMeasureHistory) {
        System.out.println(uId);
        System.out.println(healthMeasureHistory.getMeasureType());
        User existingUser = User.getUserById(uId);
        HealthMeasureHistory existingHistory = HealthMeasureHistory.getHealthMeasureHistoryById(healthMeasureHistory.getHmhId());
        List<HealthProfile> healthProfiles = existingUser.getMeasureType();

        if (existingUser == null) {
            System.out.println("Cannot find user with id: " + uId);
        } else {
            String updatedMeasureType = healthMeasureHistory.getMeasureType();
            String updatedMeasureValue = healthMeasureHistory.getMeasureValue();
            String updatedMeasureValueType = healthMeasureHistory.getMeasureValueType();
            Date updatedDateRegistered = healthMeasureHistory.getDateRegistered();
            if (updatedMeasureType != null) {
                existingHistory.setMeasureType(updatedMeasureType);
            }
            if (updatedMeasureValue != null) {
                existingHistory.setMeasureValue(updatedMeasureValue);
            }
            if (updatedMeasureValueType != null) {
                existingHistory.setMeasureValueType(updatedMeasureValueType);
            }

            for (HealthProfile healthProfile: healthProfiles) {
                if (healthProfile.getMeasureType().equalsIgnoreCase(healthMeasureHistory.getMeasureType())) {
                   healthProfile.setMeasureValue(healthMeasureHistory.getMeasureValue());
                   healthProfile.setMeasureValueType(healthMeasureHistory.getMeasureValueType());
                   healthProfile.setDateRegistered(new Date());
                   HealthProfile.updateHealthProfile(healthProfile);
                }
            }
        }
        return HealthMeasureHistory.updateHealthMeasureHistory(existingHistory);
    }

    /* Request 10
        Request to delete measure details about a measure of a user in the list.
        Expected Input: uId (Integer)
        hmhId (Integer)
        Expected Output: Response Message. */

    @Override
    public int deleteMeasure(int uId, int hmhId) {
        User user = User.getUserById(uId);
        if (user!=null) {
            HealthMeasureHistory healthMeasureHistory = HealthMeasureHistory.getHealthMeasureHistoryById(hmhId);
            if (healthMeasureHistory != null && healthMeasureHistory.getUser().getUId() == uId)  {
                HealthMeasureHistory.removeHealthMeasureHistory(healthMeasureHistory);
                System.out.println("Successfully deleted Health Measure History with id: " + hmhId );
                return 0;
            }
            else {
                System.out.println("Cannot find Health Measure History with id: " + hmhId + " for user with id: " + uId);
                return -1;
            }

        } else {
            System.out.println("Cannot find user with id: " + uId);
            return -1;
        }
    }

    /* Request 11
        Request to obtain all the goals and their details in the list.
        Expected Input: -
        Expected Output: List of goals (String) */

    @Override
    public List<Goal> readGoalList() {
        System.out.println("Reading the list of Goal");
        return Goal.getAll();
    }

    /* Request 12
       Request to obtain a goal and the details associated to that goal from the list.
       Expected Input: goalId (Integer)
       Expected Output: Goal and the details associated to that goal. (String) */

    @Override
    public Goal readGoal(int goalId) {
        System.out.println("Reading Goal with id: " + goalId);
        Goal goal = Goal.getGoalById(goalId);
        if (goal!=null) {
            System.out.println("Successfully found Goal with id: " + goalId);
            return goal;
        } else {
            System.out.println("Unable to find any Goal with id: " + goalId);
            return null;
        }
    }

    /* Request 13
       Request to obtain a goal and the details associated to that goal from the list by goalName.
       Expected Input: goalName (String)
       Expected Output: Goal and the details associated to that goal. (String) */

    @Override
    public Goal readGoalByName(String goalName) {
        System.out.println("Reading Goal with name: " + goalName);
        try {
            Goal goal = Goal.getGoalByName(goalName);
            return goal;
        }
        catch (NoResultException e) {
            return null;
        }
    }

     /* Request 14
        Request to add a new goal in the list.
        Expected Input: Goal (Object)
        Expected Output: Newly created Goal with the details associated to that goal. (String) */

    @Override
    public Goal createGoal(Goal goal) {
        Goal.saveGoal(goal);
        System.out.println("Goal successfully created.");
        return goal;
    }

    /* Request 15
        Request to edit a goal in the list.
        Expected Input: goalId (Integer) and Goal (Object)
        Expected Output: Edited Goal with the details associated to that goal. (String) */

    @Override
    public Goal updateGoal(Goal goal) {
        int goalId = goal.getGoalId();
        Goal existing = Goal.getGoalById(goalId);
        if (existing == null) {
            System.out.println("Cannot find goal with id: " + goalId);
        } else {
            String updatedGoalName = goal.getGoalName();
            String updatedGoalDescription = goal.getGoalDescription();

            if (updatedGoalName != null) {
                existing.setGoalName(updatedGoalName);
            }
            if (updatedGoalDescription != null) {
                existing.setGoalDescription(updatedGoalDescription);
            }
            Goal.updateGoal(existing);
        }
        System.out.println("Successfully updated goal with id: " + goalId);
        return existing;
    }

    /* Request 16
        Request to delete a goal from the list.
        Expected Input: goalId (Integer)
        Expected Output: Response Message. */

    @Override
    public int deleteGoal(int goalId) {
        Goal goal = Goal.getGoalById(goalId);
        if (goal!=null) {
            Goal.removeGoal(goal);
            System.out.println("Successfully deleted Goal with id: " + goalId );
            return 0;
        } else {
            System.out.println("Cannot find user with id: " + goalId);
            return -1;
        }
    }

    /* Request 17
        Request to obtain all the activities and their details in the list.
        Expected Input: -
        Expected Output: List of activities (String) */

    @Override
    public List<Activity> readActivityList() {
        System.out.println("Reading the list of Activities");
        return Activity.getAll();
    }

    /* Request 18
       Request to obtain an activity and the details associated to that activity from the list.
       Expected Input: activityId (Integer)
       Expected Output: Activity and the details associated to that activity. (String) */

    @Override
    public Activity readActivity(int activityId) {
        System.out.println("Reading Activity with id: " + activityId);
        Activity activity = Activity.getActivityById(activityId);
        if (activity!=null) {
            System.out.println("Successfully found Activity with id: " + activityId);
            return activity;
        } else {
            System.out.println("Unable to find any Activity with id: " + activityId);
            return null;
        }
    }

    /* Request 19
       Request to obtain an activity and the details associated to that activity from the list by activityName.
       Expected Input: activityName (String)
       Expected Output: Activity and the details associated to that activity. (String) */

    @Override
    public Activity readActivityByName(String activityName) {
        System.out.println("Reading Activity with name: " + activityName);
        try {
            Activity activity = Activity.getActivityByName(activityName);
            return activity;
        }
        catch (NoResultException e) {
            return null;
        }
    }

    /* Request 20
        Request to add a new activity in the list.
        Expected Input: Activity (Object)
        Expected Output: Newly created Activity with the details associated to that activity. (String) */

    @Override
    public Activity createActivity(Activity activity) {
        Activity.saveActivity(activity);
        System.out.println("Activity successfully created.");
        return activity;
    }

    /* Request 21
        Request to edit an activity in the list.
        Expected Input: activityId (Integer) and Activity (Object)
        Expected Output: Edited activity with the details associated to that activity. (String) */

    @Override
    public Activity updateActivity(Activity activity) {
        int activityId = activity.getActivityId();
        Activity existing = Activity.getActivityById(activityId);
        if (existing == null) {
            System.out.println("Cannot find activity with id: " + activityId);
        } else {
            String updatedActivityName = activity.getActivityName();
            String updatedActivityDescription = activity.getActivityDescription();

            if (updatedActivityName != null) {
                existing.setActivityName(updatedActivityName);
            }
            if (updatedActivityDescription != null) {
                existing.setActivityDescription(updatedActivityDescription);
            }
            Activity.updateActivity(existing);
        }
        System.out.println("Successfully updated activity with id: " + activityId);
        return existing;
    }

     /* Request 22
        Request to delete an activity from the list.
        Expected Input: activityId (Integer)
        Expected Output: Response Message. */

    @Override
    public int deleteActivity(int activityId) {
        Activity activity = Activity.getActivityById(activityId);
        if (activity!=null) {
            Activity.removeActivity(activity);
            System.out.println("Successfully deleted Goal with id: " + activityId );
            return 0;
        } else {
            System.out.println("Cannot find user with id: " + activityId);
            return -1;
        }
    }
}
