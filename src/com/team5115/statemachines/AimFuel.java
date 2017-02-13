package com.team5115.statemachines;

import com.team5115.Constants;

import com.team5115.ForwardProfile;
import com.team5115.PID;
import com.team5115.TurnProfile;
import com.team5115.robot.Robot;

import edu.wpi.first.wpilibj.Timer;

public class AimFuel extends StateMachineBase {
	
	/**
	 * This state machine uses the vision from the raspberry pi to aim the robot to be aligned with the high goal
	 */

	public static final int STOP = 0;
	public static final int START = 1;	// Initialization state of the state machine
	public static final int AIMING = 2;	// State where the robot aims
	
	public boolean aimed;
	
	AutoDrive ad;
	PID pidController;
	
	double kp = 0;
	double ki = 0;
	double kd = 0;
	
	double x = Robot.xOffset;
	double y = Robot.yOffset;
	
	public double xError = x;
	public double yError = y;

	public AimFuel() {
		ad = new AutoDrive(5, 5, yError, xError, 0);
		pidController = new PID();
	}
	
	public void update() {
		switch(state) {
		case STOP:
			ad.setState(AutoDrive.STOP);
			break;
			
		case START:
			ad.setState(AutoDrive.START);
			aimed = false;
			
			setState(AIMING);
			
			break;
			
		case AIMING:
			ad.update();
			yError = y + pidController.getPID(0, y, kp, ki, kd);
			xError = x + pidController.getPID(0, x, kp, ki, kd);
			
			if (Math.abs(Robot.xOffset) < Constants.FUEL_TOLERANCE && Math.abs(Robot.yOffset) < Constants.FUEL_TOLERANCE) {
				aimed = true;
				ad.setState(AutoDrive.STOP);
			}
			
			break;
			
		}
	}

}



	

