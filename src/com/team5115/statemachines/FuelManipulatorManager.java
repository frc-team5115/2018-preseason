package com.team5115.statemachines;

import com.team5115.robot.InputManager;
import com.team5115.robot.Robot;
import com.team5115.Constants;

public class FuelManipulatorManager extends StateMachineBase {
	
	/**
	 * This is the top level state machine which controls all of the fuel manipulation of the robot
	 * There are three steps of fuel manipulation: stopped, intaking, and shooting
	 * This state machine controls all of the others in the fuel manipulation loop
	 */
	
	public static final int STOP = 0;
	public static final int INTAKE = 1;
	public static final int AIMING = 2;
	public static final int SHOOT = 3;
	
	AgitatorManager am;
	FlywheelManager fm;
	IntakeManager im;
	AimFuel aim;
	
	public FuelManipulatorManager() {
		im = new IntakeManager();
		am = new AgitatorManager();
		fm = new FlywheelManager();
		aim = new AimFuel();

	}

	public void update() {
		switch (state) {
		case STOP:
			// stop
			im.setState(IntakeManager.STOP);
			am.setState(AgitatorManager.STOP);
			fm.setState(FlywheelManager.STOP);
			
			im.update();
		    am.update();
		    fm.update();
		    break;
		    
		case INTAKE:
			// intake
			im.setState(IntakeManager.INTAKE);
			am.setState(AgitatorManager.STARVE);
		    fm.setState(FlywheelManager.STARVE);
		    
		    im.update();
		    am.update();
		    fm.update();
		    
		    if (InputManager.aimFuel()) {
		    	aim.setState(AimFuel.START);
		    	setState(AIMING);
		    }
		    
			break;
			
		case AIMING:
			// aim
			aim.update();
		    
		    im.update();
		    am.update();
		    fm.update();
			if(InputManager.shoot() && aim.aimed) {
				aim.setState(AimFuel.STOP);
		    	setState(SHOOT);
		    }
			break;
			
		case SHOOT:
			// shoot
			im.setState(IntakeManager.INTAKE);
			am.setState(AgitatorManager.FEED);
			fm.setState(FlywheelManager.SHOOT);

			im.update();
			am.update();
			fm.update();
			
			if(InputManager.cancel()) {
				setState(INTAKE);
			}
			
			break;
		}
	}
}
