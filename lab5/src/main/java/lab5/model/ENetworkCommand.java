package lab5.model;

import java.io.Serializable;

public enum ENetworkCommand implements Serializable {
    INIT,
    ACK_INIT,
    MSG,
    ACK_MSG,
    CLS;

    private static final long serialVersionUID = 1L;
}
