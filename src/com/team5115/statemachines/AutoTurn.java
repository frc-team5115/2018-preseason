package com.team5115.statemachines;

import com.team5115.ForwardProfile;
import com.team5115.PID;
import com.team5115.TurnProfile;
import com.team5115.robot.Robot;

import edu.wpi.first.wpilibj.Timer;

public class AutoTurn extends StateMachineBase {
	
	/**
	 * This state machine is similar to AutoDrive, but it ignores the distance parameter
	 * Instead, it turns in place rather than curving 
	 * Ideally, this would only be used in aiming, as curving while moving is more time efficient
	 */
	
	public static final int STOP = 0;
	public static final int START = 1;
	public static final int DRIVING = 2;
	
	TurnProfile tp;
	
	PID pidController;

	double t;
	double startTime;
	double error_left;
	double error_right;
	double v_left;
	double v_right;
	double a;
	
	double kv = 0.2;
	double ka = 0.1;
	double kp = 0;
	double ki = 0;
	double kd = 0;
	
	public AutoTurn(double maxv, double maxa, double angle) {
		tp = new TurnProfile(maxv, maxa, angle);
		pidController = new PID();
		
		a = angle;
		
	}
	
	public void update() {
		switch(state) {
		case START:
			startTime = Timer.getFPGATimestamp();
			setState(DRIVING);
			break;
			
		case DRIVING:
			
			t = Timer.getFPGATimestamp() - startTime;
			
			double accel_left = tp.getAcceleration(t);
			double vel_left = tp.getVelocity(t);
			double accel_right = -tp.getAcceleration(t);
			double vel_right = -tp.getVelocity(t);
			
			
			v_left = kv * vel_left + ka * accel_left + pidController.getPID(vel_left, Robot.drivetrain.leftSpeed(), kp, ki, kd);
			v_right = kv * vel_right + ka * accel_right + pidController.getPID(vel_right, Robot.drivetrain.rightSpeed(), kp, ki, kd);
			
			Robot.drivetrain.drive(v_left, v_right, 1);
			
			break;
			
		}
	}
	
	public boolean isFinished() {
		return (tp.getAcceleration(t) == 0 && tp.getVelocity(t) == 0);		//Will it work? Nobody knows...
	}

}
