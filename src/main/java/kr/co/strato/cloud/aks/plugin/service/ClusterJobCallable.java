package kr.co.strato.cloud.aks.plugin.service;

import java.util.concurrent.Callable;

import kr.co.strato.cloud.aks.plugin.model.CallbackData;

public abstract class ClusterJobCallable<V> implements Callable<Object> {
	private String clusterName;
	private String callbackUrl;
	private Long workJobIdx;
	private Callbackable callbackable;

	public ClusterJobCallable(Callbackable callbackable, String clusterName, String callbackUrl) {
		this.callbackable = callbackable;
		this.clusterName = clusterName;
		this.callbackUrl = callbackUrl;
	}

	@Override
	public Object call() throws Exception {		
		//시작 콜백 전송
		CallbackData startCallback = CallbackData.startSuccess(workJobIdx);
		callbackable.sendCallback(this, startCallback);
		
		
		Object result = null;
		try {
			result = clusterJob();
			
			//작업 성공
			CallbackData finishCallback = CallbackData.finishSuccess(workJobIdx, result);
			callbackable.sendCallback(this, finishCallback);
		} catch (Exception e) {
			
			//작업 실패
			CallbackData finishCallback = CallbackData.finishFail(workJobIdx, e.getMessage());
			callbackable.sendCallback(this, finishCallback);
		}
		//클러스터 작업 내용 작성.
		return result;
	}
	
	/**
	 * Cloud 클러스터 작업 내용 작성.(생성, 수정, 삭제)
	 * @return
	 * @throws Exception
	 */
	public abstract Object clusterJob() throws Exception;
	
	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}
	
	public Long getWorkJobIdx() {
		return workJobIdx;
	}

	public void setWorkJobIdx(Long workJobIdx) {
		this.workJobIdx = workJobIdx;
	}
}
