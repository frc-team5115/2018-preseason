package com.team5115.statemachines;

import com.team5115.robot.InputManager;
import com.team5115.robot.Roobit;
import com.team5115.Constants;
import com.team5115.PID;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive extends StateMachineBase {
	
	// a method that gets inputs from the joystic and turn x and y returns into left and right speeds 
	
		
		public static final int DRIVING = 1;
		public static final int STOP = 0;
		
		double speed, turn;
		
		

		
		public void update(){
			switch (state){
			case STOP:
				Roobit.drivetrain.drive(0, 0, 0);
			case DRIVING:
				if(!Roobit.drivetrain.inuse){
					
					//get sped
					speed = InputManager.getY() * Constants.TOP_SPEED * InputManager.getThrottle();
					turn = InputManager.getX() * Constants.TOP_SPEED * 0.5 * InputManager.getThrottle();

				}

				//robit define sped and turn using getsped
				Roobit.drivetrain.drive(speed, turn, 1);
				
				
			}
		}
}
