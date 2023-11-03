package net.abyssdev.abyssvouchers.voucher.registry;

import net.abyssdev.abysslib.patterns.registry.Registry;
import net.abyssdev.abyssvouchers.AbyssVouchers;
import net.abyssdev.abyssvouchers.voucher.Voucher;
import org.eclipse.collections.api.factory.Maps;

import java.util.Map;

/**
 * The voucher registry
 *
 * @author Relocation
 */
public final class VoucherRegistry implements Registry<String, Voucher> {

    private final Map<String, Voucher> vouchers = Maps.mutable.empty();

    /**
     * Constructs a new VoucherRegistry
     *
     * @param plugin The abyss vouchers plugin
     */
    public VoucherRegistry(final AbyssVouchers plugin) {
        for (final String key : plugin.getConfig().getSectionKeys("vouchers")) {
            this.vouchers.put(key, new Voucher(plugin.getConfig(), key));
        }
    }

    @Override
    public Map<String, Voucher> getRegistry() {
        return this.vouchers;
    }

}