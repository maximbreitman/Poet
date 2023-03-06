package breitman.poet;

import io.netty.channel.Channel;
import net.minecraft.SharedConstants;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpigotPoetImpl extends AbstractPoet<Player>
{
    @Override
    protected @NotNull Channel getChannel(@NotNull Player player) {
        return ((CraftPlayer)player).getHandle().b.b.m;
    }

    @Override
    protected int getProtocol(@NotNull Player player) {
        return SharedConstants.b().getProtocolVersion();
    }
}
