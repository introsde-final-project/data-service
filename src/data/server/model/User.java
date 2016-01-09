package data.server.model;

import data.server.dao.DataServiceDao;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by bishruti on 1/2/16.
 */

@Entity  // Indicates that this class is an entity to persist in DB
@Table(name="User")
@NamedQuery(name="User.findAll", query="SELECT user FROM User user")
//@XmlRootElement
@XmlType(propOrder = { "uId", "firstName", "lastName", "birthDate", "bloodGroup", "address", "measureType"})
@XmlAccessorType(XmlAccessType.FIELD)
@Cacheable(false)
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id // Defines this attributed as the one that identifies the entity
    @GeneratedValue(generator = "sqlite_user")
    @TableGenerator(name = "sqlite_user")

    @Column(name = "uId") // Id of the User
    private int uId;
    @Column(name = "firstName") // First Name of the User
    private String firstName;
    @Column(name = "lastName") // Last Name of the User
    private String lastName;
    @Temporal(TemporalType.DATE) // Defines the precision of the date attribute
    @Column(name = "birthDate") // Date of Birth of the User
    private Date birthDate;
    @Column(name = "bloodGroup") // Blood Group of the User
    private String bloodGroup;
    @Column(name = "address") // Address of the User
    private String address;

    // Creating relationship with HealthProfile
    @XmlElementWrapper(name = "currentHealth")
    @OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private List<HealthProfile> measureType;
    public List<HealthProfile> getMeasureType () {
        return measureType;
    }
    public void setMeasureType(List<HealthProfile> measureType) {
        this.measureType = measureType;
    }

    //Creating relationship with HealthMeasureHistory
    @XmlTransient
    @OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private List<HealthMeasureHistory> measureHistory;
    public List<HealthMeasureHistory> getMeasureHistory() {
        return measureHistory;
    }
    public void setMeasureHistory(List<HealthMeasureHistory> measureHistory) {
        this.measureHistory = measureHistory;
    }

    // Getters
    @XmlTransient
    public int getUId(){
        return uId;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getBirthDate(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        // Get the date today using Calendar object.
        return df.format(birthDate);
    }
    public String getBloodGroup() { return bloodGroup; }
    public String getAddress() { return address; }

    // Setters
    public void setUId(int idPerson){
        this.uId = uId;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public void setBirthDate(String birthDate) throws ParseException {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        Date date = format.parse(birthDate);
        this.birthDate = date;
    }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
    public void setAddress(String address) { this.address = address; }

    // Database Operations

    public static User getUserById(int uId) {
        EntityManager entityManager = DataServiceDao.instance.createEntityManager();
        User user = entityManager.find(User.class, uId);
        DataServiceDao.instance.closeConnections(entityManager);
        return user;
    }

    public static List<User> getAll() {
       EntityManager entityManager = DataServiceDao.instance.createEntityManager();
        List<User> list = entityManager.createNamedQuery("User.findAll", User.class).getResultList();
        DataServiceDao.instance.closeConnections(entityManager);
        return list;
    }

    public static User saveUser(User user) {
        appendHealthProfile(user);
        appendHealthMeasureHistory(user);
        EntityManager entityManager = DataServiceDao.instance.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(user);
        transaction.commit();;
        DataServiceDao.instance.closeConnections(entityManager);
        return user;
    }

    public static User updateUser(User user) {
        EntityManager entityManager = DataServiceDao.instance.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        user = entityManager.merge(user);
        transaction.commit();
        DataServiceDao.instance.closeConnections(entityManager);
        return user;
    }

    public static void removeUser(User user) {
        EntityManager entityManager = DataServiceDao.instance.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        user = entityManager.merge(user);
        entityManager.remove(user);
        transaction.commit();
        DataServiceDao.instance.closeConnections(entityManager);
    }

    private static User appendHealthProfile(User user) {
        java.util.Date date = new java.util.Date();
        for (HealthProfile healthProfile : user.measureType) {
            healthProfile.setUser(user);
            healthProfile.setDateRegistered(date);
        }
        return user;
    }

    private static User appendHealthMeasureHistory(User user) {
        java.util.Date date = new java.util.Date();
        user.measureHistory = new ArrayList<HealthMeasureHistory>(user.measureType.size());

        for (HealthProfile healthProfile : user.measureType) {
            HealthMeasureHistory healthMeasureHistory = new HealthMeasureHistory();
            healthMeasureHistory.setUser(user);
            healthMeasureHistory.setDateRegistered(date);
            healthMeasureHistory.setMeasureType(healthProfile.getMeasureType());
            healthMeasureHistory.setMeasureValue(healthProfile.getMeasureValue());
            healthMeasureHistory.setMeasureValueType(healthProfile.getMeasureValueType());
            user.measureHistory.add(healthMeasureHistory);
        }
        return user;
    }
}
