package com.baidu.oped.sia.boot.client;

/**
 * Root of all the remote invoking.
 *
 * @author mason
 */
public interface Client {

    /**
     * Execute the remoting task.
     *
     * @param task remoting task
     * @param <T>  Task result type
     */
    <T> void execute(Task<T> task);
}
