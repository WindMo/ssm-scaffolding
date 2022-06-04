package ws.ssm.scaffolding.initializer;

import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>Servlet的java配置，重写{@link #initProxyFilter()}进行过滤器配置，其核心是实现{@link WebApplicationInitializer}接口
 *
 * @author WindShadow
 * @version 2020/8/16.
 */

public abstract class BaseSpringMvcAnnotationConfigDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * 要进行代理的过滤器信息集合
     */
    private List<FilterProxyWrapper> proxyFilters = new LinkedList<>();
    private List<Object> listeners = new LinkedList<>();

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        this.initListener();
        this.registerListeners(servletContext);
        this.initEncodingFilter(servletContext);
        this.initProxyFilter();
        this.registerProxyFilters(servletContext);
        super.onStartup(servletContext);
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    // ~ About Listener
    // =====================================================================================

    protected void initListener() {

        addListener(RequestContextListener.class);
    }

    private void registerListeners(ServletContext servletContext) {

        for (Object listener : listeners) {

            if (listener instanceof ServletRequestListener) {

                servletContext.addListener((ServletRequestListener) listener);
            } else if (listener instanceof String) {

                servletContext.addListener((String) listener);
            } else if (listener instanceof Class && EventListener.class.isAssignableFrom((Class<? extends EventListener>) listener)) {

                servletContext.addListener((Class<? extends EventListener>) listener);
            } else {

                throw new IllegalStateException("Unsupported listeners config");
            }
        }
    }

    protected final <T extends ServletRequestListener> void addListener(T listener) {

        Assert.notNull(listener, "The listener must be not null");
        listeners.add(listener);
    }

    protected final <T extends EventListener> void addListener(Class<T> listenerClass) {

        Assert.notNull(listenerClass, "The listener must be not null");
        listeners.add(listenerClass);
    }

    protected final void addListener(String listenerClassName) {

        Assert.hasText(listenerClassName, "The listener must be not blank");
        listeners.add(listenerClassName);
    }

    // ~ About Filter
    // =====================================================================================

    /**
     * 设定编码过滤器
     *
     * @param servletContext
     */
    private void initEncodingFilter(ServletContext servletContext) {

        String[] urlPatterns = getServletMappings();
        Filter encodingFilter = getCharacterEncodingFilter();
        this.addFilter(servletContext, encodingFilter, "encodingFilter", urlPatterns, false);
    }

    /**
     * 获取编码过滤器
     *
     * @return CharacterEncodingFilter
     */
    protected CharacterEncodingFilter getCharacterEncodingFilter() {

        return new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true);
    }

    /**
     * 初始化要代理的过滤器
     */
    protected abstract void initProxyFilter();

    /**
     * 根据代理过滤器注册表中的注册信息配置到servletContext中
     *
     * @param servletContext
     */
    private void registerProxyFilters(ServletContext servletContext) {

        for (FilterProxyWrapper wrapper : this.proxyFilters) {

            DelegatingFilterProxy delegatingFilterProxy = new DelegatingFilterProxy();
            delegatingFilterProxy.setTargetFilterLifecycle(wrapper.isTargetFilterLifecycle());
            this.addFilter(servletContext, delegatingFilterProxy, wrapper.getFilterBeanName(), wrapper.getUrlPatterns(), wrapper.isAsyncSupported(), wrapper.getDispatcherTypes());
        }
    }

    /**
     * 注册需在IOC中要进行代理的过滤器【不管理代理的过滤器的生命周期】【不支持异步】【默认调度类型配置为空】
     * <p> 通过此方式注册过滤器时，表示{@link DelegatingFilterProxy#setTargetBeanName(String)}为驼峰表示的filterClass的短名称
     *
     * @param filterClass 过滤器class
     * @param urlPatterns 过滤器要过滤的url格式
     */
    protected final void registerProxyFilter(Class<? extends Filter> filterClass, String... urlPatterns) {

        this.registerProxyFilter(filterClass, urlPatterns, new DispatcherType[]{});
    }

    /**
     * 注册需在IOC中要进行代理的过滤器【不管理代理的过滤器的生命周期】【不支持异步】【默认调度类型配置为空】
     *
     * @param filterBeanName 过滤器在IOC中的beanName
     * @param urlPatterns    过滤器要过滤的url格式
     */
    protected final void registerProxyFilter(String filterBeanName, String... urlPatterns) {

        this.registerProxyFilter(filterBeanName, urlPatterns, new DispatcherType[]{});
    }


    /**
     * 注册需在IOC中要进行代理的过滤器【不管理代理的过滤器的生命周期】【不支持异步】
     * <p> 通过此方式注册过滤器时，表示{@link DelegatingFilterProxy#setTargetBeanName(String)}为驼峰表示的filterClass的短名称
     *
     * @param filterClass     过滤器class
     * @param urlPatterns     过滤器要过滤的url格式
     * @param dispatcherTypes 调度类型
     */
    protected final void registerProxyFilter(Class<? extends Filter> filterClass, String[] urlPatterns, DispatcherType... dispatcherTypes) {

        this.registerProxyFilter(false, filterClass, false, urlPatterns, dispatcherTypes);
    }

    /**
     * 注册需在IOC中要进行代理的过滤器【不管理代理的过滤器的生命周期】【不支持异步】
     *
     * @param filterBeanName  过滤器在IOC中的beanName
     * @param urlPatterns     过滤器要过滤的url格式
     * @param dispatcherTypes 调度类型
     */
    protected final void registerProxyFilter(String filterBeanName, String[] urlPatterns, DispatcherType... dispatcherTypes) {

        this.registerProxyFilter(false, filterBeanName, false, urlPatterns, dispatcherTypes);
    }

    /* 不支持异步 */

    /**
     * 注册需在IOC中要进行代理的过滤器【不支持异步】【默认调度类型配置为空】
     * <p> 通过此方式注册过滤器时，表示{@link DelegatingFilterProxy#setTargetBeanName(String)}为驼峰表示的filterClass的短名称
     *
     * @param filterClass           过滤器class
     * @param urlPatterns           过滤器要过滤的url格式
     * @param targetFilterLifecycle 是否管理代理的过滤器的生命周期
     */
    protected final void registerProxyFilter(Class<? extends Filter> filterClass, boolean targetFilterLifecycle, String... urlPatterns) {

        this.registerProxyFilter(filterClass, targetFilterLifecycle, urlPatterns, new DispatcherType[]{});
    }

    /**
     * 注册需在IOC中要进行代理的过滤器【不支持异步】【默认调度类型配置为空】
     *
     * @param filterBeanName        过滤器在IOC中的beanName
     * @param urlPatterns           过滤器要过滤的url格式
     * @param targetFilterLifecycle 是否管理代理的过滤器的生命周期
     */
    protected final void registerProxyFilter(String filterBeanName, boolean targetFilterLifecycle, String... urlPatterns) {

        this.registerProxyFilter(filterBeanName, targetFilterLifecycle, urlPatterns, new DispatcherType[]{});
    }

    /**
     * 注册需在IOC中要进行代理的过滤器【不支持异步】
     * <p> 通过此方式注册过滤器时，表示{@link DelegatingFilterProxy#setTargetBeanName(String)}为驼峰表示的filterClass的短名称
     *
     * @param filterClass           过滤器class
     * @param urlPatterns           过滤器要过滤的url格式
     * @param targetFilterLifecycle 是否管理代理的过滤器的生命周期
     * @param dispatcherTypes       调度类型
     */
    protected final void registerProxyFilter(Class<? extends Filter> filterClass, boolean targetFilterLifecycle, String[] urlPatterns, DispatcherType... dispatcherTypes) {

        this.registerProxyFilter(false, filterClass, targetFilterLifecycle, urlPatterns, dispatcherTypes);
    }

    /**
     * 注册需在IOC中要进行代理的过滤器【不支持异步】
     *
     * @param filterBeanName        过滤器在IOC中的beanName
     * @param urlPatterns           过滤器要过滤的url格式
     * @param targetFilterLifecycle 是否管理代理的过滤器的生命周期
     * @param dispatcherTypes       调度类型
     */
    protected final void registerProxyFilter(String filterBeanName, boolean targetFilterLifecycle, String[] urlPatterns, DispatcherType... dispatcherTypes) {

        this.registerProxyFilter(false, filterBeanName, targetFilterLifecycle, urlPatterns, dispatcherTypes);
    }

    /* 不管理代理的过滤器的生命周期 */

    /**
     * 注册需在IOC中要进行代理的过滤器【不管理代理的过滤器的生命周期】【默认调度类型配置为空】
     * <p> 通过此方式注册过滤器时，表示{@link DelegatingFilterProxy#setTargetBeanName(String)}为驼峰表示的filterClass的短名称
     *
     * @param asyncSupported 异步支持
     * @param filterClass    过滤器class
     * @param urlPatterns    过滤器要过滤的url格式
     */
    protected final void registerProxyFilter(boolean asyncSupported, Class<? extends Filter> filterClass, String... urlPatterns) {

        this.registerProxyFilter(asyncSupported, filterClass, urlPatterns, new DispatcherType[]{});
    }

    /**
     * 注册需在IOC中要进行代理的过滤器【不管理代理的过滤器的生命周期】【默认调度类型配置为空】
     *
     * @param asyncSupported 异步支持
     * @param filterBeanName 过滤器在IOC中的beanName
     * @param urlPatterns    过滤器要过滤的url格式
     */
    protected final void registerProxyFilter(boolean asyncSupported, String filterBeanName, String... urlPatterns) {

        this.registerProxyFilter(asyncSupported, filterBeanName, urlPatterns, new DispatcherType[]{});
    }

    /**
     * 注册需在IOC中要进行代理的过滤器【不管理代理的过滤器的生命周期】
     * <p> 通过此方式注册过滤器时，表示{@link DelegatingFilterProxy#setTargetBeanName(String)}为驼峰表示的filterClass的短名称
     *
     * @param asyncSupported  异步支持
     * @param filterClass     过滤器class
     * @param urlPatterns     过滤器要过滤的url格式
     * @param dispatcherTypes 调度类型
     */
    protected final void registerProxyFilter(boolean asyncSupported, Class<? extends Filter> filterClass, String[] urlPatterns, DispatcherType... dispatcherTypes) {

        this.registerProxyFilter(asyncSupported, filterClass, false, urlPatterns, dispatcherTypes);
    }

    /**
     * 注册需在IOC中要进行代理的过滤器【不管理代理的过滤器的生命周期】
     *
     * @param asyncSupported  异步支持
     * @param filterBeanName  过滤器在IOC中的beanName
     * @param urlPatterns     过滤器要过滤的url格式
     * @param dispatcherTypes 调度类型
     */
    protected final void registerProxyFilter(boolean asyncSupported, String filterBeanName, String[] urlPatterns, DispatcherType... dispatcherTypes) {

        this.registerProxyFilter(asyncSupported, filterBeanName, false, urlPatterns, dispatcherTypes);
    }

    /* base method of registerProxyFilter */

    /**
     * 注册需在IOC中要进行代理的过滤器【默认调度类型配置为空】
     * <p> 通过此方式注册过滤器时，表示{@link DelegatingFilterProxy#setTargetBeanName(String)}为驼峰表示的filterClass的短名称
     *
     * @param asyncSupported        异步支持
     * @param filterClass           过滤器class
     * @param targetFilterLifecycle 是否管理代理的过滤器的生命周期
     * @param urlPatterns           过滤器要过滤的url格式
     */
    protected final void registerProxyFilter(boolean asyncSupported, Class<? extends Filter> filterClass, boolean targetFilterLifecycle, String... urlPatterns) {

        this.registerProxyFilter(asyncSupported, filterClass, targetFilterLifecycle, urlPatterns, new DispatcherType[]{});
    }

    /**
     * 注册需在IOC中要进行代理的过滤器【默认调度类型配置为空】
     *
     * @param asyncSupported        异步支持
     * @param filterBeanName        过滤器在IOC中的beanName
     * @param targetFilterLifecycle 是否管理代理的过滤器的生命周期
     * @param urlPatterns           过滤器要过滤的url格式
     */
    protected final void registerProxyFilter(boolean asyncSupported, String filterBeanName, boolean targetFilterLifecycle, String... urlPatterns) {

        this.registerProxyFilter(asyncSupported, filterBeanName, targetFilterLifecycle, urlPatterns, new DispatcherType[]{});
    }

    /**
     * 注册需在IOC中要进行代理的过滤器
     * <p> 通过此方式注册过滤器时，表示{@link DelegatingFilterProxy#setTargetBeanName(String)}为驼峰表示的filterClass的短名称
     *
     * @param asyncSupported        异步支持
     * @param filterClass           过滤器class
     * @param targetFilterLifecycle 是否管理代理的过滤器的生命周期
     * @param urlPatterns           过滤器要过滤的url格式
     * @param dispatcherTypes       调度类型
     */
    protected final void registerProxyFilter(boolean asyncSupported, Class<? extends Filter> filterClass, boolean targetFilterLifecycle, String[] urlPatterns, DispatcherType... dispatcherTypes) {

        Assert.notNull(filterClass, "filterClass must be not null! ");
        String filterBeanName = ClassUtils.getShortNameAsProperty(filterClass);
        this.registerProxyFilter(asyncSupported, filterBeanName, targetFilterLifecycle, urlPatterns, dispatcherTypes);
    }

    /**
     * 注册需在IOC中要进行代理的过滤器
     *
     * @param dispatcherTypes       调度类型
     * @param filterBeanName        过滤器在IOC中的beanName
     * @param targetFilterLifecycle 是否管理代理的过滤器的生命周期
     * @param urlPatterns           过滤器要过滤的url格式
     * @param asyncSupported        异步支持
     */
    protected final void registerProxyFilter(boolean asyncSupported, String filterBeanName, boolean targetFilterLifecycle, String[] urlPatterns, DispatcherType... dispatcherTypes) {

        Assert.notNull(filterBeanName, "filterBeanName must be not null! ");
        Assert.notEmpty(urlPatterns, "urlPatterns must be not empty! ");
        this.proxyFilters.add(new FilterProxyWrapper(filterBeanName, urlPatterns, targetFilterLifecycle, asyncSupported, dispatcherTypes));
    }

    /* base method of registerProxyFilter end */

    /**
     * 添加过滤器到ServletContext上下文中
     *
     * @param servletContext  ServletContext上下文中
     * @param filter          过滤器实例
     * @param filterName      过滤器名称
     * @param urlPatterns     过滤器要过滤的url格式
     * @param asyncSupported  异步支持
     * @param dispatcherTypes 调度类型
     */
    private void addFilter(ServletContext servletContext, Filter filter, String filterName, String[] urlPatterns, boolean asyncSupported, DispatcherType... dispatcherTypes) {

        Assert.notNull(filter, "The filter must be not null! ");
        Assert.notNull(filterName, "The filterName must be not null! ");
        Assert.notEmpty(urlPatterns, "The urlPatterns must be not empty! ");
        // 得到过滤器动态配置实例
        FilterRegistration.Dynamic dynamic = servletContext.addFilter(filterName, filter);
        if (dynamic != null) {
            EnumSet<DispatcherType> enumSet = dispatcherTypes.length == 0 ?
                    EnumSet.noneOf(DispatcherType.class) : EnumSet.copyOf(Arrays.stream(dispatcherTypes).collect(Collectors.toSet()));
            dynamic.addMappingForUrlPatterns(enumSet, false, urlPatterns);
            dynamic.setAsyncSupported(asyncSupported);
        }
    }

    /**
     * 过滤器代理信息封装
     */
    private static class FilterProxyWrapper {

        private String filterBeanName;
        private String[] urlPatterns;
        private boolean targetFilterLifecycle;
        private boolean asyncSupported;
        private DispatcherType[] dispatcherTypes;

        public FilterProxyWrapper() {
        }

        public FilterProxyWrapper(String filterBeanName, String[] urlPatterns, boolean targetFilterLifecycle, boolean asyncSupported, DispatcherType[] dispatcherTypes) {
            this.filterBeanName = filterBeanName;
            this.urlPatterns = urlPatterns;
            this.targetFilterLifecycle = targetFilterLifecycle;
            this.asyncSupported = asyncSupported;
            this.dispatcherTypes = dispatcherTypes;
        }

        public String getFilterBeanName() {
            return filterBeanName;
        }

        public void setFilterBeanName(String filterBeanName) {
            this.filterBeanName = filterBeanName;
        }

        public String[] getUrlPatterns() {
            return urlPatterns;
        }

        public void setUrlPatterns(String[] urlPatterns) {
            this.urlPatterns = urlPatterns;
        }

        public boolean isTargetFilterLifecycle() {
            return targetFilterLifecycle;
        }

        public void setTargetFilterLifecycle(boolean targetFilterLifecycle) {
            this.targetFilterLifecycle = targetFilterLifecycle;
        }

        public boolean isAsyncSupported() {
            return asyncSupported;
        }

        public void setAsyncSupported(boolean asyncSupported) {
            this.asyncSupported = asyncSupported;
        }

        public DispatcherType[] getDispatcherTypes() {
            return dispatcherTypes;
        }

        public void setDispatcherTypes(DispatcherType[] dispatcherTypes) {
            this.dispatcherTypes = dispatcherTypes;
        }
    }
}

