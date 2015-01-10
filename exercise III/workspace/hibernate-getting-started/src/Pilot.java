import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
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
