package data.server.model;

import data.server.dao.DataServiceDao;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

/**
 * Created by bishruti on 1/4/16.
 */

@Entity  // Indicates that this class is an entity to persist in DB
@Table(name="Activity")
@NamedQuery(name="Activity.findAll", query="SELECT activity FROM Activity activity")
//@XmlRootElement
@XmlType(propOrder = { "activityId", "activityName", "activityDescription" })
@XmlAccessorType(XmlAccessType.FIELD)
@Cacheable(false)
public class Activity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id // Defines this attributed as the one that identifies the entity
    @GeneratedValue(generator = "sqlite_activity")
    @TableGenerator(name = "sqlite_activity")

    @Column(name = "activityId") // Id of Activity
    private int activityId;
    @Column(name = "activityName") // Name of Activity
    private String activityName;
    @Column(name = "activityDescription") // Description of Activity
    private String activityDescription;

    // Getters
    @XmlTransient
    public int getActivityId(){
        return activityId;
    }
    public String getActivityName(){
        return activityName;
    }
    public String getActivityDescription(){
        return activityDescription;
    }

    // Setters
    public void setActivityId(int activityId){
        this.activityId = activityId;
    }
    public void setActivityName(String activityName){
        this.activityName = activityName;
    }
    public void setActivityDescription(String activityDescription){
        this.activityDescription = activityDescription;
    }

    // Database Operations

    public static Activity getActivityById(int activityId) {
        EntityManager entityManager = DataServiceDao.instance.createEntityManager();
        Activity activity = entityManager.find(Activity.class, activityId);
        DataServiceDao.instance.closeConnections(entityManager);
        return activity;
    }

    public static List<Activity> getAll() {
        EntityManager entityManager = DataServiceDao.instance.createEntityManager();
        List<Activity> list = entityManager.createNamedQuery("Activity.findAll", Activity.class).getResultList();
        DataServiceDao.instance.closeConnections(entityManager);
        return list;
    }

    public static Activity saveActivity(Activity activity) {
        EntityManager entityManager = DataServiceDao.instance.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(activity);
        transaction.commit();;
        DataServiceDao.instance.closeConnections(entityManager);
        return activity;
    }

    public static Activity updateActivity(Activity activity) {
        EntityManager entityManager = DataServiceDao.instance.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        activity = entityManager.merge(activity);
        transaction.commit();
        DataServiceDao.instance.closeConnections(entityManager);
        return activity;
    }

    public static void removeActivity(Activity activity) {
        EntityManager entityManager = DataServiceDao.instance.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        activity = entityManager.merge(activity);
        entityManager.remove(activity);
        transaction.commit();
        DataServiceDao.instance.closeConnections(entityManager);
    }
}