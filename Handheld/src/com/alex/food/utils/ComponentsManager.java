package com.alex.food.utils;

import java.util.Stack;

import android.app.Activity;
import android.app.Service;
import android.content.Context;

/**
 * 组件管理器，用于完全退出
 * 
 * @author Alex
 * @version 1.0
 * @since 2014/6/6
 */
public class ComponentsManager {

	/**
	 * 组件（Activity或Service）管理器，用于完全退出
	 * 
	 * @return 组件管理器对象
	 */
	public static ComponentsManager getComponentManager() {
		if (instance == null) {
			instance = new ComponentsManager();
		}
		return instance;
	}

	/**
	 * 回收堆栈中指定的组件
	 * 
	 * @param 组件
	 */
	public void popComponent(Context context) {
		if (null != context) {
			if (context instanceof Activity) {
				((Activity) context).finish();
			} else if (context instanceof Service) {
				((Service) context).stopSelf();
			}
			componentStack.remove(context);
		}
	}

	/**
	 * 获取堆栈的栈顶组件
	 * 
	 * @return 栈顶组件
	 */
	private Context currentComponent() {
		Context context = null;
		if (null != componentStack && !componentStack.isEmpty()) {
			context = componentStack.pop();
		}
		return context;
	}

	/**
	 * 将组件压入堆栈
	 * 
	 * @param context
	 *            需要压入堆栈的组件
	 */
	public void pushComponent(Context context) {
		if (null == componentStack) {
			componentStack = new Stack<Context>();
		}
		componentStack.push(context);
	}

	/**
	 * 回收堆栈中的所有组件
	 */
	public void popAllComponent() {
		Context context = null;
		while (null != componentStack && !componentStack.isEmpty()) {
			context = currentComponent();
			if (null != context) {
				popComponent(context);
			}
		}
	}

	private ComponentsManager() {

	}

	private static Stack<Context> componentStack;
	private volatile static ComponentsManager instance;
}
