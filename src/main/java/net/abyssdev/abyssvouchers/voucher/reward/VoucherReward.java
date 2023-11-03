package net.abyssdev.abyssvouchers.voucher.reward;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.abyssdev.abysslib.text.message.Message;

import java.util.List;

/**
 * The voucher reward wrapper
 */
@Getter
@RequiredArgsConstructor
public final class VoucherReward {

    private final Message message;
    private final List<String> commands;

}