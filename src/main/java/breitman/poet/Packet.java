package breitman.poet;

import io.netty.buffer.ByteBuf;
import java.util.Collection;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Packet {

  <T> void write(@NotNull Type<T> type, T value);

  <T> void write(@NotNull Type.Throwing<T> type, T value) throws Throwable;

  <T> void write(@NotNull Type.Throwing<T> type, T value, @Nullable Consumer<Throwable> exceptionally);

  <T> void writeCollection(@NotNull Type<T> type, Collection<T> values);

  <T> void writeCollection(@NotNull Type<T> type, T... values);

  <T> void writeCollection(@NotNull Type.Throwing<T> type, Collection<T> values) throws Throwable;

  <T> void writeCollection(@NotNull Type.Throwing<T> type, Collection<T> values, @Nullable Consumer<Throwable> exceptionally);

  <T> void writeCollection(@NotNull Type.Throwing<T> type, T... values) throws Throwable;

  <T> void writeOptional(@NotNull Type<T> type, @Nullable T value);

  <T> void writeOptional(@NotNull Type.Throwing<T> type, @Nullable T value) throws Throwable;

  <T> void writeOptional(@NotNull Type.Throwing<T> type, @Nullable T value, @Nullable Consumer<Throwable> exceptionally);

  @NotNull ByteBuf bytes();

}