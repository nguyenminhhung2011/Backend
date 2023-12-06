package com.trainer.utils.observable;

import lombok.Getter;

/**
 * Simple Server Sent Event representation
 */
public record CompletionEvent(String data) {
    private static final String DONE_DATA = "[DONE]";

    public byte[] toBytes() {
        return String.format("data: %s\n\n", this.data).getBytes();
    }

    public boolean isDone() {
        return DONE_DATA.equalsIgnoreCase(this.data);
    }
}