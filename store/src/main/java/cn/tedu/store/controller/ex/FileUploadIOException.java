package cn.tedu.store.controller.ex;

/**
 * 上传文件时出现读写错误
 */
public class FileUploadIOException extends FileUploadException {

	private static final long serialVersionUID = -3734672957415988562L;

	public FileUploadIOException() {
		super();
	}

	public FileUploadIOException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FileUploadIOException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileUploadIOException(String message) {
		super(message);
	}

	public FileUploadIOException(Throwable cause) {
		super(cause);
	}

}
