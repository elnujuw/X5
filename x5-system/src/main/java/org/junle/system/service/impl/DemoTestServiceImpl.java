package org.junle.system.service.impl;

import java.util.Arrays;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.junle.common.annotation.EncryptDataProcess;
import org.junle.common.enums.ProcessType;
import org.junle.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.junle.system.mapper.DemoTestMapper;
import org.junle.system.domain.DemoTest;
import org.junle.system.service.IDemoTestService;

/**
 * 测试Service业务层处理
 * 
 * @author elnujuw
 * @date 2025-05-28
 */
@Service
public class DemoTestServiceImpl extends ServiceImpl<DemoTestMapper, DemoTest> implements IDemoTestService
{
    /**
     * 查询测试
     *
     * @param objectId 测试主键
     * @return 测试
     */
    @Override
    @EncryptDataProcess(type = ProcessType.DECRYPT, processResult = true, processParams = false)
    public DemoTest selectDemoTestByObjectId(Long objectId)
    {
        return this.getById(objectId);
    }

    /**
     * 查询测试列表
     *
     * @param demoTest 测试
     * @return 测试
     */
    @Override
    @EncryptDataProcess(type = ProcessType.DECRYPT, processResult = true, processParams = false)
    public List<DemoTest> selectDemoTestList(DemoTest demoTest)
    {
        QueryWrapper<DemoTest> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotNull(demoTest.getObjectId())) {
            queryWrapper.eq("object_id", demoTest.getObjectId());
        }
        if (StringUtils.isNotEmpty(demoTest.getObjectName())) {
            queryWrapper.like("object_name", demoTest.getObjectName());
        }
        return this.list(queryWrapper);
    }

    /**
     * 新增测试
     *
     * @param demoTest 测试
     * @return 结果
     */
    @Override
    @EncryptDataProcess(type = ProcessType.ENCRYPT, processResult = false, processParams = true)
    public boolean insertDemoTest(DemoTest demoTest)
    {
        return this.save(demoTest);
    }

    /**
     * 修改测试
     *
     * @param demoTest 测试
     * @return 结果
     */
    @Override
    @EncryptDataProcess(type = ProcessType.ENCRYPT, processResult = false, processParams = true)
    public boolean updateDemoTest(DemoTest demoTest)
    {
        return this.updateById(demoTest);
    }

    /**
     * 批量删除测试
     *
     * @param objectIds 需要删除的测试主键
     * @return 结果
     */
    @Override
    public boolean deleteDemoTestByObjectIds(Long[] objectIds)
    {
        return this.removeByIds(Arrays.stream(objectIds).toList());
    }

    /**
     * 删除测试信息
     *
     * @param objectId 测试主键
     * @return 结果
     */
    @Override
    public boolean deleteDemoTestByObjectId(Long objectId)
    {
        return this.removeById(objectId);
    }
}
