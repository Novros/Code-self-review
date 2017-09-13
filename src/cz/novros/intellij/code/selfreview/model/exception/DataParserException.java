package cz.novros.intellij.code.selfreview.model.exception;

/**
 * Exception for error states in {@link cz.novros.intellij.code.selfreview.model.DataParser}.
 *
 * @author Rostislav Novák
 * @version 1.0
 * @since 1.0
 */
public class DataParserException extends Exception {

	/**
	 * Create instance of exception with given message.
	 *
	 * @param message Message for exception.
	 */
	public DataParserException(final String message) {
		super(message);
	}

	/**
	 * Create instance of exception with given message and exception.
	 *
	 * @param message Message for exception.
	 * @param cause   What caused this exception.
	 */
	public DataParserException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
