/**
 * 
 */
package br.com.amil.predojo.main;

import java.io.File;
import java.io.FileNotFoundException;

import br.com.amil.predojo.controller.GameController;
import br.com.amil.predojo.controller.LogFileGameController;
import br.com.amil.predojo.exception.CanNotLocateFileException;

/**
 * Classe de ponto de entrada para execução da aplicação.
 * @author Levy
 */
public final class Dojo {

	/**
	 * Método principal para execução da aplicação.
	 * É obrigatório informar ao menos 1 argumento com o caminho
	 * para o arquivo de log, caso não seja informado a ferramenta lançará exceção.
	 * @param args espera-se um argumento com o caminho do arquivo de log a ser lido
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) {
		try {
			//verifica se ao menos um argumento foi informado
			if (args.length < 1) {
				throw new IllegalArgumentException("Please provide the path for log file");
			}

			File file = new File(args[0]);
			//verifica se é um arquivo
			if (!file.isFile()) {
				throw new CanNotLocateFileException("Can not locate the file, please make sure that argument is a path to a file!");
			}

			GameController controller = new LogFileGameController(file);
			controller.execute();
			controller.showRanking();
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

}
