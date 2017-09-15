package net.zdsoft.office.dailyoffice.sync.job;

public class YKTSyncJob implements Runnable{

	@Override
	public void run() {
		YKTSyncThread job = new YKTSyncThread();
    	Thread thread = new Thread(job);
    	thread.start();
	}

}
