package com.baidu.oped.sia.boot.client;

/**
 * Root of all the remote invoking.
 *
 * @author mason
 */
public interface Client {

    <T> void execute(Task<T> task);
}
