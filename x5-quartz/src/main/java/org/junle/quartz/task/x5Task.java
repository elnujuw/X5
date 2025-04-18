package org.junle.quartz.task;

import org.springframework.stereotype.Component;
import org.junle.common.utils.StringUtils;

/**
 * 定时任务调度测试
 * 
 * @author elnujuw
 */
@Component("x5Task")
public class x5Task
{
    public void x5MultipleParams(String s, Boolean b, Long l, Double d, Integer i)
    {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void x5Params(String params)
    {
        System.out.println("执行有参方法：" + params);
    }

    public void x5NoParams()
    {
        System.out.println("执行无参方法");
    }
}
