package com.due.bridge.tomcat.support.impl;

import com.due.basic.tookit.utils.LogicUtil;
import com.due.bridge.tomcat.support.ExceptionNotice;
import com.due.bridge.tomcat.support.UnknownExecoptionNotice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executor;

@Slf4j
public class ExceptionNoticeComposite implements ExceptionNotice, ApplicationContextAware, InitializingBean {

    private List<ExceptionNotice> exceptionNoticeList;


    private ApplicationContext applicationContext;


    private Executor executor;


    @Override
    public boolean support(Exception e) {
        throw new IllegalArgumentException("composite implements no invoke");
    }

    @Override
    public void notice(UnknownExecoptionNotice notice) {
        if (notice == null) {
            return;
        }
        if (LogicUtil.isEmpty(this.exceptionNoticeList)) {
            log.warn("系统没有异常通知类");
            return;
        }
        Optional<ExceptionNotice> first = this.exceptionNoticeList.stream().filter(e -> e.support(notice.getException())).findFirst();
        log.info("异常：{}，对应的通知类为：{}", notice.getClass(), first);
        first.ifPresent(e -> {
            if (null != executor) {
                executor.execute(() -> e.notice(notice));
            } else {
                e.notice(notice);
            }
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, ExceptionNotice> beansOfType = applicationContext.getBeansOfType(ExceptionNotice.class);
        exceptionNoticeList = new ArrayList<>(beansOfType.values());
        exceptionNoticeList.removeIf(next -> next instanceof ExceptionNoticeComposite);
        this.executor = applicationContext.getBean(Executor.class);
    }
}
