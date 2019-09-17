package br.com.diniz.qrcode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Hashtable;
import java.util.Map;

import com.google.gson.GsonBuilder;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import br.com.diniz.qrcode.exception.QrCodeException;

public class QrCode {

    private Object body;
    private int size = 250;

    public QrCode() {
    }

    private QrCode(final Object body) {
        this.body = body;
    }

    /**
     * Cria o objeto builder necessário para criação do QR Code
     * 
     * @param body
     * @return
     */
    public static QrCode of(final Object body) {
        return new QrCode(body);
    }

    /**
     * Tamanho da imagem gerada
     * 
     * @param size
     * @default 250
     * @return
     */
    public QrCode size(final int size) {
        this.size = size;
        return this;
    }

    /**
     * Gera o QR Code com base no body passado
     * 
     * @return BufferedImage
     */
    public BufferedImage generate() {
        try {
            final Map<EncodeHintType, Object> encodeHintType = new Hashtable<>();
            final QRCodeWriter qrCodeWriter = new QRCodeWriter();

            encodeHintType.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

            final BitMatrix byteMatrix = qrCodeWriter.encode(getContent(), BarcodeFormat.QR_CODE, size, size, encodeHintType);

            final Integer matrixWidth = byteMatrix.getWidth();
            final BufferedImage bufferedImage = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
            bufferedImage.createGraphics();

            final Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, matrixWidth, matrixWidth);
            graphics.setColor(Color.BLACK);

            for (int i = 0; i < matrixWidth; i++) {
                for (int j = 0; j < matrixWidth; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }
            return bufferedImage;
        } catch (WriterException e) {
            throw new QrCodeException(e.getMessage(), e);
        }
    }

    private String getContent() {
        return body instanceof String ? body.toString() : new GsonBuilder().create().toJson(body);
    }
}