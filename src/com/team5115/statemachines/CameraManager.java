package com.team5115.statemachines;

import com.team5115.robot.InputManager;
import com.team5115.robot.Robot;
import com.team5115.systems.Camera;

public class CameraManager extends StateMachineBase {
	
	/**
	 * This state machine controls the Camera subsystem, which is used to move the driver camera
	 */
	
	// These states are for the different angles the camera would be pointing at
	public static final int BOILER = 0;
	public static final int CLIMBER = 1;
	public static final int INTAKE = 2;
	
	public void update() {
		switch(state) {
		case BOILER:
			Robot.camera.setAngle(Camera.BOILER);
			if (InputManager.getCamX() == -1) {
				setState(CLIMBER);
			} else if (InputManager.getCamX() == 1) {
				setState(INTAKE);
			}
		case CLIMBER:
			Robot.camera.setAngle(Camera.CLIMBER);
			if (InputManager.getCamY() == -1) {
				setState(BOILER);
			} else if (InputManager.getCamX() == 1) {
				setState(INTAKE);
			}
		case INTAKE:
			Robot.camera.setAngle(Camera.INTAKE);
			if (InputManager.getCamY() == -1) {
				setState(BOILER);
			} else if (InputManager.getCamX() == -1) {
				setState(CLIMBER);
			}
		}
	}
	
}
