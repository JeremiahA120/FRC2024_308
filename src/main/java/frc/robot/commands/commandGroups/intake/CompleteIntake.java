package frc.robot.commands.commandGroups.intake;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ShooterPivotSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterIndexSubsystem;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakePivotSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.commands.intake.IntakeNote;
import frc.robot.commands.shooterIndex.IndexNoteGood;
import frc.robot.Constants.ShooterPivotConstants;
import frc.robot.commands.intake.IntakeDeck;
import frc.robot.commands.LED.setLED;

public class CompleteIntake extends SequentialCommandGroup  {

    /** Intakes a note from the ground and then puts it into the shooter index */
    public CompleteIntake(IntakeSubsystem intakeSubsystem, ShooterPivotSubsystem shooterPivotSubsystem, ShooterIndexSubsystem shooterIndexSubsystem, IndexSubsystem indexSubsystem, IntakePivotSubsystem intakePivotSubsystem, LEDSubsystem LEDsubsystem){
        addCommands(
            // Get the shooter pivot started early
            new SequentialCommandGroup(
                new setLED(LEDsubsystem, LEDsubsystem::teal)
            ),
            new InstantCommand(() -> shooterPivotSubsystem.setPosition(ShooterPivotConstants.kshooterPivotDeckPosition)),
            new IntakeNote(intakeSubsystem, intakePivotSubsystem),

            new ParallelDeadlineGroup(
                new IndexNoteGood(shooterIndexSubsystem), 
                new IntakeDeck(intakeSubsystem, shooterPivotSubsystem, indexSubsystem, intakePivotSubsystem)
            ),
            // Add an InstantCommand to reset the LED state after the command group finishes
            new InstantCommand(() -> LEDsubsystem.setLEDFunction(LEDsubsystem::rainbow))
        );
    }

}
