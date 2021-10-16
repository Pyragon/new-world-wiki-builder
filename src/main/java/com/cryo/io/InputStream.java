package com.cryo.io;

public final class InputStream extends Stream {

	public void initBitAccess() {
		bitPosition = offset * 8;
	}

	private static final int[] BIT_MASK = new int[] { 0, 1, 3, 7, 15, 31, 63, 127, 255, 511, 1023, 2047, 4095, 8191, 16383, 32767, 65535, 
			131071, 262143, 524287, 1048575, 2097151, 4194303, 8388607, 16777215, 33554431, 67108863, 134217727, 268435455,
			536870911, 1073741823, 2147483647, -1 };

	public void finishBitAccess() {
		offset = (7 + bitPosition) / 8;
	}

	public int readBits(int bitOffset) {
		int bytePos = bitPosition >> 1779819011;
		int i_8_ = -(0x7 & bitPosition) + 8;
		bitPosition += bitOffset;
		int value = 0;
		for (/**/; (bitOffset ^ 0xffffffff) < (i_8_ ^ 0xffffffff); i_8_ = 8) {
			value += (BIT_MASK[i_8_] & buffer[bytePos++]) << -i_8_ + bitOffset;
			bitOffset -= i_8_;
		}
		if ((i_8_ ^ 0xffffffff) == (bitOffset ^ 0xffffffff))
			value += buffer[bytePos] & BIT_MASK[i_8_];
		else
			value += (buffer[bytePos] >> -bitOffset + i_8_ & BIT_MASK[bitOffset]);
		return value;
	}

	public InputStream(int capacity) {
		buffer = new byte[capacity];
	}

	public InputStream(byte[] buffer) {
		this.buffer = buffer;
		this.length = buffer.length;
	}

