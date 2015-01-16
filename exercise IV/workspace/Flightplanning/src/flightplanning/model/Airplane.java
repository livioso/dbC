package flightplanning.model;

public class Airplane {
	
	private String mAircraftRegistrationID;
	
	private String mAircraftOwner;
	
	private String mAircraftType;
		
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