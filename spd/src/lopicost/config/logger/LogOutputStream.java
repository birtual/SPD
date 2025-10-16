package lopicost.config.logger;

import org.apache.log4j.Logger;
import java.io.OutputStream;

/**
 * Clase auxiliar para redirigir la salida estándar a un logger de Log4j.
 */
public class LogOutputStream extends OutputStream {
    private final Logger logger;
    private final int level;
    private StringBuilder buffer = new StringBuilder();

    /**
     * Constructor para redirigir los logs al logger especificado.
     * 
     * @param logger Logger de Log4j donde se redirigirán los mensajes.
     * @param level Nivel del log (INFO, DEBUG, ERROR, etc.).
     */
    public LogOutputStream(Logger logger, int level) {
        this.logger = logger;
        this.level = level;
    }

    @Override
    public void write(int b) {
        char c = (char) b;

        // Cuando se detecta un salto de línea, se escribe el contenido al logger
        if (c == '\n' || c == '\r') {
            if (buffer.length() > 0) {
                logger.log(convertLevel(level), buffer.toString());
                buffer.setLength(0); // Limpia el buffer
            }
        } else {
            buffer.append(c); // Agrega el carácter al buffer
        }
    }

    /**
     * Convierte el nivel definido en la clase Logger al nivel de Log4j.
     * 
     * @param level Nivel definido en la clase Logger.
     * @return Nivel correspondiente en Log4j.
     */
    private org.apache.log4j.Level convertLevel(int level) {
        switch (level) {
            case lopicost.config.logger.Logger.DEBUG: return org.apache.log4j.Level.DEBUG;
            case lopicost.config.logger.Logger.INFO: return org.apache.log4j.Level.INFO;
            case lopicost.config.logger.Logger.WARNING: return org.apache.log4j.Level.WARN;
            case lopicost.config.logger.Logger.ERROR: return org.apache.log4j.Level.ERROR;
            case lopicost.config.logger.Logger.FATAL: return org.apache.log4j.Level.FATAL;
            default: return org.apache.log4j.Level.INFO;
        }
    }
}