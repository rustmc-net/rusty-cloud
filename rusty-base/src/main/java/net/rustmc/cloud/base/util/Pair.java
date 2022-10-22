package net.rustmc.cloud.base.util;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * This class belongs to the HarmanCloud project
 *
 * @author Alexander Jilge
 * @since 21.10.2022
 */
@AllArgsConstructor
@Data
public final class Pair<F, S> {

    private F first;
    private S second;

}
