package run.halo.app.util;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.Strings;
import org.springframework.util.DigestUtils;
import run.halo.app.model.params.LoginParam;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class Util {
    static String GetAccConf() {
        String path = "./data/acc_count.conf";
        char[] buf = new char[1024];
        int len = 0;
        try {
            FileReader reader = new FileReader(path);
            len = reader.read(buf);
        } catch (FileNotFoundException e) {
            log.error("acc_count not config.");
            e.printStackTrace();
        } catch (IOException e) {
            log.error("acc_count read err.");
            e.printStackTrace();
        }
        return new String(buf, 0, len);
    }

    static String GetMd5(String src) {
        String salt = "MOniKjjtOZMWvJSy";
        return DigestUtils.md5DigestAsHex((DigestUtils.md5DigestAsHex(src.getBytes(StandardCharsets.UTF_8)) + salt).getBytes(
            StandardCharsets.UTF_8));
    }

    public static boolean MatchPassword(LoginParam loginParam) {
        String accConf = GetAccConf();
        String[] accInfos = Strings.split(accConf, '\n');
        for (int i = 0; i < accInfos.length; i++) {
            String[] accInfo = Strings.split(accInfos[i], ':');
            if (accInfo.length != 3) {
                log.warn("accinfo config err. " + accInfos[i]);
                continue;
            }
            if (accInfo[0].equals(loginParam.getUsername()) && accInfo[1].equals(GetMd5(loginParam.getPassword()))) {
                return true;
            }
        }
        return false;
    }
}
