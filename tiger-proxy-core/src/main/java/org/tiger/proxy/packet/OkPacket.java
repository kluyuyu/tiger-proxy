package org.tiger.proxy.packet;

import io.netty.buffer.ByteBuf;
import org.tiger.proxy.utils.MySqlByteBufUtil;

/**
 * <pre>
 * Bytes                       Name
 * -------------------------------------------------------------
 * 1                           field_count, always = 0
 * 1-9 (Length Coded Binary)   affected_rows
 * 1-9 (Length Coded Binary)   insert_id
 * 2                           server_status
 * 2                           warning_count
 * n   (until end of packet)   message fix:(Length Coded String)
 * </pre>
 *
 * @author fish at 2016/5/31 20:09
 */
public class OkPacket extends MysqlPacket {


    public static final byte FIELD_COUNT = 0x00;

    public byte fieldCount = FIELD_COUNT;

    public long affectedRows;

    public long insertId;

    public int serverStatus;

    public int warningCount;

    public byte[] message;


    public void read(BinaryPacket bin) {
        packetLength = bin.packetLength;
        packetId = bin.packetId;
        PacketReader reader = new PacketReader(bin.body);
        fieldCount = reader.read1();
        affectedRows = reader.readLength();
        insertId = reader.readLength();
        serverStatus = reader.read2();
        warningCount = reader.read2();
        if (reader.hasRemaining()) {
            this.message = reader.readBytesWithLength();
        }
    }

    public ByteBuf writeBuffer(ByteBuf buffer) {
        MySqlByteBufUtil.write3(buffer, this.calculatePacketSize());
        buffer.writeByte(this.packetId);
        buffer.writeByte(fieldCount);
        MySqlByteBufUtil.writeLength(buffer, affectedRows);
        MySqlByteBufUtil.writeLength(buffer, insertId);
        MySqlByteBufUtil.write2(buffer, serverStatus);
        MySqlByteBufUtil.write2(buffer, warningCount);
        if (message != null) {
            MySqlByteBufUtil.writeWithLength(buffer, message);
        }
        return buffer;
    }


    @Override
    public int calculatePacketSize() {
        int i = 1;
        i += MySqlByteBufUtil.getLength(affectedRows);
        i += MySqlByteBufUtil.getLength(insertId);
        i += 4;
        if (message != null) {
            i += MySqlByteBufUtil.getLength(message);
        }
        return i;
    }


    public static byte[] getOk(int packetId) {
        byte[] ok = new byte[11];
        ok[0] = 7;
        ok[3] = (byte) packetId;
        ok[7] = 2;
        return ok;
    }


}
