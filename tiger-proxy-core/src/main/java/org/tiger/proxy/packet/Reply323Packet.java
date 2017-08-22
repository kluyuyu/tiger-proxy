package org.tiger.proxy.packet;



import io.netty.buffer.ByteBuf;
import org.tiger.proxy.utils.BioStreamUtil;
import org.tiger.proxy.utils.MySqlByteBufUtil;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 如果有seed，多加一个结束符
 * Created by fish on 2016/6/5.
 */
public class Reply323Packet extends MysqlPacket {

    public byte[] seed;

    @Override
    public int calculatePacketSize() {
        return seed == null ? 1 : seed.length + 1;
    }

    @Override
    public ByteBuf writeBuffer(ByteBuf buffer) {
        MySqlByteBufUtil.write3(buffer, calculatePacketSize());
        buffer.writeByte(packetId);
        if (seed == null) {
            buffer.writeByte((byte) 0);
        } else {
            MySqlByteBufUtil.writeWithNull(buffer, seed);
        }
        return buffer;
    }

    public void write(OutputStream out) throws IOException {
        BioStreamUtil.write3(out, calculatePacketSize());
        BioStreamUtil.write(out, (byte) packetId);
        if (seed == null) {
            BioStreamUtil.write(out, (byte) 0);
        } else {
            BioStreamUtil.writeWithNull(out, seed);
        }
        out.flush();
    }

    @Override
    public void read(BinaryPacket bin) {
        this.packetId = bin.packetId + 1;
    }

}