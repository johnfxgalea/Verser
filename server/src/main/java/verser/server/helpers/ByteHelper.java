package verser.server.helpers;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ByteHelper {

	public static byte[] shortToBytes(short shortNumber){
		
		byte[] bytes = new byte[2];
		bytes[0] = (byte)(shortNumber & 0xff);
		bytes[1] = (byte)((shortNumber >> 8) & 0xff);
		
		return bytes;
	}
	
	public static short bytesToShort(byte byte1, byte byte2){
		
		ByteBuffer bb = ByteBuffer.allocate(2);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put(byte1);
		bb.put(byte2);
		return bb.getShort(0);
	}
	

}
