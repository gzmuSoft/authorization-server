package cn.edu.gzmu.authserver.util;

import cn.edu.gzmu.authserver.model.entity.SysUser;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/5/5 下午8:07
 */
public class MapUtils {

    private MapUtils() {
    }

    private final static String AVATAR = "avatar";
    private final static String IMAGE = "image";
    private final static String PHONE = "phone";
    private final static String EMAIL = "email";
    private final static String NAME = "name";
    private final static String ID = "id";

    public static JSONObject userBaseJson(SysUser user) {
        final JSONObject userObject = new JSONObject();
        userObject.put(ID, user.getId());
        userObject.put(IMAGE, user.getImage());
        userObject.put(AVATAR, user.getAvatar());
        userObject.put(PHONE, user.getPhone());
        userObject.put(EMAIL, user.getEmail());
        return userObject;
    }

    public static List<JSONObject> userBaseList(List<JSONObject> users, List<JSONObject> entity) {
        final List<JSONObject> result = new ArrayList<>(users.size());
        for (int i = 0; i < users.size(); i++) {
            final JSONObject user = users.get(i);
            final JSONObject teacher = entity.get(i);
            final JSONObject r = new JSONObject();
            r.put(ID, user.getLong(ID));
            r.put(IMAGE, user.getString(IMAGE));
            r.put(AVATAR, user.getString(AVATAR));
            r.put(PHONE, user.getString(PHONE));
            r.put(EMAIL, user.getString(EMAIL));
            r.put(NAME, teacher.getString(NAME));
            result.add(r);
        }
        return result;
    }

}
