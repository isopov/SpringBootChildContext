package com.example;

import de.tho.DummyService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Hoell on 07.07.2016.
 */
@Controller
@RequestMapping("beans")
public class ChildController implements ApplicationContextAware{

    private DummyService dummyService;
    private ApplicationContext applicationContext;

//    @Autowired
//    public ChildController(DummyService dummyService) {
//        this.dummyService = dummyService;
//    }

    private DummyService getDummyService(){
        if (this.dummyService == null){
            this.dummyService = (DummyService) applicationContext.getParent().getBean("dummyService");
        }

        return this.dummyService;
    }

    @RequestMapping("")
    @ResponseBody
    public String index(){
        StringBuilder beans = new StringBuilder();

        for (String bean : applicationContext.getBeanDefinitionNames()){
            beans
                    .append(bean)
                    .append("<br />");
        }

        return beans.toString();
    }

    @RequestMapping("service")
    @ResponseBody
    public String service(){

        return getDummyService().getMessage();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
