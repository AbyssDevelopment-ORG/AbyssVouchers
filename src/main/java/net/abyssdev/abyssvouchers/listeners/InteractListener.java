package net.abyssdev.abyssvouchers.listeners;

import net.abyssdev.abysslib.listener.AbyssListener;
import net.abyssdev.abysslib.nbt.NBTUtils;
import net.abyssdev.abyssvouchers.AbyssVouchers;
import net.abyssdev.abyssvouchers.player.VoucherPlayer;
import net.abyssdev.abyssvouchers.voucher.Voucher;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

/**
 * The interaction listener
 *
 * @author Relocation
 */
public final class InteractListener extends AbyssListener<AbyssVouchers> {

    /**
     * Constructs a new InteractListener
     *
     * @param plugin The abyss vouchers plugin
     */
    public InteractListener(final AbyssVouchers plugin) {
        super(plugin);
    }

    @EventHandler
    public void onInteract(final PlayerInteractEvent event) {

        final Player player = event.getPlayer();
        final ItemStack item = player.getItemInHand();

        if (item == null || item.getType() == Material.AIR) {
            return;
        }

        if (!NBTUtils.get().contains(item, "ABYSSVOUCHER")) {
            return;
        }

        final Optional<Voucher> voucherOpt = this.plugin.getVoucherRegistry().get(NBTUtils.get().getString(item, "ABYSSVOUCHER"));

        if (!voucherOpt.isPresent()) {
            return;
        }

        final Voucher voucher = voucherOpt.get();

        if (!player.hasPermission(voucher.getPermission())) {
            this.plugin.getMessageCache().sendMessage(player, "messages.no-permission");
            return;
        }

        final VoucherPlayer profile = this.plugin.getPlayerStorage().get(player.getUniqueId());

        if (voucher.getLimit() > -1 && profile.getRedeemed().getOrDefault(voucher.getName(), 0) >= voucher.getLimit()) {
            this.plugin.getMessageCache().sendMessage(player, "messages.limit-reached");
            return;
        }

        voucher.apply(player);

        if (item.getAmount() > 1) {
            item.setAmount(item.getAmount() - 1);
            return;
        }

        player.setItemInHand(new ItemStack(Material.AIR));

    }

}