package com.due.basic.tookit.doamin;

import com.due.basic.tookit.constant.GlobalConstant;
import lombok.Data;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

/**
 * 分页实体类
 *
 * @param <T>
 */
@Data
public class PageSet<T> implements Serializable {

    /**
     * 总页数
     */
    private Integer total;

    /**
     * 当前页
     */
    private Long start;

    /**
     * 页大小
     */
    private Long limit;

    /**
     * 记录
     */
    private List<T> records;


    public static PageSet<?> createPageSet(HttpServletRequest request) {
        String startString = request.getParameter(GlobalConstant.PAGE_PARAMS_START);
        String limitString = request.getParameter(GlobalConstant.PAGE_PARAMS_LIMIT);
        PageSet<?> pageSet = new PageSet<>();
        pageSet.setStart(NumberUtils.toLong(startString, GlobalConstant.DEFAULT_PAGE_PARAMS_START));
        pageSet.setStart(NumberUtils.toLong(limitString, GlobalConstant.DEFAULT_PAGE_PARAMS_LIMIT));
        return pageSet;
    }
}
