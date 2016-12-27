package cn.ytxu.http_wrapper.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ytxu on 16/5/3.
 */
public class BaseModel<HigherLevelModel extends BaseModel> {

    private HigherLevelModel higherLevel;// 上一级对象
    /**
     * 反射获取String数据，并帖附在对应的BaseModel上；
     * 代码：reflectModel.attach(attach, attachContent)
     * 在没有找到反射的方法后，查找该BaseModel是否有该attach==methodName的数据，有则返回attachContent
     * 现在只针对于反射String
     */
    private Map<String, String> attachs = new HashMap<>(10);

    public BaseModel(HigherLevelModel higherLevel) {
        super();
        this.higherLevel = higherLevel;
    }

    public HigherLevelModel getHigherLevel() {
        return higherLevel;
    }


    //********************** attach **********************

    /**
     * @param attach        反射方法的名称
     * @param attachContent 替换的内容
     */
    public void attach(String attach, String attachContent) {
        attachs.put(attach, attachContent);
    }

    public boolean hasThisAttach(String methodName) {
        return attachs.containsKey(methodName);
    }

    public String getAttachContent(String methodName) {
        return attachs.get(methodName);
    }
}
