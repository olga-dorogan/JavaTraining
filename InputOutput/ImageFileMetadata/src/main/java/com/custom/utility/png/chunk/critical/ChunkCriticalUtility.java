package com.custom.utility.png.chunk.critical;

/**
 * Created by olga on 11.12.14.
 */
public class ChunkCriticalUtility {
    private static enum ChunkType  {IHDR, PLTE, IDAT, IEND};
    private static final byte[][] supportedCriticalChunks =
            {{'I', 'H', 'D', 'R'},
                    {'P', 'L', 'T', 'E'},
                    {'I', 'D', 'A', 'T'},
                    {'I', 'E', 'N', 'D'}};

    public static boolean isSupported(byte[] typeCode) {
        return (getTypeCode(typeCode) != null);
    }

    public static boolean isLast(byte[] typeCode) {
        // last chunk is IEND
        return (getTypeCode(typeCode) == ChunkType.IEND);
    }

    public static void printInfo(byte[] typeCode, byte[] data) {
        switch (getTypeCode(typeCode)) {
            case IHDR:
                ChunkIHDR.printInfo(data);
                break;
            case PLTE:
                ChunkPLTE.printInfo(data);
                break;
            case IDAT:
                ChunckIDAT.printInfo(data);
                break;
            case IEND:
                ChunkIEND.printInfo(data);
                break;
            default:
                System.out.println("Unsupported critical chunk");
                break;
        }
    }

    private static ChunkType getTypeCode(byte[] typeCode) {
        if (typeCode.length != 4)
            return null;
        for (int i = 0; i < supportedCriticalChunks.length; i++) {
            int j;
            for (j = 0; j < supportedCriticalChunks[i].length; j++)
                if (typeCode[j] != supportedCriticalChunks[i][j])
                    break;
            if (j == supportedCriticalChunks[i].length)
                return ChunkType.values()[i];;
        }
        return null;
    }
}
