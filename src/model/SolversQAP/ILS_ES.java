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
public class ILS_ES extends SolverQAP {

	private final int MAX_ITERS;
	private final int MAX_EVALS;

	private final int _subl;

	double _mu, _theta;
	int _mult_size;
	double _red_max_neigh;

	public ILS_ES(ProblemQAP problem, double reduction_subl, double mu, double theta, int mult_size, double red_max_neigh, int max_iters, int max_evals) throws IOException {
		super(problem);
		MAX_ITERS = max_iters;
		MAX_EVALS = max_evals;

		_subl = (int) (_size_problem * reduction_subl);

		_mu = mu;
		_theta = theta;
		_mult_size = mult_size;
		_red_max_neigh = red_max_neigh;
	}

	//private section
	@Override
	public Solution solve() throws IOException {
		Solution sol = new Solution(_best_sol);

		sol = new ES(_problem, sol, _mu, _theta, _mult_size, _red_max_neigh, MAX_EVALS).solve();

		for (int i = 0; i < MAX_ITERS - 1; i++) {
			if (sol.getCost() < _best_sol.getCost()) {
				_best_sol = new Solution(sol);
			}

			_best_sol.highMutOp(_subl);
			sol = new ES(_problem, sol, _mu, _theta, _mult_size, _red_max_neigh, MAX_EVALS).solve();
		}

		return _best_sol;
	}
}
