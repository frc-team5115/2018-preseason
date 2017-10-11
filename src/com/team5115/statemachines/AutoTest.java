package com.team5115.statemachines;

public class AutoTest extends StateMachineBase {
	AutoDrive firstLeg;
	AutoTurn rotation;
	AutoDrive secondLeg;
	public static final int INIT = 0;
	public static final int DRIVING1 = 1;
	public static final int TURN = 2;
	public static final int DRIVING2 = 3;



	public AutoTest() {
		firstLeg = new AutoDrive(1, 1, 5, 0, 0);
		rotation = new AutoTurn(1, 1, 180);
		secondLeg = new AutoDrive(1, 1, 5, 0, 0);
	}
	public void update() {
		switch(state){
		case INIT: 
			firstLeg.setState(AutoDrive.START);
			rotation.setState(AutoTurn.START);
			secondLeg.setState(AutoDrive.START);
			state = DRIVING1;
			break;
		case DRIVING1:
			firstLeg.setState(AutoDrive.DRIVING);
			if(firstLeg.isFinished()) {
				firstLeg.setState(AutoDrive.STOP);
				state = TURN;
			}
			break;	
		case TURN:
			rotation.setState(AutoTurn.DRIVING);
			if(rotation.isFinished()){
				rotation.setState(AutoTurn.STOP);
				state = DRIVING2;
			}
			break;
		case DRIVING2:
			secondLeg.setState(AutoDrive.DRIVING);
			if(secondLeg.isFinished()) {
				secondLeg.setState(AutoDrive.STOP);
				state = 4;
			}
			break;	

		}
	}
}
