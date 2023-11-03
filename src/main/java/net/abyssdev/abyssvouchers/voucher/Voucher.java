package net.abyssdev.abyssvouchers.voucher;

import lombok.Getter;
import net.abyssdev.abysslib.chanced.ChancedCollection;
import net.abyssdev.abysslib.config.AbyssConfig;
import net.abyssdev.abysslib.nbt.NBTUtils;
import net.abyssdev.abysslib.text.message.Message;
import net.abyssdev.abysslib.utils.PlayerUtils;
import net.abyssdev.abysslib.utils.WordUtils;
import net.abyssdev.abyssvouchers.voucher.reward.VoucherReward;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * The voucher object wrapper
 *
 * @author Relocation
 */
@Getter
public final class Voucher {

    private final String name, formattedName;
    private final String permission;

    private final ItemStack item;
    private final ChancedCollection<VoucherReward> rewards = new ChancedCollection<>();

    private final int limit, min, max;

    /**
     * Constructs a new Voucher
     *
     * @param config The config
     * @param key The voucher name
     */
    public Voucher(final AbyssConfig config, final String key) {
        this.name = key;
        this.formattedName = WordUtils.formatText(WordUtils.removeChars(key, '-', '"'));
        this.permission = config.getString("vouchers." + key + ".permission");

        this.item = NBTUtils.get().setString(config.getItemStack("vouchers." + key), "ABYSSVOUCHER", key);

        this.limit = config.getInt("vouchers." + key + ".limit");

        this.min = config.getInt("vouchers." + key + ".reward-settings.min");
        this.max = config.getInt("vouchers." + key + ".reward-settings.max");

        for (final String rewardKey : config.getSectionKeys("vouchers." + key + ".reward-settings.rewards")) {
            final Message message = new Message(config, "vouchers." + key + ".reward-settings.rewards." + rewardKey + ".message");
            final List<String> commands = config.getStringList("vouchers." + key + ".reward-settings.rewards." + rewardKey + ".commands");

            final double chance = config.getDouble("vouchers." + key + ".reward-settings.rewards." + rewardKey + ".chance");

            this.rewards.put(new VoucherReward(message, commands), chance);
        }
    }

    public void apply(final Player player) {
        this.rewards.attemptUnbrokenSelection(reward -> {
            PlayerUtils.dispatchCommands(player, reward.getCommands());
            reward.getMessage().send(player);
        });
    }

}