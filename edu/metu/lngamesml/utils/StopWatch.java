package edu.metu.lngamesml.utils;

import edu.metu.lngamesml.exception.ZeroDurationException;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: 12/28/10
 * Time: 4:50 AM
 * To change this template use File | Settings | File Templates.
 */

public final class StopWatch {

    private static long begin = 0;
    private static long end = 0;

    private final int STATE_RUNNING = 0;
    private final int STATE_STOPPED = 1;
    private final int STATE_PAUSED = 2;
    private final int STATE_CANCELLED = 3;
    private final int STATE_RESET = 4;

    private int CurrentState = 1;

    public void startTimer(){
        if (CurrentState != STATE_RUNNING) {
            begin = System.nanoTime();
            CurrentState = STATE_RUNNING;
        }
    }

    public void stopTimer() {
        if(CurrentState == STATE_RUNNING || CurrentState == STATE_CANCELLED){
            CurrentState = STATE_STOPPED;
            end = System.nanoTime();
        }
    }

    public void pauseTimer() {
        if(CurrentState == STATE_RUNNING){
            CurrentState = STATE_PAUSED;
            end = System.nanoTime();
        }
    }

    public void cancelTimer() {
        if(CurrentState == STATE_RUNNING || CurrentState == STATE_CANCELLED){
            CurrentState = STATE_PAUSED;
            end = System.nanoTime();
        }
    }

    public long getDuration () throws ZeroDurationException {
        long duration = 0L;
        if (CurrentState == STATE_RESET) {
            throw new ZeroDurationException("Watch state is zero can't retrieve the duration");
        }
        else if (CurrentState != STATE_STOPPED) {
            stopTimer();
        }
        duration = end - begin;
        return duration;
    }

    public void resetWatch(){
        CurrentState = STATE_RESET;
        begin = 0;
        end = 0;
    }
}
