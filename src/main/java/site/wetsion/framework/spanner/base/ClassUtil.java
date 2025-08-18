package site.wetsion.framework.spanner.base;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.function.Predicate;

public class ClassUtil {
    /**
     * 获得给定类所在包的名称
     *
     * @param clazz 类
     * @return 包名
     */
    public static String getPackage(Class<?> clazz) {
        if (clazz == null) {
            return StringUtils.EMPTY;
        }
        final String className = clazz.getName();
        int packageEndIndex = className.lastIndexOf(".");
        if (packageEndIndex == -1) {
            return StringUtils.EMPTY;
        }
        return className.substring(0, packageEndIndex);
    }

    /**
     * 判断类是否实现了指定接口
     */
    public static boolean isClassImplementInterface(Class<?> clazz, Class<?> interfaceClazz) {
        // Check if the class implements the given interface, including all parent interfaces
        for (Class<?> iface : clazz.getInterfaces()) {
            if (iface.equals(interfaceClazz)) {
                return true;
            }
        }

        // Also check all superclasses for the interface (interfaces can come from inheritance)
        Class<?> superClass = clazz.getSuperclass();
        while (superClass != null) {
            Class<?>[] superClassInterfaces = superClass.getInterfaces();
            for (Class<?> iface : superClassInterfaces) {
                if (iface.equals(interfaceClazz)) {
                    return true;
                }
            }
            superClass = superClass.getSuperclass();
        }

        return false;
    }

    /**
     * 扫描指定的包下所有的类
     */
    public static List<Class<?>> scanPackage(String packageName) throws IOException, ClassNotFoundException {
        return scanPackage(packageName, null);
    }

    /**
     * 扫描包路径下满足class过滤器条件的所有类
     */
    public static List<Class<?>> scanPackage(String packageName, Predicate<Class<?>> predicate) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();

        // 获得指定包下的所有类名
        List<String> classNames = getClassNames(packageName);

        for (String className : classNames) {
            // 为每个类名加载对应的类对象
            Class<?> clazz = Class.forName(className);

            if (null == predicate || predicate.test(clazz)) {
                classes.add(clazz);
            }
        }

        return classes;
    }

    private static List<String> getClassNames(String packageName) throws IOException {

        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(path);
        List<File> dirs = new ArrayList<>();

        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }

        List<String> classNames = new ArrayList<>();
        for (File directory : dirs) {
            classNames.addAll(findClasses(directory, packageName));
        }

        return classNames;
    }

    private static List<String> findClasses(File directory, String packageName){
        List<String> classNames = new ArrayList<>();
        if (!directory.exists()) {
            return classNames;
        }

        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classNames.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classNames.add(packageName + '.' + file.getName().substring(0, file.getName().length() - 6));
            }
        }

        return classNames;
    }
}
