/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guiqap;

import controller.ControllerAlg;
import controller.ControllerMain;
import view.AlgFrame;
import view.mainFrame;

/**
 *
 * @author juane
 */
public class GuiQAP {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		mainFrame view=new mainFrame();
		AlgFrame alg_frame= new AlgFrame();
		
		ControllerAlg controller_alg=  new ControllerAlg(alg_frame, view);
		ControllerMain controller_main= new ControllerMain(view, controller_alg);
		
		controller_main.start();
		view.setVisible(true);
	}
	
}
