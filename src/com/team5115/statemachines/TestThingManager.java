package com.team5115.statemachines;

import com.team5115.robot.InputManager;
import com.team5115.robot.Roobit;

public class TestThingManager extends StateMachineBase {
	
	/**
	 * This state machine controls the test thing
	 */
	public static final int MOTOROFF = 0;
	public static final int MOTORON = 1;
	
	public void update() {
		switch(state) {
		case MOTOROFF:
			Roobit.tt.motorOff();
			if(Roobit.tt.getLimit() ){
				state = MOTORON;
			}
			break;
		case MOTORON:
			Roobit.tt.motorOn();
			if(!InputManager.testThing() ){
				state = MOTOROFF;
				
			}
			
			break;
		}
	}

	

}
