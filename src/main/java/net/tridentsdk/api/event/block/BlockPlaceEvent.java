/*
 * Trident - A Multithreaded Server Alternative
 * Copyright 2014 The TridentSDK Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.tridentsdk.api.event.block;

import net.tridentsdk.api.Block;
import net.tridentsdk.api.BlockFace;
import net.tridentsdk.api.Material;
import net.tridentsdk.api.entity.living.Player;
import net.tridentsdk.api.event.Ignorable;

/**
 * Called whenever a block is placed
 */
public class BlockPlaceEvent extends BlockEvent implements Ignorable {
    private final Player player;
    private final Block blockClicked;
    private final BlockFace faceClicked;

    private boolean cancel;

    /**
     * @param player       Player who placed this block
     * @param block        Block that was placed
     * @param blockClicked Block
     * @param faceClicked  BlockFace
     */
    public BlockPlaceEvent(Player player, Block block, Block blockClicked, BlockFace faceClicked) {
        super(block);
        this.player = player;
        this.blockClicked = blockClicked;
        this.faceClicked = faceClicked;
    }

    /**
     * Gets the block face of the block that was clicked on to place this block
     *
     * @return BlockFace of the clicked block
     */
    public BlockFace getFaceClicked() {
        return this.faceClicked;
    }

    /**
     * Get the Material of the placed block
     *
     * @return Material of the placed block
     */
    public Material getTypePlaced() {
        return this.getBlock().getMaterial();
    }

    @Override
    public boolean isIgnored() {
        return cancel;
    }

    @Override
    public void ignore(boolean cancel) {
        this.cancel = cancel;
    }

    /**
     * Gets the block clicked on to place this block
     *
     * @return Block that was clicked
     */
    public Block getBlockClicked() {
        return this.blockClicked;
    }

    /**
     * Returns the player associated with this event
     *
     * @return Player who placed the block
     */
    public Player getPlayer() {
        return this.player;
    }
}
