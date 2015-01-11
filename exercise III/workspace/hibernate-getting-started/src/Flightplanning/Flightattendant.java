package Flightplanning;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.*;


@Entity
@Table(name="Flightattendants")
@AttributeOverrides({
    @AttributeOverride(name="mFirstName", column=@Column(name="FIRSTNAME")),
    @AttributeOverride(name="mLastName", column=@Column(name="LASTNAME"))
})
public class Flightattendant extends Crew {

	public Flightattendant(String firstName, String lastName, String employeeID) {
		super(firstName, lastName, employeeID);
		
		setStudentName(firstName + " " + lastName);
	}
	private String studentName;
	
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	/*
	@ManyToOne
	@JoinColumn(name="college_id")
	public College getCollege() {
		return college;
	}
	public void setCollege(College college) {
		this.college = college;
	}
	*/
}
