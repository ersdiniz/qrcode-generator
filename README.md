# QR Code Generator

Biblioteca geradora de qr code.

Ex.:

```java
QrCodeGenerator.of(myObjectDto)
               .size(200) // default 250
               .generate();
```
ou

```java
QrCodeGenerator.of("String qualquer")
               .size(200) // default 250
               .generate();
```

Retorna um [java.awt.image.BufferedImage](https://docs.oracle.com/javase/7/docs/api/java/awt/image/BufferedImage.html)

Exemplo de utilização em uma [API Rest](https://restfulapi.net/) com [SpringBoot](https://spring.io/projects/spring-boot):

```java
@PostMapping(path = "/minhaapi/{toGenerate}")
public void generateQrCode(
        final HttpServletResponse response
        @PathVariable(name = "toGenerate") final String toGenerate) throws IOException {
    final BufferedImage image = QrCodeGenerator
            .of(toGenerate)
            .generate();

    if (image != null) {
        response.setContentType("images/png");
        response.setHeader("Content-Type", "application/octet-stream");
        response.setHeader("Content-Disposition", "inline; filename=\"qrCodeGerado.png\"");

        final OutputStream out = response.getOutputStream();
        ImageIO.write(image, "png", out);
        out.close();
    }
}
```
![Screenshot](example.png)
