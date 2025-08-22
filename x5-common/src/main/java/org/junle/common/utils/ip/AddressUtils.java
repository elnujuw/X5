package org.junle.common.utils.ip;

import org.junle.common.config.X5Config;
import org.junle.common.utils.RegionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junle.common.utils.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 获取地址类
 * 
 * @author elnujuw
 */
public class AddressUtils
{
    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

    // 未知地址
    public static final String UNKNOWN = "XX XX";

    public static String getRealAddressByIP(String ip)
    {
        String address = UNKNOWN;
        // 内网不查询
        if (IpUtils.internalIp(ip))
        {
            return "内网IP";
        }
        if (X5Config.isAddressEnabled())
        {
            try
            {
                String rspStr = RegionUtil.getRegion(ip);
                if (StringUtils.isEmpty(rspStr))
                {
                    log.error("获取地理位置异常 {}", ip);
                    return UNKNOWN;
                }
                String[] obj = rspStr.split("\\|");
                return Arrays.stream(obj)
                        .filter(element -> !"0".equals(element))
                        .collect(Collectors.joining(" "));
            }
            catch (Exception e)
            {
                log.error("获取地理位置异常 {}", e);
            }
        }
        return address;
    }
}

