import gui.GUISimulator;
import io.LecteurDonnees;
import representation_donnees.Direction;
import representation_donnees.DonneesSimulation;
import simulables.*;
import simulation.*;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

public class TestSimulateur {

    public static void main(String[] args) {
        DonneesSimulation sim_data;

        if (args.length < 1) {
            System.out.println("Syntaxe: java TestImpression <nomDeFichier>");
            System.exit(1);
        }

        try {

            sim_data = LecteurDonnees.creeDonnees(args[0]);

            //Start simulation
            GUISimulator gui = new GUISimulator(600, 600, Color.BLACK);
            double square_side_bulk = (double) (600 * 600) / (sim_data.getCarte().getNbLignes() * sim_data.getCarte().getNbColonnes()); //area per square
            square_side_bulk = Math.ceil(Math.sqrt(square_side_bulk)); //side per square

            CarteSimulable.setSquareSide(square_side_bulk);
            CarteSimulable.draw(gui, sim_data.getCarte());

            RobotsSimulable.initRobotsSimulablesList(sim_data.getRobotsList().size());
            RobotsSimulable.drawRobots(gui, sim_data.getRobotsList());

            IncendiesSimulable.initIncendiesSimulablesList(sim_data.getIncendiesList().size());
            IncendiesSimulable.drawIncendies(gui, sim_data.getIncendiesList());

            Simulateur simulateur = new Simulateur(gui);
            simulateur.ajouteEvenement(new DeplaceRobotEvenement(RobotsSimulable.getRobotSimulable(0), Direction.NORD, 1));
            simulateur.ajouteEvenement(new DeplaceRobotEvenement(RobotsSimulable.getRobotSimulable(0), Direction.NORD, 2));
            simulateur.ajouteEvenement(new DeplaceRobotEvenement(RobotsSimulable.getRobotSimulable(0), Direction.NORD, 3));
            simulateur.ajouteEvenement(new DeplaceRobotEvenement(RobotsSimulable.getRobotSimulable(0), Direction.NORD, 4));

            RobotSimulable robot_2 = RobotsSimulable.getRobotSimulable(1);
            simulateur.ajouteEvenement(new DeplaceRobotEvenement(robot_2, Direction.NORD, 1));
            IncendieSimulable incendie = IncendiesSimulable.getIncendieAt(5, 5);
            simulateur.ajouteEvenement(new IntervinirRobotEvenement(robot_2, incendie, 2));
            simulateur.ajouteEvenement(new DeplaceRobotEvenement(robot_2, Direction.OUEST, 3));
            simulateur.ajouteEvenement(new DeplaceRobotEvenement(robot_2, Direction.OUEST, 4));
            simulateur.ajouteEvenement(new DebutRemplirRobotEvenement(5));
            simulateur.ajouteEvenement(new FinRemplirRobotEvenement(robot_2, 35));
            simulateur.ajouteEvenement(new DeplaceRobotEvenement(robot_2, Direction.EST, 36));
            simulateur.ajouteEvenement(new DeplaceRobotEvenement(robot_2, Direction.EST, 37));
            simulateur.ajouteEvenement(new IntervinirRobotEvenement(robot_2, incendie, 38));


        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
    }
}
