package net.abyssdev.abyssvouchers.player;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.abyssdev.abysslib.storage.id.Id;
import org.eclipse.collections.api.factory.Maps;

import java.util.Map;
import java.util.UUID;

/**
 * The voucher player profile object
 *
 * @author Relocation
 */
@Getter
@RequiredArgsConstructor
public final class VoucherPlayer {

    @Id
    private final UUID uuid;
    private final Map<String, Integer> redeemed = Maps.mutable.empty();


}