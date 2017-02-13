package com.team5115.statemachines;

import com.team5115.robot.InputManager;
import com.team5115.robot.Robot;

public class SwitchDirection extends StateMachineBase {
	
	/**
	 * All that this state machine does is switches the direction that the robot is being driven in
	 */
	
	public void update() {
		switch(state) {
		default:
			if (InputManager.switchDirection()) {
				Robot.drivetrain.direction *= -1;
			}
		}
	}

}
