/*
if(victory == FALSE) 
	win;
else 
	win anyway;
*/

package com.team5115.robot;

import com.team5115.Constants;
import com.team5115.PID;
import com.team5115.Prefs;
import com.team5115.statemachines.AimGear;
import com.team5115.statemachines.AimGearManager;
import com.team5115.statemachines.Auto;
import com.team5115.statemachines.AutoDrive;
import com.team5115.statemachines.CameraManager;
import com.team5115.statemachines.ClimberManager;
import com.team5115.statemachines.Drive;
import com.team5115.statemachines.TestThingManager;
import com.team5115.statemachines.SwitchDirection;
import com.team5115.systems.Camera;
import com.team5115.systems.Climber;
import com.team5115.systems.DriveTrain;
import com.team5115.systems.TestThing;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Roobit extends IterativeRobot {
	
/**
 * The Robot class handles the basic functions of the robot
 * Subsystems and State Machines are created and run during the various stages of operation
 * Robot has 3 distinct phases, autonomous, teleop, and disabled
 * Autonomous - Robot runs by itself
 * Teleop - Robot is controlled by the driver
 * Disabled - Robot is off 
 * (Note: If you have no clue as to what you're doing, there is a Google slides presentation on the shared FRC files in your drive named "How To code" )
 */
	
	/**
	 * First, we define all of the subsystems, state machines, and other variables
	 * To define an object or variable, follow this format:
	 * public static class name;
	 * e.g. public static DriveTrain dt;
	 * class - The class of the object you are creating e.g. DriveTrain, int, double, etc.
	 * name - Any name you want, but make it something meaningful like an acronym
	 * Don't forget to end your lines with semicolons(;)
	 */
	
	// Define subsystems
	public static DriveTrain drivetrain;
	public static Climber climber;
	public static Camera camera;
	public static TestThing tt;
	
	// Define state machines
	public static Drive hd;
	public static AimGearManager agm;
	public static ClimberManager cm;
	public static CameraManager cam;
	public static SwitchDirection sd;
	public static AutoDrive ad;
	public static Auto auto;
	public static AimGear ag;
	public static TestThingManager ttm;
	
	
	// Define other stuff
	public static NetworkTable nt;
	public static double xOffset;
	public static double gearOffset;
	public static double yOffset;
	public static int team;
	public static int action;
	public static int position;
	public static SendableChooser<Integer> teamChooser;
	public static SendableChooser<Integer> positionChooser;
	public static SendableChooser<Integer> actionChooser;

	// Initialization phase of the robot class
	// This method runs once when the robot turns on and does not run again until the robot reboots
	public void robotInit() {
	// Initialize objects and variables here
		
		/**
		 * Constructing an object runs the initialization method of the class
		 * To construct an object, follow this format:
		 * name-of-object = new class();
		 * e.g. dt = new DriveTrain();
		 * To construct a variable, give it a default value
		 * e.g. var = 0;
		 * In this case, 0 is the default value that you set it to initially
		 * You can also give the constructor variables like you would any other method
		 * e.g. object = new Class(20);
		 * Make sure that the value you give it is consistent with what the constructor method in the class asks for
		 */
		
		// Initialize subsystems
		drivetrain = new DriveTrain();

		climber = new Climber();
		camera = new Camera();
		
		// Initialize state machines
		hd = new Drive();
		agm = new AimGearManager();
		cm = new ClimberManager();
		cam = new CameraManager();
		sd = new SwitchDirection();
		
		// Initialize variables
		xOffset = 0;
		yOffset = 0;
		gearOffset = 0;
		
		// Initialize network tables and SmartDashboard choosers for autonomous
		nt = NetworkTable.getTable("pi");
    	nt.putNumber("rioStatus", 0);    	
    	positionChooser = new SendableChooser<Integer>();
    	actionChooser = new SendableChooser<Integer>();
    	teamChooser = new SendableChooser<Integer>();
    	teamChooser.addDefault("Red", 1);
    	teamChooser.addObject("Blue", 2);
    	actionChooser.addDefault("Gear", 1);
    	actionChooser.addObject("Fuel", 2);
    	positionChooser.addDefault("Left", 3);
    	positionChooser.addObject("Center", 4);
    	positionChooser.addObject("Right", 5);
    	SmartDashboard.putData("Action", actionChooser);
    	SmartDashboard.putData("Position", positionChooser);
    	SmartDashboard.putData("Team", teamChooser);
	}

	// Runs once when the autonomous phase of the game starts
	public void autonomousInit() {
	// Initialize autonomous state machines here
		
		// Gets the selected values from the SmartDashboard
		team = (int) teamChooser.getSelected();
		position = (int) positionChooser.getSelected();
		action = (int) actionChooser.getSelected();
		
		// Initializing auto state machine here because team, action, and position are only made in Auto Init
		auto = new Auto(team, action, position);
		ag = new AimGear();
		
		
		
		/**
		 * To initialize a state machine, follow this format:
		 * object.setState(initialState)
		 * e.g. dt.setState(DriveTrain.START);
		 * Per this example, the initialState value should come from the class of the object
		 * You could just give it a normal integer like 0 or 1, but this is nicer organizationally
		 */
		
		auto.setState(Auto.INITIALIZE);
		ag.setState(AimGear.START);
		
	}

	//Runs periodically while the game is in the autonomous phase
	public void autonomousPeriodic() {
		
		// Gets values from the raspberry pi
		nt.putNumber("riostatus", 1);
		xOffset = nt.getNumber("xOffset", 0);
		yOffset = nt.getNumber("yOffset", 0);
		gearOffset = nt.getNumber("gearOffset", 0);
		
		// Puts values onto the SmartDashboard
		SmartDashboard.putNumber("X Offset", xOffset);
		SmartDashboard.putNumber("Y Offset", yOffset);
		SmartDashboard.putNumber("gearOffset", gearOffset);
		
		
		/**
		 * Updating the state machine runs all of the stuff in it
		 * To update a state machine, simply follow this format:
		 * object.update();
		 * e.g. dt.update();
		 */
		
		// Update state machines here
		auto.update();
		//ag.update();
		//af.update();
	
		
//		SmartDashboard.putNumber("Left Speed", drivetrain.leftSpeed());
//		SmartDashboard.putNumber("Right Speed", drivetrain.rightSpeed());
		
		Timer.delay(Constants.DELAY);
	}

	// Runs once when the game enters the driver operated stage
	public void teleopInit() {
	// Initialize state machines here for teleop
		hd.setState(Drive.DRIVING);
		agm.setState(AimGearManager.DRIVING);
		cm.setState(ClimberManager.STOP);
		ttm.setState(TestThingManager.MOTOROFF);
		drivetrain.inuse = false;
	}

	// Runs periodically when the game is in the driver operated stage
	public void teleopPeriodic() {
		// Gets values from the raspberry pi
		nt.putNumber("riostatus", 1);
		xOffset = nt.getNumber("xOffset", 5);
		yOffset = nt.getNumber("yOffset", 5);
		gearOffset = nt.getNumber("gearOffset", 0);
		
		// Puts values onto the SmartDashboard
		SmartDashboard.putNumber("X Offset", xOffset);
		SmartDashboard.putNumber("Y Offset", yOffset);
		SmartDashboard.putNumber("gearOffset", gearOffset);
//		System.out.println("X offset: " + xOffset);
//		System.out.println("Y offset: " + yOffset);
		
		// Put update commands for state machines here
		hd.update();
		agm.update();
		cm.update();
		cam.update();
		sd.update();
		
		Timer.delay(Constants.DELAY);
		
	}
	
	// Runs when the robot is disabled
	public void disabledInit() {
	// Set all state machines that run during teleop to the off state
		hd.setState(Drive.STOP);
	}

	// Runs periodically while the robot is disabled
	public void disabledPeriodic() {
		Timer.delay(Constants.DELAY);
	}

}

