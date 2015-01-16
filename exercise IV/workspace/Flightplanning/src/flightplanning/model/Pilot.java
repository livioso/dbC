package flightplanning.model;

public class Pilot extends Crew {

	private String mPilotLicenceNumber = "unknown"; 
	
	public Pilot(String firstName, String lastName, String employeeID) {
		super(firstName, lastName, employeeID);
	}

	public void updatePilotLicenceNumber (String pilotLicenceNumber) {
		mPilotLicenceNumber = pilotLicenceNumber;
	}
	
	public String getPilotLicenceNumber () {
		return mPilotLicenceNumber;
	}	
}