package com.team5115.statemachines;

import com.team5115.robot.InputManager;
import com.team5115.robot.Robot;
import com.team5115.Constants;

public class IntakeManager extends StateMachineBase {
	
	/**
	 * This state machine controls the Intake subsystem
	 */
	
	public static final int STOP = 0;
	public static final int INTAKE = 1;

	public void update() {
		switch (state) {
		case 0:
			Robot.intake.stop();
			break;
		case 1:
			Robot.intake.intake();
			break;
		}
	}
}
