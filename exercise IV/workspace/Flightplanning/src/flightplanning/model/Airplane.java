package flightplanning.model;

import javax.persistence.*;

@Entity
@Table(name = "Airplanes")
public class Airplane {
	
	@Id
	@Column(name = "AIRCRAFT_REGISTRATION_ID")
	private String mAircraftRegistrationID;
	
	@Column(name = "OWNER")
	private String mAircraftOwner;
	
	@Column(name = "AIRCRAFT_TYPE")
	private String mAircraftType;
	
	@OneToOne
    @PrimaryKeyJoinColumn
    private Flight mFlight;
		
	public Airplane(String aircraftRegistrationID, String aircraftOwner, String aircraftType) {
		mAircraftRegistrationID = aircraftRegistrationID;
		mAircraftOwner = aircraftOwner;
		mAircraftType = aircraftType;
	}
	
	public String getAircraftRegistrationID () {
		return mAircraftRegistrationID;
	}
	
	public String getAircraftOwner () {
		return mAircraftOwner;
	}
	
	public String getAircraftType () {
		return mAircraftType;
	}
}