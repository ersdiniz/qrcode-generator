package br.com.diniz.qrcode.exception;

@SuppressWarnings("serial")
public class QrCodeException extends RuntimeException {

    public QrCodeException() {
        super();
    }

    public QrCodeException(String message) {
        super(message);
    }

    public QrCodeException(String message, Exception e) {
        super(message, e);
    }
}