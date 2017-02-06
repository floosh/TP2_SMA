package fr.disp.polytech.sma.tp1;

import java.io.IOException;
import java.net.URL;

import fr.disp.polytech.sma.tp1.gui.GUI;
import fr.disp.polytech.sma.tp1.util.ThreadUtil;


public class main {

	public static void main(String [] args) {

		Simulation simu = new Simulation();
		GUI  gui = null;

		gui= new GUI(simu);
		gui.setVisible(true);

		simu.init();

		simu.start();

	}
 
}
