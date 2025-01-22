package com.atguigu.cloud.service.impl;

import com.atguigu.cloud.apis.AccountFeignApi;
import com.atguigu.cloud.apis.StorageFeignApi;
import com.atguigu.cloud.entities.Order;
import com.atguigu.cloud.mapper.OrderMapper;
import com.atguigu.cloud.service.OrderService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
/**
 * 下订单->减库存->扣余额->改(订单)状态
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;
    @Resource//订单微服务通过OpenFeign去调用库存微服务
    private StorageFeignApi storageFeignApi;
    @Resource//订单微服务通过OpenFeign去调用账户微服务
    private AccountFeignApi accountFeignApi;

    @Override
    //    @GlobalTransactional@Transactional(rollbackFor = Exception.class)//XA
    @GlobalTransactional(name = "zzyy-create-order",rollbackFor = Exception.class)//AT
    public void create(Order order) {
        //xid 全局事务id的检查
        String xid = RootContext.getXID();

        //1.新建订单
        log.info("===================================开始新建订单  xid_order:"+xid);
        //订单状态status  0:创建中；1：已完结
        order.setStatus(0);
        int result = orderMapper.insert(order);
        //插入订单成功后获得插入mysql的实体对象
        Order orderFromDB=null;
        if(result>0)
        {
            //从mysql查询刚插入的记录
            orderFromDB = orderMapper.selectOne(order);
            //orderFromDB = orderMapper.selectByPrimaryKey(order.getId());
            log.info("-------->新建订单成功，orderFromDB info:"+orderFromDB);
            System.out.println();
            //2.扣减库存
            log.info("--------->订单微服务开始调用Stroage库存，做抵扣count");
            storageFeignApi.decrease(orderFromDB.getProductId(),orderFromDB.getCount());
            log.info("-------->订单微服务结束调用Storage库存，做扣减完成");
            System.out.println();
            //3.扣减账号余款
            log.info("--------->订单为服务器开始调用Account账号，做扣减money");
            accountFeignApi.decrease(orderFromDB.getUserId(),orderFromDB.getMoney());
            log.info("--------->订单为服务器结束调用Account账号，做扣减完成");
            //4.修改订单状态
            //订单状态status  0:创建中；1：已完结
            log.info("--------->修改订单状态");
            orderFromDB.setStatus(1);

            //创建查询条件
            Example whereCondition = new Example(Order.class);
            Example.Criteria criteria = whereCondition.createCriteria();
            criteria.andEqualTo("userId",orderFromDB.getUserId());
            criteria.andEqualTo("status",0);

            //更新操作：根据Example特定的查询条件，找到相应的记录，并基于传入的实体对象来更新记录中的字段值。
            int updateResult = orderMapper.updateByExampleSelective(orderFromDB, whereCondition);
            log.info("--------->修改订单状态完成 "+updateResult);
            log.info("---------> orderFromDB info: "+orderFromDB);
        }
        System.out.println();
        log.info("====================================结束新建订单 xdi_order: "+xid);
    }
}
