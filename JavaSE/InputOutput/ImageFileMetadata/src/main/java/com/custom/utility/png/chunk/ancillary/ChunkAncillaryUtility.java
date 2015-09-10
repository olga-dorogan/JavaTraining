package com.custom.utility.png.chunk.ancillary;


/**
 * Created by olga on 16.12.14.
 */
public class ChunkAncillaryUtility {
    private static enum ChunkType {bKGD, pHYs, sPLT, iTXt, tEXt}

    ;
    private static final byte[][] supportedChunks = {
            {'b', 'K', 'G', 'D'}, {'p', 'H', 'Y', 's'},
            {'s', 'P', 'L', 'T'}, {'i', 'T', 'X', 't'},
            {'t', 'E', 'X', 't'}};

    public static void printInfo(byte[] typeCode, byte[] data) {
        switch (getTypeCode(typeCode)) {
            case bKGD:
                ChunkbKGD.printInfo(data);
                break;
            case pHYs:
                ChunkpHYs.printInfo(data);
                break;
            case iTXt:
                ChunkiTxt.printInfo(data);
                break;
            case tEXt:
                ChunktEXt.printInfo(data);
                break;
            default:
                System.out.println("Unsupported ancillary chunk");
                break;
        }
    }

    private static ChunkType getTypeCode(byte[] typeCode) {
        if (typeCode.length != 4)
            return null;
        for (int i = 0; i < supportedChunks.length; i++) {
            int j;
            for (j = 0; j < supportedChunks[i].length; j++)
                if (typeCode[j] != supportedChunks[i][j])
                    break;
            if (j == supportedChunks[i].length)
                return ChunkType.values()[i];
        }
        return null;
    }
}
