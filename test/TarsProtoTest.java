import org.gtdev.dchat.network.ProtoDisplayer;
import org.junit.Test;

import com.qq.tars.protocol.tars.*;

import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.*;

/*
  * Test data generated from cpp
  1,BYTE: 1
  2,BYTE: 255
  3,SHORT: 256
  4,SHORT: 257
  5,SHORT: 65407
  6,SHORT: 65406
  7,INT: 65536
  8,INT: 65537
  9,INT: 4294934527
  10,INT: 4294934526
  11,LONG: 4294967296
  12,LONG: 4294967297
  13,LONG: -2147483649
  14,LONG: -2147483650
  15,FLOAT: 1.1
  16,FLOAT: -2.2
  17,DOUBLE: 1.1
  18,DOUBLE: -2.2
  19,STRING1: ABCDEFGHIJKLMNOPQESTUVWXYZabcdefghijklmnopqrestuvwxyz1234567890.
  20,MAP: {12345:67890,abcde:ABCDE}
  21,SIMPLE_LIST: \x30\x31\x32\x33\x34\x35\x36\x37\x38\x39
  22,LIST: [65535,65536,65537,65538,65539,65540,65541,65542,65543,65544]
  23,[STRUCT]
  24,STRING1: END
 */

public class TarsProtoTest {

    //! Test data generated from cpp
    public String GDat =
        "100120ff31010041010151ff7f61ff7e7200010000820001000192ffff7fffa2ffff7ffeb300" +
        "00000100000000c30000000100000001d3ffffffff7fffffffe3ffffffff7ffffffef40f3f8c" +
        "cccdf410c00ccccdf5113ff19999a0000000f512c0019999a0000000f6134041424344454647" +
        "48494a4b4c4d4e4f505145535455565758595a6162636465666768696a6b6c6d6e6f70717265" +
        "737475767778797a313233343536373839302ef8140002060531323334351605363738393006" +
        "05616263646516054142434445fd1500000a30313233343536373839f916000a020000ffff02" +
        "0001000002000100010200010002020001000302000100040200010005020001000602000100" +
        "070200010008fa171200bc614e260a48656c6c6f576f726c643d000004077d020dfb17f61803" +
        "454e44";
    
    @Test
    public void proto_input() {
        byte[] data = HexUtil.hexStr2Bytes(GDat);
        TarsInputStream st = new TarsInputStream(data);
        byte a=0;
        a = st.read(a,1,true);
        assertEquals(1,a);
        a = st.read(a,2,true);
        assertEquals(-1,a);
        short b=0;
        b = st.read(b,3,true);
        assertEquals(256,b);
        b = st.read(b,4,true);
        assertEquals(257,b);
        b = st.read(b,5,true);
        assertEquals(-129,b);
        b = st.read(b,6,true);
        assertEquals(-130,b);
        int c=0;
        c = st.read(c,7,true);
        assertEquals(65536,c);
        c = st.read(c,8,true);
        assertEquals(65537,c);
        c = st.read(c,9,true);
        assertEquals(-32769,c);
        c = st.read(c,10,true);
        assertEquals(-32770,c);
        float f=0f;
        double d=0;
        f = st.read(f,15,true);
        assertEquals(1.1,f,0.0000001);
        f = st.read(f,16,true);
        assertEquals(-2.2,f,0.0000001);
        d = st.read(d,17,true);
        assertEquals(1.1,d,0.0000001);
        d = st.read(d,18,true);
        assertEquals(-2.2,d,0.0000001);
        String s = "";
        s = st.read(s,19,true);
        assertEquals("ABCDEFGHIJKLMNOPQESTUVWXYZabcdefghijklmnopqrestuvwxyz1234567890.",s);
        Map<String,String> m;
        m = st.readStringMap(20,true);
        assertEquals("67890",m.get("12345"));
        assertEquals("ABCDE",m.get("abcde"));
        byte g[] = null;
        g = st.read(g, 21, true);
        assertTrue(Arrays.equals(new byte[]{48,49,50,51,52,53,54,55,56,57}, g));
        int k[] = null;
        k = st.read(k, 22, true);
        assertTrue(Arrays.equals(new int[]{65535,65536,65537,65538,65539,65540,65541,65542,65543,65544}, k));
        TestUserInfo tui = new TestUserInfo();
        tui = (TestUserInfo) st.read(tui,23,true);
        assertEquals(12345678,tui.uin);
        assertEquals("HelloWorld",tui.nick);
        byte[] corr = {0x07,0x7d,0x02,0x0d};
        assertEquals(corr[0],tui.birthday[0]);
        assertEquals(corr[1],tui.birthday[1]);
        assertEquals(corr[2],tui.birthday[2]);
        assertEquals(corr[3],tui.birthday[3]);
        s = st.read(s, 24, true);
        assertEquals("END", s);
    }
    
    @Test
    public void proto_display() {
    	byte[] data = HexUtil.hexStr2Bytes(GDat);
        TarsInputStream st = new TarsInputStream(data);
        StringBuilder sb = new StringBuilder();
        ProtoDisplayer pd = new ProtoDisplayer(sb);
        pd.display(st);
        System.out.println(sb);
    }

}
