package com.team5115.robot;

import com.team5115.Constants;

import edu.wpi.first.wpilibj.Joystick;

public class InputManager {
	
/**
 * InputManager is the place where inputs from the joystick go to die
 * Here, they are converted into methods for the rest of the code
 * In order to reference a joystick input, you must reference the InputManager class
 * If don't really understand Getters and Setters you should go to Stack overflow or try (through trial and error) making them in a new project to see how they work 
 */
	
	static Joystick joy = new Joystick(0);
	
	//The following methods deal with the basic driving functionalities
	public static double getX() {
		return -treatAxis(joy.getRawAxis(Constants.AXIS_X));
	}
	
	public static double getY() {
		return -treatAxis(joy.getRawAxis(Constants.AXIS_Y));
	}
	
	//These methods are controlled by the nub on the top of the joystick
	public static double getCamX() {
		if (joy.getPOV() == -1) { return 0; }
		return Math.sin(Math.toRadians(joy.getPOV()));
	}
	
	public static double getCamY() {
		if (joy.getPOV() == -1) { return 0; }
		return -1 * Math.cos(Math.toRadians(joy.getPOV()));
	}
	
	public static double getThrottle() {
		// Joystick give 1 to -1 but we need 0 to 1
		return (1 - joy.getThrottle()) / 2;
	}

	public static boolean quickTurn() {
		return joy.getRawButton(Constants.BUTTON_QUICK_TURN);
	}
	
	// Handles squaring and deadband
	public static double treatAxis(double val) {
		val = Math.signum(val) * Math.pow(val, Constants.JOYSTICK_EXPO);

		if (Math.abs(val) < Constants.JOYSTICK_DEADBAND)
			val = 0;

		return val;
	}
	
	/**
	 * The following methods are used for button inputs
	 * In order to use these methods use the following format in your state machines:
	 * if (InputManager.action()) {
	 * do action;
	 * }
	 * You can find the constants in the Constants class
	 * When creating a method start with a lowercase letter 
	 */
	
	public static boolean switchDirection() {
		return joy.getRawButton(Constants.BUTTON_SWITCH_DIRECTION);
	}
		
	public static boolean aimFuel() {
		return joy.getRawButton(Constants.BUTTON_AIM_FUEL);
	}
	
	public static boolean aimGear() {
		return joy.getRawButton(Constants.BUTTON_AIM_GEAR);
	}
	
	public static boolean shoot() {
		return joy.getRawButton(Constants.BUTTON_SHOOT);
	}
	
	public static boolean climb() {
		return joy.getRawButton(Constants.BUTTON_CLIMB);
	}

	public static boolean cancel() {
		return joy.getRawButton(Constants.BUTTON_CANCEL);
	}

}