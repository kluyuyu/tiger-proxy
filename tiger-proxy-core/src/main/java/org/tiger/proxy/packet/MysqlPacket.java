package org.tiger.proxy.packet;


import io.netty.buffer.ByteBuf;

/**
 * @author fish at 2016/5/31 12:43
 */
public abstract class MysqlPacket {

    public int packetLength;

    public int packetId;

    public abstract int calculatePacketSize();

    public abstract ByteBuf writeBuffer(ByteBuf buffer);

    public abstract void  read(BinaryPacket bin );
}
