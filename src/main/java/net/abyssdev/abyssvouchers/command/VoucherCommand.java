package net.abyssdev.abyssvouchers.command;

import net.abyssdev.abysslib.command.AbyssCommand;
import net.abyssdev.abysslib.command.context.CommandContext;
import net.abyssdev.abyssvouchers.AbyssVouchers;
import org.bukkit.command.CommandSender;
import org.eclipse.collections.api.factory.Lists;

/**
 * The voucher command
 *
 * @author Relocation
 */
public final class VoucherCommand extends AbyssCommand<AbyssVouchers, CommandSender> {

    /**
     * Constructs a new VoucherCommand
     *
     * @param plugin The abyss vouchers plugin
     */
    public VoucherCommand(final AbyssVouchers plugin) {
        super(
                plugin,
                "voucher",
                "The base abyss vouchers command",
                Lists.mutable.of(
                        "vouchers",
                        "abyssvoucher",
                        "abyssvouchers",
                        "avoucher",
                        "avouchers"
                ),
                CommandSender.class);
    }

    @Override
    public void execute(final CommandContext<CommandSender> context) {
        final CommandSender sender = context.getSender();

        if (!sender.hasPermission("abyssvouchers.admin")) {
            this.plugin.getMessageCache().sendMessage(sender, "messages.no-permission");
            return;
        }

        this.plugin.getMessageCache().sendMessage(sender, "messages.admin-help");
    }

}