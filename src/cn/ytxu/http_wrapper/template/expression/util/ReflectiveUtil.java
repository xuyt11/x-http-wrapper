package cn.ytxu.http_wrapper.template.expression.util;

import cn.ytxu.http_wrapper.model.BaseModel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 * Created by ytxu on 2016/7/24.
 * 发射调用util
 */
public class ReflectiveUtil {

    public static Object invokeMethod(BaseModel reflectObj, String methodName, String printTag) {
        BaseModel realReflectObj = reflectObj;
        Method method;
        do {
            try {
                method = getMethod4CurrReflectiveObject(realReflectObj, methodName);
                break;
            } catch (NoSuchMethodException ignore) {
                try {
                    realReflectObj = getHigherLevelReflectObject(realReflectObj);
                } catch (NotCallThisMethodException e) {
                    throw new NotCallThisMethodException(methodName, reflectObj.getClass().getSimpleName());
                }
            }
        } while (true);

        try {
            return method.invoke(realReflectObj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException(printTag + ", do not find this method :" + methodName);
    }

    private static Method getMethod4CurrReflectiveObject(BaseModel reflectObj, String methodName) throws NoSuchMethodException {
        try {
            Class clazz = reflectObj.getClass();
            return clazz.getDeclaredMethod(methodName);
        } catch (NoSuchMethodException ignore) {
            try {
                Class superClazz = reflectObj.getClass().getSuperclass();// 父类
                return superClazz.getDeclaredMethod(methodName);
            } catch (NoSuchMethodException e) {
                throw new NoSuchMethodException(e.getMessage());
            }
        }
    }

    private static BaseModel getHigherLevelReflectObject(BaseModel reflectObj) throws NotCallThisMethodException {
        // 是数据树中的对象，则调用父对象的方法
        BaseModel higherLevel = reflectObj.getHigherLevel();
        if (higherLevel == null) {// reflectObj is VersionModel, so it have not super base model
            throw new NotCallThisMethodException();
        }
        return higherLevel;
    }

    private static final class NotCallThisMethodException extends IllegalArgumentException {
        public NotCallThisMethodException() {
            super();
        }

        public NotCallThisMethodException(String methodName, String reflectObjectSimpleName) {
            super("error : the data tree can not call this (" + methodName + ") method, and the reflectObj is " + reflectObjectSimpleName);
        }
    }


    //*********************** reflect sub type ***********************
    public static String getString(BaseModel reflectObj, String methodName) {
        Object data = null;
        try {
            data = invokeMethod(reflectObj, methodName, reflectObj.getClass().getSimpleName());
        } catch (NotCallThisMethodException e) {

            e.printStackTrace();
        }

        if (Objects.isNull(data)) {
            return null;
        }
        return data.toString();
    }

    public static List<BaseModel> getList(BaseModel reflectObj, String methodName) {
        return (List<BaseModel>) invokeMethod(reflectObj, methodName, reflectObj.getClass().getSimpleName());
    }

    public static boolean getBoolean(BaseModel reflectObj, String methodName) {
        return (boolean) invokeMethod(reflectObj, methodName, reflectObj.getClass().getSimpleName());
    }

    public static long getNumber(BaseModel reflectObj, String methodName) {
        return (long) invokeMethod(reflectObj, methodName, reflectObj.getClass().getSimpleName());
    }


}