	public void checkCapacity(int length) {
		if (offset + length >= buffer.length) {
			byte[] newBuffer = new byte[(offset + length) * 2];
			System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);
			buffer = newBuffer;
		}
	}

	public int read24BitInt() {
		return (readUnsignedByte() << 16) + (readUnsignedByte() << 8) + (readUnsignedByte());
	}

	public void skip(int length) {
		offset += length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public void addBytes(byte[] b, int offset, int length) {
		checkCapacity(length - offset);
		System.arraycopy(b, offset, buffer, this.offset, length);
		this.length += length - offset;
	}
	
	public int read24BitUnsignedInteger() {
		this.offset += 3;
		return ((buffer[offset - 3] & 0xff) << 16) + (buffer[offset - 1] & 0xff) + ((buffer[offset - 2] & 0xff) << 8);
	}

	public int readByte(int offset) {
		this.offset = offset;
		return getRemaining() > 0 ? buffer[offset++] : 0;
	}

	public int readByte() {
		return getRemaining() > 0 ? buffer[offset++] : 0;
	}

	public void readBytes(byte buffer[], int off, int len) {
		for (int k = off; k < len + off; k++) {
			buffer[k] = (byte) readByte();
		}
	}

	public void readBytes(byte buffer[]) {
		readBytes(buffer, 0, buffer.length);
	}

	public int readSmart2() {
		int i = 0;
		int i_33_ = readUnsignedSmart();
		while (i_33_ == 32767) {
			i_33_ = readUnsignedSmart();
			i += 32767;
		}
		i += i_33_;
		return i;
	}

	public int readUnsignedByte() {
		return readByte() & 0xff;
	}

	public int readByte128() {
		return (byte) (readByte() - 128);
	}

	public int readByteC() {
		return (byte) -readByte();
	}

	public int read128Byte() {
		return (byte) (128 - readByte());
	}

	public int readUnsignedByte128() {
		return readUnsignedByte() - 128 & 0xff;
	}

	public int readUnsignedByteC() {
		return -readUnsignedByte() & 0xff;
	}

	public int readUnsigned128Byte() {
		return 128 - readUnsignedByte() & 0xff;
	}

	public int readShortLE() {
		int i = readUnsignedByte() + (readUnsignedByte() << 8);
		if (i > 32767) {
			i -= 0x10000;
		}
		return i;
	}

	public int readShort128() {
		int i = (readUnsignedByte() << 8) + (readByte() - 128 & 0xff);
		if (i > 32767) {
			i -= 0x10000;
		}
		return i;
	}

	public int readShortLE128() {
		int i = (readByte() - 128 & 0xff) + (readUnsignedByte() << 8);
		if (i > 32767) {
			i -= 0x10000;
		}
		return i;
	}

	public int read128ShortLE() {
		int i = (128 - readByte() & 0xff) + (readUnsignedByte() << 8);
		if (i > 32767) {
			i -= 0x10000;
		}
		return i;
	}

	public int readShort() {
		int i = (readUnsignedByte() << 8) + readUnsignedByte();
		if (i > 32767) {
			i -= 65536;
		}
		return i;
	}

	public int readUnsignedShortLE() {
		return readUnsignedByte() + (readUnsignedByte() << 8);
	}

	public int readUnsignedShort() {
		return (readUnsignedByte() << 8) + readUnsignedByte();
	}

	// @SuppressWarnings("unused")
	public int readBigSmart() {
		/*
		 * if(Settings.CLIENT_BUILD < 670) return readUnsignedShort();
		 */
		if ((buffer[offset] ^ 0xffffffff) <= -1) {
			int value = readUnsignedShort();
			if (value == 32767) {
				return -1;
			}
			return value;
		}
		return readInt() & 0x7fffffff;
	}

	public int readUnsignedShort128() {
		return (readUnsignedByte() << 8) + (readByte() - 128 & 0xff);
	}

	public int readUnsignedShortLE128() {
		return (readByte() - 128 & 0xff) + (readUnsignedByte() << 8);
	}

	public int readInt() {
		return (readUnsignedByte() << 24) + (readUnsignedByte() << 16) + (readUnsignedByte() << 8) + readUnsignedByte();
	}
	
	public int readTriByte() {
		return (readUnsignedByte() << 16) + (readUnsignedByte() << 8) + readUnsignedByte();
	}

	public int readIntV1() {
		return (readUnsignedByte() << 8) + readUnsignedByte() + (readUnsignedByte() << 24) + (readUnsignedByte() << 16);
	}

	public int readIntV2() {
		return (readUnsignedByte() << 16) + (readUnsignedByte() << 24) + readUnsignedByte() + (readUnsignedByte() << 8);
	}

	public int readIntLE() {
		return readUnsignedByte() + (readUnsignedByte() << 8) + (readUnsignedByte() << 16) + (readUnsignedByte() << 24);
	}

	public int readIntLE(byte offset) {
		this.offset = offset;
		return readUnsignedByte() + (readUnsignedByte() << 8) + (readUnsignedByte() << 16) + (readUnsignedByte() << 24);
	}

	public int readIntLE(int offset) {
		this.offset = offset;
		return readUnsignedByte() + (readUnsignedByte() << 8) + (readUnsignedByte() << 16) + (readUnsignedByte() << 24);
	}

	public long readLong() {
		long l = readInt() & 0xffffffffL;
		long l1 = readInt() & 0xffffffffL;
		return (l << 32) + l1;
	}
	
	public String readNoSpecString() {
		StringBuilder s = new StringBuilder();
		int b;
		while ((b = readByte()) != 0)
			s.append((char) b);
		return s.toString();
	}

	public String readJagString() {
		readByte();
		String s = "";
		int b;
		while ((b = readByte()) != 0) {
			s += (char) b;
		}
		return s;
	}
	
	public final int readSignedSmart() {
		int v = this.buffer[this.offset] & 0xff;
		if (v < 128)
			return readUnsignedByte() - 64;
		return readUnsignedShort() - 49152;
	}

	public int readUnsignedSmart() {
		int i = buffer[offset] & 0xff;
		if (i >= 128) {
			return readUnsignedShort() - 32768;
		}
		return readUnsignedByte();
	}
	
	public int readUnsignedSmartNegOne() {
		int i = buffer[offset] & 0xff;
		if (i >= 128) {
			return readUnsignedShort() - 32769;
		}
		return readUnsignedByte() - 1;
	}

	public long readSized(int size) {
		--size;
        if (size >= 0 && size <= 7) {
            int bitSize = size * 8;
            long val;
            for (val = 0L; bitSize >= 0; bitSize -= 8) {
                val |= ((long) this.buffer[++this.offset - 1] & 0xffL) << bitSize;
            }
            return val;
        } else {
            throw new IllegalArgumentException();
        }
	}

	public float readFloat() {
		return Float.intBitsToFloat(readInt());
	}

	public float readFloatLE() {
		return Float.intBitsToFloat(readIntLE());
	}
}