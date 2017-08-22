package org.tiger.proxy.packet;

import io.netty.buffer.ByteBuf;
import org.tiger.proxy.utils.MySqlByteBufUtil;


/**
 * From Server To Client, part of Result Set Packets. One for each column in the
 * result set. Thus, if the value of field_columns in the Result Set Header
 * Packet is 3, then the Field Packet occurs 3 times.
 * <p/>
 * 从服务器到客户端,结果集包的一部分。每个列的一个结果集。所以,如果在结果集中field_columns包头值是3,其中就有三次传输。
 * <p/>
 * <pre>
 * Bytes                      Name
 * -----------------------------------
 * n (Length Coded String)    catalog
 * n (Length Coded String)    db
 * n (Length Coded String)    table
 * n (Length Coded String)    org_table
 * n (Length Coded String)    name
 * n (Length Coded String)    org_name
 * 1                          (filler)
 * 2                          charsetNumber
 * 4                          length
 * 1                          type
 * 2                          flags
 * 1                          decimals
 * 2                          (filler), always 0x00
 * n (Length Coded Binary)    default
 *
 * </pre>
 *
 * @author fish
 */
public class FieldPacket extends MysqlPacket {


    public byte[] catalog;
    public byte[] db;
    public byte[] table;
    public byte[] orgTable;
    public byte[] name;
    public byte[] orgName;
    public int charsetIndex;
    public long fieldLength;
    public int type;
    public int flags;
    public byte decimals;
    public byte[] definition;


    @Override
    public int calculatePacketSize() {
        int size = (catalog == null ? 1 : MySqlByteBufUtil.getLength(catalog));
        size += (db == null ? 1 : MySqlByteBufUtil.getLength(db));
        size += (table == null ? 1 : MySqlByteBufUtil.getLength(table));
        size += (orgTable == null ? 1 : MySqlByteBufUtil.getLength(orgTable));
        size += (name == null ? 1 : MySqlByteBufUtil.getLength(name));
        size += (orgName == null ? 1 : MySqlByteBufUtil.getLength(orgName));
        // 1+2+4+1+2+1+2
        size += 13;
        if (definition != null) {
            size += MySqlByteBufUtil.getLength(definition);
        }
        return size;
    }


    public ByteBuf writeBuffer(ByteBuf buffer) {
        int size = calculatePacketSize();
        MySqlByteBufUtil.write3(buffer, size);
        buffer.writeByte(packetId);
        byte nullVal = 0;
        MySqlByteBufUtil.writeWithLength(buffer, catalog, nullVal);
        MySqlByteBufUtil.writeWithLength(buffer, db, nullVal);
        MySqlByteBufUtil.writeWithLength(buffer, table, nullVal);
        MySqlByteBufUtil.writeWithLength(buffer, orgTable, nullVal);
        MySqlByteBufUtil.writeWithLength(buffer, name, nullVal);
        MySqlByteBufUtil.writeWithLength(buffer, orgName, nullVal);
        buffer.writeByte((byte) 0x0C);
        MySqlByteBufUtil.write2(buffer, charsetIndex);
        MySqlByteBufUtil.write4(buffer, fieldLength);
        buffer.writeByte((byte) (type & 0xff));
        MySqlByteBufUtil.write2(buffer, flags);
        buffer.writeByte(decimals);
        buffer.writeByte(nullVal);
        buffer.writeByte(nullVal);
        if (definition != null) {
            MySqlByteBufUtil.writeWithLength(buffer, definition);
        }
       return buffer;
    }


    /**
     * 这里需要测试。
     */
    @Override
    public void read(BinaryPacket bin) {
        this.packetLength = bin.packetLength;
        this.packetId = bin.packetId;
        PacketReader reader = new PacketReader(bin.body);
        this.catalog = reader.readBytesWithLength();
        this.db = reader.readBytesWithLength();
        this.table = reader.readBytesWithLength();
        this.orgTable = reader.readBytesWithLength();
        this.name = reader.readBytesWithLength();
        this.orgName = reader.readBytesWithLength();
        reader.readMove(1);
        this.charsetIndex = reader.read2();
        this.fieldLength = reader.read4();
        this.type = reader.read1() & 0xff;
        this.flags = reader.read2();
        this.decimals = reader.read1();
        reader.readMove(2);
        if (reader.hasRemaining()) {
            this.definition = reader.readBytesWithLength();
        }

    }

}
