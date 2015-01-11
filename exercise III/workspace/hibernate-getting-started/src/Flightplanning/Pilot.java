package Flightplanning;
import javax.persistence.*;


@Entity
@Table(name="Pilots")
@AttributeOverrides({
    @AttributeOverride(name="mFirstName", column=@Column(name="FIRSTNAME")),
    @AttributeOverride(name="mLastName", column=@Column(name="LASTNAME"))
})
public class Pilot extends Crew {

	public Pilot(String firstName, String lastName, String employeeID) {
		super(firstName, lastName, employeeID);
	}

}
