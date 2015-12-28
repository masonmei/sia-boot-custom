package com.baidu.oped.sia.boot.client;

/**
 * Represent a remote invoking task.
 *
 * @author mason
 */
public interface Task<T> {

    /**
     * Get the task context.
     *
     * @return context.
     */
    Context<T> context();

    /**
     * Execute the task and save the result to context.
     */
    void execute();
}
