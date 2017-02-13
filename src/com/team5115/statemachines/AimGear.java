package com.team5115.statemachines;

import com.team5115.Constants;
import com.team5115.robot.Robot;

public class AimGear extends StateMachineBase {
	
	/**
	 * This state machine is used to aim to place the gear
	 * It aims to align with the retroreflective tape on either side of the gear peg
	 */
	
	public static final int STOP = 0;
	public static final int START = 1;
	public static final int AIMING = 2;
	
	public boolean aimed;
	
	AutoTurn at;
	
	public double xError = Robot.gearOffset;

	public AimGear() {
		at = new AutoTurn(5, 5, xError);
	}
	
	public void update() {
		switch(state) {
		case STOP:
			at.setState(AutoTurn.STOP);
		
			break;
			
		case START:
			at.setState(AutoTurn.START);
			aimed = false;
			
			if (Math.abs(Robot.gearOffset) > Constants.GEAR_TOLERANCE)  {
				setState(AIMING);
			} else {
				aimed = true;
			}
			
			break;
			
		case AIMING:
			at.update();
			xError = Robot.gearOffset;
			
			if (Math.abs(Robot.gearOffset) < Constants.GEAR_TOLERANCE)  {
				aimed = true;
				at.setState(AutoTurn.STOP);
			}
			break;
		
			
		}
	}

}
