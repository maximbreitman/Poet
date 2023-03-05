package breitman.poet;

import org.jetbrains.annotations.NotNull;

public interface Poet<T> {

  void act(@NotNull T t, int packetId, @NotNull Writer writer, @NotNull Action action);

  default void close(@NotNull T player, int packetId, @NotNull Writer writer) {
    this.act(player, packetId, writer, Action.WRITE_AND_CLOSE);
  }

  default void flush(@NotNull T player, int packetId, @NotNull Writer writer) {
    this.act(player, packetId, writer, Action.WRITE_AND_FLUSH);
  }

  default void write(@NotNull T player, int packetId, @NotNull Writer writer) {
    this.act(player, packetId, writer, Action.WRITE);
  }

  @FunctionalInterface
  interface Writer {

    void write(@NotNull Packet packet, int protocol);

  }

}