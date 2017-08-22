package org.tiger.proxy.packet;


import io.netty.buffer.ByteBuf;
import org.tiger.proxy.constant.CapabilitiesType;
import org.tiger.proxy.utils.BioStreamUtil;
import org.tiger.proxy.utils.MySqlByteBufUtil;

import java.io.IOException;
import java.io.OutputStream;

/**
* <pre>
 * Bytes                        Name
 * -------------------------------------------------------
 * 4                            client_flags
 * 4                            max_packet_size
 * 1                            charset_number
 * 23                           (filler) always 0x00...
 * n (Null-Terminated String)   user
 * n (Length Coded Binary)      scramble_buff (1 + x bytes)
 * n (Null-Terminated String)   databaseName (optional)
 *
 * </pre>
 * Created by liufish on 16/7/15.
 */
public class AuthPacket extends MysqlPacket {


    private static final byte[] FILLER = new byte[23];

    public long clientFlags;

    public long maxPacketSize;

    public int charsetIndex;

    public byte[] extra;

    public String user;

    public byte[] password;

    public String database;


    public void read(BinaryPacket bin) {
        this.packetLength = bin.packetLength;
        this.packetId = bin.packetId;

        PacketReader reader = new PacketReader(bin.body);

        clientFlags = reader.read4();
        maxPacketSize = reader.read4();
        charsetIndex = (reader.read1() & 0xff);
        int current = reader.getPosition();
        int len = (int) reader.readLength();
        if (len > 0 && len < FILLER.length) {
            byte[] ab = new byte[len];
            System.arraycopy(bin.body, reader.getPosition(), ab, 0, len);
            this.extra = ab;
        }
        reader.setPosition(current + FILLER.length);
        user = reader.readStringWithNull();
        password = reader.readBytesWithLength();
        if (((clientFlags & CapabilitiesType.CLIENT_CONNECT_WITH_DB) != 0) && reader.hasRemaining()) {
            database = reader.readStringWithNull();
        }
    }


    public ByteBuf writeBuffer(ByteBuf buffer) {
        MySqlByteBufUtil.write3(buffer, this.calculatePacketSize());
        buffer.writeByte(this.packetId);
        MySqlByteBufUtil.write4(buffer, clientFlags);
        MySqlByteBufUtil.write4(buffer, maxPacketSize);
        buffer.writeByte((byte) charsetIndex);
        buffer.writeBytes(FILLER);
        if (user == null) {
            buffer.writeByte((byte) 0);
        } else {
            byte[] userData = user.getBytes();
            MySqlByteBufUtil.writeWithNull(buffer, userData);
        }
        if (password == null) {
            buffer.writeByte((byte) 0);
        } else {
            MySqlByteBufUtil.writeWithLength(buffer, password);
        }
        if (database == null) {
            buffer.writeByte((byte) 0);
        } else {
            byte[] databaseData = database.getBytes();
            MySqlByteBufUtil.writeWithNull(buffer, databaseData);
        }
        return buffer;
    }



    public void write(OutputStream out) throws IOException {
        BioStreamUtil.write3(out, calculatePacketSize());
        BioStreamUtil.write(out, (byte) packetId);
        BioStreamUtil.write4(out, clientFlags);
        BioStreamUtil.write4(out, maxPacketSize);
        BioStreamUtil.write(out, (byte) charsetIndex);
        out.write(FILLER);
        if (user == null) {
            BioStreamUtil.write(out, (byte) 0);
        } else {
            BioStreamUtil.writeWithNull(out, user.getBytes());
        }
        if (password == null) {
            BioStreamUtil.write(out, (byte) 0);
        } else {
            BioStreamUtil.writeWithLength(out, password);
        }
        if (database == null) {
            BioStreamUtil.write(out, (byte) 0);
        } else {
            BioStreamUtil.writeWithNull(out, database.getBytes());
        }
        out.flush();
    }




    @Override
    public int calculatePacketSize() {
        // 4+4+1+23;
        int size = 32;
        size += (user == null) ? 1 : user.length() + 1;
        size += (password == null) ? 1 : MySqlByteBufUtil.getLength(password);
        size += (database == null) ? 1 : database.length() + 1;
        return size;
    }
}
