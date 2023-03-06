package breitman.poet;

import static breitman.poet.Type.BOOLEAN;
import static breitman.poet.Type.BYTE;
import static breitman.poet.Type.Throwing;
import static breitman.poet.Type.VAR_INT;

import io.netty.buffer.ByteBuf;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PacketImpl implements Packet {

  protected final ByteBuf buf;

  public PacketImpl(@NotNull ByteBuf buf) {
    this.buf = buf;
  }

  @Override
  public <T> void write(@NotNull Type<T> type, T value) {
    type.write(buf, value);
  }

  @Override
  public <T> void write(@NotNull Type.Throwing<T> type, T value) throws Throwable {
    type.write(buf, value);
  }

  @Override
  public <T> void write(@NotNull Throwing<T> type, T value, @Nullable Consumer<Throwable> exceptionally) {
    try {
      write(type, value);
    } catch (Throwable e) {
      if (exceptionally != null) {
        exceptionally.accept(e);
      }
    }
  }

  @Override
  public <T> void writeCollection(@NotNull Type<T> type, Collection<T> values) {
    if (values == null) {
      write(BYTE, (byte) 0);
      return;
    }
    write(VAR_INT, values.size());
    for (T value : values) {
      write(type, value);
    }
  }

  @Override
  public <T> void writeCollection(@NotNull Type<T> type, T... values) {
    writeCollection(type, values == null ? null : List.of(values));
  }

  @Override
  public <T> void writeCollection(@NotNull Type.Throwing<T> type, Collection<T> values) throws Throwable {
    if (values == null) {
      write(BYTE, (byte) 0);
      return;
    }
    write(VAR_INT, values.size());
    for (T value : values) {
      write(type, value);
    }
  }

  @Override
  public <T> void writeCollection(@NotNull Throwing<T> type, Collection<T> values,
                                  @Nullable Consumer<Throwable> exceptionally) {
    try {
      writeCollection(type, values);
    } catch (Throwable e) {
      if (exceptionally != null) {
        exceptionally.accept(e);
      }
    }
  }

  @Override
  public <T> void writeCollection(@NotNull Type.Throwing<T> type, T... values) throws Throwable {
    writeCollection(type, values == null ? null : List.of(values));
  }

  @Override
  public <T> void writeOptional(@NotNull Type<T> type, @Nullable T value) {
    write(BOOLEAN, value != null);
    if (value != null) {
      write(type, value);
    }
  }

  @Override
  public <T> void writeOptional(@NotNull Throwing<T> type, @Nullable T value) throws Throwable {
    write(BOOLEAN, value != null);
    if (value != null) {
      write(type, value);
    }
  }

  @Override
  public <T> void writeOptional(@NotNull Throwing<T> type, @Nullable T value,
                                @Nullable Consumer<Throwable> exceptionally) {
    write(BOOLEAN, value != null);
    if (value != null) {
      try {
        write(type, value);
      } catch (Throwable e) {
        if (exceptionally != null) {
          exceptionally.accept(e);
        }
      }
    }
  }

  @Override
  public @NotNull ByteBuf bytes() {
    return this.buf;
  }

}