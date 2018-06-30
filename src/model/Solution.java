/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author juane
 */
public class Solution {

	private final ArrayList<Integer> _perm;
	private final int _size_sol;
	private long _cost;

	public Solution(int size) throws IOException {
		_size_sol = size;
		_perm = new ArrayList<>(size);
		//System.in.read();
		fillVector();
		_cost = Integer.MAX_VALUE;
	}

	public Solution(Solution other) {
		_perm = new ArrayList<>(other._perm);
		_size_sol = other._size_sol;
		_cost = other._cost;
	}

	private void fillVector() {
		for (int i = 0; i < _size_sol; i++) {
			_perm.add(i);
		}
		Collections.shuffle(_perm);
	}

	public void NeighOp(int i, int j) {
		int aux = _perm.get(i);
		_perm.set(i, _perm.get(j));
		_perm.set(j, aux);
	}

	public void highMutOp(int subl) {
		int rand_ini = ThreadLocalRandom.current().nextInt(0, subl - 1);
		int rand_fin = (rand_ini + subl) % _size_sol - 1;

		Collections.shuffle(_perm.subList(rand_ini, rand_fin));
	}

	public boolean less(Solution other) {
		return _cost < other._cost;
	}

	public long getCost() {
		return _cost;
	}

	public ArrayList<Integer> getPerm() {
		return _perm;
	}

	public void setCost(long cost) {
		_cost = cost;
	}

	public void print() {
		String out = "Permutation: " + _perm.toString() + "\n" + "Cost: " + _cost;
		System.out.println(out);
	}

}
