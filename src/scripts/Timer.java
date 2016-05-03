package scripts;

public class Timer extends Thread{
	private int millis;
	
	public Timer(int seconds) {
		millis = seconds*1000;
	}
	
	public void run(){
		synchronized(this){
			try {
				sleep(millis);
				notify();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
	}
}
