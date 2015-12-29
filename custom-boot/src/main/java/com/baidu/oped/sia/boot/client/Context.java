package com.baidu.oped.sia.boot.client;

/**
 * Task execution Context.
 * Holding the following information:
 * <li>target connection information</li>
 * <li>parameters</li>
 * <li>execution steps</li>
 * <li>execution result</li>
 *
 * @author mason
 */
public interface Context<T> {

    /**
     * Get the context information.
     *
     * @return String present context.
     */
    String contextInfo();

    /**
     * Get the execution times.
     *
     * @return execution times.
     */
    int getExecutions();

    /**
     * Check if the task complete successfully.
     *
     * @return complete or not
     */
    boolean isComplete();

    /**
     * Mark the execution of the task as completed.
     */
    void markComplete();

    /**
     * Mark an execution of the task.
     */
    void markExecution();

    /**
     * Get the task execution result.
     *
     * @return task execution result.
     */
    T result();

    /**
     * Set the execution result to context.
     *
     * @param result execution result.
     */
    void setResult(T result);
}
