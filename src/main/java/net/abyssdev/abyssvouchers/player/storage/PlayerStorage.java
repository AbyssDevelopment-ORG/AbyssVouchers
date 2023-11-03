package net.abyssdev.abyssvouchers.player.storage;

import net.abyssdev.abysslib.patterns.registry.impl.EclipseRegistry;
import net.abyssdev.abysslib.storage.json.JsonStorage;
import net.abyssdev.abysslib.utils.file.Files;
import net.abyssdev.abyssvouchers.AbyssVouchers;
import net.abyssdev.abyssvouchers.player.VoucherPlayer;

import java.util.UUID;

/**
 * The player storage
 *
 * @author Relocation
 */
public final class PlayerStorage extends JsonStorage<UUID, VoucherPlayer> {

    /**
     * Constructs a new PlayerStorage
     *
     * @param plugin The abyss vouchers plugin
     */
    public PlayerStorage(final AbyssVouchers plugin) {
        super(Files.file("data.json", plugin), VoucherPlayer.class, new EclipseRegistry<>());
    }

    @Override
    public VoucherPlayer constructValue(final UUID key) {
        return new VoucherPlayer(key);
    }

}