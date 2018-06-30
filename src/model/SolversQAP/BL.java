/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.SolversQAP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import model.ProblemQAP;
import model.Solution;

/**
 *
 * @author juane
 */
public class BL extends SolverQAP {

	private final int MAX_EVALS;

	private ArrayList<Boolean> _mask;
	private boolean _dlb;

	public BL(ProblemQAP problem, int max_evals, boolean dlb) throws IOException {
		super(problem);
		MAX_EVALS = max_evals;
		_dlb = dlb;

		if (_dlb) {
			_mask = new ArrayList<>(Collections.nCopies(_size_problem, false));
		}
	}

	public BL(ProblemQAP problem, Solution initial, int max_evals, boolean dlb) throws IOException {
		this(problem, max_evals, dlb);

		_best_sol = new Solution(initial);
		_best_sol.setCost(_problem.cost(_best_sol));
	}

	//public section
	@Override
	public Solution solve() {
		int n_evals = 0;
		boolean improve_flag = true;

		while (improve_flag && n_evals < MAX_EVALS) {
			for (int i = 0; i < _size_problem && n_evals < MAX_EVALS; i++) {
				if (!_dlb || !_mask.get(i)) {
					improve_flag = false;

					for (int j = 0; j < _size_problem && n_evals < MAX_EVALS; j++) {
						if (j != i) {
							//check move
							int cost_neigh = _problem.costFact(_best_sol, i, j);
							n_evals++;

							if (cost_neigh < 0) {
								_best_sol.NeighOp(i, j);
								_best_sol.setCost(_best_sol.getCost() + cost_neigh);
								
								if (_dlb) {
									_mask.set(i, false);
									_mask.set(j, false);
								}

								improve_flag = true;
							}
						}
					}
					if (_dlb && !improve_flag) {
						_mask.set(i, true);
					}
				}
			}
		}
		return _best_sol;
	}

}
