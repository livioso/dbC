package flightplanning.model;

import javax.persistence.*;

@Entity
@Table(name="Pilots")
@AttributeOverrides({
    @AttributeOverride(name="mFirstName", column=@Column(name="FIRSTNAME")),
    @AttributeOverride(name="mLastName", column=@Column(name="LASTNAME"))
})
public class Pilot extends Crew {

	@Column(name = "PILOTLICENCENUMBER") 
	private String mPilotLicenceNumber = "unknown"; 
	
	public Pilot(String firstName, String lastName, String employeeID) {
		super(firstName, lastName, employeeID);
	}

	public void setPilotLicenceNumber (String pilotLicenceNumber) {
		mPilotLicenceNumber = pilotLicenceNumber;
	}
	
	public String getPilotLicenceNumber () {
		return mPilotLicenceNumber;
	}	
}
