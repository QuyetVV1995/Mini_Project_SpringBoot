package vn.techmaster.blog.service;

public class FileStorageException extends RuntimeException {
private static final long serialVersionUID = 6288365963802085889L;

  public FileStorageException(String message) {
      super(message);
  }

  public FileStorageException(String message, Throwable cause) {
      super(message, cause);
  }
}
