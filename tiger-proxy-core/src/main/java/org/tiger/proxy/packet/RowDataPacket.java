package org.tiger.proxy.packet;

import io.netty.buffer.ByteBuf;
import org.tiger.proxy.utils.MySqlByteBufUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * From server to client. One packet for each row in the result set.
 * <p/>
 * <pre>
 * Bytes                   Name
 * --------------------------------------
 * n (Length Coded String) (column value)
 * ...
 *
 * (column value):         The data in the column, as a character string.
 *                         If a column is defined as non-character, the
 *                         server converts the value into a character
 *                         before sending it. Since the value is a Length
 *                         Coded String, a NULL can be represented with a
 *                         single byte containing 251(see the description
 *                         of Length Coded Strings in section "Elements" above).
 * </pre>
 *
 * @author fish
 */
public class RowDataPacket extends MysqlPacket {

    private static final byte NULL_MARK = (byte) 251;

    //这是额外加的字段,返回结果集并不存在,必须在filed里面解析出来
    private final int fieldCount;
    //字段值
    public final List<byte[]> fieldValues;

    public RowDataPacket(int fieldCount) {
        this.fieldCount = fieldCount;
        this.fieldValues = new ArrayList<byte[]>(fieldCount);
    }

    public void add(byte[] value) {
        fieldValues.add(value);
    }

    @Override
    public int calculatePacketSize() {
        int size = 0;
        for (int i = 0; i < fieldCount; i++) {
            byte[] v = fieldValues.get(i);
            size += (v == null || v.length == 0) ? 1 : MySqlByteBufUtil.getLength(v);
        }
        return size;
    }

    public ByteBuf writeBuffer(ByteBuf byteBuf) {
        MySqlByteBufUtil.write3(byteBuf, this.calculatePacketSize());
        byteBuf.writeByte(this.packetId);
        for (int i = 0; i < fieldCount; i++) {
            byte[] fv = fieldValues.get(i);
            if (fv == null || fv.length == 0) {
                byteBuf.writeByte(RowDataPacket.NULL_MARK);
            } else {
                MySqlByteBufUtil.writeLength(byteBuf, fv.length);
                byteBuf.writeBytes(fv);
            }
        }
        return byteBuf;
    }


    @Override
    public void read(BinaryPacket bin) {
        this.packetLength = bin.packetLength;
        this.packetId = bin.packetId;
        PacketReader reader = new PacketReader(bin.body);
        for (int i = 0; i < fieldCount; i++) {
            fieldValues.add(reader.readBytesWithLength());
        }
    }
}
