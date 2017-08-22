package org.tiger.proxy.packet;

import io.netty.buffer.ByteBuf;
import org.tiger.proxy.utils.MySqlByteBufUtil;

/**
 * <pre>
 * Bytes                       Name
 * -------------------------------------------------------
 * 1                           field_count, always = 0xff
 * 2                           errorNo
 * 1                           (sqlstate marker), always '#'
 * 5                           sqlstate (5 characters)
 * n                           message
 * </pre>
 *
 * @author fish at 2016/6/2 13:43
 */
public class ErrorPacket extends MysqlPacket {

    public byte fieldCount = (byte) 0xff;

    public int errNo;

    public byte mark = (byte) '#';

    public byte[] sqlState = "HY000".getBytes();

    public byte[] message;

    @Override
    public void read(BinaryPacket bin) {
        this.packetLength = bin.packetLength;
        this.packetId = bin.packetId;

        PacketReader reader = new PacketReader(bin.body);

        this.fieldCount = reader.read1();
        this.errNo = reader.read2();
        if (reader.hasRemaining() && (reader.read(reader.getPosition()) == (byte) '#')) {
            reader.read1();
            sqlState = reader.readBytes(5);
        }
        message = reader.readBytes();
    }

    public ByteBuf writeBuffer(ByteBuf buffer) {
        MySqlByteBufUtil.write3(buffer, calculatePacketSize());
        buffer.writeByte(packetId);
        buffer.writeByte(fieldCount);
        MySqlByteBufUtil.write2(buffer, errNo);
        buffer.writeByte(mark);
        buffer.writeBytes(sqlState);
        if (message != null) {
            buffer.writeBytes(message);
        }
        return buffer;
    }


    @Override
    public int calculatePacketSize() {
        // 1 + 2 + 1 + 5
        int size = 9;
        if (message != null) {
            size += message.length;
        }
        return size;
    }
}