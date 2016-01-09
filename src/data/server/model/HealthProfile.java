package data.server.model;

import data.server.dao.DataServiceDao;
import data.server.model.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

// The persistent class for the "HealthProfile" database table.

@Entity // Indicates that this class is an entity to persist in DB
@Table(name = "HealthProfile")
@NamedQueries({
        @NamedQuery(name = "HealthProfile.findAll", query = "SELECT healthProfile FROM HealthProfile healthProfile"),
        @NamedQuery(name = "HealthProfile.getHealthProfileByUser", query = "SELECT healthProfile FROM HealthProfile healthProfile WHERE healthProfile.user.uId = :uId")
})

//@XmlRootElement(name = "measure")
@XmlType(propOrder = {"idHealthProfile", "dateRegistered", "measureType", "measureValue", "measureValueType" })
@XmlAccessorType(XmlAccessType.FIELD)
@Cacheable(false)
public class HealthProfile implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id // Defines this attributed as the one that identifies the entity
    @GeneratedValue(generator="sqlite_healthprofile")
    @TableGenerator(name="sqlite_healthprofile")

    @Column(name = "idHealthProfile") // Id of HealthProfile
    private int idHealthProfile;

    @Temporal(TemporalType.DATE)
    @Column(name="dateRegistered") // Health Profile Created Date
    private Date dateRegistered;

    @Column(name = "measureType") // Measure of HealthProfile
    private String measureType;

    @Column(name = "measureValue") // Value of HealthProfile
    private String measureValue;

    @Column(name = "measureValueType") // Value Type of HealthProfile
    private String measureValueType;

    // Creating relationship with User
    @XmlTransient
    @ManyToOne
    @JoinColumn(name="uId",referencedColumnName="uId")
    private User user;

    public HealthProfile() {
    }

    //Getters
    @XmlTransient
    public int getIdHealthProfile() {
        return this.idHealthProfile;
    }

    public Date getDateRegistered() {
        return this.dateRegistered;
    }

    public String getMeasureType() {
        return this.measureType;
    }

    public String getMeasureValue() {
        return this.measureValue;
    }

    public String getMeasureValueType() {
        return this.measureValueType;
    }

    //Setters
    public void setIdHealthProfile(int idHealthProfile) {
        this.idHealthProfile = idHealthProfile;
    }

    public void setDateRegistered(Date dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public void setMeasureType(String measureType) {
        this.measureType = measureType;
    }

    public void setMeasureValue(String measureValue) {
        this.measureValue = measureValue;
    }

    public void setMeasureValueType(String measureValueType) {
        this.measureValueType = measureValueType;
    }

    // we make this transient for JAXB to avoid and infinite loop on serialization
    @XmlTransient
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Database operations

    public static HealthProfile getHealthProfileByPersonId(int uId) {
        EntityManager entityManager = DataServiceDao.instance.createEntityManager();
        HealthProfile healthProfile = entityManager.find(HealthProfile.class, uId);
        DataServiceDao.instance.closeConnections(entityManager);
        return healthProfile;
    }

    public static List<HealthProfile> getHealthProfileByPerson(int uId) {
        EntityManager entityManager = DataServiceDao.instance.createEntityManager();
        List<HealthProfile> list = new ArrayList<HealthProfile>();
        list = entityManager.createNamedQuery("HealthProfile.getHealthProfileByPerson", HealthProfile.class)
                .setParameter("uId", uId)
                .getResultList();
        DataServiceDao.instance.closeConnections(entityManager);
        return list;
    }

    public static HealthProfile getHealthProfileById(int idHealthProfile) {
        EntityManager entityManager = DataServiceDao.instance.createEntityManager();
        HealthProfile healthProfile = entityManager.find(HealthProfile.class, idHealthProfile);
        DataServiceDao.instance.closeConnections(entityManager);
        return healthProfile;
    }

    public static HealthProfile getHealthProfileByMeasure(String measure) {
        EntityManager entityManager = DataServiceDao.instance.createEntityManager();
        HealthProfile healthProfile = entityManager.find(HealthProfile.class, measure);
        DataServiceDao.instance.closeConnections(entityManager);
        return healthProfile;
    }

    public static List<HealthProfile> getAll() {
        EntityManager entityManager = DataServiceDao.instance.createEntityManager();
        List<HealthProfile> list = entityManager.createNamedQuery("HealthProfile.findAll", HealthProfile.class).getResultList();
        DataServiceDao.instance.closeConnections(entityManager);
        return list;
    }

    public static HealthProfile saveHealthProfile(HealthProfile healthProfile) {
        EntityManager entityManager = DataServiceDao.instance.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(healthProfile);
        transaction.commit();
        DataServiceDao.instance.closeConnections(entityManager);
        return healthProfile;
    }

    public static HealthProfile updateHealthProfile(HealthProfile healthProfile) {
        EntityManager entityManager = DataServiceDao.instance.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        healthProfile = entityManager.merge(healthProfile);
        transaction.commit();
        DataServiceDao.instance.closeConnections(entityManager);
        return healthProfile;
    }

    public static void removeHealthProfile(HealthProfile healthProfile) {
        EntityManager entityManager = DataServiceDao.instance.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        healthProfile = entityManager.merge(healthProfile);
        entityManager.remove(healthProfile);
        transaction.commit();
        DataServiceDao.instance.closeConnections(entityManager);
    }
}
