package com.team5115.statemachines;

import com.team5115.robot.InputManager;
import com.team5115.robot.Robot;

public class ClimberManager extends StateMachineBase {
	
	/**
	 * This state machine controls the Climber subsystem, which climbs up the rope during the endgame
	 */

	public static final int STOP = 0;
	public static final int CLIMB = 1;
	
	public void update() {
		switch(state) {
		case STOP:
			Robot.climber.stopClimb();
			if(InputManager.climb()) {
				setState(CLIMB);
			}
			break;
			
		case CLIMB:
			Robot.climber.climbUp();
			if (InputManager.cancel()) {
				setState(STOP);
			}
			break;
		}
	}
}
