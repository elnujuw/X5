package org.junle.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.junle.system.domain.DemoTest;

import java.util.List;

/**
 * 测试Service接口
 * 
 * @author elnujuw
 * @date 2025-05-28
 */
public interface IDemoTestService  extends IService<DemoTest>
{

    public List<DemoTest> selectDemoTestList(DemoTest demoTest);

    public DemoTest selectDemoTestByObjectId(Long objectId);

    public boolean insertDemoTest(DemoTest demoTest);

    public boolean updateDemoTest(DemoTest demoTest);

    public boolean deleteDemoTestByObjectIds(Long[] objectIds);

    public boolean deleteDemoTestByObjectId(Long objectId);
}
