package breitman.poet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

@FunctionalInterface
public interface Type<T> {

  Type<Boolean> BOOLEAN = ByteBuf::writeBoolean;

  Type<Byte> BYTE = ByteBuf::writeByte;

  Type<Short> SHORT = ByteBuf::writeShort;

  Type<Integer> UNSIGNED_SHORT = (buf, boxed) -> {
    buf.writeShort((short) (boxed & 0xFFFF));
  };

  Type<Integer> INT = ByteBuf::writeInt;

  Type<Long> LONG = ByteBuf::writeLong;

  Type<Float> FLOAT = ByteBuf::writeFloat;

  Type<Double> DOUBLE = ByteBuf::writeDouble;

  Type<Integer> VAR_INT = (buf, boxed) -> {
    final int value = boxed;
    if ((value & (0xFFFFFFFF << 7)) == 0) {
      buf.writeByte(value);
    } else if ((value & (0xFFFFFFFF << 14)) == 0) {
      //noinspection DuplicateExpressions
      int w = (value & 0x7F | 0x80) << 8 | (value >>> 7);
      buf.writeShort(w);
    } else {
      if ((value & (0xFFFFFFFF << 7)) == 0) {
        buf.writeByte(value);
      } else if ((value & (0xFFFFFFFF << 14)) == 0) {
        //noinspection DuplicateExpressions
        int w = (value & 0x7F | 0x80) << 8 | (value >>> 7);
        buf.writeShort(w);
      } else if ((value & (0xFFFFFFFF << 21)) == 0) {
        int w = (value & 0x7F | 0x80) << 16 | ((value >>> 7) & 0x7F | 0x80) << 8 | (value >>> 14);
        buf.writeMedium(w);
      } else if ((value & (0xFFFFFFFF << 28)) == 0) {
        int w = (value & 0x7F | 0x80) << 24 | (((value >>> 7) & 0x7F | 0x80) << 16)
            | ((value >>> 14) & 0x7F | 0x80) << 8 | (value >>> 21);
        buf.writeInt(w);
      } else {
        int w = (value & 0x7F | 0x80) << 24 | ((value >>> 7) & 0x7F | 0x80) << 16
            | ((value >>> 14) & 0x7F | 0x80) << 8 | ((value >>> 21) & 0x7F | 0x80);
        buf.writeInt(w);
        buf.writeByte(value >>> 28);
      }
    }
  };

  Type<Long> VAR_LONG = (buf, value) -> {
    while (true) {
      if ((value & ~((long) 0x7F)) == 0) {
        buf.writeByte((byte) value.intValue());
        return;
      }

      buf.writeByte((byte) (value & 0x7F | 0x80));

      // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
      value >>>= 7;
    }
  };

  Type<byte[]> RAW_BYTES = ByteBuf::writeBytes;

  Type<String> STRING = (buf, string) -> {
    final int size = ByteBufUtil.utf8Bytes(string);
    VAR_INT.write(buf, size);
    buf.writeCharSequence(string, StandardCharsets.UTF_8);
  };

  Type<Component> CHAT = (buf, component) -> {
    final String json = GsonComponentSerializer.gson().serialize(component);
    STRING.write(buf, json);
  };

  Type<UUID> UUID = (buf, uuid) -> {
    buf.writeLong(uuid.getMostSignificantBits());
    buf.writeLong(uuid.getLeastSignificantBits());
  };

  Type<Enum<?>> ENUM = (buf, value) -> {
    VAR_INT.write(buf, value.ordinal());
  };

  Type<byte[]> BYTE_ARRAY = (buf, array) -> {
    VAR_INT.write(buf, array.length);
    buf.writeBytes(array);
  };

  Type<long[]> LONG_ARRAY = (buf, array) -> {
    VAR_INT.write(buf, array.length);
    for (long value : array) {
      buf.writeLong(value);
    }
  };

  Type<int[]> VAR_INT_ARRAY = (buf, array) -> {
    VAR_INT.write(buf, array.length);
    for (int value : array) {
      VAR_INT.write(buf, value);
    }
  };

  Type<long[]> VAR_LONG_ARRAY = (buf, array) -> {
    VAR_INT.write(buf, array.length);
    for (long value : array) {
      VAR_LONG.write(buf, value);
    }
  };

  Type<Integer> OPT_VAR_INT = (buf, value) -> {
    VAR_INT.write(buf, value == null ? 0 : value + 1);
  };

  void write(@NotNull ByteBuf buf, @UnknownNullability T t);

  @FunctionalInterface
  interface Throwing<T> {

    void write(@NotNull ByteBuf buf, @UnknownNullability T t) throws Throwable;

  }

}