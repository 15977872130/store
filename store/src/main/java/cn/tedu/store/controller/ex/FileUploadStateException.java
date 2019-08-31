package cn.tedu.store.controller.ex;

/**
 * 上传文件状态异常，可能文件已经被移动或删除，无法继续访问该文件
 */
public class FileUploadStateException extends FileUploadException {

	private static final long serialVersionUID = 1622956643614506466L;

	public FileUploadStateException() {
		super();
	}

	public FileUploadStateException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FileUploadStateException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileUploadStateException(String message) {
		super(message);
	}

	public FileUploadStateException(Throwable cause) {
		super(cause);
	}

}
