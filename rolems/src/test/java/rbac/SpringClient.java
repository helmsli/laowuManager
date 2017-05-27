package rbac;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xinwei.rbac.entity.CommonBiz;
import com.xinwei.rbac.service.CommonBizService;

public class SpringClient {
	public static void main(String[] args) {  
		 ApplicationContext ctx = 
				 new ClassPathXmlApplicationContext("client/hessian-client.xml");
        // 获得客户端的Hessian代理工厂bean  
        CommonBizService  dataCommBizService = (CommonBizService)ctx.getBean("commBizClient");
        List<CommonBiz> selectAll = dataCommBizService.getAll(0,15);
        for (CommonBiz dataCommBiz : selectAll) {
        	System.out.println(dataCommBiz);
		}
    }  
}
