package br.com.amil.predojo.exception;

/**
 * Exceção quando o arquivo não foi localizado
 * @author Levy
 *
 */
public class CanNotLocateFileException extends RuntimeException {

	private static final long serialVersionUID = 3064525673007295351L;

	public CanNotLocateFileException() {
		super();
	}

	public CanNotLocateFileException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public CanNotLocateFileException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public CanNotLocateFileException(String arg0) {
		super(arg0);
	}

	public CanNotLocateFileException(Throwable arg0) {
		super(arg0);
	}
}
