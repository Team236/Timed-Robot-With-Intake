// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  private CANSparkMax leftFront, leftRear, rightFront, rightRear;

  private CANSparkMax intakeMotor;

  private DoubleSolenoid intakeSolenoid;

  private Joystick leftStick, rightStick, controller; 
  private JoystickButton aIntakeBall, bEjectBall, xIntakeExtend, yIntakeRetract, rbStopIntake; 

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {

    //Declare the two joysticks and the controller
    //0 is the computer USB port for joystick1, as seen on driver's station tab
    //1 is the computer USB port for joystick 2
    //2 is the computer USB port for the controller
    leftStick = new Joystick(0);  
    rightStick = new Joystick(1); 
    controller = new Joystick(2); 

    // Instantiate Drive and Intake motor controllers using their ID numbers
    leftFront = new CANSparkMax(30, MotorType.kBrushless); //30 is the SparkMax ID#
    leftRear = new CANSparkMax(44, MotorType.kBrushless); //44 is the SparkMax ID# 
    rightFront = new CANSparkMax(43, MotorType.kBrushless); //43 is the SparkMax ID#
    rightRear = new CANSparkMax(45, MotorType.kBrushless); //45 is the SparkMax ID#

    intakeMotor = new CANSparkMax(39, MotorType.kBrushless); //39 is the SparkMax ID#

    //Instantiate the intake double solenoid,connected to solenoid channels 1 and 0 on the PCM
    intakeSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 1, 0);

    // Set right front Drive controller and intake motor to be inverted (know by testing)
    leftFront.setInverted(false);
    rightFront.setInverted(true);
    intakeMotor.setInverted(true);

    // Set rear Drive controllers to follow front
    leftRear.follow(leftFront);
    rightRear.follow(rightFront);

   //Declare the controller buttons which will control the intake extend/retract solenoid
   //and the controller buttons which will spin the Intake motor forward/reverse
   //Find the button numbers using the tab on the Driver Station and pushing the buttons 
   aIntakeBall = new JoystickButton(controller, 2); //2 is the A button on the controller
   bEjectBall = new JoystickButton(controller, 3); // 3 is the B button on the controller
   xIntakeExtend = new JoystickButton(controller, 1); //1 is the X button on the controller
   yIntakeRetract = new JoystickButton(controller, 4); //4 is the Y button on the controller
   rbStopIntake = new JoystickButton(controller, 6); //6 is RB (top button of two on back side of controller)
  }
    /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {}

  /** This function is called periodically during autonomous. */
  public void autonomoousPeriodic() {}

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    //Set front drive motors speed according to the Y axes on the two joysticks (Tank Drive)
    leftFront.set(-leftStick.getY());
    rightFront.set(-rightStick.getY());

//Intake solenoid forward if A button (2) pressed on controller, reverse if B (3) pressed
    if (aIntakeBall.get()) {
      intakeMotor.set(0.5);
    } else if (bEjectBall.get()) {
      intakeMotor.set(-0.5);
    }
    //stops intake from running  when RB button is pressed :)
    if (rbStopIntake.get()) {
      intakeMotor.set(0);
    }
//Intake motor forward 1/2 speed if X (1) pressed on controller, reverse if Y (4) pressed
   if (xIntakeExtend.get()) {
    intakeSolenoid.set(Value.kForward);
  } else if (yIntakeRetract.get()) {
    intakeSolenoid.set(Value.kReverse);
  }
  
}

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {
    //leftFront.set(0);
    //rightFront.set(0);
  }

}
  