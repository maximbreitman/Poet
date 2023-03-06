package breitman.poet;

import org.jetbrains.annotations.NotNull;

public interface Poet<Source> {

  void act(@NotNull Source source, int packetId, @NotNull Writer writer, @NotNull Action action);

  default void close(@NotNull Source player, int packetId, @NotNull Writer writer) {
    this.act(player, packetId, writer, Action.WRITE_AND_CLOSE);
  }

  default void flush(@NotNull Source player, int packetId, @NotNull Writer writer) {
    this.act(player, packetId, writer, Action.WRITE_AND_FLUSH);
  }

  default void write(@NotNull Source player, int packetId, @NotNull Writer writer) {
    this.act(player, packetId, writer, Action.WRITE);
  }

  @FunctionalInterface
  interface Writer {

    void write(@NotNull Packet packet, int protocol);

  }

}