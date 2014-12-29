package com.custom.utility.png.chunk;

import com.custom.utility.png.chunk.critical.ChunkCriticalUtility;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by olga on 11.12.14.
 */
public class Chunk {
    private int length;
    private byte[] typeCode = new byte[4];
    private byte[] data;
    private int crc32;

    public Chunk(DataInputStream inputStream) throws IOException {
        length = inputStream.readInt();
        int read = inputStream.read(typeCode);
        if (read != typeCode.length)
            throw new IOException();
        data = new byte[length];
        read = inputStream.read(data);
        if (read != data.length)
            throw new IOException();
        crc32 = inputStream.readInt();
    }

    public boolean isCritical() {
        if ((typeCode[0] >= 'A') && (typeCode[0] <= 'Z'))
            return true;
        return false;
    }

    public boolean isLast(){
        return ChunkCriticalUtility.isLast(typeCode);
    }

    public boolean isPublic() {
        if ((typeCode[1] >= 'A') && (typeCode[1] <= 'Z'))
            return true;
        return false;
    }
    public void printInfo(){
        if(isCritical())
            ChunkCriticalUtility.printInfo(typeCode, data);

    }
    public boolean checkCRC32() {
        // TODO: realize crc32 checking
        return true;
    }
}
