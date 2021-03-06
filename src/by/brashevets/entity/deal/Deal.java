package by.brashevets.entity.deal;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "events")
public class Deal implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2338657657843770789L;
	@Id
	@Column(name = "idEvent")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "readiness")
	private String readiness;
	@Column(name = "importance")
	private String importance;
	@Column(name = "date")
	private String date;
	@Column(name = "name")
	private String nameOfDeal;
	@Column(name = "description")
	private String description;

	public String getReadiness() {
		return readiness;
	}

	public void setReadiness(String readiness) {
		this.readiness = readiness;
	}

	public String getImportance() {
		return importance;
	}

	public void setImportance(String importance) {
		this.importance = importance;
	}

	public String getNameOfDeal() {
		return nameOfDeal;
	}

	public void setNameOfDeal(String nameOfDeal) {
		this.nameOfDeal = nameOfDeal;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
