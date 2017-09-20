
# Robot_2017
Robot code and SmartDashboard Layouts for FRC 2791 Shaker Robotics, and their 2017 robot: <b><i>Stoker</b></i> </br></br>


Shaker Robotics competed in the 2017 FIRST Robotics Competition, STEAMWorks at the New York Tech Valley Regional, New York City Regional, and the St. Louis FRC Championship (Curie Division). </br></br>

<h2>Season Results: </h2>
Tech Valley - Winner and Industrial Design </br>
NYC - Semifinalists and Industrial Design </br>
Curie - Quarterfinalists</br>
IRI Invitees</br>

<h2> Table of Contents: </h2>
<ol>
  <li> Overview </li>
  <li> Command-Based Layout </li>
  <li> Subsystems and Teleop Commands </li>
  <li> Autonomous Commands</li>
   <ul>
    <li> Trajectory Based Motion Profiling </li>
    <li> GRIP Vision Processing </li>
    <li> PID Based Motion</li>
  </ul>
 </ol>
 
<h2> Overview </h2>
 This year, Shaker Robotics took a lot of big step towards enhancing our control systems. We played our hand at motion profiling and made great progress. We also used GRIP-generated vision combined with PID Commands to have Vision-Guided shooting. This was also the first year we used a Command-Based layout for our robot code. </br>
 
 <h2> Command-Based Layout </h2>
 <a href ="https://wpilib.screenstepslive.com/s/4485/m/13809/l/599732-what-is-command-based-programming">Command-Based Programming</a> is a WPILib structure for organizing robot code. </br>
 Commands have 4 sections, each with their own methods: init(), execute(), interuppted(), end(). There is an extensive overview of these methods on <a href ="https://wpilib.screenstepslive.com/s/4485/m/13809/l/599737-creating-simple-commands">WPI's ScreenStepsLive</a>.  ScreenStepsLive has a great overview of how to use Command-Based programming. 

 
 <h2> Subsystems and Teleop Commands </h2>
 
 We had four subsystems:
 <ul>
 	<li>ShakerDrivetrain.java: the <b>drive train</b> consists of the drive motors speed controllers(We have 6 motors, 3 are grouped together as the right side controllers and the other 3 are grouped together as the left side controllers)</li>
 	<li>ShakerGear.java: the <b>gear mechanism</b> that consists of a solenoid that actuates the mechanism up/down, and a motor that runs the intake in/out </li>
 	<li>ShakerHopper.java: the <b>hopper</b> feeds balls to our <b>shooter</b> and consists of a speed controller that runs wheels in or out to index balls to/from the passive elevator shaft that leads to the <b>shooter</b> wheels
 	<li>ShakerIntake.java: the <b>intake</b> is also the </b> climber. It has a speed controller that runs 2 775pros, and 2 solenoids. One solenoid causes the intake to extend out of the bumper perimeter to pick up balls from the ground. The other solenoid is a ratchet that, when activated, stops the gears of the intake from running in reverse. This is used when climbing. </li>
 	<li>ShakerShooter.java: the <b>shooter</b> has 3 TalonSRX speed controllers with tuned PID. The shooter also has a solenoid to actuate a hood that changes the angle at which the balls are shot.

These <i>Commands</i> were all in the <u>robot.commands</u> package. </br>Each Teleop Command (except for shooting commands) is named with it's <b>subsystem</b>, then it's action. So <i>GearMechDown.java</i> cause the <b>gear mechanism</b> to go down. <i>DriveWithVision.java</i> causes the <b>drive train</b> to start vision driving. <i>HopperOn.java</i>... you guessed it, turns the <b>hopper</b> on. </br>Some commands were very simple, and simply actuated a solenoid or spun wheels at pre-determined rpms. Other commands were designed to sync up the actions of mulitple subsystems. for example <i>RunLongShot</i> was timed to run the <b>hopper</b>, actuate the <b>shooter</b>, and run the <b>shooter</b> with PID. Each command was designed to give the robot operators enough control, but there is also a certain a level of autonomy, so the operators don't have to worry about remebering every small step of certain actions.</br> 
</br>To activate these commands in teleop, we mapped them in <i>OI.java</i> with the following syntax:

```java
		gamepadButton1.whenPressed(new CommandName1()); 
		gamepadButton2.whileHeld(new CommandName2());
```

Our driving command (<i>DriveWithJoystick.java</i>) didn't use buttons (it used Joysticks, Bumpers, and Triggers). Therefore rather than mapping those commands in <i>OI.java</i>, we created a utility class, <i>GTADrive.java</i> that interprets the joystick, trigger, and bumper values when it's methods are called. <i>DriveWithJoystick.java</i> was a default command that always ran, and called <i>GTADrive.java</i> every loop. So the Joystick values were always being interpretted. 

 
 
 .... and someone eventually wrote the rest of this

