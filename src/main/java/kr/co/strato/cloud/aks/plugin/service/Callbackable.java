package kr.co.strato.cloud.aks.plugin.service;

import kr.co.strato.cloud.aks.plugin.model.CallbackData;

public interface Callbackable {
	
	/**
	 * Cluster 작업 상태를 callback url로 전달한다.
	 * @param callable
	 * @param data
	 */
	public void sendCallback(ClusterJobCallable callbackable, CallbackData data);
}
