package net.abyssdev.abyssvouchers;

import lombok.Getter;
import net.abyssdev.abysslib.command.AbyssCommand;
import net.abyssdev.abysslib.config.AbyssConfig;
import net.abyssdev.abysslib.patterns.registry.Registry;
import net.abyssdev.abysslib.plugin.AbyssPlugin;
import net.abyssdev.abysslib.storage.common.CommonStorageImpl;
import net.abyssdev.abysslib.text.MessageCache;
import net.abyssdev.abyssvouchers.command.VoucherCommand;
import net.abyssdev.abyssvouchers.command.sub.GiveSubCommand;
import net.abyssdev.abyssvouchers.listeners.InteractListener;
import net.abyssdev.abyssvouchers.player.VoucherPlayer;
import net.abyssdev.abyssvouchers.player.storage.PlayerStorage;
import net.abyssdev.abyssvouchers.voucher.Voucher;
import net.abyssdev.abyssvouchers.voucher.registry.VoucherRegistry;
import org.bukkit.command.CommandSender;

import java.util.UUID;

/**
 * The abyss vouchers main class
 */
@Getter
public final class AbyssVouchers extends AbyssPlugin {

    private final AbyssConfig config = this.getAbyssConfig("vouchers");
    private final AbyssConfig lang = this.getAbyssConfig("lang");

    private final MessageCache messageCache = new MessageCache(this.lang);

    private final CommonStorageImpl<UUID, VoucherPlayer> playerStorage = new CommonStorageImpl<>(new PlayerStorage(this));
    private final Registry<String, Voucher> voucherRegistry = new VoucherRegistry(this);

    private final AbyssCommand<AbyssVouchers, CommandSender> command = new VoucherCommand(this);

    @Override
    public void onEnable() {
        this.loadMessages(this.messageCache, this.lang);

        this.command.register();
        this.command.register(new GiveSubCommand(this));

        new InteractListener(this);
    }

    @Override
    public void onDisable() {
        this.command.unregister();
        this.playerStorage.write();
    }

}