/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import model.ProblemQAP;
import model.SolversQAP.BL;
import model.SolversQAP.BMB;
import model.SolversQAP.ES;
import model.SolversQAP.ILS;
import model.SolversQAP.ILS_ES;
import model.SolversQAP.SolverQAP;
import view.AlgFrame;
import view.mainFrame;

/**
 *
 * @author juane
 */
public class ControllerAlg implements ActionListener {

	private ProblemQAP _problem;
	private final mainFrame main_frame;
	private final AlgFrame _view;
	private SolverQAP solver;
	CardLayout card;
	String selected_jcombo;
	String[] algs = {"BL", "ES", "BMB", "ILS", "ILS_ES"};

	public ControllerAlg(AlgFrame view, mainFrame m_f) {
		_view = view;
		main_frame = m_f;
		_view.jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(algs));

		this._view.jComboBox1.addActionListener(this);
		this._view.button_solve.addActionListener(this);
		this._view.button_back.addActionListener(this);
		this._view.button_print.addActionListener(this);

		this._view.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this._view.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (JOptionPane.showConfirmDialog(_view,
						"Are you sure to close the program?", "closing",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});

		selected_jcombo = _view.jComboBox1.getSelectedItem().toString();
		card = (CardLayout) _view.panel_mainPanel.getLayout();
	}

	public void setProblem(ProblemQAP problem) {
		_problem = problem;
	}

	public void start() {
		_view.setTitle("choose algorithm");
		_view.setLocationRelativeTo(null);
		_view.setVisible(true);
		_view.text_elapsed.setText("");
		_view.text_cost.setText("");
		_view.label_instance_alg.setText(main_frame.jtext_instance.getText());
		card.show(_view.panel_mainPanel, "card_" + _view.jComboBox1.getSelectedItem().toString());
		_view.button_print.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();

		if (src == _view.jComboBox1) {
			selected_jcombo = _view.jComboBox1.getSelectedItem().toString();
			card.show(_view.panel_mainPanel, "card_" + selected_jcombo);
			_view.button_print.setEnabled(false);
			_view.text_elapsed.setText("");
			_view.text_cost.setText("");
		} else if (src == _view.button_solve) {
			switch (selected_jcombo) {
				case "BL": {
					//System.out.println("resolviendo BL..");
					try {
						solver = new BL(_problem, Integer.parseInt(_view.text_evals_bl.getText()), _view.check_bl.isSelected());
					} catch (IOException ex) {
						Logger.getLogger(ControllerAlg.class.getName()).log(Level.SEVERE, null, ex);
					}
					break;
				}
				case "ES": {
					//System.out.println("resolviendo ES..");
					try {
						solver = new ES(_problem, Double.parseDouble(_view.text_mu_es.getText()), Double.parseDouble(_view.text_theta_es.getText()), Integer.parseInt(_view.text_mults_es.getText()), Double.parseDouble(_view.text_redneighs_es.getText()), Integer.parseInt(_view.text_evals_es.getText()));
					} catch (IOException ex) {
						Logger.getLogger(ControllerAlg.class.getName()).log(Level.SEVERE, null, ex);
					}
					break;
				}
				case "BMB": {
					//System.out.println("resolviendo BMB..");
					try {
						solver = new BMB(_problem, Integer.parseInt(_view.text_iters_bmb.getText()), Integer.parseInt(_view.text_evals_bmb.getText()), _view.check_bl.isSelected());
					} catch (IOException ex) {
						Logger.getLogger(ControllerAlg.class.getName()).log(Level.SEVERE, null, ex);
					}
					break;
				}
				case "ILS": {
					//System.out.println("resolviendo ILS..");
					try {
						solver = new ILS(_problem, Double.parseDouble(_view.text_redsubl_ils.getText()), Integer.parseInt(_view.text_iters_ils.getText()), Integer.parseInt(_view.text_evals_ils.getText()), _view.check_bl.isSelected());
					} catch (IOException ex) {
						Logger.getLogger(ControllerAlg.class.getName()).log(Level.SEVERE, null, ex);
					}
					break;
				}
				case "ILS_ES": {
					//System.out.println("resolviendo ILS_ES..");
					try {
						solver = new ILS_ES(_problem, Double.parseDouble(_view.text_redsubl_ilses.getText()), Double.parseDouble(_view.text_mu_ilses.getText()), Double.parseDouble(_view.text_theta_ilses.getText()), Integer.parseInt(_view.text_mults_ilses.getText()), Double.parseDouble(_view.text_redn_ilses.getText()), Integer.parseInt(_view.text_iters_ilses.getText()), Integer.parseInt(_view.text_evals_ilses.getText()));
					} catch (IOException ex) {
						Logger.getLogger(ControllerAlg.class.getName()).log(Level.SEVERE, null, ex);
					}
					break;
				}
			}

			_view.button_print.setEnabled(true);
			_view.button_print.setSelected(false);

			long startTime, endTime = 0;
			try {
				//JOptionPane.showMessageDialog(_view, "SOLVED!");
				startTime = System.currentTimeMillis();
				solver.solve();
				endTime = System.currentTimeMillis() - startTime;
			} catch (IOException ex) {
				Logger.getLogger(ControllerAlg.class.getName()).log(Level.SEVERE, null, ex);
			}

			_view.text_cost.setText(String.valueOf(solver.getSol().getCost()));
			_view.text_elapsed.setText(String.valueOf((double) endTime / 1000));
		} else if (src == _view.button_back) {
			main_frame.setVisible(true);
			_view.setVisible(false);
		} else if (src == _view.button_print) {
			String solution = solver.getSol().getPerm().toString();
			JTextArea textArea = new JTextArea(solution);
			textArea.setEditable(false);
			textArea.setLineWrap(true);
			textArea.setWrapStyleWord(true);
			textArea.setPreferredSize(new Dimension(600, 250));

			JOptionPane.showMessageDialog(null, textArea, "Permutation solution", JOptionPane.OK_OPTION);
		}
	}
}
