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
public class BMB extends SolverQAP {

	private final int MAX_ITERS;
	private final int MAX_EVALS;
	private boolean _dlb;

	public BMB(ProblemQAP problem, int max_iters, int max_evals, boolean dlb) throws IOException {
		super(problem);
		MAX_ITERS = max_iters;
		MAX_EVALS = max_evals;
		_dlb = dlb;
	}

	@Override
	public Solution solve() throws IOException {
		for (int i = 0; i < MAX_ITERS; i++) {
			Solution curr_sol = new Solution(_size_problem);

			curr_sol = new BL(_problem, curr_sol, MAX_EVALS, _dlb).solve();

			if (curr_sol.getCost() < _best_sol.getCost()) {
				_best_sol = new Solution(curr_sol);
			}
		}

		return _best_sol;
	}
}
