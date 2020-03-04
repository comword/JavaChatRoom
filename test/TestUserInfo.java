import com.qq.tars.protocol.tars.*;

public class TestUserInfo extends TarsStructBase {

    int uin;
    String nick;
    byte[] birthday;

    @Override
    public void writeTo(TarsOutputStream os) {
        os.write(uin,1);
        os.write(nick,2);
        os.write(birthday,3);
    }

    @Override
    public void readFrom(TarsInputStream is) {
        uin = is.read(uin,1,true);
        nick = is.read(nick,2,true);
        birthday = is.read(birthday,3,true);
    }
}