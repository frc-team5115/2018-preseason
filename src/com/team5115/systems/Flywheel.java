package com.team5115.systems;

import com.team5115.Constants;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;

public class Flywheel {
	/**
	 * This code is for the flywheel
	 * 
	 */
	public boolean inuse;

	public int direction;
	Victor flywheel;
	Encoder wheel;
	
	public Flywheel() {
		flywheel = new Victor(Constants.FLYWHEEL_MOTOR_ID);
	}
	
	public void spinUp() {
		flywheel.set(1);
	}
	
	public void idle() {
		flywheel.set(0);
	}
	
	public void back() {
		flywheel.set(-0.1);
	}
	
	public double getFlywheelRPM() {
		return wheel.getRate();
	}
}