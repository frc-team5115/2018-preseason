package com.team5115;

public class Constants {

	/**
	 * This class exists solely to store constant values that will remain the same for the whole robot
	 * While referencing this class is not necessary, it is a good organizational habit
	 * That means it's mandatory because if you don't do it, brian will be sad
	 */
	
	public static final double DELAY = 0.005;

	// Buttons and Axes
	public static final int AXIS_X = 1;
	public static final int AXIS_Y = 0;
	public static final int AXIS_Y_CAM = 6;
	public static final int AXIS_X_CAM = 5;
	public static final int BUTTON_QUICK_TURN = 9;
	public static final int BUTTON_AIM_FUEL = 5;
	public static final int BUTTON_AIM_GEAR = 3;
	public static final int BUTTON_SHOOT = 1;
	public static final int BUTTON_CANCEL = 7;
	public static final int BUTTON_CLIMB = 6;
	public static final int BUTTON_SWITCH_DIRECTION = 2;
	public static final double JOYSTICK_DEADBAND = 0.1;
	public static final int JOYSTICK_EXPO = 2;

	// Motors
	public static final int FRONT_LEFT_MOTOR_ID = 3;
	public static final int FRONT_RIGHT_MOTOR_ID = 4;
	public static final int BACK_LEFT_MOTOR_ID = 1;
	public static final int BACK_RIGHT_MOTOR_ID = 2;
	public static final int FLYWHEEL_MOTOR_ID = 5;
	public static final int INTAKE_VICTOR = 1;
	public static final int AGITATOR = 2;
	public static final int FEEDER = 3;
	public static final int CLIMBER_VICTOR = 4;
	public static final int CAMERA_SERVO = 6;

	// Driving
	public static final double THROTTLE = 0.5;
	public static final double QUICK_TURN_POWER = 1;
	public static final double NEG_INERTIA_TURN = .002;
	public static final double NEG_INERTIA_SPEED = .001;
	
	// Sensors
	
	
	//Directions
	public static final int DIR_ARM = -1;
	public static final int DIR_BALL = 1;
	
	//other constants
	public static final double OPTIMUM_FLYWHEEL_RPM = 12345;	//figure out actual rpm later

	public static final double FUEL_TOLERANCE = 10;	//figure out actual value later
	
	public static final double GEAR_TOLERANCE = 5; //figure out actual value later;
	
	public static final double BOILER_ANGLE = 100;
	public static final double CLIMBER_ANGLE = 135;
	public static final double INTAKE_ANGLE = 80;
	
}