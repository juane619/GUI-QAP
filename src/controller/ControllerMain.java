/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import model.ProblemQAP;
import view.mainFrame;

/**
 *
 * @author juane
 */
public class ControllerMain implements ActionListener {

	private File _instance_file;
	private final mainFrame _view;
	private ProblemQAP problemQap;
	private final ControllerAlg _algController;

	public ControllerMain(mainFrame view, ControllerAlg algCont) {
		problemQap = new ProblemQAP();
		_view = view;
		_algController = algCont;
		_instance_file = new File(".");
		this._view.button_instance.addActionListener(this);
		this._view.button_choose.addActionListener(this);
	
		this._view.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent){
				if (JOptionPane.showConfirmDialog(_view,
						"Are you sure to close the program?", "closing",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
	}

	public void start() {
		_view.setTitle("GUI QAP");
		_view.setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if (src == _view.button_instance) {
			JFileChooser jf = new JFileChooser();
			jf.setCurrentDirectory(_instance_file);

			if (jf.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				_instance_file = jf.getSelectedFile();
				_view.jtext_instance.setText(_instance_file.getName());

				try {
					problemQap = new ProblemQAP(_instance_file.toString());
				} catch (IOException ex) {
					Logger.getLogger(ControllerMain.class.getName()).log(Level.SEVERE, null, ex);
				}

				if (problemQap.isOK()) {
					_view.label_size_instance.setText(Integer.toString(problemQap.getSizeProblem()));
					_view.label_instance_name.setText(_instance_file.getName());
				}
			}
		} else if (src == _view.button_choose) {
			if (problemQap.isOK()) {
				_algController.setProblem(problemQap);
				_algController.start();
				_view.setVisible(false);
			} else {
				JOptionPane.showMessageDialog(_view, "Please load the instance!");
			}
		}
	}

}
