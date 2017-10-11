package com.team5115.systems;

import com.team5115.Constants;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;

public class TestThing {
	
	Victor motor;
	DigitalInput limit;
	
	public TestThing() {
		motor = new Victor(Constants.MOTOR_VICTOR);
		limit = new DigitalInput(Constants.LIMIT_SWITCH);
	}
	
	
	public void motorOff() {
		
		motor.set(0);
		
	}
	public void motorOn() {
		
		motor.set(1);
	}
	public boolean getLimit() {
		return limit.get();
	}
}
