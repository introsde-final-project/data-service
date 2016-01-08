package data.server.model;

import data.server.dao.DataServiceDao;
import data.server.model.User;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

/**
 * Created by bishruti on 1/2/16.
 */

// The persistent class for the "HealthMeasureHistory" database table.
@Entity // Indicates that this class is an entity to persist in DB
@Table(name="HealthMeasureHistory")
//Queries to obtain HealthMeasureHistory by using various attributes.
@NamedQueries({
	@NamedQuery(name="HealthMeasureHistory.findAll", query="SELECT healthMeasureHistory FROM HealthMeasureHistory healthMeasureHistory"),
	@NamedQuery(name="HealthMeasureHistory.getTypes", query="SELECT DISTINCT healthMeasureHistory.measureType FROM HealthMeasureHistory healthMeasureHistory"),
	@NamedQuery(name="HealthMeasureHistory.getHistoryByUserIdAndMeasureType", query="SELECT healthMeasureHistory FROM HealthMeasureHistory healthMeasureHistory WHERE healthMeasureHistory.user.uId = :uId AND healthMeasureHistory.measureType = :measureType"),
	@NamedQuery(name="HealthMeasureHistory.getHistoryByUserIdAndMeasureId", query="SELECT healthMeasureHistory FROM HealthMeasureHistory healthMeasureHistory WHERE healthMeasureHistory.user.uId = :uId AND healthMeasureHistory.measureType= :measureType AND healthMeasureHistory.hmhId = :measureId"),
})

//@XmlRootElement(name = "measureHistory")
@XmlType(propOrder = {"hmhId", "dateRegistered", "measureType", "measureValue", "measureValueType" })
@XmlAccessorType(XmlAccessType.FIELD)
@Cacheable(false)
public class HealthMeasureHistory implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id // Defines this attributed as the one that identifies the entity
	@GeneratedValue(generator="sqlite_healthmeasurehistory")
	@TableGenerator(name="sqlite_healthmeasurehistory")

	@Column(name="hmhId") // Id of HealthMeasureHistory
	private int hmhId;

	@Temporal(TemporalType.DATE)
	@Column(name="dateRegistered") // HealthMeasureHistory created date
	private Date dateRegistered;

	@Column(name="measureType") // Name of Measure
	private String measureType;

	@Column(name="measureValue") // Value of Measure
	private String measureValue;

	@Column(name="measureValueType") // Value Type of Measure
	private String measureValueType;

    // Creating relationship with User
    @XmlTransient
	@ManyToOne
	@JoinColumn(name = "uId", referencedColumnName = "uId")
	private User user;

	public HealthMeasureHistory() {
	}

    //Getters

	public int getHmhId() {
		return this.hmhId;
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

	public void setHmhId(int hmhId) {
		this.hmhId = hmhId;
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

	@XmlTransient
	public User getUser() {
	    return user;
	}

	public void setUser(User param) {
	    this.user = param;
	}

	// Database Operations
	public static HealthMeasureHistory getHealthMeasureHistoryById(int hmhId) {
		EntityManager entityManager = DataServiceDao.instance.createEntityManager();
		HealthMeasureHistory healthMeasureHistory = entityManager.find(HealthMeasureHistory.class, hmhId);
		DataServiceDao.instance.closeConnections(entityManager);
		return healthMeasureHistory;
	}

	public static HealthMeasureHistory getHealthMeasureHistoryByType(String type) {
		EntityManager entityManager = DataServiceDao.instance.createEntityManager();
		HealthMeasureHistory healthMeasureHistory = entityManager.find(HealthMeasureHistory.class, type);
		DataServiceDao.instance.closeConnections(entityManager);
		return healthMeasureHistory;
	}

	public static List<HealthMeasureHistory> getHealthHistoryOfUserByMeasureType(int uId, String measureType) {
		EntityManager entityManager = DataServiceDao.instance.createEntityManager();
		List<HealthMeasureHistory> list = new ArrayList<HealthMeasureHistory>();
		list = entityManager.createNamedQuery("HealthMeasureHistory.getHistoryByUserIdAndMeasureType", HealthMeasureHistory.class)
				.setParameter("uId", uId)
				.setParameter("measureType", measureType)
				.getResultList();
		DataServiceDao.instance.closeConnections(entityManager);
		return list;
	}

	public static List<HealthMeasureHistory> getMeasurebyHmhId(int uId, String measureType, int HmhId) {
		EntityManager entityManager = DataServiceDao.instance.createEntityManager();
		List<HealthMeasureHistory> list = new ArrayList<HealthMeasureHistory>();
		list = entityManager.createNamedQuery("HealthMeasureHistory.getHistoryByUserIdAndMeasureId", HealthMeasureHistory.class)
				.setParameter("uId", uId)
				.setParameter("measureType", measureType)
				.setParameter("measureId", HmhId)
				.getResultList();
		DataServiceDao.instance.closeConnections(entityManager);
		return list;
	}

	public static List<String> getTypes() {
		EntityManager entityManager = DataServiceDao.instance.createEntityManager();
		List<String> list = new ArrayList<String>();
		list = entityManager.createNamedQuery("HealthMeasureHistory.getTypes", String.class)
				.getResultList();
		DataServiceDao.instance.closeConnections(entityManager);
		return list;
	}

	
	public static List<HealthMeasureHistory> getAll() {
		EntityManager entityManager = DataServiceDao.instance.createEntityManager();
	    List<HealthMeasureHistory> list = entityManager.createNamedQuery("HealthMeasureHistory.findAll", HealthMeasureHistory.class).getResultList();
	    DataServiceDao.instance.closeConnections(entityManager);
	    return list;
	}
	
	public static HealthMeasureHistory saveHealthMeasureHistory(HealthMeasureHistory healthMeasureHistory) {
		EntityManager entityManager = DataServiceDao.instance.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(healthMeasureHistory);
        transaction.commit();
	    DataServiceDao.instance.closeConnections(entityManager);
	    return healthMeasureHistory;
	}
	
	public static HealthMeasureHistory updateHealthMeasureHistory(HealthMeasureHistory healthMeasureHistory) {
		EntityManager entityManager = DataServiceDao.instance.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        healthMeasureHistory = entityManager.merge(healthMeasureHistory);
        transaction.commit();
	    DataServiceDao.instance.closeConnections(entityManager);
	    return healthMeasureHistory;
	}
	
	public static void removeHealthMeasureHistory(HealthMeasureHistory healthMeasureHistory) {
		EntityManager entityManager = DataServiceDao.instance.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        healthMeasureHistory = entityManager.merge(healthMeasureHistory);
        entityManager.remove(healthMeasureHistory);
        transaction.commit();
	    DataServiceDao.instance.closeConnections(entityManager);
	}
}