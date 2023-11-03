package net.abyssdev.abyssvouchers.command.sub;

import net.abyssdev.abysslib.command.AbyssSubCommand;
import net.abyssdev.abysslib.command.context.CommandContext;
import net.abyssdev.abysslib.placeholder.PlaceholderReplacer;
import net.abyssdev.abysslib.utils.Utils;
import net.abyssdev.abyssvouchers.AbyssVouchers;
import net.abyssdev.abyssvouchers.voucher.Voucher;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.eclipse.collections.api.factory.Sets;

import java.util.Optional;

/**
 * The give sub command
 *
 * @author Relocation
 */
public final class GiveSubCommand extends AbyssSubCommand<AbyssVouchers> {

    /**
     * Constructs a new GiveSubCommand
     *
     * @param plugin The abyss vouchers plugin
     */
    public GiveSubCommand(final AbyssVouchers plugin) {
        super(plugin, 3, Sets.immutable.of("give"));
    }

    @Override
    public void execute(final CommandContext<?> context) {

        final CommandSender sender = context.getSender();

        if (!sender.hasPermission("abyssvouchers.admin")) {
            this.plugin.getMessageCache().sendMessage(sender, "messages.no-permission");
            return;
        }

        final Player player = context.asPlayer(0);

        if (player == null) {
            this.plugin.getMessageCache().sendMessage(sender, "messages.admin-help");
            return;
        }

        final Optional<Voucher> voucherOpt = this.plugin.getVoucherRegistry().get(context.asString(1));

        if (!voucherOpt.isPresent()) {
            this.plugin.getMessageCache().sendMessage(sender, "messages.admin-help");
            return;
        }

        final Voucher voucher = voucherOpt.get();
        final ItemStack item = voucher.getItem().clone();

        final int amount = context.asInt(2);

        item.setAmount(amount);

        this.plugin.getMessageCache().sendMessage(sender, "messages.voucher-given", new PlaceholderReplacer()
                .addPlaceholder("%player%", player.getName())
                .addPlaceholder("%type%", voucher.getFormattedName())
                .addPlaceholder("%amount%", Utils.format(amount)));

        this.plugin.getMessageCache().sendMessage(player, "messages.voucher-received", new PlaceholderReplacer()
                .addPlaceholder("%player%", player.getName())
                .addPlaceholder("%type%", voucher.getFormattedName())
                .addPlaceholder("%amount%", Utils.format(amount)));

        if (player.getInventory().firstEmpty() == -1) {
            player.getWorld().dropItem(player.getLocation(), item);
            return;
        }

        player.getInventory().addItem(item);
    }

}