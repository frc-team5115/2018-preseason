package com.team5115.systems;

import com.team5115.Constants;

import edu.wpi.first.wpilibj.Victor;

public class Climber {

	Victor climb;
	
	public Climber() {
		climb = new Victor(Constants.CLIMBER_VICTOR);
	}
	
	public void climbUp() {
		climb.set(1);
	}
	
	public void stopClimb() {
		climb.set(0);
	}
}
