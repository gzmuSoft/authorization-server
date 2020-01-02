package cn.edu.gzmu.authserver.model.entity.projection;

import cn.edu.gzmu.authserver.model.entity.Student;
import org.springframework.data.rest.core.config.Projection;

/**
 * 学生投影.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/1/2 下午10:06
 */
@SuppressWarnings("unused")
@Projection(name = "ordinary", types = { Student.class })
public interface StudentOrdinary {

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
    Long getSpecialtyId();

    /**
     * 获取名字
     * @return 名字
     */
    String getName();

    /**
     * 学号
     * @return 学号
     */
    String getNo();

    /**
     * 性别
     * @return 性别
     */
    String getGender();

}
