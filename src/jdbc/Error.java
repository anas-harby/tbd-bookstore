package jdbc;

public enum Error {
    HEAVY_LOAD,
    CONNECTION_FAILED,
    INTERNAL_ISSUE, // Try again
    ACCESS_DENIED, // Either unregistered user or invalid password
    ALREADY_REGISTERED, // Username taken
    DUPLICATE_ITEM,
    UNKNOWN_ITEM, // Ex: adding a new book having an invalid category
    KEY_FIELD_MISSING, // Obligatory field left empty, handled in the UI?
    UNSPECIFIED // General error
}
