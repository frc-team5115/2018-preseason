package com.team5115;

public class PID {
	
	/**
	 * What is PID?
	 * PID stands for Proportional-Integral-Derivative
	 * It is a type of feedback loop that takes in an error value and tries to compensate for the error
	 * Proportional control changes the motor input as a function of some fraction of the error in speed
	 * Integral control changes the motor input as a function of some fraction of the error in distance travelled(or distance left to travel)
	 * Derivative control changes the rate of change of the motor input as a function of some fraction of the error in the motor's acceleration
	 */
	
	double error;
	double last_error = 0;
	double error_accum;
	double derror;
	double output;

	//takes values and converts it into an output
	public double getPID(double setpoint, double actual, double p, double i, double d) {
		error = setpoint - actual;
		// Calculation of the integral of the error
		error_accum += error;
		// Calculation of the derivative of the error
		derror = (error - last_error) / Constants.DELAY;
		last_error = error;
		output = (p * error) + (i * error_accum) + (d * derror);
		return output;
	}
}