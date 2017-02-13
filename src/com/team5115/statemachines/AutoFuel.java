package com.team5115.statemachines;

import com.team5115.robot.Robot;

public class AutoFuel extends StateMachineBase {
	
	/**
	 * This state machine contains the routine for the fuel path of the autonomous section
	 */
	
	public static final int INIT = 1;
	public static final int DRIVING = 2;
	public static final int AIMING = 3;
	public static final int SHOOTING = 4;
	
	AutoDrive firstleg;
	AutoDrive secondleg;
	AimFuel af;
	
	public AutoFuel() {
		firstleg = new AutoDrive(5, 10, 8.8, -135, 2);
		secondleg = new AutoDrive(2.5, 5, 11.1, 0, 0);
		af = new AimFuel();
	}
	
	public void update() {
		switch (state) {
		case INIT:
			af.setState(AimFuel.START);
			firstleg.setState(AutoDrive.START);
			secondleg.setState(AutoDrive.START);
			setState(DRIVING);
			break;
			
		case DRIVING:
			firstleg.update();
			if (firstleg.isFinished()) {
				firstleg.setState(AutoDrive.STOP);
				setState(AIMING);
			}
			break;
			
		case AIMING:
			secondleg.update();
			if (secondleg.isFinished()) {
				af.update();
			}
			if (af.aimed) {
				setState(SHOOTING);
			}
			break;
		case SHOOTING:
			Robot.fuel.setState(FuelManipulatorManager.SHOOT);
			break;
		}
	}

}
