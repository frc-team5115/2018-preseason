package com.team5115.statemachines;

import com.team5115.robot.InputManager;
import com.team5115.robot.Robot;
import com.team5115.Constants;

public class Drive extends StateMachineBase {
	
	/**
	 * You might be wondering why we are using State Machines instead of the built in wpilibj commands, so let me tell you a story
	 * It all started 15 years ago...
	 * Long story short, we now have state machines
	 * The state machine is how the robot class interfaces with the subsystem to control the robot
	 * The state machine can either have the states progressively being done in sequence, or it can have the driver choose which state to run at a time
	 * This is the state machine for the drivetrain
	 * You should put the inputs from InputManager in the state machine to control the actions of the subsystem
	 */
	
	// These are the state names which you reference when you use the setState() method instead of the actual number so the code is easier to understand
	public static final int STOP = 0;
	public static final int DRIVING = 1;

	// Define the variables for the state machine here
	double speed, turn, throttle;
	double dSpeed, dTurn;
	double turnPower;
	double lastSpeed = 0, lastTurn = 0;

	public void update() {
		switch (state) {
		case DRIVING:

			// STATE 1 -- DRIVING
			speed = InputManager.getY();
			turn = InputManager.getX();
			throttle = InputManager.getThrottle();

			 //Negative inertia. Increasing constants makes it more "repsonsive"
//			dSpeed = (speed - lastSpeed) / Constants.DELAY;
//			dTurn = (turn - lastTurn) / Constants.DELAY;
//			speed += Constants.NEG_INERTIA_SPEED * dSpeed;
//			turn += Constants.NEG_INERTIA_TURN * dTurn;
//			
//			lastSpeed = speed;
//			lastTurn = turn;

			
			// Turning in place and quickturn button
			if (speed == 0 || InputManager.quickTurn()) {
				turnPower = turn * Constants.QUICK_TURN_POWER;
			} else {
				// Keep in mind speed will always be in [-1, 1]
				turnPower = speed * turn;
			}

			Robot.drivetrain.drive(speed + turn, speed - turn, throttle);

			break;

		}
	}
}
