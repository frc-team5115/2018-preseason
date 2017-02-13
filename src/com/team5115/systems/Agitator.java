package com.team5115.systems;

import com.team5115.Constants;
import com.team5115.robot.Robot;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;

public class Agitator {
	
	public boolean inuse;
	
	Victor agitator; 
	Victor feeder;
	
	public Agitator() {
		agitator = new Victor(Constants.AGITATOR);
		feeder = new Victor(Constants.FEEDER);
	}
	
	public void feed() {
		agitator.set(1);
		feeder.set(1);
	}
	
	public void starve() {
		agitator.set(-1);
		feeder.set(-1);
	}

	public void stop() {
		agitator.set(0);
		feeder.set(0);
	}
	

}