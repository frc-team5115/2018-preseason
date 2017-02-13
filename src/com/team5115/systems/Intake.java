package com.team5115.systems;

import com.team5115.Constants;
import com.team5115.robot.Robot;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;

public class Intake {
	
	public boolean inuse;

	public int direction;
	
	Victor intake;
	double intakeSpeed;
	
	public Intake() {
		intake = new Victor(Constants.INTAKE_VICTOR);
		intakeSpeed = Robot.drivetrain.averageSpeed();
	}
	
	public void intake() {
		if (intakeSpeed < 0) {
			intake.set(0.3);
		}
		else  if (intakeSpeed + 0.5 < 1){  
			intake.set(intakeSpeed + 0.5);
		}
		else {
			intake.set(1);
		}
	}

	public void stop() {
		intake.set(0);
	}
	

}