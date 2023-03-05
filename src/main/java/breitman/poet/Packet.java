package breitman.poet;

import io.netty.buffer.ByteBuf;
import java.util.Collection;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Packet {

  <T> void write(@NotNull Type<T> type, @NotNull T value);

  <T> void write(@NotNull Type.Throwing<T> type, @NotNull T value) throws Throwable;

  <T> void write(@NotNull Type.Throwing<T> type, @NotNull T value, @Nullable Consumer<Throwable> exceptionally);

  <T> void writeCollection(@NotNull Type<T> type, @Nullable Collection<@NotNull T> values);

  <T> void writeCollection(@NotNull Type<T> type, @NotNull T @Nullable ... values);

  <T> void writeCollection(@NotNull Type.Throwing<T> type, @Nullable Collection<@NotNull T> values) throws Throwable;

  <T> void writeCollection(@NotNull Type.Throwing<T> type, @Nullable Collection<@NotNull T> values, @Nullable Consumer<Throwable> exceptionally);

  <T> void writeCollection(@NotNull Type.Throwing<T> type, @NotNull T @Nullable ... values) throws Throwable;

  @NotNull ByteBuf bytes();

}