package com.team5115.statemachines;

public class AutoGearLeftBlue extends StateMachineBase {
	
	/**
	 * This state machine contains the routine for the gear path from the left on the blue team of the autonomous section
	 */
	
	public static final int INIT = 1;
	public static final int DRIVING = 2;
	public static final int AIMING = 3;
	
	AutoDrive firstleg;
	AutoDrive secondleg;
	AimGear ag;
	
	public AutoGearLeftBlue() {
		firstleg = new AutoDrive(5, 10, 9, 60, 1.5);
		secondleg = new AutoDrive(2.5, 5, 3, 0, 0);
		ag = new AimGear();
	}
	
	public void update() {
		switch (state) {
		case INIT:
			ag.setState(AimGear.START);
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
			ag.update();
			if (secondleg.isFinished()) {		//or sensor says it is finished
				secondleg.setState(AutoDrive.STOP);
			}
			break;
		}
	}

}
