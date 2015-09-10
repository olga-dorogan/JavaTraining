package com.custom;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by olga on 10.12.14.
 */
public class MainEntry {
    public static void main(String[] args) {
        getInfoAboutDataStreamStringUTFMetadata();
    }

    public static void getInfoAboutDataStreamStringUTFMetadata() {
        String testDataOnlyASCII = "abc";
        String testDataWithRussian = "абв";
        if (testDataOnlyASCII.length() != testDataWithRussian.length()) {
            System.out.println("Internal error in getInfoAboutDataStreamStringUTFMetadata() method: wrong test data.");
            return;
        }

        // 1. get length of metadata in bytes
        int metadataLength;
        try {
            metadataLength = getMetadataLengthInBytes();
        } catch (IOException e) {
            System.out.println("Internal IO error in getMetadataLengthInByte() method.");
            return;
        }

        // 2. get metadata values for tested strings
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream outputStream = new DataOutputStream(byteArrayOutputStream);
        byte[] onlyASCIIAsArrayFromStream;
        byte[] withRussianAsArrayFromStream;

        try {
            outputStream.writeUTF(testDataOnlyASCII);
            onlyASCIIAsArrayFromStream = byteArrayOutputStream.toByteArray();

            byteArrayOutputStream.reset();
            outputStream.writeUTF(testDataWithRussian);
            withRussianAsArrayFromStream = byteArrayOutputStream.toByteArray();

        } catch (IOException e) {
            System.out.println("Internal IO error in getInfoAboutDataStreamStringUTFMetadata() method.");
            return;
        }

        long metadataForStringWithOnlyASCII = 0;
        long metadataForStringWithRussian = 0;

        for (int i = 0; i < metadataLength; i++) {
            if (i != 0) {
                metadataForStringWithOnlyASCII <<= 8;
                metadataForStringWithRussian <<= 8;
            }
            metadataForStringWithOnlyASCII += onlyASCIIAsArrayFromStream[i];
            metadataForStringWithRussian += withRussianAsArrayFromStream[i];
        }

        // 3. compare metadata values
        System.out.print("DataOutputStream.writeUTF() method: ");
        if (metadataForStringWithOnlyASCII == metadataForStringWithRussian)
            System.out.println("metadata is the length of the string in symbols.");
        else
            System.out.println("metadata is the length of the string in bytes.");

    }

    private static int getMetadataLengthInBytes() throws IOException {
        String testDataFirst = "a";
        String testDataSecond = "b";

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream outputStream = new DataOutputStream(byteArrayOutputStream);

        outputStream.writeUTF(testDataFirst);
        byte[] firstStringAsArrayFromStream = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.reset();
        outputStream.writeUTF(testDataSecond);
        byte[] secondStringAsArrayFromStream = byteArrayOutputStream.toByteArray();

        int i;
        for (i = 0; i < firstStringAsArrayFromStream.length; i++) {
            if (firstStringAsArrayFromStream[i] != secondStringAsArrayFromStream[i])
                break;
        }
        return i;
    }
}
