package vendi.exception;

public enum VendiErrorCode {

	INTERNAL_SERVER_ERROR("Internal server error"),
    SHIPMENT_NOT_FOUND("Shipment not found"),
    DUPLICATE_REQUEST("Duplicate Request");

    private final String name;

    private VendiErrorCode(String name){
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
