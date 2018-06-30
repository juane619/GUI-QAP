/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.SolversQAP;

import java.io.IOException;
import model.ProblemQAP;
import model.Solution;

/**
 *
 * @author juane
 */
public abstract class SolverQAP {

	protected Solution _best_sol;
	protected ProblemQAP _problem;
	protected int _size_problem;

	public SolverQAP(ProblemQAP problem) throws IOException {
		_problem = problem;
		_size_problem = _problem.getSizeProblem();
		_best_sol = new Solution(_size_problem);
		_best_sol.setCost(_problem.cost(_best_sol));
	}

	public abstract Solution solve() throws IOException;

	public Solution getSol() {
		return _best_sol;
	}
}
