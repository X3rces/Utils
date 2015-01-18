package me.xerces.utils.managers;

import java.util.concurrent.TimeUnit;

/**
 * @author Xerces
 * @since 18/01/2015
 */
public class TimeManager {

    private long lastTime;

    /**
     * Initialize the TimeManager without setting the {@link #lastTime}
     */
    public TimeManager()
    {
        this(false);
    }

    /**
     * Intialize the TimeManager
     * @param setTime whether or not to set the {@link #lastTime}
     */
    public TimeManager(boolean setTime)
    {
        if(setTime)
            updateTime();
    }

    /**
     * Update the {@link #lastTime}
     */
    public void updateTime()
    {
        this.lastTime = System.nanoTime();
    }

    /**
     * Checks if the specified time has elapsed
     * @param time the time to check
     * @param timeUnit the unit of time
     * @return true if the time has elapsed false if not
     */
    public boolean hasTimeElapsed(long time, TimeUnit timeUnit)
    {
        return timeUnit.convert(lastTime + time, TimeUnit.NANOSECONDS) <= timeUnit.convert(System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    /**
     * Returns the elapsed time
     * @param timeUnit the unit of time
     * @return the elapsed time in the specified unit of time
     */
    public long getElapsedTime(TimeUnit timeUnit)
    {
        return timeUnit.convert(System.nanoTime() - lastTime, TimeUnit.NANOSECONDS);
    }

    public String toString()
    {
        return String.format("%dms has elapsed.", getElapsedTime(TimeUnit.MILLISECONDS));
    }
}
