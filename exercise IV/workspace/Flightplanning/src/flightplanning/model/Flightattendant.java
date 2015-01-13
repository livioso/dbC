package flightplanning.model;

import javax.persistence.*;

@Entity
@Table(name="Flightattendants")
@AttributeOverrides({
    @AttributeOverride(name="mFirstName", column=@Column(name="FIRSTNAME")),
    @AttributeOverride(name="mLastName", column=@Column(name="LASTNAME"))})
public class Flightattendant extends Crew {

	public Flightattendant(String firstName, String lastName, String employeeID) {
		super(firstName, lastName, employeeID);
	}
}