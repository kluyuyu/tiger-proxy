package org.tiger.proxy.packet;


import io.netty.buffer.ByteBuf;
import org.tiger.proxy.utils.MySqlByteBufUtil;

/**
 * <pre>
 * (Result Set Header Packet)   the number of columns
 * (Field Packets)              column descriptors
 * (EOF Packet)                 marker: end of Field Packets
 * (Row Data Packets)           row contents
 * (EOF Packet)                 marker: end of Data Packets
 *
 * Bytes                        Name
 * ----------------------------------------
 * 1-9   (Length-Coded-Binary)  field_count
 * 1-9   (Length-Coded-Binary)  extra
 *
 * </pre>
 *
 * @author fish at 2016/6/21 13:42
 */
public class ResultSetHeaderPacket extends MysqlPacket {

    public int fieldCount;

    public long extra;

    @Override
    public int calculatePacketSize() {
        int size = MySqlByteBufUtil.getLength(fieldCount);
        if (extra > 0) {
            size += MySqlByteBufUtil.getLength(extra);
        }
        return size;
    }

    public ByteBuf writeBuffer(ByteBuf byteBuf) {
        int size = this.calculatePacketSize();
        MySqlByteBufUtil.write3(byteBuf, size);
        byteBuf.writeByte(packetId);
        MySqlByteBufUtil.writeLength(byteBuf, fieldCount);
        if (this.extra > 0) {
            MySqlByteBufUtil.writeLength(byteBuf, extra);
        }
        return byteBuf;
    }

    @Override
    public void read(BinaryPacket bin) {
        this.packetLength = bin.packetLength;
        this.packetId = bin.packetId;
        PacketReader reader = new PacketReader(bin.body);
        this.fieldCount = (int) reader.readLength();
        if (reader.hasRemaining()) {
            this.extra = reader.readLength();
        }
    }

}

