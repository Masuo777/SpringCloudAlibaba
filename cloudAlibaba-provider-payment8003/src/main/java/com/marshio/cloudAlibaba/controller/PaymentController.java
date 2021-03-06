package com.marshio.cloudAlibaba.controller;

import com.marshio.cloudAlibaba.dao.PaymentDao;
import com.marshio.cloudAlibaba.entities.Payment;
import com.marshio.cloudAlibaba.vo.ResponseBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author masuo
 * @data 24/4/2022 下午4:27
 * @Description 值赋服务
 */

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentDao paymentDao;

    // 服务发现客户端，其作用是主动发现并获取服务
    @Resource
    private DiscoveryClient discoveryClient;

    // @Value可以获取xml/yml中定义的数据
    @Value("${server.port}")
    private String port;

    @PostMapping(value = "/payment/create")
    public ResponseBean<Integer> create(Payment payment) {
        int result = paymentDao.create(payment);
        log.info("插入结果 ：" + result);
        if (result > 0) {
            //parameterized
            return new ResponseBean<>(200, "success,端口为：" + port, result);
        } else {

            return new ResponseBean<>(408, "失败了哦", null);
        }
    }

    @GetMapping(value = "/payment/getPaymentById/{id}")
    public ResponseBean<Payment> getPaymentById(@PathVariable("id") Long id) {

        Payment payment = paymentDao.getPaymentById(id);
        log.info("查询结果 ：" + payment);
        if (payment != null) {
            //parameterized
            return new ResponseBean<>(200, "success,端口为：" + port, payment);
        } else {
            return new ResponseBean<>(408, "失败了哦", null);
        }
    }

    @GetMapping(value = "/getServices")
    public ResponseBean<Object> getDiscoveryList() {
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            System.out.println("服务列表：" + service);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUDALIBABA-PAYMENT-SERVICE");
        instances.forEach(instanceInfo -> System.out.println(instanceInfo + "**" + instanceInfo.getHost() + "**" + instanceInfo.getUri()));
        return new ResponseBean<>(200,"成功",services);
    }
}
