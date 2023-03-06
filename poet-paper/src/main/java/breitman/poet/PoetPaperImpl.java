package breitman.poet;

import io.netty.channel.Channel;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PoetPaperImpl extends AbstractPoet<Player>
{

    @Override
    protected @NotNull Channel getChannel(@NotNull Player player) {
        return ((CraftPlayer)player).getHandle().connection.connection.channel;
    }

    @Override
    protected int getProtocol(@NotNull Player player) {
        return player.getProtocolVersion();
    }
}
