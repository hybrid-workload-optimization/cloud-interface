package kr.co.strato.cloud.aks.plugin.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClusterJobExecutor implements Runnable {		
	private BlockingQueue<ClusterJobCallable<Object>> blockingQueue;
	private boolean isStop = false;
	private boolean isRun = false;
	private ExecutorService executor;
	private Long workJobIdx;
	
	
	public ClusterJobExecutor() {
		this.blockingQueue = new LinkedBlockingDeque<>();
	}
	
	public void start() {
		this.executor = Executors.newSingleThreadExecutor();
		this.executor.submit(this);
		this.isStop = false;
	}
	
	
	@Override
	public void run() {	
		log.info("PluginExecuter start.");
		this.isRun = true;
		while(!isStop) {
			ClusterJobCallable<Object> job = blockingQueue.poll();
			if (job == null) {
				try {Thread.sleep(500);} catch (InterruptedException e) {}
				continue;
			}
			
			log.info("ClusterJobExecutor - ClusterName: {}, Queue Size: {}", job.getClusterName(), getJobSize());
			executeJob(job);
		}
		this.isRun = false;
		log.info("PluginExecuter stop.");
	}
	
	/**
	 * Cluster 작업 실행.
	 * @param clusterJob
	 */
	private void executeJob(ClusterJobCallable<Object> clusterJob) {
		String clusterName = clusterJob.getClusterName();
		this.workJobIdx = clusterJob.getWorkJobIdx();
		
		log.info("Cluster Job Start.");
		log.info("Cluster name: {}", clusterName);
		log.info("WorkjobIdx: {}", workJobIdx);		
		
		Object result = null;
		try {
			result = Executors.newSingleThreadExecutor().submit(clusterJob).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
        
		log.info("Cluster Job Finish.");
		log.info("WorkjobIdx: {}", workJobIdx);
		
		if(result != null) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String resultStr = gson.toJson(result);
			log.info("Result: {}", resultStr);
		}		
	}


	/**
	 * Job 등록
	 * @param clusterJob
	 */
	public void putClusterJob(ClusterJobCallable<Object> clusterJob) {
		try {
			blockingQueue.put(clusterJob);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public int getJobSize() {
		return blockingQueue.size();
	}


	public void stop() {
		this.isStop = true;
		if(this.executor != null) {
			this.executor.shutdown();
		}
	}

	public boolean isRun() {
		return isRun;
	}

	public Long getCurrentJobIdx() {
		return workJobIdx;
	}
}
 