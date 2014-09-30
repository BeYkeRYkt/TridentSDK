package net.tridentsdk.api.event.player;

import net.tridentsdk.api.Block;
import net.tridentsdk.api.entity.living.Player;
import net.tridentsdk.api.event.Cancellable;

/**
 * Called when a player tries to leave a bed
 */
public class PlayerLeaveSleepEvent extends PlayerEvent implements Cancellable {
    private final Block bed;
    private boolean cancelled;

    public PlayerLeaveSleepEvent(Player player, Block bed) {
        super(player);
        this.bed = bed;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * Gets the bed that the player tried to leave
     * @return
     */
    public Block getBed() {
        return bed;
    }
}
