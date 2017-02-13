package com.team5115.statemachines;

import com.team5115.robot.InputManager;
import com.team5115.robot.Robot;
import com.team5115.Constants;

public class AgitatorManager extends StateMachineBase {
	
	/**
	 * This state machine controls the Agitator subsystem which is used to move the balls in the container around
	 */

	public static final int STOP = 0;
	public static final int FEED = 1;	// Gives balls to the shooter
	public static final int STARVE = 2;	// Keeps balls away from the shooter

	public void update() {
		switch (state) {
		case STOP:
			Robot.agitator.stop();
			break;
		case FEED:
			Robot.agitator.feed();
			break;
		case STARVE:
			Robot.agitator.starve();
			break;
		}
	}
}
