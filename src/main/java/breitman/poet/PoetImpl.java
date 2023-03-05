package breitman.poet;

import io.netty.channel.Channel;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;

public final class PoetImpl<T> extends AbstractPoet<T> {

  private final Function<@NotNull T, @NotNull Channel> tToChannel;
  private final Function<@NotNull T, @NotNull Integer> tToProtocol;

  public PoetImpl(Function<@NotNull T, @NotNull Channel> tToChannel,
                  Function<@NotNull T, @NotNull Integer> tToProtocol) {
    this.tToChannel = tToChannel;
    this.tToProtocol = tToProtocol;
  }

  @Override
  protected @NotNull Channel getChannel(@NotNull T t) {
    return tToChannel.apply(t);
  }

  @Override
  protected int getProtocol(@NotNull T t) {
    return tToProtocol.apply(t);
  }

}