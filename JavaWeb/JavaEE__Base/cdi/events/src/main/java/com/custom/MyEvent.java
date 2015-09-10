package com.custom;

/**
 * Created by olga on 07.04.15.
 */
public class MyEvent {
    private String data;
    private long time;

    public MyEvent() {

    }

    public MyEvent(String data, long time) {
        this.data = data;
        this.time = time;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MyEvent myEvent = (MyEvent) o;

        if (time != myEvent.time) return false;
        return data.equals(myEvent.data);

    }

    @Override
    public int hashCode() {
        int result = data.hashCode();
        result = 31 * result + (int) (time ^ (time >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "MyEvent{" +
                "data='" + data + '\'' +
                ", time=" + time +
                '}';
    }
}
