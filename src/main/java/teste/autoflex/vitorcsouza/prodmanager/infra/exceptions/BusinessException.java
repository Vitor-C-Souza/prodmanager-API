package teste.autoflex.vitorcsouza.prodmanager.infra.exceptions;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
