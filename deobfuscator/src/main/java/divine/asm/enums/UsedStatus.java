package divine.asm.enums;

/**
 * Author: Typically
 * Project: divine.asm.enums
 * Date: 2/4/2019
 */
public enum UsedStatus {

    UNKNOWN(0),
    UNUSED(1),
    USED(2);

    private final int status;

    UsedStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
