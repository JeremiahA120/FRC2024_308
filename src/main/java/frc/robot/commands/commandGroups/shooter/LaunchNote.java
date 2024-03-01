package frc.robot.commands.commandGroups.shooter;

import frc.robot.commands.LED.setLED;
import frc.robot.commands.shooterIndex.RunShooterIndexer;
import frc.robot.subsystems.ShooterIndexSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

public class LaunchNote extends SequentialCommandGroup {

    public LaunchNote(ShooterIndexSubsystem shooterIndexSubsystem, LEDSubsystem ledSubsystem){
        addCommands(
            new setLED(ledSubsystem, ledSubsystem::teal),
            new ParallelDeadlineGroup(
                new SequentialCommandGroup(
                    new WaitUntilCommand(() -> shooterIndexSubsystem.gamePieceDetected()).withTimeout(1), 
                    // Wait a little longer just to make sure the note has fully left the shooter
                    new WaitCommand(0.3)
                ), 
                new RunShooterIndexer(shooterIndexSubsystem, 1)
            ),
            new InstantCommand(() -> ledSubsystem.setLEDFunction(ledSubsystem::rainbow))

        );
    }
}
