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
public class ILS extends SolverQAP {
	private final int MAX_ITERS;
	private final int MAX_EVALS;
	private boolean _dlb;

	private final int _subl;

	public ILS(ProblemQAP problem, double reduction_subl, int max_iters, int max_evals, boolean dlb) throws IOException {
		super(problem);
		MAX_ITERS=max_iters;
		MAX_EVALS=max_evals;
		_dlb=dlb;
		
		_subl = (int) (_size_problem * reduction_subl);
	}

	//private section
	@Override
	public Solution solve() throws IOException {
		Solution sol = new Solution(_best_sol);

		sol = new BL(_problem, sol, MAX_EVALS, true).solve();

		for (int i = 0; i < MAX_ITERS - 1; i++) {
			if (sol.getCost() < _best_sol.getCost()) {
				_best_sol = new Solution(sol);
			}

			_best_sol.highMutOp(_subl);
			sol = new BL(_problem, _best_sol, MAX_EVALS, _dlb).solve();
		}

		return _best_sol;
	}

}
