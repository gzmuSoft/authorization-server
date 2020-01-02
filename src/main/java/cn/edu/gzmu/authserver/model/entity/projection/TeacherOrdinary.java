package cn.edu.gzmu.authserver.model.entity.projection;

import cn.edu.gzmu.authserver.model.entity.Teacher;
import org.springframework.data.rest.core.config.Projection;

/**
 * 教师投影.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/1/2 下午10:06
 */
@SuppressWarnings("unused")
@Projection(name = "ordinary", types = { Teacher.class })
public interface TeacherOrdinary {

    /**
     * ID
     * @return ID
     */
    Long getId();

    /**
     * 学校id
     * @return 学校id
     */
    Long getSchoolId();

    /**
     * 学院id
     * @return 学院id
     */
    Long getCollegeId();

    /**
     * 专业id
     * @return 专业id
     */
    Long getDepId();

    /**
     * 获取名字
     * @return 名字
     */
    String getName();

    /**
     * 性别
     * @return 性别
     */
    String getGender();

}
