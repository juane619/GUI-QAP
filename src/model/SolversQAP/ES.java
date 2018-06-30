/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.SolversQAP;

import java.io.IOException;
import static java.lang.Math.exp;
import static java.lang.Math.log;
import model.ProblemQAP;
import model.Solution;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author juane
 */
public class ES extends SolverQAP {

	private final int MAX_EVALS;

	private final int _max_neighs, _max_succ, _M;
	private final double _mu, _theta, _beta;
	private final double _temp_ini, _temp_fin;

	public ES(ProblemQAP problem, double mu, double theta, int mult_size, double red_max_neigh, int max_evals) throws IOException {
		super(problem);
		MAX_EVALS = max_evals;
		_mu = mu;
		_theta = theta;
		_max_neighs = mult_size * _size_problem;
		_max_succ = (int) (red_max_neigh * _max_neighs);
		_M = MAX_EVALS / _max_neighs;
		_temp_ini = _best_sol.getCost() * _mu / -log(_theta);
		_temp_fin = 1E-3;
		_beta = (_temp_ini - _temp_fin) / (_M * _temp_ini * _temp_fin);
	}

	public ES(ProblemQAP problem, Solution initial, double mu, double theta, int mult_size, double red_max_neigh, int max_evals) throws IOException {
		this(problem, mu, theta, mult_size, red_max_neigh, max_evals);

		_best_sol = new Solution(initial);
		_best_sol.setCost(_problem.cost(_best_sol));
	}

	//private section
	//public section
	@Override
	public Solution solve() {
		Solution curr_sol = _best_sol;
		int size_problem = _size_problem;
		int n_evals = 0, num_succ, neighs_gen;
		boolean keep_gen, keep_cooling = true;
		double curr_temp = _temp_ini, crit_accept;

		while (keep_cooling) {
			keep_gen = true;
			neighs_gen = 0;
			num_succ = 0;

			while (keep_gen) {
				neighs_gen++;

				int r, s;
				r = ThreadLocalRandom.current().nextInt(0, size_problem - 1);
				do {
					s = ThreadLocalRandom.current().nextInt(0, size_problem - 1);
				} while (r == s);

				long cost_fact = _problem.costFact(curr_sol, r, s);
				n_evals++;

				if (cost_fact < 0 || ThreadLocalRandom.current().nextDouble(0, 1) <= exp(-cost_fact / curr_temp)) {
					curr_sol.NeighOp(r, s);

					curr_sol.setCost(curr_sol.getCost() + cost_fact);

					if (curr_sol.getCost() < _best_sol.getCost()) {
						_best_sol = curr_sol;
					}

					num_succ++;
				}

				if (neighs_gen >= _max_neighs || num_succ >= _max_succ) {
					keep_gen = false;
				}

			}

			if (n_evals >= MAX_EVALS || num_succ == 0) { // || temp_act <= temp_fin
				keep_cooling = false;
			} else {
				double alfa = ThreadLocalRandom.current().nextDouble(0.9, 0.99);

				curr_temp *= alfa;
				//temp_act = temp_act / (1 + beta * temp_act);
			}

		}

		return _best_sol;
	}

}
