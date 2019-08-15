package danylenko.stanislav.noteefication.db;

public enum Status {

    ACTUAL("actual"), DONE("done");

    private String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Status fromString(String value) {
        for (Status status : Status.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
