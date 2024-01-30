package com.due.basic.tookit.utils;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author daizl
 * @create 2023-10-10-14:03
 */

public class ListPageInfoUtil {

    /**
     * 手动分页
     *
     * @param pageNum
     * @param pageSize
     * @param list
     * @param <T>
     * @return
     */
    public static <T> PageInfo<T> listPageInfo(Integer pageNum, Integer pageSize, List<T> list) {
        if (Objects.isNull(pageNum) || pageNum <= 0) {
            pageNum = 1;
        }
        if (Objects.isNull(pageSize) || pageSize <= 0) {
            pageSize = 10;
        }
        //stream实现list分页
        List<T> pageList = list.stream().skip((pageNum - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
        PageInfo<T> pageInfo = new PageInfo<T>(pageList);
        //获取PageInfo其他参数
        pageInfo.setTotal(list.size());
        int endRow = pageInfo.getEndRow() == 0 ? 0 : (int) ((pageNum - 1) * pageSize + pageInfo.getEndRow() + 1);
        pageInfo.setEndRow(endRow);
        boolean hasNextPage = list.size() <= pageSize * pageNum ? false : true;
        pageInfo.setHasNextPage(hasNextPage);
        boolean hasPreviousPage = pageNum == 1 ? false : true;
        pageInfo.setHasPreviousPage(hasPreviousPage);
        pageInfo.setIsFirstPage(!hasPreviousPage);
        boolean isLastPage = (list.size() > pageSize * (pageNum - 1) && list.size() <= pageSize * pageNum) ? true : false;
        pageInfo.setIsLastPage(isLastPage);
        int pages = list.size() % pageSize == 0 ? list.size() / pageSize : (list.size() / pageSize) + 1;
        pageInfo.setNavigateLastPage(pages);
        int[] navigatePageNums = new int[pages];
        for (int i = 1; i < pages; i++) {
            navigatePageNums[i - 1] = i;
        }
        pageInfo.setNavigatepageNums(navigatePageNums);
        int nextPage = pageNum < pages ? pageNum + 1 : 0;
        pageInfo.setNextPage(nextPage);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        pageInfo.setPages(pages);
        pageInfo.setPrePage(pageNum - 1);
        pageInfo.setSize(pageInfo.getList().size());
        int starRow = list.size() < pageSize * pageNum ? 1 + pageSize * (pageNum - 1) : 0;
        pageInfo.setStartRow(starRow);
        return pageInfo;
    }
}
