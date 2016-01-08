package data.server.model;

import data.server.dao.DataServiceDao;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by bishruti on 1/4/16.
 */

@Entity  // Indicates that this class is an entity to persist in DB
@Table(name="Goal")
@NamedQuery(name="Goal.findAll", query="SELECT goal FROM Goal goal")
//@XmlRootElement
@XmlType(propOrder = { "goalId", "goalDescription" })
@XmlAccessorType(XmlAccessType.FIELD)
@Cacheable(false)
public class Goal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id // Defines this attributed as the one that identifies the entity
    @GeneratedValue(generator = "sqlite_goal")
    @TableGenerator(name = "sqlite_goal")

    @Column(name = "goalId") // Id of Goal
    private int goalId;
    @Column(name = "goalDescription") // Description of Goal
    private String goalDescription;

    // Getters
    @XmlTransient
    public int getGoalId(){
        return goalId;
    }
    public String getGoalDescription(){
        return goalDescription;
    }

    // Setters
    public void setGoalId(int goalId){
        this.goalId = goalId;
    }
    public void setGoalDescription(String goalDescription){
        this.goalDescription = goalDescription;
    }


    // Database Operations

    public static Goal getGoalById(int goalId) {
        EntityManager entityManager = DataServiceDao.instance.createEntityManager();
        Goal goal = entityManager.find(Goal.class, goalId);
        DataServiceDao.instance.closeConnections(entityManager);
        return goal;
    }

    public static List<Goal> getAll() {
        EntityManager entityManager = DataServiceDao.instance.createEntityManager();
        List<Goal> list = entityManager.createNamedQuery("Goal.findAll", Goal.class).getResultList();
        DataServiceDao.instance.closeConnections(entityManager);
        return list;
    }

    public static Goal saveGoal(Goal goal) {
        EntityManager entityManager = DataServiceDao.instance.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(goal);
        transaction.commit();;
        DataServiceDao.instance.closeConnections(entityManager);
        return goal;
    }

    public static Goal updateGoal(Goal goal) {
        EntityManager entityManager = DataServiceDao.instance.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        goal = entityManager.merge(goal);
        transaction.commit();
        DataServiceDao.instance.closeConnections(entityManager);
        return goal;
    }

    public static void removeGoal(Goal goal) {
        EntityManager entityManager = DataServiceDao.instance.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        goal = entityManager.merge(goal);
        entityManager.remove(goal);
        transaction.commit();
        DataServiceDao.instance.closeConnections(entityManager);
    }
}




