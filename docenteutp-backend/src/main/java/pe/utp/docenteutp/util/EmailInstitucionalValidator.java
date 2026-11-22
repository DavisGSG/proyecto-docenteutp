package pe.utp.docenteutp.util;

public final class EmailInstitucionalValidator {

    private static final String DOMINIO = "@utp.edu.pe";

    private EmailInstitucionalValidator() {}

    public static boolean esValido(String correo) {
        if (correo == null) {
            return false;
        }
        String c = correo.trim().toLowerCase();
        return c.endsWith(DOMINIO) && c.length() > DOMINIO.length();
    }
}