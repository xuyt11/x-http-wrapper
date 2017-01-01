package cn.ytxu.http_wrapper.config.property.response;

import cn.ytxu.http_wrapper.common.util.FileUtil;
import cn.ytxu.http_wrapper.model.BaseModel;

public class BaseResponseParamBean extends BaseModel{
    private String name;
    private String type;

    public BaseResponseParamBean() {
        super(null);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }


    //*************** reflect method area ***************
    public String bro_type() {
        return type;
    }

    public String bro_name() {
        return name;
    }

    public String bro_getter() {
        return "get" + FileUtil.getClassFileName(name);
    }

    public String bro_setter() {
        return "set" + FileUtil.getClassFileName(name);
    }

}