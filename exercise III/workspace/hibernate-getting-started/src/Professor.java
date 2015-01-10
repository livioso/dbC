import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.*;


@Entity
@Table(name="PROFESSOR")
@AttributeOverrides({
    @AttributeOverride(name="mFirstName", column=@Column(name="FIRSTNAME")),
    @AttributeOverride(name="mLastName", column=@Column(name="LASTNAME"))
})
public class Professor extends AbstractPerson {

	public Professor(String firstName, String lastName) {
		super(firstName, lastName);
	}

}
