/*
 * Copyright 2002-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.context;

/**
 * Strategy interface for processing Lifecycle beans within the ApplicationContext.
 *
 * @author Mark Fisher
 * @author Juergen Hoeller
 * @since 3.0
 *
 * 生命周期相关的几个阶段，在对应的节点会调用对应方法
 * start stop isRunning
 * onRefresh onClose
 */
public interface LifecycleProcessor extends Lifecycle {

	/**
	 * Notification of context refresh, for example, for auto-starting components.
	 */
	void onRefresh();

	/**
	 * Notification of context close phase, for example, for auto-stopping components.
	 */
	void onClose();

}
