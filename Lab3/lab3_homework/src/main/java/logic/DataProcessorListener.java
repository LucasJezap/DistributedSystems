package logic;

public interface DataProcessorListener {
    void existenceStatusChanged(byte[] data);

    void sessionToClose(int reasonCode);
}
