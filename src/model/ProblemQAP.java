/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author juane
 */
public class ProblemQAP {

	private int[][] _distances;
	private int[][] _flows;
	private int size_problem;
	boolean _isOK=false;

	public ProblemQAP(String instance) throws IOException {
		readMatrices(instance);
	}

	public ProblemQAP() {
		size_problem=0;
	}

	//Private section
	private void readMatrices(String filep) throws FileNotFoundException {
		int aux;

		File file = new File(filep);
		try (Scanner scanner = new Scanner(new File(filep))) {
			size_problem = scanner.nextInt();

			_flows = new int[size_problem][size_problem];
			_distances = new int[size_problem][size_problem];

			for (int i = 0; i < size_problem; i++) {
				for (int j = 0; j < size_problem; j++) {
					_flows[i][j] = scanner.nextInt();
				}
			}

			for (int i = 0; i < size_problem; i++) {
				for (int j = 0; j < size_problem; j++) {
					_distances[i][j] = scanner.nextInt();
				}
			}

			_isOK = true;

		} catch (Exception ex) {
			System.err.println(ex + ": El fichero de instancia no existe o es erroneo");
			_isOK = false;
		}
		
	}

	//Public section
	public int cost(Solution S) {
		int cost = 0;

		//the size of f is equal that d
		for (int i = 0; i < _flows.length; i++) {
			for (int j = 0; j < _flows.length; j++) {
				if (j != i) {
					cost += _flows[i][j] * _distances[S.getPerm().get(i)][S.getPerm().get(j)];
				}
			}
		}

		return cost;
	}

	public int costFact(Solution S, int r, int s) {
		int cost = 0;

		//the size of f is equal that d
		for (int k = 0; k < _flows.length; k++) {
			if (k != r && k != s) {
				cost
						+= _flows[r][k] * (_distances[S.getPerm().get(s)][S.getPerm().get(k)] - _distances[S.getPerm().get(r)][S.getPerm().get(k)])
						+ _flows[s][k] * (_distances[S.getPerm().get(r)][S.getPerm().get(k)] - _distances[S.getPerm().get(s)][S.getPerm().get(k)])
						+ _flows[k][r] * (_distances[S.getPerm().get(k)][S.getPerm().get(s)] - _distances[S.getPerm().get(k)][S.getPerm().get(r)])
						+ _flows[k][s] * (_distances[S.getPerm().get(k)][S.getPerm().get(r)] - _distances[S.getPerm().get(k)][S.getPerm().get(s)]);
			}
		}

		return cost;
	}

	public int getSizeProblem() {
		return size_problem;
	}
	
	public boolean isOK(){
		return _isOK;
	}

	public int[][] getDistancesMatrix() {
		return _distances;
	}

	public int[][] getFlowsMatrix() {
		return _flows;
	}

	public void print() {
		String aux = "";
		System.out.println("Tamaño del problema: " + size_problem);

		System.out.println("Flujos: ");
		for (int i = 0; i < size_problem; i++) {
			for (int j = 0; j < size_problem; j++) {
				aux += _flows[i][j] + " ";
			}
			aux += "\n";
		}
		System.out.println(aux);

		aux = "";
		System.out.println("Distancias: ");
		for (int i = 0; i < size_problem; i++) {
			for (int j = 0; j < size_problem; j++) {
				aux += _distances[i][j] + " ";
			}
			aux += "\n";
		}
		System.out.println(aux);
	}
}
