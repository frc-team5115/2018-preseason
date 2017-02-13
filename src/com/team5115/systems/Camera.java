package com.team5115.systems;

import com.team5115.Constants;
import com.team5115.robot.Robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;

public class Camera {
	
	Servo yServo;
	
	public static final int BOILER = 0;
	public static final int CLIMBER = 1;
	public static final int INTAKE = 2;
	
	public Camera() {
		yServo = new Servo(Constants.CAMERA_SERVO);
	}
	
	public void setAngle(int angle) {
		if (angle == BOILER) {
			yServo.setAngle(Constants.BOILER_ANGLE);
		} else if (angle == CLIMBER) {
			yServo.setAngle(Constants.CLIMBER_ANGLE);
		} else if (angle == INTAKE) {
			yServo.setAngle(Constants.INTAKE_ANGLE);
		}
	}
	
}