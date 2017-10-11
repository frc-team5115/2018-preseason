package com.team5115.systems;

import com.ctre.CANTalon;
//import com.kauailabs.navx.frc.AHRS;
import com.team5115.Constants;

public class DriveTrain {

	public boolean inuse;
	
	CANTalon frontleft;
	CANTalon frontright;
	CANTalon backleft;
	CANTalon backright;
	
	public double lastLeftSpeed = 0;
	public double lastRightSpeed = 0;
	
	public int direction;
	
	public DriveTrain(){
		
	frontleft = new CANTalon(Constants.FRONT_LEFT_MOTOR_ID);
	frontright = new CANTalon(Constants.FRONT_RIGHT_MOTOR_ID);
	backleft = new CANTalon(Constants.BACK_LEFT_MOTOR_ID);
	backright = new CANTalon(Constants.BACK_RIGHT_MOTOR_ID);
	
	backleft.changeControlMode(CANTalon.TalonControlMode.Follower);
	backright.changeControlMode(CANTalon.TalonControlMode.Follower);
	frontleft.set(backleft.getDeviceID());
	frontright.set(backright.getDeviceID());
	direction = 1;
	
	}
	public void drive(double speed, double turn, double throttle){
		double leftspeed = speed + turn;
		double rightspeed = speed - turn;
		
		if(Math.abs(leftspeed) > 1){
			leftspeed = 1;
		}
		if(Math.abs(rightspeed) > 1){
			rightspeed = 1;
		}
		
		backleft.set(leftspeed * throttle);
		backright.set(rightspeed * throttle);
	}
		
	public double leftDist(){
		double leftDist = -direction * backleft.getPosition();
		return leftDist / 1440 * 4 * Math.PI / 12;
	}
	
	public double rightDist(){
		double rightDist = direction * backright.getPosition();
		return rightDist / 1440 * 4 * Math.PI / 12;
		
	}
	
	public double distanceTraveled() {
		return (leftDist() + rightDist()) / 2;
	}
	
	public double leftSpeed() {
		double leftSpeed = backleft.getSpeed();
		return((leftSpeed * 4 * Math.PI * 10) / (1440 * 12));
	}
	
	public double rightSpeed() {
		double rightSpeed = backright.getSpeed();
		return ((rightSpeed * 4 * Math.PI * 10) / (1440 * 12));
	}
	public double averageSpeed() {
		return (frontright.getSpeed() + frontleft.getSpeed()) / 2;
	}
	 
	public double leftAcceleration() {
		double acceleration = (leftSpeed() - lastLeftSpeed) / Constants.DELAY;
		lastLeftSpeed = leftSpeed();
		return acceleration;
			
	}
		 
	public double rightAcceleration() {
		double acceleration = (rightSpeed() - lastRightSpeed) / Constants.DELAY;
		lastRightSpeed = leftSpeed();
		return acceleration;
	}	 
	
	// This method resets the values given by the encoders to a default of 0
	public void resetEncoders() {
		backleft.setPosition(0);
		backright.setPosition(0);
	}
	 
//	public double getYaw() {
//		return navx.getYaw();
//	}
//	    
//	public double getPitch() {
//		return navx.getPitch();
//	}
//	    
//	public double getRoll() {
//		return navx.getRoll();
//	}
//	  
//	public void resetGyro() {
//		 navx.reset();
//	}
	
	
	
}