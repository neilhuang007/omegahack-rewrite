/*
 * Decompiled with CFR 0_132.
 */
package keystrokesmod.client.events.world;


import keystrokesmod.client.events.Event;

public class EventPostUpdate
extends Event {
    public float yaw, pitch;

    public EventPostUpdate(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
}

