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
package net.tridentsdk.api.entity.projectile;

import net.tridentsdk.api.entity.Projectile;

/**
 * A hook at the end of the fishing rod that can catch fish or damage entities
 *
 * @author The TridentSDK Team
 */
public interface FishHook extends Projectile {
    /**
     * The chance that a fish will be caught on the hook
     * <p/>
     * <p>Works only in water</p>
     *
     * @return the chance a fish will be caught on the hook
     */
    float getBiteChance();

    /**
     * Sets the chance the fish will bite the hook
     *
     * @param chance the chance the fish will bite the hook
     * @return unspecified TODO
     */
    float setBiteChance(float chance);
}
