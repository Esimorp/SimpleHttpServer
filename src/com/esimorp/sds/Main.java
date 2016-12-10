package com.esimorp.sds;

import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static class DNSMessage {
        private byte[] ID;
        private String QR;
        private String opcode;
        private String AA;
        private String TC;
        private String RD;
        private String RA;
        private final String ZERO = "000";
        private String RCODE;
        private byte[] QCount;
        private byte[] RRCount;
        private byte[] ARCount;
        private byte[] ERCount;
        private byte[] header;
        private DNSQuestion question;
        private DNSResourceRecord resourceRecord;

        public DNSMessage(byte[] bytes) {
            byte[] header = Arrays.copyOfRange(bytes, 0, 12);
            System.out.print(header.length);
            initHeader(header);
            initQuestion(Arrays.copyOfRange(bytes, 12, bytes.length - 1));
        }

        public DNSMessage(String hexIp, byte[] header, DNSQuestion question) {
            this.header = header;
            initHeader(header);
            this.QR = "1";
            this.RA = "1";
            this.header = headerToBytes();
            this.question = question;
            this.resourceRecord = new DNSResourceRecord(question.getQueryNameBytes(), question.getQueryType(), question.getQueryClass(), hexIp);
        }

        private byte[] headerToBytes() {
            List<Byte> bytes = new ArrayList<>();
            bytes.addAll(Arrays.asList(toObjects(ID)));
            bytes.add((byte) Integer.parseInt(QR + opcode + AA + TC + RD, 2));
            bytes.add((byte) Integer.parseInt(RA + ZERO + RCODE, 2));
            bytes.addAll((Arrays.asList(toObjects(ByteBuffer.allocate(2).putShort((short) 0).array()))));
            bytes.addAll((Arrays.asList(toObjects(ByteBuffer.allocate(2).putShort((short) 1).array()))));
            bytes.addAll((Arrays.asList(toObjects(ByteBuffer.allocate(2).putShort((short) 0).array()))));
            bytes.addAll((Arrays.asList(toObjects(ByteBuffer.allocate(2).putShort((short) 0).array()))));

            Byte[] upperBytes = bytes.toArray(new Byte[bytes.size()]);
            return ArrayUtils.toPrimitive(upperBytes);
        }

        private void initQuestion(byte[] bytes) {
            this.question = new DNSQuestion(bytes);
        }

        private void initHeader(byte[] header) {
            this.header = header;
            this.ID = Arrays.copyOfRange(header, 0, 2);
            byte[] tag = Arrays.copyOfRange(header, 2, 4);
            initTag(tag);
            this.QCount = Arrays.copyOfRange(header, 4, 6);
            this.RRCount = Arrays.copyOfRange(header, 6, 8);
            this.ARCount = Arrays.copyOfRange(header, 8, 10);
            this.ERCount = Arrays.copyOfRange(header, 10, 12);
        }

        private void initTag(byte[] tag) {
            String bits = byteToBit(tag[0]) + byteToBit(tag[1]);
            this.QR = bits.substring(0, 1);
            this.opcode = bits.substring(1, 5);
            this.AA = bits.substring(5, 6);
            this.TC = bits.substring(6, 7);
            this.RD = bits.substring(7, 8);
            this.RA = bits.substring(8, 9);
            this.RCODE = bits.substring(12, 16);
        }

        public byte[] getHeader() {
            return header;
        }

        public DNSQuestion getQuestion() {
            return question;
        }

        public byte[] toBytes() {
            List<Byte> bytes = new ArrayList<>();

            bytes.addAll(Arrays.asList(toObjects(header)));
            if (resourceRecord != null) {
                bytes.addAll(Arrays.asList(toObjects(resourceRecord.toBytes())));
            }

            Byte[] upperBytes = bytes.toArray(new Byte[bytes.size()]);
            return ArrayUtils.toPrimitive(upperBytes);
        }

    }

    public static class DNSQuestion {
        private String queryName;
        private byte[] queryNameBytes;
        private byte[] queryType;
        private byte[] queryClass;

        public DNSQuestion(byte[] bytes) {
            int nowIndex = 0;
            int length = byteHexToInteger(bytes[nowIndex]);
            nowIndex++;
            StringBuilder queryName = new StringBuilder();

            while (length != 0) {
                String str = new String(Arrays.copyOfRange(bytes, nowIndex, nowIndex + length));
                queryName.append(str);
                nowIndex += length;
                length = byteHexToInteger(bytes[nowIndex]);
                nowIndex++;
                if (length != 0) {
                    queryName.append(".");
                }
            }
            this.queryNameBytes = Arrays.copyOfRange(bytes, 0, nowIndex);

            this.queryName = queryName.toString();
            this.queryType = Arrays.copyOfRange(bytes, nowIndex, nowIndex + 2);
            nowIndex += 2;
            this.queryClass = Arrays.copyOfRange(bytes, nowIndex, nowIndex + 2);
        }

        public byte[] getQueryNameBytes() {
            return queryNameBytes;
        }

        public byte[] getQueryType() {
            return queryType;
        }

        public byte[] getQueryClass() {
            return queryClass;
        }

        public String getQueryName() {
            return queryName;
        }

        public byte[] toBytes() {
            List<Byte> bytes = new ArrayList<>();

            bytes.addAll(Arrays.asList(toObjects(queryNameBytes)));
            bytes.addAll(Arrays.asList(toObjects(queryType)));
            bytes.addAll(Arrays.asList(toObjects(queryClass)));

            Byte[] upperBytes = bytes.toArray(new Byte[bytes.size()]);
            return ArrayUtils.toPrimitive(upperBytes);
        }
    }

    public static class DNSResourceRecord {
        private byte[] queryName;
        private byte[] queryType;
        private byte[] queryClass;
        private byte[] TTL;
        private byte[] rrLength;
        private byte[] rrData;

        public DNSResourceRecord(byte[] queryName, byte[] queryType, byte[] queryClass, String hexIp) {
            this.queryClass = queryClass;
            this.queryName = queryName;
            this.queryType = queryType;
            this.TTL = ByteBuffer.allocate(4).putInt(1024).array();
            this.rrLength = ByteBuffer.allocate(2).putShort((short) 4).array();
            this.rrData = hexStringToBytes(hexIp);

        }

        public byte[] toBytes() {
            List<Byte> bytes = new ArrayList<>();

            bytes.addAll(Arrays.asList(toObjects(queryName)));
            bytes.addAll(Arrays.asList(toObjects(queryType)));
            bytes.addAll(Arrays.asList(toObjects(queryClass)));
            bytes.addAll(Arrays.asList(toObjects(TTL)));
            bytes.addAll(Arrays.asList(toObjects(rrLength)));
            bytes.addAll(Arrays.asList(toObjects(rrData)));

            Byte[] upperBytes = bytes.toArray(new Byte[bytes.size()]);
            return ArrayUtils.toPrimitive(upperBytes);
        }
    }

    public static void main(String args[]) throws IOException {
        byte[] buf = new byte[512];
        DatagramSocket socket = new DatagramSocket(5533);
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        while (true) {
            socket.receive(packet);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    DNSMessage message = new DNSMessage(packet.getData());
                    String queryName = message.getQuestion().getQueryName();
                    System.out.println("Ask for:" + queryName);
                    DNSMessage response = null;
                    if (queryName.equals("www.1024.com")) {
                        response = new DNSMessage("7f000001", message.getHeader(), message.getQuestion());
                    } else {
                        response = new DNSMessage("01010101", message.getHeader(), message.getQuestion());
                    }
                    byte[] responseBytes = response.toBytes();
                    DatagramPacket responsePacket = new DatagramPacket(responseBytes, responseBytes.length, packet.getAddress(), packet.getPort());
                    try {
                        socket.send(responsePacket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).run();
        }
    }

    static Byte[] toObjects(byte[] bytesPrim) {
        Byte[] bytes = new Byte[bytesPrim.length];

        int i = 0;
        for (byte b : bytesPrim) bytes[i++] = b; // Autoboxing

        return bytes;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));

        }
        return d;
    }


    public static <T> T[] concatAll(T[] first, T[]... rest) {
        int totalLength = first.length;
        for (T[] array : rest) {
            totalLength += array.length;
        }
        T[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (T[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static int byteHexToInteger(byte byteHex) {
        return Integer.valueOf(String.format("%02X", byteHex), 16);
    }

    public static String byteToBit(byte b) {
        return "" + (byte) ((b >> 7) & 0x1) +
                (byte) ((b >> 6) & 0x1) +
                (byte) ((b >> 5) & 0x1) +
                (byte) ((b >> 4) & 0x1) +
                (byte) ((b >> 3) & 0x1) +
                (byte) ((b >> 2) & 0x1) +
                (byte) ((b >> 1) & 0x1) +
                (byte) ((b >> 0) & 0x1);
    }
}
