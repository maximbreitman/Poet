package breitman.poet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractPoet<Source> implements Poet<Source> {

  @Override
  public void act(@NotNull Source source, int packetId, @NotNull Writer writer, @NotNull Action action) {
    final Channel channel = getChannel(source);
    final int protocol = getProtocol(source);
    channel.eventLoop().execute(() -> {
      final ByteBuf buf = allocate(channel);
      Type.VAR_INT.write(buf, packetId);

      final Packet packet = new PacketImpl(buf);
      writer.write(packet, protocol);

      write(action, channel, packet.bytes());
    });
  }

  protected @NotNull ByteBuf allocate(@NotNull Channel channel) {
    return channel.alloc().ioBuffer(256).setIndex(4, 4);
  }

  protected abstract @NotNull Channel getChannel(@NotNull Source source);

  protected abstract int getProtocol(@NotNull Source source);

  protected void write(@NotNull Action action, @NotNull Channel channel, @NotNull ByteBuf buf) {
    switch (action) {
      case WRITE -> channel.write(buf, channel.voidPromise());

      case WRITE_AND_FLUSH -> channel.writeAndFlush(buf, channel.voidPromise());

      case WRITE_AND_CLOSE -> channel.writeAndFlush(buf).addListener(ChannelFutureListener.CLOSE);
    }
  }

}