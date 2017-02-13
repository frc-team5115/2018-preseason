package com.team5115.statemachines;

import com.team5115.robot.InputManager;
import com.team5115.robot.Robot;
import com.team5115.Constants;

public class FlywheelManager extends StateMachineBase {
	
	/**
	 * This state machine controls the flywheel shooter, which shoots the ball
	 */
	
	public static final int STOP = 0;
	public static final int SHOOT = 1;
	public static final int STARVE = 2;

	public void update() {
		switch (state) {
		case 0:
			Robot.flywheel.idle();
		case 1:
			if (Robot.flywheel.getFlywheelRPM() < Constants.OPTIMUM_FLYWHEEL_RPM) //figure out actual rpm later
					Robot.flywheel.spinUp();
			else {
					Robot.flywheel.idle();
			}
			break;
		case 2:
			Robot.flywheel.back();
		}
	}
}
