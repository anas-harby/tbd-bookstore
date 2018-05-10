package jdbc;

import java.util.HashMap;

public class ErrorHandler {

    private static ErrorHandler errorHandlerInstance = new ErrorHandler();

    public static ErrorHandler getInstance() {
        return errorHandlerInstance;
    }

    public Error getError(int errorCode) {
        Error error = errorMap.get(errorCode);
        return error != null ? error : Error.UNSPECIFIED;
    }

    private HashMap<Integer, Error> errorMap = new HashMap<>();

    private ErrorHandler() {
        setErrors();
    }

    private void setErrors() {
        errorMap.put(1040, Error.HEAVY_LOAD); // Too many connections
        // TODO: CONNECTION_FAILED
        errorMap.put(1213, Error.INTERNAL_ISSUE); // Transaction failed due to internal issues and could be restarted
        errorMap.put(1205, Error.INTERNAL_ISSUE); // Transaction failed due to internal issues and could be restarted
        errorMap.put(1045, Error.ACCESS_DENIED); // Either unregistered user or invalid password
        errorMap.put(1396, Error.ALREADY_REGISTERED); // Duplicate username
        errorMap.put(1062, Error.DUPLICATE_ITEM); // Duplicate key value
        errorMap.put(1452, Error.UNKNOWN_ITEM); // Cannot add or update a child row: a foreign key constraint fails
        errorMap.put(1216, Error.UNKNOWN_ITEM); // Cannot add or update a child row: a foreign key constraint fails
        // errorMap.put(1217, Error.UNKNOWN_ITEM); // Deleting a parent row
        errorMap.put(1364, Error.KEY_FIELD_MISSING); // A key/NOT-NULL field with no default value is left empty
    }
}
