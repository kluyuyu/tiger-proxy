package org.tiger.proxy.packet;

import io.netty.buffer.ByteBuf;
import org.tiger.proxy.utils.BioStreamUtil;
import org.tiger.proxy.utils.MySqlByteBufUtil;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by liufish on 16/7/14.
 */
public class BinaryPacket {

    /**
     * body长度
     */
    public int packetLength;
    /**
     * 包序列
     */
    public int packetId;

    /**
     * 内容存储
     */
    public byte[] body;

    public  BinaryPacket(int packetLength,int packetId,byte[] body){
        this.packetLength = packetLength;
        this.packetId = packetId;
        this.body = body;
    }

    public  BinaryPacket(InputStream inputStream) throws IOException{
        this.packetLength = BioStreamUtil.read3(inputStream);
        this.packetId = BioStreamUtil.read(inputStream);
        byte [] data = new byte[packetLength];
        BioStreamUtil.read(inputStream,data,0,data.length);
        this.body = data;
    }

    public ByteBuf writeBuffer(ByteBuf buffer){
        MySqlByteBufUtil.write3(buffer, packetLength);
        buffer.writeByte(packetId);
        buffer.writeBytes(body);
        return buffer;
    }

}
