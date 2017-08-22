package org.tiger.proxy.packet;

import io.netty.buffer.ByteBuf;
import org.tiger.proxy.utils.MySqlByteBufUtil;

/**
 * mysql 握手数据包
 * <pre>
 *
 * Bytes                        Name
 * ---------------------------------------------------------
 * 1                            protocol_version
 * n (Null-Terminated String)   server_version
 * 4                            thread_id
 * 8                            scramble_buff
 * 1                            (filler) always 0x00
 * 2                            server_capabilities
 * 1                            server_language
 * 2                            server_status
 * 13                           (filler) always 0x00 ...
 * 13                           rest of scramble_buff (4.1)
 * </pre>
 *
 * @author fish at 2016/5/31 12:32
 */
public class HandshakePacket extends MysqlPacket {

    private static final byte[] FILLER_13 = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    public byte protocolVersion;

    public byte[] serverVersion;

    public long threadId;

    public byte[] seed;

    public int serverCapabilities;

    public byte serverCharsetIndex;

    public int serverStatus;

    public byte[] restOfScrambleBuff;


    public void read(BinaryPacket bin) {
        this.packetLength = bin.packetLength;
        this.packetId = bin.packetId;

        PacketReader reader = new PacketReader(bin.body);

        protocolVersion = reader.read1();
        serverVersion = reader.readBytesWithNull();
        threadId = reader.read4();
        seed = reader.readBytesWithNull();
        serverCapabilities = reader.read2();
        serverCharsetIndex = reader.read1();
        serverStatus = reader.read2();
        reader.readMove(13);
        restOfScrambleBuff = reader.readBytesWithNull();
    }

    public ByteBuf writeBuffer(ByteBuf buffer) {
        MySqlByteBufUtil.write3(buffer, calculatePacketSize());
        buffer.writeByte(packetId);
        buffer.writeByte(protocolVersion);
        MySqlByteBufUtil.writeWithNull(buffer, serverVersion);
        MySqlByteBufUtil.write4(buffer, threadId);
        MySqlByteBufUtil.writeWithNull(buffer, seed);
        MySqlByteBufUtil.write2(buffer, serverCapabilities);
        buffer.writeByte(serverCharsetIndex);
        MySqlByteBufUtil.write2(buffer, serverStatus);
        buffer.writeBytes(FILLER_13);
        MySqlByteBufUtil.writeWithNull(buffer, restOfScrambleBuff);
        return buffer;
    }

    @Override
    public int calculatePacketSize() {
        int size = 1;
        // n
        size += serverVersion.length;
        // 1+4
        size += 5;
        // 8
        size += seed.length;
        // 1+2+1+2+13
        size += 19;
        // 12
        size += restOfScrambleBuff.length;
        // 1
        size += 1;
        return size;

    }

}