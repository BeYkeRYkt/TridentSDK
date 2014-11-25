/*
 *     TridentSDK - A Minecraft Server API
 *     Copyright (C) 2014, The TridentSDK Team
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.tridentsdk.api.scheduling;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public abstract class TridentRunnable implements Runnable {
    private static int currentId = 0;

    private final AtomicInteger id = new AtomicInteger(-1);
    private final boolean inAHurry = false;

    private final AtomicReference<Task> task = new AtomicReference<>();

    public TridentRunnable() {
        id.set(currentId += 1);
    }

    /**
     * Returns if this runnable is in a hurry, usually indicating that the server is shutting down
     */
    public boolean isInAHurry() {
        return this.inAHurry;
    }

    /**
     * Guaranteed to be run before this Runnable on the main thread, even if this runnable is going to be run
     * asynchronously, useful for collecting resources to work on.
     */
    public void prerunSync() {
    }

    /**
     * Runs after this runnable has finished, asynchronously
     */
    public void runAfterAsync() {
    }

    /**
     * Runs after this runnable has finished, synchronously
     */
    public void runAfterSync() {
    }

    /**
     * Cancels the task and removes from execution. See {@link net.tridentsdk.api.scheduling.Task#cancel()}
     *
     * WARNING: This is a delegated function. DO NOT call this method before it is scheduled. A NullPointerException
     * will be thrown. This can be called when {@code getTask() != null}.
     */
    public final void cancel() {
        task.get().cancel();
    }

    /**
     * Gets how long between runs this is supposed to wait if it is a repeating task
     *
     * WARNING: This is a delegated function. DO NOT call this method before it is scheduled. A NullPointerException
     * will be thrown. This can be called when {@code getTask() != null}.
     */
    public final long getInterval() {
        return task.get().getInterval();
    }

    /**
     * Sets how long this runnable should wait between executions if this is a repeating task <p>If this task is
     * synchronous to the main thread, the change will be immediate, if it is not, the change may take an iteration to
     * take effect, however {@link TridentRunnable#getInterval()} will reflect the changes immediately</p>
     *
     * WARNING: This is a delegated function. DO NOT call this method before it is scheduled. A NullPointerException
     * will be thrown. This can be called when {@code getTask() != null}.
     */
    public final void setInterval(long interval) {
        task.get().setInterval(interval);
    }

    /**
     * Used internally to refer to this runnable, probably shouldn't be used by plugins
     */
    public final int getId() {
        return this.id.get();
    }

    /**
     * Should be reimplemented by a runnable if it wants to use in a hurry <p>Reimplement to return true</p>
     */
    public boolean usesInAHurry() {
        return false;
    }

    /**
     * The scheduled representation of the runnable
     *
     * @return the {@link net.tridentsdk.api.scheduling.Task} object held within the scheduling implementation
     */
    public Task getTask() {
        return this.task.get();
    }

    /**
     * Invoked by the scheduler to mark that the task has been scheduled.
     *
     * @param task the task handed down to delegate off functionality
     */
    public void markSchedule(Task task) {
        this.task.set(task);
    }
}