// This year's code brought to you by:
// Rohan and Takeshi and Abraham

                                                                                                                                                             
                                           //..``               `'#@@@@@@@+`                                                                                                                  
                                     //`'@@@@@@@@@@@@#`       @@@@#:``  `;@@@'`                                                                                                               
                                  //`;@@#.          ;@@@'   @@+             .@@@                                                                                                              
                                 //+@@                 :@@+@'                  @@+                                                                                                            
                              //.@@                     :@#                    `#@                                                                                                           
                             //@@`                        @@                     @@                                                                                                          
                           //`@#                   ;@@@@@@@@#:            .''''''.@@                                                                                                         
                          //.@,                 +@@@:``` `@@@@@@@+.  `#@@@@@@@#@@@@@@@@@,                                                                                                    
                        //`@,               .@@+    .@@@@;```` `:@@@@+`         ;#@@@@@@@@@.                                 `.'@@@@@@@@@@@@@@#:                                            
                         //@.              :@@,    #@@;             +@`      :@@@#'``   ` ,#@@ `                           ;@@@@#'`        ```.#@@@'`                                        
                        //@'              @@.   `@@'                 +@  ``#@@,              ;@#                        `#@@:                     :@@+                                       
                       //#@             ;#+    #@.      `.+@@@@@@@@@#,@ .@@+       ,++@@@@++,  @@                      #@#                          `@@                                      
                       //@             +@     @#      #@@;           #@@@.     .+@@@,,` ``,#@@@.@`                   .@@                              @@`                                    
                      //##            @@    :@`    .@@,                #@   ;@@#:             '@@'                  ,@;                                @@`                                   
                    //:@            +@    :@     @@                    '@;@@                  `+@.                 @'                                  @@                                   
                    //@:           '@    `@    ;#;                      '@                      '@,               #@                                   .@                                   
                   //,@            @   ` @    @@                         @@                      +@               @;     `     '       #@+  @@@+        @:                                  
                  //@'           @'    @    @'     +@@#:                 @`   `@@@@@+            @@             '@      :     '.   @ @@#@''@  @.       +@                                  
                  //@            @    @.   @#    @@@@@@@@                @@  '@@@@@@@@           .@             @@      #`    ;'   +@@   @@   ;#       .@                                  
                 //;@           +.    #   @@    #@@@@@@@@@               ,@ :@@@@@@@@@@           @.            @:      #,    .#   ;@`   @@    @        @                                  
                //`@#           @    @   ;@    @@@@@@@@:#@@               @ @@@@@@@@@@@:          @#            @`      ;;     @    @    #@    @        @                                  
                 //@;          ,         @`   ,@@@@@@ `  #@               @:@@@@@@   ;@@          @@            @       ,'     @    @    '#    @        @                                  
                 //@,          ;        @@    @@@@@@+    .@               @ #@@@@@    @@         @#            @`      `@    @@    @    ;@    @        @,     `                           
                 //@                    @     @@@@@@`    '@.              @ #@@@@#`    @'         @#            @'       @@, #@@`   @,   .@    @        #@@@@@@@@@@@@:                     
                 //@                   @+     @@@@@@,    @@               @+@@@@@     #@          @;          '@@@        +@@@`@'   #;`   ``   '                    #@@@                   
                //;@,                  @      @@@@@@@#+@@@@              ;@:@@@@@@@@@@@#          @          @@,                `                                      @@@                 
              //#@@@`               '@@;      @@@@@@@@@@@@,              @, @@@@@@@@@@#          :@        :@@                                                          .@@                
             //;@#  @#             +@:         :@@@@@@@@@@#              .@  ;@@@@@@@@#           @+       ,@'`                                                            @@               
            //@@     +            '#            @@@@@@@@@,               @.   ;@@@@@@.           '@       .@'  `                                                            @@              
            //@+                   '@:   +        :@@@@@'                ##      ;+'             ;@        @#                                                                 @#             
          //;@,                     +@@@@#@`                            ,@@                     #@`       +@                                               ;                  ;@             
        // '@`                            ;@#                         `@+ @                   ,@@@        @'         #      +                              @                   @@            
       //: @`                               @@:                      #@``@#                 :@@..@       '@          @      @                              +;             `    ;@            
       //@,                             `   .@@#                 ;@@,  ;@@@            '@@@@,  @+       @#          @      :'                             ;#           .       @+            
      //@+                              +@.  `.#@@+`         `+@@+    ##  @@@@@@@@@@@@@@;     @@  `    `@`          @       @             ``              .@    @#  .@@@@      #@           
     //:@                                `@@`    `@@@@@#@@@@@@;     ,@+   `@'               #@;        '@           @       @           `@@@+       ,,     @   @@   @,  @+     .@           
     //@:                                  +@@,      `.;,.`       ,@@      `@@       `  .#@#@#         @@           @       @           #. .@.  ;+,@@@@    #  @@    @           @,          
    //:@                                     :@@@'             :@@@         `@@@@@@@@@@@@;  @`         @@           @       #. @@@+   `#'   ;@  `@@:  @    @`@+  `  @#          @@          
    //@:                                        ,@@@@@@@@#@@@@@@+@            #@+          @@          #@         ;+@@@@@   ;+@@`:@       :,.@   @@   @.   @#@#      @@@        @@          
    //@                                              ,.,,::   `+@               #@'       @@@+`        #@         :+@;#;, `  @@   ##    #@@@@@   @:   ##   @: #,      ,@@       @@          
   //@@                                                      `@@                  :`    ;@;  @@        @@           @        @#   :@   .@   @@   @,   +@   @' ,@        @       @@          
   //@,                                                     @@:                        @@     @@    `  :@           @.       @,   `@   ;'    @   @:   ,@   :+  @@  ``   @.      @'          
   //@                                                   ;@@'                                  #@@@@    @,          +:       @,    @   :@   .@   @,   ,@   ;@  `@.  @   @       @.          
   //@                                    @@@@+;:,:`,+@@@@.                                    .@ ,@@   #@          ,@   ,;  @:    @    @@@@@@   @;    @   ,@   ;;  #@@@@      ;@           
  //,@                                   `   .+@@@@@@+;                                       +@.   @:  :@`          @   @,  @:`   @     `+; @   #+    @              ,.       @#           
  //#@                                     `                                                `@@     @#   @@          @@@@@                             ,                      ,@            
  //@+                                                                                     @@;`     @,    @'          +#.`                                                    @#            
  //@+                                                                                  ,@@'       :@     '@,                                                               `#@             
  //@+                                                                              `;@@@:         @@      #@#                                                              ;@              
  //@+                            ```                                        ` `'#@@@#.   `      `@@        :@@                                                            #@,              
  //@;                        +#@@@@@@@@@@@',,                          ,+#@#@@@+.               #@           @@#                                                        .@@                
  //@,                      ,@@`         `;+@@@@@@@@@@@+@@@##@@@@@@@@@@@@+,                     @@            `,@@@+.                                                  :@#;                 
  //@:                      @`                     ..,,,,,::,,,,        `                     +@#                 #@@@@@@'',..    `                                .+@@@:                   
  //@'                     @'                                               `              `#@@'@;                     :##@@@@@@@@@@@@@              `,'''@@@@@@@@@@@:                      
  //#@                    ;@                                                             '@@@`  .@'                                  @@            `@@@@###'+:;`                            
  //.@                    @;                                                         `;@@@@      :@                                 @@          ` `@@                                       
    //@`                   @          :@##@@##;#,:`                         ``::'#@@@@@@; `@       @;                              `@@           `;@@                                        
     //@+                   @         @@'+'+++#@@@@@#@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@,.       @.      ##                             +@@            @@+                                         
      //'@                   #         #          `+@@:               `           +@         @       +@                           :@@.           `@@.                                          
       //@;                  @,        @@@#:         +@@                          @@        @@       @+                        .@@@:            #@@                                            
       //:@                  @@          ;@@@@',       #@@                        @'      '@@        @`     #@@''``      `.'#@@@#             :#@.                                             
        //@@                  @'            `;@@@@@'` ` `@@'                  ``;@@:;'@@@@@;        :@      @@@:#@@@@@@@@@@@;,              ,@@'                                               
         //@,                 ;@+`               .+@@@@@@@@@@@,.;:@'##@@@@@@@@@@@@@@@@'.            @'       `@@@`     `                  +@#;                                                 
         //#@                  ;@@,                    ..'#@@@@@#@@@'+':.`                         @@          `@@@;                  .@@@@,                                                   
          //@@                   @@@,                                                            `@@              +@@@@+,        .'#@@@@,                                                      
          //`@#                    #@@#,                                                       :@@#`                 ,'#@#@@@@@@@@@+,                                                          
           //`@@                     '@#@@#:                                              :+@@@@'                                                                                              
             //.@+                       `'@@@@@@+;.                          .;;@@@@@@@@@@@@@'                                                                                                 
             //;@+                         ` ..@@@@@@@#;`        ``:;'@@@@@@@@@@@#;:,``   @@                                                                                                   
            //` .@'                                `;#@@@@@@@@@@@@@@@@@':,              '@+                                                                                                    
               //`@@                                                                  ;@@                                                                                                      
                  //@@,                                                              ;@@`                                                                                                                //+@#                                                           @@@.                                                                                                         
                     //`@@:                                                     ` +#@'                                                                                                            
                       //;@@:                                                 ;@@@,                                                                                                              
                         //'@@;                                            ,@@@,                                                                                                                 
                           //,@@#,                                      .@@@.                                                                                                                    
                            //`#@@#.                               `:@@@'                                                                                                                       
                                 //+@@@@+,`                     ,+@@@#,                                                                                                                          
                                      //:#@@@@@@@@+,.+:''++@@@@@@#'.                                                                                                                              
                                              //,:'#@@@##@#';`                                                                                                                                     