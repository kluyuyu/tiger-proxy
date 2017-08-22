package org.tiger.proxy.packet;

import io.netty.buffer.ByteBuf;
import org.tiger.proxy.utils.MySqlByteBufUtil;

/**
 * Created by liufish on 16/7/24.
 */
public class EOFPacket extends MysqlPacket {

    public static final byte FIELD_COUNT = (byte) 0xfe;

    public byte fieldCount = FIELD_COUNT;
    public int warningCount;
    public int status = 2;

    @Override
    public int calculatePacketSize() {
        // 1+2+2;
        return 5;
    }


    public ByteBuf writeBuffer(ByteBuf byteBuf) {
        int size = this.calculatePacketSize();
        MySqlByteBufUtil.write3(byteBuf, size);
        byteBuf.writeByte(this.packetId);
        byteBuf.writeByte(this.fieldCount);
        MySqlByteBufUtil.write2(byteBuf, this.warningCount);
        MySqlByteBufUtil.write2(byteBuf, this.status);
        return byteBuf;
    }

    @Override
    public void read(BinaryPacket bin) {
        this.packetLength = bin.packetLength;
        this.packetId = bin.packetId;
        PacketReader reader = new PacketReader(bin.body);
        this.fieldCount = reader.read1();
        this.warningCount = reader.read2();
        this.status = reader.read2();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getWarningCount() {
        return warningCount;
    }

    public void setWarningCount(int warningCount) {
        this.warningCount = warningCount;
    }

    public byte getFieldCount() {
        return fieldCount;
    }

    public void setFieldCount(byte fieldCount) {
        this.fieldCount = fieldCount;
    }
}
